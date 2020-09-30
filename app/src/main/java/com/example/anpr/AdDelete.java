package com.example.anpr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class AdDelete extends Fragment
{
    Spinner spinner;
    TextView name,id;
    Button btndelete,btncancel;
    AnprManager manager;
    SQLiteDatabase sq;
    ArrayList<String> dlist;
    String x;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ad_delete, null, false);
        dlist = new ArrayList<>();
        manager = new AnprManager(getActivity());
        sq = manager.OpenDb();
        spinner = view.findViewById(R.id.pspin);
        name = view.findViewById(R.id.name);
        id = view.findViewById(R.id.id);
        btndelete = view.findViewById(R.id.btndelete);
        btncancel = view.findViewById(R.id.btncancel);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,new Ad_Managef()).commit();
            }
        });
        populateList();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String did = spinner.getItemAtPosition(position).toString();
                //Toast.makeText(getActivity(), ""+did, Toast.LENGTH_SHORT).show();
                fetchDetails(did);
                x= did;



            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //did = driverspin.getItemAtPosition(0).toString();


            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });



        return view;
    }

    public void populateList()
    {
        Cursor cursor = sq.query(AnprConstant.TABLE_NAME,null,null,null,null,null,null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(AnprConstant.USER_NAME));

            dlist.add(id);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,dlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    public void fetchDetails(String did) {
        String[] selcolumns = {AnprConstant.NAME};
        String[] args = {did};
        //String[]assign={data.tostring(),String.valueOf(fr),}
        // Cursor c1=sq.rawQuery()

        Cursor cursor = sq.query(AnprConstant.TABLE_NAME, selcolumns, AnprConstant.USER_NAME + "=?", args, null, null, null);

        while (cursor.moveToNext()) {
            String name1 = cursor.getString(cursor.getColumnIndex(AnprConstant.NAME));
            id.setText(did);
            name.setText(name1);


            // Toast.makeText(getActivity(), email + phone, Toast.LENGTH_SHORT).show();
        }
    }
    public void delete()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Police Details");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String[] args ={x};
                int rw = sq.delete(AnprConstant.TABLE_NAME,AnprConstant.USER_NAME+"=?",args);
                int rw2 = sq.delete(AnprConstant.TABLE_NAME2,AnprConstant.USER_NAME+"=?",args);
                if (rw>0 && rw2 >0) {
                    Toast.makeText(getActivity(), "Police details with User Id = " + x + " deleted sucessfully", Toast.LENGTH_SHORT).show();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame,new Ad_Managef()).commit();

                }

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setMessage("Are u sure to delete the record of Police whose User id is "+ x);
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
