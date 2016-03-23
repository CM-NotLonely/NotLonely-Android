package com.efan.notlonely.ui;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.efan.basecmlib.activity.BaseFragment;
import com.efan.basecmlib.activity.BaseFragmentActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.basecmlib.others.SystemBarTintManager;
import com.efan.notlonely.R;
import com.efan.notlonely.ui.adapter.FragmentTabAdapter;
import com.efan.notlonely.ui.find.FindFragment;
import com.efan.notlonely.ui.menu.MenuFragment;
import com.efan.notlonely.ui.message.MessageFragment;
import com.efan.notlonely.ui.mine.MineFragment;

import java.util.ArrayList;
import java.util.List;

@ContentView(id = R.layout.activity_main)
public class MainActivity extends BaseFragmentActivity {

    @ViewInject(id = R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(id = R.id.button1)
    private Button button1;
    @ViewInject(id = R.id.button2)
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

    private FragmentTabAdapter fragmentTabAdapter;
    private List<BaseFragment> list;

    @Override
    public void initView() {
        setStatusBarColor();
        setFrameLayout();
        setSeletedBottomBar(1);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    @OnClick(value = {R.id.button1, R.id.button2, R.id.menu, R.id.find, R.id.raise, R.id.message, R.id.mine})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:

                break;
            case R.id.button2:

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
        switch (index){
            case 1:
                menuButton.setSelected(true);
                findButton.setSelected(false);
                messageButton.setSelected(false);
                mineButton.setSelected(false);
                fragmentTabAdapter.setCurrentTab(0);
                break;
            case 2:
                findButton.setSelected(true);
                menuButton.setSelected(false);
                messageButton.setSelected(false);
                mineButton.setSelected(false);
                fragmentTabAdapter.setCurrentTab(1);
                break;
            case 3:
                menuButton.setSelected(false);
                findButton.setSelected(false);
                messageButton.setSelected(false);
                mineButton.setSelected(false);
                break;
            case 4:
                messageButton.setSelected(true);
                menuButton.setSelected(false);
                findButton.setSelected(false);
                mineButton.setSelected(false);
                fragmentTabAdapter.setCurrentTab(2);
                break;
            case 5:
                mineButton.setSelected(true);
                menuButton.setSelected(false);
                findButton.setSelected(false);
                messageButton.setSelected(false);
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
