package com.verizon.mvyas.tags_asc_dsc_libs;


import android.util.Log;

import com.verizon.mvyas.tags_asc_dsc_libs.data.TagCounts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by manisha on 7/23/15.
 */
public class TagsUtil {

    public static List<TagCounts> sortTagsAsc(ArrayList<TagCounts> tag_array_list) {
        for(int i=0;i<tag_array_list.size();i++){
            Log.d("Hi Asc 1 = "+i + " = ", tag_array_list.get(i).getTag());
        }
        List<TagCounts> sorted = tag_array_list;
        Collections.sort(tag_array_list,new TagCounts());
        for(int i=0;i<tag_array_list.size();i++){
            Log.d("Hi Asc2 = "+i + " = ", tag_array_list.get(i).getTag());
        }
       return tag_array_list;
    }
    public static  List<TagCounts> sortTagsDsc(ArrayList<TagCounts> tag_array_list) {
        for(int i=0;i<tag_array_list.size();i++){
            Log.d("Hi Dsc1 = "+i + " = ", tag_array_list.get(i).getTag());
        }
       List<TagCounts> sorted = tag_array_list;
        Collections.sort(tag_array_list,Collections.reverseOrder(new TagCounts()));
        for(int i=0;i<tag_array_list.size();i++){
            Log.d("Hi Dsc2 = "+i + " = ", tag_array_list.get(i).getTag());
        }
       return tag_array_list;
    }
}
