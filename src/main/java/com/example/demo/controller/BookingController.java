package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.model.Booking;
import com.example.demo.service.BookingService;
import com.example.demo.service.EmailSenderService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BookingController {
	
	 @Autowired
	 private BookingService bookingService;
	 
	 @Autowired
	 private EmailSenderService senderService;
	
		
	/*Start Here*/
    @GetMapping("/booking-details")
    public String getBookingDetails(@RequestParam("id") Long id, Model model) {
    	
        Booking booking = bookingService.findOneBook(id);  

	     model.addAttribute("book", booking);

	     return "bookingDetails";

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
	 

}
