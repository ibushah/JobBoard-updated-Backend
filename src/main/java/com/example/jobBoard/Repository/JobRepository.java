package com.example.jobBoard.Repository;

import com.example.jobBoard.Model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//import java.awt.print.Pageable;
import java.util.List;

public interface JobRepository  extends JpaRepository<Job, Long> {

    @Query(value = "select * from job where user_id=:id", nativeQuery = true)
    List<Job> findByUserId(@Param("id") Long id);

    @Query(value = "select * from job where user_id=:id", nativeQuery = true)
    Page<Job> findJobsByCompanyPaginated(@Param("id") Long id, Pageable pageable);
}
