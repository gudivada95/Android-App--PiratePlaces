package edu.ecu.cs.pirateplaces;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public class PiratePlaceListActivity extends SingleFragmentActivity
        implements PiratePlaceFragment.Callbacks, PiratePlaceListFragment.Callbacks
{
    private static final int REQUEST_ERROR = 0;
    @Override
    protected Fragment createFragment()
    {
        return new PiratePlaceListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onPiratePlaceSelected(PiratePlace piratePlace)
    {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = PiratePlacePagerActivity.newIntent(this, piratePlace.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = PiratePlaceFragment.newInstance(piratePlace.getId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }

    @Override
    public void onPiratePlaceUpdated(PiratePlace piratePlace)
    {
        PiratePlaceListFragment listFragment = (PiratePlaceListFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }

    @Override
    public void onPiratePlaceDeleted(PiratePlace piratePlace)
    {
        FragmentManager fm = getSupportFragmentManager();
        PiratePlaceListFragment listFragment = (PiratePlaceListFragment)
                fm.findFragmentById(R.id.fragment_container);
        listFragment.updateUI();

        if (findViewById(R.id.detail_fragment_container) != null) {
            Fragment fragment = fm.findFragmentById(R.id.detail_fragment_container);
            getSupportFragmentManager().beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = apiAvailability.getErrorDialog(this,
                    errorCode,
                    REQUEST_ERROR,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            // Leave if services are unavailable.
                            finish();
                        }
                    });

            errorDialog.show();
        }
    }
}
