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
public class CandidateProfileService {

    @Autowired
    UserDaoRepository userDaoRepository;

    @Autowired
    ProfileRepository profileRepository;

    public ApiResponse postCandidateProfile(Long userId, ProfileDto candidateProfileDTO)
    {
        Optional<User> optionalUser =userDaoRepository.findById(userId);

        if(optionalUser.isPresent())
        {
            User user=optionalUser.get();

            if(!user.getProfileActive())
                user.setProfileActive(true);

            user.setEmail(candidateProfileDTO.getEmail());
            user.setName(candidateProfileDTO.getName());
            userDaoRepository.save(user);
            User newUser=userDaoRepository.findById(userId).isPresent()?userDaoRepository.findById(userId).get():null;

            Profile candidateProfile=profileRepository.findByUserId(userId)!=null?profileRepository.findByUserId(userId):new Profile();
            candidateProfile.setPresentationLetter(candidateProfileDTO.getPresentationLetter());
            candidateProfile.setField(candidateProfileDTO.getField());
            candidateProfile.setResume(candidateProfileDTO.getResume());
            candidateProfile.setDp(candidateProfileDTO.getDp());
            candidateProfile.setUserType("candidate");
            candidateProfile.setResumeContentType(candidateProfileDTO.getResumeContentType());
            candidateProfile.setDpContentType(candidateProfileDTO.getDpContentType());
            candidateProfile.setUser(newUser);
            profileRepository.save(candidateProfile);
            return new ApiResponse(200,"Candidate profile successfuly updated",userDaoRepository.findById(userId).get());
        }

        return  new ApiResponse(500,"Something went wrong",null);


    }
}
