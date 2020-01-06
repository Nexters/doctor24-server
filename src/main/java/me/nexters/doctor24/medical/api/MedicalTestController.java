package me.nexters.doctor24.medical.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;
import me.nexters.doctor24.medical.hospital.repository.HospitalRepository;

// TODO 테스트 용 나중에 지울 것임

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/medicals")
public class MedicalTestController {
	private final HospitalRepository hospitalRepository;

	@GetMapping
	public ResponseEntity<List<Hospital>> findAll() {
		return ResponseEntity.ok(hospitalRepository.findAll());
	}
}
