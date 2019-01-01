package com.kpp.kamalpanditpatil.ui.activities.utilities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.kpp.kamalpanditpatil.R;
import com.kpp.kamalpanditpatil.constants.constants;
import com.kpp.kamalpanditpatil.models.worker_model;
import com.kpp.kamalpanditpatil.ui.activities.admin.Attendance.AdminAttendanceBaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceOvertimeDetails extends AppCompatActivity {
    private static final String TAG = "PdfCreatorActivity";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    ArrayList<String> workerDataList, workerdataexport;
    ArrayList<String> workerDataTransferList;
    String startdate, enddate, department, code, message;
    worker_model worker;
    ProgressDialog pDiaalog;
    LstViewOvertimewiseAdapter adapter;
    private ListView lv1;
    private View mRootView;
    private Button ExportButton;
    private Button mCreateButton;
    private File pdfFile;

    private static void addImage(Document document, byte[] byteArray) {
        Image image = null;
        try {
            image = Image.getInstance(byteArray);
        } catch (BadElementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // image.scaleAbsolute(150f, 150f);
        try {
            document.add(image);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overtime_details);
        startdate = getIntent().getStringExtra("startdate");
        enddate = getIntent().getStringExtra("enddate");
        department = getIntent().getStringExtra("department");
        Toolbar toolbar = findViewById(R.id.adminOvertimeAttendanceDetailsToolbar);
        toolbar.setTitle(department);
        lv1 = findViewById(R.id.workerOvertimewiseList);
        ExportButton = findViewById(R.id.attendaceovertimewiseExportbutton);
        workerDataList = new ArrayList<String>();
        workerdataexport = new ArrayList<String>();
        pDiaalog = new ProgressDialog(this);
        pDiaalog.setMessage("Fetching data ...");
        pDiaalog.show();
        workerDataList.clear();
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.header_admin_attendance_overtimewise_detail_layout, lv1, false);
        // Add header view to the ListView
        lv1.addHeaderView(headerView);
        mRootView = lv1.getRootView();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, constants.ATTENDANCEADMINOVERTIMEWISE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    code = jsonObject.getString("code");
                    if (code.equals("0")) {
                        Toast.makeText(AttendanceOvertimeDetails.this, "Unable to fetch data", Toast.LENGTH_SHORT).show();
                    } else if (code.equals("1")) {
                        JSONArray jAry = new JSONArray(response);
                        workerdataexport.add(String.format("|%20.10s|", "  DATE") + String.format("|%40.30s|", "NAME") + String.format("|%15.5s|", "OTHOURS") + String.format("|%25.20s|", "P RATE/D WAGES") + "\n");
                        for (int i = 1; i < jAry.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String export = (String.format("|%20.10s", jsonObject1.get("date"))) + (String.format("|%-40s |", jsonObject1.get("name"))) + (String.format("|%15.5s|", jsonObject1.get("othours"))) + (String.format("|%25.20s|", jsonObject1.get("pieceordaily"))) + "\n";
                            String name = jsonObject1.get("date") + "__" + jsonObject1.get("name") + "__" + jsonObject1.get("othours") + "__" + jsonObject1.get("pieceordaily");
                            workerdataexport.add(export);
                            workerDataList.add(name);
                        }
                        adapter = new LstViewOvertimewiseAdapter(AttendanceOvertimeDetails.this, R.layout.rowlayout_attendance_overtimewise_details, R.id.overtime_dates, workerDataList);
                        lv1.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        pDiaalog.dismiss();


                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> datamap = new HashMap<String, String>();
                datamap.put("startdate", startdate);
                datamap.put("enddate", enddate);
                datamap.put("department", department);
                return datamap;
            }
        };
        com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(this).addtoRequestQueue(stringRequest);
        ExportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
//                String state = Environment.getExternalStorageState();
//                if (!Environment.MEDIA_MOUNTED.equals(state)) {
//                    Toast.makeText(AttendanceOvertimeDetails.this, "donot know", Toast.LENGTH_SHORT).show();
//                }
//
////Create a directory for your PDF
//                File pdfDir = new File(Environment.getExternalStoragePublicDirectory(
//                        Environment.DIRECTORY_DOCUMENTS), "MyApp");
//                if (!pdfDir.exists()){
//                    pdfDir.mkdir();
//                }
//
////Then take the screen shot
//                Bitmap screen; View v1 = mRootView.getRootView();
//                v1.setDrawingCacheEnabled(true);
//                screen = Bitmap.createBitmap(v1.getDrawingCache());
//                v1.setDrawingCacheEnabled(false);
//
////Now create the name of your PDF file that you will generate
//                File pdfFile = new File(pdfDir, "myPdfFile.pdf");
//                try {
//                    Document  document = new Document();
//
//                    PdfWriter.getInstance(document, new FileOutputStream(file));
//                    document.open();
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    screen.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                    byte[] byteArray = stream.toByteArray();
//                    addImage(document,byteArray);
//                    document.close();
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                }
                try {
                    createPdfWrapper();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }

            }

        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, AdminAttendanceBaseActivity.class));
    }

    private void createPdfWrapper() {

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        } else {
            createPdf1();
        }
    }

    private void createPdf1() {
        //Then take the screen shot
        Bitmap screen;
        View v1 = mRootView.getRootView();
        v1.setDrawingCacheEnabled(true);
        screen = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);
        try {
            File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
            if (!docsFolder.exists()) {
                docsFolder.mkdir();
                Log.i(TAG, "Created a new directory for PDF");
            }
            Document document = new Document();
            File pdfFile = new File(docsFolder, "myPdfFile.pdf");
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            screen.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            addImage(document, byteArray);
            document.close();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(new File(docsFolder, "pdfFileName"));
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(this, "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }
        pDiaalog.setMessage("exporting data to pdf you can find pdf named " + department + "overtime" + startdate + "-" + enddate + ".pdf" + "in" + "/Documents folder");
        pDiaalog.show();
        pdfFile = new File(docsFolder.getAbsolutePath(), department + "overtime" + startdate + "-" + enddate + ".pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        document.open();
        document.add(new Paragraph(workerdataexport.toString()));

        document.close();
        pDiaalog.dismiss();
//        previewPdf();
    }

    private void previewPdf() {

        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");

            startActivity(intent);
        } else {
            Toast.makeText(this, "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
        }
    }
}