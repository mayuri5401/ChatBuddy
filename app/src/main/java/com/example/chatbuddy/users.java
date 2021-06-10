package com.example.chatbuddy;

public class users {

    String uid;
    String name;
    String emailid;
    String imageUri;
    String status;

    public users() {

    }



    public users(String uid, String name, String emailid, String imageUri, String status) {
        this.uid = uid;
        this.name = name;
        this.emailid = emailid;
        this.imageUri = imageUri;
        this.status=status;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
