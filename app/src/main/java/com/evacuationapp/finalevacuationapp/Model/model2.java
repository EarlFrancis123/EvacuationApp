package com.evacuationapp.finalevacuationapp.Model;

public class model2
{
  String name,course,email,purl;
    String firstName, lastName, middleName, contactInfo,gender,age,address,barangay,headOfFamily,evacuationName,streetAddress,state,country;
    String  AddReliefGoodsEvacuationName, AddReliefGoodsFood, AddReliefGoodsFoodPerPerson,AddReliefGoodsWater,AddReliefGoodsWaterPerPerson,AddReliefGoodsSponsor,AddReliefGoodsDate;
            ;
    model2()
    {
    }

    public model2(String name, String addReliefGoodsEvacuationName, String addReliefGoodsFood, String addReliefGoodsWater, String addReliefGoodsSponsor, String addReliefGoodsDate) {
        this.name = name;
        AddReliefGoodsEvacuationName = addReliefGoodsEvacuationName;
        AddReliefGoodsFood = addReliefGoodsFood;
        AddReliefGoodsWater = addReliefGoodsWater;
        AddReliefGoodsSponsor = addReliefGoodsSponsor;
        AddReliefGoodsDate = addReliefGoodsDate;
    }

    public String getAddReliefGoodsEvacuationName() {
        return AddReliefGoodsEvacuationName;
    }

    public void setAddReliefGoodsEvacuationName(String addReliefGoodsEvacuationName) {
        AddReliefGoodsEvacuationName = addReliefGoodsEvacuationName;
    }

    public String getAddReliefGoodsFood() {
        return AddReliefGoodsFood;
    }

    public void setAddReliefGoodsFood(String addReliefGoodsFood) {
        AddReliefGoodsFood = addReliefGoodsFood;
    }

    public String getAddReliefGoodsFoodPerPerson() {
        return AddReliefGoodsFoodPerPerson;
    }

    public void setAddReliefGoodsFoodPerPerson(String addReliefGoodsFoodPerPerson) {
        AddReliefGoodsFoodPerPerson = addReliefGoodsFoodPerPerson;
    }

    public String getAddReliefGoodsWater() {
        return AddReliefGoodsWater;
    }

    public void setAddReliefGoodsWater(String addReliefGoodsWater) {
        AddReliefGoodsWater = addReliefGoodsWater;
    }

    public String getAddReliefGoodsWaterPerPerson() {
        return AddReliefGoodsWaterPerPerson;
    }

    public void setAddReliefGoodsWaterPerPerson(String addReliefGoodsWaterPerPerson) {
        AddReliefGoodsWaterPerPerson = addReliefGoodsWaterPerPerson;
    }

    public String getAddReliefGoodsSponsor() {
        return AddReliefGoodsSponsor;
    }

    public void setAddReliefGoodsSponsor(String addReliefGoodsSponsor) {
        AddReliefGoodsSponsor = addReliefGoodsSponsor;
    }

    public String getAddReliefGoodsDate() {
        return AddReliefGoodsDate;
    }

    public void setAddReliefGoodsDate(String addReliefGoodsDate) {
        AddReliefGoodsDate = addReliefGoodsDate;
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
