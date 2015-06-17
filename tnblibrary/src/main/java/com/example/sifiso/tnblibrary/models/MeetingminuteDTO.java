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
public class MeetingminuteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer meetingMinuteID, meetingID;
    private long uploadDate;
    private String fileUrl;
    private List<CommentsDTO> commentsList = new ArrayList<CommentsDTO>();
    private MeetingDTO meeting;



    public Integer getMeetingMinuteID() {
        return meetingMinuteID;
    }

    public void setMeetingMinuteID(Integer meetingMinuteID) {
        this.meetingMinuteID = meetingMinuteID;
    }

    public long getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(long uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Integer getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(Integer meetingID) {
        this.meetingID = meetingID;
    }

    public List<CommentsDTO> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<CommentsDTO> commentsList) {
        this.commentsList = commentsList;
    }

    public MeetingDTO getMeeting() {
        return meeting;
    }

    public void setMeeting(MeetingDTO meeting) {
        this.meeting = meeting;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (meetingMinuteID != null ? meetingMinuteID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MeetingminuteDTO)) {
            return false;
        }
        MeetingminuteDTO other = (MeetingminuteDTO) object;
        if ((this.meetingMinuteID == null && other.meetingMinuteID != null) || (this.meetingMinuteID != null && !this.meetingMinuteID.equals(other.meetingMinuteID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sifiso.noticeboard.data.Meetingminute[ meetingMinuteID=" + meetingMinuteID + " ]";
    }

}
