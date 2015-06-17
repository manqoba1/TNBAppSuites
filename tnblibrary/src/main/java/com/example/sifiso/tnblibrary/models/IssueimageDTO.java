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
public class IssueimageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer issueImageID,reportedIssueID;
    private String imageUrl, dateTaken;
    private ReportedissueDTO reportedIssue;

    public IssueimageDTO() {
    }

    public Integer getReportedIssueID() {
        return reportedIssueID;
    }

    public void setReportedIssueID(Integer reportedIssueID) {
        this.reportedIssueID = reportedIssueID;
    }

    public Integer getIssueImageID() {
        return issueImageID;
    }

    public void setIssueImageID(Integer issueImageID) {
        this.issueImageID = issueImageID;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ReportedissueDTO getReportedIssue() {
        return reportedIssue;
    }

    public void setReportedIssue(ReportedissueDTO reportedIssue) {
        this.reportedIssue = reportedIssue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (issueImageID != null ? issueImageID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IssueimageDTO)) {
            return false;
        }
        IssueimageDTO other = (IssueimageDTO) object;
        if ((this.issueImageID == null && other.issueImageID != null) || (this.issueImageID != null && !this.issueImageID.equals(other.issueImageID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sifiso.noticeboard.data.Issueimage[ issueImageID=" + issueImageID + " ]";
    }

}
