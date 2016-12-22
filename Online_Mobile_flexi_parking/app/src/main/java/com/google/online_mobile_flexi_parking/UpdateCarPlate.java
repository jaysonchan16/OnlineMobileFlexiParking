package com.google.online_mobile_flexi_parking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpdateCarPlate extends AppCompatActivity {
    ListView lv;
    EditText nameTxt;
    Button addbtn, updatebtn, deleteBtn, clearBtn,backBtn;
    List<String> names=new ArrayList<>();
    ArrayAdapter<String>adapter;
    DatabaseReference mDatabase;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_car_plate);

        lv = (ListView) findViewById(R.id.listView1);
        nameTxt = (EditText) findViewById(R.id.nameText);
        addbtn = (Button) findViewById(R.id.addbtn);
        updatebtn = (Button) findViewById(R.id.updatebtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        clearBtn = (Button) findViewById(R.id.clearBtn);
        backBtn=(Button)findViewById(R.id.backUpdate);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        mDatabase.child(user.getUid()).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Account acc=new Account();
            Map<String,String> map=(Map)dataSnapshot.getValue();
          /*  if(acc.get_CarPlate1().equals(null)) {
                names.add(map.get("_CarPlate1"));
             //   acc.set_CarPlate1(names);
            }
            else
            {
                names.add(map.get("_CarPlate"));
           //     acc.set_CarPlate1(names);
            }*/
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }});

        //ADAPTER
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, names);
        lv.setAdapter(adapter);

        //SET SELECTED ITEM
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                nameTxt.setText(names.get(pos));
            }
        });

        //HANDLE EVENTS
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateCarPlate.this, TestUser.class));
                finish();
            }
        });
    }

    //ADD
    private void add() {
        String name = nameTxt.getText().toString();
        if (!name.isEmpty() && name.length() > 0) {
            //ADD
          adapter.add(name);
          mDatabase.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                                                   @Override
                                                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                                                       Account phoneAcc = new Account();
                                                                       Map<String, String> map = (Map) dataSnapshot.getValue();
                                                                       phoneAcc.set_FullName(map.get("_FullName"));
                                                                   //    phoneAcc.set_CarPlate1(map.get("_CarPlate1"));
                                                                       phoneAcc.set_IC(map.get("_IC"));
                                                                       //        phoneAcc.set_Phone(txtPhone);
                                                                   }

                                                                   @Override
                                                                   public void onCancelled(DatabaseError databaseError) {

                                                                   }
/*        mDatabase.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Account phoneAcc=new Account();
                    Map<String,String> map=(Map)dataSnapshot.getValue();
                    phoneAcc.set_FullName(map.get("_FullName"));
                    phoneAcc.set_CarPlate1(map.get("_CarPlate1"));
                    phoneAcc.set_IC(map.get("_IC"));
                    phoneAcc.set_Phone(txtPhone);
                    mDatabase.child(user.getUid()).setValue(phoneAcc);
                    Toast.makeText(ChangeEmailPassword.this, "Phone is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ChangeEmailPassword.this, MainActivity.class));
                    finish();*/
              });
            //REFRESH
            adapter.notifyDataSetChanged();

            nameTxt.setText("");

            Toast.makeText(getApplicationContext(), "Added " + name, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "!! Nothing to Add", Toast.LENGTH_SHORT).show();
        }
    }

    //UPDATE
    private void update()
    {
        String name=nameTxt.getText().toString();

        //GET POS OF SELECTED ITEM
        int pos=lv.getCheckedItemPosition();

        if(!name.isEmpty()&&name.length()>0)
        {
            //REMOVE ITEM
            adapter.remove(names.get(pos));

            //INSERT
            adapter.insert(name,pos);

            //REFRESH
            adapter.notifyDataSetChanged();

            Toast.makeText(getApplicationContext(), "Updated " + name, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "!! Nothing to update " + name, Toast.LENGTH_SHORT).show();
        }
    }

    //DELETE
    private void delete()
    {
        int pos=lv.getCheckedItemPosition();

        if(pos>-1)
        {
            //remove
            adapter.remove(names.get(pos));

            //REFRESH
            adapter.notifyDataSetChanged();

            nameTxt.setText("");
            Toast.makeText(getApplicationContext(), "Deleted ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"!! Nothing to Delete ",Toast.LENGTH_SHORT).show();
        }
    }

    //CLEAR
    private void clear()
    {
        adapter.clear();
    }

}
