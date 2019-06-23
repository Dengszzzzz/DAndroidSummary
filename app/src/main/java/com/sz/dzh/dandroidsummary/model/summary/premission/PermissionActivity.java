package com.sz.dzh.dandroidsummary.model.summary.premission;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.socks.library.KLog;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;
import com.sz.dzh.dandroidsummary.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengzh on 2019/6/22
 * 权限管理
 * 1.区分普通权限和危险权限
 * 危险权限是涉及到用户隐私的权限，分9组：分别是联系人、通话、日历、相机、传感器、定位、存储、音视频/录音、短信权限。
 * <p>
 * 2.动态权限请求与处理的流程
 * <p>
 * 3.常见问题分析
 * 4.注意点：
 * 1）如果同一组的任何一个权限被授权了，其他权限也自动被授权。例如，一旦WRITE_EXTERNAL_STORAGE被授权了，app也有READ_EXTERNAL_STORAGE权限了。
 * 2）申请某一个权限的时候系统弹出的Dialog是对整个权限组的说明，而不是单个权限。例如我申请READ_EXTERNAL_STORAGE，系统会提示"允许xxx访问设备上的照片、媒体内容和文件吗？
 * <p>
 * 参考：https://www.jianshu.com/p/8e37e9cf20a5
 */
public class PermissionActivity extends BaseActivity {

    @BindView(R.id.tv_desc)
    TextView tvDesc;

    /**
     * 2.动态权限请求与处理的流程
     * 1）检测权限
     * ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
     * 2）在Activity中请求权限
     * ActivityCompat.requestPermissions(activity, permissions, resultCode);
     * 3）APP弹出系统的授权提示框，让用户授权（用户可授权、也可取消、也可选择不再提醒）
     * 4）在Activity的onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)方法中，
     * 返回用户授权的信息（请求码，请求的权限集合、每个权限授权的情况集合）
     * a) 如果已都已授权，可以继续操作，也可以是什么也不做，等待用户重新操作；
     * b) 如果未授权，引导用户去授权；
     */


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_permission);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("权限管理");
    }

    @OnClick(R.id.clickBt)
    public void onViewClicked() {
        applyPremission();
    }

    /**
     * 权限申请可以考虑放在首页申请
     */
    private void applyPremission() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        PermissionsUtils.getInstance().checkPermissions(this, permissions, new PermissionsUtils.IPermissionsResult() {
            @Override
            public void accept() {
                //点了允许，这个是false
                KLog.e("Permission","允许权限--shouldShowRequestPermissionRationale--" + ActivityCompat.shouldShowRequestPermissionRationale(PermissionActivity.this, Manifest.permission.CAMERA));
                ToastUtils.showToast("您已允许权限");
            }

            @Override
            public void forbit(ArrayList<String> disList) {
                for (String permission : disList) {
                    KLog.e("Permission","拒绝权限--shouldShowRequestPermissionRationale--" + ActivityCompat.shouldShowRequestPermissionRationale(PermissionActivity.this, permission));
                    //直接被拒绝，则返回true，如果点了不再询问拒绝,则返回false
                    //这个方法的理解是，是否需要告诉用户请求权限的原因，那么当用户拒绝的时候，最好给用户一个提示。
                    //而不再询问拒绝or允许权限，则没必要给用户提示了。
                    if (ActivityCompat.shouldShowRequestPermissionRationale(PermissionActivity.this, permission)) {
                        ToastUtils.showToast("您已拒绝储存 or 相机权限，为了该功能正常使用，请允许");
                    }else{
                        new AlertDialog.Builder(PermissionActivity.this)
                                .setMessage("如果要开启该功能，请您去系统设置打开该权限")
                                .show();
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtils.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
