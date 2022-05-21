package com.example.myapplication;


public class Model_todo {
    private String gorev,aciklama,date;
    public Model_todo(){

    }

    public Model_todo(String gorev, String aciklama, String date) {
        this.gorev = gorev;
        this.aciklama = aciklama;
        this.date = date;
    }

    public String getGorev() {
        return gorev;
    }

    public void setGorev(String gorev) {
        this.gorev = gorev;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
