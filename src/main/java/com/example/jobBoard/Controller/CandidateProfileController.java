package com.example.jobBoard.Controller;


import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Dto.ProfileDto;
import com.example.jobBoard.Service.CandidateProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
