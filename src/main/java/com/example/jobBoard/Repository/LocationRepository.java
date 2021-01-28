package com.example.jobBoard.Repository;

import com.example.jobBoard.Dto.RidersLocationDTO;
import com.example.jobBoard.Model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {


    @Transactional
    @Modifying
    @Query(value = "update location set longitude=:longitude, latitude=:latitude where user_id=:id", nativeQuery = true)
    void setLongitudeAndLatitude(@Param("id") Long id,@Param("longitude") Double Longitude,@Param("latitude") Double latitude);

    @Query(value ="select * from location where user_id=:userId" ,nativeQuery = true)
    Location findLocationByUserId(@Param("userId") Long userId);

//    @Query(value="SELECT new com.example.jobBoard.Dto.RidersLocationDTO(l.id,l.longitude,l.latitude)"+
//            "from Location l INNER JOIN User u ON (l.user.id=u.id)"+
//            "where u.userType='Rider'")
//    List<RidersLocationDTO> getAllRidersLocation();


   
}
