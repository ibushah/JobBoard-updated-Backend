package com.example.jobBoard.Repository;

import com.example.jobBoard.Dto.UserDto;
import com.example.jobBoard.Model.Job;
import com.example.jobBoard.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//import java.awt.print.Pageable;
import javax.transaction.Transactional;
import java.util.List;

public interface JobRepository  extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {

    @Query(value = "select * from job where user_id=:id", nativeQuery = true)
    List<Job> findByUserId(@Param("id") Long id);

    @Query(value = "select * from job where user_id=:id", nativeQuery = true)
    Page<Job> findJobsByCompanyPaginated(@Param("id") Long id, Pageable pageable);


    @Query(value = "select * from job where category=:cat", nativeQuery = true)
    Page<Job> findByCategory(@Param("cat") String category, Pageable p);



    @Modifying
    @Transactional
    @Query(value = "DELETE from applied_for where job_id=:id",nativeQuery = true)
    void deleteAssociatedRecords(@Param("id") Long id);

    @Query(value = "select * from job where category=:cat AND user_id=:id", nativeQuery = true)
    Page<Job> findByCategoryAndCompanyId(@Param("cat") String category, @Param("id") Long id, Pageable p);

    @Query(value = "SELECT new com.example.jobBoard.Dto.UserDto(user.id,user.name,user.email,user.userType,user.profileActive)FROM User user\n" +
            "INNER JOIN AppliedFor ap \n" +
            "ON user.id = ap.candidateProfile.id where ap.job.id=:id")
    List<UserDto> findAllAppliedUser(@Param("id") Long id);
}
