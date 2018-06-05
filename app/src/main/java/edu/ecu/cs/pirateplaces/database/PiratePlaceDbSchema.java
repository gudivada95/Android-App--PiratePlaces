package edu.ecu.cs.pirateplaces.database;


public class PiratePlaceDbSchema
{
    public static final class PiratePlaceTable
    {
        public static final String NAME = "places";

        public static final class Cols
        {
            public static final String UUID = "uuid";
            public static final String PLACE_NAME = "place_name";
            public static final String LAST_VISITED_DATE = "last_visited_date";
            public static final String HAS_LOCATION = "has_location";
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";
        }
    }
}
