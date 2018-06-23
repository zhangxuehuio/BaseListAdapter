package com.baselistadapter.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baselistadapter.R;

public class BaseCommonListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_common_list);
    }
}
