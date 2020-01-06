package me.nexters.doctor24.medical.hospital.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;

public interface HospitalRepository extends MongoRepository<Hospital, String> {
}
