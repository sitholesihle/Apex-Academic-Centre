package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Review {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entryId;
    
	@Column(name = "name")
	private String name;
	
	@Column(name = "tutorEmail")
	private String tutorEmail;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "rating")
	private int rating;
	
	@Column(name = "status")
	private String status;

	public Review() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Review(String name, String tutorEmail, String message, int rating, String status) {
		super();
		this.name = name;

		this.tutorEmail = tutorEmail;
		this.message = message;
		this.rating = rating;
		this.status = status;
	}

	public String getTutorEmail() {
		return tutorEmail;
	}

	public void setTutorEmail(String tutorEmail) {
		this.tutorEmail = tutorEmail;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getEntryId() {
		return entryId;
	}

}
