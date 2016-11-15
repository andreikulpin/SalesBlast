package ru.arcsinus.salesblast;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;

import ru.arcsinus.salesblast.event.ListEvent;
import ru.arcsinus.salesblast.model.Item;

public class ListFragment extends Fragment {
    private MainActivity mActivity;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mList;
    private RecyclerView.Adapter mListAdapter;
    private View view;

    private Controller mController;
    public ServiceResultReceiver mReceiver;

    public static ListFragment newInstance(MainActivity activity){
        ListFragment listFragment = new ListFragment();
        listFragment.mActivity = activity;
        return listFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);
        mReceiver = new ServiceResultReceiver(new Handler());
        mController = new Controller(mActivity, mReceiver);
        mReceiver.setReceiver(mController);

        mSwipeRefresh = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mController.refreshData();
            }
        });

        mController.fetchDataFromApi();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mReceiver.setReceiver(null);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mSwipeRefresh.setRefreshing(false);
    }

    public void populateList(ArrayList<Item> list){
        if (mList == null){
            mList = (RecyclerView)view.findViewById(R.id.list);
            mList.setLayoutManager(new LinearLayoutManager(mActivity));
        }
        if (mListAdapter == null){
            Collections.sort(list, new Item.DateComparator());
            mListAdapter = new ListAdapter(mActivity, list);

        } else {
            mListAdapter.notifyDataSetChanged();
        }
        mList.setAdapter(mListAdapter);
    }

    @Subscribe
    public void onEvent(ListEvent listEvent){
        view.findViewById(R.id.progress).setVisibility(View.GONE);
        mSwipeRefresh.setRefreshing(false);
        if (listEvent.getList() != null)
            populateList(listEvent.getList());
    }
}
