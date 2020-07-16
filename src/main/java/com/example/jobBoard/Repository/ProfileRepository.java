package com.example.jobBoard.Repository;

import com.example.jobBoard.Model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query(value = "Select * from profile where user_id=:id",nativeQuery = true)
    public Profile findByUserId(@Param("id") Long id);
}
