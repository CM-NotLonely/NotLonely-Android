package com.efan.notlonely_android.ui.menu;

import android.view.View;
import android.widget.SimpleAdapter;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 一帆 on 2016/4/1.
 */
@ContentView(id = R.layout.activity_activity)
public class ActivityDetailsActivity extends BaseActivity{

//    @ViewInject(id = R.id.grid)
//    private GridView gridView;
    @ViewInject(id = R.id.list_background)
    private View mListBackgroundView;

    @Override
    public void initView() {
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<50;i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", R.mipmap.ic_launcher);//添加图像资源的ID
            map.put("ItemText", "NO."+String.valueOf(i));//按序号做ItemText
            lstImageItem.add(map);
        }
        //生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
        SimpleAdapter saImageItems = new SimpleAdapter(this, //没什么解释
                lstImageItem,//数据来源
                R.layout.item_grid_user,//night_item的XML实现

                //动态数组与ImageItem对应的子项
                new String[] {"ItemImage","ItemText"},

                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[] {R.id.ItemImage,R.id.ItemText});
        //添加并且显示
//        gridView.setAdapter(saImageItems);
        //添加消息处理
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View v) {

    }
}
