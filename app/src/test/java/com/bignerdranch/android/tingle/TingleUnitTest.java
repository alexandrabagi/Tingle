package com.bignerdranch.android.tingle;


import android.content.Context;

import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class TingleUnitTest {
    private static ThingsDB thingsDB;
    Thing t;
    //Context c= null;

    @Test
    public void thingTest() {

        //Testing Thing
        t = new Thing("aa", "bb");
        assertEquals("aa", t.getWhat());
        assertEquals("bb", t.getWhere());
        t.setWhat("aaa");
        t.setWhere("bbb");
        assertEquals("aaa", t.getWhat());
        assertEquals("bbb", t.getWhere());
        assertEquals("aaa is here: bbb", t.toString());
    }

    @Test
    public void thingsDBEmptyTest() {
        thingsDB = thingsDB.get();
        //Emptying database (fill is in the constructor)
        thingsDB.getThingsDB().removeAll(thingsDB.getThingsDB());
        //Testing the empty database
        assertEquals(0, thingsDB.getThingsDB().size());
        assertEquals(null, thingsDB.getThingByIdx(0));
        assertEquals(null, thingsDB.getThingByIdx(1));
        assertEquals("", thingsDB.listThings());
    }
    @Test
    public void thingsDBOneTest() {
        thingsDB = thingsDB.get();
        //Emptying database (fill is in the constructor)
        thingsDB.getThingsDB().removeAll(thingsDB.getThingsDB());
        //Database with a single Thing
        Thing t = new Thing("x", "y");
        thingsDB.addThing(t);
        assertEquals(1, thingsDB.getThingsDB().size());
        assertEquals("x", thingsDB.getThingByIdx(0).getWhat());
        assertEquals("y", thingsDB.getThingByIdx(0).getWhere());
        assertEquals(null, thingsDB.getThingByIdx(1));
        assertEquals("\nx is here: y", thingsDB.listThings());
        assertEquals(t ,thingsDB.getThing("x"));

    }
    @Test
    public void thingsDBTest() {
        thingsDB = thingsDB.get();
        assertEquals(12, thingsDB.getThingsDB().size());
        assertEquals("Cat", thingsDB.getThingByIdx(5).getWhat());
        assertEquals("A", thingsDB.getThingByIdx(6).getWhat());
        assertEquals("Basket", thingsDB.getThingByIdx(5).getWhere());
        thingsDB.addThing(new Thing("x", "y"));
        assertEquals(13, thingsDB.getThingsDB().size());
        thingsDB.deleteThing("Cat");
        assertEquals("A", thingsDB.getThingByIdx(5).getWhat());

    }

}