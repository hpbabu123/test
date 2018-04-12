package com.siaans.skillindia;

/**
 * Created by HarshPanjwani on 12-04-2018.
 */

public class TCenter {
    String tp, tcname, tcspocname, mobno, emailid, pincode, address, bid, stime, etime, batchname;
    String seats;

    public TCenter(String tp,String tcname,String tcspocname,String mobno,String emailid,String pincode,String address,String bid,String stime,String etime,String batchname,String totalseats,String filled) {
        this.tp=tp;
        this.tcname=tcname;
        this.tcspocname=tcspocname;
        this.mobno=mobno;
        this.emailid=emailid;
        this.pincode=pincode;
        this.address=address;
        this.bid=bid;
        this.stime=stime;
        this.etime=etime;
        this.batchname=batchname;
        this.seats= String.valueOf(Integer.parseInt(totalseats)-Integer.parseInt(filled));

    }

    public String tp() {
        return tp;
    }

    public String tcname() {
        return tcname;
    }
    public String tcspocname() {
        return tcspocname;
    }public String  mobno() {
        return  mobno;
    }public String  emailid() {
        return emailid;
    }public String pincode() {
        return  pincode;
    }public String address() {
        return  address;
    }

    public String bid() {
        return bid;
    }public String stime() {
        return  stime;
    }public String etime() {
        return etime;
    }public String seats() {
        return seats;
    }
    public String batchname() {
        return batchname ;
    }
}
