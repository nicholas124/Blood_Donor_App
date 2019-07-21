package com.example.mypc.inventorymanagement.Model;

public class BloodBank {

    private String Apos;
    private String Aneg;
    private String Bpos;
    private String Bneg;
    private String ABpos;
    private String ABneg;
    private String Opos;
    private String Oneg;

    public BloodBank() {
    }

    public BloodBank(String apos, String aneg, String bpos, String bneg, String ABpos, String ABneg, String opos, String oneg) {
        Apos = apos;
        Aneg = aneg;
        Bpos = bpos;
        Bneg = bneg;
        this.ABpos = ABpos;
        this.ABneg = ABneg;
        Opos = opos;
        Oneg = oneg;
    }

    public String getApos() {
        return Apos;
    }

    public void setApos(String apos) {
        Apos = apos;
    }

    public String getAneg() {
        return Aneg;
    }

    public void setAneg(String aneg) {
        Aneg = aneg;
    }

    public String getBpos() {
        return Bpos;
    }

    public void setBpos(String bpos) {
        Bpos = bpos;
    }

    public String getBneg() {
        return Bneg;
    }

    public void setBneg(String bneg) {
        Bneg = bneg;
    }

    public String getABpos() {
        return ABpos;
    }

    public void setABpos(String ABpos) {
        this.ABpos = ABpos;
    }

    public String getABneg() {
        return ABneg;
    }

    public void setABneg(String ABneg) {
        this.ABneg = ABneg;
    }

    public String getOpos() {
        return Opos;
    }

    public void setOpos(String opos) {
        Opos = opos;
    }

    public String getOneg() {
        return Oneg;
    }

    public void setOneg(String oneg) {
        Oneg = oneg;
    }
}
