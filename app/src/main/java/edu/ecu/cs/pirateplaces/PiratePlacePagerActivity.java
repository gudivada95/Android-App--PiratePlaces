package edu.ecu.cs.pirateplaces;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;
import java.util.UUID;


public class PiratePlacePagerActivity extends AppCompatActivity
        implements PiratePlaceFragment.Callbacks
{
    private static final String EXTRA_PIRATE_PLACE_ID = "edu.ecu.cs.pirateplaces.piratePlaceId";

    private ViewPager mViewPager;
    private List<PiratePlace> mPiratePlaces;

    public static Intent newIntent(Context packageContext, UUID id) {
        Intent intent = new Intent(packageContext, PiratePlacePagerActivity.class);
        intent.putExtra(EXTRA_PIRATE_PLACE_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pirate_place_pager);

        UUID piratePlaceId = (UUID) getIntent().getSerializableExtra(EXTRA_PIRATE_PLACE_ID);
        mViewPager = (ViewPager) findViewById(R.id.pirate_place_view_pager);

        mPiratePlaces = PirateBase.getPirateBase(this).getPiratePlaces();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                PiratePlace piratePlace = mPiratePlaces.get(position);
                return PiratePlaceFragment.newInstance(piratePlace.getId());
            }

            @Override
            public int getCount() {
                return mPiratePlaces.size();
            }
        });

        for (int i = 0; i < mPiratePlaces.size(); i++) {
            if (mPiratePlaces.get(i).getId().equals(piratePlaceId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    public void onPiratePlaceUpdated(PiratePlace piratePlace)
    {

    }

    @Override
    public void onPiratePlaceDeleted(PiratePlace piratePlace)
    {
        this.finish();
    }
}
