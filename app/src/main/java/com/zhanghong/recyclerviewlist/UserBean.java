package com.zhanghong.recyclerviewlist;

import java.io.Serializable;

/**
 * Created by ZhangHong on 2017/3/22.
 */
public class UserBean implements Serializable {
    private String mName;
    private int mType;

    public UserBean(String name, int type) {
        mName = name;
        mType = type;
    }

    public String getmName() {
        return mName;
    }

    public int getmType() {
        return mType;
    }

}
