package ru.arcsinus.salesblast;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.arcsinus.salesblast.model.Item;

public class DatabaseLoader extends AsyncTaskLoader<ArrayList<Item>> {

    private DBHelper dbHelper;
    private List<Item> newList;

    public DatabaseLoader(Context context) {
        super(context);
        dbHelper = new DBHelper(context);
    }

    public DatabaseLoader(Context context, ArrayList<Item> newList) {
        super(context);
        dbHelper = new DBHelper(context);
        this.newList = newList;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public ArrayList<Item> loadInBackground() {

        if (newList == null){
            if (dbHelper.isDatabaseEmpty()) return null;
            else return dbHelper.getAllItems();
        } else {
            if (dbHelper.isDatabaseEmpty()) createDB();
            else updateDB();
        }
        ArrayList<Item> result = dbHelper.getAllItems();
        return result;
    }

    private void createDB(){
        for (int i = 0; i < newList.size(); i++){
            if (newList.get(i).getContentStatusId() == 3 || newList.get(i).getContentStatusId() == 4)
                newList.remove(i);
        }
        dbHelper.insertItems(newList);
    }

    private void updateDB(){
        for (int i = 0; i < newList.size(); i++) {
            Item item = newList.get(i);
            if (item.getContentStatusId() == 3 || item.getContentStatusId() == 4) {
                if (dbHelper.getById(item.getId()) != null) {
                    dbHelper.deleteItem(item.getId());
                }
                newList.remove(i);
            } else {
                if (dbHelper.getById(item.getId()) == null){
                    dbHelper.insertItem(item);
                }
            }
        }
    }
}
