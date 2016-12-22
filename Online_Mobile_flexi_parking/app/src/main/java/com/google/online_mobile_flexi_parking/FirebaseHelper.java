package com.google.online_mobile_flexi_parking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class FirebaseHelper extends Activity{
    private Firebase mRef=new Firebase("https://onlineflexiparking.firebaseio.com/");
    DatabaseReference db;
    Boolean saved;
    ArrayList<Account> accounts=new ArrayList<>();
    private FirebaseDatabase mDatabase;
    DatabaseReference reference;
    private Context context;
    private FirebaseAuth auth;
    public boolean checkList(ArrayList<Account> accounts)
    {
        if(accounts.isEmpty())
        {
            return false;
        }
        return true;
    }
    //Pass Database Reference
    public FirebaseHelper(DatabaseReference db)
    {
        this.db=db;
    }

   /* public void writeNewUser(long userID, String registerusername, String registerpassword,String phone, String email, String carplate, String fullname, String identitycard)
    {
        reference= FirebaseDatabase.getInstance().getReference();
        Account account = new Account(userID, registerusername, registerpassword, Integer.valueOf(phone), email, carplate, fullname, Integer.valueOf(identitycard));
        reference.child("User").child(String.valueOf(userID)).setValue(account);
    }*/
    public void incrementCounter() {
        mRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(final MutableData currentData) {
                if (currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }

                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean b, com.firebase.client.DataSnapshot dataSnapshot) {

            }
        });
    }

    public void Authentication(String email, String password)
    {
        auth=FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(FirebaseHelper.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(FirebaseHelper.this,"createUserWithEmail:onComplete:" + task.isSuccessful(),Toast.LENGTH_LONG).show();
                    if(!task.isSuccessful())
                    {
                        Toast.makeText(FirebaseHelper.this, "Authentication failed." + task.getException(),
                        Toast.LENGTH_SHORT).show();
                    }else {
                        startActivity(new Intent(FirebaseHelper.this, MainActivity.class));
                        finish();
            }
        }});}

    //Write if not null
 /*   public Boolean save(ArrayList<Account> accounts)//Account account)
    {
      if(accounts.isEmpty())
      {
          saved=false;
      }
        else
      {
          try{
              String identifier="User"+id;
              Firebase userRef=mRef.child("User");
              for(Account item:accounts)
              {
                  if (accounts.get(0).equals(null))
                  {
                   //   item.set_id(1);
                      accounts.add(0,1);
                  }
                  else
                  {
                      //item.set_id(item.get_id()+1);
                      accounts.add();
                  }
              }


              saved=true;
          }catch(DatabaseException ex)
          {
              ex.printStackTrace();
              saved=false;
          }
      }
        return saved;
    }*/

    //implement fetch data and fill arraylist
    private void fetchData(DataSnapshot dataSnapshot)
    {
        accounts.clear();

        for(DataSnapshot ds:dataSnapshot.getChildren())
        {
            Account account=ds.getValue(Account.class);
            accounts.add(account);
        }
    }

    //Retrieve
    public ArrayList<Account> retrieve()
    {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return accounts;
    }


   /* public boolean incrementCounter() {
        mRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(final MutableData currentData) {
                if (currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }

                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean b, com.firebase.client.DataSnapshot dataSnapshot) {
                if (firebaseError != null) {
                    Log.d("a", "Firebase counter increment failed.");
                } else {
                    Log.d("b", "Firebase counter increment succeeded.");
                }
            }
        });
    }*/
}
