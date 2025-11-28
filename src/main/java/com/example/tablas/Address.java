package com.example.tablas;

public class Address {
    private int addressId;
    private String line1;
    private String line2;
    private String line3;
    private String townCity;
    private String stateProvince;
    private int countryCode;
    
    public Address() {}
    
    public Address(int addressId, String line1, String line2, String line3, 
                   String townCity, String stateProvince, int countryCode) {
        this.addressId = addressId;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.townCity = townCity;
        this.stateProvince = stateProvince;
        this.countryCode = countryCode;
    }
    
    // Getters and Setters
    public int getAddressId() { return addressId; }
    public void setAddressId(int addressId) { this.addressId = addressId; }
    
    public String getLine1() { return line1; }
    public void setLine1(String line1) { this.line1 = line1; }
    
    public String getLine2() { return line2; }
    public void setLine2(String line2) { this.line2 = line2; }
    
    public String getLine3() { return line3; }
    public void setLine3(String line3) { this.line3 = line3; }
    
    public String getTownCity() { return townCity; }
    public void setTownCity(String townCity) { this.townCity = townCity; }
    
    public String getStateProvince() { return stateProvince; }
    public void setStateProvince(String stateProvince) { this.stateProvince = stateProvince; }
    
    public int getCountryCode() { return countryCode; }
    public void setCountryCode(int countryCode) { this.countryCode = countryCode; }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", line1='" + line1 + '\'' +
                ", line2='" + line2 + '\'' +
                ", line3='" + line3 + '\'' +
                ", townCity='" + townCity + '\'' +
                ", stateProvince='" + stateProvince + '\'' +
                ", countryCode=" + countryCode +
                '}';
    }
}