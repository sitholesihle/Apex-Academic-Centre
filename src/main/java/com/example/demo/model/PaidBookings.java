package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PaidBookings {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long entry;

	    @Column(name = "clientName")
	    private String clientName;

	    @Column(name = "studentName")
	    private String studentName;

	    @Column(name = "amount")
	    private String amount;

	    @Column(name = "numSessions")
	    private String numSessions;

	    @Column(name = "email")
	    private String email;

	    @Column(name = "tutoringOption")
	    private String tutoringOption;

	    @Column(name = "createdAt")
	    private LocalDateTime createdAt;

		public PaidBookings() {
			super();
			this.createdAt = LocalDateTime.now(); 
		}

		public PaidBookings(String clientName, String studentName, String amount, String numSessions,
				String email, String tutoringOption) {
			super();
		
			this.clientName = clientName;
			this.studentName = studentName;
			this.amount = amount;
			this.numSessions = numSessions;
			this.email = email;
			this.tutoringOption = tutoringOption;
		}

		public Long getEntry() {
			return entry;
		}

		public void setEntry(Long entry) {
			this.entry = entry;
		}

		public String getClientName() {
			return clientName;
		}

		public void setClientName(String clientName) {
			this.clientName = clientName;
		}

		public String getStudentName() {
			return studentName;
		}

		public void setStudentName(String studentName) {
			this.studentName = studentName;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getNumSessions() {
			return numSessions;
		}

		public void setNumSessions(String numSessions) {
			this.numSessions = numSessions;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getTutoringOption() {
			return tutoringOption;
		}

		public void setTutoringOption(String tutoringOption) {
			this.tutoringOption = tutoringOption;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}
	    


}
