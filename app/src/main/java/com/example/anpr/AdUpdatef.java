package com.example.anpr;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.anpr.dbtask.AnprConstant;
import com.example.anpr.dbtask.AnprManager;

import java.util.ArrayList;

public class AdUpdatef extends Fragment
{
    AnprManager manager;
    SQLiteDatabase sq;
    ArrayList<String> plist;

    Button btnback,btnupdate;
    Spinner pspin;
    TextView email,phone;
    EditText newemail,newphone;
    String x;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ad_updatef, null, false);

        plist = new ArrayList<>();
        pspin = view.findViewById(R.id.policespin);
        manager = new AnprManager(getActivity());
        sq = manager.OpenDb();
        populateList();

        btnback = view.findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,new Ad_Managef()).commit();
            }
        });
        pspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String UserId = pspin.getItemAtPosition(position).toString();
                //Toast.makeText(getActivity(), ""+did, Toast.LENGTH_SHORT).show();
                fetchDetails(UserId);
                x= UserId;



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //did = driverspin.getItemAtPosition(0).toString();


            }
        });
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        newemail = view.findViewById(R.id.newemail);
        newphone = view.findViewById(R.id.newphone);
        btnupdate = view.findViewById(R.id.btnupdate);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDetails(x);
            }
        });

        return view;
    }
    public void populateList()
    {
        Cursor cursor = sq.query(AnprConstant.TABLE_NAME,null,null,null,null,null,null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(AnprConstant.USER_NAME));

            plist.add(id);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,plist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pspin.setAdapter(adapter);
    }
    public void fetchDetails(String uid)

    {
        String[] selcolumns = {AnprConstant.EMAIL,AnprConstant.PHONE};
        String[]args={uid};
        //String[]assign={data.tostring(),String.valueOf(fr),}
        // Cursor c1=sq.rawQuery()

        Cursor cursor = sq.query(AnprConstant.TABLE_NAME,selcolumns,AnprConstant.USER_NAME+"=?",args,null,null,null);

        while (cursor.moveToNext()) {
            String email1 = cursor.getString(cursor.getColumnIndex(AnprConstant.EMAIL));
            String phone1 = cursor.getString(cursor.getColumnIndex(AnprConstant.PHONE));
            email.setText(email1);
            phone.setText(phone1);

            // Toast.makeText(getActivity(), email + phone, Toast.LENGTH_SHORT).show();
        }




    }
    public void saveDetails(String id)
    {
        String em = newemail.getText().toString();
        if(em.isEmpty())
        {
            em = email.getText().toString();
            Toast.makeText(getActivity(), "Please write the same mail,if u don't wish to update email!", Toast.LENGTH_SHORT).show();
        }
        String ph = newphone.getText().toString();
        if(ph.isEmpty())
        {
            ph = phone.getText().toString();
            Toast.makeText(getActivity(), "Please write the same phone no.,if u don't wish to update phone no!", Toast.LENGTH_SHORT).show();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(AnprConstant.EMAIL,em);
        contentValues.put(AnprConstant.PHONE,ph);
        String[] args = {id};

        int rw = sq.update(AnprConstant.TABLE_NAME,contentValues,AnprConstant.USER_NAME+"=?",args);
        if(rw>0) {
            Toast.makeText(getActivity(), "Details updated sucessfully", Toast.LENGTH_SHORT).show();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, new Ad_Managef()).commit();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Update Unsucessful!");
            builder.setMessage("U may have left any one or both the field,empty!");
            builder.setPositiveButton("OK",null);
            AlertDialog dialog = builder.create();
            dialog.show();

        }


    }
}
