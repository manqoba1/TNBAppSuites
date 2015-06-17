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
public class CommunitymemberDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer communityMemberID, wardsID,municipalityID;
    private String name;
    private String cell;
    private String email;
    private String password;
    private List<CommentsDTO> commentsList = new ArrayList<CommentsDTO>();
    private List<ReportedissueDTO> reportedissueList =new ArrayList<ReportedissueDTO>();
    private WardsDTO wards;

    public Integer getMunicipalityID() {
        return municipalityID;
    }

    public void setMunicipalityID(Integer municipalityID) {
        this.municipalityID = municipalityID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCommunityMemberID() {
        return communityMemberID;
    }

    public void setCommunityMemberID(Integer communityMemberID) {
        this.communityMemberID = communityMemberID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getWardsID() {
        return wardsID;
    }

    public void setWardsID(Integer wardsID) {
        this.wardsID = wardsID;
    }

    public List<CommentsDTO> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<CommentsDTO> commentsList) {
        this.commentsList = commentsList;
    }

    public List<ReportedissueDTO> getReportedissueList() {
        return reportedissueList;
    }

    public void setReportedissueList(List<ReportedissueDTO> reportedissueList) {
        this.reportedissueList = reportedissueList;
    }

    public WardsDTO getWards() {
        return wards;
    }

    public void setWards(WardsDTO wards) {
        this.wards = wards;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (communityMemberID != null ? communityMemberID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommunitymemberDTO)) {
            return false;
        }
        CommunitymemberDTO other = (CommunitymemberDTO) object;
        if ((this.communityMemberID == null && other.communityMemberID != null) || (this.communityMemberID != null && !this.communityMemberID.equals(other.communityMemberID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sifiso.noticeboard.data.Communitymember[ communityMemberID=" + communityMemberID + " ]";
    }

}
