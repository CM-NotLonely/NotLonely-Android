/*
 * Copyright (C) 2013 Peng fei Pan <sky@xiaopan.me>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.efan.notlonely.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.efan.notlonely.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * רΪViewPager���ƵĻ���ѡ� HOME URL��http://github.com/xiaopansky/PagerSlidingTabStrip
 * @version 1.6.0
 * @author Peng fei Pan
 */
public class PagerSlidingTabStrip extends HorizontalScrollView implements View.OnClickListener {
    private int currentPosition;	//��ǰλ��
    private int lastOffset;
    private int lastScrollX = 0;
    private float currentPositionOffset;	//��ǰλ��ƫ����
    private float FirstPositionOffset;
    private boolean start;
    private boolean allowWidthFull;    // ���ݿ���޷�����ʱ�������Զ�����Item�Ŀ���Գ���
    private boolean disableViewPager;   // ����ViewPager
    private Drawable slidingBlockDrawable;	//����
    private ViewPager viewPager;	//ViewPager
    private ViewGroup tabsLayout;	//�������
    private ViewPager.OnPageChangeListener onPageChangeListener;	//ҳ��ı������
    private OnClickTabListener onClickTabListener;
    private List<View> tabViews;
    private boolean disableTensileSlidingBlock; // ��ֹ���컬��ͼƬ
    private TabViewFactory tabViewFactory;

    public PagerSlidingTabStrip(Context context) {
        this(context, null);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        setHorizontalScrollBarEnabled(false);	//
        removeAllViews();
        if(attrs != null){
            TypedArray attrsTypedArray = context.obtainStyledAttributes(attrs, R.styleable.PagerSlidingTabStrip);
            if(attrsTypedArray != null){
                allowWidthFull = attrsTypedArray.getBoolean(R.styleable.PagerSlidingTabStrip_allowWidthFull, false);
                slidingBlockDrawable = attrsTypedArray.getDrawable(R.styleable.PagerSlidingTabStrip_slidingBlock);
                disableViewPager = attrsTypedArray.getBoolean(R.styleable.PagerSlidingTabStrip_disableViewPager, false);
                disableTensileSlidingBlock = attrsTypedArray.getBoolean(R.styleable.PagerSlidingTabStrip_disableTensileSlidingBlock, false);
                attrsTypedArray.recycle();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(allowWidthFull && tabsLayout != null){
            View childView;
            for(int w = 0, size = tabsLayout.getChildCount(); w < size; w++){
                childView = tabsLayout.getChildAt(w);
                ViewGroup.LayoutParams params = childView.getLayoutParams();
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                childView.setLayoutParams(params);
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(!allowWidthFull){
            return;
        }
        ViewGroup tabsLayout = getTabsLayout();
        if(tabsLayout == null){
            return;
        }
        if(tabsLayout.getChildCount() <= 0){
            return;
        }

        if(tabViews == null){
            tabViews = new ArrayList<View>();
        }else{
            tabViews.clear();
        }
        for(int w = 0; w < tabsLayout.getChildCount(); w++){
            tabViews.add(tabsLayout.getChildAt(w));
        }

        adjustChildWidthWithParent(tabViews, getMeasuredWidth()-tabsLayout.getPaddingLeft()-tabsLayout.getPaddingRight(), widthMeasureSpec, heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * ����views�����е�View��������View�Ŀ�ȼ��������õ���parentViewWidth
     * @param views ��View����
     * @param parentViewWidth ��Vie�Ŀ��
     * @param parentWidthMeasureSpec ��View�Ŀ�ȹ���
     * @param parentHeightMeasureSpec ��View�ĸ߶ȹ���
     */
    private void adjustChildWidthWithParent(List<View> views, int parentViewWidth, int parentWidthMeasureSpec, int parentHeightMeasureSpec){
        for(View view : views){
            if(view.getLayoutParams() instanceof MarginLayoutParams){
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
                parentViewWidth -= lp.leftMargin + lp.rightMargin;
            }
        }

        // ȥ����ȴ���ƽ����ȵ�View���ٴμ���ƽ�����
        int averageWidth = parentViewWidth /views.size();
        int bigTabCount = views.size();
        while(true){
            Iterator<View> iterator = views.iterator();
            while(iterator.hasNext()){
                View view = iterator.next();
                if(view.getMeasuredWidth() > averageWidth){
                    parentViewWidth -= view.getMeasuredWidth();
                    bigTabCount--;
                    iterator.remove();
                }
            }
            if(bigTabCount <= 0){
                break;
            }
            averageWidth = parentViewWidth /bigTabCount;
            boolean end = true;
            for(View view : views){
                if(view.getMeasuredWidth() > averageWidth){
                    end = false;
                }
            }
            if(end){
                break;
            }
        }

        int i=0;
        // �޸Ŀ��С���µ�ƽ����ȵ�View�Ŀ��
        for(View view : views){
            if(view.getMeasuredWidth() < averageWidth){
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                layoutParams.width = averageWidth;
                //----_>
                //rest[i]=averageWidth-view.getMeasuredWidth();
                view.setLayoutParams(layoutParams);
                // �ٴβ������¿����Ч
                if(layoutParams instanceof MarginLayoutParams){
                    measureChildWithMargins(view, parentWidthMeasureSpec, 0, parentHeightMeasureSpec, 0);
                }else{
                    measureChild(view, parentWidthMeasureSpec, parentHeightMeasureSpec);
                }
            }
            i++;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        ViewGroup tabViewGroup = getTabsLayout();
        FirstPositionOffset=(float)tabViewGroup.getLeft();
        Log.e("aaaaaa", String.valueOf(FirstPositionOffset));
        if(tabViewGroup != null){

            currentPosition = viewPager != null?viewPager.getCurrentItem():0;
            if(!disableViewPager){
                scrollToChild(currentPosition, 0);
                selectedTab(currentPosition);
            }

            //��ÿһ��tab���õ���¼����������ʱ���л�Pager
            for(int w = 0; w < tabViewGroup.getChildCount(); w++){
                View itemView = tabViewGroup.getChildAt(w);
                itemView.setTag(w);
                itemView.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int index = (Integer) v.getTag();
        if(onClickTabListener != null){
            onClickTabListener.onClickTab(v, index);
        }
        if(viewPager != null){
            viewPager.setCurrentItem(index, true);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(disableViewPager) return;
		/* ���ƻ��� */
        ViewGroup tabsLayout = getTabsLayout();
        if(tabsLayout != null && tabsLayout.getChildCount() > 0 && slidingBlockDrawable != null){
            View currentTab = tabsLayout.getChildAt(currentPosition);
            if(currentTab != null){
                float slidingBlockLeft = currentTab.getLeft()+FirstPositionOffset;
                float slidingBlockRight = currentTab.getRight()+FirstPositionOffset;
                if (currentPositionOffset > 0f && currentPosition < tabsLayout.getChildCount() - 1) {
                    View nextTab = tabsLayout.getChildAt(currentPosition + 1);
                    if(nextTab != null){
                        final float nextTabLeft = nextTab.getLeft()+FirstPositionOffset;
                        final float nextTabRight = nextTab.getRight()+FirstPositionOffset;
                        slidingBlockLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * slidingBlockLeft);
                        slidingBlockRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * slidingBlockRight);
                    }
                }

                // ������
                if(disableTensileSlidingBlock){
                    int center = (int) (slidingBlockLeft + (slidingBlockRight-slidingBlockLeft)/2);
                    slidingBlockLeft = center - slidingBlockDrawable.getIntrinsicWidth()/2;
                    slidingBlockRight = center + slidingBlockDrawable.getIntrinsicWidth()/2;
                }

                slidingBlockDrawable.setBounds((int)slidingBlockLeft, getHeight()-slidingBlockDrawable.getIntrinsicHeight(), (int)slidingBlockRight, getHeight());
                slidingBlockDrawable.draw(canvas);
            }
        }
    }

    /**
     * ��ȡ����
     */
    private ViewGroup getTabsLayout(){
        if(tabsLayout == null){
            if(getChildCount() > 0){
                tabsLayout = (ViewGroup) getChildAt(0);
            }else{
                removeAllViews();
                LinearLayout tabsLayout = new LinearLayout(getContext());
                tabsLayout.setGravity(Gravity.CENTER_VERTICAL);
                this.tabsLayout = tabsLayout;
                addView(tabsLayout, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL));
            }
        }
        return tabsLayout;
    }

    /**
     * ���ã��������tab
     */
    public void reset(){
        if(tabViewFactory != null){
            tabViewFactory.addTabs(getTabsLayout(), viewPager != null?viewPager.getCurrentItem():0);
        }
    }

    /**
     * ��ȡTab
     * @param position λ��
     * @return Tab��View
     */
    public View getTab(int position){
        if(tabsLayout != null && tabsLayout.getChildCount() > position){
            return tabsLayout.getChildAt(position);
        }else{
            return null;
        }
    }

    /**
     * ������ָ����λ��
     */
    private void scrollToChild(int position, int offset) {
        ViewGroup tabsLayout = getTabsLayout();
        if(tabsLayout != null && tabsLayout.getChildCount() > 0 && position < tabsLayout.getChildCount()){
            View view = tabsLayout.getChildAt(position);
            if(view != null){
                //�����µ�X����
                int newScrollX = view.getLeft() + offset - getLeftMargin(view);
                if (position > 0 || offset > 0) {
                    newScrollX -= getWidth()/2 - getOffset(view.getWidth())/2;
                }

                //���ͬ�ϴ�X���겻һ����ִ�й���
                if (newScrollX != lastScrollX) {
                    lastScrollX = newScrollX;
                    scrollTo(newScrollX, 0);
                }
            }
        }
    }

    private int getLeftMargin(View view){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if(params instanceof MarginLayoutParams){
            MarginLayoutParams marginParams = (MarginLayoutParams) params;
            return marginParams.leftMargin;
        }
        return 0;
    }

    private int getRightMargin(View view){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if(params instanceof MarginLayoutParams){
            MarginLayoutParams marginParams = (MarginLayoutParams) params;
            return marginParams.rightMargin;
        }
        return 0;
    }

    /**
     * ��ȡƫ����
     */
    private int getOffset(int newOffset){
        if(lastOffset < newOffset){
            if(start){
                lastOffset += 1;
                return lastOffset;
            }else{
                start = true;
                lastOffset += 1;
                return lastOffset;
            }
        }if(lastOffset > newOffset){
            if(start){
                lastOffset -= 1;
                return lastOffset;
            }else{
                start = true;
                lastOffset -= 1;
                return lastOffset;
            }
        }else{
            start = true;
            lastOffset = newOffset;
            return lastOffset;
        }
    }

    /**
     * ѡ��ָ��λ�õ�TAB
     */
    private void selectedTab(int newSelectedTabPosition){
        ViewGroup tabsLayout = getTabsLayout();
        if(newSelectedTabPosition > -1 && tabsLayout != null && newSelectedTabPosition < tabsLayout.getChildCount()){
            for(int w = 0, size = tabsLayout.getChildCount(); w < size; w++){
                View tabView = tabsLayout.getChildAt(w);
                tabView.setSelected(w==newSelectedTabPosition);
            }
        }
    }

    /**
     * ����ViewPager
     * @param viewPager ViewPager
     */
    public void setViewPager(ViewPager viewPager) {
        if(disableViewPager) return;
        this.viewPager = viewPager;
        this.viewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                selectedTab(position);
                if(onPageChangeListener != null){
                    onPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrolled(int nextPagePosition, float positionOffset, int positionOffsetPixels) {
                ViewGroup tabsLayout = getTabsLayout();
                if(nextPagePosition < tabsLayout.getChildCount()){
                    View view = tabsLayout.getChildAt(nextPagePosition);
                    if(view != null){
                        currentPosition = nextPagePosition;
                        currentPositionOffset = positionOffset;
                        scrollToChild(nextPagePosition, (int) (positionOffset * (view.getWidth() + getLeftMargin(view) + getRightMargin(view))));
                        invalidate();
                    }
                }
                if(onPageChangeListener != null){
                    onPageChangeListener.onPageScrolled(nextPagePosition, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                if(onPageChangeListener != null){
                    onPageChangeListener.onPageScrollStateChanged(arg0);
                }
            }
        });
        requestLayout();
    }

    /**
     * ����Page�л�������
     * @param onPageChangeListener Page�л�������
     */
    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    /**
     * �����Ƿ������Ļ
     * @param allowWidthFull true�������ݵĿ���޷�������Ļʱ���Զ�����ÿһ��Item�Ŀ���Գ�����Ļ
     */
    public void setAllowWidthFull(boolean allowWidthFull) {
        this.allowWidthFull = allowWidthFull;
        requestLayout();
    }

    /**
     * ���û���ͼƬ
     */
    public void setSlidingBlockDrawable(Drawable slidingBlockDrawable) {
        this.slidingBlockDrawable = slidingBlockDrawable;
        requestLayout();
    }

    /**
     * �����Ƿ��ֹ���컬��ͼƬ
     * @param disableTensileSlidingBlock �Ƿ��ֹ���컬��ͼƬ
     */
    public void setDisableTensileSlidingBlock(boolean disableTensileSlidingBlock) {
        this.disableTensileSlidingBlock = disableTensileSlidingBlock;
        invalidate();
    }

    /**
     * ��ȡTab����
     */
    public int getTabCount(){
        ViewGroup tabsLayout = getTabsLayout();
        return tabsLayout!=null?tabsLayout.getChildCount():0;
    }

    /**
     * ����Tab���������
     * @param onClickTabListener Tab���������
     */
    public void setOnClickTabListener(OnClickTabListener onClickTabListener) {
        this.onClickTabListener = onClickTabListener;
    }

    /**
     * ���ò�ʹ��ViewPager
     * @param disableViewPager ��ʹ��ViewPager
     */
    public void setDisableViewPager(boolean disableViewPager) {
        this.disableViewPager = disableViewPager;
        if(viewPager != null){
            viewPager.setOnPageChangeListener(onPageChangeListener);
            viewPager = null;
        }
        requestLayout();
    }

    /**
     * ����TabView������
     * @param tabViewFactory
     */
    public void setTabViewFactory(TabViewFactory tabViewFactory) {
        this.tabViewFactory = tabViewFactory;
        tabViewFactory.addTabs(getTabsLayout(), viewPager!=null?viewPager.getCurrentItem():0);
    }

    /**
     * Tab���������
     */
    public interface OnClickTabListener {
        public void onClickTab(View tab, int index);
    }

    /**
     * TabView������
     */
    public interface TabViewFactory{
        /**
         * ���tab
         * @param parent
         * @param defaultPosition
         */
        public void addTabs(ViewGroup parent, int defaultPosition);
    }
}