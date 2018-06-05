package edu.ecu.cs.pirateplaces.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import edu.ecu.cs.pirateplaces.PirateBase;
import edu.ecu.cs.pirateplaces.PiratePlace;

import static edu.ecu.cs.pirateplaces.database.PiratePlaceDbSchema.*;


public class PirateCursorWrapper extends CursorWrapper
{
    public PirateCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    public PiratePlace getPiratePlace()
    {
        String uuidString = getString(getColumnIndex(PiratePlaceTable.Cols.UUID));
        String placeName = getString(getColumnIndex(PiratePlaceTable.Cols.PLACE_NAME));
        long lastVisitedDate = getLong(getColumnIndex(PiratePlaceTable.Cols.LAST_VISITED_DATE));
        String hasLocation = getString(getColumnIndex(PiratePlaceTable.Cols.HAS_LOCATION));
        boolean has_location = false;
        if(hasLocation.equals("1"))
            has_location = true;
        else
            has_location = false;
        double latitude = getDouble(getColumnIndex(PiratePlaceTable.Cols.LATITUDE));
        double longitude = getDouble(getColumnIndex(PiratePlaceTable.Cols.LONGITUDE));

        PiratePlace piratePlace = new PiratePlace(UUID.fromString(uuidString));
        piratePlace.setPlaceName(placeName);
        piratePlace.setLastVisited(new Date(lastVisitedDate));
        piratePlace.setHas_location(has_location);
        piratePlace.setLatitude(latitude);
        piratePlace.setLongitude(longitude);

        return piratePlace;
    }

}
