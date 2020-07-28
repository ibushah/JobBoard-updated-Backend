package com.example.jobBoard.Dto;

import com.example.jobBoard.Model.Profile;
import com.example.jobBoard.Model.RecruiterJob;

import java.util.List;

public class ViewPrivateJobDTO {
    RecruiterJob recruiterJob;
    List<AllCandidatesReferedOrNotList> AllCandidatesReferedOrNotList;
    List<Profile> candidateProfiles;

    public ViewPrivateJobDTO(RecruiterJob recruiterJob, List<com.example.jobBoard.Dto.AllCandidatesReferedOrNotList> allCandidatesReferedOrNotList, List<Profile> candidateProfiles) {
        this.recruiterJob = recruiterJob;
        AllCandidatesReferedOrNotList = allCandidatesReferedOrNotList;
        this.candidateProfiles = candidateProfiles;
    }

    public ViewPrivateJobDTO() {
    }

    public RecruiterJob getRecruiterJob() {
        return recruiterJob;
    }

    public void setRecruiterJob(RecruiterJob recruiterJob) {
        this.recruiterJob = recruiterJob;
    }

    public List<com.example.jobBoard.Dto.AllCandidatesReferedOrNotList> getAllCandidatesReferedOrNotList() {
        return AllCandidatesReferedOrNotList;
    }

    public void setAllCandidatesReferedOrNotList(List<com.example.jobBoard.Dto.AllCandidatesReferedOrNotList> allCandidatesReferedOrNotList) {
        AllCandidatesReferedOrNotList = allCandidatesReferedOrNotList;
    }

    public List<Profile> getCandidateProfiles() {
        return candidateProfiles;
    }

    public void setCandidateProfiles(List<Profile> candidateProfiles) {
        this.candidateProfiles = candidateProfiles;
    }
}
