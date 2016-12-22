package com.google.online_mobile_flexi_parking;

import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Sheng on 10/12/2016.
 */

public class AccountQuoteAdapter implements ChildEventListener {
    String key;
    DatabaseReference mDatabase;
    FirebaseUser user;
    Account account;
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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

    public void add(Account account)
    {
        mDatabase.child(user.getUid()).setValue(account);
    }
}
