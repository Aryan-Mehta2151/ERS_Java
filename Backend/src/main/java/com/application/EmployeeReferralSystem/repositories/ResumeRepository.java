package com.application.EmployeeReferralSystem.repositories;

import com.application.EmployeeReferralSystem.models.ResumeData;
import com.application.EmployeeReferralSystem.models.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ResumeRepository extends MongoRepository<ResumeData , String> {


}
