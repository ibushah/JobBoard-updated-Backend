package com.example.jobBoard.Repository;

import com.example.jobBoard.Model.ReviewAndRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewAndRatingRepository extends JpaRepository<ReviewAndRating,Long> {
    @Query(value = "select * from review_and_rating where candidate_id=:candId AND company_id=:companyId AND rate_by=:rateBy",nativeQuery = true)
    ReviewAndRating checkReviewStatus(@Param("candId") Long canId, @Param("companyId") Long companyId, @Param("rateBy") String rateBy);

}
