package com.example.jobBoard.Service;


import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Dto.ProfileDto;
import com.example.jobBoard.Model.Profile;
import com.example.jobBoard.Model.User;
import com.example.jobBoard.Repository.ProfileRepository;
import com.example.jobBoard.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyProfileService {

    @Autowired
    ProfileRepository companyProfileRepository;

    @Autowired
    UserDaoRepository userDaoRepository;
    public ApiResponse<String> postCompanyProfile(Long userId, ProfileDto companyProfileDTO) {
        Optional<User> optionalUser = userDaoRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (!user.getProfileActive()) {
                user.setProfileActive(true);
                userDaoRepository.save(user);
            }
            Profile companyProfile = companyProfileRepository.findByUserId(userId) != null ? companyProfileRepository.findByUserId(userId) : new Profile();


            companyProfile.setBillingAddress(companyProfileDTO.getBillingAddress());
            companyProfile.setContactName(companyProfileDTO.getContactName());
            companyProfile.setContactTitle(companyProfileDTO.getContactTitle());
            companyProfile.setCorporateAddress(companyProfileDTO.getCorporateAddress());
            companyProfile.setDp(companyProfileDTO.getDp());
            companyProfile.setAvgRating(0.0);
            companyProfile.setName(companyProfileDTO.getName());
            companyProfile.setDpContentType(companyProfileDTO.getDpContentType());
            companyProfile.setUser(user);
            if(user.getUserType().equalsIgnoreCase("recruiter")){
                companyProfile.setResume(companyProfileDTO.getResume());
                companyProfile.setCertificate(companyProfileDTO.getCertificate());
                companyProfile.setResumeContentType(companyProfileDTO.getResumeContentType());
                companyProfile.setCertificateContentType(companyProfileDTO.getCertificateContentType());
            }

            companyProfileRepository.save(companyProfile);
            return new ApiResponse(200, "Company profile successfuly updated", companyProfileRepository.findByUserId(userId));

        }

        return new ApiResponse(500, "Something went wrong", null);

    }
}
