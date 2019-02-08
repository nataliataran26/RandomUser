package com.nataliiataran.randomuser.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Registered implements Parcelable {

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

    public Registered() {
    }

    protected Registered(Parcel in) {
        this.date = in.readString();
        this.age = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Registered> CREATOR = new Creator<Registered>() {
        @Override
        public Registered createFromParcel(Parcel source) {
            return new Registered(source);
        }

        @Override
        public Registered[] newArray(int size) {
            return new Registered[size];
        }
    };
}
