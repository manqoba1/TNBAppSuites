/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.sifiso.tnblibrary.models;

import java.io.Serializable;

/**
 *
 * @author sifiso
 */
public class CommentsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer commentsID, meetingMinuteID, memberID;
    private String message;
    private long commentDate;
    private MeetingminuteDTO meetingMinute;
    private CommunitymemberDTO member;

    public CommentsDTO() {
    }


    public CommentsDTO(Integer commentsID) {
        this.commentsID = commentsID;
    }

    public Integer getCommentsID() {
        return commentsID;
    }

    public void setCommentsID(Integer commentsID) {
        this.commentsID = commentsID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getMeetingMinuteID() {
        return meetingMinuteID;
    }

    public void setMeetingMinuteID(Integer meetingMinuteID) {
        this.meetingMinuteID = meetingMinuteID;
    }

    public Integer getMemberID() {
        return memberID;
    }

    public void setMemberID(Integer memberID) {
        this.memberID = memberID;
    }

    public long getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(long commentDate) {
        this.commentDate = commentDate;
    }

    public MeetingminuteDTO getMeetingMinute() {
        return meetingMinute;
    }

    public void setMeetingMinute(MeetingminuteDTO meetingMinute) {
        this.meetingMinute = meetingMinute;
    }

    public CommunitymemberDTO getMember() {
        return member;
    }

    public void setMember(CommunitymemberDTO member) {
        this.member = member;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commentsID != null ? commentsID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommentsDTO)) {
            return false;
        }
        CommentsDTO other = (CommentsDTO) object;
        if ((this.commentsID == null && other.commentsID != null) || (this.commentsID != null && !this.commentsID.equals(other.commentsID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sifiso.noticeboard.data.Comments[ commentsID=" + commentsID + " ]";
    }

}
