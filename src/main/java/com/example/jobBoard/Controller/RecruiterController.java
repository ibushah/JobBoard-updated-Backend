package com.example.jobBoard.Controller;


import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Dto.ApplyOrReferOnPrivateJobDTO;
import com.example.jobBoard.Dto.RecruiterJobDto;
import com.example.jobBoard.Dto.SearchUserDTO;
import com.example.jobBoard.Model.Job;
import com.example.jobBoard.Model.RecruiterJob;
import com.example.jobBoard.Model.User;
import com.example.jobBoard.Repository.AppliedForRecruiterJobRepository;
import com.example.jobBoard.Repository.RecruiterJobRepository;
import com.example.jobBoard.Repository.UserDaoRepository;
import com.example.jobBoard.Service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/recruiter/")
public class RecruiterController {


    @Autowired
    UserDaoRepository userDaoRepository;


    @Autowired
    RecruiterService recruiterService;

    @Autowired
    RecruiterJobRepository recruiterJobRepository;

    @Autowired
    AppliedForRecruiterJobRepository appliedForRecruiterJobRepository;

    @PostMapping("post/job/{userId}")
    public ApiResponse postRecruiterJob(@RequestBody RecruiterJobDto recruiterProfileDTO,@PathVariable("userId") Long id){
        return recruiterService.postJob(recruiterProfileDTO,id);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<RecruiterJob> getJobById(@NotNull @Valid @PathVariable("jobId") Long jobId){
        try{
            Optional<RecruiterJob> job = recruiterJobRepository.findById(jobId);
            return new ResponseEntity<RecruiterJob>(job.get(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<RecruiterJob>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/alreadyApplied/{userId}/{jobId}")
    public Boolean alreadyApplied(@NotNull @Valid @PathVariable("userId") Long userId,@NotNull @Valid @PathVariable("jobId") Long jobId){
            Boolean alreadyApplied = appliedForRecruiterJobRepository.isAlreadyApplied(userId,jobId);
            return  alreadyApplied;
    }



    @GetMapping("get/job/{userId}")
    public Page<RecruiterJob> getAllJobsOfRecruiterUserId(@PathVariable("userId") Long userId, @RequestParam(defaultValue = "0")int page){

        Pageable pageable = PageRequest.of(page,5);

        return  recruiterJobRepository.findAllJobsByRecruiterCompanyId(userId,pageable);
    }

    @PutMapping("update/job/{userId}/{jobId}")
    public ApiResponse updateRecruiterJob(@PathVariable("userId") Long userId,@PathVariable("jobId") Long jobId,@RequestBody RecruiterJobDto recruiterProfileDTO){
        return recruiterService.updateJob(userId,recruiterProfileDTO,jobId);
    }


    @GetMapping("jobsbycategory")
    public Page<RecruiterJob> getJobsByCategory(@RequestParam Map<String,String> requestParams){
        String category=requestParams.get("category");
        category = category.replaceAll("_and_","&");
        Integer page=Integer.parseInt(requestParams.get("page"));

        Pageable pageable = PageRequest.of(page,5);

        return recruiterJobRepository.findByCategory(category,pageable);
    }


    @PostMapping("apply")
    public ApiResponse applyOnPrivateJob(@RequestBody ApplyOrReferOnPrivateJobDTO applyOrReferOnPrivateJobDTO){
        return recruiterService.applyOnPrivateJob(applyOrReferOnPrivateJobDTO);
    }


    @PostMapping("referJobToCandidate")
    public ApiResponse referRecruiterJobToCandidates(@RequestBody ApplyOrReferOnPrivateJobDTO referJobToCandidateDTO){
        return recruiterService.referJob(referJobToCandidateDTO);
    }
    @GetMapping("notReferedJobs")
    public ApiResponse getAllNotReferedJobsToSelectedCandidate(@RequestParam Map<String,String> requestParams){
        Long candId = Long.parseLong(requestParams.get("candId"));
        Long companyId = Long.parseLong(requestParams.get("companyId"));
        Integer page=Integer.parseInt(requestParams.get("page"));

        Pageable pageable = PageRequest.of(page,3);


        List<Long> idList = appliedForRecruiterJobRepository.getDetails(candId,companyId,pageable);
        List<RecruiterJob> recruiterJobs = new ArrayList<>();
        for (Long id:idList) {
            Optional<RecruiterJob> jobsOptional = recruiterJobRepository.findById(id);
            if(jobsOptional.isPresent()){
                recruiterJobs.add(jobsOptional.get()) ;
            }
        }
        return new ApiResponse(200,"Successfull",recruiterJobs);
    }

    @DeleteMapping("undoRefer/{jobId}/{candId}")
    public ApiResponse undoReferToCandidate(@PathVariable("jobId") Long jobId,@PathVariable("candId") Long candId){
        return recruiterService.undoRefered(jobId,candId);
    }

    @GetMapping("search")
    public ApiResponse searchAllCandidates(@RequestParam("search") String searchString){



        List<User> foundUsers  =  userDaoRepository.searchUser(searchString) ;
        List<SearchUserDTO> searchUserDTOS = new ArrayList<>();

        for (User u:foundUsers) {

            SearchUserDTO userDTO = new SearchUserDTO();


            userDTO.setDp(null);
            userDTO.setProfileId(null);
            userDTO.setName(u.getName());
            userDTO.setUserId(u.getId());
            userDTO.setUserType(u.getUserType());

            if(u.getUserType().equalsIgnoreCase("candidate") && u.getProfile()!=null){


                userDTO.setDp(u.getProfile().getDp());
                userDTO.setProfileId(u.getProfile().getId());

            }
            else if(u.getProfile()!=null){

                userDTO.setDp(u.getProfile().getDp());
                userDTO.setProfileId(u.getProfile().getId());
            }

            searchUserDTOS.add(userDTO);
        }
        return new ApiResponse(200,"Successfull",searchUserDTOS);
    }





}
