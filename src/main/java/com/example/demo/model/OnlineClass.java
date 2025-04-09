package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class OnlineClass {
	
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
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "idNumber")
	private String idNumber;
	
	@Column(name = "province")
	private String province;
	
	@Column(name = "date")
	private String date;
	
	@Column(name = "subject")
	private String subject;
	
	@Column(name = "days")
	private String availableDays;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "amountPaid")
	private String amountPaid;
	
	@Column(name = "action")
	private String action;

	@Column(name = "createdAt")
    private LocalDateTime createdAt;
	public OnlineClass() {
		super();
		this.createdAt = LocalDateTime.now(); 
		
	}

	public OnlineClass(String name, String surname, String email, String phone, String gender, String idNumber,
			String province, String date, String subject, String availableDays, String message, String status,
			String amountPaid) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.idNumber = idNumber;
		this.province = province;
		this.date = date;
		this.subject = subject;
		this.availableDays = availableDays;
		this.message = message;
		this.status = status;
		this.amountPaid = amountPaid;
		this.action = "Pending";
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAvailableDays() {
		return availableDays;
	}

	public void setAvailableDays(String availableDays) {
		this.availableDays = availableDays;
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

	public Long getEntry() {
		return entry;
	}

	public void setEntry(Long entry) {
		this.entry = entry;
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
