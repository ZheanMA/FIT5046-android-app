package com.example.yjw.assignment2;

/**
 * Created by yjw on 2018/4/27.
 */

public class Credentials {
    private int resId;
    private String userName;
    private String passwordHash;
    private String regDate;

    public Credentials(int resId, String userName, String passwordHash, String regDate) {
        this.resId = resId;
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.regDate = regDate;
    }


    public String toCString(String resident) {
        return "{" +
                "\"username\":" + "\"" + userName + "\"" +
                ", \"resid\":" + resident +
                ", \"passwordhash\":" + "\"" + passwordHash + "\"" +
                ", \"regdate\":" + "\"" + regDate + "\"" +
                '}';
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}
