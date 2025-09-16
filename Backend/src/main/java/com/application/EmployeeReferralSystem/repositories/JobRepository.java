package com.application.EmployeeReferralSystem.repositories;

import com.application.EmployeeReferralSystem.models.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface JobRepository extends MongoRepository<Job, String> {
    List<Job> findByPostedBy_Id(String hrId);
    List<Job> findAll(); // Fetch all jobs
}
