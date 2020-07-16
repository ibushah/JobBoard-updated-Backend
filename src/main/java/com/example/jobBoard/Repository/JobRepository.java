package com.example.jobBoard.Repository;

import com.example.jobBoard.Model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository  extends JpaRepository<Job, Long> {

    @Query(value = "select * from job where user_id=:id", nativeQuery = true)
    List<Job> findByUserId(@Param("id") Long id);
}
