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
public class ClerkDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer clerkID, municipalityID, wardID;
    private String name;
    private String surname, wardName, municipalityName;
    private String email;
    private String password;
    private MunicipalityDTO municipality;
    private WardsDTO wards;

    public ClerkDTO() {
    }



    public Integer getMunicipalityID() {
        return municipalityID;
    }

    public void setMunicipalityID(Integer municipalityID) {
        this.municipalityID = municipalityID;
    }

    public Integer getWardID() {
        return wardID;
    }

    public void setWardID(Integer wardID) {
        this.wardID = wardID;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getMunicipalityName() {
        return municipalityName;
    }

    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
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

    public ClerkDTO(Integer clerkID) {
        this.clerkID = clerkID;
    }

    public Integer getClerkID() {
        return clerkID;
    }

    public void setClerkID(Integer clerkID) {
        this.clerkID = clerkID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clerkID != null ? clerkID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClerkDTO)) {
            return false;
        }
        ClerkDTO other = (ClerkDTO) object;
        if ((this.clerkID == null && other.clerkID != null) || (this.clerkID != null && !this.clerkID.equals(other.clerkID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sifiso.noticeboard.data.Clerk[ clerkID=" + clerkID + " ]";
    }

}
