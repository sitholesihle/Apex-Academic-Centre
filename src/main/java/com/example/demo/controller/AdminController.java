package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.BecomeTutor;
import com.example.demo.model.Booking;
import com.example.demo.model.MathsClass;
import com.example.demo.model.OnlineClass;
import com.example.demo.model.PaidBookings;
import com.example.demo.model.Review;
import com.example.demo.model.Tutor;
import com.example.demo.service.AdminService;
import com.example.demo.service.BecomeTutorService;
import com.example.demo.service.BookingService;
import com.example.demo.service.MathsClassService;
import com.example.demo.service.OnlineClassService;
import com.example.demo.service.PaidBookingsService;
import com.example.demo.service.ReviewService;
import com.example.demo.service.TutorService;

import jakarta.servlet.http.HttpSession;



@Controller
public class AdminController {
	
	@Autowired
	 private AdminService adminService;
	
	 @Autowired
	 private TutorService tutorService;
	 
	 @Autowired
	 private BookingService bookingService;
	 
	 @Autowired
	 private OnlineClassService onlineService;
	 
	 @Autowired
	 private ReviewService reviewService;
	 
	 @Autowired
	 private MathsClassService mathsService;
	 
	@Autowired
	 private BecomeTutorService becomeTutorService;
	
	 @Autowired
	 private PaidBookingsService paidService;
	
	
	/*Render login page*/
	 
	@GetMapping("/admin")
	public String admin() {
		
		return "adminLogin";
	}
	
	/*Authentication*/
	
	 @PostMapping("/login")
	public String loginAdmin(@RequestParam String email, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
	
		 if (adminService.validateAdmin(email, password)) {
	        session.setAttribute("adminEmail", email); // Store email in session
	        adminService.logInAdmin(email, session); // Register this session
	        return "redirect:/dashboard"; // Redirect to dashboard page
	    } else {
	        // Use flash attributes to store the message
	        redirectAttributes.addFlashAttribute("message", "Invalid credentials.");
	        return "redirect:/admin"; // Redirect back to login page
	        
	    }
	}
	 
	 
	    @GetMapping("/logout")
	    public String logout(HttpSession session) {
	        String email = (String) session.getAttribute("adminEmail");
	        if (email != null) {
	            adminService.logOutAdmin(email); // Remove this admin from logged-in users
	            session.invalidate(); // Invalidate the session
	        }
	        return "adminLogin"; // Redirect to the login page
	    }
	    
	    
	 
	 /*Authentication*/
	    
	    
	 /*Dashboard*/

		@GetMapping("/dashboard")
	       public String adminDashboard(HttpSession session, Model model) {
			
			
			    String email = (String) session.getAttribute("adminEmail");
			    if (email == null || !adminService.isAdminLoggedIn(email)) {
			    	 return "redirect:/admin"; // Redirect back to login page
			    }
			    
				 List<Tutor> listTutors = tutorService.getAllTutors();

				 Page<Tutor> page = tutorService.findPage(listTutors,1,10);



		     // Get the total number of pages and total items
		     long totalPages = page.getTotalPages();
		     long totalItems = page.getTotalElements();
		     List<Tutor> tutor = page.getContent();
			
	
			 List<Booking> booking = bookingService.listAll();
	  
	        booking.sort((tutor1, tutor2) -> {

	            // Check if createdAt dates are not null
	            if (tutor1.getCreatedAt() == null && tutor2.getCreatedAt() == null) {
	                return 0; // Both createdAt are null, they are considered equal
	            } else if (tutor1.getCreatedAt() == null) {
	                return 1; // tutor1's createdAt is null, so tutor2 should come first
	            } else if (tutor2.getCreatedAt() == null) {
	                return -1; // tutor2's createdAt is null, so tutor1 should come first
	            }

	            // If both createdAt are not null, compare them
	            return tutor2.getCreatedAt().compareTo(tutor1.getCreatedAt());
	        });

			
			List<Review> reviews = reviewService.loadedReviews();
			List<OnlineClass> rewrite = onlineService.loadedClasses();

			// Sorting the list with "Not opened" classes on top
			rewrite.sort((class1, class2) -> {
			    // First compare based on the status, putting "Not opened" at the top
			    if ("Pending".equals(class1.getAction()) && !"Pending".equals(class2.getAction())) {
			        return -1; // class1 should come before class2
			    } else if (!"Pending".equals(class1.getAction()) && "Pending".equals(class2.getAction())) {
			        return 1; // class1 should come after class2
			    }
			    return 0; // If both have the same status, maintain the order
			});
			
			
			List<MathsClass> mathsClasses = mathsService.loadedMathsClasses();
			
			// Sorting the list with "Not opened" classes on top
			mathsClasses.sort((class1, class2) -> {
			    // First compare based on the status, putting "Not opened" at the top
			    if ("Pending".equals(class1.getAction()) && !"Pending".equals(class2.getAction())) {
			        return -1; // class1 should come before class2
			    } else if (!"Pending".equals(class1.getAction()) && "Pending".equals(class2.getAction())) {
			        return 1; // class1 should come after class2
			    }
			    return 0; // If both have the same status, maintain the order
			});
			

			List<BecomeTutor> becomeTutor = becomeTutorService.loadedTutors();
			
	        becomeTutor.sort((tutor1, tutor2) -> tutor2.getCreatedAt().compareTo(tutor1.getCreatedAt()));
	        
	 
	        List<PaidBookings> paid = paidService.listAll();
			
			if(email.equals("info@apexacademiccentre.co.za")) {
			
		     model.addAttribute("tutors", tutor);
		     model.addAttribute("currentPage", 1);
		     
		     model.addAttribute("totalPages", totalPages);
		     model.addAttribute("totalItems", totalItems);
		     
		     model.addAttribute("bookings", booking);
		     model.addAttribute("reviews", reviews);
		     
		     model.addAttribute("rewrite", rewrite);
		     model.addAttribute("mathsClasses", mathsClasses);
		     
		     model.addAttribute("becomeTutor", becomeTutor);
		     model.addAttribute("paid", paid);
		     		     
		     model.addAttribute("currentPage", 1);
	
		     return "dashboard";
			
			}
			
			else {
		
					
				return "adminLogin";
				
				
			}
		}
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    

}
