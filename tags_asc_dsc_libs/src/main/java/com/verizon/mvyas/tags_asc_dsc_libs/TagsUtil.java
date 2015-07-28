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

        List<TagCounts> sorted = tag_array_list;
        Collections.sort(tag_array_list,new TagCounts());

       return tag_array_list;
    }
    public static  List<TagCounts> sortTagsDsc(ArrayList<TagCounts> tag_array_list) {

       List<TagCounts> sorted = tag_array_list;
        Collections.sort(tag_array_list,Collections.reverseOrder(new TagCounts()));

       return tag_array_list;
    }
}
