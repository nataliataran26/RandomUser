package com.nataliiataran.randomuser.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dob implements Parcelable {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("age")
    @Expose
    private Integer age;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeValue(this.age);
    }

    public Dob() {
    }

    protected Dob(Parcel in) {
        this.date = in.readString();
        this.age = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Dob> CREATOR = new Creator<Dob>() {
        @Override
        public Dob createFromParcel(Parcel source) {
            return new Dob(source);
        }

        @Override
        public Dob[] newArray(int size) {
            return new Dob[size];
        }
    };
}