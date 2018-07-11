package br.com.workshop.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.workshop.R;
import br.com.workshop.model.Talks;

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Talks> talkList;

    public CustomPagerAdapter(Context context, List<Talks> talkList) {
        mContext = context;
        this.talkList = talkList;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return talkList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.fragment_events, container, false);

        ImageView image = itemView.findViewById(R.id.image);
        TextView title = itemView.findViewById(R.id.title);

        Glide.with(container).load(talkList.get(position).image).into(image);
        title.setText(talkList.get(position).name);

        container.addView(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("talk", talkList.get(position));
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((CardView) object);
    }

}