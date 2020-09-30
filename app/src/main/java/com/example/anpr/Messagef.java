package com.example.anpr;

import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.anpr.dbtask.AnprConstant;
import com.example.anpr.dbtask.AnprManager;

import java.util.ArrayList;

public class Messagef extends Fragment
{
    Spinner spinner;

    Button btnsend;
    EditText msgtext;
    SQLiteDatabase sq;
    AnprManager manager;
    ArrayList<String> dlist,clist;
    String idSelected,phone1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.messagef,null,false);
        manager = new AnprManager(getActivity());
        sq = manager.OpenDb();
        msgtext = view.findViewById(R.id.msgtext);

        spinner = view.findViewById(R.id.spinId);


        dlist = new ArrayList<>();
        clist = new ArrayList<>();
        populateSpinDriver();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idSelected = spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //idSelected = spinner.getItemAtPosition(0).toString();

            }
        });
        btnsend=view.findViewById(R.id.btnsend);
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] selcolumns = {AnprConstant.PHONE};
                String[]args={idSelected};


                Cursor cursor = sq.query(AnprConstant.TABLE_NAME,selcolumns,AnprConstant.USER_NAME+"=?",args,null,null,null);

                while (cursor.moveToNext()) {

                    phone1 = cursor.getString(cursor.getColumnIndex(AnprConstant.PHONE));

                }


                String msg = msgtext.getText().toString();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),1,intent,PendingIntent.FLAG_ONE_SHOT);

                SmsManager smsManager =SmsManager.getDefault();
                smsManager.sendTextMessage(phone1,null,msg,pendingIntent,null);
                Toast.makeText(getActivity(), "Message sent sucessfully", Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }
    public void populateSpinDriver()
    {
        Cursor cursor = sq.query(AnprConstant.TABLE_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext())
        {
            String id = cursor.getString(cursor.getColumnIndex(AnprConstant.USER_NAME));
            dlist.add(id);


        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,dlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
