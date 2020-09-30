package com.example.anpr;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.anpr.dbtask.AnprConstant;
import com.example.anpr.dbtask.AnprManager;

import java.util.Date;

public class DBentry extends Fragment
{
    EditText txt;
    Button btnsub,btnre;
    SQLiteDatabase sq;
    AnprManager manager;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.p_db_entry,null,false);
        String strtxt =getArguments().getString("A");
        strtxt.replaceAll("\\s","");
        txt = view.findViewById(R.id.edittxt);
        txt.setText(strtxt);

        btnre = view.findViewById(R.id.btnrescan);

        manager = new AnprManager(getActivity());
        sq = manager.OpenDb();

        btnre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framep,new Scanf()).commit();
            }
        });
        btnsub = view.findViewById(R.id.btnsubmit);
        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lic=txt.getText().toString().toUpperCase().trim();
                lic = lic.replaceAll("\\s","");
                int len = lic.length();
                String status = "Blacklisted";
                String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

                ContentValues contentValues = new ContentValues();
                contentValues.put(AnprConstant.LICENSE_NO,lic);
                contentValues.put(AnprConstant.STATUS,status);
                contentValues.put(AnprConstant.DATE_TIME,currentDateTimeString);
                long row=0;
                if(len==9 || len==10  ) {
                     row = sq.insert(AnprConstant.TABLE_NAME3,null,contentValues);
                }
                if(row > 0)
                {
                    Toast.makeText(getActivity(), lic + " added successfully in Number table" , Toast.LENGTH_SHORT).show();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.framep,new P_Homef()).commit();

                }
                else
                    Toast.makeText(getActivity(), "Something wrong here", Toast.LENGTH_SHORT).show();

            }
        });

        return view;

    }
}
