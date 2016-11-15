package ru.arcsinus.salesblast.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {

    public class Data {
        @SerializedName("content")
        List<Item> content;

        public List<Item> getContent() {
            return content;
        }

        public void setContent(List<Item> content) {
            this.content = content;
        }
    }

    @SerializedName("data")
    Data data;

    @SerializedName("message")
    String message;

    @SerializedName("status")
    int status;

    public ApiResponse(Data data, String message, int status) {
        this.data = data;
        this.data.content = data.content;
        this.message = message;
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
