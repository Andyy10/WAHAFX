package com.example.tablas;

public class Employee {
    private int employeeId;
    private int roleCode;
    private int supervisorId;
    private String firstName;
    private String lastName;
    private int age;

    public Employee(){}

    public Employee(int employeeId, int roleCode, int supervisorId, String firstName, String lastName, int age) {
        this.employeeId = employeeId;
        this.roleCode = roleCode;
        this.supervisorId = supervisorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(int roleCode) {
        this.roleCode = roleCode;
    }

    public int getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(int supervisorId) {
        this.supervisorId = supervisorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", roleCode=" + roleCode +
                ", supervisorId=" + supervisorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
