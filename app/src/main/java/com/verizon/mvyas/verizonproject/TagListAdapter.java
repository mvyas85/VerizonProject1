package com.verizon.mvyas.verizonproject;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.verizon.mvyas.tags_asc_dsc_libs.data.TagCounts;

import java.util.ArrayList;

public class TagListAdapter  extends ArrayAdapter<TagCounts> {

    private Context context;
     ArrayList<TagCounts> selectedStrings = new ArrayList<TagCounts>();

    public TagListAdapter(Context context, ArrayList<TagCounts> tagObj) {
        super(context, 0, tagObj);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TagCounts aTag = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView tv_tag = (TextView) convertView.findViewById(R.id.tv_tag);
        final TextView tv_numb = (TextView) convertView.findViewById(R.id.tv_numb);

        tv_tag.setText(aTag.getTag());
        tv_numb.setText(aTag.getCount()+"");
        tv_numb.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                // Log.d("Hi","You inserted " + arg0.toString());
                // Toast.makeText(context,"You inserted"+ arg0.toString(),Toast.LENGTH_SHORT).show();
                //arrTemp[holder.ref] = arg0.toString();
            }
        });


        tv_numb.setText("< " + aTag.getCount() + " >");

//        final ViewSwitcher switcher = (ViewSwitcher) convertView.findViewById(R.id.my_switcher);
//        final EditText hidden_edit_view = (EditText) convertView.findViewById(R.id.hidden_edit_view);
//
//        tv_numb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switcher.showNext();
//                hidden_edit_view.requestFocus();
//
//                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.showSoftInput(hidden_edit_view, InputMethodManager.SHOW_IMPLICIT);
//            }
//        });
//
//        hidden_edit_view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    switcher.showNext();
//
//                    int new_Count = Integer.parseInt(hidden_edit_view.getText().toString());
//                    tv_numb.setText("< " + new_Count + " >");
//                }
//            }
//        });

        final CheckBox tv = (CheckBox)convertView.findViewById(R.id.checkBox);

        tv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedStrings.add(aTag);
                } else {
                    selectedStrings.remove(aTag);
                }

            }
        });

        return convertView;
    }

    public  ArrayList<TagCounts> getSelectedTags(){
        return selectedStrings;
    }
}

/*

      //  TextView tv_numb = (TextView) convertView.findViewById(R.id.tv_numb);
        final EditText hidden_edit_view = (EditText) convertView.findViewById(R.id.hidden_edit_view);
//

        if (hidden_edit_view.length() > 0) {
            hidden_edit_view.getText().clear();
        }
       // tv_numb.setText("< " + tag_map.getCount() + " >");
//
//        final ViewSwitcher switcher = (ViewSwitcher) convertView.findViewById(R.id.my_switcher);
//        final EditText hidden_edit_view = (EditText) convertView.findViewById(R.id.hidden_edit_view);
//
//        tv_numb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               switcher.showNext();
//               // switcher.getNextView().requestFocus();
//            }
//        });
 */