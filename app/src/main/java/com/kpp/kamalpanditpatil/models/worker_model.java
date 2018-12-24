package com.kpp.kamalpanditpatil.models;

public class worker_model {
    String gender,department,IFSC_code,bankname,address,aadhar,name,accountno,PF,ESIC;
    int id_no;

    public String getPF() {
        return PF;
    }

    public void setPF(String PF) {
        this.PF = PF;
    }

    public String getESIC() {
        return ESIC;
    }

    public void setESIC(String ESIC) {
        this.ESIC = ESIC;
    }

    public worker_model() {
    }

    public worker_model(String gender, String department, String IFSC_code, String bankname, String address, String aadhar, String name, String accountno, String PF, String ESIC, int id_no) {
        this.gender = gender;
        this.department = department;
        this.IFSC_code = IFSC_code;
        this.bankname = bankname;
        this.address = address;
        this.aadhar = aadhar;
        this.name = name;
        this.accountno = accountno;
        this.PF = PF;
        this.ESIC = ESIC;
        this.id_no = id_no;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getIFSC_code() {
        return IFSC_code;
    }

    public void setIFSC_code(String IFSC_code) {
        this.IFSC_code = IFSC_code;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public int getId_no() {
        return id_no;
    }

    public void setId_no(int id_no) {
        this.id_no = id_no;
    }
}
