package com.example.jobBoard.Service;


import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Dto.NotificationsDTO;
import com.example.jobBoard.Model.User;
import com.example.jobBoard.Repository.NotificationRepository;
import com.example.jobBoard.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationsRepository;

    @Autowired
    UserDaoRepository userDaoRepository;

    public Page<NotificationsDTO> getNotificationsForUser(Long userId,int page){
            Pageable pageable = PageRequest.of(page,5);
            try{
                String userType = userDaoRepository.findById(userId).get().getUserType();
                if(userType.equalsIgnoreCase("employer") || userType.equalsIgnoreCase("recruiter")){

                    Page<NotificationsDTO> notificationsDTOPage = notificationsRepository.getNotificationsPublic(userId,"notcandidate","public",pageable);
                    return  notificationsDTOPage;
                }
                else{
                    Page<NotificationsDTO> notificationsDTOPage = notificationsRepository.getNotificationsPrivate(userId,"candidate","private",pageable);
                    return  notificationsDTOPage;
                }
            }catch (Exception e){
                return null;
            }
    }
}
