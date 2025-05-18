package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository repo;
	
	@Autowired
	private TutorService tutorService;
	
	private List<Review> reviews;
	
    @PostConstruct
    public void init() {
        // Load data from the database when the application starts
    	reviews = repo.findAll();  // Or your custom query
        
        System.out.println("Reviews Loaded.");
                
    }
    
	public List<Review> loadedReviews(){
		
		 return reviews;
	}
	
	public List<Review> listAll(){
		
		 return repo.findAll();
	}
	
	public void save(Review msc) {
		
		  repo.save(msc);
	}
	
	@Async
	public void updateReview(Long id) {
	       
		 Optional<Review> rev = repo.findById(id);
		 
		 if (rev.isPresent()){
			 
			 Review extReview = rev.get();
			 
			   String useEmail = extReview.getTutorEmail();
			   int rate = extReview.getRating();
			   
			   tutorService.updateRatings(useEmail, rate);
			   extReview.setStatus("approved");
			  
	            // Save the updated student object
	            repo.save(extReview);
	            
	            // Update the object in the list
	            for (Review css : reviews) {
	            	
	                if (css.equals(extReview)) {
	                    css.setStatus("approved");
	                    break;
	                }
	            }
	            
	            
	            
           }
		 
		   
	        
	 }
	
	
	public Review findOneReview(Long id) {
		
		 Optional<Review> rev = repo.findById(id);
		 
		 Review extReview = new Review();
		 
         if (rev.isPresent()){
			 
			  extReview = rev.get();
			 
 
         }

         return extReview;
         
	}
	
	/*update list*/
	
	@Async
	public void updateBooking(Review review) {
		 
		        // Remove the booking from the list
		        reviews.remove(review);
		        
		        System.out.println("Review Removed");

      }
	
	public void reloadReviews() {
	    this.reviews = repo.findAll();
	}
	


}
