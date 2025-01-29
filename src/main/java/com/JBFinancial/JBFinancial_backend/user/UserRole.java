package com.JBFinancial.JBFinancial_backend.user;


public enum UserRole {
    ADMIN("admin"),
    USER("usuario");

    private String role;

    UserRole(String role){
        this.role = role;
    }

   public String getRole() {
       return role;
   }
}
