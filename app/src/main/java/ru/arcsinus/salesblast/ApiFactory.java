package ru.arcsinus.salesblast;

import android.support.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.arcsinus.salesblast.model.ApiResponse;
import ru.arcsinus.salesblast.model.ContentRequestBody;
import ru.arcsinus.salesblast.model.Item;
import ru.arcsinus.salesblast.model.RegisterDeviceBody;


public class ApiFactory {

    private static final String baseURL = "http://service-retailmob.rhcloud.com/api/v1/mobclient/";

    private static final int TIMEOUT = 60;
    private static final int WRITE_TIMEOUT = 120;
    private static final int CONNECT_TIMEOUT = 10;

    private static OkHttpClient sClient;

    public interface ApiInterface {

        @POST("register")
        Call<JsonObject> registerDevice(@Body RegisterDeviceBody body);

        @POST("getcontent")
        Call<ApiResponse> getContent(@Body ContentRequestBody body);
    }

    @NonNull
    public static ApiInterface getService() {
        return buildRetrofit().create(ApiInterface.class);
    }

    @NonNull
    private static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .client(getClient())
                .build();
    }

    @NonNull
    private static OkHttpClient getClient() {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (ApiFactory.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor())
                .build();
    }

}
