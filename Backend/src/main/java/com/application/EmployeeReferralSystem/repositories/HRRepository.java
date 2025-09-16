package com.application.EmployeeReferralSystem.repositories;

import com.application.EmployeeReferralSystem.models.Employee;
import com.application.EmployeeReferralSystem.models.HR;
import com.application.EmployeeReferralSystem.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HRRepository extends MongoRepository<HR, String> {
    Optional<HR> findByUser(User user);
    Optional<HR> findByEmail(String email);

}

