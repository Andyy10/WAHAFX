package com.example.tablas;

public class Skill {
    private int skillCode;
    private String skillName;
    
    public Skill() {}
    
    public Skill(int skillCode, String skillName) {
        this.skillCode = skillCode;
        this.skillName = skillName;
    }
    
    // Getters and Setters
    public int getSkillCode() { return skillCode; }
    public void setSkillCode(int skillCode) { this.skillCode = skillCode; }
    
    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }

    @Override
    public String toString() {
        return "Skill{" +
                "skillCode=" + skillCode +
                ", skillName='" + skillName + '\'' +
                '}';
    }
}