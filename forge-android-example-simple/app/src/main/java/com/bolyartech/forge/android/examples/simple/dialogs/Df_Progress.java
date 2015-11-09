package com.bolyartech.forge.android.examples.simple.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.bolyartech.forge.adnroid.examples.simple.R;


/**
 * Created by ogre on 2015-11-08 16:36
 */
public class Df_Progress extends DialogFragment {
    public static final String DIALOG_TAG = "Df_Progress";


    private Listener mListener;

    private ProgressDialog mProgressDialog;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof Listener) {
            mListener = (Listener) getActivity();
        }

        setCancelable(true);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(R.string.dlg__progress__title);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMax(100);

        return mProgressDialog;
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mListener != null) {
            mListener.onProgressDialogCancelled();
        }
    }


    public interface Listener {
        void onProgressDialogCancelled();
    }


    public void setProgress(int progress) {
        mProgressDialog.setProgress(progress);
    }

}
