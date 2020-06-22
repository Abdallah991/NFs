package com.fathom.nfs.DataModels;

public class AppointmentDataModel {

    private String day;
    private String month;
    private String doctorName;
    private String specialty;
    private String timing;
    private String to;
    private String message;
    private String userEmail;
    private String userName;
    private String documentId;


    public AppointmentDataModel(String day, String month, String doctorName, String specialty, String timing) {
        this.day = day;
        this.month = month;
        this.doctorName = doctorName;
        this.specialty = specialty;
        this.timing = timing;
    }

    public AppointmentDataModel() {
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getTiming() {
        return timing;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
