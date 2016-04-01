package com.efan.notlonely_android.ui;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efan.basecmlib.activity.BaseFragment;
import com.efan.basecmlib.activity.BaseFragmentActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.basecmlib.others.SystemBarTintManager;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.ui.adapter.FragmentTabAdapter;
import com.efan.notlonely_android.ui.find.FindFragment;
import com.efan.notlonely_android.ui.menu.MenuFragment;
import com.efan.notlonely_android.ui.message.MessageFragment;
import com.efan.notlonely_android.ui.mine.MineFragment;
import com.efan.notlonely_android.view.BlurringView;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

@ContentView(id = R.layout.activity_main)
public class MainActivity extends BaseFragmentActivity {

    @ViewInject(id=R.id.tv_title)
    private TextView tv_title;
    @ViewInject(id=R.id.ll_title)
    private LinearLayout ll_title;
    @ViewInject(id = R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(id = R.id.tv_activity)
    private Button button1;
    @ViewInject(id = R.id.tv_circle)
    private Button button2;
    @ViewInject(id = R.id.menu)
    private ImageButton menuButton;
    @ViewInject(id = R.id.find)
    private ImageButton findButton;
    @ViewInject(id = R.id.raise)
    private ImageButton raiseButton;
    @ViewInject(id = R.id.message)
    private ImageButton messageButton;
    @ViewInject(id = R.id.mine)
    private ImageButton mineButton;
    @ViewInject(id = R.id.bottombar)
    private LinearLayout bottomLayout;
    @ViewInject(id = R.id.framelayout)
    private FrameLayout frameLayout;
    @ViewInject(id=R.id.blurring_view)
    private BlurringView blur;

    private FragmentTabAdapter fragmentTabAdapter;
    private List<BaseFragment> list;

    @Override
    public void initView() {
        Fresco.initialize(this);
//        blur.setBlurredView(frameLayout);
        setStatusBarColor();
        setFrameLayout();
        setSeletedBottomBar(1);
    }

    @Override
    public void initData() {
//        Map<String,String> params = new HashMap<>();
//        params.put("username","123456");
//        params.put("password","123456");
//        params.put("introduction","test");
//        params.put("sex","true");
//        params.put("nickname","test");
//
//        OkHttpUtils.post()
//                .url("http://192.168.1.236:3000/create.json")
//                .params(params)
//                .build()
//                .execute(new Callback() {
//                    @Override
//                    public Object parseNetworkResponse(Response response) throws Exception {
//                        return response.body().string();
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e) {
//                        Log.d("bb",e.toString());
//                    }
//
//                    @Override
//                    public void onResponse(Object response) {
//                        Log.d("aa",response.toString());
//                    }
//                });
    }

    @Override
    public void initEvent() {

    }

    @Override
    @OnClick(value = {R.id.tv_activity, R.id.tv_circle, R.id.menu, R.id.find, R.id.raise, R.id.message, R.id.mine})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_activity:
                break;
            case R.id.tv_circle:
                break;
            case R.id.menu:
                setSeletedBottomBar(1);
                break;
            case R.id.find:
                setSeletedBottomBar(2);
                break;
            case R.id.raise:
                setSeletedBottomBar(1);
                break;
            case R.id.message:
                setSeletedBottomBar(4);
                break;
            case R.id.mine:
                setSeletedBottomBar(5);
                break;
            default:
                break;
        }
    }

    /**
     * 设置FrameLyout
     */
    private void setFrameLayout(){
        list = new ArrayList<BaseFragment>();
        list.add(new MenuFragment());
        list.add(new FindFragment());
        list.add(new MessageFragment());
       // list.add(new MineFragment());
        list.add(new MineFragment());
        fragmentTabAdapter = new FragmentTabAdapter(this, list, R.id.framelayout);
        fragmentTabAdapter.setOnFragmentListener(new FragmentTabAdapter.onFragmentChangedListener() {
            @Override
            public void onFragmentChanged(int index) {

            }
        });
    }

    /**
     * 设置底部Bar的按钮选中状态
     * @param index
     */
    private void setSeletedBottomBar(int index){
        menuButton.setSelected(false);
        findButton.setSelected(false);
        messageButton.setSelected(false);
        mineButton.setSelected(false);
        tv_title.setVisibility(View.GONE);
        ll_title.setVisibility(View.GONE);
        switch (index){
            case 1:
                ll_title.setVisibility(View.VISIBLE);
                menuButton.setSelected(true);
                tv_title.setVisibility(View.GONE);
                ll_title.setVisibility(View.VISIBLE);
                fragmentTabAdapter.setCurrentTab(0);
                break;
            case 2:
                tv_title.setText("发现");
                tv_title.setVisibility(View.VISIBLE);
                findButton.setSelected(true);
                fragmentTabAdapter.setCurrentTab(1);
                break;
            case 3:
                break;
            case 4:
                tv_title.setText("消息");
                tv_title.setVisibility(View.VISIBLE);
                messageButton.setSelected(true);
                fragmentTabAdapter.setCurrentTab(2);
                break;
            case 5:
                tv_title.setText("个人");
                tv_title.setVisibility(View.VISIBLE);
                mineButton.setSelected(true);
                fragmentTabAdapter.setCurrentTab(3);
                break;
            default:
                break;
        }
    }

    /**
     * 改变系统状态栏的颜色，沉浸效果
     */
    private void setStatusBarColor(){
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setTintColor(getResources().getColor(R.color.common));
    }
}
