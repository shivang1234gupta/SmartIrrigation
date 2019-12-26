package com.myapp.android.smartirrigation;

public class Farmer
{
    private String Farmerid,Farmername,Farmerdate,mobileno;

    public Farmer() {
    }

    public Farmer(String farmerid, String farmername, String farmerdate,String mobileno) {
        Farmerid = farmerid;
        Farmername = farmername;
        Farmerdate = farmerdate;
        this.mobileno=mobileno;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getFarmerid() {
        return Farmerid;
    }

    public void setFarmerid(String farmerid) {
        Farmerid = farmerid;
    }

    public String getFarmername() {
        return Farmername;
    }

    public void setFarmername(String farmername) {
        Farmername = farmername;
    }

    public String getFarmerdate() {
        return Farmerdate;
    }

    public void setFarmerdate(String farmerdate) {
        Farmerdate = farmerdate;
    }
}
