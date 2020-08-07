package com.example.jobBoard.Repository;

import com.example.jobBoard.Dto.ReviewAndRatingDTO;
import com.example.jobBoard.Dto.UserDto;
import com.example.jobBoard.Model.ReviewAndRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewAndRatingRepository extends JpaRepository<ReviewAndRating,Long> {
    @Query(value = "select * from review_and_rating where candidate_id=:candId AND company_id=:companyId AND rate_by=:rateBy",nativeQuery = true)
    ReviewAndRating checkReviewStatus(@Param("candId") Long canId, @Param("companyId") Long companyId, @Param("rateBy") String rateBy);


    @Query(value = "SELECT new com.example.jobBoard.Dto.ReviewAndRatingDTO" +
            "(r.review,r.rating,r.date,r.rateBy,r.type" +
            ",u.name,r.videoUrl,u.profile.dp,u.id,u.profile.avgRating)FROM ReviewAndRating r\n" +
            "INNER JOIN User u \n" +
            "ON u.id = r.companyProfile.id where r.candidateProfile.id=:id AND r.rateBy=:rateBy")
    List<ReviewAndRatingDTO> findReviews(@Param("id") Long id, @Param("rateBy") String rateBy);

    @Query(value = "SELECT new com.example.jobBoard.Dto.ReviewAndRatingDTO" +
            "(r.review,r.rating,r.date,r.rateBy,r.type" +
            ",u.name,r.videoUrl,u.profile.dp,u.id,u.profile.avgRating)FROM ReviewAndRating r\n" +
            "INNER JOIN User u \n" +
            "ON u.id = r.candidateProfile.id where (r.companyProfile.id=:id AND r.rateBy=:rateBy)")
    List<ReviewAndRatingDTO> findReviewsForCompany(@Param("id") Long id, @Param("rateBy") String rateBy);

}
