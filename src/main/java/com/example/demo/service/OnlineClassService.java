package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.model.OnlineClass;

import com.example.demo.repository.OnlineClassRepository;


@Service
public class OnlineClassService {
	
	@Autowired
	private OnlineClassRepository repo;
	
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