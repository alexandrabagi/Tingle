package com.bignerdranch.android.tingle.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.tingle.Thing;
import com.bignerdranch.android.tingle.database.ThingDbSchema.ThingTable;

import java.util.UUID;

public class ThingCursorWrapper extends CursorWrapper {
    public ThingCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Thing getThing() {
        String uuidString = getString(getColumnIndex(ThingTable.Cols.UUID));
        String title = getString(getColumnIndex(ThingTable.Cols.TITLE));
        String place = getString(getColumnIndex(ThingTable.Cols.PLACE));

        Thing thing = new Thing(UUID.fromString(uuidString), title, place);
        thing.setWhat(title);
        thing.setWhere(place);

        return thing;
    }
}
