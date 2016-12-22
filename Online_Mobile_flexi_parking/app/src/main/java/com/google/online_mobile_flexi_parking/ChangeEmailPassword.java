package com.google.online_mobile_flexi_parking;

import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.core.view.Change;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeEmailPassword extends AppCompatActivity {

    private Button btnChangeEmail, btnChangePassword,
            changeEmail, changePassword, back, btnChangePhone, btnChangeCarPlate, changePhone,
            changeCarPlate, signOut;

    private EditText oldEmail, newEmail, password, newPassword, oldPhone, newPhone, confirmPassword;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    DatabaseReference mDatabase;
    FirebaseUser user;
    private String txtPhone;
    private String name, phone, carplate,ic;
    private HashMap<String,Object> names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(ChangeEmailPassword.this, MainActivity.class));
                    finish();
                }
            }
        };

        btnChangeEmail = (Button) findViewById(R.id.change_email_button);
        btnChangePassword = (Button) findViewById(R.id.change_password_button);
        btnChangePhone=(Button)findViewById(R.id.change_phone_button);
        changeEmail = (Button) findViewById(R.id.changeEmail);
        changePassword = (Button) findViewById(R.id.changePass);
        changePhone=(Button) findViewById(R.id.changePhone);
        signOut = (Button) findViewById(R.id.sign_out);
        back=(Button)findViewById(R.id.btnback1);

        oldEmail = (EditText) findViewById(R.id.old_email);
        newEmail = (EditText) findViewById(R.id.new_email);
        password = (EditText) findViewById(R.id.password);
        newPassword = (EditText) findViewById(R.id.newPassword);
        confirmPassword=(EditText)findViewById(R.id.confirmpassword);
        oldPhone=(EditText)findViewById(R.id.old_phone);
        newPhone=(EditText)findViewById(R.id.new_phone);


        oldEmail.setVisibility(View.GONE);
        newEmail.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        newPassword.setVisibility(View.GONE);
        confirmPassword.setVisibility(View.GONE);
        changeEmail.setVisibility(View.GONE);
        changePassword.setVisibility(View.GONE);
        oldPhone.setVisibility(View.GONE);
        newPhone.setVisibility(View.GONE);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangeEmailPassword.this, TestUser.class));
                finish();
            }
        });

        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.VISIBLE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                confirmPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.VISIBLE);
                changePassword.setVisibility(View.GONE);
                changePhone.setVisibility(View.GONE);
                oldPhone.setVisibility(View.GONE);
                newPhone.setVisibility(View.GONE);
            }
        });

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (user != null && !newEmail.getText().toString().trim().equals("")) {
                    user.updateEmail(newEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ChangeEmailPassword.this, "Email address is updated. Please sign in with new email id!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(ChangeEmailPassword.this, MainActivity.class));
                                        finish();
                                        signOut();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(ChangeEmailPassword.this, "Failed to update email!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else if (newEmail.getText().toString().trim().equals("")) {
                    newEmail.setError("Enter email");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.VISIBLE);
                confirmPassword.setVisibility(View.VISIBLE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.VISIBLE);
                changePhone.setVisibility(View.GONE);
                oldPhone.setVisibility(View.GONE);
                newPhone.setVisibility(View.GONE);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (user != null && !newPassword.getText().toString().trim().equals("")) {
                    if (newPassword.getText().toString().trim().length() < 6) {
                        newPassword.setError("Password too short, enter minimum 6 characters");
                        progressBar.setVisibility(View.GONE);
                    } else if (!(newPassword.getText().toString().trim().equals(confirmPassword.getText().toString().trim()))) {
                        newPassword.setError("Password are not matching");
                        progressBar.setVisibility(View.GONE);
                    } else {
                        user.updatePassword(newPassword.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ChangeEmailPassword.this, "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(ChangeEmailPassword.this, MainActivity.class));
                                            finish();
                                            signOut();
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(ChangeEmailPassword.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    }
                } else if (newPassword.getText().toString().trim().equals("")) {
                    newPassword.setError("Enter password");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnChangePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                confirmPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.GONE);
                changePhone.setVisibility(View.VISIBLE);
                oldPhone.setVisibility(View.GONE);
                newPhone.setVisibility(View.VISIBLE);
            }
        });


        changePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                txtPhone = newPhone.getText().toString().trim();
                if (!txtPhone.equals("")) {
                    mDatabase.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                         //   String key=mDatabase.child(user.getUid()).getKey();
                            Map<String,Object> names=new HashMap<String, Object>();
                            Account phoneAcc=new Account();
                            for(DataSnapshot message:dataSnapshot.getChildren())
                            {
                                /*name=(String)message.child("_FullName").getValue();
                                carplate=(String)message.child("_CarPlate").child("CarPlate1").getValue();
                                ic=(String)message.child("_IC").getValue();
                                phone=(String)message.child("_Phone").getValue();*/
                                Account acc=dataSnapshot.getValue(Account.class);
                                acc.set_Phone(txtPhone);
                            }
                            /*phoneAcc.set_FullName(name);
                            phoneAcc.set_IC(ic);
                            phoneAcc.set_Phone(phone);
                            names.put("_CarPlate",carplate);
                            phoneAcc.set_CarPlate(names);*/
                            /*
                            Map<String,String> map=(Map)dataSnapshot.getValue();
                            phoneAcc.set_FullName(map.get("_FullName"));
                            //phoneAcc.set_CarPlate1(map.get("_CarPlate1"));
                            phoneAcc.set_CarPlate("_CarPlate",map.get("CarPlate1"));
                            //names.put("CarPlate",map.get("_CarPlate"));
                            phoneAcc.set_CarPlate(names);
                            phoneAcc.set_IC(map.get("_IC"));
                            phoneAcc.set_Phone(txtPhone);
                            mDatabase.child(user.getUid()).setValue(phoneAcc);
                           // List<String> sample1 = phoneAcc.getList();
                       /*     mDatabase.child(user.getUid()).setValue(phoneAcc.get_CarPlate());
                            mDatabase.child(user.getUid()).removeValue();*/
                         /*   Map<String,Object> postValues=phoneAcc.toMap();
                            Map<String,Object> childUpdates=new HashMap<>();
                            childUpdates.put(key,postValues);
                            mDatabase.updateChildren(childUpdates);*/
                            mDatabase.child(user.getUid()).setValue(phoneAcc, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    Toast.makeText(ChangeEmailPassword.this, "Phone Number is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ChangeEmailPassword.this, MainActivity.class));
                                    finish();
                                    signOut();
                                }
                            });

                            //signOut();
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                } else if (txtPhone.equals("")) {
                    newPhone.setError("Enter phone number");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


      /*  signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });*/

    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}