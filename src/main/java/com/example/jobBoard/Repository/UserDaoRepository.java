package com.example.jobBoard.Repository;

import com.example.jobBoard.Dto.RidersLocationDTO;
import com.example.jobBoard.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDaoRepository extends JpaRepository<User, Long> {

    User findByEmail(String username);

    @Query(value = "select * from user where active=true",nativeQuery = true)
    public List<User> findByActive();

    public User findByEmailAndActive(String email,Boolean active);

    public User findByEmailAndUserType(String email,String userType);

    @Query(value = "select * from user u where u.name like %:search%",nativeQuery = true)
    public List<User> searchUser(@Param("search") String search);

    public List<User> findByUserType(String userType);

    @Query(value="SELECT new com.example.jobBoard.Dto.RidersLocationDTO(u.id,u.name,u.email,u.password,u.active,u.userType,u.profileActive,l.longitude,l.latitude,u.userOnlineStatus)"+
         "from User u INNER JOIN Location l ON (l.user.id=u.id)"+
            "where u.userType='Rider'")
    List<RidersLocationDTO> getAllRidersLocation();
}
