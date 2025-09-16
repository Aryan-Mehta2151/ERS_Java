package com.application.EmployeeReferralSystem.repositories;


import com.application.EmployeeReferralSystem.models.Applicant;
import com.application.EmployeeReferralSystem.models.Employee;
import com.application.EmployeeReferralSystem.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicantRepository extends MongoRepository<Applicant, String> {
    Optional<Applicant> findByUser(User user);
}
