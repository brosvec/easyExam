package cz.easyExam.service;


import cz.easyExam.dao.UserDao;
import cz.easyExam.model.User;
import cz.easyExam.rest.DTO.UserDTO;
import cz.easyExam.security.SecurityUtils;
import cz.easyExam.security.model.UserDetails;
import cz.easyExam.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Objects;

@Service
public class UserService {

    private final UserDao dao;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void persist(User user) {
        Objects.requireNonNull(user);
        user.encodePassword(passwordEncoder);
        user.setRole(Constants.DEFAULT_ROLE);
        dao.persist(user);
    }

    @Transactional(readOnly = true)
    public boolean exists(String username) {
        return dao.findByUsername(username) != null;
    }

    public User getCurrentUser() {
        final User currentUser = SecurityUtils.getCurrentUser();
        assert currentUser != null;
        return currentUser;
    }
    @Transactional
    public boolean updatePassword(UserDTO user) throws Exception {
        final User currentUser = dao.find(SecurityUtils.getCurrentUser().getId());
        if(passwordEncoder.matches(user.getOldPassword(),currentUser.getPassword())){
            currentUser.setPassword(user.getPassword());
            currentUser.encodePassword(passwordEncoder);
            return true;
        }else{
            return false;
        }
    }

    @Transactional
    public User updateUser(UserDTO user) throws Exception{
        final User currentUser = dao.find(SecurityUtils.getCurrentUser().getId());
        currentUser.setEmail(user.getEmail());
        currentUser.setUsername(user.getUsername());
        dao.update(currentUser);
        updateSecurityCredentials(currentUser);
        return currentUser;
    }

    public void updateSecurityCredentials(User currentUser) throws Exception{
        UserDetails userDetails = new UserDetails((User)currentUser.clone());
        userDetails.getUser().erasePassword();
        SecurityUtils.setCurrentUser(userDetails);
    }
}
