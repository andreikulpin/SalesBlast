package ru.arcsinus.salesblast;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ru.arcsinus.salesblast.event.FragmentEvent;
import ru.arcsinus.salesblast.model.Item;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    private MainActivity mActivity;
    private ArrayList<Item> mList;

    public ListAdapter(MainActivity activity, ArrayList<Item> list) {
        mActivity = activity;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view, mActivity);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textTitle.setText(mList.get(position).getHeader());
        holder.textBrief.setText(mList.get(position).getShortText());
        String type = mList.get(position).getType();
        try {
            Date date = new SimpleDateFormat(mActivity.getString(R.string.date_pattern_api)).parse(mList.get(position).getPublishTime());
            type += ", " + new SimpleDateFormat(mActivity.getString(R.string.date_pattern)).format(date);
        }catch (ParseException e){}
        holder.textTypeDate.setText(type);
        if (!mList.get(position).getImagePreviewUrl().isEmpty())
            Picasso.with(mActivity).load(mList.get(position).getImagePreviewUrl()).resize(50, 50).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new FragmentEvent(mList.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private MainActivity mActivity;
        private ImageView imageView;
        private TextView textTitle;
        private TextView textTypeDate;
        private TextView textBrief;

        public ViewHolder(View itemView, MainActivity activity) {
            super(itemView);
            this.mActivity = activity;
            imageView = (ImageView) itemView.findViewById(R.id.image_preview_item);
            textTitle = (TextView)itemView.findViewById(R.id.title_item);
            textTypeDate = (TextView)itemView.findViewById(R.id.type_date_item);
            textBrief = (TextView)itemView.findViewById(R.id.short_text_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }
    }
}
