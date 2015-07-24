package com.verizon.mvyas.verizonproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
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
    private TagListAdapter adapter;
    private ListView listView;
    private TextView tv_total_tag_count;
    private Button btn_duplicate_thousand_times,btn_most_frq;
    private ToggleButton toggle;
    private String TotalNumTAGStr ;
    private boolean Result = false;
    private Context context;

    ProgressDialog myLoadingDialog;
    ArrayList<TagCounts> sortedList;

    public static final byte ASC_ORD = 0;
    public static final byte DSC_ORD = 1;

    private byte Current_Order = DSC_ORD;

    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_tag_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = getApplicationContext();
        mydb = new DBHelper(this);
        sortedList = new ArrayList<>();

        reLoadData(Current_Order);

        listView  = (ListView) findViewById(R.id.lv_tags);
        listView.setAdapter(adapter);
        TotalNumTAGStr = getResources().getString(R.string.Total_num_of_tags);

        tv_total_tag_count = (TextView) findViewById(R.id.tv_total_tag_count);
        tv_total_tag_count.setText(TotalNumTAGStr + totalTagsCount());

        toggle = (ToggleButton) findViewById(R.id.btn_asc_dsc);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    reLoadData(ASC_ORD);
                    Current_Order = ASC_ORD;
                } else {
                    reLoadData(DSC_ORD);
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
                new LoadDataTask().execute(context);
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
    void reLoadData(byte order) {
        sortedList.clear();
        if(order == ASC_ORD) {
            sortedList.addAll(TagsUtil.sortTagsAsc(mydb.getTagsGroupByTagCount()));
        }else {
            sortedList.addAll(TagsUtil.sortTagsDsc(mydb.getTagsGroupByTagCount()));
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
                .setTitle("Most Frequent Tag is : ")
                .setMessage( t.getTag())
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //dismiss the dialog
                            }
                        })
                .show();
    }

    protected class LoadDataTask extends AsyncTask<Context, Integer, String>
    {
        @Override
        protected void onPreExecute()
        {
            myLoadingDialog = new ProgressDialog(TagListActivity.this);
            myLoadingDialog.setMessage("Loading");
            myLoadingDialog.setIndeterminate(false);
            myLoadingDialog.setCancelable(false);
            myLoadingDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Context... arg0)
        {
            Result = duplicate1000TimesTags(adapter.getSelectedTags());
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (Result) {
                Toast.makeText(getApplicationContext(), "TAG(s) successfully added !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error storing data !", Toast.LENGTH_SHORT).show();
            }
            reLoadData(Current_Order);
            adapter.resetSelectedTags();
            myLoadingDialog.hide();
            super.onPostExecute(result);
        }
    }

}

