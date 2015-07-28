package com.verizon.mvyas.verizonproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import java.util.Iterator;


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
    ArrayList<TagCounts> selectedTags;

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
        selectedTags = new ArrayList<>();

      //  adapter = TagListAdapter(this,);

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
                adapter.resetSelectedTags();
            }
        });

        btn_most_frq = (Button) findViewById(R.id.btn_most_frq);
        btn_most_frq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sortedList.size()>0) {
                    showMostFrequentTag(Collections.max(sortedList));
                }else {
                    Toast.makeText(context, "TAG list is Empty !", Toast.LENGTH_SHORT).show();
                }
                adapter.resetSelectedTags();
            }
        });

        btn_duplicate_thousand_times = (Button) findViewById(R.id.btn_duplicate_thousand_times);
        btn_duplicate_thousand_times.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedTags= adapter.getSelectedTags();
                new LoadDataTask().execute();
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
        if (order == ASC_ORD) {
            sortedList.addAll(TagsUtil.sortTagsAsc(mydb.getTagsGroupByTagCount()));
        } else {
            sortedList.addAll(TagsUtil.sortTagsDsc(mydb.getTagsGroupByTagCount()));
        }
        if (adapter == null) {
            adapter = new TagListAdapter(this, sortedList);
            return;
        }
        adapter.notifyDataSetChanged();
        tv_total_tag_count.setText(TotalNumTAGStr + totalTagsCount());
    }

    void reLoadData() {
        reLoadData(Current_Order);
        View view = this.getCurrentFocus();

    }
    private boolean duplicate1000TimesTags (ArrayList<TagCounts> tags){
        boolean success = false;

        for (Iterator<TagCounts> it = tags.iterator(); it.hasNext(); ) {
            TagCounts aTag = it.next();
            Log.d("Duplicating", "TAG: = " + aTag.getTag());
            success = mydb.duplicateTag1000Time(aTag.getTag());
            if(success == false)
                break;

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


    protected class LoadDataTask extends AsyncTask<Void, Void, Void>
    {


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            myLoadingDialog = new ProgressDialog(TagListActivity.this);
            myLoadingDialog.setMessage("Loading");
            myLoadingDialog.setIndeterminate(false);
            myLoadingDialog.setCancelable(false);
            myLoadingDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Result = duplicate1000TimesTags(selectedTags);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            myLoadingDialog.dismiss();
            if (Result) {
                Toast.makeText(getApplicationContext(), "TAG(s) successfully added !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error storing data !", Toast.LENGTH_SHORT).show();
            }
            reLoadData(Current_Order);
            adapter.resetSelectedTags();
        }
    }




}

