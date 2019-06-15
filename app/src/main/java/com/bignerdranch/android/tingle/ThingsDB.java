package com.bignerdranch.android.tingle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.tingle.database.ThingBaseHelper;
import com.bignerdranch.android.tingle.database.ThingCursorWrapper;
import com.bignerdranch.android.tingle.database.ThingDbSchema;
import com.bignerdranch.android.tingle.database.ThingDbSchema.ThingTable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.UUID;

public class ThingsDB extends Observable {

    private static ThingsDB sThingsDB;


    private Context mContext;
    private SQLiteDatabase mDatabase;

    private ThingsDB(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ThingBaseHelper(mContext)
                .getWritableDatabase();
    }

    public static ThingsDB get(Context context) {
        if(sThingsDB == null) {
            sThingsDB = new ThingsDB(context);
        }
        return sThingsDB;
    }

// Methods:

    public List<Thing> getThingsDB() {
        List<Thing> things = new ArrayList<>();

        ThingCursorWrapper cursor = queryThings(null,null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                things.add(cursor.getThing());
                cursor.moveToNext();
            }
        }
        finally{
            cursor.close();
        }
        return things;
    }

    /*public void fillThingsDB() {
        thingsDB.add(new Thing("Android Phone", "Desk"));
        thingsDB.add(new Thing("Big Nerd book", "Desk"));
        thingsDB.add(new Thing("Sunglasses", "Kitchen drawer"));
        thingsDB.add(new Thing("Keys", "Pocket"));
        thingsDB.add(new Thing("Mouse", "Table"));
        thingsDB.add(new Thing("Cat", "Basket"));
        thingsDB.add(new Thing("A", "B"));
        thingsDB.add(new Thing("B", "C"));
        thingsDB.add(new Thing("C", "D"));
        thingsDB.add(new Thing("D", "E"));
        thingsDB.add(new Thing("E", "F"));
        thingsDB.add(new Thing("F", "G"));

    }*/

    public void addThing(Thing t) {
        ContentValues values = getContentValues(t);
        mDatabase.insert(ThingTable.NAME, null, values);

        this.setChanged();
        notifyObservers();
    }

    public void deleteThing(String whatString) {
        Iterator iterator = sThingsDB.getThingsDB().iterator();
        while (iterator.hasNext())
        {
            Thing currentWhat = (Thing) iterator.next(); // should be Thing object not string to be removed
            if (currentWhat.getWhat() == whatString)
                iterator.remove();
        }
        notifyObservers();
    }

    public Thing getThing(UUID thingId) {
        ThingCursorWrapper cursor = queryThings(
                ThingTable.Cols.UUID + " = ?",
                new String[] { thingId.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getThing();
        } finally {
            cursor.close();
        }
    }

    public void updateThing(Thing thing) {
        String uuidString = thing.getThingID().toString();
        ContentValues values = getContentValues(thing);

        mDatabase.update(ThingTable.NAME, values,
                ThingTable.Cols.UUID + " = ?",
                new String[] {uuidString});
    }

    public static ContentValues getContentValues(Thing thing) {
        ContentValues values = new ContentValues();
        values.put(ThingTable.Cols.UUID, thing.getThingID().toString());
        values.put(ThingTable.Cols.TITLE, thing.getWhat());
        values.put(ThingTable.Cols.PLACE, thing.getWhere());

        return values;
    }

    private ThingCursorWrapper queryThings(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ThingTable.NAME,
                null,  // columns - null selects all columns
                whereClause,
                whereArgs,
                null,  // groupBy
                null,   // havig
                null   // orderBy
        );
        return new ThingCursorWrapper(cursor);
    }
}


