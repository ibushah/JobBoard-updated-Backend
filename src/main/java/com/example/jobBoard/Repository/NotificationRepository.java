package com.example.jobBoard.Repository;

import com.example.jobBoard.Dto.NotificationsDTO;
import com.example.jobBoard.Model.Notifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationRepository  extends JpaRepository<Notifications,Long> {

    @Query("Select new com.example.jobBoard.Dto.NotificationsDTO" +
            "(n.job.id,n.job.title,n.notificationByUser.name,n.notificationByUser.profile.dp,n.notificationDate,n.seenOrNot,n.typeOfJob) " +
            "from  Notifications n " +
            "where (n.notificationForUser.id=:id AND n.notificateFor=:notificateFor AND n.typeOfJob=:type) ORDER BY n.notificationDate DESC")
    Page<NotificationsDTO> getNotificationsPublic(@Param("id") Long id,@Param("notificateFor") String notificateFor,@Param("type") String type,Pageable pageable);



    @Query("Select new com.example.jobBoard.Dto.NotificationsDTO" +
            "(n.recruiterJob.id,n.recruiterJob.title,n.notificationByUser.name,n.notificationByUser.profile.dp,n.notificationDate,n.seenOrNot,n.typeOfJob) " +
            "from  Notifications n " +
            "where (n.notificationForUser.id=:id AND n.notificateFor=:notificateFor AND n.typeOfJob=:type) ORDER BY n.notificationDate DESC")
    Page<NotificationsDTO> getNotificationsPrivate(@Param("id") Long id,@Param("notificateFor") String notificateFor,@Param("type") String type,Pageable pageable);



    @Transactional
    @Modifying
    @Query("update Notifications a " +
            "set " +
            "a.seenOrNot = true where a.notificationForUser.id=:id")
    void setAllNoticationsAsRead(@Param("id") Long id);

    @Query(value = "select count(id) from notifications  where notification_for_user=:id AND seen_Or_not=false AND type_of_job=:type",nativeQuery = true)
    Long getNotificationsCount(@Param("id") Long id,@Param("type") String type);

    @Transactional
    @Modifying
    @Query("update Notifications a set a.seenOrNot=true where a.notificationForUser.id=:userId AND a.job.id=:jobId")
    void setSelectedNotificationAsReadPublic(@Param("userId") Long userId,@Param("jobId") Long jobId);


    @Transactional
    @Modifying
    @Query("update Notifications a set a.seenOrNot=true where a.notificationForUser.id=:userId AND a.recruiterJob.id=:jobId")
    void setSelectedNotificationAsReadPrivate(@Param("userId") Long userId,@Param("jobId") Long jobId);



    @Transactional
    @Modifying
    @Query("delete from Notifications a where a.notificationForUser.id=:userId AND a.recruiterJob.id=:jobId")
    void undoNotification(@Param("userId") Long userId,@Param("jobId") Long jobId);



}

