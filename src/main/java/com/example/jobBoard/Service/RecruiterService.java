package com.example.jobBoard.Service;


import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Dto.ApplyOrReferOnPrivateJobDTO;
import com.example.jobBoard.Dto.RecruiterJobDto;
import com.example.jobBoard.Model.*;
import com.example.jobBoard.Repository.AppliedForRecruiterJobRepository;
import com.example.jobBoard.Repository.NotificationRepository;
import com.example.jobBoard.Repository.RecruiterJobRepository;
import com.example.jobBoard.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class RecruiterService {


    @Autowired
    UserDaoRepository userDaoRepository;

    @Autowired
    RecruiterJobRepository recruiterJobRepository;

    @Autowired
    AppliedForRecruiterJobRepository appliedForRecruiterJobRepository;

    @Autowired
    NotificationRepository notificationRepository;

    public ApiResponse postJob(RecruiterJobDto jobDTO, Long userId) {

        Optional < User > user = userDaoRepository.findById(userId);

        if (user != null && user.get().getUserType().equalsIgnoreCase("recruiter")) {

            RecruiterJob job = new RecruiterJob();
            job.setDescription(jobDTO.getDescription());
            job.setSalary(jobDTO.getSalary());
            job.setLatitude(jobDTO.getLatitude());
            job.setLongitude(jobDTO.getLongitude());
            job.setTitle(jobDTO.getTitle());
            job.setCity(jobDTO.getCity());
            job.setCountry(jobDTO.getCountry());
            job.setProvince(jobDTO.getProvince());
            job.setCategory(jobDTO.getCategory());
            job.setType(jobDTO.getType());
            job.setPublishFrom(jobDTO.getPublishFrom());
            job.setAddress(jobDTO.getAddress());
            job.setPublishTo(jobDTO.getPublishTo());
            job.setUser(user.get());
            job.setDate(new Date());
            return new ApiResponse(200, "Recruiter Job successfully posted", recruiterJobRepository.save(job));
        }

        return new ApiResponse(500, "Something went wrong", null);
    }

    public ApiResponse updateJob(Long userId, RecruiterJobDto jobDTO, Long jobId) {

        Optional < User > user = userDaoRepository.findById(userId);

        if (user != null && user.get().getUserType().equalsIgnoreCase("recruiter") && user.get().getProfile() != null) {

            RecruiterJob job = recruiterJobRepository.findById(jobId).get();
            job.setDescription(jobDTO.getDescription());
            job.setSalary(jobDTO.getSalary());
            job.setLatitude(jobDTO.getLatitude());
            job.setLongitude(jobDTO.getLongitude());
            job.setTitle(jobDTO.getTitle());
            job.setCity(jobDTO.getCity());
            job.setCountry(jobDTO.getCountry());
            job.setProvince(jobDTO.getProvince());
            job.setCategory(jobDTO.getCategory());
            job.setType(jobDTO.getType());
            job.setPublishFrom(jobDTO.getPublishFrom());
            job.setAddress(jobDTO.getAddress());
            job.setPublishTo(jobDTO.getPublishTo());
            job.setUser(user.get());
            job.setDate(new Date());
            return new ApiResponse(200, "Recruiter Job successfully updated", recruiterJobRepository.save(job));
        }

        return new ApiResponse(500, "Something went wrong", null);
    }

    public ApiResponse referJob(ApplyOrReferOnPrivateJobDTO referJobToCandidateDTO) {



        Optional < User > companyProfile = userDaoRepository
                .findById(referJobToCandidateDTO.getCompanyId());

        Optional < RecruiterJob > recruiterJobs = recruiterJobRepository
                .findById(referJobToCandidateDTO.getJobId());

        Optional <User> candidateProfile = userDaoRepository.findById(referJobToCandidateDTO.getCandidateId());

        if (companyProfile.isPresent() && recruiterJobs.isPresent()) {
            AppliedForRecruiterJob appliedForRecruiterJob = new AppliedForRecruiterJob();
            appliedForRecruiterJob.setCompanyProfile(companyProfile.get());
            appliedForRecruiterJob.setRecruiterJob(recruiterJobs.get());

            appliedForRecruiterJob.setCandidateProfile(userDaoRepository
                    .findById(referJobToCandidateDTO.getCandidateId()).get());

            appliedForRecruiterJob.setSeenOrNot(false);
            appliedForRecruiterJob.setApplied(false);
            appliedForRecruiterJob.setAppliedDate(null);
            appliedForRecruiterJob.setReferedDate(new Date());

            Notifications notifications = new Notifications();
            notifications.setNotificationByUser(companyProfile.get());
            notifications.setNotificationForUser(candidateProfile.get());
            notifications.setRecruiterJob(recruiterJobs.get());
            notifications.setNotificateFor("candidate");
            notifications.setNotificationDate(new Date());
            notifications.setSeenOrNot(false);
            notifications.setTypeOfJob("private");
            notificationRepository.save(notifications);

            appliedForRecruiterJobRepository.save(appliedForRecruiterJob);


            return new ApiResponse(200, "SuccessFully refered", true);
        }
        return new ApiResponse(500, "ERROR OCCURED", null);

    }

    public ApiResponse applyOnPrivateJob(ApplyOrReferOnPrivateJobDTO applyOrReferOnPrivateJobDTO) {

        AppliedForRecruiterJob appliedForRecruiterJob =
                appliedForRecruiterJobRepository
                .alreadyReferedOrNot(applyOrReferOnPrivateJobDTO.getCandidateId(), applyOrReferOnPrivateJobDTO.getCompanyId(), applyOrReferOnPrivateJobDTO.getJobId());

        if (appliedForRecruiterJob != null) {
            appliedForRecruiterJob.setApplied(true);
            appliedForRecruiterJob.setAppliedDate(new Date());
            appliedForRecruiterJobRepository.save(appliedForRecruiterJob);
            return new ApiResponse(200, "Successfull", appliedForRecruiterJob);
        } else {
            return new ApiResponse(500, "Unsuccessfull", null);
        }
    }


    public ApiResponse undoRefered(Long jobId, Long candidateId) {
        appliedForRecruiterJobRepository.undoRefer(jobId, candidateId);
        notificationRepository.undoNotification(jobId,candidateId);
        return new ApiResponse(200, "Successfull", "ok");
    }

//    public ApiResponse getReferedList(Long jobId){
//        Optional<RecruiterJob> recruiterJob = recruiterJobRepository.findById(jobId);
//
//
//    }






}