package com.zhanghong.recyclerviewlist;

import android.view.View;

/**
 * Created by DAB on 2016/8/29 09:32.
 * RecyclerView的回调接口
 *
 */
public interface OnItemClickListener {
    void onItemClick(View view,Object o, int position);
}
