package com.bolyartech.forge.android.examples.simple.units.file_upload;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bolyartech.forge.adnroid.examples.simple.R;
import com.bolyartech.forge.android.examples.simple.app.MyActivity;
import com.bolyartech.forge.android.examples.simple.dialogs.MyDialogs;
import com.bolyartech.forge.android.examples.simple.units.get_param.Res_GetParam;
import com.bolyartech.forge.app_unit.ResidentComponent;
import com.bolyartech.forge.misc.FileUtils;
import com.bolyartech.forge.misc.ViewUtils;

import java.io.File;


public class Act_FileUpload extends MyActivity {
    private static final int PICKFILE_REQUEST_CODE = 1;
    private String mFilePath;

    private Button mBtnUpload;
    private TextView mTvFilePath;
    private TextView mTvFileSize;


    private Res_FileUpload mResident;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act__file_upload);


        initViews();

    }


    private void initViews() {
        View view = getWindow().getDecorView();
        ViewUtils.initButton(view, R.id.btn_select_file, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, PICKFILE_REQUEST_CODE);
            }
        });


        mBtnUpload = ViewUtils.initButton(view, R.id.btn_upload, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResident.upload(new File(mFilePath));
            }
        });

        mTvFilePath = ViewUtils.findTextViewX(view, R.id.tv_file);
        mTvFileSize = ViewUtils.findTextViewX(view, R.id.tv_file_size);
    }


    @Override
    public ResidentComponent createResidentComponent() {
        return new Res_FileUploadImpl(
                getMyApp().getBus(),
                getString(R.string.server_base_url),
                getMyApp().getHttpFunctionality());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICKFILE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                mFilePath = FileUtils.getPath(this, uri);
                mBtnUpload.setEnabled(true);
                mTvFilePath.setText(mFilePath);
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        mResident = (Res_FileUpload) getResidentComponent();

        switch (mResident.getState()) {
            case IDLE:
                //nothing
                break;
            case WAITING_EXCHANGE:
                MyDialogs.showCommWaitDialog(getFragmentManager());
                break;
            case EXCHANGE_OK:
                onUploadOk();
                break;
            case EXCHANGE_FAIL:
                onUploadFailed();
                break;
        }
    }


    void onUploadOk() {
        MyDialogs.hideCommWaitDialog(getFragmentManager());
        mTvFileSize.setText(getString(R.string.act__file_upload__tv_size, Long.toString(mResident.getLastResult())));

        mResident.reset();
    }


    void onUploadFailed() {
        MyDialogs.hideCommWaitDialog(getFragmentManager());
        mResident.reset();
        MyDialogs.showCommProblemDialog(getFragmentManager());
    }


    void onProgress(float progress) {

    }

}
