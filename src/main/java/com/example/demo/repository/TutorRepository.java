package com.example.demo.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Projection.TutorProjection;
import com.example.demo.model.Tutor;

public interface TutorRepository extends JpaRepository<Tutor, String>{
		
	@Query("SELECT t FROM Tutor t WHERE t.fullNames LIKE %:name%")
	List<Tutor> searchByName(@Param("name") String name); 
	
    @Query("SELECT t FROM Tutor t WHERE t.area = :location")
    List<Tutor> findTutorsByLocation(@Param("location") String location);
    

    @Query("SELECT t FROM Tutor t WHERE t.subjects LIKE %:subject%")
    Page<Tutor> findTutorsBySubjects(@Param("subject") String subject, Pageable pageable);
    
    Page<TutorProjection> findAllProjectedBy(Pageable pageable);
    		
}