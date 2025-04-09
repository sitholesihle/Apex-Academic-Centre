package com.example.demo.model;


import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;


@Entity
@Table(
    name = "tutor",
    indexes = {
        @Index(name = "idx_ratings", columnList = "ratings") // This creates an index on the ratings column
    }
)
public class Tutor {
	
	@Id
	@Column(name = "email")
	private String email;

	@Column(name = "entryId")
	private int entryId;
	
	@Column(name = "fullNames")
	private String fullNames;
	
	@Column(name = "surname")
	private String surname;
	
	@Column(name = "background")
	private String background;
	
	@Column(name = "PhoneNumber")
	private String phoneNumber;
	
	@Column(name = "Subjects")
	private String subjects;
	
	@Column(name = "Grades")
	private String grades;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "trainOnline")
	private boolean canTrainOnline;
	
	@Column(name = "availability")
	private String availability;
	
	@Column(name = "syllabus")
	private String syllabus;
	
	@Column(name = "Bio")
	private String bio;
	
	@Column(name = "about" ,length = 5000)
	private String about;
	
	@Column(name = "area")
	private String area;
	
	@Column(name = "ratings")
	private int ratings;
	
	@Column(name = "hoursTutored",length = 5000)
	private String hoursTutored;
	
	@Column(name = "dob")
	private Date dob;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "module")
	private String module;
	
	@Column(name = "experience")
	private int experience;
	
	@Lob
	@Column(columnDefinition = "longblob")
	private byte[] image;
	
	@Column(name = "imageBase64")
	private String imageBase64;
	
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] cv;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] education;  

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] idPassport;
    
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
	
	public Tutor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tutor(String email, String fullNames, String background, String phoneNumber, String subjects, String grades,
			String address, String availability, String bio, String about, String hoursTutored, byte[] image , String syllabus , String area, String country, Date dob , String surname, String modules, int experience) {
		super();
		this.email = email;
		this.fullNames = fullNames;
		this.background = background;
		this.phoneNumber = phoneNumber;
		this.subjects = subjects;
		this.grades = grades;
		this.address = address;
		this.availability = availability;
		this.bio = bio;
		this.about = about;
		this.hoursTutored = hoursTutored;
		this.image = image;
		this.syllabus = syllabus;
		this.area = area;
		this.country = country;
		this.dob = dob;
		this.surname = surname;
		this.module = modules;
		this.experience = experience;
		
		String online = availability.substring(0, 6);
		
		
		if(online.equals("Online")) {
			
			  this.canTrainOnline = true;
		}
		
		else {
			
			this.canTrainOnline = false;
		}
		
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getEntryId() {
		return entryId;
	}


	public String getFullNames() {
		return fullNames;
	}

	public void setFullNames(String fullNames) {
		this.fullNames = fullNames;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isCanTrainOnline() {
		return canTrainOnline;
	}

	public void setCanTrainOnline(boolean canTrainOnline) {
		this.canTrainOnline = canTrainOnline;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public int getRatings() {
		return ratings;
	}

	public void setRatings(int ratings) {
		this.ratings = ratings;
	}

	public String getHoursTutored() {
		return hoursTutored;
	}

	public void setHoursTutored(String hoursTutored) {
		this.hoursTutored = hoursTutored;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getSyllabus() {
		return syllabus;
	}

	public void setSyllabus(String syllabus) {
		this.syllabus = syllabus;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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
	
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
	

			
}
