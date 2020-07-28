package com.example.jobBoard.Controller;


import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Dto.ProfileDto;
import com.example.jobBoard.Service.CandidateProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/cp")
public class CandidateProfileController {

    @Autowired
    CandidateProfileService candidateProfileService;

    @PostMapping("/{userid}")
    public ApiResponse postCandidateProfile(@PathVariable("userid") Long userId, @RequestBody ProfileDto profileDto) {
    return candidateProfileService.postCandidateProfile(userId,profileDto);
    }

    @GetMapping("/alreadyappliedjob")
    public ApiResponse getAlreadyAppliedJobs(@RequestParam Map<String,String> requestParams){
        Long jobId = Long.parseLong(requestParams.get("jobId"));
        Long userId = Long.parseLong(requestParams.get("userId"));
        return candidateProfileService.getAlreadyAppliedJobs(userId,jobId);
    }

}
