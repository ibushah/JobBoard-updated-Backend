package com.example.jobBoard.Controller;


import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Dto.ProfileDto;
import com.example.jobBoard.Service.CompanyProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/companyprofile")
public class CompanyProfileController {

    @Autowired
    CompanyProfileService companyProfileService;

    @PostMapping("/{userId}")
    public ApiResponse postCompanyProfile(@PathVariable(value = "userId",required = false) Long userId, @RequestBody ProfileDto companyProfileDTO) {
        return companyProfileService.postCompanyProfile(userId, companyProfileDTO);
    }
}
