package com.example.anpr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.zip.Inflater;

public class whitelist extends Fragment
{
    Spinner spinner;
    AnprManager manager;
    SQLiteDatabase sq;
    TextView license, status,datetime;
    ArrayList<String> dlist;
    Button btnwh,btnback2;
    String x;
    LocationManager locationManager;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)  {
        View view = inflater.inflate(R.layout.activity_whitelistf,null,false);
        dlist = new ArrayList<>();
        spinner = view.findViewById(R.id.policespin);
        btnback2 = view.findViewById(R.id.btnback);
        license = view.findViewById(R.id.lpn);
        datetime = view.findViewById(R.id.dandt);
        status = view.findViewById(R.id.stat);
        btnwh = view.findViewById(R.id.btnwhite);







        btnwh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });



        btnback2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framep, new P_Homef()).commit();
            }
        });
        manager = new AnprManager(getActivity());
        sq = manager.OpenDb();
        populateList();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pid = spinner.getItemAtPosition(position).toString();
                //Toast.makeText(getActivity(), ""+did, Toast.LENGTH_SHORT).show();
                fetchDetails(pid);
                x = pid;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //did = policespin.getItemAtPosition(0).toString();


            }
        });




        return view;
    }


    public void delete()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Whitelist");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String[] args ={x};
                int rw = sq.delete(AnprConstant.TABLE_NAME3,AnprConstant.LICENSE_NO+"=?",args);

                if (rw>0 ) {
                    Toast.makeText(getActivity(), "Vehicle with license no = " + x + " whitelisted sucessfully", Toast.LENGTH_SHORT).show();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.framep,new whitelist()).commit();

                }

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setMessage("Are u sure to whitelist the vehicle with no = "+ x);
        AlertDialog dialog = builder.create();
        dialog.show();

    }
    public void populateList() {
        Cursor cursor = sq.query(AnprConstant.TABLE_NAME3, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(AnprConstant.LICENSE_NO));


            dlist.add(id);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, dlist);
        spinner.setAdapter(adapter);
    }
    public void fetchDetails(String did) {

        String[] args = {did};
        //String[]assign={data.tostring(),String.valueOf(fr),}
        // Cursor c1=sq.rawQuery()

        Cursor cursor = sq.query(AnprConstant.TABLE_NAME3, null, AnprConstant.LICENSE_NO + "=?", args, null, null, null);

        while (cursor.moveToNext()) {
            String li = cursor.getString(cursor.getColumnIndex(AnprConstant.LICENSE_NO));
            String st = cursor.getString(cursor.getColumnIndex(AnprConstant.STATUS));
            String dt = cursor.getString(cursor.getColumnIndex(AnprConstant.DATE_TIME));


            license.setText(li);
            status.setText(st);
            datetime.setText(dt);
        }
    }
}
