package com.example.demo.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.BecomeTutor;
import com.example.demo.model.Booking;
import com.example.demo.model.MathsClass;
import com.example.demo.model.OnlineClass;
import com.example.demo.model.PaidBookings;
import com.example.demo.model.Review;
import com.example.demo.model.Tutor;
import com.example.demo.repository.BecomeTutorRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.service.AdminService;
import com.example.demo.service.BecomeTutorService;
import com.example.demo.service.BookingService;
import com.example.demo.service.EmailSenderService;
import com.example.demo.service.MathsClassService;
import com.example.demo.service.OnlineClassService;
import com.example.demo.service.PaidBookingsService;
import com.example.demo.service.ReviewService;
import com.example.demo.service.TutorService;

import jakarta.mail.internet.ParseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdmController {
	
	 @Autowired
	 private ReviewRepository reviewRepo;
	
	 @Autowired
	 private BecomeTutorRepository becomeTutorRepo;
	
    @Autowired         
    private HttpServletRequest request;
	
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
	 
	 @Autowired
	 private EmailSenderService senderService;
	
	
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
	        return "redirect:/adminLogin"; // Redirect back to login page
	        
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
			
		     int currentPage = 1;
			 long pageStart = Math.max(currentPage - 2, 1); // 
			 long pageEnd = Math.min(currentPage + 3, totalPages); 
	
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
		     model.addAttribute("currentPage", currentPage);
		     
		     model.addAttribute("totalPages", totalPages);
		     model.addAttribute("totalItems", totalItems);
		     
		     model.addAttribute("bookings", booking);
		     model.addAttribute("reviews", reviews);
		     
		     model.addAttribute("rewrite", rewrite);
		     model.addAttribute("mathsClasses", mathsClasses);
		     
		     model.addAttribute("becomeTutor", becomeTutor);
		     model.addAttribute("paid", paid);
		     
		     model.addAttribute("pageStart", pageStart);
		     model.addAttribute("pageEnd", pageEnd);
		     
		     		     
		     model.addAttribute("currentPage", currentPage);
	
		     return "dashboard";
			
			}
			
			else {
		
					
				return "adminLogin";
				
				
			}
		}
	    
	    
		/*Pagination for Admin*/
		
		 @GetMapping("/adminpage-{page}")
		 public String adminList(@PathVariable int page, Model model) {
			 
			 return getOnePageAdmin(page,model);
		 }
		 
		 
		 public String getOnePageAdmin(int currentPage, Model model) {

		
			 List<Tutor> listTutors = tutorService.getAllTutors();

			 Page<Tutor> page = tutorService.findPage(listTutors,currentPage,10);


		     // Get the total number of pages and total items
		     long totalPages = page.getTotalPages();
		     long totalItems = page.getTotalElements();
		     List<Tutor> tutor = page.getContent();
	
			 long pageStart = Math.max(currentPage - 2, 1); // 
			 long pageEnd = Math.min(currentPage + 3, totalPages); 
	
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
			
  	         model.addAttribute("tutors", tutor);
		     model.addAttribute("currentPage", currentPage);
		     
		     model.addAttribute("totalPages", totalPages);
		     model.addAttribute("totalItems", totalItems);
		     
		     model.addAttribute("bookings", booking);
		     model.addAttribute("reviews", reviews);
		     
		     model.addAttribute("rewrite", rewrite);
		     model.addAttribute("mathsClasses", mathsClasses);
		     
		     model.addAttribute("becomeTutor", becomeTutor);
		     model.addAttribute("paid", paid);
		     
		     model.addAttribute("pageStart", pageStart);
		     model.addAttribute("pageEnd", pageEnd);
		 
		     return "dashboard";
			
				 
		 }
		
		
		
		/*Pagination for Admin*/
		 
		/*Payment Link*/
		 
		 @PostMapping("/sendEmail")
		 public String sendEmail(@RequestParam("lclient") String clientName, @RequestParam("lstudname") String studentName , @RequestParam("lemail") String email,
				 @RequestParam("ldate") String date, @RequestParam("lamount") String amount , @RequestParam("lsession") String sess , @RequestParam("ltutor") String optTutor, Model model) {
			 
			 int clientOrBoth = 0;
			 String subject;
			 
			 if(!studentName.equals("")) {
				 
				 clientOrBoth++;
				 subject = 	"Re: Tuition Fee Payment Notification for " + studentName;
				 
			 }
			 
			 else {
				 
				 clientOrBoth = 0;
				 subject = 	"Re: Tuition Fee Payment Notification";
				 
			 }
		
	
			 clientName = clientName.replace(" ", "@");
			 studentName = studentName.replace(" ", "@");
			
			 senderService.sendEmailWithButton(email,subject,clientName,studentName,date,amount,sess,optTutor,clientOrBoth);
			
			 return getOnePageAdmin(1,model);
			 
		 }
		 
		 
	    /*Payment Link*/
		 
		/*Admin Accept Tutor*/
		    
			 @PostMapping("/add-accepted-tutor")//only admin have access
				public String addAcceptedTutor(@RequestParam("rName")  String name, @RequestParam("rEmail") String email ,
						@RequestParam("rPhone") String phone , @RequestParam("rSubjects") String subjects ,
						@RequestParam("rGrades") String grades , @RequestParam("rSyllabus") String syllabus ,
						@RequestParam("rTutorOption") String tutorOption , @RequestParam("rAddress") String address,
						@RequestParam("rAchievements") String qualification , @RequestParam("rAbout") String about,
						@RequestParam("rHours") String bio , @RequestParam("rArea") String area, @RequestParam("rCountry") String country , @RequestParam("rProfile") String profile , 
						@RequestParam("rDOB") String dob , @RequestParam("rSurname") String surname ,@RequestParam("rModules") String hiddenModules ,
						@RequestParam("rExp") String hiddenExp, Model model) throws IOException, ParseException, java.text.ParseException 
				{
				 
				 
				 
			 if(address.equals("")) {
					 
					 address = "international";
					 
				 }
			
				 
				 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				 Date date = dateFormat.parse(dob);
				 
				 int expYear = Integer.parseInt(hiddenExp);
				 
				   BecomeTutor bTutor = becomeTutorService.findOneTutor(email);
				 
		           byte[] imageData = bTutor.getImage();
		           byte[] cv = bTutor.getCv();
		           byte[] qualiDoc = bTutor.getEducation();
		           byte[] idPass = bTutor.getIdPassport();
		              
				   Tutor tutor = new Tutor(email,name,"Yes",phone,subjects,grades,address,tutorOption,qualification,about,bio,imageData,syllabus,area,country,date,surname,hiddenModules,expYear);
				   tutor.setCv(cv);
				   tutor.setEducation(qualiDoc);
				   tutor.setIdPassport(idPass);
				   tutor.setCreatedAt(bTutor.getCreatedAt());
				   
				   
				    tutorService.save(tutor); 
				    becomeTutorService.delete(bTutor);
				    
				    String serverName = request.getServerName();
				    int serverPort = request.getServerPort();
				    String protocol = request.getScheme();
				    String host = protocol + "://" + serverName + ":" + serverPort;

			        String reviewLink = host + "/review-ratings?email=" + email;

			        // Create and save the third review
			        Review review1 = new Review();
			        review1.setTutorEmail(email);
			        review1.setName("Sihle Sithole");
			        review1.setRating(2);
			        review1.setMessage("Outstanding guidance and support! Created a comfortable learning environment, addressed all questions, and provided constructive feedback. Grateful for the expertise!");
			        review1.setStatus("approved");
			        reviewService.save(review1);

			        // Create and save the second review
			        Review review2 = new Review();
			        review2.setTutorEmail(email);
			        review2.setName("Mabhena Bongisani");
			        review2.setRating(0);
			        review2.setMessage("Great tutor! Helped me understand complex concepts, built my confidence, and provided valuable feedback. Would definitely recommend!");
			        review2.setStatus("approved");
			        reviewService.save(review2);


			        // Create and save the fourth review
			        Review review4 = new Review();
			        review4.setTutorEmail(email);
			        review4.setName("Paul Peo");
			        review4.setRating(1);
			        review4.setMessage("Fantastic experience! Effective teaching, regular progress updates, and flexible approach helped me achieve my goals. Highly recommend!");
			        review4.setStatus("approved");
			        reviewService.save(review4);

			        String sHeading = "Dear " + name;
			        
					senderService.sendSimpleEmail(email, "Tutor Registration Approval" ,
					sHeading+",\n\nCongratulations! You have been successfully added to the Apex Academic Centre website. We are confident that you will deliver exceptional service to our clients.\r\n"
							+ "\r\n"
							+ "Clients can book your services directly through our website, and we will also assign clients to you via email. To enhance your profile and increase visibility, please share the link below with your students, so they can leave reviews about your services on our website. These reviews will make your profile more attractive to potential clients and increase your chances of being recommended.\n\n Link: " + reviewLink + "\n\nThank you for joining our team, and we look forward to your success!");
				
					 /*remove tutor from the preloaded data*/
					
					 return getOnePageAdmin(1,model);
		 
				}
	    
			 /*Delete Tutor*/
			 
			 @PostMapping("/deleteTutor")
		     public String deleteTutor(@RequestParam("deleteEmail") String email, Model model ) {
				 
				 Tutor tutor = new Tutor();
				    tutor.setEmail(email);
				      tutorService.delete(tutor);
				 	 
				      return getOnePageAdmin(1,model);
				 
			 }
			 
			 
			 /*unsuccessfully application*/
			 
			 
			 @PostMapping("/deleteApplicant")
		     public String deleteApplicant(@RequestParam("emailApplicant") String email , Model model ) {
				 
				  
				 Optional<BecomeTutor> opT = becomeTutorRepo.findById(email);
				 BecomeTutor pTutor = new BecomeTutor();
				 
			        if (opT.isPresent()){
						 
			        	pTutor = opT.get();
			
					 }
			        
			        String sHeading = "Dear " + pTutor.getName() + " " + pTutor.getSurname();
			        
			        /*Send unsuccessful email to the applicant*/
					   
						senderService.sendSimpleEmail(email, "Update on Your Tutor Application" ,
						sHeading+"\n\nThank you for your interest in joining our team of tutors at Apex Academic Centre. We appreciate the time you took to complete our Become a Tutor form.\n"
								+ "\n"
								+ "After careful review, we regret to inform you that your current qualifications do not meet our requirements. However, we encourage you to continue developing your skills and qualifications.\n"
								+ "\n"
								+ "We invite you to reapply in the future once you have upgraded your qualifications. We appreciate your passion for education and look forward to considering your application again soon.\n"
								+ "\n"
								+ "Kind regards,\n"
								+ "\n"
								+ "Apex Academic Centre Team");

					/*Delete The Application*/
						
						   BecomeTutor tutor = new BecomeTutor();
						   tutor.setEmail(email);
						   becomeTutorService.delete(tutor);
						   
                           /*remove tutor from the preloaded data*/
					   
						   return getOnePageAdmin(1,model);
			 }
			 
			 /*unsuccessfully application*/
			 
			 /*Delete Booking*/
			 
			 @PostMapping("/deleteOther")
		     public String deleteOther(@RequestParam("deleteEmailOther") Long id , Model model) {
				 
				 Booking booking = bookingService.findOneBook(id);
				    bookingService.delete(booking);
				 	 
				 return getOnePageAdmin(1,model);
				 
			 }
			 
			 /*Delete Booking*/
			 
			 
			    /*DELETE CONSULT BOOKING*/
	    		
				 @PostMapping("/deleteConsult")
			     public String deleteConsultant(@RequestParam("deleteEmailConsult") Long id, Model model ) {
					 
					 Booking booking = bookingService.findOneBook(id);
					    bookingService.delete(booking);
					
					  return getOnePageAdmin(1,model);
					 
				 }
				 
				 
				 /*Approve Review*/
				 
				 @PostMapping("/approve-review")
				 public String acceptReview(@RequestParam("rEntryId") Long entryId , @RequestParam("rTutorEmail") String email, Model model ) {
					 
					 reviewService.updateReview(entryId);
						
					 return getOnePageAdmin(1,model);					 
				 }
				 
				 
				 /*Delete Review*/
				 
				 @PostMapping("/deleteReview")
				 public String deleteReview(@RequestParam("entryReview") Long entryId, Model model) {
					 
					 Review rev = reviewService.findOneReview(entryId);
					 reviewRepo.delete(rev);
					 
					 return getOnePageAdmin(1,model);
					 
				 }
				 
				 /*DELETE APPROVED BOOKING*/
				 
				 @PostMapping("/delete-approved-booking")
			     public String deleteApprovedBooking(@RequestParam("deleteEmailApproved") Long id, Model model) {
					 
					 Booking booking = bookingService.findOneBook(id);
					    bookingService.delete(booking);
					 	 
					  return getOnePageAdmin(1,model);
					 
				 }
				 
				 
				 /*DELETE REGISTERED MATRIC*/
				 
				 @PostMapping("/delete-registered-matric")
			     public String deleteMatricReg(@RequestParam("deleteMatricReg") Long id, Model model) {
					  
					 OnlineClass matric = onlineService.findOneBook(id);
					    onlineService.delete(matric);
					 	 
					    return getOnePageAdmin(1,model);
					 
				 }
				 
				 /*UPDATE VIEW MATRIC STATUS*/
				 
				 @PostMapping("/update-view-status")
				 @ResponseBody
				 public void updateViewStatus(@RequestBody Map<String, String> status) {
					 
					 
					 String id = status.get("status");
					 Long parsId = Long.valueOf(id);
					 
					 onlineService.update(parsId);
					 
					 
				 }
				 
				 
				 /*UPDATE VIEW MATHS STATUS*/
				 
				 @PostMapping("/mark-as-viewed")
				 @ResponseBody
				 public void updateMathsView(@RequestBody Map<String, String> status) {
					 
					 
					 String id = status.get("status");
					 Long parsId = Long.valueOf(id);
					 
					 mathsService.update(parsId);
					 
					 
				 }
    

}
