/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.sifiso.tnblibrary.models;

import java.io.Serializable;

/**
 * @author sifiso
 */
public class StatusreportedissueDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer statusReportedIssueID, reportedIssueID, statusID, flagDone;
    private String statusReportedDate, statusName;
    private ReportedissueDTO reportedIssue;
    private StatusDTO status;

    public Integer getFlagDone() {
        return flagDone;
    }

    public void setFlagDone(Integer flagDone) {
        this.flagDone = flagDone;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getStatusReportedIssueID() {
        return statusReportedIssueID;
    }

    public void setStatusReportedIssueID(Integer statusReportedIssueID) {
        this.statusReportedIssueID = statusReportedIssueID;
    }

    public Integer getReportedIssueID() {
        return reportedIssueID;
    }

    public void setReportedIssueID(Integer reportedIssueID) {
        this.reportedIssueID = reportedIssueID;
    }

    public Integer getStatusID() {
        return statusID;
    }

    public void setStatusID(Integer statusID) {
        this.statusID = statusID;
    }

    public String getStatusReportedDate() {
        return statusReportedDate;
    }

    public void setStatusReportedDate(String statusReportedDate) {
        this.statusReportedDate = statusReportedDate;
    }

    public ReportedissueDTO getReportedIssue() {
        return reportedIssue;
    }

    public void setReportedIssue(ReportedissueDTO reportedIssue) {
        this.reportedIssue = reportedIssue;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (statusReportedIssueID != null ? statusReportedIssueID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StatusreportedissueDTO)) {
            return false;
        }
        StatusreportedissueDTO other = (StatusreportedissueDTO) object;
        if ((this.statusReportedIssueID == null && other.statusReportedIssueID != null) || (this.statusReportedIssueID != null && !this.statusReportedIssueID.equals(other.statusReportedIssueID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sifiso.noticeboard.data.Statusreportedissue[ statusReportedIssueID=" + statusReportedIssueID + " ]";
    }

}
