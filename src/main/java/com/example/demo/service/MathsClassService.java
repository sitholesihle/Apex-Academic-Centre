package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.MathsClass;
import com.example.demo.repository.MathsClassRepository;

@Service
public class MathsClassService{
	
	@Autowired
	private MathsClassRepository repo;
	
	public List<MathsClass> listAll(){
		
		 return repo.findAll();
	}
	
	
	public void save(MathsClass msc) {
		
		  repo.save(msc);
	}
	
	public void delete(MathsClass msc) {
		
		repo.delete(msc);
		
	}
	
	
	public MathsClass findOneClass(Long id) {
		
		 Optional<MathsClass> onClass = repo.findById(id);
		 
		 MathsClass oneClass = onClass.get();
		      
		    return oneClass;
		
		}
	
	
	public void update(Long id) {
	       
		 Optional<MathsClass> onClass = repo.findById(id);
		 
		 if (onClass.isPresent()){
			 
			 MathsClass oneClass = onClass.get();

			 oneClass.setAction("Opened");
			  
	            repo.save(oneClass);
	            
       }
	        
	 }
}