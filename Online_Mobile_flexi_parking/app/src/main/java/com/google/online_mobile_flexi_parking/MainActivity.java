package com.google.online_mobile_flexi_parking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Context ctx=this;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, TestUser.class));
            finish();
        }
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

    }

    public void onclickRegistered(View view) {
        Intent registerpage = new Intent(MainActivity.this, RegisterEmailPassword.class);
        startActivity(registerpage);
    }

    public void onclickLogin(View view)
    {
       /* InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputEmail.getWindowToken(), 0);*/

        final String email = inputEmail.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                inputPassword.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(MainActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            SharedPreferences preferences=ctx.getSharedPreferences("register",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("email",email);
                            editor.putString("password",password);
                            editor.commit();

                            //   session.SavePreferences("email",email);
                         //   session.SavePreferences("password",password);
                            //display some message here
                            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, TestUser.class));
                            finish();
                            /*loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                            loginPrefsEditor = loginPreferences.edit();

                            saveLogin = loginPreferences.getBoolean("saveLogin", false);
                            if (saveLogin == true) {
                                inputEmail.setText(loginPreferences.getString("email", ""));
                                inputPassword.setText(loginPreferences.getString("password", ""));
                                saveLoginCheckBox.setChecked(true);
                            }
                            if (saveLoginCheckBox.isChecked()) {
                                loginPrefsEditor.putBoolean("saveLogin", true);
                                loginPrefsEditor.putString("email", email);
                                loginPrefsEditor.putString("password", password);
                                loginPrefsEditor.commit();
                            } else {
                                loginPrefsEditor.clear();
                                loginPrefsEditor.commit();
                            }*/
                        }
                    }
                });
    }

    public void onclickReset(View view)
    {
        Intent Resetpage=new Intent(MainActivity.this,ResetPage.class);
        startActivity(Resetpage);
    }

    public void onclickWatchTutorial(View view)
    {
        Intent Tutorial=new Intent(MainActivity.this,WatchTutorial.class);
        startActivity(Tutorial);
    }

}
