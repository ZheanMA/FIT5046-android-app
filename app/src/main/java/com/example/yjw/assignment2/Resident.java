package com.example.yjw.assignment2;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * Created by yjw on 2018/4/21.
 */

public class Resident implements Parcelable {
    //credential table
    private Integer resId;
    private String userName;
    private String password;
    private String regdate;
    //resident table
    private String firstName;
    private String surName;
    private String address;
    private String dob;
    private String email;
    private String mobile;
    private String nameofenergyprovider;
    private String postCode;
    private String numofresident;

    //All attributes
    public Resident(Integer resId, String userName, String password, String regdate,
                    String firstName, String secondName, String address, String dob,
                    String email, String mobile, String nameofenergyprovider, String postCode,
                    String numofresident) {
        this.resId = resId;
        this.userName = userName;
        this.password = password;
        this.regdate = regdate;
        this.firstName = firstName;
        this.surName = secondName;
        this.address = address;
        this.dob = dob;
        this.email = email;
        this.mobile = mobile;
        this.nameofenergyprovider = nameofenergyprovider;
        this.postCode = postCode;
        this.numofresident = numofresident;
    }

    //Part of attributes used to create a resident in database


    public Resident(Integer resId, String firstName, String secondName, String address,
                    String dob, String email, String mobile, String nameofenergyprovider, String postCode,
                    String numofresident) {
        this.resId = resId;
        this.firstName = firstName;
        this.surName = secondName;
        this.address = address;
        this.dob = dob;
        this.email = email;
        this.mobile = mobile;
        this.nameofenergyprovider = nameofenergyprovider;
        this.postCode = postCode;
        this.numofresident = numofresident;
    }

    @Override
    public String toString() {
        return "{" +
                "\"resid\":" + resId +
                ", \"firstname\":" + "\"" + firstName + "\"" +
                ", \"surname\":" + "\"" + surName + "\"" +
                ", \"address\":" + "\"" + address + "\"" +
                ", \"dob\":" + "\"" + dob + "\"" +
                ", \"email\":" + "\"" + email + "\"" +
                ", \"mobile\":" + "\"" + mobile + "\"" +
                ", \"nameofenergyprovider\":" + "\"" + nameofenergyprovider + "\"" +
                ", \"postcode\":" + "\"" + postCode + "\"" +
                ", \"numofresident\":" + numofresident +
                '}';
    }

    public Resident (JsonArray jsonArray) {
        this.resId = jsonArray.get(0).getAsJsonObject().get("resid").getAsJsonObject().get("resid").getAsInt();
        this.userName = jsonArray.get(0).getAsJsonObject().get("username").getAsString();
        this.password = jsonArray.get(0).getAsJsonObject().get("passwordhash").getAsString();
        this.regdate = jsonArray.get(0).getAsJsonObject().get("regdate").getAsString();
        this.firstName = jsonArray.get(0).getAsJsonObject().get("resid").getAsJsonObject().get("firstname").getAsString();
        this.surName = jsonArray.get(0).getAsJsonObject().get("resid").getAsJsonObject().get("surname").getAsString();
        this.address = jsonArray.get(0).getAsJsonObject().get("resid").getAsJsonObject().get("address").getAsString();
        this.dob = jsonArray.get(0).getAsJsonObject().get("resid").getAsJsonObject().get("dob").getAsString();
        this.email = jsonArray.get(0).getAsJsonObject().get("resid").getAsJsonObject().get("email").getAsString();
        this.mobile = jsonArray.get(0).getAsJsonObject().get("resid").getAsJsonObject().get("mobile").getAsString();
        this.nameofenergyprovider = jsonArray.get(0).getAsJsonObject().get("resid").getAsJsonObject().get("nameofenergyprovider").getAsString();
        this.postCode = jsonArray.get(0).getAsJsonObject().get("resid").getAsJsonObject().get("postcode").getAsString();
        this.numofresident = jsonArray.get(0).getAsJsonObject().get("resid").getAsJsonObject().get("numofresident").getAsString();


    }


    protected Resident(Parcel in) {
        if (in.readByte() == 0) {
            resId = null;
        } else {
            resId = in.readInt();
        }
        userName = in.readString();
        password = in.readString();
        regdate = in.readString();
        firstName = in.readString();
        surName = in.readString();
        address = in.readString();
        dob = in.readString();
        email = in.readString();
        mobile = in.readString();
        nameofenergyprovider = in.readString();
        postCode = in.readString();
        numofresident = in.readString();
    }

    public static final Creator<Resident> CREATOR = new Creator<Resident>() {
        @Override
        public Resident createFromParcel(Parcel in) {
            return new Resident(in);
        }

        @Override
        public Resident[] newArray(int size) {
            return new Resident[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (resId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(resId);
        }
        parcel.writeString(userName);
        parcel.writeString(password);
        parcel.writeString(regdate);
        parcel.writeString(firstName);
        parcel.writeString(surName);
        parcel.writeString(address);
        parcel.writeString(dob);
        parcel.writeString(email);
        parcel.writeString(mobile);
        parcel.writeString(nameofenergyprovider);
        parcel.writeString(postCode);
        parcel.writeString(numofresident);
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getsurName() {
        return surName;
    }

    public void setsurName(String surName) {
        this.surName = surName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getnameofenergyprovider() {
        return nameofenergyprovider;
    }

    public void setnameofenergyprovider(String nameofenergyprovider) {
        this.nameofenergyprovider = nameofenergyprovider;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getnumofresident() {
        return numofresident;
    }

    public void setnumofresident(String numofresident) {
        this.numofresident = numofresident;
    }
}
