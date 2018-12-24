package com.kpp.kamalpanditpatil.constants;

import com.android.volley.toolbox.StringRequest;

public class constants {
    /**
     * Server url
     *
     */
    public static final String LOGINURL = "https://chippip.000webhostapp.com/php/basic/loginsup.php";
    public static final String WORKERLIST="https://chippip.000webhostapp.com/php/basic/workernamedisplay.php";
    public static final String WORKERDATA="https://chippip.000webhostapp.com/php/basic/workerdetailsdisplay.php";
//    public static final String INSERTATTEND = "https://chippip.000webhostapp.com/php/insertAttendance.php";
//    public static final String VIEWATTENDANCE = "https://chippip.000webhostapp.com/php/viewAttendance.php";
//    public static final String QUICKATTENDANCE = "https://chippip.000webhostapp.com/php/quickAttendance.php";
    public static final String WORKERREGISTRATION="https://chippip.000webhostapp.com/php/basic/workerreg.php";
    public static final String WORKERUPDATION ="https://chippip.000webhostapp.com/php/basic/workerupdate.php" ;
    public static final String CASINGURL ="" ;
    public static final String CASINGUPDATEURL ="" ;
    public static final String GRINDINGUPDATEURL ="" ;
    public static final String GRINDINGURL ="" ;
    /**
     * pref
     */
    public static final String CASING="CASING";
    public static final String GRINDING="GRINDING";
    public static final String CNC="C.N.C";
    public static final String QANDA="QUALITY AND ASSSURANCE";
    public static final String ATTENDANCE="ATTENDANCE";
    public static final String PRODUCTION="PRODUCTION";
    public static final String EDITING ="EDITING";
    public static final String REGISTRATION ="REGISTRATION";
    public static final String ECONOMICS="ECONOMICS";
    public static final String[] userRoleString = new String[]{"ADMIN", "SUPERVISOR"};
    public static final String[] workerDisplayarray = new String[]{"Name:","Gender:","Address:","ID no:","Department:","Aadhar:","Bank name:","IFSC code:","Account no","PF number","ESIC number"};
    public static final String[] genderRoleString = new String[]{"MALE", "FEMALE"};
    public static final String[] departmentRoleString = new String[]{"CASING", "CNC", "GRINDING", "QUALITY AND ASSURANCE"};

    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";



}


