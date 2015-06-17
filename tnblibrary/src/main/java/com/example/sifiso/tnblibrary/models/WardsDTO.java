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
public class WardsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer wardsID, municipalityID;
    private String wardName;
    private MunicipalityDTO municipality;
    private List<ClerkDTO> clerkList = new ArrayList<ClerkDTO>();
    private List<CommunitymemberDTO> communitymemberList = new ArrayList<CommunitymemberDTO>();
    private List<MeetingDTO> meetingList = new ArrayList<MeetingDTO>();


    public Integer getWardsID() {
        return wardsID;
    }

    public void setWardsID(Integer wardsID) {
        this.wardsID = wardsID;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public Integer getMunicipalityID() {
        return municipalityID;
    }

    public void setMunicipalityID(Integer municipalityID) {
        this.municipalityID = municipalityID;
    }

    public MunicipalityDTO getMunicipality() {
        return municipality;
    }

    public void setMunicipality(MunicipalityDTO municipality) {
        this.municipality = municipality;
    }

    public List<ClerkDTO> getClerkList() {
        return clerkList;
    }

    public void setClerkList(List<ClerkDTO> clerkList) {
        this.clerkList = clerkList;
    }

    public List<CommunitymemberDTO> getCommunitymemberList() {
        return communitymemberList;
    }

    public void setCommunitymemberList(List<CommunitymemberDTO> communitymemberList) {
        this.communitymemberList = communitymemberList;
    }

    public List<MeetingDTO> getMeetingList() {
        return meetingList;
    }

    public void setMeetingList(List<MeetingDTO> meetingList) {
        this.meetingList = meetingList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wardsID != null ? wardsID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WardsDTO)) {
            return false;
        }
        WardsDTO other = (WardsDTO) object;
        if ((this.wardsID == null && other.wardsID != null) || (this.wardsID != null && !this.wardsID.equals(other.wardsID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sifiso.noticeboard.data.Wards[ wardsID=" + wardsID + " ]";
    }

}
