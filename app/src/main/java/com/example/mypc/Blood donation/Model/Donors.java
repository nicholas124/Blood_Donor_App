package com.example.mypc.inventorymanagement.Model;

public class Donors {

    private String Name;
    private String Email;
    private String Phone;
    private String Gender;
    private String Image;
    private String Address;
    private String City;
    private String Status;
    private String Option;

    public Donors() {
    }

    public Donors(String name, String email, String phone, String gender, String image, String address, String city, String status, String option) {
        Name = name;
        Email = email;
        Phone = phone;
        Gender = gender;
        Image = image;
        Address = address;
        City = city;
        Status = status;
        Option = option;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getOption() {
        return Option;
    }

    public void setOption(String option) {
        Option = option;
    }
}
