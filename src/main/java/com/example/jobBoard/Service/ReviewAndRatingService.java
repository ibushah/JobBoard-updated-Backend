package com.example.jobBoard.Service;

import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Commons.Constants;
import com.example.jobBoard.Dto.ReviewAndRatingDTO;
import com.example.jobBoard.Model.Profile;
import com.example.jobBoard.Model.ReviewAndRating;
import com.example.jobBoard.Model.User;
import com.example.jobBoard.Repository.ProfileRepository;
import com.example.jobBoard.Repository.ReviewAndRatingRepository;
import com.example.jobBoard.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Service
public class ReviewAndRatingService {
    @Autowired
    ReviewAndRatingRepository reviewAndRatingRepository;

    @Autowired
    UserDaoRepository userDaoRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    ProfileRepository companyProfileRepository;

    @Value("${review.video.url}")
    String reviewVideoUrl;


    public ApiResponse saveRatingAndReview(ReviewAndRatingDTO reviewAndRatingDTO,Long userId) {

        Optional<User> candidateUser = userDaoRepository.findById(reviewAndRatingDTO.getCandidateId());
        Optional<User> companyUser = userDaoRepository.findById(reviewAndRatingDTO.getCompanyId());
        Optional<User> user = userDaoRepository.findById(userId);
        ReviewAndRating reviewAndRatingModel = new ReviewAndRating();
        ReviewAndRating reviewAndRating;

        if(reviewAndRatingDTO.getRatedBy().equalsIgnoreCase("candidate")){
            reviewAndRating= reviewAndRatingRepository.checkReviewStatus(reviewAndRatingDTO.getCandidateId(),reviewAndRatingDTO.getCompanyId(),"candidate");
        }
        else{
            reviewAndRating = reviewAndRatingRepository.checkReviewStatus(reviewAndRatingDTO.getCompanyId(),reviewAndRatingDTO.getCandidateId(),"company");
            reviewAndRatingDTO.setRatedBy("company");

        }if (reviewAndRating == null && reviewAndRatingDTO.getType().equalsIgnoreCase("text")) {
            reviewAndRatingModel.setDate(new Date());
            if(reviewAndRatingDTO.getRatedBy().equalsIgnoreCase("company")){
                reviewAndRatingModel.setReviewerProfile(companyUser.get());
                reviewAndRatingModel.setRevieweeProfile(candidateUser.get());
            }
            else{

                reviewAndRatingModel.setReviewerProfile(candidateUser.get());
                reviewAndRatingModel.setRevieweeProfile(companyUser.get());
            }


            reviewAndRatingModel.setReview(reviewAndRatingDTO.getReview());
            reviewAndRatingModel.setType(reviewAndRatingDTO.getType());

            double rating;
            if(reviewAndRatingDTO.getRatedBy().equalsIgnoreCase("company")){
                rating = candidateUser.get().getProfile().getAvgRating();
                rating = rating + reviewAndRatingDTO.getRating();
                candidateUser.get().getProfile().setAvgRating(rating);
                profileRepository.save(candidateUser.get().getProfile());

            }
            else{
                rating = companyUser.get().getProfile().getAvgRating();
                rating = rating + reviewAndRatingDTO.getRating();
                companyUser.get().getProfile().setAvgRating(rating);
                profileRepository.save(companyUser.get().getProfile());
            }

            reviewAndRatingModel.setRating(reviewAndRatingDTO.getRating());
            reviewAndRatingModel.setRateBy(reviewAndRatingDTO.getRatedBy());
            reviewAndRatingRepository.save(reviewAndRatingModel);
            return new ApiResponse(200, "SucesssFull",rating);

        }
        else if (reviewAndRating == null && reviewAndRatingDTO.getType().equalsIgnoreCase("video")) {

            String unique = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());
            if (saveVideoReview(reviewAndRatingDTO.getVideo(), user.get().getName(),user.get().getId(), unique)) {
                reviewAndRatingModel.setVideoUrl(reviewVideoUrl + user.get().getId() + "-" + user.get().getName() + "/" + unique + reviewAndRatingDTO.getVideo().getOriginalFilename());
                reviewAndRatingModel.setDate(new Date());
                if(reviewAndRatingDTO.getRatedBy().equalsIgnoreCase("company")){
                    reviewAndRatingModel.setReviewerProfile(companyUser.get());
                    reviewAndRatingModel.setRevieweeProfile(candidateUser.get());
                }
                else{

                    reviewAndRatingModel.setReviewerProfile(candidateUser.get());
                    reviewAndRatingModel.setRevieweeProfile(companyUser.get());
                }
                reviewAndRatingModel.setType(reviewAndRatingDTO.getType());

                double rating;
                if(reviewAndRatingDTO.getRatedBy().equalsIgnoreCase("company")){
                    rating = candidateUser.get().getProfile().getAvgRating();
                    rating = rating + reviewAndRatingDTO.getRating();
                    candidateUser.get().getProfile().setAvgRating(rating);
                    profileRepository.save(candidateUser.get().getProfile());

                }
                else{
                    rating = companyUser.get().getProfile().getAvgRating();
                    rating = rating + reviewAndRatingDTO.getRating();
                    companyUser.get().getProfile().setAvgRating(rating);
                    profileRepository.save(companyUser.get().getProfile());
                }
                reviewAndRatingModel.setRating(reviewAndRatingDTO.getRating());
                reviewAndRatingModel.setRateBy(reviewAndRatingDTO.getRatedBy());
                reviewAndRatingRepository.save(reviewAndRatingModel);
                return new ApiResponse(200, "SucesssFull",rating);
            }
        }
        return new ApiResponse(500, "Something went wrong", null);

    }

    public ResponseEntity<InputStreamResource> getImage(String filename, String userIdAndName) throws IOException {
        String filepath = Constants.SERVER_PATH + "serverFiles//" + userIdAndName + "//" + filename;
        File f = new File(filepath);
        Resource file = new UrlResource(f.toURI());
        return ResponseEntity
                .ok()
                .contentLength(file.contentLength())
                .contentType(
                        MediaType.parseMediaType("video/mp4"))
                .body(new InputStreamResource(file.getInputStream()));
    }

    public Boolean saveVideoReview(MultipartFile file, String name, Long id, String unique) {
        try {

            String UPLOADED_FOLDER_NEW = Constants.SERVER_PATH + "serverFiles//" + id + "-" + name + "//";

            File dir = new File(UPLOADED_FOLDER_NEW);
            dir.setExecutable(true);
            dir.setReadable(true);
            dir.setWritable(true);

            if (!dir.exists()) {
                dir.mkdirs();
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER_NEW + unique + file.getOriginalFilename());
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public ResponseEntity deleteReview(Long id) {
        reviewAndRatingRepository.deleteById(id);

        return new ResponseEntity<>("\" review successfully deleted\"", HttpStatus.OK);
    }

    public ResponseEntity checkStatus(Long userId,Long visitedUserId){
        try {
            Optional<User> userOptional = userDaoRepository.findById(userId);
            Optional<User> visitedUserProfile = userDaoRepository.findById(visitedUserId);
            ReviewAndRating reviewAndRating;

//            check the candidate give review to the company or not
            if(userOptional.get().getUserType().equalsIgnoreCase("candidate")){
                reviewAndRating= reviewAndRatingRepository.checkReviewStatus(userId,visitedUserId,"candidate");
            }
//            check the company give review to the candidate or not
            else{
                reviewAndRating = reviewAndRatingRepository.checkReviewStatus(userId,visitedUserId,"company");
            }

            if(reviewAndRating!=null){
                return new ResponseEntity<>("\"Already_reported\"", HttpStatus.ALREADY_REPORTED);
            }
            return new ResponseEntity<>("\"NOTFOUND\"", HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        }
    }

//    public ResponseEntity updateReview(Long id, ReviewAndRatingDTO reviewAndRatingDTO) {
//
//        Optional<ReviewAndRating> reviewAndRating = reviewAndRatingRepository.findById(id);
//
//
//        if (reviewAndRating.isPresent()) {
//            if (reviewAndRatingDTO.getType().equalsIgnoreCase("text")) {
//                reviewAndRating.get().setReview(reviewAndRatingDTO.getReview());
//                reviewAndRating.get().setRating(reviewAndRatingDTO.getRating());
//                reviewAndRatingRepository.save(reviewAndRating.get());
//                return new ResponseEntity<>("\" review successfully updated\"", HttpStatus.OK);
//            } else {
//                String unique = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());
//                //video rating
//                if (reviewAndRating.get().getRateBy().equalsIgnoreCase("candidate")) {
//                    Optional<CompanyProfile> reviewGetter = companyProfileRepository.findById(reviewAndRating.get().getCompanyProfile().getId());
//
//                    if (reviewGetter.isPresent()) {
//                        if (saveVideoReview(reviewAndRatingDTO.getVideo(), reviewGetter.get().getUser().getName(),
//                                reviewGetter.get().getUser().getId(), unique)) {
//
//                            reviewAndRating.get().setVideoUrl(reviewVideoUrl + reviewGetter.get().getUser().getId() + "-" +
//                                    reviewGetter.get().getUser().getName() + "/" + unique + reviewAndRatingDTO.getVideo().getOriginalFilename());
//                            reviewAndRating.get().setRating(reviewAndRatingDTO.getRating());
//                            reviewAndRatingRepository.save(reviewAndRating.get());
//                            return new ResponseEntity<>("\" review successfully updated\"", HttpStatus.OK);
//                        }
//                    }
//                } else {
//
//                    Optional<CandidateProfile> reviewGetter1 = candidateProfileRepository.findById(reviewAndRating.get().getCandidateId());
//
//                    if (reviewGetter1.isPresent()) {
//                        if (saveVideoReview(reviewAndRatingDTO.getVideo(), reviewGetter1.get().getUser().getName(),
//                                reviewGetter1.get().getUser().getId(), unique)) {
//
//                            reviewAndRating.get().setVideoUrl(reviewVideoUrl + reviewGetter1.get().getUser().getId() + "-" +
//                                    reviewGetter1.get().getUser().getName() + "/" + unique + reviewAndRatingDTO.getVideo().getOriginalFilename());
//                            reviewAndRating.get().setRating(reviewAndRatingDTO.getRating());
//                            reviewAndRatingRepository.save(reviewAndRating.get());
//                            return new ResponseEntity<>("\" review successfully updated\"", HttpStatus.OK);
//                        }
//                    }
//                }
//            }
//            return new ResponseEntity<>("\" review not updated\"", HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>("\" review not updated\"", HttpStatus.NOT_FOUND);
//    }


}