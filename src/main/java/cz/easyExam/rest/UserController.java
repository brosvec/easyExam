package cz.easyExam.rest;

import cz.easyExam.dao.UserDao;
import cz.easyExam.exception.ValidationException;
import cz.easyExam.model.User;
import cz.easyExam.rest.DTO.UserDTO;
import cz.easyExam.rest.util.RestUtils;
import cz.easyExam.security.model.AuthenticationToken;
import cz.easyExam.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/rest/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a new user.
     *
     * @param userDto UserDTO data
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity register(@RequestBody UserDTO userDto) {
        try {
            User user = userDto.convertToUser();
            userService.persist(user);
            LOG.debug("User {} successfully registered.", user);
            final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/current");
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
        catch (ValidationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getCurrent(Principal principal) {
        final AuthenticationToken auth = (AuthenticationToken) principal;
        return userService.getCurrentUser();
    }
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/updatePassword")
    public ResponseEntity updatePassword(@RequestBody UserDTO user) {
        try {
           if(userService.updatePassword(user) == false){
               return new ResponseEntity<>("Wrong Old Password", HttpStatus.BAD_REQUEST);
           }
            LOG.debug("Updated user pasword {}.", user);
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            LOG.error("UPDATE Error: ",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@RequestBody UserDTO user) {
        try {
            userService.updateUser(user);
            LOG.debug("Updated user {}.", user);
        }
        catch (Exception e){
            LOG.error("UPDATE Error: ",e);
        }
    }
}
