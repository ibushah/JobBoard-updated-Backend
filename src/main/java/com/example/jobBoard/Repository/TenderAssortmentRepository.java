package com.example.jobBoard.Repository;

//import com.example.jobBoard.Dto.TestNotificationsDTO;
import com.example.jobBoard.Model.TenderAssortments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface TenderAssortmentRepository extends JpaRepository<TenderAssortments,Long> {

    @Query(value = "select * from tender_assortments where recruiter_id=:id && notification_for='recruiter' ORDER BY notification_date DESC",nativeQuery = true)
    List<TenderAssortments> getTenderNotifications(@Param("id") Long id);

    @Query(value = "select * from tender_assortments where employer_id=:id && notification_for='employer' ORDER BY notification_date DESC",nativeQuery = true)
    List<TenderAssortments> getAcceptOrDeclineNotification(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "update tender_assortments set is_applied=:isApplied,notification_from='recruiter',notification_for='employer',is_seen=true where (tender_id=:tenderId AND recruiter_id=:recruiterUserId AND employer_id=:employerUserId)",nativeQuery = true)
    void updateTender(@Param("isApplied") Boolean isApplied,@Param("tenderId") Long tenderId,@Param("recruiterUserId") Long recruiterUserId,@Param("employerUserId") Long employerUserId);

}
