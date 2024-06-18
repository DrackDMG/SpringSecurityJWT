package com.security.spring_security.util;

import java.util.Arrays;
import java.util.List;

public enum Role {


    CUSTOMER(Arrays.asList(Permission.READ_ALL_PRODUCT)),

    ADMIN(Arrays.asList(Permission.SAVE_ONE_PRODUCT, Permission.EDIT_ONE_PRODUCT, Permission.DELETE_ONE_PRODUCT, Permission.READ_ALL_USER)),

    ADMINISTRATOR(Arrays.asList(Permission.READ_ALL_PRODUCT, Permission.SAVE_ONE_PRODUCT, Permission.READ_ONE_PRODUCT, Permission.EDIT_ONE_PRODUCT, Permission.DELETE_ONE_PRODUCT, Permission.READ_ALL_USER, Permission.SAVE_ONE_USER, Permission.EDIT_ONE_USER, Permission.DELETE_ONE_USER));

    private List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
