package com.example.jobBoard.Dto;

public class ApplyOrReferOnPrivateJobDTO {
    Long companyId;
    Long jobId;
    Long  candidateId;

    public ApplyOrReferOnPrivateJobDTO(Long companyId, Long jobId, Long candidateId) {
        this.companyId = companyId;
        this.jobId = jobId;
        this.candidateId = candidateId;
    }

    public ApplyOrReferOnPrivateJobDTO() {
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }
}
