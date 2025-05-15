package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.model.OnlineClass;
import com.example.demo.repository.OnlineClassRepository;

import jakarta.annotation.PostConstruct;


@Service
public class OnlineClassService {
	
	@Autowired
	private OnlineClassRepository repo;
	
	private List<OnlineClass> classes;
	
    @PostConstruct
    public void init() {
        // Load data from the database when the application starts
    	classes = repo.findAll();  // Or your custom query
        
        System.out.println("Matric Rewrite Loaded.");
                
    }
    
	public List<OnlineClass> loadedClasses(){
		
		 return classes;
	}
	
	public List<OnlineClass> listAll(){
		
		 return repo.findAll();
	}
	
	public void save(OnlineClass msc) {
		
		  repo.save(msc);
	}
	
	public void delete(OnlineClass msc) {
		
		repo.delete(msc);
		
	}
	
	public OnlineClass findOneBook(Long id) {
		
	 Optional<OnlineClass> onClass = repo.findById(id);
	 
	    OnlineClass oneClass = onClass.get();
	      
	    return oneClass;
	
	}
	
	public void update(Long id) {
	       
		 Optional<OnlineClass> onClass = repo.findById(id);
		 
		 if (onClass.isPresent()){
			 
			 OnlineClass oneClass = onClass.get();

			 oneClass.setAction("Opened");
			  
	            repo.save(oneClass);
	            
        }
	        
	 }

}