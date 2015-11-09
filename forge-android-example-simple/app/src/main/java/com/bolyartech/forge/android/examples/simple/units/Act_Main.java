package com.bolyartech.forge.android.examples.simple.units;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bolyartech.forge.adnroid.examples.simple.R;
import com.bolyartech.forge.android.examples.simple.units.file_download.Act_FileDownload;
import com.bolyartech.forge.android.examples.simple.units.file_upload.Act_FileUpload;
import com.bolyartech.forge.android.examples.simple.units.get_param.Act_GetParam;
import com.bolyartech.forge.android.examples.simple.units.get_simple.Act_GetSimple;
import com.bolyartech.forge.android.examples.simple.units.post.Act_Post;
import com.bolyartech.forge.misc.ViewUtils;


public class Act_Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act__main);

        initViews();
    }


    private void initViews() {
        View view = getWindow().getDecorView();

        ViewUtils.initButton(view, R.id.btn_get_simple, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Act_Main.this, Act_GetSimple.class);
                startActivity(intent);
            }
        });


        ViewUtils.initButton(view, R.id.btn_get_param, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Act_Main.this, Act_GetParam.class);
                startActivity(intent);
            }
        });


        ViewUtils.initButton(view, R.id.btn_post, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Act_Main.this, Act_Post.class);
                startActivity(intent);
            }
        });

        ViewUtils.initButton(view, R.id.btn_upload, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Act_Main.this, Act_FileUpload.class);
                startActivity(intent);
            }
        });

        ViewUtils.initButton(view, R.id.btn_download, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Act_Main.this, Act_FileDownload.class);
                startActivity(intent);
            }
        });
    }
}
