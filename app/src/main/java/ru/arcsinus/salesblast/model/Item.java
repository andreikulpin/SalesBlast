package ru.arcsinus.salesblast.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import ru.arcsinus.salesblast.R;

public class Item implements Parcelable{

    @SerializedName("id") private long id;
    @SerializedName("header") private String header;
    @SerializedName("short_text") private String shortText;
    @SerializedName("full_text") private String fullText;
    @SerializedName("publish_time") private String publishTime;
    @SerializedName("change_datetime") private String changeDateTime;
    @SerializedName("content_type_id") private byte contentTypeId;
    @SerializedName("type") private String type;
    @SerializedName("link") private String link;
    @SerializedName("content_status_id") private byte contentStatusId;
    @SerializedName("status") private String status;
    @SerializedName("start_datetime") private String startDateTime;
    @SerializedName("end_datetime") private String endDateTime;
    @SerializedName("img_preview_url") private String imagePreviewUrl;
    @SerializedName("img_url") private String imageUrl;

    @Override
    public String toString() {
        return "" + id + " header: " + header + " shortText: "+ shortText  + " fullText: " + fullText + " publishTime: "
                + publishTime + " changeTime: " + changeDateTime + " " + contentTypeId + " "
                + type + " " + link + " " + contentStatusId + " " + status + " \n"
                + "preview url: " + imagePreviewUrl + " image url: " + imageUrl;
    }

    public Item(long id, String header, String shortText, String fullText, String publishTime, String changeDateTime, byte contentTypeId, String type, String link, String startDateTime, String endDateTime, String imagePreviewUrl, String imageUrl) {
        this.id = id;
        this.header = header;
        this.shortText = shortText;
        this.fullText = fullText;
        this.publishTime = publishTime;
        this.changeDateTime = changeDateTime;
        this.contentTypeId = contentTypeId;
        this.type = type;
        this.link = link;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.imagePreviewUrl = imagePreviewUrl;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(byte contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChangeDateTime() {
        return changeDateTime;
    }

    public void setChangeDateTime(String changeDateTime) {
        this.changeDateTime = changeDateTime;
    }

    public byte getContentStatusId() {
        return contentStatusId;
    }

    public void setContentStatusId(byte contentStatusId) {
        this.contentStatusId = contentStatusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getImagePreviewUrl() {
        return imagePreviewUrl;
    }

    public void setImagePreviewUrl(String imagePreviewUrl) {
        this.imagePreviewUrl = imagePreviewUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private Item(Parcel in){
        this.id = in.readLong();
        this.header = in.readString();
        this.shortText = in.readString();
        this.fullText = in.readString();
        this.publishTime = in.readString();
        this.changeDateTime = in.readString();
        this.contentTypeId = in.readByte();
        this.type = in.readString();
        this.link = in.readString();
        this.startDateTime = in.readString();
        this.endDateTime = in.readString();
        this.imagePreviewUrl = in.readString();
        this.imageUrl = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(header);
        parcel.writeString(shortText);
        parcel.writeString(fullText);
        parcel.writeString(publishTime);
        parcel.writeString(changeDateTime);
        parcel.writeByte(contentTypeId);
        parcel.writeString(type);
        parcel.writeString(link);
        parcel.writeString(startDateTime);
        parcel.writeString(endDateTime);
        parcel.writeString(imagePreviewUrl);
        parcel.writeString(imageUrl);
    }

    public static class DateComparator implements Comparator<Item> {

        @Override
        public int compare(Item item, Item anotherItem) {
            try{
                Date date = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse(item.getPublishTime());
                Date anotherDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse(anotherItem.getPublishTime());
                return anotherDate.compareTo(date);
            } catch (ParseException e){}
            return 0;
        }
    }

}
