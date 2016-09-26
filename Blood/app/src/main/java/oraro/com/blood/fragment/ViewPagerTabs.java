package oraro.com.blood.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import oraro.com.blood.R;

/**
 * Created by Administrator on 2016/9/26.
 */
public class ViewPagerTabs extends HorizontalScrollView implements ViewPager.OnPageChangeListener {

    ViewPager mPager;
    private ViewPagerTabStrip mTabStrip;
    private int[] mTabIcons;
    final int mTextStyle;
    final ColorStateList mTextColor;
    final int mTextSize;
    final boolean mTextAllCaps;
    int mPrevSelected = -1;
    int mSidePadding;

    private static final int[] ATTRS = new int[] {
            android.R.attr.textSize,
            android.R.attr.textStyle,
            android.R.attr.textColor,
            android.R.attr.textAllCaps
    };

    private class OnTabLongClickListener implements OnLongClickListener {
        final int mPosition;

        public OnTabLongClickListener(int position) {
            mPosition = position;
        }

        @Override
        public boolean onLongClick(View v) {
            final int[] screenPos = new int[2];
            getLocationOnScreen(screenPos);

            final Context context = getContext();
            final int width = getWidth();
            final int height = getHeight();
            final int screenWidth = context.getResources().getDisplayMetrics().widthPixels;

            Toast toast = Toast.makeText(context, mPager.getAdapter().getPageTitle(mPosition),
                    Toast.LENGTH_SHORT);

            // Show the toast under the tab
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL,
                    (screenPos[0] + width / 2) - screenWidth / 2, screenPos[1] + height);

            toast.show();
            return true;
        }
    }

    public ViewPagerTabs(Context context) {
        this(context, null);
    }
    public ViewPagerTabs(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }
    public ViewPagerTabs (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFillViewport(true);
        mSidePadding =(int) getResources().getDisplayMetrics().density * 10;
        final TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
        mTextSize = a.getDimensionPixelSize(0, 0);
        mTextStyle = 0;
        mTextColor = null;
        mTextAllCaps =  false;

        mTabStrip = new ViewPagerTabStrip(context);
        addView(mTabStrip,
                new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        a.recycle();
    }


    public void setTabIcons(int [] tabIcons) {
        mTabIcons = tabIcons;
    }

    public void setViewPager(ViewPager viewPager) {
        mPager = viewPager;
        addTabs(mPager.getAdapter());
    }

    private void addTabs(PagerAdapter adapter) {
        mTabStrip.removeAllViews();

        final int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            addTab(adapter.getPageTitle(i), i);
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @SuppressWarnings("ResourceAsColor")
    private void addTab(CharSequence tabTitle, final int position) {
        View tabView;
        if (mTabIcons != null && position < mTabIcons.length) {
            View iconView = new View(getContext());
            iconView.setBackgroundResource(mTabIcons[position]);
            iconView.setContentDescription(tabTitle);

            tabView = iconView;
        } else {
            final TextView textView = new TextView(getContext());
            textView.setText(tabTitle);
            textView.setBackgroundColor(android.R.color.white);
            // Assign various text appearance related attributes to child views.
            if (mTextStyle > 0) {
                textView.setTypeface(textView.getTypeface(), mTextStyle);
            }
            if (mTextSize > 0) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
            }
            if (mTextColor != null) {
                textView.setTextColor(mTextColor);
            }
            textView.setAllCaps(mTextAllCaps);
            textView.setGravity(Gravity.CENTER);

            tabView = textView;
        }

        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(getRtlPosition(position));
            }
        });

        tabView.setOnLongClickListener(new OnTabLongClickListener(position));

        tabView.setPadding(mSidePadding, 0, mSidePadding, 0);
        mTabStrip.addView(tabView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT, 1));

        // Default to the first child being selected
        if (position == 0) {
            mPrevSelected = 0;
            tabView.setSelected(true);
        }
    }

    private int getRtlPosition(int position) {
        return position;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
