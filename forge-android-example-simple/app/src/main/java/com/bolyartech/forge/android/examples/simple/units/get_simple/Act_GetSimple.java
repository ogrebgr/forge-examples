package com.bolyartech.forge.android.examples.simple.units.get_simple;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bolyartech.forge.adnroid.examples.simple.R;
import com.bolyartech.forge.android.app_unit.ResidentComponent;
import com.bolyartech.forge.android.examples.simple.app.MyActivity;
import com.bolyartech.forge.android.examples.simple.dialogs.MyDialogs;
import com.bolyartech.forge.android.misc.ViewUtils;


public class Act_GetSimple extends MyActivity {
    private Res_GetSimple mResident;

    private TextView mTvVar1;
    private TextView mTvVar2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act__get_simple);

        initViews();
    }


    private void initViews() {
        View view = getWindow().getDecorView();

        ViewUtils.initButton(view, R.id.btn_execute, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialogs.showCommWaitDialog(getFragmentManager());
                mResident.executeSimpleGet();
            }
        });

        mTvVar1 = ViewUtils.findTextViewX(view, R.id.tv_var1);
        mTvVar2 = ViewUtils.findTextViewX(view, R.id.tv_var2);
    }


    @Override
    public ResidentComponent createResidentComponent() {
        return new Res_GetSimpleImpl(getString(R.string.server_base_url),
                getMyApp().getForgeExchangeManager(),
                getMyApp().getBus(),
                getMyApp().getHttpFunctionality());
    }


    @Override
    public void onResume() {
        super.onResume();

        mResident = (Res_GetSimple) getResidentComponent();

        switch (mResident.getState()) {
            case IDLE:
                //nothing
                break;
            case WAITING_EXCHANGE:
                MyDialogs.showCommWaitDialog(getFragmentManager());
                break;
            case EXCHANGE_OK:
                onSimpleGetOk();
                break;
            case EXCHANGE_FAIL:
                onSimpleGetFailed();
                break;
        }
    }


    @SuppressLint("SetTextI18n")
    void onSimpleGetOk() {
        MyDialogs.hideCommWaitDialog(getFragmentManager());

        SimpleGetResult rez = mResident.getLastResult();
        mResident.reset();

        mTvVar1.setText(Integer.toString(rez.mValue1));
        mTvVar2.setText(rez.mValue2);
    }


    void onSimpleGetFailed() {
        MyDialogs.hideCommWaitDialog(getFragmentManager());
        mResident.reset();
        mTvVar1.setText("");
        mTvVar2.setText("");

        MyDialogs.showCommProblemDialog(getFragmentManager());
    }

}
