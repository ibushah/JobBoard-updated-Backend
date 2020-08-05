package com.example.jobBoard.Service;

import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Dto.ProfileDto;
import com.example.jobBoard.Model.AppliedFor;
import com.example.jobBoard.Model.Profile;
import com.example.jobBoard.Model.User;
import com.example.jobBoard.Repository.AppliedForRepository;
import com.example.jobBoard.Repository.ProfileRepository;
import com.example.jobBoard.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CandidateProfileService {

    @Autowired
    UserDaoRepository userDaoRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    AppliedForRepository appliedForRepository;

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
            user.setProfileActive(true);
            userDaoRepository.save(user);
            User newUser=userDaoRepository.findById(userId).isPresent()?userDaoRepository.findById(userId).get():null;

            Profile candidateProfile=profileRepository.findByUserId(userId)!=null?profileRepository.findByUserId(userId):new Profile();
            candidateProfile.setPresentationLetter(candidateProfileDTO.getPresentationLetter());
            candidateProfile.setField(candidateProfileDTO.getField());
            candidateProfile.setResume(candidateProfileDTO.getResume());
            candidateProfile.setDp(candidateProfileDTO.getDp());
            candidateProfile.setUserType("candidate");
            candidateProfile.setAvgRating(0.0);
            candidateProfile.setResumeContentType(candidateProfileDTO.getResumeContentType());
            candidateProfile.setDpContentType(candidateProfileDTO.getDpContentType());
            candidateProfile.setUser(newUser);
            profileRepository.save(candidateProfile);
            return new ApiResponse(200,"Candidate profile successfuly updated",userDaoRepository.findById(userId).get());
        }

        return  new ApiResponse(500,"Something went wrong",null);


    }




    public ApiResponse getAlreadyAppliedJobs(Long userId,Long jobId){

        Optional<User> user = userDaoRepository.findById(userId);

        if (user != null && user.get().getUserType().equalsIgnoreCase("candidate")) {

            try{
                AppliedFor appliedFor= appliedForRepository.applied(jobId,userId);
                if(appliedFor!=null)
                {
                    return  new ApiResponse(200,"Already appied",true);
                }
                return  new ApiResponse(500,"Not applied on job",false);

            }
            catch (Exception e){
                return  new ApiResponse(500,"Something went wrong",false);
            }
        }
        return  new ApiResponse(500,"Something went wrong",false);

    }
}
