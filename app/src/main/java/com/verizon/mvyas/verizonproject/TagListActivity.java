package com.verizon.mvyas.verizonproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ViewSwitcher;

import com.verizon.mvyas.data.TagCounts;

import java.util.ArrayList;
import java.util.Collections;


public class TagListActivity extends ActionBarActivity {

    private DBHelper mydb ;
    private ArrayList<TagCounts> tag_array_list;

    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_tag_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mydb = new DBHelper(this);
        tag_array_list = mydb.getTagsGroupByTagCount();//new ArrayList<String>();

        TagListAdapter adapter = new TagListAdapter(this, tag_array_list);
        ListView listView = (ListView) findViewById(R.id.lv_tags);
        listView.setAdapter(adapter);

        ToggleButton toggle = (ToggleButton) findViewById(R.id.btn_asc_dsc);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });

        Button btn_most_frq = (Button) findViewById(R.id.btn_most_frq);
        btn_most_frq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMostFrequentTag(Collections.max(tag_array_list));
            }
        });

        TextView tv_total_tag_count = (TextView) findViewById(R.id.tv_total_tag_count);
        tv_total_tag_count.setText(getResources().getString(R.string.Total_num_of_tags) + totalTagsCount());

        Button btn_duplicate_thousand_times = (Button) findViewById(R.id.btn_duplicate_thousand_times);
        btn_most_frq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // duplicate1000TimesTags();
            }
        });


    }

    private long totalTagsCount(){
        long total = 0;
        for(TagCounts p : tag_array_list){
            total += p.getCount();
        }
        return total;
    }

    private void duplicate1000TimesTags(ArrayList<TagCounts> tags){
        for(int i = 0;i<tags.size();i++){
            mydb.duplicateTag1000Time(tags.get(i).getTag());
        }
    }

    private void showMostFrequentTag(TagCounts t) {
        new AlertDialog.Builder(this)
                .setTitle("TAG")
                .setMessage("Most Frequent Tag is : \n"+ t.getTag())
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //dismiss the dialog
                            }
                        })
               // .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}