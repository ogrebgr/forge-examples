package com.bolyartech.forge.android.examples.simple.units.post;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bolyartech.forge.adnroid.examples.simple.R;
import com.bolyartech.forge.android.app_unit.ResidentComponent;
import com.bolyartech.forge.android.examples.simple.app.MyActivity;
import com.bolyartech.forge.android.examples.simple.dialogs.MyDialogs;
import com.bolyartech.forge.android.misc.ViewUtils;


public class Act_Post extends MyActivity {
    private Res_Post mResident;

    private EditText mEtParam;
    private TextView mTvParam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act__post);

        initViews();
    }


    private void initViews() {
        View view = getWindow().getDecorView();

        mEtParam = ViewUtils.findEditTextX(view, R.id.et_param);

        ViewUtils.initButton(view, R.id.btn_execute, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialogs.showCommWaitDialog(getFragmentManager());
                mResident.executePostParam(mEtParam.getText().toString());
            }
        });

        mTvParam = ViewUtils.findTextViewX(view, R.id.tv_param);
    }


    @Override
    public void onResume() {
        super.onResume();

        mResident = (Res_Post) getResidentComponent();

        switch (mResident.getState()) {
            case IDLE:
                //nothing
                break;
            case WAITING_EXCHANGE:
                MyDialogs.showCommWaitDialog(getFragmentManager());
                break;
            case EXCHANGE_OK:
                onPostOk();
                break;
            case EXCHANGE_FAIL:
                onPostFailed();
                break;
        }
    }


    @Override
    public ResidentComponent createResidentComponent() {
        return new Res_PostImpl(getString(R.string.server_base_url),
                getMyApp().getForgeExchangeManager(),
                getMyApp().getBus(),
                getMyApp().getHttpFunctionality());
    }


    public void onPostOk() {
        MyDialogs.hideCommWaitDialog(getFragmentManager());
        mTvParam.setText(mResident.getLastResult());

        mResident.reset();


    }


    public void onPostFailed() {
        MyDialogs.hideCommWaitDialog(getFragmentManager());
        mResident.reset();
        MyDialogs.showCommProblemDialog(getFragmentManager());
    }

}
