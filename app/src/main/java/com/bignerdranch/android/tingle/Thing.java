package com.bignerdranch.android.tingle;

import java.util.UUID;

public class Thing {
    private String mWhat = null;
    private String mWhere = null;
    private UUID thingID;

    public Thing(String what, String where) {
        mWhat = what;
        mWhere = where;
        thingID = UUID.randomUUID();
    }

    public Thing(UUID id, String what, String where) {
        thingID = id;
        mWhat = what;
        mWhere = where;
    }

    @Override
    public String toString() { return oneLine("", "is here: "); }
    public String getWhat() { return mWhat; }
    public void setWhat(String what) { mWhat = what; }
    public String getWhere() { return mWhere; }
    public void setWhere(String where) { mWhere = where; }
    public String oneLine(String pre, String post) {
        return pre+mWhat + " "+post + mWhere;
    }

    public UUID getThingID() {
        return thingID;
    }

}
