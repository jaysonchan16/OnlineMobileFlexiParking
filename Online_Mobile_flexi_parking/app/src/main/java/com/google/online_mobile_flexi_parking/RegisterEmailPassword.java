package com.google.online_mobile_flexi_parking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterEmailPassword extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button buttonSignup;
    private ProgressDialog progressDialog;
    private Context ctx=this;

    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email_password);
        FirebaseApp.initializeApp(this);
        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextConfirmPassword=(EditText) findViewById(R.id.editTextConfirmPassword);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
    }

    private void registerUser() {

        //getting email and password from edit texts
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        String confirmpassword = editTextConfirmPassword.getText().toString().trim();
        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(confirmpassword)) {
            Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!(password.equals(confirmpassword))) {
            Toast.makeText(getApplicationContext(), "Password are not matching", Toast.LENGTH_SHORT).show();
            editTextEmail.setText("");
            editTextPassword.setText("");
            editTextConfirmPassword.setText("");
        } else {
            //if the email and password are not empty
            //displaying a progress dialog

            progressDialog.setMessage("Registering Please Wait...");
            progressDialog.show();

            //creating a new user
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if (task.isSuccessful()) {

                                SharedPreferences preferences=ctx.getSharedPreferences("register",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putString("email",email);
                                editor.putString("password",password);
                                editor.commit();

                             /*   session.SavePreferences("email",email);
                                session.SavePreferences("password",password);*/
                                //display some message here
                                Toast.makeText(RegisterEmailPassword.this, "Successfully registered", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterEmailPassword.this, RegisterPage.class));
                                finish();
                               // http://www.androidhive.info/2016/06/android-getting-started-firebase-simple-login-registration-auth/
                            } else {
                                //display some message here
                                Toast.makeText(RegisterEmailPassword.this, "Registration Error", Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });

        }
    }

    @Override
    public void onClick(View view) {
        //calling register method on click
        registerUser();
    }

    public void onclickCancelRegister(View view)
    {
        startActivity(new Intent(RegisterEmailPassword.this, MainActivity.class));
        finish();
    }
}