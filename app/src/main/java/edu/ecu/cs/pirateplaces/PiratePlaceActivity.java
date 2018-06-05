package edu.ecu.cs.pirateplaces;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.UUID;

/**
 * Edit a PiratePlace item
 *
 * @author Mark Hills (mhills@cs.ecu.edu)
 * @version 1.1
 */
public class PiratePlaceActivity extends SingleFragmentActivity
    implements PiratePlaceFragment.Callbacks
{
    private static final String EXTRA_PIRATE_PLACE_ID = "edu.ecu.cs.pirateplaces.piratePlaceId";

    public static Intent newIntent(Context packageContext, UUID id) {
        Intent intent = new Intent(packageContext, PiratePlaceActivity.class);
        intent.putExtra(EXTRA_PIRATE_PLACE_ID, id);
        return intent;
    }

    @Override
    protected Fragment createFragment()
    {
        UUID id = (UUID)getIntent().getSerializableExtra(EXTRA_PIRATE_PLACE_ID);
        return PiratePlaceFragment.newInstance(id);
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
