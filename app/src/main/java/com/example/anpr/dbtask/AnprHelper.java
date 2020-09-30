package com.example.anpr.dbtask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class AnprHelper extends SQLiteOpenHelper
{
    Context context;
    public AnprHelper(Context context,  String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(AnprConstant.POLICE_DETAIL_QUERY);
        Toast.makeText(context, "Police detail table created", Toast.LENGTH_SHORT).show();
        db.execSQL(AnprConstant.LOGIN_QUERY);
        Toast.makeText(context, "Login table created", Toast.LENGTH_SHORT).show();
        db.execSQL(AnprConstant.NUMBER_QUERY);
        Toast.makeText(context, "Number table created", Toast.LENGTH_SHORT).show();

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
