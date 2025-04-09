package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MathsClass {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entry;
    
    // Parents
    @Column(name = "pname")
    private String pname;
    
    @Column(name = "psurname")
    private String psurname;
    
    @Column(name = "pemail")
    private String pemail;
    
    @Column(name = "pphone")
    private String pphone;
    
    // Student
    @Column(name = "sname")
    private String sname;
    
    @Column(name = "ssurname")
    private String ssurname;
    
    @Column(name = "grade")
    private String grade; // Keeping as String
    
    @Column(name = "subject")
    private String subject;
    
    @Column(name = "curriculum")
    private String curriculum;
    
    @Column(name = "marks")
    private String marks; // Keeping as String
    
    @Column(name = "message")
    private String message;
    
    @Column(name = "supportOptions")
    private String supportOptions;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "amountPaid")
    private String amountPaid; // Keeping as String
    
    @Column(name = "action")
	private String action;
    
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    // Default constructor
    public MathsClass() {
    	super();
    	 this.createdAt = LocalDateTime.now(); 
        
    }

    // Parameterized constructor
    public MathsClass(String pname, String psurname, String pemail, String pphone, String grade, String sname, String ssurname, String subject, String curriculum, String marks, String supportOptions, String message, String status, String amountPaid) {
        this.pname = pname;
        this.psurname = psurname;
        this.pemail = pemail;
        this.pphone = pphone;
        this.sname = sname;
        this.ssurname = ssurname;
        this.grade = grade;
        this.subject = subject;
        this.curriculum = curriculum;
        this.marks = marks;
        this.supportOptions = supportOptions;
        this.message = message;
        this.status = status;
        this.amountPaid = amountPaid;
        this.action = "Pending";
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPsurname() {
        return psurname;
    }

    public void setPsurname(String psurname) {
        this.psurname = psurname;
    }

    public String getPemail() {
        return pemail;
    }

    public void setPemail(String pemail) {
        this.pemail = pemail;
    }

    public String getPphone() {
        return pphone;
    }

    public void setPphone(String pphone) {
        this.pphone = pphone;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSsurname() {
        return ssurname;
    }

    public void setSsurname(String ssurname) {
        this.ssurname = ssurname;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade; // Correctly setting grade
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getSupportOptions() {
        return supportOptions;
    }

    public void setSupportOptions(String supportOptions) {
        this.supportOptions = supportOptions;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }
    
    
    public Long getEntry() {
        return entry;
    }

    public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
