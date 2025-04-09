package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.PaidBookings;

public interface PaidBookingsRepository extends JpaRepository<PaidBookings, Long> {

}
