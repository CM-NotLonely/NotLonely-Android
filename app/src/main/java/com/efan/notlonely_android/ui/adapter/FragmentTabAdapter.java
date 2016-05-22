package com.efan.notlonely_android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.efan.basecmlib.activity.BaseFragment;
import com.efan.basecmlib.activity.BaseFragmentActivity;

import java.util.List;

/**
 * Created by 一帆 on 2016/3/22.
 */
public class FragmentTabAdapter {

    private List<BaseFragment> fragments; // 每个tab页面对应的Fragment
    private FragmentActivity fragmentActivity; // Fragment依赖的Activity
    private int fragmentContentId; // Activity中所要被替换的区域的id
    private int currentTab = -1; // 当前Tab页面索引
    private onFragmentChangedListener onFragmentListener;

    /**
     *
     * <p>Title:
     * <p>Description:
     * @param fragmentActivity
     * @param fragments fragment列表
     * @param fragmentContentId 视图容器的id
     */
    public FragmentTabAdapter(BaseFragmentActivity fragmentActivity, List<BaseFragment> fragments, int fragmentContentId){
        this.fragments = fragments;
        this.fragmentActivity = fragmentActivity;
        this.fragmentContentId = fragmentContentId;
        // 默认显示第一页
//         FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
//         ft.add(fragmentContentId, fragments.get(0));
//         ft.commit();
//         if (onFragmentListener != null) {
//        	 onFragmentListener.onFragmentChanged(0);
//		}
    }

    /**
     * 设置当前Tab，索引从0开始
     * <p>Title: setCurrentTab
     * <p>Description:
     * @param index
     */
    public void setCurrentTab(int index) {
        // TODO Auto-generated method stub
        Fragment fragment = fragments.get(index);
        FragmentTransaction ft = obtainFragmentTransaction(index);
        if (currentTab >= 0) {
            getCurrentFragment().onPause(); // 暂停当前tab
        }

        if(fragment.isAdded()){
            fragment.onResume(); // 启动目标tab的onResume()
        }else{
            ft.add(fragmentContentId, fragment,"index");
        }
        showTab(index); // 显示目标tab
        ft.commit();

        if(null != onFragmentListener){
            onFragmentListener.onFragmentChanged(index);
        }
    }

    /**
     * 切换tab
     * @param index
     */
    private void showTab(int index){
        for(int i = 0; i < fragments.size(); i++){
            Fragment fragment = fragments.get(i);
            FragmentTransaction ft = obtainFragmentTransaction(index);

            if(index == i){
                ft.show(fragment);
            }else{
                ft.hide(fragment);
            }
            ft.commit();
        }
        currentTab = index; // 更新目标tab为当前tab
    }

    /**
     * 获取带动画的FragmentTransaction
     * @param index
     * @return
     */
    private FragmentTransaction obtainFragmentTransaction(int index){
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
        // 设置切换动画
//      if(index > currentTab){
//          ft.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
//      }else{
//          ft.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out);
//      }
        return ft;
    }

    public int getCurrentTab() {
        return currentTab;
    }

    public Fragment getCurrentFragment(){
        return fragments.get(currentTab);
    }

    public void setOnFragmentListener(onFragmentChangedListener onFragmentListener) {
        this.onFragmentListener = onFragmentListener;
    }

    /**
     * fragment切换，外部调用接口
     * @author blakequ Blakequ@gmail.com
     *
     */
    public interface onFragmentChangedListener{
        public void onFragmentChanged(int index);
    }

}
