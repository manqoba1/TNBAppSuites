package com.example.sifiso.tnblibrary.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sifiso on 2015-05-16.
 */
public class ResponseDTO implements Serializable {

    private Integer success;
    private String message;
    private Date lastCacheDate, startdate, endDate, dateTaken;
    private List<IssuesDTO> issuesList = new ArrayList<>();
    private List<IssueimageDTO> issueImageList = new ArrayList<>();
    private List<StatusreportedissueDTO> statusReportedIssueList = new ArrayList<>();
    private List<ReportedissueDTO> reportedIssueList = new ArrayList<>();
    private List<TownDTO> townList = new ArrayList<>();
    private List<WardsDTO> wardList = new ArrayList<>();
    private List<MunicipalityDTO> municipalityList = new ArrayList<>();

    public Integer getSuccess() {
        return success;
    }

    public Date getLastCacheDate() {
        return lastCacheDate;
    }

    public void setLastCacheDate(Date lastCacheDate) {
        this.lastCacheDate = lastCacheDate;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Date dateTaken) {
        this.dateTaken = dateTaken;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<IssuesDTO> getIssuesList() {
        return issuesList;
    }

    public void setIssuesList(List<IssuesDTO> issuesList) {
        this.issuesList = issuesList;
    }

    public List<IssueimageDTO> getIssueImageList() {
        return issueImageList;
    }

    public void setIssueImageList(List<IssueimageDTO> issueImageList) {
        this.issueImageList = issueImageList;
    }

    public List<StatusreportedissueDTO> getStatusReportedIssueList() {
        return statusReportedIssueList;
    }

    public void setStatusReportedIssueList(List<StatusreportedissueDTO> statusReportedIssueList) {
        this.statusReportedIssueList = statusReportedIssueList;
    }

    public List<ReportedissueDTO> getReportedIssueList() {
        return reportedIssueList;
    }

    public void setReportedIssueList(List<ReportedissueDTO> reportedIssueList) {
        this.reportedIssueList = reportedIssueList;
    }

    public List<TownDTO> getTownList() {
        return townList;
    }

    public void setTownList(List<TownDTO> townList) {
        this.townList = townList;
    }

    public List<WardsDTO> getWardList() {
        return wardList;
    }

    public void setWardList(List<WardsDTO> wardList) {
        this.wardList = wardList;
    }

    public List<MunicipalityDTO> getMunicipalityList() {
        return municipalityList;
    }

    public void setMunicipalityList(List<MunicipalityDTO> municipalityList) {
        this.municipalityList = municipalityList;
    }
}
