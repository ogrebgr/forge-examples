package com.bolyartech.forge.android.examples.simple.units.file_download;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bolyartech.forge.adnroid.examples.simple.R;
import com.bolyartech.forge.android.examples.simple.app.MyActivity;
import com.bolyartech.forge.android.examples.simple.dialogs.Df_Progress;
import com.bolyartech.forge.android.examples.simple.dialogs.MyDialogs;
import com.bolyartech.forge.app_unit.ResidentComponent;
import com.bolyartech.forge.misc.ViewUtils;


public class Act_FileDownload extends MyActivity implements Df_Progress.Listener {
    private Res_FileDownload mResident;

    private TextView mTvFilePath;


    private Df_Progress mProgressDialogFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act__download);

        initViews();
    }



    private void initViews() {
        View view = getWindow().getDecorView();

        ViewUtils.initButton(view, R.id.btn_download, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                if (mResident.getState() == Res_FileDownload.State.IDLE) { // prevents double tap
                    mResident.download();
                }
            }
        });

        mTvFilePath = ViewUtils.findTextViewX(view, R.id.tv_file_path);
    }


    @Override
    public ResidentComponent createResidentComponent() {
        return new Res_FileDownloadImpl(
                getMyApp().getBus(),
                getString(R.string.server_base_url),
                getMyApp().getHttpFunctionality());
    }


    @Override
    public void onResume() {
        super.onResume();

        mResident = (Res_FileDownload) getResidentComponent();

        switch (mResident.getState()) {
            case IDLE:
                //nothing
                break;
            case DOWNLOADING:
                showProgressDialog();
                break;
            case DOWNLOAD_OK:
                onDownloadOk();
                break;
            case DOWNLOAD_FAIL:
                onDownloadFailed();
                break;
        }
    }


    void onDownloadOk() {
        dismissProgressDialog();
        mTvFilePath.setText(mResident.getDownloadedFilePath());

        mResident.reset();
    }


    void onDownloadFailed() {
        dismissProgressDialog();
        mResident.reset();
        MyDialogs.showCommProblemDialog(getFragmentManager());
    }


    void onProgress(float progress) {
        mProgressDialogFragment.setProgress((int) progress);
    }


    private void showProgressDialog() {
        mProgressDialogFragment = MyDialogs.showProgressDialog(getFragmentManager());
    }


    private void dismissProgressDialog() {
        if (mProgressDialogFragment != null) {
            mProgressDialogFragment.dismiss();
        }
    }


    @Override
    public void onProgressDialogCancelled() {
        if (mResident != null) {
            if (mResident.getState() == Res_FileDownload.State.DOWNLOADING) {
                mResident.abortDownload();
                mResident.reset();
            }
        }
    }
}
