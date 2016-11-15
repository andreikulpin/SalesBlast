package ru.arcsinus.salesblast.model;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

public class ContentRequestBody extends RegistrationModel {

    @SerializedName("UID") String uid;
    @SerializedName("last_session_datetime") long lastSessionDateTime;
    @SerializedName("content_type_id") long contentTypeId;
    @SerializedName("from_id") long fromId;
    @SerializedName("max") int max;

    public ContentRequestBody(Context context, double latitude, double longitude, String uid) {
        super(context, latitude, longitude);
        this.uid = uid;
        this.lastSessionDateTime = 0;
        this.contentTypeId = 0;
        this.fromId = 0;
        this.max = 100;
    }

    @Override
    public String toString() {
        return "" + appKey + " " + packageName + " " + appVersion + " " + latitude + " " + longitude + " " + deviceType + " " + deviceVersion
                + " " + deviceModel + " "  + screenWidth + " " + screenHeight + " " + uid + " " + lastSessionDateTime + " "
                + contentTypeId + " " + fromId + " " + max;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getLastSessionDateTime() {
        return lastSessionDateTime;
    }

    public void setLastSessionDateTime(long lastSessionDateTime) {
        this.lastSessionDateTime = lastSessionDateTime;
    }

    public long getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(long contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
