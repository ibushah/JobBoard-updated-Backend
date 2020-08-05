package com.example.jobBoard.Controller;


import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Dto.ReviewAndRatingDTO;
import com.example.jobBoard.Model.User;
import com.example.jobBoard.Repository.ReviewAndRatingRepository;
import com.example.jobBoard.Service.JobService;
import com.example.jobBoard.Service.ReviewAndRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/review")
public class RatingAndReviewController {

    @Autowired
    JobService jobService;
    @Autowired
    ReviewAndRatingRepository reviewAndRatingRepository;

    @Autowired
    ReviewAndRatingService reviewAndRatingService;


    @PostMapping("/save/{userId}")
    public ApiResponse saveComment(@RequestParam(required = false,name = "video") MultipartFile videoFile, ReviewAndRatingDTO reviewAndRatingDTO,@PathVariable(name = "userId") Long userId){
        if(reviewAndRatingDTO.getType().equalsIgnoreCase("video"))
            reviewAndRatingDTO.setVideo(videoFile);
        return reviewAndRatingService.saveRatingAndReview(reviewAndRatingDTO,userId)   ;
    }

    @GetMapping("/alreadyCommented/{userId}/{visitedUserId}")
    public ResponseEntity alreadyGivenReviewOrNot(@NotNull @Valid @PathVariable("userId") Long userId,@NotNull @Valid @PathVariable("visitedUserId") Long visitedUserId){
        return reviewAndRatingService.checkStatus(userId,visitedUserId);
    }


    @GetMapping("/{user}/{filename:.+}")
    public ResponseEntity<InputStreamResource> getGalleryImage(@PathVariable("user") String userIdAndName, @PathVariable("filename") String filename)
            throws IOException {
        return reviewAndRatingService.getImage(filename,userIdAndName);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReview(@PathVariable("id") Long id){

        return reviewAndRatingService.deleteReview(id);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity updateReview(@PathVariable("id") Long id, @RequestParam(required = false,name = "video") MultipartFile videoFile, ReviewAndRatingDTO reviewAndRatingDTO)
//    {
//        if(reviewAndRatingDTO.getType().equalsIgnoreCase("video"))
//            reviewAndRatingDTO.setVideo(videoFile);
//        return reviewAndRatingService.updateReview(id,reviewAndRatingDTO);
//    }
}

