package com.kpp.kamalpanditpatil.ui.activities.utilities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.kpp.kamalpanditpatil.R;

public class cncDialogclass extends AppCompatDialogFragment {
    private EditText editTextProduction;
    private EditText editTextDispach;
    private EditText editTextVoucher_No;
    private cncDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.supervisor_producton_dialog,null);
        builder.setView(view)
                .setTitle("CNC PRODUCTION")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String production=editTextProduction.getText().toString();
                        String dispach=editTextDispach.getText().toString();
                        String voucher_no=editTextVoucher_No.getText().toString();
                        listener.applyText(production,dispach,voucher_no);
                    }
                });
        editTextProduction=view.findViewById(R.id.productionTypeEditTExt);
        editTextDispach=view.findViewById(R.id.dispachProductionEditText);
        editTextVoucher_No=view.findViewById(R.id.voucher_no_EditText);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener=(cncDialogListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"must implememnt cncDailoglistenr");
        }
    }

    public interface cncDialogListener{
        void applyText(String production,String dispach,String voucher_no);
    }
}




