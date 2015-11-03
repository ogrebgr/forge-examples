package com.bolyartech.forge.android.examples.simple.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.bolyartech.forge.adnroid.examples.simple.R;


public class Df_CommProblem extends DialogFragment {
    public static final String DIALOG_TAG = "Df_CannotSendData";


    private Listener mListener;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof Listener) {
            mListener = (Listener) getActivity();
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        b.setTitle(R.string.dlg__comm_problem_title);
        b.setMessage(R.string.dlg__comm_problem_msg);
        b.setCancelable(false);
        b.setNeutralButton(R.string.global_btn__close, null);
        return b.create();
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mListener != null) {
            mListener.onCommProblemClosed();
        }
    }


    public interface Listener {
        void onCommProblemClosed();
    }
}
