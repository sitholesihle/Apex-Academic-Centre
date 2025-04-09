package com.example.demo.controller;


import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.model.BecomeTutor;
import com.example.demo.model.Booking;
import com.example.demo.model.MathsClass;
import com.example.demo.model.OnlineClass;
import com.example.demo.model.PaidBookings;
import com.example.demo.model.Review;
import com.example.demo.model.Tutor;

import com.example.demo.repository.BecomeTutorRepository;

import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.TutorRepository;
import com.example.demo.service.AdminService;
import com.example.demo.service.BecomeTutorService;
import com.example.demo.service.BookingService;
import com.example.demo.service.EmailSenderService;
import com.example.demo.service.MathsClassService;
import com.example.demo.service.OnlineClassService;
import com.example.demo.service.PaidBookingsService;
import com.example.demo.service.ReviewService;
import com.example.demo.service.TutorService;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpSession;

@Controller
public class PgController {
	

	@Autowired
	private EmailSenderService senderService;
	
    @Autowired         
    private HttpServletRequest request;
	
	 @Autowired
    private TutorService tutorService;
	 
	 @Autowired
	 private BookingService bookingService;
	 
	 @Autowired
	 private ReviewService reviewService;
	 
	 @Autowired
	 private AdminService adminService;
	 
	 @Autowired
	 private OnlineClassService onlineService;
	 
	 @Autowired
	 private MathsClassService mathsService;
	 
	@Autowired
	 private BecomeTutorService becomeTutorService;
	 
	 @Autowired
	 private TutorRepository tutorRepo;
	 
	 @Autowired
	 private BecomeTutorRepository becomeTutorRepo;

	 @Autowired
	 private ReviewRepository reviewRepo;

	 @Autowired
	 private PaidBookingsService paidService;
	 
	 /*Index Page*/
	 
	 @Cacheable("pageCache")
	 @GetMapping("/")
	 public String showTutors(Model model) {
	   int currentPage = 1;
	    return getOnePage(model,currentPage);
	 }
	 
	 @Cacheable("pageCache")
	 @GetMapping("/tutors-{location}")
	 public String tutorsList(Model model, @PathVariable int location) {
		 
		 return getOnePage(model,location);
	 }

	 
	 public String getOnePage(Model model, int currentPage) {
	     // Retrieve the paginated list of tutors
	   //  Page<Tutor> page = tutorService.findPage(currentPage);
		
		 List<Tutor> listTutors = tutorService.getAllTutors();

		 Page<Tutor> page = tutorService.findPage(listTutors,currentPage,10);
		 
	     int totalPages = page.getTotalPages();
	     long totalItems = page.getTotalElements();
	     
		 long pageStart = Math.max(currentPage - 2, 1); // 
		 long pageEnd = Math.min(currentPage + 3, totalPages); 
	     
	     List<Tutor> countries = page.getContent(); 

	     model.addAttribute("user", countries);
	     model.addAttribute("tutors", countries);
	     
	     model.addAttribute("totalPages", totalPages);
	     model.addAttribute("totalItems", totalItems);
	     
	     model.addAttribute("pageStart", pageStart);
	     model.addAttribute("pageEnd", pageEnd);
	     
	     model.addAttribute("currentPage", currentPage);
	     
	     // Return the ModelAndView object
	     return "index";
	 }
	 
	 /*Index Page*/
	 
	 /*TUTORS BY LOCATIONS*/
	 
	 @Cacheable("pageCache")
	 @GetMapping("/tutors-in-{location}")
	 public String byLocationList(Model model, @PathVariable String location,@RequestParam(value = "page", defaultValue = "1") int currentPage) {
	
		 return byLocation(model, location, currentPage);
	 }
	 
	 @Cacheable("pageCache")
     @GetMapping("/tutor-in-{location}-{currentPage}")
	 public String byLocationListNext(Model model, @PathVariable String location, @PathVariable int currentPage ) {
		 
    	 System.out.println("Page Number : " + currentPage);
		 return byLocation(model, location, currentPage);
		 
	 } 
	 
	 @GetMapping("/tutors-in-place")
	 public String byLocation(Model model, String location, int currentPage) {
		 
		   System.out.println(location);
	     
	        String search = "l"+location;
	      
	        String[] v = search.split("_");
	        
	        System.out.println("Search By Location");

		     // Check if there are at least two elements in the array
	        
		     if (v.length > 1) {
		    	
		         if (v[0].equalsIgnoreCase("lCape")) {
		        	 System.out.println("We inside");
		             search = "lCape Town";
		             location = "Cape Town";
		         } else if (v[0].equalsIgnoreCase("lKempton")) {
		             search = "lKempton Park";
		             location = "Kempton Park";
		         } 
		         
		         else if (v[0].equalsIgnoreCase("lCosmo")) {
		             search = "lCosmo City";
		             location = "Cosmo City";
		         } 
		         
		         else if (v[0].equalsIgnoreCase("lCentury")) {
		             search = "lCentury City";
		             location = "Century City";
		         } 
		         
		         else if (v[0].equalsIgnoreCase("lBlue")) {
		             search = "lBlue downs";
		             location = "Blue downs";
		         } 
		         
		         else if (v[0].equalsIgnoreCase("lMichells")) {
		             search = "lMichells Plain";
		             location = "Michells Plain";
		         } 
		         
		         else if (v[0].equalsIgnoreCase("lNewlands")) {
		             search = "lNewlands East";
		             location = "Newlands East";
		         } 

		     } 
		       
	        // Get all tutors
	        List<Tutor> tutors = tutorService.getAllTutors();
	         
	        Page<Tutor> page = tutorService.paginateTutorsByLocation(tutors, location, currentPage);

	        System.out.println("Tutors found");
	        
		     int totalPages = page.getTotalPages();
		     long totalItems = page.getTotalElements();
		     
			 long pageStart = Math.max(currentPage - 2, 1); // 
			 long pageEnd = Math.min(currentPage + 3, totalPages); 
		     
		     List<Tutor> countries = page.getContent(); 

		     model.addAttribute("user", countries);
		     model.addAttribute("tutors", countries);
		     
		     model.addAttribute("totalPages", totalPages);
		     model.addAttribute("totalItems", totalItems);
		     
		     model.addAttribute("pageStart", pageStart);
		     model.addAttribute("pageEnd", pageEnd);
		     
		     model.addAttribute("currentPage", currentPage);
		     model.addAttribute("location", location);
		     
		     // Return the ModelAndView object
		     return "tutorsLocation";
	        
	    }
	 
	 /*TUTORS BY LOCATIONS*/
	 
	 
	 /*TUTORS BY SUBJECTS*/
	 
	 @Cacheable("pageCache")
	 @GetMapping("/{subjectv}-tutors")
	 public String bySubjectsList(Model model, @PathVariable String subjectv,@RequestParam(value = "page", defaultValue = "1") int currentPage) {
	
		 return bySubjects(model, subjectv, currentPage);
		 
	 }
	 
	 @Cacheable("pageCache")
     @GetMapping("/{subjectv}-{currentPage}-tutor")
	 public String bySubjectsPagination(Model model, @PathVariable String subjectv, @PathVariable int currentPage ) {
		 
    	 System.out.println("Subject : " + subjectv);
    	 System.out.println("Page Number : " + currentPage);
    	 return bySubjects(model, subjectv, currentPage);
		 
	 } 
	     
     
     /*Update the function*/
	 @GetMapping("/bySubjects")
	 public String bySubjects(Model model, @PathVariable String subjectv, int currentPage) {

	    	String search = "s"+subjectv;
	    	
	    	String[] v = search.split("_");

	    	System.out.println("The Length is: " + v.length);

	    	// Check if there are enough elements in the array before accessing v[1] and v[2]
	    	if (v.length > 1) {
	    	    // Check for specific subject types
	    	    if (v[1].equalsIgnoreCase("Literacy")) {
	    	        search = "sMathematics Literacy";
	    	        subjectv = "Mathematics Literacy";
	    	    } else if (v[0].equalsIgnoreCase("sPhysical")) {
	    	        search = "sPhysical Sciences";
	    	        subjectv = "Physical Sciences";
	    	    } else if (v[0].equalsIgnoreCase("sLife")) {
	    	        search = "sLife Sciences";
	    	        subjectv = "Life Sciences";
	    	    } else if (v[0].equalsIgnoreCase("sNatural")) {
	    	        search = "sNatural Sciences";
	    	        subjectv = "Natural Sciences";
	    	    } else if (v[0].equalsIgnoreCase("sBusiness")) {
	    	        search = "sBusiness Studies";
	    	        subjectv = "Business Studies";
	    	    } else if (v[0].equalsIgnoreCase("sComputer")) {
	    	        search = "sComputer Science";
	    	        subjectv = "Computer Science";
	    	    }
	    	    
	    	    else if (v[0].equalsIgnoreCase("sAgricultural")) {
	    	        search = "sAgricultural Sciences";
	    	        subjectv = "Agricultural Sciences";
	    	    }
	    	    
	    	    else if (v[0].equalsIgnoreCase("sSocial")) {
	    	        search = "sSocial Sciences";
	    	        subjectv = "Social Sciences";
	    	    }
	    	    
	    	    else {
	    	    	
	    	        // Fallback case if none of the conditions match
	    	        search = "s"+subjectv;
	    	    }
	    	} else {
	    	    // If the split result doesn't have enough parts (e.g., no underscore), fallback
	    	    search = "s"+subjectv;  // Default to the original subject
	    	}
	    	
	    	System.out.println("we here");

	    	List<Tutor> tutors = tutorService.getAllTutors();
	    	
	        Page<Tutor> page = tutorService.paginateTutorsBySubject(tutors,subjectv, currentPage);
			
	        System.out.println("Tutors found");
	        
		     int totalPages = page.getTotalPages();
		     long totalItems = page.getTotalElements();
		     
			 long pageStart = Math.max(currentPage - 2, 1); // 
			 long pageEnd = Math.min(currentPage + 3, totalPages); 
		     
		     List<Tutor> countries = page.getContent(); 

		     model.addAttribute("user", countries);
		     model.addAttribute("tutors", countries);
		     
		     model.addAttribute("totalPages", totalPages);
		     model.addAttribute("totalItems", totalItems);
		     
		     model.addAttribute("pageStart", pageStart);
		     model.addAttribute("pageEnd", pageEnd);
		     
		     model.addAttribute("subject", subjectv);		     
		     model.addAttribute("currentPage", currentPage);
		     
		     // Return the ModelAndView object
		     return "tutorsSubject";
	        
	        
	    }
	 
	 /*TUTORS BY SYLLABUS*/
	 
     /*TUTORS BY SYLLABUS*/

	 @Cacheable("pageCache")	 
	 @GetMapping("/syllabus-{syllabus}")
	 public String bySyllabusList(Model model, @PathVariable String syllabus,@RequestParam(value = "page", defaultValue = "1") int currentPage) {
	
		 return bySyllabus(model, syllabus, currentPage);
	 }
	 @Cacheable("pageCache")
     @GetMapping("/tutor-syllabus-{syllabus}-{currentPage}")
	 public String bySyllabusPagination(Model model, @PathVariable String syllabus, @PathVariable int currentPage ) {
		 
    	 System.out.println("Subject : " + syllabus);
    	 System.out.println("Page Number : " + currentPage);
    	 return bySyllabus(model, syllabus, currentPage);
		 
	 } 
	   
	 
	 @GetMapping("/bySyllabus")
	 @ResponseBody
	 public String bySyllabus(Model model, String location, int currentPage) {
	    
	        String search = "c"+location;
	      
	        String[] v = search.split("_");
	        
	        System.out.println(v.length);

		     // Check if there are at least two elements in the array
	        
		     if (v.length > 1) {
		    	
		         if (v[0].equalsIgnoreCase("cPearson")) {
		        	 System.out.println("We inside");
		             search = "cPearson Edexcel";
		             location = "Pearson Edexcel";
		         }
		        

		     } 
	        
	        System.out.println("Search Input: " + search); // For debugging

	        // Split the search string by commas
	        String[] searchParams = search.split(",");

	        // Create an array to hold the filters
	        String[] filters = new String[4];

	        // Parse the search parameters
	        for (String param : searchParams) {
	            if (param.startsWith("l")) {
	                filters[0] = param.substring(1).trim(); // Set location
	            } else if (param.startsWith("s")) {
	                filters[1] = param.substring(1).trim(); // Set subject
	            } else if (param.startsWith("c")) {
	                filters[2] = param.substring(1).trim(); // Set curriculum
	            } else if (param.startsWith("t")) {
	                filters[3] = param.substring(1).trim(); // Set tutoring option
	            }
	        }

	        // Get all tutors
	        List<Tutor> tutors = tutorService.getAllTutors();

	        // Filter the tutors based on the provided attributes
	        List<Tutor> filteredTutors = tutors.stream()
	            .filter(tutor -> {
	                boolean matches = true;

	                // Check location
	                if (filters[0] != null) {
	                    String[] areas = tutor.getArea().split(",\\s*");
	                    String[] countries = tutor.getCountry().split(",\\s*");
	                    String address = tutor.getAddress(); // Get the tutor's address

	                    matches = Arrays.stream(areas)
	                        .anyMatch(area -> area.equalsIgnoreCase(filters[0])) || 
	                        Arrays.stream(countries)
	                        .anyMatch(country -> country.equalsIgnoreCase(filters[0])) ||
	                        address.equalsIgnoreCase(filters[0]); // Check if the address matches the filter
	                }

	                // Check subjects
	                if (matches && filters[1] != null) {
	                    String[] subjects = tutor.getSubjects().split(",\\s*");
	                    matches = Arrays.stream(subjects)
	                        .anyMatch(subject -> subject.equalsIgnoreCase(filters[1]));
	                }

	                // Check syllabus
	                if (matches && filters[2] != null) {
	                    String[] syllabuses = tutor.getSyllabus().split(",\\s*");
	                    matches = Arrays.stream(syllabuses)
	                        .anyMatch(syllabus -> syllabus.equalsIgnoreCase(filters[2]));
	                }

	             // Check availability
	                if (matches && filters[3] != null) {
	                    String[] availabilities = tutor.getAvailability().split("/");

	                    // Trim each availability to remove leading/trailing whitespace
	                    availabilities = Arrays.stream(availabilities)
	                        .map(String::trim) // Trim each element
	                        .toArray(String[]::new); // Collect back to an array

	                    // Check if any availability matches the filter (case insensitive)
	                    matches = Arrays.stream(availabilities)
	                        .anyMatch(availability -> availability.equalsIgnoreCase(filters[3]));
	                }
	                return matches;
	            })
	            .collect(Collectors.toList());
	        
	        String description = "";
	        String title = "Expert " + location + " Tutors | Personalized Online and In-Person Lessons";
	        
	        if(location.equals("IEB")) {
	        	
	        	title = "Expert " + location + " Tutors | Personalized Online and In-Person Lessons";
	        	description = "Unlock academic success with Apex Academic Centre's expert IEB tutors. Get personalized online and in-person lessons tailored to your needs.";
	        }
	        
	        else {
	        	
	        	if(location.equals("CAPS")) {
		        	
	        		title = "CAPS Tutors | Expert Online and In-Person Lessons for South African Students";
		        	description = "CAPS Tutors | Expert Online and In-Person Lessons for South African Students";
		        }
	        	
	        	else {
	        		
	        	 	if(location.equals("Cambridge")) {
			        	
	        	 		title = "Cambridge Tutors | Expert Online and In-Person Tutoring Services";
			        	description = "Discover the benefits of personalized Cambridge tutoring with Apex Academic Centre. Expert online and in-person lessons for academic success.";
			        	
	        	 	}
	        	 	
	        	 	else {
	        	 		
	        	 		title = "Pearson Edexcel Tutors | Expert Online and In-Person Tutoring Services";
	        	 		description = "Unlock your potential with Apex Academic Centre's expert Pearson Edexcel tutors. Personalized online and in-person lessons for academic success.";
	        	 	}
		        	
	        		
	        	}
	        }
	       
	        Page<Tutor> page = tutorService.paginateTutorsBySyllabus(filteredTutors, currentPage);
		     
		        System.out.println("Tutors found");
		        
			     int totalPages = page.getTotalPages();
			     long totalItems = page.getTotalElements();
			     
				 long pageStart = Math.max(currentPage - 2, 1); // 
				 long pageEnd = Math.min(currentPage + 3, totalPages); 
			     
			     List<Tutor> countries = page.getContent(); 

			     model.addAttribute("title", title);
			     model.addAttribute("description", description);
			     
			     model.addAttribute("user", countries);
			     model.addAttribute("tutors", countries);
			     
			     model.addAttribute("totalPages", totalPages);
			     model.addAttribute("totalItems", totalItems);
			     
			     model.addAttribute("pageStart", pageStart);
			     model.addAttribute("pageEnd", pageEnd);
			     
			     model.addAttribute("syllabus", location);	
			     model.addAttribute("currentPage", currentPage);
			     
			     // Return the ModelAndView object
			     return "tutorsSyllabus";
	        

	    }
	 
	 /*TUTORS BY SYLLABUS*/
	 
	 
	    /*RENDER THE BOOKING PAGE*/
	    
		 @GetMapping("/bookingTutor")
		 public String bookingForm() {
			 
				return "bookings";
			 
		 }
		 
		 /*RENDER THE BOOKING PAGE*/
		 
		    /*RENDER THE BOOKING PAGE*/
		    
			 @GetMapping("/bookingTutor/{tutorId}")
			 public String bookingFormTutor(@PathVariable String tutorId, Model model) {
				 
			    	List<Tutor> tutors = tutorService.getAllTutors();
			    	String email ="", name = "", surname ="";
			    	Tutor tutorBook = new Tutor();
			    	
			        for (Tutor tutor : tutors) {
			            // If a tutor's email matches the search email, return that tutor
			            if (tutor.getEmail().equalsIgnoreCase(tutorId)) {
			            	
			            	email = tutor.getEmail();
			            	name = tutor.getFullNames();
			            	surname = tutor.getSurname();
			            	tutorBook = tutor;
			               
			            }
			        }
			        
			        model.addAttribute("email", email);	
			        model.addAttribute("name", name);	
			        model.addAttribute("surname", surname);
			        model.addAttribute("tutor", tutorBook);	
			        
				 
					return "bookingTutor";
				 
			 }
			 
			 /*RENDER THE BOOKING PAGE*/

	 /*TUTORS PROFILE*/
	 
	 
	    @GetMapping("/view-profile")
	    public String getTry(@RequestParam("email") String email, Model model) {
	    	
	    	Tutor tutorView = new Tutor();
	    	List<Tutor> tutors = tutorService.getAllTutors();
	    	
	    	List<Review> review = new ArrayList<>();
	    	List<Review> reviews = reviewService.loadedReviews();
	    	
	        for (Tutor tutor : tutors) {
	            // If a tutor's email matches the search email, return that tutor
	            if (tutor.getEmail().equalsIgnoreCase(email)) {
	            	
	            	tutorView = tutor;
	               
	            }
	        }
	        
	        for (Review rev : reviews) {
	            // If a tutor's email matches the search email, return that tutor
	            if (rev.getTutorEmail().equalsIgnoreCase(email)) {
	            	
	            	review.add(rev);
	               
	            }
	        }

		     List<Tutor> tutorsList = tutorService.viewProfile(tutors);
		     
		     Date currentDate = new Date();
	            SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
	            String formattedDate = formatter.format(currentDate);
	            int currentYear = Integer.parseInt(formattedDate); 

	            Date dob = tutorView.getDob();
	            formattedDate = formatter.format(dob);
	            int birthYear = Integer.parseInt(formattedDate);

	            // Calculate age
	            int age = currentYear - birthYear;
	            
	            // Optional: Adjust if the birthday hasn't occurred yet this year
	            Calendar today = Calendar.getInstance();
	            Calendar birthDate = Calendar.getInstance();
	            birthDate.setTime(dob);
	            
	            if (today.get(Calendar.MONTH) < birthDate.get(Calendar.MONTH) || 
	                (today.get(Calendar.MONTH) == birthDate.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < birthDate.get(Calendar.DAY_OF_MONTH))) {
	                age--; // Decrease age if birthday hasn't occurred yet this year
	            }
  
		     model.addAttribute("tutor", tutorView);
		     model.addAttribute("tutors", tutorsList);
		     model.addAttribute("age", age);
		     
		     model.addAttribute("reviews", review);
	        
	        return "profile";   
	        
	    }
	    
	    public String simpleDecrypt(String encryptedText) {
	        int shift = 3; // This should match the shift used in encryption
	        StringBuilder decrypted = new StringBuilder();
	        
	        for (int i = 0; i < encryptedText.length(); i++) {
	            char ch = encryptedText.charAt(i);
	          
	            if (ch >= 'A' && ch <= 'Z') {
	                ch = (char) (((ch - 'A' - shift + 26) % 26) + 'A');
	            }
	         
	            else if (ch >= 'a' && ch <= 'z') {
	                ch = (char) (((ch - 'a' - shift + 26) % 26) + 'a');
	            }
	            // Shift numbers
	            else if (ch >= '0' && ch <= '9') {
	                ch = (char) (((ch - '0' - shift + 10) % 10) + '0');
	            }
	            
	            decrypted.append(ch);
	        }
	        return decrypted.toString();
	    }
	 
	 
	 /*TUTORS PROFILE*/
	    
	    /*TUTOR BOOKINNGS*/
	    
		 @PostMapping("/booking")
		    public String placeBooking(@RequestBody Map<String, String> booking, HttpSession session, Model model) {
		        
			             /*Amount*/
		        String amount = booking.get("amount");
		        
		        System.out.println("The Amount Selected Is : " + amount);
		        
		                 /**first-form**/
			    String name = booking.get("first-name");
			    String surname = booking.get("last-name");
			    String email = booking.get("email");
			    String phone = booking.get("phone");
			    String province = booking.get("province");
			    String country = booking.get("country");
			    String instrLangauge = booking.get("language");
			    
			            /*second form*/
			    String tutorFor = booking.get("tutoring-for");
			    String helpWith = booking.get("help-with");

			 
		                  /*school*/
			    
			    String learnerName = "N/A";
			    String learnerSurname = "N/A";
			    String grade = "N/A";
			    String syllabus = "N/A";
			    
			            /*university*/
			    
			    String studentName = "N/A";
			    String studentSurname = "N/A";
			    String year ="N/A";
			    String postGrad = "N/A";
			    
		         String qualification = "N/A";
		         String module = "N/A";
		         String specification = "N/A";
		         
			    if(helpWith.equals("school")) {
			    	
			    	 learnerName = booking.get("student-name");
			    	 learnerSurname = booking.get("student-last-name");
			    	 grade = booking.get("grade");
			    	 
				       if(!grade.equals("N/A")){
				    	   
				    	   grade = booking.get("inter-grade");
				       }
				       
			    	 syllabus = booking.get("syllabus");
			    }
			    
			    else {
			    	
				    studentName = booking.get("stud-name");
				    studentSurname = booking.get("stud-last");
				    year = booking.get("year");
				    postGrad = booking.get("postgrad-type");
			        qualification = booking.get("qualification-id");
			        module = booking.get("module-id");
			        specification = booking.get("specification-id");
			    	
			    }
			    

			    /*third form*/
			    
			    String subject =  booking.get("subject");
			    String onlineInperson =  booking.get("tutor-style");
			    String suburb =  "N/A";
			    String toStart = "N/A";
			    
			    if(!onlineInperson.equals("Online")) {
			    	
				    suburb =  booking.get("suburb-inperson");
				    toStart =  booking.get("address-inperson");
			    	
			    }
	 
			    /*fourth form*/
			    String message = booking.get("message");
			    
			    if(message.equals("")) {
			    	
			    	message = "nothing...";
			    
			    }
			    
			    String secTutor = booking.get("tutor-option");
			    String tutorName = booking.get("hiddenTutorName");
			    String tutorEmail = booking.get("hiddenTutorEmail");
			    
			    System.out.println("tutor name :: " + tutorName);
			    System.out.println("tutor surname :: " +tutorEmail);
			    
			    if(tutorName.equals("undefined")) {
			    	
			    	  tutorName = "N/A";
			    	  tutorEmail = "N/A";
	 
			    }
			    
			    if(tutorName.equals("")) {
			    	
			    	  tutorName = "Apex Tutor";
			    	  tutorEmail = "info@apexacademiccentre.co.za";
			    	  	 
			    }
			    
			    String packageType = booking.get("package");
			    String sessions = booking.get("sessions");
			    
		        // Create or update session attributes
		        session.setAttribute("bookings", booking);
		        session.setAttribute("amount", amount);
			    session.setAttribute("name", name);
			    session.setAttribute("surname", surname);
			    session.setAttribute("email", email);
			    session.setAttribute("phone", phone);
			    session.setAttribute("province", province);
			    session.setAttribute("country", country);
			    session.setAttribute("instrLangauge", instrLangauge);
			    session.setAttribute("tutorFor", tutorFor);
			    session.setAttribute("helpWith", helpWith);
			    session.setAttribute("learnerName", learnerName);
			    session.setAttribute("learnerSurname", learnerSurname);
			    session.setAttribute("grade", grade);
			    session.setAttribute("syllabus", syllabus);
			    session.setAttribute("studentName", studentName);
			    session.setAttribute("studentSurname", studentSurname);
			    session.setAttribute("year", year);
			    session.setAttribute("postGrad", postGrad);
			    session.setAttribute("subject", subject);
			    session.setAttribute("onlineInperson", onlineInperson);
			    session.setAttribute("suburb", suburb);
			    session.setAttribute("toStart", toStart);
			    session.setAttribute("secTutor", secTutor);
			    session.setAttribute("tutorName", tutorName);
			    session.setAttribute("tutorEmail", tutorEmail);
			    session.setAttribute("packageType", packageType);
			    session.setAttribute("message", message);
			    session.setAttribute("sessions", sessions);
			    session.setAttribute("qualification", qualification);
			    session.setAttribute("module", module);
			    session.setAttribute("specification", specification);
   		        
		        return "payment";
		    }
		 
		 /*TUTOR BOOKINNGS*/
	 

		 /*MATRIC REWRITE*/
		 
		 @GetMapping("/matric-rewrite")
		 public String onlineClasses() {
			 
			   return "matricRewrite";
		 }
		 
		 @PostMapping("/online-registration")
		 @ResponseBody
		    public void onlineRegistration(@RequestBody Map<String, String> registration, HttpSession session) {
		        
			                 /*first form*/
		        String name = registration.get("firstName");
		        String surname = registration.get("lastName");
		        String email = registration.get("email");
		        String mobile = registration.get("mobile");
		        String idPassport = registration.get("idPassport");
		        String gender = registration.get("gender");
		     
		        
		                     /*Second form*/
				String province = registration.get("province");
				String date = registration.get("writingDate");
				String subjects = registration.get("chooseSubjects");
				String days = registration.get("availableDays");
				
				
				
				              /*Third form*/
				String message = registration.get("message");
				String status = registration.get("status");
				
				String clientName = name + " " + surname;
				
				
		        // Create or update session attributes
				
				if(status.equals("pending")) {
					
		        session.setAttribute("cName", name);
		        session.setAttribute("cSurname", surname);
		        session.setAttribute("cEmail", email);
		        session.setAttribute("cMobile", mobile);
		        session.setAttribute("cIdPassport", idPassport);
		        session.setAttribute("cGender", gender);
		        session.setAttribute("cProvince", province);
		        session.setAttribute("cDate", date);
		        session.setAttribute("cSubjects", subjects);
		        session.setAttribute("cDays", days);
		        session.setAttribute("cMessage", message);
		        session.setAttribute("cStatus", status);
		        
				}
				
				else {
						            
		            OnlineClass onlineClass = new OnlineClass(name, surname, email, mobile, gender, idPassport ,
		        	 province, date, subjects , days, message,  status,
		        			"0");
		            
		            
	            	senderService.sendSimpleEmail("matricrewritereg@apexacademiccentre.co.za", 
		            	    "Class Registration - " + clientName, 
		            	    "Dear Apex Academic Centre Team,\r\n" +
		            	    "\r\n" +
		            	    "We have received an inquiry from " + clientName + ", who has filled out our class registration form.\r\n" +
		            	    "\r\n" +
		            	    "Please review the details of their request on the Admin panel \r\n" +
		            	    "\r\n" +
		            	    "We kindly request that you contact " + clientName + " to schedule a consultation.\r\n" +
		            	    "\r\n" +
		            	    "Thank you for your prompt attention.\r\n" +
		            	    "\r\n" +
		            	    "Best regards,\r\n" +
		            	    "Admin"
		            	);
	            	
	            	senderService.sendSimpleEmail(email, 
		            	    "Next Steps - Matric Rewrite Online Class Consultation", 
		            	    "Dear " + clientName + " ,\r\n\n"
		            	    + "\r\n"
		            	    + "Thank you for expressing interest in our Matric Rewrite online class for the following subjects:\r\n"
		            	    + "\r\n"
		            	    + ""+ subjects + "\r\n"
		            	    + "\r\n"
		            	    + "We appreciate your trust in Apex Academic Centre. A dedicated consultant will contact you shortly to discuss your requirements, answer questions, and tailor a plan to ensure your success.\r\n"
		            	    + "\r\n"
		            	    + "Important Note:\r\n"
		            	    + "\r\n"
		            	    + "Apex Academic Centre is a tutoring institution focused on preparing students for the May-June and November 2025 exams. Please note that we are not an examination center.\r\n"
		            	    + "\r\n"
		            	    + "Our consultant will arrange a convenient time to speak with you. If you have any urgent questions, please don't hesitate to contact us.\r\n"
		            	    + "\r\n"
		            	    + "Thank you for considering Apex Academic Centre.\r\n"
		            	    + "\r\n"
		            	    + "Kind regards,\r\n"
		            	    + "\r\n"
		            	    + "Apex Academic Centre"
		            	);
		            
		            onlineService.save(onlineClass);
			
				}

				
		 }
		 
		 
		 
		    @GetMapping("/success_class_registration")
		    public String onlineClassSuccess(@RequestParam Map<String, String> paymentDetails , HttpSession session, Model model) {
		        // Extract necessary details from paymentDetails

	          
		    	String amount = paymentDetails.get("amount");
		    	
		        String name = (String)session.getAttribute("cName");
		        String surname = (String) session.getAttribute("cSurname");
		        String email = (String)session.getAttribute("cEmail");
		        String phone = (String) session.getAttribute("cMobile");
		        String idPassport = (String) session.getAttribute("cIdPassport");
		        String gender = (String)session.getAttribute("cGender");
		        String province = (String)session.getAttribute("cProvince");
		        String date = (String)session.getAttribute("cDate");
		        String subjects = (String) session.getAttribute("cSubjects");
		        String days = (String)session.getAttribute("cDays");
		        String message = (String) session.getAttribute("cMessage");
		      
		        
	            OnlineClass onlineClass = new OnlineClass(name, surname, email, phone, gender, idPassport ,
	        	 province, date, subjects , days, message,  "paid",
	        			amount);
	            
	            onlineService.save(onlineClass);
	            
	            String clientName = name + " " + surname;

	            /*Email to Apex*/

	            /*Email to the client*/
	            
            	senderService.sendSimpleEmail(email, 
	            	    "Confirmation: Matric Rewrite Online Class Registration" , 
	            	    "Dear " +  clientName + ",\r\n"
	            	    + "\r\n"
	            	    + "Congratulations on taking the first step toward achieving academic success! We confirm receipt of your registration for our Matric Rewrite online class for the following subjects:\r\n"
	            	    + "\r\n"
	            	    + subjects +"\r\n"
	            	    + "\r\n"
	            	    + "Next Steps:\r\n"
	            	    + "\r\n"
	            	    + "- We will add you to our dedicated Matric Rewrite WhatsApp group, where you'll receive:\r\n"
	            	    + "\r\n"
	            	    + "    - Timely updates\r\n"
	            	    + "    - Study materials\r\n"
	            	    + "    - Engagement with peers and tutors\r\n"
	            	    + "\r\n"
	            	    + "Important Note:\r\n"
	            	    + "\r\n"
	            	    + "Apex Academic Centre is a tutoring institution focused on preparing you for the May/June and November 2025 exams. Please note that we are not an examination center.\r\n"
	            	    + "\r\n"
	            	    + "Our team of experienced tutors is committed to guiding you through this journey. If you have any questions or concerns, please don't hesitate to reach out.\r\n"
	            	    + "\r\n"
	            	    + "Thank you for entrusting us with your academic goals.\r\n"
	            	    + "\r\n"
	            	    + "Kind regards,\r\n"
	            	    + "\r\n"
	            	    + "Apex Academic Centre\r\n"
	            	    + "\r\n"
	            	    + "info@apexacademiccentre.co.za \r\n"
	            	    + "Call 0113540198\r\n"
	            	    + "Whatsapp 068 035 1845"
	            	);
            	
                senderService.sendSimpleEmail("matricrewritereg@apexacademiccentre.co.za",
		                "Matric Re-Write Payment Received - " + clientName,
		                "Dear Apex Academic Centre Team,\r\n\r\nWe have received a payment from " + clientName + ", who has booked the matric re-write. Please review the details in the Admin panel.\r\n\r\nThank you for your prompt attention.\r\n\nBest regards,\r\nAdmin");

			     model.addAttribute("name", name);
			     model.addAttribute("email", email);
			        
		        return "success"; // Name of the success HTML page (success.html)
		        
		    }
		 
		 /*MATRIC REWRITE*/
		 
		 /*MATHS CLASSES*/
		 
			@GetMapping("/maths-class")
	       public String mathsOnlineClasses() {

				return "mathsClasses";
			
			}
			

			 @PostMapping("/maths-registration")
			 @ResponseBody
			 public void mathsClasses(@RequestBody Map<String, String> classes, HttpSession session) {
		
				  // FORM1
			        String parentName = classes.get("parentFirstName");
			        String parentSurname = classes.get("parentLastName");
			        String parentEmail = classes.get("parentEmail");
			        String parentMobile = classes.get("parentPhone");

			        // FORM2
			        String studName = classes.get("studentFirstName");
			        String studSurname = classes.get("studentLastName");
			        String grade = classes.get("grade");
			        String subject = classes.get("subject");
			        String curriculum = classes.get("curriculum");
			        String marks = classes.get("marks");

			        // FORM3
			        String opt = classes.get("supportOptions");
			        String message = classes.get("message");
			        String status = classes.get("status");

			        String clientName = parentName + " " + parentSurname;
				 
				 
			        // Session to hold data
			        if (status.equals("pending")) {
			        	
			            session.setAttribute("parentName", parentName);
			            session.setAttribute("parentName", parentName);
			            session.setAttribute("parentEmail", parentEmail);
			            session.setAttribute("parentMobile", parentMobile);
			            
			            session.setAttribute("studName", studName);
			            session.setAttribute("studSurname", studSurname);
			            session.setAttribute("grade", grade);
			            session.setAttribute("subject", subject);
			            session.setAttribute("curriculum", curriculum);
			            session.setAttribute("marks", marks);
			            
			            session.setAttribute("opt", opt);
			            session.setAttribute("message", message);
			            session.setAttribute("status", status);
			            
			        }
			         else
			        {
			            // Send emails
			            senderService.sendSimpleEmail(parentEmail, "Joining Apex Academic Centre Math Online Class",
			                "Dear " + clientName + ",\r\n\nThank you for considering Apex Academic Centre for your child's online education. We appreciate your interest in our programs.\r\n"
			                		+ "\r\n"
			                		+ "A dedicated consultant will contact you shortly to discuss:\r\n"
			                		+ "\r\n"
			                		+ "Registration Details\r\n"
			                		+ "- Your child's academic needs\r\n"
			                		+ "- Suitable programs and schedules\r\n"
			                		+ "- Any questions or concerns\r\n"
			                		+ "\r\n"
			                		+ "What to Expect\r\n"
			                		+ "- Personalized guidance\r\n"
			                		+ "- Tailored learning plans\r\n"
			                		+ "- Expert tutors\r\n"
			                		+ "\r\n"
			                		+ "Consultation Timing\r\n"
			                		+ "We will contact you within 12 hours.\r\n"
			                		+ "\r\n"
			                		+ "Contact Information\r\n"
			                		+ "Email: info@apexacademiccentre.co.za\r\n"
			                		+ "Phone: 011 354 0198\r\n"
			                		+ "WhatsApp: 068 035 1845\r\n"
			                		+ "\r\n"
			                		+ "Thank you for choosing Apex Academic Centre.\r\n"
			                		+ "\r\n"
			                		+ "Best regards,\r\n"
			                		+ "\r\n"
			                		+ "Apex Academic Centre");
			            
			  

			            MathsClass maths = new MathsClass(parentName, parentSurname, parentEmail, parentMobile, grade,
			                studName, studSurname, subject, curriculum, marks, opt, message, status, "0"); 

			            senderService.sendSimpleEmail("registration@apexacademiccentre.co.za",
			                "Maths Registration Class- " + clientName,
			                "Dear Apex Academic Centre Team,\r\n\r\nWe have received an inquiry from " + clientName + ", who has filled out our class registration form. Please review the details in the Admin panel.\r\n\r\nThank you for your prompt attention.\r\n\nBest regards,\r\nAdmin");

			            mathsService.save(maths);
			            
			        }

				 
			 }
			 
			 @GetMapping("/success_maths_registration")
			    public String mathsClassSuccess(@RequestParam Map<String, String> ParentspaymentDetails, HttpSession session, Model model) {
			        String amount = ParentspaymentDetails.get("amount");

			        String parentName = (String) session.getAttribute("parentName");
			        String parentSurname = (String) session.getAttribute("parentSurname");
			        String parentEmail = (String) session.getAttribute("parentEmail");
			        String parentMobile = (String) session.getAttribute("parentMobile");

			        String studName = (String) session.getAttribute("studName");
			        String studSurname = (String) session.getAttribute("studSurname");
			        String grade = (String) session.getAttribute("grade");
			        String subject = (String) session.getAttribute("subject");
			        String curriculum = (String) session.getAttribute("curriculum");
			        String marks = (String) session.getAttribute("marks");
			        
			        String opt = (String) session.getAttribute("opt");
			        String message = (String) session.getAttribute("message");
			       
			        
			        String clientName = parentName + " " + parentSurname;
			        
		            MathsClass maths = new MathsClass(parentName, parentSurname, parentEmail, parentMobile, grade,
			                studName, studSurname, subject, curriculum, marks, opt, message, "paid", amount); 

			        mathsService.save(maths);

		            senderService.sendSimpleEmail(parentEmail,
			                "Confirmation: Online Math Class Registration",
			                "Dear " + clientName + ",\r\n"
			                + "\r\n"
			                + "Thank you for entrusting your child's math education to Apex Academic Centre. We confirm receipt of your registration for our online math class.\r\n"
			                + "\r\n"
			                + "Registration Details\r\n"
			                + "\r\n"
			                + "- Student Name: " + studName + " " + studSurname + " \r\n"
			                + "- Grade Level: " +  grade + " \r\n"
			                + "- Subjects: " + subject + " \r\n"
			                + "\r\n"
			                + "Next Steps\r\n"
			                + "\r\n"
			                + "1. Our consultants will contact you shortly to discuss lesson plans and expectations.\r\n"
			                + "2. You'll receive access to our intuitive online learning platform.\r\n"
			                + "\r\n"
			                + "Support\r\n"
			                + "\r\n"
			                + "For any questions or concerns:\r\n"
			                + "\r\n"
			                + "- Email: info@apexacademiccentre.co.za\r\n"
			                + "- Phone: 011 354 0198\r\n"
			                + "- WhatsApp: 068 035 1845\r\n"
			                + "\r\n"
			                + "Thank you for choosing Apex Academic Centre.\r\n"
			                + "\r\n"
			                + "Best regards,\r\n"
			                + "\r\n"
			                + "Apex Academic Centre");
		            
		            senderService.sendSimpleEmail("registration@apexacademiccentre.co.za",
			                "Maths Registration Class Payment Received - " + clientName,
			                "Dear Apex Academic Centre Team,\r\n\r\nWe have received a payment from " + clientName + ", who has filled booked the maths online class. Please review the details in the Admin panel.\r\n\r\nThank you for your prompt attention.\r\n\nBest regards,\r\nAdmin");


			        // Create ModelAndView for success page
			        ModelAndView data = new ModelAndView("success.jsp");
			        data.addObject("name", parentName);
			        data.addObject("email", parentEmail);
			        
				     model.addAttribute("name", parentName);
				     model.addAttribute("email", parentEmail);
				        
			        return "success";
			        
			    }
			 
	     /*MATHS CLASSES*/
			
		 /*BECOME TUTOR*/
			 
			 @GetMapping("/become-tutor")
		       public String becomeTutorApplication() {

					return "becomeTutor";
				
				}

			 
			    @PostMapping("/becomeTutor-applications")
			    @ResponseBody
			    public void becomeTutorRegistration(
			            @RequestParam Map<String, String> tutorRegister,
			            @RequestParam("cv") MultipartFile cvFile,
			            @RequestParam("dQual") MultipartFile qualificationsFiles,
			            @RequestParam("ids") MultipartFile idPassportFile,
			            @RequestParam("image") MultipartFile imageFile,
			            HttpSession session) throws ParseException, IOException{

			        // Extract basic info
			        String name = tutorRegister.get("firstName");
			        String surname = tutorRegister.get("lastName");
			        String email = tutorRegister.get("email");
			        String country = tutorRegister.get("country");
			        String dobString = tutorRegister.get("dob");
			        Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(dobString);
			        
			        int experience = 2024 - Integer.parseInt(tutorRegister.get("experience"));
			       
			        
			        String phone = tutorRegister.get("phone");
			        String area = tutorRegister.get("area");
			        String address = tutorRegister.get("address");
			        
			        String subjects = tutorRegister.get("subjects");
		    	    String grades = tutorRegister.get("grades");
		    	    String syllabus = tutorRegister.get("syllabus");
		    	   
		    	    String bio = tutorRegister.get("bio");
		    	    String about = tutorRegister.get("about");
		    	  
		    	    String achievements = tutorRegister.get("achievements");
		    	    
		    	    String province = "n/a";
		    	    String tutorOptions = tutorRegister.get("tutorOptions");
			    	String qualification = "n/a";
			    	String specification = "n/a";
			    	String uniStud = "n/a";
		    	    
			    	if(country.equals("South Africa"))
		    	    {
		    	    	province = tutorRegister.get("province");
		    	    }
		    	    
		    	     uniStud = tutorRegister.get("university");
		    	     
		    	     if(uniStud == null) {
		    	    	 
		    	    	  uniStud = "n/a";
		    	    	 
		    	     }
		    	     

		    	    if(uniStud.equals("yes") )
		    	    {
		    	    	
				    	 qualification = tutorRegister.get("qualifications");
				
				    	 specification = tutorRegister.get("specifications");
				    	
		    	    }

			        // Create the BecomeTutor entity
			        BecomeTutor becomeTutor = new BecomeTutor();
			        becomeTutor.setName(name);
			        becomeTutor.setSurname(surname);
			        becomeTutor.setEmail(email);
			        becomeTutor.setCountry(country);
			        becomeTutor.setDob(dob);
			        becomeTutor.setExperience(experience);
			        becomeTutor.setPhone(phone);
			        becomeTutor.setArea(area);
			        becomeTutor.setAddress(address);
			        becomeTutor.setProvince(province);
			        becomeTutor.setSubjects(subjects);
			        becomeTutor.setGrades(grades);
			        becomeTutor.setSyllabus(syllabus);
			        becomeTutor.setTutorsUniversityStudents(uniStud);
			        becomeTutor.setTutorOptions(tutorOptions);
			        becomeTutor.setAchievements(achievements);
			        becomeTutor.setQualification(qualification);
			        becomeTutor.setSpecification(specification);
			        becomeTutor.setBio(bio); 
			        becomeTutor.setAbout(about);

			        becomeTutor.setCv(cvFile.getBytes());
			        becomeTutor.setEducation(qualificationsFiles.getBytes());
			        becomeTutor.setIdPassport(idPassportFile.getBytes());
			        becomeTutor.setImage(imageFile.getBytes());
			       
			       System.out.print("we here");
			            
			         becomeTutorService.save(becomeTutor);
			          
			         /*Email to TUTOR*/
			    	    
			    	    senderService.sendSimpleEmail(email,
				                " Application Received - Become a Tutor\n"
				                + "",
				                "Dear " + name + ",\r\n"
				                + "\r\n"
				                + "Thank you for submitting your application to join Apex Academic Centre's tutoring team!\n"
				                + "\r\n"
				                + "We appreciate the time you took to complete our Become a Tutor form. Our administration team will review your profile, verify your credentials, and approve your profile for visibility on our website.\n"
				                + "\r\n"
				                + "For any questions or concerns:\r\n"
				                + "\r\n"
				                + "- Email: info@apexacademiccentre.co.za\r\n"
				                + "- Phone: 011 354 0198\r\n"
				                + "- WhatsApp: 068 035 1845\r\n"
				                + "\r\n"
				                + "We look forward to welcoming you!\n"
				                + ".\r\n"
				                + "Best regards,\r\n"
				                + "\r\n"
				                + "Apex Academic Centre");
			    	    
			    	    /*Email to Apex*/
			    	    
			    	    senderService.sendSimpleEmail("tutors@apexacademiccentre.co.za", 
			    	    		"Become Tutor Application - " + name, 
			    	    		"Dear Apex Academic Centre Team,\r\n" +
			    	    		"\r\n" +
			    	    		"We are pleased to inform you that we have received an application from " + name + " "+surname+", who is interested in becoming a tutor with us.\r\n" +
			    	    		"\r\n" +
			    	    		"Please take a moment to review the details of their application on the Admin panel.\r\n" +
			    	    		"\r\n" +
			    	    		"Thank you for your prompt attention to this matter.\r\n" +
			    	    		"\r\n" +
			    	    		"Best regards,\r\n" +
			    	    		"The Admin Team"
			            	);
			    	    
			            
			            
			    }

		 /*BECOME TUTOR*/
			    
	     /*PAYMENT GATEWAY*/
			    
				 @GetMapping("/payment")
				 public String proess_paynment(HttpSession session, Model model) {

					 String amount =   (String) session.getAttribute("amount");
			         String baseUrl = "https://bookatutorapexacademiccentre.co.za/"; // Change this to your actual domain
			         String successUrl = baseUrl + "success";
					    
				        model.addAttribute("amount", amount);
				        model.addAttribute("succUrl", successUrl);

						return "payment";
					 
				 }
				 
				    /*SUCCESS : AFTER PAYMENT RECEIVED*/
				    
		    	    
				    @GetMapping("/success")
				    public String handlePaymentSuccess(HttpSession session, Model model) {
				        // Extract necessary details from paymentDetails

			            String amount =  (String) session.getAttribute("amount");
			            String name =  (String) session.getAttribute("name");
			            String email = (String) session.getAttribute("email");
			            String surname = (String) session.getAttribute("surname");
			            String phone = (String) session.getAttribute("phone");
			            String province =  (String) session.getAttribute("province");
			            String country =  (String) session.getAttribute("country");
			            String instrLangauge =  (String) session.getAttribute("instrLangauge");
			            String tutorFor = (String) session.getAttribute("tutorFor");
			            String helpWith =  (String) session.getAttribute("helpWith");
			            String learnerName = (String) session.getAttribute("learnerName");
			            String learnerSurname =  (String) session.getAttribute("learnerSurname");
			            String grade = (String) session.getAttribute("grade");
			            String syllabus =  (String) session.getAttribute("syllabus");
			            String studentName =  (String) session.getAttribute("studentName");
			            String studentSurname = (String) session.getAttribute("studentSurname");
			            String year =  (String) session.getAttribute("year");
			         // String postGrad = (String) session.getAttribute("postGrad");
			            String subject =  (String) session.getAttribute("subject");
			            String onlineInperson =  (String) session.getAttribute("onlineInperson");
			            String suburb =  (String) session.getAttribute("suburb");
			            String toStart = (String) session.getAttribute("toStart");
			            String secTutor =  (String) session.getAttribute("secTutor");
			            String tutorName = (String) session.getAttribute("tutorName");
			            String tutorEmail =  (String) session.getAttribute("tutorEmail");
			            String sessions =  (String) session.getAttribute("sessions");
			            
			           
			            String qualification = (String) session.getAttribute("qualification");
			            String module =  (String) session.getAttribute("module");
			            String specification = (String) session.getAttribute("specification");
			           
			            
			            if (tutorName.equals("N/A")) {
			                tutorName = "Main Booking"; // Initialize to empty string
			                tutorEmail = "Main Booking"; // Initialize to empty string
			            }
			            
			            if(sessions == null) {
			            	
			            	sessions = "0";
			            }


			            String packageType =  (String) session.getAttribute("packageType");
			            String message = (String) session.getAttribute("message");
			            String isPaid = "Yes"; 

				        model.addAttribute("name", name);
				        model.addAttribute("email", email);
				
					     Booking bookings = new Booking( name,  surname,  email,  phone,  province,  country,
					    		 instrLangauge,  tutorFor,  helpWith,  learnerName,  learnerSurname,  grade,
								 syllabus,  studentName,  studentSurname,  year,  subject,  onlineInperson,
								 toStart,  suburb,  message,  secTutor,  "pending",  packageType,
								 tutorName,  tutorEmail,  isPaid , amount , sessions  , qualification , module , specification);
					     
					     bookingService.save(bookings);
					     
					     String sendName = "";
					     String clientName = name + " " + surname;
					     
					     if(learnerName.equals("N/A")) {
					    	 
					    	 sendName = studentName + " " + studentSurname;
					     }
					     
					     else {
					    	 
					    	 sendName = learnerName + " " + learnerSurname;
					    	 
					     }
					     
					     if(tutorName.equals("Main Booking")) {
					    	 
					    	 tutorName = "Our Highly intelligent tutor";
					    	 
					     }
					     		     
					     bookingService.save(bookings);
						    
						    Long entryId = bookings.getEntry();
						    String tutorEmaill = bookings.getTutorEmail();
						    
						     if(tutorEmail.equals("Main Booking")) {
						    	 
						    	 tutorEmaill = "info@apexacademiccentre.co.za";
						    	 
						     }
						     
						     /*TO DO*/
						    
						    String serverName = request.getServerName();
						    int serverPort = request.getServerPort();
						    String protocol = request.getScheme();
						    String host = protocol + "://" + serverName + ":" + serverPort;

					        String bookingLink = host + "/booking-details?id=" + entryId;
					        
						    /*Send email to tutor*/
							senderService.sendSimpleEmail(tutorEmaill, "Booking-info: " + subject ,
							"Dear " + tutorName + "\n\nWe're pleased to inform you that [" +  name + " " + surname + "] has booked your services to support their child, [" + sendName + "]. We're thrilled to have you on board and are confident in your ability to provide exceptional guidance and support.\r\n"
									+ "\r\n"
									+ "To confirm your booking, please review the details at the link below and ACCEPT within 24 hours. If you're unavailable or need to reschedule, please notify us immediately so we can arrange a suitable replacement tutor.\r\n"
									+ "\r\n"
									+ "Link : " + bookingLink + "\r\n"
									+ "\r\n"
									+ "Thank you for your cooperation and expertise. We look forward to a successful collaboration!\r\n"
									+ "\r\n"
									+ "Best regards,\r\n"
									+ "Apex Academic Centre");
							
							
						    /*Send email to the student who book tutor*/
							senderService.sendSimpleEmail(email, "Apex Tutor booking : no-reply" ,
							"Dear " + clientName + "\n\nThank you for choosing " + tutorName + "! We're thrilled to have them work with " + sendName + " and are confident they will receive exceptional guidance and support in " + subject + ".\r\n"
									+ "\r\n"
									+ "Next Steps:\r\n"
									+ "\r\n"
									+ "- " + tutorName + " will contact you within the next 24 hours to introduce themselves and discuss a personalized lesson plan.\r\n"
									+ "- Please note that if " + tutorName + " is unavailable for any reason or needs to reschedule, we have a secondary tutor available to ensure continuity.\r\n"
									+ "\r\n"
									+ "Thank you for entrusting us with your academic needs. We look forward to " + sendName + " success!\r\n"
									+ "\r\n"
									+ "Best regards,\r\n"
									+ "Apex Academic Centre");
				        
				        
				        
				        return "success"; // Name of the success HTML page (success.html)
				        
				    }
				
		/*PAYMENT GATEWAY*/
				 
	@GetMapping("/admin")
       public ModelAndView adminDashboard(HttpSession session) {
		    String email = (String) session.getAttribute("adminEmail");
		    if (email == null || !adminService.isAdminLoggedIn(email)) {
		        return new ModelAndView("loginAdmin.html"); // Redirect to login if not logged in
		    }

		//List<Tutor> tutor = tutorService.listAll();
		
	  /*   Page<Tutor> page = tutorService.findPage(1);

	    
	     long totalPages = page.getTotalPages();
	     long totalItems = page.getTotalElements();
	     List<Tutor> tutor = page.getContent();*/
		
		
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

		
		List<Review> reviews = reviewService.listAll();
		List<OnlineClass> rewrite = onlineService.listAll();

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
		
		List<MathsClass> mathsClasses = mathsService.listAll();
		
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
		
		
		List<BecomeTutor> becomeTutor = becomeTutorService.listAll();
		
        becomeTutor.sort((tutor1, tutor2) -> tutor2.getCreatedAt().compareTo(tutor1.getCreatedAt()));
        
        List<PaidBookings> paid = paidService.listAll();
		
		if(email.equals("info@apexacademiccentre.co.za")) {
		
		ModelAndView data = new ModelAndView("adminDashboard.jsp");// load the admin dashboard
		
		/*data.addObject("tutors" , tutor);
		data.addObject("currentPage", 1);
		data.addObject("totalPages", totalPages);
		data.addObject("totalItems", totalItems);
		
		data.addObject("bookings", booking);
		data.addObject("reviews", reviews);
		data.addObject("rewrite", rewrite);
		data.addObject("mathsClasses",mathsClasses);	
		data.addObject("becomeTutor",becomeTutor);
		data.addObject("paid",paid);*/
		
		return data;
		
		}
		
		else {
			
			ModelAndView data = new ModelAndView("loginAdmin.html");// login first
		
				
			return data;
			
			
		}
	}
	
	
	/*Pagination for Admin*/
	
	 @GetMapping("/adminpage-{page}")
	 @ResponseBody
	 public ModelAndView adminList(@PathVariable int page) {
		 
		 return getOnePageAdmin(page);
	 }
	 
	 @GetMapping("/adminlist/{pageNumber}")
	 public ModelAndView getOnePageAdmin(@PathVariable("pageNumber") int currentPage) {


		//List<Tutor> tutor = tutorService.listAll();
		
	 /*    Page<Tutor> page = tutorService.findPage(currentPage);

	     // Get the total number of pages and total items
	     long totalPages = page.getTotalPages();
	     long totalItems = page.getTotalElements();
	     List<Tutor> tutor = page.getContent();
		*/
		
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

		
		List<Review> reviews = reviewService.listAll();
		List<OnlineClass> rewrite = onlineService.listAll();

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
		
		List<MathsClass> mathsClasses = mathsService.listAll();
		
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
		
		
		List<BecomeTutor> becomeTutor = becomeTutorService.listAll();
		
        becomeTutor.sort((tutor1, tutor2) -> tutor2.getCreatedAt().compareTo(tutor1.getCreatedAt()));
     
        List<PaidBookings> paid = paidService.listAll();

		ModelAndView data = new ModelAndView("adminDashboard.jsp");// load the admin dashboard
		
	/*	data.addObject("tutors" , tutor);
		data.addObject("currentPage", currentPage);
		data.addObject("totalPages", totalPages);
		data.addObject("totalItems", totalItems);*/
		
		data.addObject("bookings", booking);
		data.addObject("reviews", reviews);
		data.addObject("rewrite", rewrite);
		data.addObject("mathsClasses",mathsClasses);	
		data.addObject("becomeTutor",becomeTutor);
		data.addObject("paid",paid);
		
		return data;		 
		 
	 }
	
	
	
	/*Pagination for Admin*/
	
	
	
	

	 @PostMapping("/addTutor")//only admin have access
		public String addTutor(@RequestParam("profile") MultipartFile profile , @RequestParam("hiddenName")  String name ,
				@RequestParam("hiddenID") String id , @RequestParam("hiddenEmail") String email ,
				@RequestParam("hiddenPhone") String phone , @RequestParam("hiddenSubjects") String subjects ,
				@RequestParam("hiddenGrades") String grades , @RequestParam("hiddenSyllabus") String syllabus ,
				@RequestParam("tutorOption") String tutorOption , @RequestParam("hiddenAddress") String address,
				@RequestParam("bio") String qualification , @RequestParam("about") String about,
				@RequestParam("hours") String bio , @RequestParam("hiddenArea") String area, @RequestParam("hiddenCountry") String country , 
				@RequestParam("hiddenIdentity") String dob , @RequestParam("hiddenSurname") String surname ,@RequestParam("hiddenModules") String hiddenModules ,
				@RequestParam("hiddenExp") String hiddenExp) throws IOException, ParseException 
		{
		 
		 if(address.equals("")) {
			 
			 address = "international";
			 
		 }
		 		 
		 int expYear = Integer.parseInt(hiddenExp);
		 int experienceYears = 2024 - expYear;
		 
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 Date date = dateFormat.parse(dob);
		 
           byte[] imageData = profile.getBytes();
                
		   Tutor tutor = new Tutor(email,name,id,phone,subjects,grades,address,tutorOption,qualification,about,bio,imageData,syllabus,area,country,date,surname,hiddenModules,experienceYears);
		      
		    tutorService.save(tutor); 
		    
		    String serverName = request.getServerName();
		    int serverPort = request.getServerPort();
		    String protocol = request.getScheme();
		    String host = protocol + "://" + serverName + ":" + serverPort;

	        String reviewLink = host + "/review-ratings?email=" + email;
	        

	        // Create and save the second review
	        Review review2 = new Review();
	        review2.setTutorEmail(email);
	        review2.setName("Mabhena Bongisani");
	        review2.setRating(0);
	        review2.setMessage("Great tutor! Helped me understand complex concepts, built my confidence, and provided valuable feedback. Would definitely recommend!");
	        review2.setStatus("approved");
	        reviewService.save(review2);

	        // Create and save the third review
	        Review review3 = new Review();
	        review3.setTutorEmail(email);
	        review3.setName("Sihle Sithole");
	        review3.setRating(2);
	        review3.setMessage("Outstanding guidance and support! Created a comfortable learning environment, addressed all questions, and provided constructive feedback. Grateful for the expertise!");
	        review3.setStatus("approved");
	        reviewService.save(review3);

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
		   
			 return "redirect:/admin"; // Redirect to adminDashBoard
		 
 
		}
	 
	 @PostMapping("/deleteTutor")
     public String deleteTutor(@RequestParam("deleteEmail") String email ) {
		 
		 Tutor tutor = new Tutor();
		    tutor.setEmail(email);
		      tutorService.delete(tutor);
		 	 
		 return "redirect:/admin";
		 
	 }
	 
	 @PostMapping("/deleteApplicant")
     public String deleteApplicant(@RequestParam("emailApplicant") String email ) {
		 
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
			   
		    return "redirect:/admin";
		 
	 }
	 
	 
	 
	 @PostMapping("/editTutor")//only admin have access
	 @ResponseBody
		public String updateTutor(@RequestParam("editprofile") MultipartFile profile , @RequestParam("edithiddenName")  String name 
				, @RequestParam("edithiddenEmail") String email ,
				@RequestParam("edithiddenPhone") String phone , @RequestParam("edithiddenSubjects") String subjects ,
				@RequestParam("edithiddenGrades") String grades , @RequestParam("edithiddenSyllabus") String syllabus ,
				@RequestParam("edittutorOption") String tutorOption , @RequestParam("edithiddenAddress") String address,
				@RequestParam("editbio") String bio , @RequestParam("editabout") String about,@RequestParam("edithiddenCountry") String country,
				@RequestParam("edithours") String hours , @RequestParam("edithiddenArea") String area  ) throws IOException 
		{
		 
		 System.out.println(country);
		 System.out.println(address);
		 
		 Optional<Tutor> opT = tutorRepo.findById(email);
		 Tutor pTutor = new Tutor();
		 
	        if (opT.isPresent()){
				 
	        	pTutor = opT.get();
	
			 }
		 
			 byte[] imageData;
			 
			 if (profile == null || profile.isEmpty()) {
			 
				 System.out.println("The Image is empty and will not be updated");
				 imageData = pTutor.getImage();
				 
			    }
			 
			 else {
				 System.out.println("The Image is not empty and it will  be updated");
				 imageData = profile.getBytes();
			 }
			 
			  // Set the address based on the country
			    if (!"South Africa".equals(country)) {
			        address = "n/a"; 
			    } 
			    
           Tutor tutor = new Tutor();
           tutor.setEmail(email);
		    tutor.setFullNames(name);
		    tutor.setImage(imageData);
		    tutor.setPhoneNumber(phone);
		    tutor.setEmail(email);
		    tutor.setAddress(address);
		    tutor.setSubjects(subjects);
		    tutor.setGrades(grades);
		    tutor.setSyllabus(syllabus);
		    tutor.setBio(bio);
		    tutor.setAbout(about);
		    tutor.setHoursTutored(hours);
		    tutor.setAvailability(tutorOption);
		    tutor.setArea(area);
		    tutor.setCountry(country);
		   
		    tutorService.update(email, tutor);
		    
		    return "Tutor Successfully Updated."; 
		   
			//return "redirect:/admin"; // Redirect to adminDashBoard
		 
		}
	 
	 
	 @PostMapping("/searchTutor")
	 @ResponseBody
	 public List<Tutor> searchTutor(@RequestBody Map<String, String> requestBody) {
	
		 String name = requestBody.get("searchEmail");
		 
		 System.out.println(name);
		 
		 List<Tutor> tutor = tutorRepo.searchByName(name);
		
		 return tutor;
		 
	 }
	 

	 
	 @PostMapping("/other-booking")
	 @ResponseBody
	    public void otherBooking(@RequestBody Map<String, String> booking) {
	        

		 System.out.println("We here - Send the email");
		 
		    String name = booking.get("first-name");
		    String surname = booking.get("last-name");
		    String email = booking.get("email");
		    String phone = booking.get("phone");
		    String province = booking.get("province");
		    String country = booking.get("country");
		    String instrLangauge = booking.get("language");
		    String tutorFor = booking.get("tutoring-for");
		    String helpWith = booking.get("help-with");

		    
		    String schName = "n/a";
		    String schSurname = "n/a";
		    String schGrade = "n/a";
		    String schSyllabus = "n/a";
		    String unName = "n/a";
		    String unSurname = "n/a";
		    String unYear = "n/a";
		    String qualification = "n/a";
		    String module = "n/a";
		    String specification = "n/a";
		    
		    boolean isStudent = false;

		    if(helpWith.equals("school")){

		        schName = booking.get("student-name");
		        schSurname = booking.get("student-last-name");
		        
		        if(schName.equals(name) && schSurname.equals(surname)) {
		        	
		        	isStudent = true;
		        }
		        
		        schGrade = booking.get("grade");
		        		        
		       if(!schGrade.equals("n/a") && schGrade.equals("")){
		    	   
		    	    schGrade = booking.get("inter-grade");
		       }
		           
		        schSyllabus = booking.get("syllabus");

		    }

		    else{

		         unName = booking.get("stud-name");
		         unSurname = booking.get("stud-last");
		         
			        if(unName.equals(name) && unSurname.equals(surname)) {
			        	
			        	isStudent = true;
			        }
			        
		         unYear = booking.get("year");
		         qualification = booking.get("qualification-id");
		         module = booking.get("module-id");
		         specification = booking.get("specification-id");
		        

		    }
		    		    
		    String subject = booking.get("subject");
		    String tutorStyle = booking.get("tutor-style");

		    String address = "n/a";
		    String suburb = "n/a";

		    if(tutorStyle.equals("In Person")){

		         address = booking.get("address-inperson");
		         suburb = booking.get("suburb-inperson");
		    }

		    String message = booking.get("message");
		    if(message.equals("")){
		        message = "n/a";
		    }
		    
		     String tutorOption = booking.get("tutor-option");
		     String status = booking.get("who");
		     String userPackage = "Not Selected";
		     
			    String tutorName = booking.get("hiddenTutorName");
			    String tutorEmail = booking.get("hiddenTutorEmail");

			    if(tutorName.equals("undefined")) {
			    	
			    	  tutorName = "Apex Tutor";
			    	  tutorEmail = "info@apexacademiccentre.co.za";
			    	  	 
			    }
			    
			    
			    if(tutorName.equals("")) {
			    	
			    	  tutorName = "Apex Tutor";
			    	  tutorEmail = "info@apexacademiccentre.co.za";
			    	  	 
			    }
			    
			    
		     
		     String isPaid = "No";
		     
		     Booking bookings = new Booking( name,  surname,  email,  phone,  province,  country,
		    		 instrLangauge,  tutorFor,  helpWith,  schName,  schSurname,  schGrade,
					 schSyllabus,  unName,  unSurname,  unYear,  subject,  tutorStyle,
					 address,  suburb,  message,  tutorOption,  status,  userPackage,
					 tutorName,  tutorEmail,  isPaid , "not paid" , "0", qualification , module , specification);
		     
		     String sendName = "";
		     String clientName = name + " " + surname;
		     
		     if(schName.equals("n/a")) {
		    	 
		    	 sendName = unName + " " + unSurname;
		     }
		     
		     else {
		    	 
		    	 sendName = schName + " " + schSurname;
		    	 
		     }
		     
		     
		     bookingService.save(bookings);
		     
		 
	        // SEND EMAIL TO CLIENT
		     
		     if(isStudent) {
		    	 
		            senderService.sendSimpleEmail(email, "Apex Academic Centre - Tutor Booking" ,
		    	            "Dear " + clientName + "  ,\r\n\n"
		    	            + "Thank you for considering Apex Academic Centre for your educational needs. We appreciate your inquiry about our one-on-one, " + tutorStyle + " tutoring services for " + subject + ".\r\n\n"
		    	            + "Our consultants will promptly contact you to discuss your requirements and match you with a suitable tutor.\r\n\n"
		    	            + "Kind regards,\r\n\n"
		    	            + "Apex Academic Centre");
		    	 
		     }
		     
		     else {
	         
	            senderService.sendSimpleEmail(email, "Apex Academic Centre - Tutor Booking" ,
	            "Dear " + clientName + ",\r\n"
	            + "\r\n"
	            + "\r\n"
	            + "Thank you for considering Apex Academic Centre for " + sendName  +"'s educational needs. We appreciate your inquiry about our one-on-one " + tutorStyle + " tutoring services for " + subject + ".\r\n"
	            + "\r\n"
	            + "\r\n"
	            + "Our consultants will promptly contact you to discuss " + sendName  +"'s requirements and match them with a suitable tutor.\r\n"
	            + "\r\n"
	            + "\r\n"
	            + "Best regards,\r\n"
	            + "Apex Academic Centre");
	            
		     }
	        
	            /*   SEND EMAIL TO APEX  */
	            
			    Long entryId = bookings.getEntry();
			    
			    String serverName = request.getServerName();
			    int serverPort = request.getServerPort();
			    String protocol = request.getScheme();
			    String host = protocol + "://" + serverName + ":" + serverPort;

		        String bookingLink = host + "/booking-details?id=" + entryId;
	            
	            if(status.equals("consult")) {
	            	
	            	
	        	    if(!tutorName.equals("Apex Tutor")) {
				    	
				    	  tutorName = "Apex Tutor";
				    	  tutorEmail = "info@apexacademiccentre.co.za";
				    	  
			            	senderService.sendSimpleEmail(tutorEmail, 
				            	    "New Tutor Request - " + clientName, 
				            	    "Dear Apex Academic Centre Team,\r\n" +
				            	    "\r\n" +
				            	    "We have received a tutoring inquiry from " + clientName + ", who has filled out our Book A Tutor form.\r\n" +
				            	    "\r\n" +
				            	    "Please review the details of their request here: " + bookingLink + "\r\n" +
				            	    "\r\n" +
				            	    "We kindly request that you contact " + clientName + " to schedule a consultation and discuss their tutoring needs.\r\n" +
				            	    "\r\n" +
				            	    "Thank you for your prompt attention.\r\n" +
				            	    "\r\n" +
				            	    "Best regards,\r\n" +
				            	    "Admin"
				            	);
			            	
			            	senderService.sendSimpleEmail("info@apexacademiccentre.co.za", 
				            	    "New Tutor Request - " + clientName, 
				            	    "Dear Admin,\r\n" +
				            	    "\r\n" +
				            	    "We have received a tutoring inquiry from " + clientName + ", who has filled out our Book A Tutor form.\r\n" +
				            	    "\r\n" +
				            	    "We are pleased to inform you that " + clientName + " has successfully booked a session with tutor \r\n" +
				            	    "Name :" + tutorName + "\r" +
				            	    "Email :" + tutorEmail + "\r\n" +
				            	    "Please review the details of their request here: " + bookingLink + "\r\n" +
				            	    "\r\n" +
				            	    "We kindly request that you contact " + clientName + " to schedule a consultation and discuss their tutoring needs.\r\n" +
				            	    "\r\n" +
				            	    "Thank you for your prompt attention.\r\n" +
				            	    "\r\n" +
				            	    "Best regards,\r\n" +
				            	    "Admin"
				            	);
		        	 
				    	  
				    	  	 
				    }
	        	    
	        	    else {
	        	    	
		            	senderService.sendSimpleEmail(tutorEmail, 
			            	    "New Tutor Request - " + clientName, 
			            	    "Dear Apex Academic Centre Team,\r\n" +
			            	    "\r\n" +
			            	    "We have received a tutoring inquiry from " + clientName + ", who has filled out our Book A Tutor form.\r\n" +
			            	    "\r\n" +
			            	    "Please review the details of their request here: " + bookingLink + "\r\n" +
			            	    "\r\n" +
			            	    "We kindly request that you contact " + clientName + " to schedule a consultation and discuss their tutoring needs.\r\n" +
			            	    "\r\n" +
			            	    "Thank you for your prompt attention.\r\n" +
			            	    "\r\n" +
			            	    "Best regards,\r\n" +
			            	    "Admin"
			            	);
	        	    	
	        	    }
		
	            	
	            }else {
	            	
	            	senderService.sendSimpleEmail(tutorEmail, 
		            	    "New Tutor Request - " + clientName, 
		            	    "Dear Apex Academic Centre Team,\r\n" +
		            	    "\r\n" +
		            	    "We have received a tutoring inquiry from " + clientName + ", who has filled out our Book A Tutor form.\r\n" +
		            	    "\r\n" +
		            	    "Please review the details of their request here: " + bookingLink + "\r\n" +
		            	    "\r\n" +
		            	    "We kindly request that you contact " + clientName + " to schedule a consultation and discuss their tutoring needs.\r\n" +
		            	    "\r\n" +
		            	    "Thank you for your prompt attention.\r\n" +
		            	    "\r\n" +
		            	    "Best regards,\r\n" +
		            	    "Admin"
		            	);
	            	
	            	
	            	
	            }
		      
	         
	    }
	 

	    @GetMapping("/booking-details")
	    public ModelAndView getBookingDetails(@RequestParam("id") Long id) {
	    	
	        Booking booking = bookingService.findOneBook(id);  

			ModelAndView data = new ModelAndView("confirm-booking.jsp");// load the admin dashboard
			data.addObject("bookings", booking);
			
			return data;
	
	    }
	    
		 @PostMapping("/accept-booking")
		 @ResponseBody
		 public RedirectView acceptBooking(@RequestParam("accept-id") Long id, HttpServletRequest request) {
			 
			 Booking booking = bookingService.findOneBook(id); 
			 
			 String checkStatus = booking.getStatus();
			 
			  bookingService.update(id);
			  
			    String serverName = request.getServerName();
			    int serverPort = request.getServerPort();
			    String protocol = request.getScheme();
			    String host = protocol + "://" + serverName + ":" + serverPort;

		        String bookingLink = host + "/booking-details?id=" + id;
		        
			    /*Send email to applicant*/
		        
		        
		        String email = booking.getEmail();
		        String subject = booking.getSubject();
		        String tutorName = booking.getTutorName();
		        String name = booking.getName();
		        String surname = booking.getSurname();
 
		        String clientName = name + " " + surname;
		        
		        if(checkStatus.equals("consult")) {
		        	
					senderService.sendSimpleEmail(email, "Booking Confirmation - Next Steps"  ,
							"Dear " + clientName+",\n\nThank you for choosing Apex Academic Centre. We're delighted to confirm your booking.\r\n"
									+ "\r\n"
									+ "\r\n"
									+ "What's next:\r\n"
									+ "\r\n"
									+ "\r\n"
									+ "- Our team will contact you to discuss tutor-matching\r\n"
									+ "- Arrange scheduling and logistics\r\n"
									+ "- Ensure a tailored tutoring plan\r\n"
									+ "\r\n"
									+ "\r\n"
									+ "We're dedicated to your child's academic success.\r\n"
									+ "\r\n"
									+ "\r\n"
									+ "Best regards,\r\n"
									+ "Apex Academic Centre");
		        	
		        	
		        }
		        else {
		        
				senderService.sendSimpleEmail(email, "Booking Approval - " + subject  ,
				"Dear " + clientName+",\n\nWe're delighted to inform you that " + tutorName + " has accepted your booking! They will be in touch with you shortly to arrange a suitable timetable and discuss any necessary details.\r\n"
						+ "\r\n"
						+ "Please note that if you have any questions, concerns, or misunderstandings, you can reach us directly via:\r\n"
						+ "\r\n"
						+ "Email: admin@apexacademiccentre.co.za\r\n"
						+ "Phone/WhatsApp: 068 035 1845\r\n"
						+ "\r\n"
						+ "We're committed to ensuring a smooth and successful tutoring experience.\r\n"
						+ "\r\n"
						+ "Kind regards,\r\n"
						+ "Apex Academic Centre");
				
		        }
			  
		        return new RedirectView(bookingLink);

			 
		 }
		 
		 @GetMapping("/review-ratings")
		 public ModelAndView reviewRatings(@RequestParam("email") String tutorEmail) {
			 
				ModelAndView data = new ModelAndView("rate-tutor.jsp");// load the admin dashboard
				data.addObject("tutorEmail", tutorEmail);
				
				return data;
			 
		 }
		 
		 @PostMapping("/add-review-ratings")
		 public String addReviews(@RequestParam("tutorEmail") String tutorEmail ,  @RequestParam("name") String name , @RequestParam("message") String message , @RequestParam("ratings") int ratings) {
			 
			
			 Review reviews = new Review(name,tutorEmail,message,ratings,"pending");
			  
			 reviewService.save(reviews);
				
				return "redirect:/";
			 
		 }
		 
		 @PostMapping("/approve-review")
		 public String acceptReview(@RequestParam("rEntryId") Long entryId , @RequestParam("rTutorEmail") String email) {
			 
			 reviewService.updateReview(entryId);
				
				return "redirect:/admin";
			 
		 }
		 
		 

  

		    

		    /*DELETE CONSULT BOOKING*/
		    		
			 @PostMapping("/deleteConsult")
		     public String deleteConsultant(@RequestParam("deleteEmailConsult") Long id ) {
				 
				 Booking booking = bookingService.findOneBook(id);
				    bookingService.delete(booking);
				 	 
				 return "redirect:/admin";
				 
			 }
			 
			 /*DELETE OTHER BOOKING*/
			 
			 @PostMapping("/deleteOther")
		     public String deleteOther(@RequestParam("deleteEmailOther") Long id ) {
				 
				 Booking booking = bookingService.findOneBook(id);
				    bookingService.delete(booking);
				 	 
				 return "redirect:/admin";
				 
			 }
			 
			 /*DELETE APPROVED BOOKING*/
			 
			 @PostMapping("/delete-approved-booking")
		     public String deleteApprovedBooking(@RequestParam("deleteEmailApproved") Long id) {
				 
				 Booking booking = bookingService.findOneBook(id);
				    bookingService.delete(booking);
				 	 
				 return "redirect:/admin";
				 
			 }
			 
			 /*DELETE REGISTERED MATRIC*/
			 
			 @PostMapping("/delete-registered-matric")
		     public String deleteMatricReg(@RequestParam("deleteMatricReg") Long id) {
				  
				 OnlineClass matric = onlineService.findOneBook(id);
				    onlineService.delete(matric);
				 	 
				 return "redirect:/admin";
				 
			 }
			 
			
			 
			 /*RENDER ADMIN LOGIN PAGE*/
			 
			    @GetMapping("/adminpanel")
			    public String adminLogin() {
			    	
			    	  return "loginAdmin.html";
			    }
			    
			 /*   @GetMapping("/resetPassword")
			    @ResponseBody
			    public passwordReset getAdmin() {
			 
					 
					 return CredentialsUpdate.html;
			    }*/
			    
			    

			    

				 /*AUTHENTIFICATION*/
				 
				 @PostMapping("/login")
				 public ModelAndView login(@RequestParam String email, @RequestParam String password, HttpSession session) {
				     /*if (adminService.isAdminLoggedIn(email)) {
				         return new ModelAndView("redirect:/loginAdmin.html").addObject("message", "This admin is already logged in.");
				     }*/

				     if (adminService.validateAdmin(email, password)) {
				         session.setAttribute("adminEmail", email); // Store email in session
				         adminService.logInAdmin(email, session); // Register this session
				         return new ModelAndView("redirect:/admin"); // Redirect without exposing email in URL
				     } else {
				         return new ModelAndView("redirect:/loginAdmin.html").addObject("message", "Invalid credentials.");
				     }
				 }
				 
				 
				    @GetMapping("/logout")
				    public String logout(HttpSession session) {
				        String email = (String) session.getAttribute("adminEmail");
				        if (email != null) {
				            adminService.logOutAdmin(email); // Remove this admin from logged-in users
				            session.invalidate(); // Invalidate the session
				        }
				        return "loginAdmin.html"; // Redirect to the login page
				    }
				    
				    
				    
				    /*RESET CREDENTIALS*/
				    
				    @GetMapping("/rstapexpasswyhjbhcentre")
					   
				    public String passwordReset() {
				 
						 
						 return "CredentialsUpdate.html";
						 
				    }
				    
				    @GetMapping("/generateVerificationCode")
				    @ResponseBody
				    public String generateVerificationCode() {
				        // Generate a random 6-digit verification code
				        String generatedCode = String.valueOf(new Random().nextInt(900000) + 100000);
				        System.out.println("Generated Code: " + generatedCode); // Log the generated code (for demo purposes)
				        // In a real application, you would send this code to the user's email

				        String subject = "Apex Academic Centre - Security";
				       
				        String recipientEmail = "info@apexacademiccentre.co.za"; 
				       
				        String emailBody = String.format(
				            "Dear Admin,\n\n" +
				            "To enhance your account security, we have generated a verification code for you. Please use the code below to proceed:\n\n" +
				            "Verification Code:" + generatedCode +
				            "\n\nIf you did not request this code, please ignore this email or contact our support team if you have any concerns.\n\n" +
				            "Best regards,\n" +
				            "Apex Academic Centre Team\n");

				        // Send the email
				        senderService.sendSimpleEmail(recipientEmail, subject, emailBody);

				        return generatedCode; // Return the generated code
				    }
				    
				    
				    
				    @PostMapping("/resetPassword")
				    @ResponseBody
				    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> password) {
				        String newPassword = password.get("password");
				        
				        // Log the received password
				        System.out.println("Received password: " + newPassword);
				           
				        adminService.update(newPassword);
				        
	        
				        return ResponseEntity.ok("Password reset successfully."); // Sending success response
				    }
				    
				    
				    /*SEARCH ENGINE*/
    
				    @PostMapping("/searchEngine")
				    @ResponseBody
				    public void searchEngine(@RequestBody Map<String, String> searchInput, HttpSession session) {
				        // Extract the search string
				        String search = searchInput.get("search");

				        System.out.println("Search Input: " + search); // For debugging

				        // Split the search string by commas
				        String[] searchParams = search.split(",");

				        // Create an array to hold the filters
				        String[] filters = new String[4];

				        // Parse the search parameters
				        for (String param : searchParams) {
				            if (param.startsWith("l")) {
				                filters[0] = param.substring(1).trim(); // Set location
				            } else if (param.startsWith("s")) {
				                filters[1] = param.substring(1).trim(); // Set subject
				            } else if (param.startsWith("c")) {
				                filters[2] = param.substring(1).trim(); // Set curriculum
				            } else if (param.startsWith("t")) {
				                filters[3] = param.substring(1).trim(); // Set tutoring option
				            }
				        }

				        // Get all tutors
				        List<Tutor> tutors = tutorService.listAll();

				        // Filter the tutors based on the provided attributes
				        List<Tutor> filteredTutors = tutors.stream()
				            .filter(tutor -> {
				                boolean matches = true;

				                // Check location
				                if (filters[0] != null) {
				                    String[] areas = tutor.getArea().split(",\\s*");
				                    String[] countries = tutor.getCountry().split(",\\s*");
				                    String address = tutor.getAddress(); // Get the tutor's address

				                    matches = Arrays.stream(areas)
				                        .anyMatch(area -> area.equalsIgnoreCase(filters[0])) || 
				                        Arrays.stream(countries)
				                        .anyMatch(country -> country.equalsIgnoreCase(filters[0])) ||
				                        address.equalsIgnoreCase(filters[0]); // Check if the address matches the filter
				                }

				                // Check subjects
				                if (matches && filters[1] != null) {
				                    String[] subjects = tutor.getSubjects().split(",\\s*");
				                    matches = Arrays.stream(subjects)
				                        .anyMatch(subject -> subject.equalsIgnoreCase(filters[1]));
				                }

				                // Check syllabus
				                if (matches && filters[2] != null) {
				                    String[] syllabuses = tutor.getSyllabus().split(",\\s*");
				                    matches = Arrays.stream(syllabuses)
				                        .anyMatch(syllabus -> syllabus.equalsIgnoreCase(filters[2]));
				                }

				             // Check availability
				                if (matches && filters[3] != null) {
				                    String[] availabilities = tutor.getAvailability().split("/");

				                    // Trim each availability to remove leading/trailing whitespace
				                    availabilities = Arrays.stream(availabilities)
				                        .map(String::trim) // Trim each element
				                        .toArray(String[]::new); // Collect back to an array

				                    // Check if any availability matches the filter (case insensitive)
				                    matches = Arrays.stream(availabilities)
				                        .anyMatch(availability -> availability.equalsIgnoreCase(filters[3]));
				                }
				                return matches;
				            })
				            .collect(Collectors.toList());

				        session.setAttribute("filteredTutors", filteredTutors);
				    }

				    
					@GetMapping("/searchOptimazation")
				       public ModelAndView searchOptimazation(HttpSession session) {
						
					
						List<Tutor> tutors = (List<Tutor>)session.getAttribute("filteredTutors");
						    
							ModelAndView data = new ModelAndView("tutorsSearchPage.jsp");
							data.addObject("tutors" , tutors);
					
							return data;
						
					}
					
					
					/*ONLINE CLASSES : REGISTRATION*/
					

					
				
					
				    
					    /*MATHS ONLINE CLASSES*/
					    

						
						 
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
						 
						 
						 /*THE Become A TUTOR*/
						 
						 
						
						    
						    /*Admin Accept Tutor*/
						    
							 @PostMapping("/add-accepted-tutor")//only admin have access
								public String addAcceptedTutor(@RequestParam("rName")  String name, @RequestParam("rEmail") String email ,
										@RequestParam("rPhone") String phone , @RequestParam("rSubjects") String subjects ,
										@RequestParam("rGrades") String grades , @RequestParam("rSyllabus") String syllabus ,
										@RequestParam("rTutorOption") String tutorOption , @RequestParam("rAddress") String address,
										@RequestParam("rAchievements") String qualification , @RequestParam("rAbout") String about,
										@RequestParam("rHours") String bio , @RequestParam("rArea") String area, @RequestParam("rCountry") String country , @RequestParam("rProfile") String profile , 
										@RequestParam("rDOB") String dob , @RequestParam("rSurname") String surname ,@RequestParam("rModules") String hiddenModules ,
										@RequestParam("rExp") String hiddenExp) throws IOException, ParseException 
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
								
									 return "redirect:/admin"; // Redirect to adminDashBoard
								 
						 
								}
					 
							 

							 
							 
						/*	 @PostMapping("/subscribe")
								public ModelAndView subscribeMethod(@RequestParam("email") String email) {
								 
								 
									senderService.sendSimpleEmail(email, " Welcome to Apex Academic Centre!" ,
									"Dear,\n"
									+ "\n"
									+ "Thank you for subscribing to Apex Academic Centre! We're thrilled to have you on board.\n"
									+ "\n"
									+ "What to Expect:\n"
									+ "\n"
									+ "- Personalized academic support for primary, high school, and university students\n"
									+ "- Expert tutors in all subjects\n"
									+ "- Flexible online and in-person lessons\n"
									+ "\n"
									+ "Getting Started:\n"
									+ "\n"
									+ "1. Browse our tutor profiles\n"
									+ "2. Book a tutor https://bookatutorapexacademiccentre.co.za\n"
									+ "3. Achieve academic success!\n"
									+ "\n"
									+ "Need Help?\n"
									+ "\n"
									+ "Email: info@apexacademiccentre.co.za\n"
									+ "Phone: 011 354 0198\n"
									+ "WhatsApp: 068 035 1845\n"
									+ "\n"
									+ "Thank you for choosing Apex Academic Centre. We look forward to supporting your academic journey!\n"
									+ "\n"
									+ "Best regards,\n"
									+ "\n"
									+ "Apex Academic Centre Team");
									
									
									senderService.sendSimpleEmail("marketing@apexacademiccentre.co.za", "" ,
									"Dear Apex Academic Centre \n"
									+ "\n"
									+ "We are pleased to inform you that " + email + " subscribed to your website.\n"
									+ "\n"
									+ "Best regards\n"
									+ "\n"
									+ "The admin Team");
									
									
								 
								    // Retrieve the list of tutors
									
									return getOnePage(1);
									
								}*/
							 
							 
							 @PostMapping("/deleteReview")
							 public String deleteReview(@RequestParam("entryReview") Long entryId) {
								 
								 Review rev = reviewService.findOneReview(entryId);
								 reviewRepo.delete(rev);
								 
								return "redirect:/admin";
								 
							 }
							 
							 @PostMapping("/sendEmail")
							 public String sendEmail(@RequestParam("lclient") String clientName, @RequestParam("lstudname") String studentName , @RequestParam("lemail") String email,
									 @RequestParam("ldate") String date, @RequestParam("lamount") String amount , @RequestParam("lsession") String sess , @RequestParam("ltutor") String optTutor) {
								 
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
							
								 System.out.println(studentName);
								 System.out.println(date);
								 System.out.println(amount);
								 System.out.println(sess);
								 System.out.println(optTutor);
								 System.out.println(email);
						
								 clientName = clientName.replace(" ", "@");
								 studentName = studentName.replace(" ", "@");
								
								 senderService.sendEmailWithButton(email,subject,clientName,studentName,date,amount,sess,optTutor,clientOrBoth);
								
								 return "redirect:/admin";
								 
							 }
							 
							 
							                     /*Cancel Payment*/
							 
							    @GetMapping("/cancel-link")
							    public String handlePaymentLinkCancel(@RequestParam Map<String, String> paymentDetails , HttpSession session) {
							    	
							    	
							    	   String details = paymentDetails.get("details");
							    	   
							    	   session.setAttribute("paymentDetails", details);
							    	   
							    	   return "redirect:/unsuccessful-payment";
							    	   
							    }
							    
							    
							    @GetMapping("/unsuccessful-payment")
							    public ModelAndView handlePaymentLinkUnsuccessful(HttpSession session) {
							    
							    	   String details = (String)session.getAttribute("paymentDetails");
							    	   
							    	   String[] clientDetails = details.split("_");
							    	   
							    	   String clientName = clientDetails[0];
							    		clientName = clientName.replace("@", " ");
							    			
							    	   String studentName = clientDetails[1];
							    	   studentName = studentName.replace("@", " ");
							    	   
							    	   System.out.println("Name " + clientName);
							    	   System.out.println("Surname " + studentName);
							    	   
								        ModelAndView data = new ModelAndView("unsuccessfulPayment.jsp"); 
								        data.addObject("name", clientName);
								        data.addObject("email", studentName);
							    
								        
								          return data;
							    	   
							    }
							    
							    
							               /* Successful Payment*/
							    
							    @GetMapping("/success-link")
							    public String handlePaymentLinkSuccess(@RequestParam Map<String, String> paymentDetails , HttpSession session) {
							    	
							    	
							    	   String details = paymentDetails.get("details");
							    	   
							    	   session.setAttribute("paymentDetailsSuccess", details);
							    	   
							    	   return "redirect:/successful-payment";
							    	   
							    }
							    
							    
							    @GetMapping("/successful-payment")
							    public ModelAndView handlePaymentLinkSuccessful(HttpSession session) {
							    
							    	   String details = (String)session.getAttribute("paymentDetailsSuccess");
							    	   
							    	   String[] clientDetails = details.split("_");
							    	   
							    	   String clientName = clientDetails[0];
							    		clientName = clientName.replace("@", " ");
							    			
							    	   String studentName = clientDetails[1];
							    	   studentName = studentName.replace("@", " ");
							    	   
							    	   String amount = clientDetails[2];
							    	   String sess = clientDetails[3];
							    	   String tutorOpt = clientDetails[4];
							    	   String email = clientDetails[5];
							    	   
							    	   PaidBookings paid = new PaidBookings(clientName,studentName,amount,sess,email,tutorOpt);
							    	   paidService.save(paid);
							    	   
							    	   System.out.println("Name " + clientName);
							    	   System.out.println("Surname " + studentName);
							    	   
							    	   /*Save to database*/
							    	   
								        ModelAndView data = new ModelAndView("successPayment.jsp"); 
								        data.addObject("name", clientName);
								        data.addObject("email", studentName);
							    
								        
								        return data;
							    	   
							    }
							 
							 
							 
							 
							 
							 
							 
				 }

