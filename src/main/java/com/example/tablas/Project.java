package com.example.tablas;

import java.time.LocalDate;

public class Project {
    //variables. projectId es final ya que no se puede cambiar desde la aplicación
    private int projectId;
    private int clientId;
    private String projectName;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private String projectDescription;
    private Integer budget;

    //constructor vacío
    public Project(){}

    //constructor para inicializar el registro
    public Project(int projectId, int clientId, String projectName, LocalDate projectStartDate, LocalDate projectEndDate, String projectDescription, Integer budget) {
        this.projectId = projectId;
        this.clientId = clientId;
        this.projectName = projectName;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.projectDescription = projectDescription;
        this.budget = budget;
    }

    //setters y getters

    public int getProjectId() {
        return projectId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDate getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(LocalDate projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public LocalDate getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(LocalDate projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", clientId=" + clientId +
                ", projectName='" + projectName + '\'' +
                ", projectStartDate=" + projectStartDate +
                ", projectEndDate=" + projectEndDate +
                ", projectDescription='" + projectDescription + '\'' +
                ", budget=" + budget +
                '}';
    }
}
