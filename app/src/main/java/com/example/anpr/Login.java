package com.example.anpr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anpr.dbtask.AnprConstant;
import com.example.anpr.dbtask.AnprManager;

public class Login extends AppCompatActivity {

    EditText txtlog,txtpass;
    Button btnsub,btnex;
    SQLiteDatabase sq;
    AnprManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtlog = findViewById(R.id.txtlogin);
        txtpass = findViewById(R.id.txtpass);
        btnsub = findViewById(R.id.btnsubmit);
        manager = new AnprManager(this);
        sq = manager.OpenDb();
        btnex = findViewById(R.id.btnexit);
        btnex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(Login.this, "Press again to quit", Toast.LENGTH_SHORT).show();
                finish();
                System.exit(0);

            }
        });
        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String id =txtlog.getText().toString().trim();
                String pass = txtpass.getText().toString().trim();
                if(id.equals("ekta")&& pass.equals("1234"))
                {
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    startActivity(intent);

                }
                else
                {

                    Cursor cursor = sq.query(AnprConstant.TABLE_NAME2, new String[]{AnprConstant.USER_NAME,AnprConstant.PASS}, null, null, null, null, null);

                    int flag = 0;
                    while (cursor.moveToNext())
                    {

                        String userId = cursor.getString(cursor.getColumnIndex(AnprConstant.USER_NAME));
                        String userPas = cursor.getString(cursor.getColumnIndex(AnprConstant.PASS));
                        String userPass = userPas.substring(userPas.length() - 2) + userPas.substring(0, userPas.length()-2);
                        if(id.equals(userId) && pass.equals(userPass))
                        {
                            flag = 1;
                            Intent intent = new Intent(Login.this,Police.class);
                            startActivity(intent);
                            break;
                        }

                    }
                    if(flag == 0)
                        Toast.makeText(Login.this, "You have enterred incorrect combination of User-Id and Password!" , Toast.LENGTH_SHORT).show();




                }



            }
        });


    }
}
