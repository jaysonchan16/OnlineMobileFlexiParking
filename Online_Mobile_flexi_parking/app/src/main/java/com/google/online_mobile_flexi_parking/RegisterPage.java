package com.google.online_mobile_flexi_parking;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.AuthResult;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.firebase.client.AuthData;
import com.google.firebase.auth.FirebaseUser;
import com.firebase.client.Firebase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RegisterPage extends AppCompatActivity {

    private Firebase mRef;
    Context ctx = this;
    EditText Password, Phone, Email, IC, FullName, CarPlate;
    String registerpassword, phone, email, ic, fullname, carplate;
    Button register;
    DatabaseReference mDatabase;
    FirebaseUser user;
    //List<String>carPlate=new ArrayList<String>();
    Map<String,Object> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        Password = (EditText) findViewById(R.id.txtRegisterPassword);
        Phone = (EditText) findViewById(R.id.txtPhone);
        Email = (EditText) findViewById(R.id.txtEmail);
        IC = (EditText) findViewById(R.id.txtICardNo);
        FullName = (EditText) findViewById(R.id.txtFullName);
        CarPlate = (EditText) findViewById(R.id.txtCarPlate);
        register = (Button) findViewById(R.id.btnRegister);

        SharedPreferences prefs = ctx.getSharedPreferences("register", MODE_PRIVATE);
        email = prefs.getString("email", "");
        registerpassword = prefs.getString("password", "");
        Email.setText(email);
        Password.setText(registerpassword);

     /*   accountQuoteAdapter.mDatabase=FirebaseDatabase.getInstance().getReference();
        accountQuoteAdapter.user=FirebaseAuth.getInstance().getCurrentUser();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = Phone.getText().toString();
                identitycard = IdentityCard.getText().toString();
                fullname = FullName.getText().toString();
                carplate = CarPlate.getText().toString();
                Account account = new Account(fullname, Integer.valueOf(phone), carplate, Integer.valueOf(identitycard));
                if(accountQuoteAdapter.user!=null) {
                    mDatabase.addChildEventListener(new AccountQuoteAdapter());
                    accountQuoteAdapter.add(account);
                    Toast.makeText(getBaseContext(), "Register Successfully...", Toast.LENGTH_LONG).show();
                    Intent in = new Intent(RegisterPage.this, MainActivity.class);
                    startActivity(in);
                    finish();
                }

            }
        });*/
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = Phone.getText().toString();
                ic = IC.getText().toString();
                fullname = FullName.getText().toString();
                carplate = CarPlate.getText().toString();
                //carPlate.add(carplate);
                map=new HashMap<String, Object>();
                map.put("CarPlate1",carplate);
                Account account=new Account(fullname,phone,ic,map);
                //Account account = new Account(fullname, phone, carplate, ic);
                if(user!=null) {
                    mDatabase.child(user.getUid()).setValue(account);
                    Toast.makeText(getBaseContext(), "Register Successfully...", Toast.LENGTH_LONG).show();
                    Intent in = new Intent(RegisterPage.this, MainActivity.class);
                    startActivity(in);
                    finish();
                }

            }
        });
    }

    public void onclickCancelRegister2(View view) {
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterPage.this, "Cancel Registration", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterPage.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(RegisterPage.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }}





