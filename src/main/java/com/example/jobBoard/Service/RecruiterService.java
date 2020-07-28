package com.example.jobBoard.Service;


import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Dto.AllCandidatesReferedOrNotList;
import com.example.jobBoard.Dto.RecruiterJobDto;
import com.example.jobBoard.Dto.ViewPrivateJobDTO;
import com.example.jobBoard.Model.RecruiterJob;
import com.example.jobBoard.Model.User;
import com.example.jobBoard.Repository.RecruiterJobRepository;
import com.example.jobBoard.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RecruiterService {


    @Autowired
    UserDaoRepository userDaoRepository;

    @Autowired
    RecruiterJobRepository recruiterJobRepository;

    public ApiResponse postJob(RecruiterJobDto jobDTO,Long userId) {

       Optional<User> user = userDaoRepository.findById(userId);

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

    public ApiResponse updateJob(Long jobId,RecruiterJobDto jobDTO,Long userId) {

        Optional<User> user = userDaoRepository.findById(userId);

        if (user != null && user.get().getUserType().equalsIgnoreCase("recruiter") && user.get().getProfile()!=null) {

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
            return new ApiResponse(200, "Recruiter Job successfully updated", recruiterJobRepository.save(job));
        }

        return new ApiResponse(500, "Something went wrong", null);
    }
//    public ApiResponse getAllInfoOfJobIdForRecruiter(Long jobId){
//
//
//        ViewPrivateJobDTO viewPrivateJobDTO = new ViewPrivateJobDTO();
//        Optional<RecruiterJob> recruiterJob = recruiterJobRepository.findById(jobId);
//        viewPrivateJobDTO.setRecruiterJob(recruiterJob.get());
//        List<AllCandidatesReferedOrNotList> allCandidatesReferedOrNotLists = appliedForRecruiterJobRepository.getAllCandidates(jobId);
//        viewPrivateJobDTO.setAllCandidatesReferedOrNotList(allCandidatesReferedOrNotLists);
//        return new ApiResponse(200,"SUCCESSFULL",viewPrivateJobDTO);
//
//    }


}
