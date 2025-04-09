package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.example.demo.model.Tutor;
import com.example.demo.repository.TutorRepository;

import jakarta.annotation.PostConstruct;


@Service
public class TutorService {
	
	@Autowired
	private TutorRepository repo;
	
	private List<Tutor> tutors;
	
    @PostConstruct
    public void init() {
        // Load data from the database when the application starts
        tutors = repo.findAll();  // Or your custom query
        
        tutors = tutors.stream()
                .sorted((tutor1, tutor2) -> Integer.compare(tutor2.getRatings(), tutor1.getRatings()))
                .collect(Collectors.toList());
        
        for (Tutor tutor : tutors) {
            if (tutor.getImage() != null) {
                tutor.setImageBase64("jpeg");  // Set the Base64 string in the tutor object
            }
        }
        
        System.out.println("Tutor data preloaded at startup!");
                
    }
    
    public List<Tutor> getAllTutors() {
        return tutors;
    }
    	
	/*TUTORS BY LOCATION*/
	 
	 public Page<Tutor> paginateTutorsByLocation(List<Tutor> tutors, String location, int currentPage) {
		    int pageSize = 10; // Number of tutors per page

		    // Filter the list by subject
		    List<Tutor> filteredTutors = tutors.stream()
		            .filter(tutor -> tutor.getArea().contains(location))
		            .collect(Collectors.toList());

		    // Calculate pagination indices
		    int fromIndex = Math.min((currentPage - 1) * pageSize, filteredTutors.size());
		    int toIndex = Math.min(fromIndex + pageSize, filteredTutors.size());

		    // Sublist to create a page of tutors
		    List<Tutor> pageOfTutors = filteredTutors.subList(fromIndex, toIndex);

		    // Return a Page object with the sublist
		    return new PageImpl<>(pageOfTutors, PageRequest.of(currentPage - 1, pageSize), filteredTutors.size());
		}
		
	 /*TUTORS BY SUBJECT*/

	 public Page<Tutor> paginateTutorsBySubject(List<Tutor> tutors, String subject, int currentPage) {
		    int pageSize = 10; // Number of tutors per page

		    // Filter the list by subject
		    List<Tutor> filteredTutors = tutors.stream()
		            .filter(tutor -> tutor.getSubjects().contains(subject))
		            .collect(Collectors.toList());

		    // Calculate pagination indices
		    int fromIndex = Math.min((currentPage - 1) * pageSize, filteredTutors.size());
		    int toIndex = Math.min(fromIndex + pageSize, filteredTutors.size());

		    // Sublist to create a page of tutors
		    List<Tutor> pageOfTutors = filteredTutors.subList(fromIndex, toIndex);

		    // Return a Page object with the sublist
		    return new PageImpl<>(pageOfTutors, PageRequest.of(currentPage - 1, pageSize), filteredTutors.size());
		}
	 
	 /*TUTORS BY SYLLABUS*/

	 public Page<Tutor> paginateTutorsBySyllabus(List<Tutor> filteredTutors,  int currentPage) {
		    int pageSize = 10; // Number of tutors per page

		    // Calculate pagination indices
		    int fromIndex = Math.min((currentPage - 1) * pageSize, filteredTutors.size());
		    int toIndex = Math.min(fromIndex + pageSize, filteredTutors.size());

		    // Sublist to create a page of tutors
		    List<Tutor> pageOfTutors = filteredTutors.subList(fromIndex, toIndex);

		    // Return a Page object with the sublist
		    return new PageImpl<>(pageOfTutors, PageRequest.of(currentPage - 1, pageSize), filteredTutors.size());
		}
	 
	 /*VIEW PROFILE*/
	 
	    public List<Tutor> viewProfile(List<Tutor> filteredTutors) {
	        
	        // Pick the first 5 tutors after shuffling
	        return filteredTutors.subList(0, Math.min(5, filteredTutors.size()));
	    }

	 /*VIEW PROFILE*/
	 
	
	
	
	
	
	
	
	    public List<Tutor> listAll() {
	        return tutors;
	    }

	
	
	public List<Tutor> getRandomTutors() {
	    // Get all tutors
	    List<Tutor> allTutors = repo.findAll();

	    // Return the first 8 tutors (or fewer if there are not enough)
	    return allTutors.subList(0, Math.min(5, allTutors.size()));
	    
	}
	
	public void save(Tutor msc) {
		
		  repo.save(msc);
	}
	
	public void delete(Tutor msc) {
		
		repo.delete(msc);
		
	}
	
	public void update(String id , Tutor updatedTutor) {
	       
		 Optional<Tutor> tutor = repo.findById(updatedTutor.getEmail());
		 
		 if (tutor.isPresent()){
			 
			 Tutor existingTutor = tutor.get();

			    existingTutor.setFullNames(updatedTutor.getFullNames());
			    existingTutor.setImage(updatedTutor.getImage());
			    existingTutor.setPhoneNumber(updatedTutor.getPhoneNumber());
			    existingTutor.setEmail(updatedTutor.getEmail());
			    existingTutor.setAddress(updatedTutor.getAddress());
			    existingTutor.setSubjects(updatedTutor.getSubjects());
			    existingTutor.setGrades(updatedTutor.getGrades());
			    existingTutor.setSyllabus(updatedTutor.getSyllabus());
			    existingTutor.setAvailability(updatedTutor.getAvailability());
			    existingTutor.setBio(updatedTutor.getBio());
			    existingTutor.setAbout(updatedTutor.getAbout());
			    existingTutor.setHoursTutored(updatedTutor.getHoursTutored());
			    existingTutor.setBackground("Yes");
			    existingTutor.setArea(updatedTutor.getArea());
			    existingTutor.setCountry(updatedTutor.getCountry());

	            // Save the updated student object
	            repo.save(existingTutor);
	            
         }
	        
	 }
	
	public void updateRatings(String email , int rate) {
		
		String noSpaces = email.replaceAll("\\s+", "");
		
		 Optional<Tutor> tutor = repo.findById(noSpaces);
		 
		 if (tutor.isPresent()){
			 
			 Tutor existingTutor = tutor.get();
			 
			 int ratings = existingTutor.getRatings();
			   ratings += 0;
			 
			 existingTutor.setRatings(ratings);
			 			 
			 repo.save(existingTutor);
			 
		 }
		
	}

	public Page<Tutor> findPage(List<Tutor> tutorProjections, int pageNumber, int pageSize) {
	    // Calculate the start index for the pagination
	    int start = (pageNumber - 1) * pageSize;
	    int end = Math.min((start + pageSize), tutorProjections.size());

	    // Create a sublist to return the paginated results
	    List<Tutor> pagedList = tutorProjections.subList(start, end);

	    // Create a Pageable object based on the pageNumber and pageSize
	    Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

	    // Create and return a Page object
	    return new PageImpl<>(pagedList, pageable, tutorProjections.size());
	}
	

	 
	 


	
	
		
}