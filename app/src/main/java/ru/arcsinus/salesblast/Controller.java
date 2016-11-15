package ru.arcsinus.salesblast;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import ru.arcsinus.salesblast.event.ErrorEvent;
import ru.arcsinus.salesblast.event.ListEvent;
import ru.arcsinus.salesblast.model.Item;

public class Controller implements LoaderManager.LoaderCallbacks<ArrayList<Item>>, ServiceResultReceiver.Receiver{

    private Context context;
    public ServiceResultReceiver mReceiver;

    private final int ID_LOADER_DB = 1;

    public Controller(Context context, ServiceResultReceiver receiver) {
        this.context = context;
        mReceiver = receiver;
    }

    public void fetchDataFromApi(){
        final Intent intent = new Intent("", null, context, WebService.class);
        intent.putExtra(Constants.WEB_SERVICE_KEY, Constants.WEB_SERVICE_INIT);
        intent.putExtra(Constants.RECEIVER_KEY, mReceiver);
        context.startService(intent);
    }

    public void refreshData(){
        final Intent intent = new Intent("", null, context, WebService.class);
        intent.putExtra(Constants.WEB_SERVICE_KEY, Constants.WEB_SERVICE_REFRESH);
        intent.putExtra(Constants.RECEIVER_KEY, mReceiver);
        context.startService(intent);
    }

    @Override
    public Loader<ArrayList<Item>> onCreateLoader(int id, Bundle args) {
        switch (id){
            case ID_LOADER_DB:
                ArrayList<Item> newList = args.getParcelableArrayList(Constants.BUNDLE_KEY_RESULT);
                return new DatabaseLoader(context, newList);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Item>> loader, ArrayList<Item> data) {
        if (data != null)
            EventBus.getDefault().post(new ListEvent(data));
        else
            EventBus.getDefault().post(new ListEvent(null));
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Item>> loader) {

    }

    @Override
    public void onReceiveResult(int resultCode, Bundle data) {
        switch (resultCode) {
            case Constants.STATUS_FINISHED :
                List<Item> newList = data.getParcelableArrayList(Constants.BUNDLE_KEY_RESULT);
                ((MainActivity) context).getSupportLoaderManager().initLoader(ID_LOADER_DB, data, this);
                break;

            case Constants.STATUS_CONNECTION_ERROR:
                EventBus.getDefault().post(new ErrorEvent(ErrorEvent.ERROR_CONNECTION));
                ((MainActivity) context).getSupportLoaderManager().initLoader(ID_LOADER_DB, data, this);
                break;

            case Constants.STATUS_CONNECTION_ERROR_REFRESH:
                EventBus.getDefault().post(new ErrorEvent(ErrorEvent.ERROR_CONNECTION));
                EventBus.getDefault().post(new ListEvent(null));
                break;
        }
    }


}
