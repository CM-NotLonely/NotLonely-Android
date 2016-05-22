package com.efan.notlonely_android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.efan.basecmlib.activity.BaseFragment;
import com.efan.basecmlib.activity.BaseFragmentActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.basecmlib.annotate.ViewInjectUtils;
import com.efan.basecmlib.others.SystemBarTintManager;
import com.efan.notlonely_android.MainApplication;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.ui.adapter.FragmentTabAdapter;
import com.efan.notlonely_android.ui.find.FindFragment;
import com.efan.notlonely_android.ui.menu.MenuFragment;
import com.efan.notlonely_android.ui.message.MessageFragment;
import com.efan.notlonely_android.ui.mine.AlterpasswordActivity;
import com.efan.notlonely_android.ui.mine.MineFragment;
import com.efan.notlonely_android.ui.publish.PubActivityActivity;
import com.efan.notlonely_android.utils.ActivityUtils;
import com.efan.notlonely_android.utils.IntentUtils;
import com.efan.notlonely_android.utils.ToastUtils;
import com.efan.notlonely_android.view.BlurringView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.jaeger.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseFragmentActivity {

    @ViewInject(id = R.id.tv_title)
    private TextView tv_title;
    @ViewInject(id = R.id.ll_title)
    private LinearLayout ll_title;
    @ViewInject(id = R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(id = R.id.tv_activity)
    private Button button1;
    @ViewInject(id = R.id.tv_circle)
    private Button button2;
    @ViewInject(id = R.id.setting)
    private ImageView setting;
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
    @ViewInject(id = R.id.blurring_view)
    private BlurringView blur;

    private FragmentTabAdapter fragmentTabAdapter;
    private List<BaseFragment> list;
    private MenuFragment menuFragment;
    private FindFragment findFragment;
    private MessageFragment messageFragment;
    private MineFragment mineFragment;
    private FragmentManager fragmentManager;

    //记录Fragment的位置
    private int position=1;

    /**
     *当activity恢复至前台时，得到之前未销毁的fragment实例，以及位置
     * 这时会先执行onCreate方法
     *by: linqh0806
     *at: 2016/5/22 17:11
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        position = savedInstanceState.getInt("position");
        setSeletedBottomBar(position);
        Log.d("MainActivity","onRestoreInstanceState set the position");
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     *当activity不再前台时，意外销毁，但fragment实例没有销毁
     *存储那时fragment的位置信息
     *by: linqh0806
     *at: 2016/5/22 17:09
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //记录当前的position
        Log.d("MainActivity","onSaveInstanceState save the position");
        outState.putInt("position", position);
    }

    /**
     *沉浸式StatusBar状态显示
     *解决hide，show方法导致的fragment重影问题
     *by: linqh0806
     *at: 2016/5/22 17:57
     */
    @Override
    protected void onCreate(Bundle arg0) {
        if (arg0 != null) {
            Log.d("MainActivity","get the old fragment");
            fragmentManager = getSupportFragmentManager();
            menuFragment= (MenuFragment) fragmentManager.findFragmentByTag("0");
            findFragment= (FindFragment) fragmentManager.findFragmentByTag("1");
            messageFragment= (MessageFragment) fragmentManager.findFragmentByTag("2");
            mineFragment= (MineFragment) fragmentManager.findFragmentByTag("3");
        }
        setContentView(R.layout.activity_main);
        StatusBarUtil.setColorNoTranslucent(this,getResources().getColor(R.color.common));
        setStatusBarColor();
        super.onCreate(arg0);
    }

    @Override
    public void initView() {
        Fresco.initialize(this);
//        blur.setBlurredView(frameLayout);
        setFrameLayout();
        setSeletedBottomBar(position);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initEvent() {
    }

    @Override
    @OnClick(value = {R.id.tv_activity, R.id.tv_circle, R.id.menu, R.id.find, R.id.raise, R.id.message, R.id.mine, R.id.setting})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu:
                position=1;
                setSeletedBottomBar(1);
                break;
            case R.id.find:
                position=2;
                setSeletedBottomBar(2);
                break;
            case R.id.raise:
                setSeletedBottomBar(3);
                break;
            case R.id.message:
                position=4;
                setSeletedBottomBar(4);
                break;
            case R.id.mine:
                position=5;
                setSeletedBottomBar(5);
                break;
            case R.id.setting:
                if (MainApplication.getInstance().isLogin()) {
                    Intent intent = new Intent(this, AlterpasswordActivity.class);
                    startActivity(intent);
                } else Toast.makeText(this, "主人还未登录哦~~~", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    /**
     *初始化fragmentTabAdapter
     *by: linqh0806
     *at: 2016/5/22 16:39
     */
    private void setFrameLayout() {
        list = new ArrayList<BaseFragment>();

        if(menuFragment==null){menuFragment=new MenuFragment();}
        if(findFragment==null){findFragment=new FindFragment();}
        if(messageFragment==null){messageFragment=new MessageFragment();}
        if(mineFragment==null){mineFragment=new MineFragment();}

        list.add(menuFragment);
        list.add(findFragment);
        list.add(messageFragment);
        list.add(mineFragment);

        if(fragmentTabAdapter==null)fragmentTabAdapter = new FragmentTabAdapter(this, list, R.id.framelayout);
        fragmentTabAdapter.setOnFragmentListener(new FragmentTabAdapter.onFragmentChangedListener() {
            @Override
            public void onFragmentChanged(int index) {

            }
        });
    }

    /**
     *用来设置头部title显示的text内容,底部button状态
     *by: linqh0806
     *at: 2016/5/22 20:34
     */
    private void setSeletedBottomBar(int index) {
        switch (index) {
            case 1:
                tv_title.setVisibility(View.GONE);
                ll_title.setVisibility(View.VISIBLE);
                setting.setVisibility(View.GONE);
                menuButton.setSelected(true);
                findButton.setSelected(false);
                messageButton.setSelected(false);
                mineButton.setSelected(false);
                fragmentTabAdapter.setCurrentTab(0);
                break;
            case 2:
                tv_title.setText("发现");
                tv_title.setVisibility(View.VISIBLE);
                ll_title.setVisibility(View.GONE);
                setting.setVisibility(View.GONE);
                menuButton.setSelected(false);
                findButton.setSelected(true);
                messageButton.setSelected(false);
                mineButton.setSelected(false);
                fragmentTabAdapter.setCurrentTab(1);
                break;
            case 3:
                IntentUtils.startActivity(this, PubActivityActivity.class);
                break;
            case 4:
                tv_title.setText("消息");
                tv_title.setVisibility(View.VISIBLE);
                ll_title.setVisibility(View.GONE);
                setting.setVisibility(View.GONE);
                menuButton.setSelected(false);
                findButton.setSelected(false);
                messageButton.setSelected(true);
                mineButton.setSelected(false);
                fragmentTabAdapter.setCurrentTab(2);
                break;
            case 5:
                tv_title.setText("个人");
                tv_title.setVisibility(View.VISIBLE);
                ll_title.setVisibility(View.GONE);
                setting.setVisibility(View.VISIBLE);
                menuButton.setSelected(false);
                findButton.setSelected(false);
                messageButton.setSelected(false);
                mineButton.setSelected(true);
                fragmentTabAdapter.setCurrentTab(3);
                break;
            default:
                break;
        }
    }

    /**
     * 改变系统状态栏的颜色
     */
    private void setStatusBarColor() {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setTintColor(getResources().getColor(R.color.common));
    }

    @Override
    public void onBackPressed() {
        if (ActivityUtils.exitTwice()) {
            super.onBackPressed();
        } else {
            ToastUtils.show(getApplicationContext(), "再按一次退出程序");
        }
    }
}
