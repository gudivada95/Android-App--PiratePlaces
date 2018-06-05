package edu.ecu.cs.pirateplaces;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import edu.ecu.cs.pirateplaces.databinding.FragmentPiratePlaceListBinding;
import edu.ecu.cs.pirateplaces.databinding.ListItemPiratePlaceBinding;

/**
 * Main fragment for the Pirate Place list
 *
 * @author Mark Hills (mhills@cs.ecu.edu)
 * @version 1.1
 */
public class PiratePlaceListFragment extends Fragment
{
    private RecyclerView mPiratePlaceRecyclerView;
    private PiratePlaceAdapter mAdapter;
    private Callbacks mCallbacks;

    /**
     * Required interface for hosting activities.
     */
    public interface Callbacks {
        void onPiratePlaceSelected(PiratePlace piratePlace);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        FragmentPiratePlaceListBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_pirate_place_list, container, false);

        mPiratePlaceRecyclerView = binding.piratePlaceRecyclerView;
        mPiratePlaceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return binding.getRoot();
    }

    protected void updateUI() {
        PirateBase pirateBase = PirateBase.getPirateBase(getActivity());
        List<PiratePlace> piratePlaces = pirateBase.getPiratePlaces();

        if (mAdapter == null) {
            mAdapter = new PiratePlaceAdapter(piratePlaces);
            mPiratePlaceRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setPlaces(piratePlaces);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_pirate_place_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.new_place:
                PiratePlace piratePlace = new PiratePlace();
                PirateBase.getPirateBase(getActivity()).addPiratePlace(piratePlace);
                mCallbacks.onPiratePlaceSelected(piratePlace);
                return true;
            case R.id.launch_map:
                Intent intent = new Intent(getActivity(), PirateMapsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onResume()
    {
        super.onResume();
        updateUI();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    private class PiratePlaceHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        private ListItemPiratePlaceBinding mBinding;

        public PiratePlaceHolder(ListItemPiratePlaceBinding binding)
        {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setViewModel(new PiratePlaceViewModel(PirateBase.getPirateBase(getContext()), getContext()));
            itemView.setOnClickListener(this);
        }

        public void bind(PiratePlace piratePlace)
        {
            mBinding.getViewModel().setPiratePlace(piratePlace);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v)
        {
            mCallbacks.onPiratePlaceSelected(mBinding.getViewModel().getPiratePlace());
        }
    }

    private class PiratePlaceAdapter extends RecyclerView.Adapter<PiratePlaceHolder>
    {
        private List<PiratePlace> mPiratePlaces;

        public PiratePlaceAdapter(List<PiratePlace> piratePlaces)
        {
            mPiratePlaces = piratePlaces;
        }

        public void setPlaces(List<PiratePlace> places) {
            mPiratePlaces = places;
        }

        @Override
        public PiratePlaceHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            ListItemPiratePlaceBinding binding = DataBindingUtil
                    .inflate(layoutInflater, R.layout.list_item_pirate_place,
                            parent, false);
            return new PiratePlaceHolder(binding);
        }

        @Override
        public void onBindViewHolder(PiratePlaceHolder holder, int position)
        {
            PiratePlace piratePlace = mPiratePlaces.get(position);
            holder.bind(piratePlace);
        }

        @Override
        public int getItemCount()
        {
            return mPiratePlaces.size();
        }
    }


}
