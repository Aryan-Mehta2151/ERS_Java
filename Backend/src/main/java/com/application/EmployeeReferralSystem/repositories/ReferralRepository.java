package com.application.EmployeeReferralSystem.repositories;

import com.application.EmployeeReferralSystem.models.Referral;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferralRepository extends MongoRepository<Referral, String> {

    List<Referral> findByReferredBy_Id(String employeeId);

    List<Referral> findByApplicant_Id(String applicantId);

    List<Referral> findByJob_JobId(String jobId);

    List<Referral> findByJob_PostedBy_Id(String hrId); // For HR to see referrals to their jobs

    boolean existsByApplicant_IdAndJob_JobIdAndReferredBy_Id(String applicantId, String jobId, String referredById);
}
