package com.verizon.mvyas.verizonproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.verizon.mvyas.tags_asc_dsc_libs.data.TagCounts;
import com.verizon.mvyas.tags_asc_dsc_libs.TagsUtil;

import java.util.ArrayList;
import java.util.Collections;


public class TagListActivity extends ActionBarActivity {

    private DBHelper mydb ;
    private ArrayList<TagCounts> unsorted_arraylist;
    private TagListAdapter adapter;
    private  ListView listView;
    private TextView tv_total_tag_count;
    private Button btn_duplicate_thousand_times,btn_most_frq;
    private ToggleButton toggle;
    private String TotalNumTAGStr ;
    ArrayList<TagCounts> sortedList;

    public static final byte ASC_ORD = 0;
    public static final byte DSC_ORD = 1;

    private byte Current_Order = DSC_ORD;

    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_tag_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mydb = new DBHelper(this);
        unsorted_arraylist = mydb.getTagsGroupByTagCount();
        sortedList = new ArrayList<TagCounts>();

        reLoadData(unsorted_arraylist, Current_Order);

        listView  = (ListView) findViewById(R.id.lv_tags);
        listView.setAdapter(adapter);
        TotalNumTAGStr = getResources().getString(R.string.Total_num_of_tags);

        tv_total_tag_count = (TextView) findViewById(R.id.tv_total_tag_count);
        tv_total_tag_count.setText(TotalNumTAGStr + totalTagsCount());


        toggle = (ToggleButton) findViewById(R.id.btn_asc_dsc);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               sortedList.clear();
                if (isChecked) {
                    reLoadData(unsorted_arraylist, ASC_ORD);
                    Current_Order = ASC_ORD;
                } else {
                    reLoadData(unsorted_arraylist, DSC_ORD);
                    Current_Order = DSC_ORD;
                }
            }
        });

        btn_most_frq = (Button) findViewById(R.id.btn_most_frq);
        btn_most_frq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMostFrequentTag(Collections.max(sortedList));
            }
        });

        btn_duplicate_thousand_times = (Button) findViewById(R.id.btn_duplicate_thousand_times);
        btn_duplicate_thousand_times.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (duplicate1000TimesTags(adapter.getSelectedTags())) {
                    Toast.makeText(getApplicationContext(), "TAG(s) successfully added !", Toast.LENGTH_SHORT).show();
                    reLoadData(mydb.getTagsGroupByTagCount(), Current_Order);
                } else {
                    Toast.makeText(getApplicationContext(), "Error storing data !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private long totalTagsCount(){
        long total = 0;
        for(TagCounts p : sortedList){
            total += p.getCount();
        }
        return total;
    }
    void reLoadData(ArrayList<TagCounts> unsorted_arraylist, byte order) {
        if(order == ASC_ORD) {
            sortedList.addAll(TagsUtil.sortTagsAsc(unsorted_arraylist));
        }else {
            sortedList.addAll(TagsUtil.sortTagsDsc(unsorted_arraylist));
        }
        if(adapter == null){
            adapter = new TagListAdapter(this, sortedList);
            return;
        }
        adapter.notifyDataSetChanged();
        tv_total_tag_count.setText(TotalNumTAGStr + totalTagsCount());
    }

    private boolean duplicate1000TimesTags (ArrayList<TagCounts> tags){
        boolean success = false;
        for(int i = 0;i<tags.size();i++){
            success = mydb.duplicateTag1000Time(tags.get(i).getTag());
        }
        return success;
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
                .show();
    }

}