package me.nexters.doctor24.medical.pharmacy.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import me.nexters.doctor24.medical.pharmacy.model.mongo.Pharmacy;

public interface PharmacyRepository extends MongoRepository<Pharmacy, String> {
}
