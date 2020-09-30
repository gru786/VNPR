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

public class PUpdateProfilef extends Fragment
{
    AnprManager manager;
    SQLiteDatabase sq;
    ArrayList<String> plist;

    Button btnback,btnupdate;
    Spinner pspin;
    TextView phone;
    EditText newphone;
    String x;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pupdateprofilef,null,false);
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
                fragmentTransaction.replace(R.id.framep,new P_Homef()).commit();
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

        phone = view.findViewById(R.id.phone);

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
        String[] selcolumns = {AnprConstant.PASS};
        String[]args={uid};
        //String[]assign={data.tostring(),String.valueOf(fr),}
        // Cursor c1=sq.rawQuery()

        Cursor cursor = sq.query(AnprConstant.TABLE_NAME,selcolumns,AnprConstant.USER_NAME+"=?",args,null,null,null);

        while (cursor.moveToNext()) {

            String userPas = cursor.getString(cursor.getColumnIndex(AnprConstant.PASS));
            String userPass = userPas.substring(userPas.length() - 2) + userPas.substring(0, userPas.length()-2);

            phone.setText(userPass);

            // Toast.makeText(getActivity(), email + phone, Toast.LENGTH_SHORT).show();
        }




    }
    public void saveDetails(String id)
    {


        String ph = newphone.getText().toString();

        if(ph.isEmpty())
        {
            ph = phone.getText().toString();

            Toast.makeText(getActivity(), "Please write the same password, if u don't wish to update it!", Toast.LENGTH_SHORT).show();
        }
        String password = ph.substring(2) + ph.substring(0, 2);
        ContentValues contentValues = new ContentValues();

        contentValues.put(AnprConstant.PASS,password);
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(AnprConstant.PASS,password);
        String[] args = {id};

        int rw = sq.update(AnprConstant.TABLE_NAME,contentValues,AnprConstant.USER_NAME+"=?",args);
        int rw2 = sq.update(AnprConstant.TABLE_NAME2,contentValues2,AnprConstant.USER_NAME+"=?",args);

        if(rw>0 && rw2>0) {
            Toast.makeText(getActivity(), "Password updated sucessfully", Toast.LENGTH_SHORT).show();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.framep, new P_Homef()).commit();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Update Unsucessful!");
            builder.setMessage("U may have left the field,empty!");
            builder.setPositiveButton("OK",null);
            AlertDialog dialog = builder.create();
            dialog.show();

        }


    }
}
