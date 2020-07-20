package com.example.jobBoard.Controller;


import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Dto.JobDto;
import com.example.jobBoard.Model.Job;
import com.example.jobBoard.Repository.JobRepository;
import com.example.jobBoard.Repository.UserDaoRepository;
import com.example.jobBoard.Service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/job")
public class JobController {


    @Autowired
    JobService jobService;

    @Autowired
    JobRepository jobRepository;


    @Autowired
    UserDaoRepository userDaoRepository;

    @PostMapping("/{userId}")
    public ApiResponse postJob(@RequestBody JobDto jobDTO,@PathVariable("userId") Long userId)
    {
        return jobService.postJob(jobDTO,userId);
    }


    @PutMapping("/update/{jobId}")
    public ApiResponse updateJob(@PathVariable("jobId") Long jobId,@PathVariable("userId") Long userId,@RequestBody JobDto jobDTO)
    {
        return jobService.updateJOB(jobId,userId,jobDTO);
    }

    @GetMapping("/myJobs/{userId}")
    public Page<Job> getMyJobs(@PathVariable("userId") Long id,@RequestParam(defaultValue ="0")int page)
    {
        Pageable pageable = PageRequest.of(page,5);
        return jobRepository.findJobsByCompanyPaginated(id,pageable);

    }



}
