package com.example.demo.Projection;

import java.util.Date;

public interface TutorProjection {
	
   String getEmail();	
   int getEntryId(); 
   String getFullNames();
   String getBackground();
   String getPhoneNumber();
   String getSubjects();
   String getGrades();
   String getAddress();
   boolean isCanTrainOnline();
   String getAvailability();
   String getBio();
   String getAbout();
   int getRatings();
   String getHoursTutored();
   byte[] getImage();
   String getSyllabus();
   String getArea();
   String getCountry();
   Date getDob();
   String getModule();
   int getExperience();

}
