package cz.easyExam.rest.DTO;

import cz.easyExam.model.User;

public class UserDTO {
    private String username;
    private String email;
    private String oldPassword;
    private String password;

    public User convertToUser(){
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setEmail(email);
        return user;
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getOldPassword() {
        return oldPassword;
    }
    public String getPassword() {
        return password;
    }
}

