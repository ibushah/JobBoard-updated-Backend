package com.example.jobBoard.Service;

import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Dto.JobDto;
import com.example.jobBoard.Model.Job;
import com.example.jobBoard.Model.User;
import com.example.jobBoard.Repository.JobRepository;
import com.example.jobBoard.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {


    @Autowired
    JobRepository jobRepository;

    @Autowired
    UserDaoRepository userDaoRepository;

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }


    public ApiResponse postJob(JobDto jobDTO, Long userId) {


        Optional<User> user = userDaoRepository.findById(userId);


        if (user.isPresent() && (user.get().getUserType().equalsIgnoreCase("recruiter"))
                || (!user.get().getUserType().equalsIgnoreCase("candidate") && user.get().getProfileActive())) {

            Job job = new Job();

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
            job.setJobPostPermission(true);
            return new ApiResponse(200, "Job successfully posted", jobRepository.save(job));
        }

        return new ApiResponse(500, "Something went wrong", null);
    }



    public ApiResponse updateJOB(Long jobId,Long userId,JobDto jobDTO) {

       Optional<User> user = userDaoRepository.findById(userId);
        Optional<Job> jobOptional = jobRepository.findById(jobId);

//        job post for company
        if (user.isPresent() && (user.get().getUserType().equalsIgnoreCase("recruiter"))
                || (!user.get().getUserType().equalsIgnoreCase("candidate") && user.get().getProfileActive())) {

            if(jobOptional.isPresent()) {


                jobOptional.get().setDescription(jobDTO.getDescription());
                jobOptional.get().setSalary(jobDTO.getSalary());
                jobOptional.get().setLatitude(jobDTO.getLatitude());
                jobOptional.get().setLongitude(jobDTO.getLongitude());
                jobOptional.get().setTitle(jobDTO.getTitle());
                jobOptional.get().setCity(jobDTO.getCity());
                jobOptional.get().setCountry(jobDTO.getCountry());
                jobOptional.get().setProvince(jobDTO.getProvince());
                jobOptional.get().setCategory(jobDTO.getCategory());
                jobOptional.get().setType(jobDTO.getType());
                jobOptional.get().setPublishFrom(jobDTO.getPublishFrom());
                jobOptional.get().setAddress(jobDTO.getAddress());
                jobOptional.get().setPublishTo(jobDTO.getPublishTo());
//                jobOptional.get().setUser(user.get());
                jobOptional.get().setDate(new Date());
                jobOptional.get().setJobPostPermission(true);
                return new ApiResponse(200, "Job Updated posted", jobRepository.save(jobOptional.get()));
            }
        }



        return new ApiResponse(500, "Something went wrong", null);
    }

    public  List<Job> getJobsByCompany(Long jobId){

        Optional<Job> job=jobRepository.findById(jobId);
        if(job.isPresent())
        {
            return jobRepository.findByUserId(job.get().getUser().getId());
        }
        return null;

    }



}
