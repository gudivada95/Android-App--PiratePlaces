package edu.ecu.cs.pirateplaces;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


public class PiratePlace implements Serializable
{

    private UUID mId;


    private String mPlaceName;


    private Date mLastVisited;

    private boolean has_location;
    private double latitude;
    private double longitude;


    public PiratePlace()
    {
        this(UUID.randomUUID());
    }


    public PiratePlace(UUID id)
    {
        mId = id;
        mPlaceName = "";
        mLastVisited = new Date();
        latitude = 0.0;
        longitude = 0.0;
    }

    public PiratePlace(String placeName, Date lastVisited)
    {
        mId = UUID.randomUUID();
        mPlaceName = placeName;
        mLastVisited = lastVisited;
    }

    public UUID getId()
    {
        return mId;
    }


    public String getPlaceName()
    {
        return mPlaceName;
    }


    public void setPlaceName(String placeName)
    {
        mPlaceName = placeName;
    }


    public Date getLastVisited()
    {
        return mLastVisited;
    }

    public double getLatitude() {
        return latitude;
    }
    public boolean isHas_location() {
        return has_location;
    }

    public void setHas_location(boolean has_location) {
        this.has_location = has_location;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public void setLastVisited(Date lastVisited)
    {
        mLastVisited = lastVisited;
    }


    public String getPhotoFilenameDir()
    {
        return "images/" + getId().toString();
    }




}
