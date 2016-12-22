package com.google.online_mobile_flexi_parking;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Sheng on 7/11/2016.
 */

@IgnoreExtraProperties
public class Account {
    private String _Phone;
    private String _IC;
    private String _FullName;
    private String _CarPlate1;
    private String _CarPlate2;
    private String _CarPlate3;
 //   private List _CarPlate;
    private Map<String,Object> _CarPlate;

    public Account(){}

  /*  public Account(String fullname, String phone, String carplate1, String IC)
    {
        this._Phone=phone;
        this._CarPlate1=carplate1;
        this._FullName=fullname;
        this._IC=IC;
    }*/

    public Account(String fullname, String phone, String IC, Map<String,Object> carplate)
    {
        this._Phone=phone;
        this._CarPlate=carplate;
        this._FullName=fullname;
        this._IC=IC;
    }

/*    public Account(String fullname, String phone, List carplate, String IC)
    {
        this._Phone=phone;
        this._CarPlate=carplate;
        this._FullName=fullname;
        this._IC=IC;
    }*/

    /*public Account(String username, String password, int phone, String email)
    {
        this._username=username;
        this._password=password;
        this._Phone=phone;
        this._Email=email;
    }*/

   /* public Account(String username, String password)
    {
        this._username=username;
        this._password=password;
    }*/

   /* public Account(List CarPlate)
    {
        this._CarPlate=CarPlate;
    }*/

   /* public Account(String carplate1,String carplate2,String carplate3)
    {
        this._CarPlate1=carplate1;
        this._CarPlate2=carplate2;
        this._CarPlate3=carplate3;
    }*/

    //public void set_CarPlate(List CarPlate){this._CarPlate=_CarPlate;}

    //public List<String> get_CarPlate(){return _CarPlate;}

    public void set_CarPlate(Map<String, Object> carplate)
    {
        this._CarPlate = carplate;
    }

    public Map<String, Object> get_CarPlate()
    {
        return this._CarPlate;
    }

    public void set_Phone(String _Phone) {
        this._Phone = _Phone;
    }

    public String get_Phone() {
        return _Phone;
    }

    public String get_IC() {
        return _IC;
    }

    public void set_IC(String _IC){this._IC=_IC;}

    public String get_FullName() {
        return _FullName;
    }

    public void set_FullName(String _FullName){ this._FullName=_FullName;}

/*    public void set_CarPlate1(String _CarPlate1) {
        this._CarPlate1 = _CarPlate1;
    }

    public String get_CarPlate1() {
        return _CarPlate1;
    }

    public void set_CarPlate2(String _CarPlate2) {
        this._CarPlate2 = _CarPlate2;
    }

    public String get_CarPlate2() {
        return _CarPlate2;
    }

    public void set_CarPlate3(String _CarPlate3) {
        this._CarPlate3 = _CarPlate3;
    }

    public String get_CarPlate3() {
        return _CarPlate3;
    }*/

/*    @Exclude
    public Map<String,Object> toMap()
    {
        HashMap<String,Object> result=new HashMap<>();
        result.put("_Phone",_Phone);
        result.put("_IC",_IC);
        result.put("_FullName",_FullName);
        result.put("_CarPlate",_CarPlate);
        return result;
    }*/
}
