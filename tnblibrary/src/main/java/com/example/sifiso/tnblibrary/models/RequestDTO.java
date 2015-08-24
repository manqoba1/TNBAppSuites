package com.example.sifiso.tnblibrary.models;

import java.io.Serializable;

/**
 * Created by sifiso on 2015-05-16.
 */
public class RequestDTO implements Serializable {
    private String email, password;
    private Integer communityMemberID, municipalityID, clerkID;
    private CommunitymemberDTO communityMember;
    private ReportedissueDTO reportedissue;
    private IssueimageDTO issueimage;
    private StatusreportedissueDTO statusreportedissue;

    public StatusreportedissueDTO getStatusreportedissue() {
        return statusreportedissue;
    }

    public void setStatusreportedissue(StatusreportedissueDTO statusreportedissue) {
        this.statusreportedissue = statusreportedissue;
    }

    public Integer getClerkID() {
        return clerkID;
    }

    public void setClerkID(Integer clerkID) {
        this.clerkID = clerkID;
    }

    public Integer getMunicipalityID() {
        return municipalityID;
    }

    public void setMunicipalityID(Integer municipalityID) {
        this.municipalityID = municipalityID;
    }

    public IssueimageDTO getIssueimage() {
        return issueimage;
    }

    public void setIssueimage(IssueimageDTO issueimage) {
        this.issueimage = issueimage;
    }

    public Integer getCommunityMemberID() {
        return communityMemberID;
    }

    public void setCommunityMemberID(Integer communityMemberID) {
        this.communityMemberID = communityMemberID;
    }

    public ReportedissueDTO getReportedissue() {
        return reportedissue;
    }

    public void setReportedissue(ReportedissueDTO reportedissue) {
        this.reportedissue = reportedissue;
    }

    public CommunitymemberDTO getCommunityMember() {
        return communityMember;
    }

    public void setCommunityMember(CommunitymemberDTO communityMember) {
        this.communityMember = communityMember;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
