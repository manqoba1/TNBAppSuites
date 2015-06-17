/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.sifiso.tnblibrary.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sifiso
 */
public class ReportedissueDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer reportedIssueID, communitymemberID, municipalityID, issuesID;
    private String dateReported;
    private String reviews, issueName, icon;
    private Double latitude;
    private Double longitude;
    private String dateCreated;
    private String refNumber;
    private List<StatusreportedissueDTO> statusreportedissueList = new ArrayList<StatusreportedissueDTO>();
    private CommunitymemberDTO member;
    private IssuesDTO issues;
    private MunicipalityDTO municipality;
    private List<IssueimageDTO> issueimageList = new ArrayList<IssueimageDTO>();

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getIssuesID() {
        return issuesID;
    }

    public void setIssuesID(Integer issuesID) {
        this.issuesID = issuesID;
    }

    public Integer getReportedIssueID() {
        return reportedIssueID;
    }

    public void setReportedIssueID(Integer reportedIssueID) {
        this.reportedIssueID = reportedIssueID;
    }

    public Integer getCommunitymemberID() {
        return communitymemberID;
    }

    public void setCommunitymemberID(Integer communitymemberID) {
        this.communitymemberID = communitymemberID;
    }

    public Integer getMunicipalityID() {
        return municipalityID;
    }

    public void setMunicipalityID(Integer municipalityID) {
        this.municipalityID = municipalityID;
    }

    public String getDateReported() {
        return dateReported;
    }

    public void setDateReported(String dateReported) {
        this.dateReported = dateReported;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public List<StatusreportedissueDTO> getStatusreportedissueList() {
        return statusreportedissueList;
    }

    public void setStatusreportedissueList(List<StatusreportedissueDTO> statusreportedissueList) {
        this.statusreportedissueList = statusreportedissueList;
    }

    public CommunitymemberDTO getMember() {
        return member;
    }

    public void setMember(CommunitymemberDTO member) {
        this.member = member;
    }

    public IssuesDTO getIssues() {
        return issues;
    }

    public void setIssues(IssuesDTO issues) {
        this.issues = issues;
    }

    public MunicipalityDTO getMunicipality() {
        return municipality;
    }

    public void setMunicipality(MunicipalityDTO municipality) {
        this.municipality = municipality;
    }

    public List<IssueimageDTO> getIssueimageList() {
        return issueimageList;
    }

    public void setIssueimageList(List<IssueimageDTO> issueimageList) {
        this.issueimageList = issueimageList;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reportedIssueID != null ? reportedIssueID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReportedissueDTO)) {
            return false;
        }
        ReportedissueDTO other = (ReportedissueDTO) object;
        if ((this.reportedIssueID == null && other.reportedIssueID != null) || (this.reportedIssueID != null && !this.reportedIssueID.equals(other.reportedIssueID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sifiso.noticeboard.data.Reportedissue[ reportedIssueID=" + reportedIssueID + " ]";
    }

}
