package ru.arcsinus.salesblast.model;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Andrei on 13.11.2016.
 */

public class RegistrationModel {

    @SerializedName("app_key") String appKey;
    @SerializedName("package_name") String packageName;
    @SerializedName("app_version") String appVersion;
    @SerializedName("latitude") double latitude;
    @SerializedName("longitude") double longitude;
    @SerializedName("devicetype") int deviceType;
    @SerializedName("deviceversion") int deviceVersion;
    @SerializedName("devicemodel") String deviceModel;
    @SerializedName("screenwidth") int screenWidth;
    @SerializedName("screenheight") int screenHeight;

    public RegistrationModel(Context context, double latitude, double longitude) {
        this.appKey = "04f0f542ea27a58461a44fbd75a89b30";
        this.packageName = context.getPackageName();
        try{
            this.appVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e){
            this.appVersion = "1.1.0";
        }
        this.latitude = latitude;
        this.longitude = longitude;
        this.deviceType = 0;
        this.deviceVersion = android.os.Build.VERSION.SDK_INT;
        this.deviceModel = Build.MODEL;

        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        this.screenWidth = metrics.widthPixels;
        this.screenHeight = metrics.heightPixels;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(int deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }
}
