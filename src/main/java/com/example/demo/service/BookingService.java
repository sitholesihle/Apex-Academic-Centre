package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Booking;
import com.example.demo.repository.BookingRepository;


@Service
public class BookingService {
	
	@Autowired
	private BookingRepository repo;
	
	public List<Booking> listAll(){
		
		 return repo.findAll();
	}
	
	public void save(Booking msc) {
		
		  repo.save(msc);
	}
	
	public void delete(Booking msc) {
		
		repo.delete(msc);
		
	}
	
	public Booking findOneBook(Long id) {
		
	 Optional<Booking> book = repo.findById(id);
	 
	    Booking oneBooking = book.get();
	      
	    return oneBooking;
	
	}
	
	public void update(Long id) {
	       
		 Optional<Booking> book = repo.findById(id);
		 
		 if (book.isPresent()){
			 
			 Booking existingBooking = book.get();

			 existingBooking.setStatus("approved");
			  
	            // Save the updated student object
	            repo.save(existingBooking);
	            
        }
	        
	 }

}
