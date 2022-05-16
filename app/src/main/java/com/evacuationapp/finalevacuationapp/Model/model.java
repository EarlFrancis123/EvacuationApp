package com.evacuationapp.finalevacuationapp.Model;

public class model
{
  String name,course,email,purl;
    String firstName, lastName, middleName, contactInfo,gender,age,address,barangay,headOfFamily,evacuationName,streetAddress,state,country;
    model()
    {

    }
    public model(String name, String course, String email, String purl) {
        this.name = name;
        this.course = course;
        this.email = email;
        this.purl = purl;
    }

    public model(String firstName, String lastName, String middleName, String contactInfo, String gender, String age, String address, String barangay, String headOfFamily, String evacuationName, String streetAddress, String state, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.contactInfo = contactInfo;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.barangay = barangay;
        this.headOfFamily = headOfFamily;
        this.evacuationName = evacuationName;
        this.streetAddress = streetAddress;
        this.state = state;
        this.country = country;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getHeadOfFamily() {
        return headOfFamily;
    }

    public void setHeadOfFamily(String headOfFamily) {
        this.headOfFamily = headOfFamily;
    }

    public String getEvacuationName() {
        return evacuationName;
    }

    public void setEvacuationName(String evacuationName) {
        this.evacuationName = evacuationName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
