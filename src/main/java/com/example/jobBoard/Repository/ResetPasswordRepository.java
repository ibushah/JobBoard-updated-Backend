package com.example.jobBoard.Repository;

import com.example.jobBoard.Model.Profile;
import com.example.jobBoard.Model.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {

     ResetPassword findByToken(String token);

}
