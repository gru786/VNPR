package com.example.anpr;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.anpr.dbtask.AnprConstant;
import com.example.anpr.dbtask.AnprManager;

import java.sql.Driver;
import java.util.ArrayList;

public class AdViewf extends Fragment
{
    Spinner spinner;
    AnprManager manager;
    SQLiteDatabase sq;
    TextView name, email, phone, age, street,city,state,gender;
    ArrayList<String> dlist;
    Driver driver;
    Button btnback2;
    String x;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_viewf, null, false);

        dlist = new ArrayList<>();
        spinner = view.findViewById(R.id.policespin);
        btnback2 = view.findViewById(R.id.btnback);
        btnback2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, new Ad_Managef()).commit();
            }
        });
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        age = view.findViewById(R.id.age);
        city = view.findViewById(R.id.city);
        street = view.findViewById(R.id.street);
        state = view.findViewById(R.id.state);
        gender = view.findViewById(R.id.gender);

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
                //did = driverspin.getItemAtPosition(0).toString();


            }
        });







        return view;
    }

    public void populateList() {
        Cursor cursor = sq.query(AnprConstant.TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(AnprConstant.USER_NAME));


            dlist.add(id);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, dlist);
        spinner.setAdapter(adapter);
    }

    public void fetchDetails(String did) {

        String[] args = {did};
        //String[]assign={data.tostring(),String.valueOf(fr),}
        // Cursor c1=sq.rawQuery()

        Cursor cursor = sq.query(AnprConstant.TABLE_NAME, null, AnprConstant.USER_NAME + "=?", args, null, null, null);

        while (cursor.moveToNext()) {
            String nm = cursor.getString(cursor.getColumnIndex(AnprConstant.NAME));
            String em = cursor.getString(cursor.getColumnIndex(AnprConstant.EMAIL));
            String ph = cursor.getString(cursor.getColumnIndex(AnprConstant.PHONE));
            String st = cursor.getString(cursor.getColumnIndex(AnprConstant.STREET));
            String ci = cursor.getString(cursor.getColumnIndex(AnprConstant.CITY));
            String stat = cursor.getString(cursor.getColumnIndex(AnprConstant.STATE));
            String ag = cursor.getString(cursor.getColumnIndex(AnprConstant.AGE));
            String gen = cursor.getString(cursor.getColumnIndex(AnprConstant.GENDER));

            name.setText(nm);
            email.setText(em);
            phone.setText(ph);
            age.setText(ag);
            state.setText(stat);
            street.setText(st);
            city.setText(ci);
            gender.setText(gen);


            // Toast.makeText(getActivity(), email + phone, Toast.LENGTH_SHORT).show();
        }

    }
}
