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
public class MunicipalityDTO implements Serializable {
    private static final long serialVersionUID = 1L;
   
    private Integer municipalityID;
    private String municipalityName;
    private String tell;
    private Double longitude;
    private Double latitude;
    private String address;
    private String email;
    private List<TownDTO> townList=new ArrayList<TownDTO>();
    private List<ReportedissueDTO> reportedissueList=new ArrayList<ReportedissueDTO>();
    private List<WardsDTO> wardsList=new ArrayList<WardsDTO>();
    private List<ClerkDTO> clerkList=new ArrayList<ClerkDTO>();
    private List<MeetingDTO> meetingList=new ArrayList<MeetingDTO>();


   
    
    public Integer getMunicipalityID() {
        return municipalityID;
    }

    public void setMunicipalityID(Integer municipalityID) {
        this.municipalityID = municipalityID;
    }

    public String getMunicipalityName() {
        return municipalityName;
    }

    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<TownDTO> getTownList() {
        return townList;
    }

    public void setTownList(List<TownDTO> townList) {
        this.townList = townList;
    }

    public List<ReportedissueDTO> getReportedissueList() {
        return reportedissueList;
    }

    public void setReportedissueList(List<ReportedissueDTO> reportedissueList) {
        this.reportedissueList = reportedissueList;
    }

    public List<WardsDTO> getWardsList() {
        return wardsList;
    }

    public void setWardsList(List<WardsDTO> wardsList) {
        this.wardsList = wardsList;
    }

    public List<ClerkDTO> getClerkList() {
        return clerkList;
    }

    public void setClerkList(List<ClerkDTO> clerkList) {
        this.clerkList = clerkList;
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
        hash += (municipalityID != null ? municipalityID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MunicipalityDTO)) {
            return false;
        }
        MunicipalityDTO other = (MunicipalityDTO) object;
        if ((this.municipalityID == null && other.municipalityID != null) || (this.municipalityID != null && !this.municipalityID.equals(other.municipalityID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sifiso.noticeboard.data.Municipality[ municipalityID=" + municipalityID + " ]";
    }
    
}
