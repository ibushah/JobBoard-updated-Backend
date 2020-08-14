package com.example.jobBoard.Controller;

import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Dto.NotificationsDTO;
import com.example.jobBoard.Model.User;
import com.example.jobBoard.Repository.NotificationRepository;
import com.example.jobBoard.Repository.UserDaoRepository;
import com.example.jobBoard.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/notifications")
public class NotifcationController {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserDaoRepository userDaoRepository;

    @GetMapping("/all/{userId}")
    public ApiResponse getNotificationsByCompanyId(@PathVariable("userId") Long userId, @RequestParam(defaultValue = "0") int page) {
        Page<NotificationsDTO> notificationsDTOPage;
        notificationsDTOPage= notificationService.getNotificationsForUser(userId,page);
        try{
           return  new ApiResponse(200,"Successfull",notificationsDTOPage);
        }
        catch (Exception e){
            return new ApiResponse(500,"Failed",e);
        }
    }




    @GetMapping("/notifications_read/{userId}")
    public ApiResponse markAllNoticationsRead(@PathVariable("userId") Long userId,@RequestParam(defaultValue = "0") int page) {

        Pageable pageable=  PageRequest.of(page,5);
        notificationRepository.setAllNoticationsAsRead(userId);
        return  new ApiResponse(200,"All notifications read",notificationService.getNotificationsForUser(userId,page));
    }



    @GetMapping("/notification_marked")

    public ApiResponse markSelectNotificationAsRead(@RequestParam(required = true) Map<String,String> requestParms) {

        Long userId=Long.parseLong(requestParms.get("userId"));
        Long jobId=Long.parseLong(requestParms.get("jobId"));
        Integer page = Integer.parseInt(requestParms.get("page"));
        String userType = userDaoRepository.findById(userId).get().getUserType();
        if(userType.equalsIgnoreCase("candidate")){
            notificationRepository.setSelectedNotificationAsReadPrivate(userId,jobId);
        }
        else{
            notificationRepository.setSelectedNotificationAsReadPublic(userId,jobId);
        }

        Pageable pageable=  PageRequest.of(page,5);
        return  new ApiResponse(200,"notifications read",notificationService.getNotificationsForUser(userId,page));
    }


    @GetMapping("/notification_count/{userId}")
    @ResponseBody
    public Long getNotificationsCount(@PathVariable("userId") Long userId) {
        try{
            String type = userDaoRepository.findById(userId).get().getUserType();
            type = type.equalsIgnoreCase("candidate")?"private":"public";
            return notificationRepository.getNotificationsCount(userId,type);
        }
        catch (Exception e){
            return null;
        }

    }




}
