package com.sz.dzh.dandroidsummary.model;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;
import com.sz.dzh.dandroidsummary.fragment.ProblemsFragment;
import com.sz.dzh.dandroidsummary.fragment.SpecialFuncFragment;
import com.sz.dzh.dandroidsummary.fragment.SummaryFragment;
import com.sz.dzh.dandroidsummary.fragment.ViewDetailsFragment;
import com.sz.dzh.dandroidsummary.utils.AppManager;
import com.sz.dzh.dandroidsummary.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{


    @BindView(R.id.fr_container)
    FrameLayout mFrContainer;
    @BindView(R.id.rg_view_details)
    RadioButton mRgViewDetails;
    @BindView(R.id.rg_special_functions)
    RadioButton mRgSpecialFunctions;
    @BindView(R.id.rg_problems)
    RadioButton mRgProblems;
    @BindView(R.id.rg_summary)
    RadioButton mRgSummary;
    @BindView(R.id.tabs_rg)
    RadioGroup mTabsRg;
    @BindView(R.id.iv_center)
    ImageView mIvCenter;

    private Fragment mViewFragment,mSpecialFragment,mProblemFragment,mSummaryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //设置Group监听，也就是下方的RadioButton的状态监听
        mTabsRg.setOnCheckedChangeListener(this);
        //初始化默认第一个RadioButton为选中状态
        mRgViewDetails.setChecked(true);
        mIvCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast("O(∩_∩)O123456789~");
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch (checkedId) {
            case R.id.rg_view_details:
                if (mViewFragment == null) {
                    mViewFragment = ViewDetailsFragment.newInstance();
                    transaction.add(R.id.fr_container, mViewFragment);
                } else {
                    transaction.show(mViewFragment);
                }
                break;
            case R.id.rg_special_functions:
                if (mSpecialFragment == null) {
                    mSpecialFragment = SpecialFuncFragment.newInstance();
                    transaction.add(R.id.fr_container, mSpecialFragment);
                } else {
                    transaction.show(mSpecialFragment);
                }
                break;
            case R.id.rg_problems:
                if (mProblemFragment == null) {
                    mProblemFragment = ProblemsFragment.newInstance();
                    transaction.add(R.id.fr_container, mProblemFragment);
                } else {
                    transaction.show(mProblemFragment);
                }
                break;
            case R.id.rg_summary:
                if (mSummaryFragment == null) {
                    mSummaryFragment = SummaryFragment.newInstance();
                    transaction.add(R.id.fr_container, mSummaryFragment);
                } else {
                    transaction.show(mSummaryFragment);
                }
                break;
        }
        transaction.commit();
    }

    public void hideAllFragment(FragmentTransaction transaction) {
        if (mViewFragment != null) {
            transaction.hide(mViewFragment);
        }
        if (mSpecialFragment != null) {
            transaction.hide(mSpecialFragment);
        }
        if (mProblemFragment != null) {
            transaction.hide(mProblemFragment);
        }
        if (mSummaryFragment != null) {
            transaction.hide(mSummaryFragment);
        }
    }


    private long firstTime = 0;
    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Toast.makeText(this, "再点一次退出程序", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else {
            AppManager.getAppManager().AppExit(this);
        }
    }
}
