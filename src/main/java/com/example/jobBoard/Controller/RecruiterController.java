package com.example.jobBoard.Controller;


import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Dto.RecruiterJobDto;
import com.example.jobBoard.Model.RecruiterJob;
import com.example.jobBoard.Repository.RecruiterJobRepository;
import com.example.jobBoard.Service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/recruiter/")
public class RecruiterController {


    @Autowired
    RecruiterService recruiterService;

    @Autowired
    RecruiterJobRepository recruiterJobRepository;

    @PostMapping("post/job/{userId}")
    public ApiResponse postRecruiterJob(@RequestBody RecruiterJobDto recruiterProfileDTO,@PathVariable("userId") Long id){
        return recruiterService.postJob(recruiterProfileDTO,id);
    }



    @GetMapping("get/job/{userId}")
    public Page<RecruiterJob> getAllJobsOfRecruiterUserId(@PathVariable("userId") Long userId, @RequestParam(defaultValue = "0")int page){

        Pageable pageable = PageRequest.of(page,5);

        return  recruiterJobRepository.findAllJobsByRecruiterCompanyId(userId,pageable);
    }

    @PutMapping("update/job/{jobId}/{userId}")
    public ApiResponse updateRecruiterJob(@PathVariable("jobId") Long jobId,@PathVariable("userId") Long userId,@RequestBody RecruiterJobDto recruiterProfileDTO){
        return recruiterService.updateJob(jobId,recruiterProfileDTO,userId);
    }


    @GetMapping("jobsbycategory")
    public Page<RecruiterJob> getJobsByCategory(@RequestParam Map<String,String> requestParams){
        String category=requestParams.get("category");
        category = category.replaceAll("_and_","&");
        Integer page=Integer.parseInt(requestParams.get("page"));

        Pageable pageable = PageRequest.of(page,5);

        return recruiterJobRepository.findByCategory(category,pageable);
    }


}
