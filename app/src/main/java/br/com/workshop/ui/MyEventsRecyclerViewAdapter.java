package br.com.workshop.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.workshop.R;
import br.com.workshop.model.Talks;
import br.com.workshop.ui.EventsFragment.OnListFragmentInteractionListener;


public class MyEventsRecyclerViewAdapter extends RecyclerView.Adapter<MyEventsRecyclerViewAdapter.ViewHolder> {

    private final List<Talks> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyEventsRecyclerViewAdapter(List<Talks> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_events, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitle.setText(mValues.get(position).name);

        Glide.with(holder.itemView).load(mValues.get(position).image).into(holder.mImage);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImage;
        public final TextView mTitle;
        public Talks mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImage = view.findViewById(R.id.image);
            mTitle = view.findViewById(R.id.title);
        }

    }
}
