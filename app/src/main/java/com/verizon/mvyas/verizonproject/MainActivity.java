package com.verizon.mvyas.verizonproject;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private DBHelper mydb ;

    private Button btn_insert_tag;
    private TextView tv_goto_taglist_activity, et_mytag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBHelper(this);
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {

        }

        tv_goto_taglist_activity = (TextView) findViewById(R.id.tv_goto_taglist_activity);
        tv_goto_taglist_activity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                goToViewTagActivity();
            }
        });

        et_mytag = (TextView) findViewById(R.id.et_mytag);
        btn_insert_tag = (Button) findViewById(R.id.btn_insert_tag);
        btn_insert_tag.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mydb.insertTAG(et_mytag.getText().toString())){
                    Toast.makeText(getApplicationContext(), "TAG successfully added !", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Error storing data !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goToViewTagActivity(){
        Intent viewTagActivityIntent = new Intent(this, TagListActivity.class);
        startActivity(viewTagActivityIntent);
    }
}
