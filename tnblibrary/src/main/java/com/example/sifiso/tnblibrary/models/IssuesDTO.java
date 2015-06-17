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
 *
 * @author sifiso
 */
public class IssuesDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer issuesID;
    private String name;
    private String icon;
    private List<ReportedissueDTO> reportedissueList = new ArrayList<ReportedissueDTO>();



    public Integer getIssuesID() {
        return issuesID;
    }

    public void setIssuesID(Integer issuesID) {
        this.issuesID = issuesID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<ReportedissueDTO> getReportedissueList() {
        return reportedissueList;
    }

    public void setReportedissueList(List<ReportedissueDTO> reportedissueList) {
        this.reportedissueList = reportedissueList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (issuesID != null ? issuesID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IssuesDTO)) {
            return false;
        }
        IssuesDTO other = (IssuesDTO) object;
        if ((this.issuesID == null && other.issuesID != null) || (this.issuesID != null && !this.issuesID.equals(other.issuesID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sifiso.noticeboard.data.Issues[ issuesID=" + issuesID + " ]";
    }

}
