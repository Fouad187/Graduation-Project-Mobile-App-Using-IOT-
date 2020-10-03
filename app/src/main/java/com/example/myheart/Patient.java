package com.example.myheart;

public class Patient
{
    public String id,fname , lname , email , password,age,gender, doctormail,address , pressure , smoker,drinker,athelete,diabetic ,photo,security,height,weight;


    public Patient()
    {

    }

    public Patient(String id, String fname, String lname, String email, String password, String age, String gender, String doctormail, String address ,String pressure ,String smoker,String drinker ,String athelete , String diabetic,String photo,String security , String height ,String weight) {
        this.id=id;
        this.height=height;
        this.weight=weight;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.doctormail = doctormail;
        this.address = address;
        this.pressure=pressure;
        this.smoker=smoker;
        this.drinker=drinker;
        this.athelete=athelete;
        this.diabetic=diabetic;
        this.photo=photo;
        this.security=security;
        
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDoctormail() {
        return doctormail;
    }

    public void setDoctormail(String doctormail) {
        this.doctormail = doctormail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getSmoker() {
        return smoker;
    }

    public void setSmoker(String smoker) {
        this.smoker = smoker;
    }

    public String getDrinker() {
        return drinker;
    }

    public void setDrinker(String drinker) {
        this.drinker = drinker;
    }

    public String getAthelete() {
        return athelete;
    }

    public void setAthelete(String athelete) {
        this.athelete = athelete;
    }

    public String getDiabetic() {
        return diabetic;
    }

    public void setDiabetic(String diabetic) {
        this.diabetic = diabetic;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
