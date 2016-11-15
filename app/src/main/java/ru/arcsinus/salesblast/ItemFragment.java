package ru.arcsinus.salesblast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.arcsinus.salesblast.model.Item;


public class ItemFragment extends Fragment {
    private View view;
    private ImageView image;

    public static ItemFragment newInstance(Item item){
        ItemFragment itemFragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BUNDLE_KEY_ITEM, item);
        itemFragment.setArguments(bundle);
        return itemFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Item item = getArguments().getParcelable(Constants.BUNDLE_KEY_ITEM);
        ((TextView) getActivity().findViewById(R.id.title_item_content)).setText(item.getHeader());
        ((TextView) getActivity().findViewById(R.id.text_item)).setText(item.getFullText());
        try {
            Date date = new SimpleDateFormat(getString(R.string.date_pattern_api)).parse(item.getPublishTime());
            ((TextView) getActivity().findViewById(R.id.date_item)).setText(new SimpleDateFormat(getString(R.string.date_pattern)).format(date));
        }catch (ParseException e){}

        if (!item.getLink().isEmpty()) {
            getActivity().findViewById(R.id.button_source).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.button_source).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String URL = item.getLink();
                    if (!URL.startsWith("https://") && !URL.startsWith("http://")) {
                        URL = "http://" + URL;
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(URL));
                    startActivity(intent);
                }
            });
        }

        image = (ImageView) getActivity().findViewById(R.id.image_item);
        if (!item.getImageUrl().isEmpty() && !item.getImageUrl().equals(" ")) {
            String imageUrl = item.getImageUrl().substring(2, item.getImageUrl().length() - 2);
            Picasso.with(getActivity()).load(imageUrl).into(image);
        }
    }


}
