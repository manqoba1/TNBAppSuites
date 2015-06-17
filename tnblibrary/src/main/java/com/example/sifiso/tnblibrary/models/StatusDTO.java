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
public class StatusDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer statusID;
    private String statusName;
    private List<StatusreportedissueDTO> statusreportedissueList = new ArrayList<StatusreportedissueDTO>();


    public Integer getStatusID() {
        return statusID;
    }

    public void setStatusID(Integer statusID) {
        this.statusID = statusID;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public List<StatusreportedissueDTO> getStatusreportedissueList() {
        return statusreportedissueList;
    }

    public void setStatusreportedissueList(List<StatusreportedissueDTO> statusreportedissueList) {
        this.statusreportedissueList = statusreportedissueList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (statusID != null ? statusID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StatusDTO)) {
            return false;
        }
        StatusDTO other = (StatusDTO) object;
        if ((this.statusID == null && other.statusID != null) || (this.statusID != null && !this.statusID.equals(other.statusID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sifiso.noticeboard.data.Status[ statusID=" + statusID + " ]";
    }

}
