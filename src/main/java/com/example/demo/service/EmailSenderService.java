package com.example.demo.service;

import java.io.UnsupportedEncodingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSenderService {
	
    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail,String subject,String body ) {
    	
    	
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("info@apexacademiccentre.co.za");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Mail Send...");


         }
    
    public void sendEmailWithButton(String toEmail, String subject, String clientName, String student, String date,  String amount, String sessions, String optTutor, int i)  {
    	 	
    	String details = clientName+"_"+student+"_"+amount+"_"+sessions+"_"+optTutor+"_"+toEmail;
    	
    	clientName = clientName.replace("@", " ");
    	student = student.replace("@", " ");
    	
    	
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true); 
            messageHelper.setFrom("accounts@apexacademiccentre.co.za", "Apex Academic Centre");  
            messageHelper.setTo(toEmail);  
            messageHelper.setSubject(subject);
            
            String htmlContent = "";

            if(i == 1) {
            	
                htmlContent = "<html><body>"
                        + "<p>Dear <b>" + clientName+"</b>,</p>"
                        + "<p>We are writing to confirm that the tuition fee for <b>" + student + "</b> is due on <b>" + date+"</b>. Please find the payment details below:</p>"
                        + "<p>Amount: <b>R"+amount + "</b><br>Number of Sessions: <b>" + sessions + "</b><br>Tutoring Option: <b>" + optTutor+ "</b></p>"
                        + "<p>To avoid any delays or penalties, kindly settle the payment by the due date. If you have any queries or concerns, please don't hesitate to contact us.</p>"
                        + "<p>Thank you for your prompt attention to this matter.</p>"
                        + "<p>Kind regards,</p>"
                        + "<p>Apex Academic Centre</p>"
                        + "<p>To proceed with payment click the <b> Pay Now </b> button</p>"
                        + "<a href='https://payment.payfast.io/eng/process?cmd=_paynow&receiver=25220959&amount="+amount+"&item_name=Payment&item_description=Package%20Payment&return_url=https://bookatutorapexacademiccentre.co.za/success-link?details="+details+"&cancel_url=https://bookatutorapexacademiccentre.co.za/cancel-link?details="+details+"'>\n"
                        + ""
                        + "<button style='background-color:#4CAF50;color:white;padding:14px 20px;border:none;border-radius:20px;text-align:center;font-size:16px;'>Pay Now</button>"
                        + "</a>"
                       
                        + "</body></html>";
            	
            }
            else {
            	
                htmlContent = "<html><body>"
                        + "<p>Dear <b>" + clientName+"</b>,</p>"
                        + "<p>We are writing to confirm that your tuition fee is due on <b>" + date+"</b>. Please find the payment details below:</p>"
                        + "<p>Amount: <b>R"+amount + "</b><br>Number of Sessions: <b>" + sessions + "</b><br>Tutoring Option: <b>" + optTutor+ "</b></p>"
                        + "<p>To avoid any delays or penalties, kindly settle the payment by the due date. If you have any queries or concerns, please don't hesitate to contact us.</p>"
                        + "<p>Thank you for your prompt attention to this matter.</p>"
                        + "<p>Kind regards,</p>"
                        + "<p>Apex Academic Centre</p>"
                        + "<p>To proceed with payment click the <b> Pay Now </b> button</p>"
                        + "<a href='https://payment.payfast.io/eng/process?cmd=_paynow&receiver=25220959&amount="+amount+"&item_name=Payment&item_description=Package%20Payment&return_url=https://bookatutorapexacademiccentre.co.za/success-link?details="+details+"&cancel_url=https://bookatutorapexacademiccentre.co.za/cancel-link?details="+details+"'>\n"
                        + ""
                        + "<button style='background-color:#4CAF50;color:white;padding:14px 20px;border:none;border-radius:20px;text-align:center;font-size:16px;'>Pay Now</button>"
                        + "</a>"
                       
                        + "</body></html>";
            	
            }
            

            messageHelper.setText(htmlContent, true);
            mailSender.send(message);  
            System.out.println("Mail Sent...with button");

        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    }
