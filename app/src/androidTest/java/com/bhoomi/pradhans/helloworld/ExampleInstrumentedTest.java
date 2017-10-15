package com.bhoomi.pradhans.helloworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.bhoomi.pradhans.helloworld", appContext.getPackageName());

        SQLLiteConnection connection = new SQLLiteConnection(appContext);
        SQLiteDatabase writableDatabase = connection.getWritableDatabase();
        ArrayList<LatLongHolder> holders = new ArrayList<LatLongHolder>();
            holders.add(new LatLongHolder("11.1","12.2"));
        holders.add(new LatLongHolder("12.1","13.2"));
        holders.add(new LatLongHolder("13.1","14.2"));
        Type type = new TypeToken<List<LatLongHolder>>(){}.getType();
        String geo_points = new Gson().toJson(holders, type);

        ContentValues values = new ContentValues();
        values.put(BhutatraAttributes.DEVICE_ID,"testMachine");
        values.put(BhutatraAttributes.GEO_POINTS,geo_points);
        values.put(BhutatraAttributes.NAME,"FirstLocation");
        writableDatabase.insert("bhutatra",null,values);
    }
}
