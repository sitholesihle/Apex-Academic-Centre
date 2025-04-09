package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entry;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "province")
    private String province;

    @Column(name = "country")
    private String country;

    @Column(name = "instrLanguage")
    private String instrLanguage;

    @Column(name = "tutorFor")
    private String tutorFor;

    @Column(name = "helpWith")
    private String helpWith;

    @Column(name = "schName")
    private String schName;

    @Column(name = "schSurname")
    private String schSurname;

    @Column(name = "schGrade")
    private String schGrade;

    @Column(name = "schSyllabus")
    private String schSyllabus;

    @Column(name = "unName")
    private String unName;

    @Column(name = "unSurname")
    private String unSurname;

    @Column(name = "unYear")
    private String unYear;

    @Column(name = "subject")
    private String subject;

    @Column(name = "tutorStyle")
    private String tutorStyle;

    @Column(name = "address")
    private String address;

    @Column(name = "suburb")
    private String suburb;

    @Column(name = "message")
    private String message;

    @Column(name = "tutorOption")
    private String tutorOption;

    @Column(name = "status")
    private String status;

    @Column(name = "packageType")
    private String packageType;

    @Column(name = "tutorName")
    private String tutorName;

    @Column(name = "tutorEmail")
    private String tutorEmail;

    @Column(name = "isPaid")
    private String isPaid;

    @Column(name = "paidAmount")
    private String paidAmount;

    @Column(name = "Sessions")
    private String sessions;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "module")
    private String module;

    @Column(name = "specification")
    private String specification;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    public Booking() {
        super();
        this.createdAt = LocalDateTime.now(); 
    }

    public Booking(String name, String surname, String email, String phone, String province, String country,
                   String instrLanguage, String tutorFor, String helpWith, String schName, String schSurname, String schGrade,
                   String schSyllabus, String unName, String unSurname, String unYear, String subject, String tutorStyle,
                   String address, String suburb, String message, String tutorOption, String status, String packageType,
                   String tutorName, String tutorEmail, String isPaid, String paidAmount, String sessions, 
                   String qualification, String module, String specification) {
        super();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.province = province;
        this.country = country;
        this.instrLanguage = instrLanguage;
        this.tutorFor = tutorFor;
        this.helpWith = helpWith;
        this.schName = schName;
        this.schSurname = schSurname;
        this.schGrade = schGrade;
        this.schSyllabus = schSyllabus;
        this.unName = unName;
        this.unSurname = unSurname;
        this.unYear = unYear;
        this.subject = subject;
        this.tutorStyle = tutorStyle;
        this.address = address;
        this.suburb = suburb;
        this.message = message;
        this.tutorOption = tutorOption;
        this.status = status;
        this.packageType = packageType;
        this.tutorName = tutorName;
        this.tutorEmail = tutorEmail;
        this.isPaid = isPaid;
        this.paidAmount = paidAmount;
        this.sessions = sessions;
        this.qualification = qualification;
        this.module = module;
        this.specification = specification;
        this.createdAt = LocalDateTime.now(); 
    }

    // Getters and setters
    public Long getEntry() {
        return entry;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getInstrLanguage() {
        return instrLanguage;
    }

    public void setInstrLanguage(String instrLanguage) {
        this.instrLanguage = instrLanguage;
    }

    public String getTutorFor() {
        return tutorFor;
    }

    public void setTutorFor(String tutorFor) {
        this.tutorFor = tutorFor;
    }

    public String getHelpWith() {
        return helpWith;
    }

    public void setHelpWith(String helpWith) {
        this.helpWith = helpWith;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public String getSchSurname() {
        return schSurname;
    }

    public void setSchSurname(String schSurname) {
        this.schSurname = schSurname;
    }

    public String getSchGrade() {
        return schGrade;
    }

    public void setSchGrade(String schGrade) {
        this.schGrade = schGrade;
    }

    public String getSchSyllabus() {
        return schSyllabus;
    }

    public void setSchSyllabus(String schSyllabus) {
        this.schSyllabus = schSyllabus;
    }

    public String getUnName() {
        return unName;
    }

    public void setUnName(String unName) {
        this.unName = unName;
    }

    public String getUnSurname() {
        return unSurname;
    }

    public void setUnSurname(String unSurname) {
        this.unSurname = unSurname;
    }

    public String getUnYear() {
        return unYear;
    }

    public void setUnYear(String unYear) {
        this.unYear = unYear;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTutorStyle() {
        return tutorStyle;
    }

    public void setTutorStyle(String tutorStyle) {
        this.tutorStyle = tutorStyle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTutorOption() {
        return tutorOption;
    }

    public void setTutorOption(String tutorOption) {
        this.tutorOption = tutorOption;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getTutorEmail() {
        return tutorEmail;
    }

    public void setTutorEmail(String tutorEmail) {
        this.tutorEmail = tutorEmail;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getSessions() {
        return sessions;
    }

    public void setSessions(String sessions) {
        this.sessions = sessions;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
