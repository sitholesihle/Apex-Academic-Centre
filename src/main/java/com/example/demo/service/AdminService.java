package com.example.demo.service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class AdminService {
    private final ConcurrentHashMap<String, HttpSession> loggedInAdmins = new ConcurrentHashMap<>();
    @Autowired
    private  AdminRepository repo;

    public boolean isAdminLoggedIn(String email) {
        return loggedInAdmins.containsKey(email);
    }

    public void logInAdmin(String email, HttpSession session) {
        loggedInAdmins.put(email, session);
    }

    public void logOutAdmin(String email) {
        loggedInAdmins.remove(email);
    }
    
    public boolean validateAdmin(String email, String password) {
    	
    	boolean  bFlag = false;
    	Optional<Admin> admin = repo.findById("info@apexacademiccentre.co.za");
    	
    	if (admin.isPresent()){
			 
			 Admin existingAdmin = admin.get();

		    	if(email.equals("info@apexacademiccentre.co.za") && password.equals(existingAdmin.getPassword())) {
		    		
		    		  bFlag = true;
		    	}
		    	
    	
    	}
    	
    	
    	return bFlag;
    }
    
    
    public void update(String password) {
	       
		 Optional<Admin> admin = repo.findById("info@apexacademiccentre.co.za");
		 
		 if (admin.isPresent()){
			 
			 Admin existingAdmin = admin.get();

			    existingAdmin.setPassword(password); 
			  

	            // Save the updated student object
	            repo.save(existingAdmin);
	            
        }
	        
	 }
    
	public void save(Admin msc) {
		
		  repo.save(msc);
		  
	}
	
    
    
    
}
