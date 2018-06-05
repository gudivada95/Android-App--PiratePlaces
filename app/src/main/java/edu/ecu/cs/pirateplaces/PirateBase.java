package edu.ecu.cs.pirateplaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import edu.ecu.cs.pirateplaces.database.PirateBaseHelper;
import edu.ecu.cs.pirateplaces.database.PirateCursorWrapper;
import edu.ecu.cs.pirateplaces.database.PiratePlaceDbSchema;

import static edu.ecu.cs.pirateplaces.database.PiratePlaceDbSchema.*;


public class PirateBase
{

    private static PirateBase sPirateBase;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static PirateBase getPirateBase(Context context)
    {
        if (sPirateBase == null) {
            sPirateBase = new PirateBase(context);
        }
        return sPirateBase;
    }

    PirateBase(Context context)
    {
        mContext = context.getApplicationContext();
        mDatabase = new PirateBaseHelper(mContext).getWritableDatabase();
    }

    public List<PiratePlace> getPiratePlaces()
    {
        List<PiratePlace> piratePlaces = new ArrayList<>();

        PirateCursorWrapper cursor = queryPiratePlaces(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                piratePlaces.add(cursor.getPiratePlace());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return piratePlaces;
    }


    public PiratePlace getPiratePlace(UUID id)
    {
        PirateCursorWrapper cursor = queryPiratePlaces(
                PiratePlaceTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getPiratePlace();
        } finally {
            cursor.close();
        }
    }


    public void addPiratePlace(PiratePlace piratePlace)
    {
        ContentValues values = PirateBase.getContentValues(piratePlace);
        mDatabase.insert(PiratePlaceTable.NAME, null, values);
    }

    public void updatePiratePlace(PiratePlace piratePlace)
    {
        String uuidString = piratePlace.getId().toString();
        ContentValues values = PirateBase.getContentValues(piratePlace);

        mDatabase.update(PiratePlaceTable.NAME, values,
                PiratePlaceTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    public void deletePiratePlace(PiratePlace piratePlace)
    {
        String uuidString = piratePlace.getId().toString();

        mDatabase.delete(PiratePlaceTable.NAME,
                PiratePlaceTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private PirateCursorWrapper queryPiratePlaces(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(
                PiratePlaceTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        return new PirateCursorWrapper(cursor);
    }


    private static ContentValues getContentValues(PiratePlace piratePlace)
    {
        ContentValues values = new ContentValues();
        values.put(PiratePlaceTable.Cols.UUID, piratePlace.getId().toString());
        values.put(PiratePlaceTable.Cols.PLACE_NAME, piratePlace.getPlaceName());
        values.put(PiratePlaceTable.Cols.LAST_VISITED_DATE, piratePlace.getLastVisited().getTime());
        values.put(PiratePlaceTable.Cols.LATITUDE, piratePlace.getLatitude());
        values.put(PiratePlaceTable.Cols.LONGITUDE, piratePlace.getLongitude());
        if(piratePlace.isHas_location())
            values.put(PiratePlaceTable.Cols.HAS_LOCATION, 1);
        else
            values.put(PiratePlaceTable.Cols.HAS_LOCATION, 0);

        return values;
    }

    public File getPhotoFileDir(PiratePlace piratePlace)
    {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, piratePlace.getPhotoFilenameDir());
    }

    public List<File> getPhotoFiles(PiratePlace piratePlace)
    {
        File filesDir = getPhotoFileDir(piratePlace);

        if (! filesDir.exists() || ! filesDir.isDirectory() ) {
            return new ArrayList<>();
        }

        File[] files = filesDir.listFiles();
        List<File> fileList = new ArrayList<>();
        for (int i = 0 ; i < files.length; ++i) {
            fileList.add(files[i]);
        }

        return fileList;
    }

    public File getNewPhotoFile(PiratePlace piratePlace)
    {
        File filesDir = getPhotoFileDir(piratePlace);
        if (! filesDir.exists()) {
            filesDir.mkdirs();
        }
        String newFileName = "IMG_" + UUID.randomUUID().toString() + ".jpg";
        return new File(filesDir, newFileName);
    }
}
