package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.PaidBookings;
import com.example.demo.repository.PaidBookingsRepository;

@Service
public class PaidBookingsService {
	
	@Autowired
	private PaidBookingsRepository repo;
	
	public List<PaidBookings> listAll(){
		
		 return repo.findAll();
	}
	
	public void save(PaidBookings msc) {
		
		  repo.save(msc);
	}

}
