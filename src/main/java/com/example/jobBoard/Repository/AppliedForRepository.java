package com.example.jobBoard.Repository;


import com.example.jobBoard.Model.AppliedFor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppliedForRepository extends JpaRepository<AppliedFor,Long> {
    @Query(value = "Select * from applied_for where candidate_id=:userId AND job_id=:jobId",nativeQuery = true)
    AppliedFor applied(@Param("jobId") Long jobId,@Param("userId") Long userId);
}
