package com.example.jobBoard.Specifications;

import com.example.jobBoard.Model.Job;
import org.springframework.data.jpa.domain.Specification;

public interface JobSearchSPECIFICATIONS {
    static Specification<Job> hasType(String type) {
        return (jobRoot, cq, cb) -> cb.equal(jobRoot.get("type"), type);
    }

    static Specification<Job> hasCity(String city) {
        return (jobRoot, cq, cb) -> cb.equal(jobRoot.get("city"), city);
    }

    static Specification<Job> hasCompany(Long company_id) {
        return (jobRoot, cq, cb) -> cb.equal(jobRoot.get("user"), company_id);
    }

    static Specification<Job> hasCategory(String category){
        return (jobRoot, cq, cb) -> cb.equal(jobRoot.get("category"),category);
    }

}
