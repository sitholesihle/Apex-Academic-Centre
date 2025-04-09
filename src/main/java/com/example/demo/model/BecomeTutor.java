package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.*;

@Entity
public class BecomeTutor {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "country")
    private String country;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "experience")
    private int experience;

    @Column(name = "phone")
    private String phone;

    @Column(name = "area")
    private String area;
    
    @Column(name = "address")
    private String address;

    @Column(name = "province")
    private String province;

    @Column(name = "subjects")
    private String subjects;

    @Column(name = "grades")
    private String grades;

    @Column(name = "syllabus")
    private String syllabus;

    @Column(name = "tutorOptions")
    private String tutorOptions;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "specification")
    private String specification;
    
    @Column(name = "achievements")
    private String achievements;

    @Column(name = "bio", length = 600)
    private String bio;
    
    @Column(name = "tutorsUniversityStudents")
    private String tutorsUniversityStudents;

    @Column(name = "about", length = 600)
    private String about;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] cv;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] education;  

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] idPassport;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] image;
    
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    // Default constructor
    public BecomeTutor() {
        super();
        this.createdAt = LocalDateTime.now(); 
    }

    // Parameterized constructor
    public BecomeTutor(String name, String surname, String email, String country, Date dob,
                       int experience, String phone, String area, String address, String province,
                       String subjects, String grades, String syllabus, String tutorOptions,String achievements,
                       String qualification, String specification, String bio,String tutorsUniversityStudents,
                       String about, byte[] cv, byte[] education,
                       byte[] idPassport, byte[] image) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.country = country;
        this.dob = dob;
        this.experience = experience;
        this.phone = phone;
        this.area = area;
        this.address = address;
        this.province = province;
        this.subjects = subjects;
        this.grades = grades;
        this.syllabus = syllabus;
        this.tutorOptions = tutorOptions;
        this.qualification = qualification;
        this.specification = specification;
        this.bio = bio;
        this.about = about;
        this.cv = cv;
        this.education = education; 
        this.idPassport = idPassport;
        this.image = image;
        this.achievements = achievements;
        this.tutorsUniversityStudents = tutorsUniversityStudents;
        this.createdAt = LocalDateTime.now();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public String getTutorOptions() {
        return tutorOptions;
    }

    public void setTutorOptions(String tutorOptions) {
        this.tutorOptions = tutorOptions;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public byte[] getCv() {
        return cv;
    }

    public void setCv(byte[] cv) {
        this.cv = cv;
    }

    public byte[] getEducation() {
        return education; 
    }

    public void setEducation(byte[] education) {
        this.education = education; 
    }

    public byte[] getIdPassport() {
        return idPassport;
    }

    public void setIdPassport(byte[] idPassport) {
        this.idPassport = idPassport;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    
    public String getTutorsUniversityStudents() {
        return tutorsUniversityStudents;
    }

    public void setTutorsUniversityStudents(String tutorsUniversityStudents) {
        this.tutorsUniversityStudents = tutorsUniversityStudents;
    }
    
    public String getAchievements() {
        return achievements;
    }

 
    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
