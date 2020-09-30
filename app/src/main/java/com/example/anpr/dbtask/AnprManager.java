package com.example.anpr.dbtask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.fragment.app.FragmentActivity;

public class AnprManager
{
    AnprHelper helper;
    Context context;
    public AnprManager(FragmentActivity context)
    {
        this.context = context;
        helper = new AnprHelper(context,AnprConstant.DB_NAME,null,AnprConstant.DB_VERSION);


    }
    public SQLiteDatabase OpenDb()
    {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        return sqLiteDatabase;

    }
    public void closeDb()
    {
        if(helper!=null)
            helper.close();

    }

}
