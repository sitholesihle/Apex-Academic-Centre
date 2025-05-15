package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.PaidBookings;
import com.example.demo.repository.PaidBookingsRepository;

import jakarta.annotation.PostConstruct;

@Service
public class PaidBookingsService {
	
	@Autowired
	private PaidBookingsRepository repo;
	
	private List<PaidBookings> bookings;
	
    @PostConstruct
    public void init() {
        // Load data from the database when the application starts
    	bookings = repo.findAll();  // Or your custom query
        
        System.out.println("Paid Bookings Loaded.");
                
    }
    
	public List<PaidBookings> loadedPaidbookingd(){
		
		 return bookings;
	}
	
	
	public List<PaidBookings> listAll(){
		
		 return repo.findAll();
	}
	
	public void save(PaidBookings msc) {
		
		  repo.save(msc);
	}

}
