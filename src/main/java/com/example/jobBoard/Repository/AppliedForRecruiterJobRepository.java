package com.example.jobBoard.Repository;


import com.example.jobBoard.Model.AppliedForRecruiterJob;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppliedForRecruiterJobRepository extends JpaRepository<AppliedForRecruiterJob,Long>
{
    @Query(value = "select * from applied_or_refered_recruiter_jobs where " +
            "(candidate_id=:candId AND company_id=:companyId AND recruiter_job_id=:jobId)",nativeQuery = true)
    AppliedForRecruiterJob alreadyReferedOrNot(@Param("candId") Long candId,
                                               @Param("companyId") Long companyId,@Param("jobId") Long jobId);

    Optional<AppliedForRecruiterJob> findByCandidateProfileIdAndCompanyProfileIdAndRecruiterJobIdAndIsApplied(Long candId, Long compId, Long jobId, Boolean applied);

    @Modifying
    @Transactional
    @Query(value = "DELETE from applied_or_refered_recruiter_jobs where recruiter_job_id=:jobId AND candidate_id=:candidateId",nativeQuery = true)
    void undoRefer(@Param("jobId") Long jobId, @Param("candidateId") Long candidateId);

    @Query(value = "select recruiter_jobs.id from recruiter_jobs  left join\n" +
            "(select * from applied_or_refered_recruiter_jobs where candidate_id =:candId and company_id=:companyId)\n" +
            " ref on recruiter_jobs.id = ref.recruiter_job_id\n" +
            "WHERE ref.recruiter_job_id is NULL",
            nativeQuery = true)
    public List<Long> getDetails(@Param("candId") Long candId, @Param("companyId") Long companyId, Pageable pageable);

    @Query(value = "select  is_applied from applied_or_refered_recruiter_jobs where (candidate_id=:candId AND recruiter_job_id=:jobId)",nativeQuery = true)
    Boolean isAlreadyApplied(@Param("candId") Long candId,
                             @Param("jobId") Long jobId);
}
