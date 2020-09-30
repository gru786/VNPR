package com.example.anpr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.anpr.dbtask.AnprConstant;
import com.example.anpr.dbtask.AnprManager;

import java.util.zip.Inflater;

public class AddPolice extends Fragment
{
    EditText txtpname,txtusername,txtpass,txtmail,txtstreet,txtcity,txtstate,txtphone,txtage,rgender;
    Button btnsubmit,btnback;
    SQLiteDatabase sq;
    AnprManager manager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.addpolice,null,false);
        txtpname = view.findViewById(R.id.txtpolicename);
        txtusername = view.findViewById(R.id.txtpusername);
        txtpass = view.findViewById(R.id.txtppass);
        txtmail = view.findViewById(R.id.txtpmail);
        txtstreet = view.findViewById(R.id.txtpstreet);
        txtcity = view.findViewById(R.id.txtpcity);
        txtstate = view.findViewById(R.id.txtpstate);
        txtphone = view.findViewById(R.id.txtpphone);
        txtage = view.findViewById(R.id.txtpage);


        manager = new AnprManager(getActivity());
        sq = manager.OpenDb();



        btnsubmit = view.findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String pname =txtpname.getText().toString().trim();
                String passwor = txtpass.getText().toString().trim();
                String password = passwor.substring(2) + passwor.substring(0, 2);



                String userid = txtusername.getText().toString().trim();

                String email = txtmail.getText().toString().trim();
                String street = txtstreet.getText().toString().trim();
                String city = txtcity.getText().toString().trim();
                String state = txtstate.getText().toString().trim();
                String phone = txtphone.getText().toString().trim();
                String age = txtage.getText().toString().trim();
                String gender = "Male";


                ContentValues contentValues = new ContentValues();
                contentValues.put(AnprConstant.NAME,pname);
                contentValues.put(AnprConstant.USER_NAME,userid);
                contentValues.put(AnprConstant.PASS,password);
                contentValues.put(AnprConstant.EMAIL,email);
                contentValues.put(AnprConstant.STREET,street);
                contentValues.put(AnprConstant.CITY,city);
                contentValues.put(AnprConstant.STATE,state);
                contentValues.put(AnprConstant.PHONE,phone);
                contentValues.put(AnprConstant.AGE,age);
                contentValues.put(AnprConstant.GENDER,gender);



                long row = sq.insert(AnprConstant.TABLE_NAME,null,contentValues);
                if(row > 0)
                {
                    Toast.makeText(getActivity(), "Police details added successfully in PoliceDetails table" , Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getActivity(),"User name must be unique! Use mobile number instead",Toast.LENGTH_SHORT).show();


                ContentValues contentValues2 = new ContentValues();
                contentValues2.put(AnprConstant.USER_NAME,userid);
                contentValues2.put(AnprConstant.PASS,password);



                long row2 = sq.insert(AnprConstant.TABLE_NAME2,null,contentValues2);
                if(row2 > 0)
                {
                    //Toast.makeText(getActivity(), "Login details added successfully in Login table!" , Toast.LENGTH_SHORT).show();
                }
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,new AHomef()).commit();






            }
            public void hideKeyboard(Activity activity) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                //Find the currently focused view, so we can grab the correct window token from it.
                View view = activity.getCurrentFocus();
                //If no view currently has focus, create a new one, just so we can grab a window token from it
                if (view == null) {
                    view = new View(activity);
                }
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }







        });
        btnback = view.findViewById(R.id.btncancel);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,new AHomef()).commit();

            }
        });



        return view;
    }
}
