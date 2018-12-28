package com.kpp.kamalpanditpatil.constants;

public class constants {
    /**
     * Server url
     *
     */
    public static final String LOGINURL = "https://chippip.hostingerapp.com/php/basic/loginsup.php";
    public static final String WORKERLIST = "https://chippip.hostingerapp.com/php/basic/workernamedisplay.php";
    public static final String WORKERDATA = "https://chippip.hostingerapp.com/php/basic/workerdetailsdisplay.php";
    public static final String WORKERREGISTRATION = "https://chippip.hostingerapp.com/php/basic/workerreg.php";
    public static final String WORKERUPDATION = "https://chippip.hostingerapp.com/php/basic/workerupdate.php";
    public static final String CASINGURL = "https://chippip.hostingerapp.com/php/basic/casingtotalproduction.php";
    public static final String CASINGUPDATEURL ="" ;
    public static final String GRINDINGUPDATEURL ="" ;
    public static final String GRINDINGURL = "https://chippip.hostingerapp.com/php/basic/grindinginsert.php";
    public static final String CASINGWORKERLIST = "https://chippip.hostingerapp.com/php/basic/casingnames.php";
    public static final String CASINGWORKERWISEPRODUCTION = "https://chippip.hostingerapp.com/php/basic/casingworkerinsert.php";
    public static final String CNCURL = "https://chippip.hostingerapp.com/php/basic/cnctotalproduction.php";
    public static final String CNCWORKERWISEPRODUCTION = "https://chippip.hostingerapp.com/php/basic/cncworkerinsert.php";
    public static final String CNCWORKERLIST = "https://chippip.hostingerapp.com/php/basic/cncnames.php";
    public static final String ATTENDANCECASINGWORKERLIST = "https://chippip.hostingerapp.com/php/basic/casingnames.php";
    public static final String ATTENDANCEINSERT = "https://chippip.hostingerapp.com/php/basic/attendinsert.php";
    public static final String ATTENDANCECNCWORKERLIST = "https://chippip.hostingerapp.com/php/basic/cncnames.php";
    public static final String ATTENDANCEGRINDINGINGWORKERLIST = "https://chippip.hostingerapp.com/php/basic/grindingnames.php ";
    public static final String ATTENDANCEQANDAWORKERLIST = "https://chippip.hostingerapp.com/php/basic/QandAnames.php";
    public static final String QANDAPRODUCTION = "https://chippip.hostingerapp.com/php/basic/Q&Apresentnames.php";
//    public static final String ATTENDANCEDELETE = "http://chippip.hostingerapp.com/php/basic/deleteattendance.php";
//    public static final String GRINDINGDELETE = "http://chippip.hostingerapp.com/php/basic/grindingdelete.php";
//    public static final String CNCTOTALPRODUCTIONDELETE = "http://chippip.hostingerapp.com/php/basic/cncproductiondelete.php";
//    public static final String CNCPEROSNWISEPRODUCTIONDELETE = "http://chippip.hostingerapp.com/php/basic/cncworkerdelete.php";
//    public static final String CASINGTOTALPRODUCTIONDELETE = "http://chippip.hostingerapp.com/php/basic/casingproductiondelete.php";
//    public static final String CASINGPERSONWISEDELETE = "http://chippip.hostingerapp.com/php/basic/casingworkerdelete.php";

    /**
     * pref
     */
    public static final String CASING="CASING";
    public static final String GRINDING="GRINDING";
    public static final String CNC="C.N.C";
    public static final String QANDA = "QUALITY AND ASSURANCE";
    public static final String ATTENDANCE="ATTENDANCE";
    public static final String PRODUCTION="PRODUCTION";
    public static final String EDITING ="EDITING";
    public static final String REGISTRATION ="REGISTRATION";
    public static final String ECONOMICS="ECONOMICS";
    public static final String[] userRoleString = new String[]{"ADMIN", "SUPERVISOR"};
    public static final String[] workerDisplayarray = new String[]{"Name:","Gender:","Address:","ID no:","Department:","Aadhar:","Bank name:","IFSC code:","Account no:","PF number:","ESIC number:"};
    public static final String[] genderRoleString = new String[]{"MALE", "FEMALE"};
    public static final String[] departmentRoleString = new String[]{"CASING", "CNC", "GRINDING", "QUALITY AND ASSURANCE"};
    public static final String[] presentORabsentString = new String[]{"Present", "Absent"};
    public static final String[] otshiftString = new String[]{"   ", "1st", "2nd", "3rd", "General"};
    public static final String[] shiftString = new String[]{"1st", "2nd", "3rd", "General"};
    public static final String[] piecerateorDailyString = new String[]{"Piece Rate", "Daily wages"};

    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
}





