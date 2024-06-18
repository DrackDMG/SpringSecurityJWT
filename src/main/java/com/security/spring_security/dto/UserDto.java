package com.security.spring_security.dto;


import com.security.spring_security.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDto {
    private String username;
    private String name;
    private String role;

    public UserDto() {
    }

    public UserDto(String username, String name, String role) {
        this.username = username;
        this.name = name;
        this.role = role;
    }

    public static List<UserDto> fromEntity(List<User> users) {
        return users.stream().map(user -> new UserDto(user.getUsername(), user.getName(), user.getRole().name())).collect(Collectors.toList());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
