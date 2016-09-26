package oraro.com.blood.fragment;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import oraro.com.blood.R;

/**
 * Created by Administrator on 2016/9/26.
 */
public class ListFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPage;
    private static final String TAG = "ListFragment";

    public static final int TAB_INDEX_GENERATION = 0;
    public static final int TAB_INDEX_BIOGRAPHY = 1;
    public static final int TAB_INDEX_PHOTO = 2;
    public static final int TAB_INDEX_TEMPLE = 3;

    public static final int TAB_COUNT_DEFAULT = 3;
    public static final int TAB_COUNT_WITH_VOICEMAIL = 4;

    private GenerationFragment mGenerationFragment;
    private BiographyFragment mBiographyFragment;
    private PhotoFragment mPhotoFragment;
    private TempleFragment mTempleFragment;

    private int mTabIndex = TAB_INDEX_GENERATION;

    private static final int MAX_RECENTS_ENTRIES = 20;
    private ViewPager mViewPager;
    private ViewPagerTabs mViewPagerTabs;
    private ViewPagerAdapter mViewPagerAdapter;
    private RemoveView mRemoveView;
    private View mRemoveViewContent;
    private ActionBar mActionBar;

    private ArrayList<ViewPager.OnPageChangeListener> mOnPageChangeListeners =
            new ArrayList<ViewPager.OnPageChangeListener>();

    private String[] mTabTitles;
    private int[] mTabIcons;


    public class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public long getItemId(int position) {
            return getRtlPosition(position);
        }

        @Override
        public Fragment getItem(int position) {
            switch (getRtlPosition(position)) {
                case TAB_INDEX_GENERATION:
                    mGenerationFragment = new GenerationFragment();
                    return mGenerationFragment;
                case TAB_INDEX_BIOGRAPHY:
                mBiographyFragment = new BiographyFragment();
                return mBiographyFragment;
                case TAB_INDEX_PHOTO:
                    mPhotoFragment = new PhotoFragment();
                    return mPhotoFragment;
                case TAB_INDEX_TEMPLE:
                    mTempleFragment = new TempleFragment();
                    return mTempleFragment;
            }
            throw new IllegalStateException("No fragment at position " + position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // On rotation the FragmentManager handles rotation. Therefore getItem() isn't called.
            // Copy the fragments that the FragmentManager finds so that we can store them in
            // instance variables for later.
            final Fragment fragment =
                    (Fragment) super.instantiateItem(container, position);
            if (fragment instanceof GenerationFragment) {
                mGenerationFragment = (GenerationFragment) fragment;
            } else if (fragment instanceof BiographyFragment) {
                mBiographyFragment = (BiographyFragment) fragment;
            } else if (fragment instanceof PhotoFragment) {
                mPhotoFragment = (PhotoFragment) fragment;
            } else if (fragment instanceof TempleFragment ) {
                mTempleFragment = (TempleFragment) fragment;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return TAB_COUNT_DEFAULT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }
    }

    private int getRtlPosition(int position) {
        return position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    public void onResume() {
        super.onResume();
        mActionBar = getActivity().getActionBar();
        if (getUserVisibleHint()) {
            sendScreenViewForCurrentPosition();
        }

        // Fetch voicemail status to determine if we should show the voicemail tab.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View parentView = inflater.inflate(R.layout.list_fragment, container, false);
        Log.i("sysout","sfljdkf")
;        mViewPager = (ViewPager) parentView.findViewById(R.id.lists_pager);
        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(TAB_COUNT_WITH_VOICEMAIL);
        mViewPager.setOnPageChangeListener(this);
        showTab(TAB_INDEX_GENERATION);
        mTabTitles = new String[TAB_COUNT_WITH_VOICEMAIL];
        mTabTitles[TAB_INDEX_GENERATION] = getResources().getString(R.string.tab_generation);
        mTabTitles[TAB_INDEX_BIOGRAPHY] = getResources().getString(R.string.tab_biography);
        mTabTitles[TAB_INDEX_PHOTO] = getResources().getString(R.string.tab_photo);
        mTabTitles[TAB_INDEX_TEMPLE] = getResources().getString(R.string.tab_temple);

        mTabIcons = new int[TAB_COUNT_WITH_VOICEMAIL];
        mTabIcons[TAB_INDEX_GENERATION] = R.drawable.tab_generation;
        mTabIcons[TAB_INDEX_BIOGRAPHY] = R.drawable.tab_biography;
        mTabIcons[TAB_INDEX_PHOTO] = R.drawable.tab_photo;
        mTabIcons[TAB_INDEX_TEMPLE] = R.drawable.tab_temple;

        mViewPagerTabs = (ViewPagerTabs) parentView.findViewById(R.id.lists_pager_header);
        mViewPagerTabs.setTabIcons(mTabIcons);
        mViewPagerTabs.setViewPager(mViewPager);
        addOnPageChangeListener(mViewPagerTabs);

        mRemoveView = (RemoveView) parentView.findViewById(R.id.remove_view);
        mRemoveViewContent = parentView.findViewById(R.id.remove_view_content);

        return parentView;
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        if (!mOnPageChangeListeners.contains(onPageChangeListener)) {
            mOnPageChangeListeners.add(onPageChangeListener);
        }
    }

    /**
     * Shows the tab with the specified index. If the voicemail tab index is specified, but the
     * voicemail status hasn't been fetched, it will try to show the tab after the voicemail status
     * has been fetched.
     */
    public void showTab(int index) {
            mViewPager.setCurrentItem(getRtlPosition(index));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mTabIndex = getRtlPosition(position);

        final int count = mOnPageChangeListeners.size();
        for (int i = 0; i < count; i++) {
            mOnPageChangeListeners.get(i).onPageScrolled(position, positionOffset,
                    positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        mTabIndex = getRtlPosition(position);

        // Show the tab which has been selected instead.

        final int count = mOnPageChangeListeners.size();
        for (int i = 0; i < count; i++) {
            mOnPageChangeListeners.get(i).onPageSelected(position);
        }
        sendScreenViewForCurrentPosition();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        final int count = mOnPageChangeListeners.size();
        for (int i = 0; i < count; i++) {
            mOnPageChangeListeners.get(i).onPageScrollStateChanged(state);
        }
    }

    public void sendScreenViewForCurrentPosition() {
        if (!isResumed()) {
            return;
        }

        String fragmentName;
        switch (getCurrentTabIndex()) {
            case TAB_INDEX_GENERATION:
                fragmentName = GenerationFragment.class.getSimpleName();
                break;
            case TAB_INDEX_BIOGRAPHY:
                fragmentName = BiographyFragment.class.getSimpleName();
                break;
            case TAB_INDEX_PHOTO:
                fragmentName = PhotoFragment.class.getSimpleName();
                break;
            case TAB_INDEX_TEMPLE:
                fragmentName = TempleFragment.class.getSimpleName();
            default:
                return;
        }
        AnalyticsUtil.sendScreenView(fragmentName, getActivity(), null);
    }

    public int getCurrentTabIndex() {
        return mTabIndex;
    }

}
