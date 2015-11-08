package com.bolyartech.forge.android.examples.simple.dialogs;

import android.app.DialogFragment;
import android.app.FragmentManager;


/**
 * Created by ogre on 2015-11-02 08:40
 */
public class MyDialogs {
    public static void showCommWaitDialog(FragmentManager fm) {
        if (fm.findFragmentByTag(Df_CommWait.DIALOG_TAG) == null) {
            Df_CommWait fra = new Df_CommWait();
            fra.show(fm, Df_CommWait.DIALOG_TAG);
        }
    }


    public static void hideCommWaitDialog(FragmentManager fm) {
        DialogFragment df = (DialogFragment) fm.findFragmentByTag(Df_CommWait.DIALOG_TAG);
        if (df != null) {
            df.dismiss();
        }
    }


    public static void showCommProblemDialog(FragmentManager fm) {
        if (fm.findFragmentByTag(Df_CommProblem.DIALOG_TAG) == null) {
            Df_CommProblem fra = new Df_CommProblem();
            fra.show(fm, Df_CommProblem.DIALOG_TAG);
        }
    }



    public static Df_Progress showProgressDialog(FragmentManager fm) {
        Df_Progress fra = (Df_Progress) fm.findFragmentByTag(Df_Progress.DIALOG_TAG);
        if (fra == null) {
            fra = new Df_Progress();
            fra.show(fm, Df_Progress.DIALOG_TAG);
        }

        return fra;
    }


    public static void hideProgressDialog(FragmentManager fm) {
        DialogFragment df = (DialogFragment) fm.findFragmentByTag(Df_Progress.DIALOG_TAG);
        if (df != null) {
            df.dismiss();
        }
    }

}
