package com.google.online_mobile_flexi_parking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Sheng on 8/11/2016.
 */

public class CustomAdapter extends BaseAdapter{
    Context c;
    ArrayList<Account> accounts;

    public  CustomAdapter(Context c, ArrayList<Account> accounts)
    {
        this.c=c;
        this.accounts=accounts;
    }

    @Override
    public int getCount() {
        return accounts.size();
    }

    @Override
    public Object getItem(int position) {
        return accounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        return view;
    }
}
