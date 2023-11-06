package com.example.festival.payment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.festival.image.Image;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {
    
    @Query("{ 'vnp_TxnRef' : ?0 }")
    Optional<Payment> findByVnpTxnRef(String vnpTxnRef);

    @Query("{ 'bookedTour.id' : ?0 }")
    Optional<Payment> findByBookedTourId(String bookedTourId);
}
