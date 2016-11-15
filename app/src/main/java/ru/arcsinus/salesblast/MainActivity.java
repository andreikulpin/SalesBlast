package ru.arcsinus.salesblast;

import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import ru.arcsinus.salesblast.event.ErrorEvent;
import ru.arcsinus.salesblast.event.FragmentEvent;
import ru.arcsinus.salesblast.model.Item;

public class MainActivity extends AppCompatActivity{

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListFragment listFragment = ListFragment.newInstance(this);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, listFragment).commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_toolbar_list));

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onEvent(FragmentEvent fragmentEvent){
        ItemFragment itemFragment = ItemFragment.newInstance(fragmentEvent.getItem());
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, itemFragment).addToBackStack(null).commit();
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_left_white_24dp);
        toolbar.setTitle(fragmentEvent.getItem().getType());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Subscribe
    public void onEvent(ErrorEvent errorEvent){
        Snackbar
                .make(findViewById(R.id.drawer_layout), getString(R.string.snack_connection_error), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void onBackPressed() {
        toolbar.setNavigationIcon(null);
        toolbar.setTitle(getString(R.string.title_toolbar_list));
        super.onBackPressed();
    }
}
