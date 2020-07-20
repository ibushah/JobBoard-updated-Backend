package com.example.jobBoard.Controller;


import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Dto.RecruiterJobDto;
import com.example.jobBoard.Service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/recruiter/")
public class RecruiterController {


    @Autowired
    RecruiterService recruiterService;

    @PostMapping("post/job/{userId}")
    public ApiResponse postRecruiterJob(@RequestBody RecruiterJobDto recruiterProfileDTO,@PathVariable("userId") Long id){
        return recruiterService.postJob(recruiterProfileDTO,id);
    }
}
