package com.example.jobBoard.Controller;


import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Dto.JobDto;
import com.example.jobBoard.Dto.ReviewAndRatingDTO;
import com.example.jobBoard.Model.Job;
import com.example.jobBoard.Model.Profile;
import com.example.jobBoard.Repository.JobRepository;
import com.example.jobBoard.Repository.ProfileRepository;
import com.example.jobBoard.Repository.UserDaoRepository;
import com.example.jobBoard.Service.AppliedForService;
import com.example.jobBoard.Service.JobService;
import com.example.jobBoard.Specifications.JobSearchSPECIFICATIONS;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/job")
public class JobController implements JobSearchSPECIFICATIONS{


    @Autowired
    JobService jobService;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    ProfileRepository companyProfileRepository;
    @Autowired
    UserDaoRepository userDaoRepository;

    @Autowired
    AppliedForService appliedForService;

    @PostMapping("/{userId}")
    public ApiResponse postJob(@RequestBody JobDto jobDTO,@PathVariable("userId") Long userId)
    {
        return jobService.postJob(jobDTO,userId);
    }


    @PutMapping("/update/{userId}/{jobId}")
    public ApiResponse updateJob(@PathVariable("userId") Long id,@PathVariable("jobId") Long jobId,@RequestBody JobDto jobDTO)
    {
        return jobService.updateJOB(jobId,id,jobDTO);
    }


    @GetMapping("/")
    public ApiResponse<Job> getJobById(@RequestParam(defaultValue = "1") Long id)
    {
        try{
            return new ApiResponse(200, "Job fetching successful", jobRepository.findById(id).get());
        }catch (Exception e){
            return new ApiResponse(500, "Error fetching the job", null);
        }
    }


    @GetMapping("/company")
    public List<Job> getJobsByCompany(@RequestParam(defaultValue = "1") Long id)
    {

        return jobService.getJobsByCompany(id);
    }




    @GetMapping("/myJobs/{userId}")
    public Page<Job> getMyJobs(@PathVariable("userId") Long id,@RequestParam(defaultValue ="0")int page)
    {
        Pageable pageable = PageRequest.of(page,5);

        return jobRepository.findJobsByCompanyPaginated(id,pageable);

    }


    @GetMapping("/jobsbycategory/{userId}")
    public Page<Job> getJobsByCategory(@PathVariable("userId") Long userId,@RequestParam Map<String,String> requestParams){
        String category=requestParams.get("category");
        category = category.replaceAll("_and_","&");
        Integer page=Integer.parseInt(requestParams.get("page"));

        return jobService.findByCategory(category,page,userId);
    }


    @GetMapping("/paginatedjobs")
    public Page<Job> getAllPaginatedJobs(@RequestParam(defaultValue = "0") int page)
    {

        return jobRepository.findAll(PageRequest.of(page,5));
    }



//    Candiidate search
    @GetMapping("/specifications")
    public Page<Job> testing(@RequestParam Map<String,String> requestParams){

        Profile companyProfile = new Profile();
        Long companyId = 0l;
        String city=requestParams.get("city").equalsIgnoreCase("null")?"":requestParams.get("city");
        String company=requestParams.get("company").equalsIgnoreCase("null")?"":requestParams.get("company");
        String type=requestParams.get("type");
        type = type.equalsIgnoreCase("all")?"":type;
        int page=Integer.parseInt(requestParams.get("page"));

        Pageable pageable = PageRequest.of(page,5);

        if(company.length()>1){
            companyProfile   = companyProfileRepository.findCompanyProfileByName(company);
            companyId  = companyProfile.getId();
        }


//        Search by type
        if(type.length()>1 && (company.length()<1 && city.length()<1)){
            return jobRepository.findAll((JobSearchSPECIFICATIONS.hasType(type)),pageable);
        }


        else if(company.length()<1 && (type.length()>1 && city.length()>1)){
            return jobRepository.findAll(Specification.where(JobSearchSPECIFICATIONS.hasType(type).and(JobSearchSPECIFICATIONS.hasCity(city))),pageable);
        }

//        Search by
        else if((city.length()>1) && type.length()<1 && company.length()<1)
        {
            return jobRepository.findAll(JobSearchSPECIFICATIONS.hasCity(city),pageable);
        }

        else if((company.length()>1 && type.length()>1) && city.length()<1)
        {
            return jobRepository.findAll(Specification.where(JobSearchSPECIFICATIONS.hasType(type)).and(JobSearchSPECIFICATIONS.hasCompany(companyId)),pageable);
        }

        else if((city.length()>1 && company.length()>1) && type.length()<1){
            return jobRepository.findAll(Specification.where(JobSearchSPECIFICATIONS.hasCity(city)).and(JobSearchSPECIFICATIONS.hasCompany(companyId)),pageable);
        }

        else if(city.length()>1 && company.length()>1 && type.length()>1){
            return jobRepository.findAll(Specification.where(JobSearchSPECIFICATIONS.hasCity(city)).and(JobSearchSPECIFICATIONS.hasCompany(companyId).and(JobSearchSPECIFICATIONS.hasType(type))),pageable);
        }

        else if(company.length()>1 && (city.length()<1 && type.length()<1)){

            if(companyProfile!=null){

                return jobRepository.findJobsByCompanyPaginated(companyId,pageable);
            }
            else{
                return null;
            }
        }

        return jobRepository.findAll(pageable);
    }


    @DeleteMapping("/delete/{jobId}/{userId}/page")
    public ApiResponse deleteJobAndItsAssociations(@PathVariable("userId") Long userId,@PathVariable(name = "jobId") Long jobId,@RequestParam(name = "page") int page){
        return  jobService.deleteJobById(userId,jobId,PageRequest.of(page,5));
    }


    @PostMapping("/applyJob")
    public ApiResponse applyJobDTOApiResponse(@RequestBody ReviewAndRatingDTO reviewAndRatingDTO){
        return  appliedForService.applyOnJob(reviewAndRatingDTO);
    }


    @GetMapping("/candidateprofiles/{jobId}")
    public ApiResponse showCandidateProfileAgainstJobId(@NotNull @Valid @PathVariable(name = "jobId") Long jobId){
        try {
            return new ApiResponse(200,"successfull",jobRepository.findAllAppliedUser(jobId));
        }catch (Exception e){
            return  new ApiResponse(500,"Error",e);
        }

    }



}
