package com.example.jobBoard.Controller;


import com.example.jobBoard.Commons.ApiResponse;
//import com.example.jobBoard.Dto.JobPostDTO;
import com.example.jobBoard.Dto.TenderDTO;
import com.example.jobBoard.Dto.ViewTenderDTO;
import com.example.jobBoard.Model.Job;
import com.example.jobBoard.Model.Tender;
import com.example.jobBoard.Model.TenderAssortments;
import com.example.jobBoard.Repository.TenderRepository;
import com.example.jobBoard.Service.TenderService;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class TenderController {

    @Autowired
    TenderRepository tenderRepository;

    @Autowired
    TenderService tenderService;

    @GetMapping("/tender/all/{tenderType}/{id}")
    public ResponseEntity<List<Tender>> getAllTenders(@PathVariable("tenderType") String tenderType,@PathVariable("id") Long id)
    {

       List<Tender> tenderList = tenderRepository.getAllPublicTenders(tenderType,id);
        return new ResponseEntity<List<Tender>>(tenderList, HttpStatus.OK);
    }

    @GetMapping("/tender/all/{tenderType}")
    public ResponseEntity<List<Tender>> getAllTenders(@PathVariable("tenderType") String tenderType)
    {

        List<Tender> tenderList = tenderRepository.getAllPublicTendersForRecruiter(tenderType);
        return new ResponseEntity<List<Tender>>(tenderList, HttpStatus.OK);
    }


   @PostMapping("/tender")
    public ResponseEntity<Tender> addTender(@Valid @NotNull @RequestBody TenderDTO tenderDTO){
        return tenderService.save(tenderDTO);
   }

    @GetMapping("/tender/{tenderId}")
    public ResponseEntity<ViewTenderDTO> findTender(@PathVariable("tenderId") Long tenderId){
        return tenderService.findTenderById(tenderId);
    }
    @GetMapping("/tendernotification/recruiter/{recruiterId}")
    public ResponseEntity<List<TenderAssortments>> getTenderNotificationsToRecruiter(@PathVariable("recruiterId") Long recruiterId){
       return tenderService.getTenderNotifications(recruiterId);
    }


    @PostMapping("/tender/accept_decline/{isApplied}")
    public ResponseEntity getTenderNotificationsToEmployer(@RequestBody TenderDTO tenderDTO,@PathVariable("isApplied") Boolean isApplied){
        return tenderService.acceptOrDeclineTender(tenderDTO,isApplied);
    }

    @GetMapping("/tendernotification/employer/{employerUserId}")
    public ResponseEntity<List<TenderAssortments>> sendNotificationToEmployerAboutAcceptOrDecline(@PathVariable("employerUserId") Long employerUserId){
        return tenderService.sendNotificationToEmployerAboutAcceptOrDecline(employerUserId);
    }






}
