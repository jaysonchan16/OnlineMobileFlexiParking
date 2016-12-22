package com.google.online_mobile_flexi_parking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class AddModifyDeleteCarPlate extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputCar1, inputCar2, inputCar3;
    private Button btnSave;
    private DatabaseReference mDatabase;
    private String userId;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_delete_car_plate);

        txtDetails = (TextView) findViewById(R.id.txt_user);
        inputCar1 = (EditText) findViewById(R.id.CarPlate1);
        inputCar2 = (EditText) findViewById(R.id.CarPlate2);
        inputCar3=(EditText) findViewById(R.id.CarPlate3);
        btnSave = (Button) findViewById(R.id.btn_save);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        // Save / update the user
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    String car1 = inputCar1.getText().toString().trim();
                String car2 = inputCar2.getText().toString().trim();
                String car3=inputCar3.getText().toString().trim();*/

                // Check for already existed userId
                if (TextUtils.isEmpty(userId)) {
                    //AddCarPlate(car1,car2,car3);
           //         updateAccount();
                }/* else {
                    updateAccount(car1, car2, car3);
                }*/
            }
        });

        toggleButton();
    }

    // Changing button text
    private void toggleButton() {
        if (TextUtils.isEmpty(userId)) {
            //btnSave.setText("Save");
            btnSave.setText("Update");
        }/* else {
            btnSave.setText("Update");
        }*/
    }

    /**
     * Creating new user node under 'users'
     */
    private void AddCarPlate(String car1, String car2, String car3) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mDatabase.getKey();
        }

   /*     Account account = new Account(car1,car2,car3);

        mDatabase.child(userId).setValue(account);*/

        addAccountChangeListener();
    }

    /**
     * User data change listener
     */
    private void addAccountChangeListener() {
        // User data change listener
        mDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Account account = dataSnapshot.getValue(Account.class);

                // Check for null
                if (account == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }

               // Log.e(TAG, "User data is changed!" + account.get_CarPlate1() + ", " + account.get_CarPlate2()+ ", " +account.get_CarPlate3());

                // Display newly updated name and email
              //  txtDetails.setText(account.get_CarPlate1() + ", " + account.get_CarPlate2() + ", " + account.get_CarPlate3());

                // clear edit text
                inputCar1.setText("");
                inputCar2.setText("");
                inputCar3.setText("");

                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    private void saveAccount() {


                String Car1=inputCar1.getText().toString();
                String Car2=inputCar2.getText().toString();
                String Car3=inputCar3.getText().toString();
                if (!TextUtils.isEmpty(Car1))
                    mDatabase.child(userId).child(user.getUid()).setValue(Car1);

                if (!TextUtils.isEmpty(Car2))
                    mDatabase.child(userId).child(user.getUid()).setValue(Car2);

                if (!TextUtils.isEmpty(Car3))
                    mDatabase.child(userId).child(user.getUid()).setValue(Car3);
            }




        // updating the user via child nodes
        /*if (!TextUtils.isEmpty(car1))
            mDatabase.child(userId).child(user.getUid()).setValue(car1);

        if (!TextUtils.isEmpty(car2))
            mDatabase.child(userId).child(user.getUid()).setValue(car2);

        if (!TextUtils.isEmpty(car3))
            mDatabase.child(userId).child(user.getUid()).setValue(car3);*/

    public void showAccountInformation()
    {
        mDatabase.child(user.getUid()).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
        Account account=new Account();
        Map<String, String> map=(Map)dataSnapshot.getValue();
     /*   account.set_CarPlate1(map.get("_CarPlate1"));
        account.set_CarPlate2(map.get("_CarPlate2"));
        account.set_CarPlate3(map.get("_CarPlate3"));

        inputCar1.setText(account.get_CarPlate1());
        inputCar2.setText(account.get_CarPlate2());
        inputCar3.setText(account.get_CarPlate3());*/

        }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
}
}