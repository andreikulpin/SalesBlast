package ru.arcsinus.salesblast.model;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Field;

/**
 * Created by Andrei on 13.11.2016.
 */

public class RegisterDeviceBody extends RegistrationModel{

    @SerializedName("aiuid") String aiuid;

    public RegisterDeviceBody(Context context, double latitude, double longitude) {
        super(context, latitude, longitude);
        this.aiuid = " ";
    }

    @Override
    public String toString() {
        return "" + appKey + " " + packageName + " " + appVersion + " " + latitude + " " + longitude + " " + deviceType + " " + deviceVersion
            + " " + deviceModel + " "  + screenWidth + " " + screenHeight + " " + aiuid;
    }

    public String getAiuid() {
        return aiuid;
    }

    public void setAiuid(String aiuid) {
        this.aiuid = aiuid;
    }
}
