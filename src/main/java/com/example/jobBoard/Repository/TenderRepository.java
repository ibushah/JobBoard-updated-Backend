package com.example.jobBoard.Repository;

import com.example.jobBoard.Model.Tender;
import com.example.jobBoard.Model.TenderAssortments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TenderRepository extends JpaRepository<Tender,Long> {

    @Query(value = "select * from tender where id=:id",nativeQuery = true)
    Tender findTender(@Param("id") Long id);

    @Query(value ="select * from tender where (tender_poster_id=:id)" ,nativeQuery = true)
    List<Tender> getTenders(@Param("id") Long id);

    @Query(value ="select * from tender where tender_type=:tenderType" ,nativeQuery = true)
    List<Tender> getAllPublicTendersForRecruiter(@Param("tenderType") String tenderType);





}
