package ru.arcsinus.salesblast;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.SyncStateContract;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import ru.arcsinus.salesblast.model.ApiResponse;
import ru.arcsinus.salesblast.model.ContentRequestBody;
import ru.arcsinus.salesblast.model.Item;
import ru.arcsinus.salesblast.model.RegisterDeviceBody;


public class WebService extends IntentService{

    DBHelper dbHelper;

    public WebService() {
        super("Web");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        dbHelper = new DBHelper(getApplicationContext());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final ResultReceiver receiver = intent.getParcelableExtra(Constants.RECEIVER_KEY);
        receiver.send(Constants.STATUS_RUNNING, Bundle.EMPTY);
        final Bundle data = new Bundle();
        data.putInt(Constants.WEB_SERVICE_KEY, intent.getIntExtra(Constants.WEB_SERVICE_KEY, 0));

        if (Preferences.getInstance() == null) Preferences.getInstance(getApplicationContext());
        try {
            if (Preferences.getInstance().getString(Preferences.Keys.APP_KEY, "").equals(""))
                registerDevice();

            List<Item> result = getContent();
            data.putParcelableArrayList(Constants.BUNDLE_KEY_RESULT, new ArrayList<Parcelable>(result));
        } catch (IOException e) {

            switch (intent.getIntExtra(Constants.WEB_SERVICE_KEY, 0)){
                case Constants.WEB_SERVICE_INIT:
                    receiver.send(Constants.STATUS_CONNECTION_ERROR, data);
                    return;
                case Constants.WEB_SERVICE_REFRESH:
                    receiver.send(Constants.STATUS_CONNECTION_ERROR_REFRESH, data);
                    return;
            }

            return;
        }

        receiver.send(Constants.STATUS_FINISHED, data);
    }

    private void registerDevice() throws IOException {
        Call<JsonObject> call = ApiFactory.getService().registerDevice(buildRegisterBody());
        JsonObject response = call.execute().body();
        if (response.get("status").getAsInt() == 0) {
            String uid = response.getAsJsonObject("data").get("UID").getAsString();
            Preferences.getInstance().put(Preferences.Keys.APP_KEY, uid);
        }
    }

    private List<Item> getContent() throws IOException {
        Call<ApiResponse> call = ApiFactory.getService().getContent(buildContentRequestBody());
        ApiResponse response = call.execute().body();

        List<Item> list = response.getData().getContent();
        return list;
    }

    private RegisterDeviceBody buildRegisterBody() {
        return new RegisterDeviceBody(getApplicationContext(), 55.7656, 37.6058);
    }

    private ContentRequestBody buildContentRequestBody(){
        ContentRequestBody body =  new ContentRequestBody(getApplicationContext(), 55.7656, 37.6058, Preferences.getInstance().getString(Preferences.Keys.APP_KEY, ""));
        return body;
    }

}
