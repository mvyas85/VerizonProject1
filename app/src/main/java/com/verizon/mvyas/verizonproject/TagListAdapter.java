package com.verizon.mvyas.verizonproject;

import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.verizon.mvyas.tags_asc_dsc_libs.data.TagCounts;

import java.util.ArrayList;

public class TagListAdapter  extends ArrayAdapter<TagCounts> {

    private ArrayList<TagCounts> selectedStrings = new ArrayList<TagCounts>();
    private ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
    private Context context;
    private DBHelper mydb ;

    public TagListAdapter(Context context, ArrayList<TagCounts> tagObj) {
        super(context, 0, tagObj);
        this.context = context;

        mydb = new DBHelper(context);
    }

    static class ViewHolder {
        private TextView tv_numb,tv_tag;
        private EditText hidden_edit_view;
        private ToggleButton btn_image;
        private CheckBox checkBox;

        boolean changeToEditMode = true;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final TagCounts aTag = getItem(position);

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            rowView = inflater.inflate(R.layout.list_item, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.tv_numb = (TextView) rowView.findViewById(R.id.tv_numb);
            holder.tv_tag = (TextView) rowView.findViewById(R.id.tv_tag);
            holder.hidden_edit_view = (EditText) rowView.findViewById(R.id.hidden_edit_view);
            holder.btn_image = (ToggleButton) rowView.findViewById(R.id.btn_image);
            holder.checkBox = (CheckBox) rowView.findViewById(R.id.checkBox);
            rowView.setTag(holder);
        }

        final ViewHolder holder = (ViewHolder) rowView.getTag();
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        holder.tv_tag.setText(aTag.getTag());
        holder.tv_numb.setText("< " + aTag.getCount() + " >");
        holder.hidden_edit_view.setText(aTag.getTag());

        holder.tv_tag.setText(aTag.getTag());
        holder.tv_numb.setText("< " + aTag.getCount() + " >");
        holder.hidden_edit_view.setText(aTag.getTag());
        holder.hidden_edit_view.setFilters(new InputFilter[]{MainActivity.myFilter});
        holder.btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (holder.changeToEditMode) {
                    Log.d("NNNNMMNM", "is clicke" + holder.changeToEditMode);
                    holder.tv_tag.setVisibility(View.GONE);
                    holder.hidden_edit_view.setVisibility(View.VISIBLE);
                    holder.hidden_edit_view.setText(holder.tv_tag.getText());
                    holder.hidden_edit_view.requestFocus();

                    //  imm.showSoftInput(holder.hidden_edit_view, InputMethodManager.SHOW_IMPLICIT);

                    holder.changeToEditMode = false;
                } else {
                    holder.tv_tag.setVisibility(View.VISIBLE);
                    holder.hidden_edit_view.setVisibility(View.GONE);

                    //  imm.hideSoftInputFromWindow(holder.hidden_edit_view.getWindowToken(), 0);

                    String new_tag_text = holder.hidden_edit_view.getText().toString();
                    holder.tv_tag.setText(new_tag_text);
                    if (!aTag.getTag().equals(new_tag_text)) {
                        if (mydb.updateTag(aTag.getTag(), new_tag_text)) {
                            Toast.makeText(context, "TAG successfully updated !", Toast.LENGTH_SHORT).show();
                            ((TagListActivity) getContext()).reLoadData();
                        } else {
                            Toast.makeText(context, "Error storing updated !", Toast.LENGTH_SHORT).show();
                        }
                    } else {/*Dont store in DB - do nothing*/}
                    holder.changeToEditMode = true;
                }
            }
        });


        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedStrings.add(aTag);
                } else {
                    selectedStrings.remove(aTag);
                }
                for (TagCounts selected : selectedStrings)
                    Log.d("Shooooooo ", "selected strings are " + selected.getTag());
            }

        });
        checkBoxes.add(holder.checkBox);
        return rowView;
    }

    public  ArrayList<TagCounts> getSelectedTags(){
        for (TagCounts selected : selectedStrings){
            Log.d("Sho ", "selected strings are "+selected.getTag());
    }
        Log.d("Sending", "Size = "+selectedStrings.size());
        return selectedStrings;
    }
    public  void resetSelectedTags(){
        for(CheckBox c : checkBoxes)
            c.setChecked(false);
    }
}