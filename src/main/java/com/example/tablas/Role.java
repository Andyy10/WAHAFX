package com.example.tablas;

public class Role {
    private int roleCode;
    private String roleName;

    public Role(){}

    public Role(int roleCode, String roleName){
        this.roleCode = roleCode;
        this.roleName = roleName;
    }

    public int getRoleCode() {
        return roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleCode=" + roleCode +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
