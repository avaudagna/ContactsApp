package com.alan.contactsapp.adapter.model;

/**
 * POJO class representing a Contact.
 *
 * @author alan
 */
public class Contact {
    private String name;
    private String phoneNumber;
    private String smallImageUrl;
    private String detailsUrl;
    private String company;
    private long birthDate;

    public Contact setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public Contact setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Contact setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
        return this;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public Contact setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
        return this;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public Contact setCompany(String company) {
        this.company = company;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public Contact setBirthDate(long birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public long getBirthDate() {
        return birthDate;
    }
}
