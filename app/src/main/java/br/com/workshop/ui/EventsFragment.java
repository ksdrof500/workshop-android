package br.com.workshop.ui;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.workshop.R;
import br.com.workshop.model.DataBase;
import br.com.workshop.model.Talks;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class EventsFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    public EventsFragment() {
    }

    @SuppressWarnings("unused")
    public static EventsFragment newInstance(int columnCount) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.list);
        RecyclerView.LayoutManager verticalManager = new LinearLayoutManager(getContext(),
                OrientationHelper.VERTICAL, false);
        recyclerView.setLayoutManager(verticalManager);

        RecyclerView recyclerViewBanner = view.findViewById(R.id.banner);

        RecyclerView.LayoutManager horizontalManager = new LinearLayoutManager(getContext(),
                OrientationHelper.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalManager);

        SQLiteDatabase db = new DataBase(getActivity()).getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT name, description, image FROM talks", null);
        List<Talks> talks = new ArrayList<>();

        while(cursor.moveToNext()) {
         talks.add(new Talks(cursor.getString(0),
                 cursor.getString(1),
                 cursor.getString(2)));
        }

        db.close();

        recyclerView.setAdapter(new MyEventsRecyclerViewAdapter(talks, mListener));
        recyclerViewBanner.setAdapter(new MyEventsRecyclerViewAdapter(talks, mListener));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Talks item);
    }
}
