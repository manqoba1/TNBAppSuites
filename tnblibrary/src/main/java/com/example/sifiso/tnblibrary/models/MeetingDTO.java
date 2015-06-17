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
public class MeetingDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer meetingID, municipalityID, wardsID;
    private String topic;
    private String uploadflyerUrl;
    private long meetingDate;
    private int clerkID;
    private MunicipalityDTO municipality;
    private WardsDTO wards;
    private List<MeetingminuteDTO> meetingminuteList = new ArrayList<MeetingminuteDTO>();

    public MeetingDTO() {
    }


    public Integer getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(Integer meetingID) {
        this.meetingID = meetingID;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUploadflyerUrl() {
        return uploadflyerUrl;
    }

    public void setUploadflyerUrl(String uploadflyerUrl) {
        this.uploadflyerUrl = uploadflyerUrl;
    }

    public Integer getMunicipalityID() {
        return municipalityID;
    }

    public void setMunicipalityID(Integer municipalityID) {
        this.municipalityID = municipalityID;
    }

    public Integer getWardsID() {
        return wardsID;
    }

    public void setWardsID(Integer wardsID) {
        this.wardsID = wardsID;
    }

    public long getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(long meetingDate) {
        this.meetingDate = meetingDate;
    }

    public int getClerkID() {
        return clerkID;
    }

    public void setClerkID(int clerkID) {
        this.clerkID = clerkID;
    }

    public MunicipalityDTO getMunicipality() {
        return municipality;
    }

    public void setMunicipality(MunicipalityDTO municipality) {
        this.municipality = municipality;
    }

    public WardsDTO getWards() {
        return wards;
    }

    public void setWards(WardsDTO wards) {
        this.wards = wards;
    }

    public List<MeetingminuteDTO> getMeetingminuteList() {
        return meetingminuteList;
    }

    public void setMeetingminuteList(List<MeetingminuteDTO> meetingminuteList) {
        this.meetingminuteList = meetingminuteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (meetingID != null ? meetingID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MeetingDTO)) {
            return false;
        }
        MeetingDTO other = (MeetingDTO) object;
        if ((this.meetingID == null && other.meetingID != null) || (this.meetingID != null && !this.meetingID.equals(other.meetingID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sifiso.noticeboard.data.Meeting[ meetingID=" + meetingID + " ]";
    }

}
