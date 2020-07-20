package com.example.jobBoard.Repository;


import com.example.jobBoard.Model.RecruiterJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruiterJobRepository extends JpaRepository<RecruiterJob,Long> {
}
