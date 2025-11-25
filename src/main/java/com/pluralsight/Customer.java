package com.pluralsight;

public class Customer {
    private String contactName;
    private String companyName;
    private String city;
    private String country;
    private String phone;

    public Customer(String contactName, String companyName,
                    String city, String country, String phone){

        this.contactName = contactName;
        this.companyName = companyName;
        this.city = city;
        this.country = country;
        this.phone = phone;
    }

    public String getContactName() {
        return contactName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return String.format("| %-25s | %-30s | %-15s | %-15s | %-15s |",
                contactName, companyName, city, country, phone);
    }
}

