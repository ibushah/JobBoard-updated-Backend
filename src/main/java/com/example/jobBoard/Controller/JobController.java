package com.example.jobBoard.Controller;


import com.example.jobBoard.Repository.JobRepository;
import com.example.jobBoard.Repository.UserDaoRepository;
import com.example.jobBoard.Service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
