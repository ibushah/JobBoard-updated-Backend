package com.example.jobBoard.Service;


import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Dto.ReviewAndRatingDTO;
import com.example.jobBoard.Model.AppliedFor;
import com.example.jobBoard.Model.Job;
import com.example.jobBoard.Model.Notifications;
import com.example.jobBoard.Model.User;
import com.example.jobBoard.Repository.AppliedForRepository;
import com.example.jobBoard.Repository.JobRepository;
import com.example.jobBoard.Repository.NotificationRepository;
import com.example.jobBoard.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AppliedForService {
    @Autowired
    AppliedForRepository appliedForRepository;

    @Autowired
    UserDaoRepository userDaoRepository;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    NotificationRepository notificationsRepository;

    public ApiResponse applyOnJob(ReviewAndRatingDTO reviewAndRatingDTO) {

        Optional < User > candidateProfile = userDaoRepository.findById(reviewAndRatingDTO.getCandidateId());
        Optional < User > companyProfile = userDaoRepository.findById(reviewAndRatingDTO.getCompanyId());
        Optional < Job > job = jobRepository.findById(reviewAndRatingDTO.getJobId());

        if (candidateProfile != null && candidateProfile.get().getUserType().equalsIgnoreCase("candidate") && candidateProfile.get().getProfile() != null) {

            AppliedFor appliedForPresent = appliedForRepository.applied(reviewAndRatingDTO.getJobId(), reviewAndRatingDTO.getCandidateId());
            if (appliedForPresent == null) {
                AppliedFor appliedFor = new AppliedFor(job.get(), candidateProfile.get(), companyProfile.get(), false, new Date());
                Notifications notifications = new Notifications();
                notifications.setNotificationByUser(candidateProfile.get());
                notifications.setNotificationForUser(companyProfile.get());
                notifications.setJob(job.get());
                notifications.setNotificateFor("notcandidate");
                notifications.setNotificationDate(new Date());
                notifications.setSeenOrNot(false);
                notifications.setTypeOfJob("public");
                notificationsRepository.save(notifications);
                appliedForRepository.save(appliedFor);

                return new ApiResponse(200, "You have successfully applied for the job",appliedFor);
            }

            else {
                return new ApiResponse(400, "You have already applied for this job", null);
            }
        }


        return new ApiResponse(400, "Something went wrong", null);






    }



}
