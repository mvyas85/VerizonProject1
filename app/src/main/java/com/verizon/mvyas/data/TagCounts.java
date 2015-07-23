package com.verizon.mvyas.data;

/**
 * Created by manisha on 7/22/15.
 */
public class TagCounts implements Comparable<TagCounts>{
    private String tag;
    private int count;

    public TagCounts() {
    }

    public TagCounts(String tag, int count) {
        this.tag = tag;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTag() {

        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int compareTo(TagCounts another) {
        if (count <  another.getCount())
            return -1;
        if (count > another.getCount())
            return 1;

        return 0;
    }
}
