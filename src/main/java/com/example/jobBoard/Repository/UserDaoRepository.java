package com.example.jobBoard.Repository;

import com.example.jobBoard.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDaoRepository extends JpaRepository<User, Long> {

    User findByEmail(String username);
    @Query(value = "select * from user where active=true",nativeQuery = true)
    public List<User> findByActive();

    public User findByEmailAndActive(String email,Boolean active);

    public User findByEmailAndUserType(String email,String userType);
}
