package com.example.jobBoard.Repository;


import com.example.jobBoard.Model.RecruiterJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruiterJobRepository extends JpaRepository<RecruiterJob,Long> {
    @Query(value = "select * from recruiter_jobs where user_id=:id",nativeQuery = true)
    Page<RecruiterJob> findAllJobsByRecruiterCompanyId(@Param("id") Long id, Pageable pageable);

//    @Query(value = "select ")

    @Query(value = "select * from recruiter_jobs where category=:cat",nativeQuery = true)
    Page<RecruiterJob> findByCategory(@Param("cat") String category, Pageable pageable);
}
