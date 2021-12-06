package com.example.studyplanner.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.studyplanner.AssignmentFragment;
import com.example.studyplanner.ExamFragment;
import com.example.studyplanner.LectureFragment;
import com.example.studyplanner.R;
import com.example.studyplanner.StudyPlanFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        Fragment retFrag = null;

        System.out.println("section changed");
        switch(position)
        {
            case 0:
                retFrag = new StudyPlanFragment();
                break;
            case 1:
                retFrag = new AssignmentFragment();
                break;
            case 2:
                retFrag = new ExamFragment();
                break;
            case 3:
                retFrag = new LectureFragment();
                break;

        }

        return retFrag;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 4;
    }
}