package com.sz.dzh.dandroidsummary.model.specialFunc.fingerprint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.view.View;

import com.socks.library.KLog;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;
import com.sz.dzh.dandroidsummary.utils.ToastUtils;
import com.sz.dzh.dandroidsummary.utils.cryptoUtils.CryptoObjectHelper;

import javax.crypto.Cipher;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by administrator on 2018/7/24.
 * 指纹识别
 *
 */

public class FingerPrintActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_finger_print);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("指纹识别");

    }


    @OnClick({R.id.openBt, R.id.cancelBt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.openBt:  //开启
                try {
                    CryptoObjectHelper cryptoObjectHelper = new CryptoObjectHelper();
                    handleFingerPrint(cryptoObjectHelper.buildCryptoObject());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.cancelBt: //取消
                FingerprintUtils.cancel();
                break;
        }
    }

    private void handleFingerPrint(FingerprintManagerCompat.CryptoObject crypt){
        //目前尚未对加密做处理，未解
        FingerprintUtils.callFingerPrint(crypt,new FingerprintUtils.OnCallBackListenr() {
            @Override
            public void onSupportFailed() {
                KLog.d("当前设备不支持指纹识别");
            }

            @Override
            public void onInsecurity() {
                KLog.d("没有开启锁屏密码");
            }

            @Override
            public void onEnrollFailed() {
                KLog.d("没有指纹录入");
            }

            @Override
            public void onAuthenticationStart() {
                KLog.d("开启识别指纹");
            }

            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                KLog.d(errString.toString());
                ToastUtils.showToast(errString.toString());
            }

            @Override
            public void onAuthenticationFailed() {
                KLog.d("指纹验证失败");
                ToastUtils.showToast("指纹验证失败");
            }

            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                KLog.d(helpString.toString());
                ToastUtils.showToast(helpString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                Cipher cipher = result.getCryptoObject().getCipher();
                cipher.toString();

             /*   Signature signature = result.getCryptoObject().getSignature();
                try {
                    byte[] sigBytes = signature.sign();
                    KLog.d("得到加密数据：" + sigBytes.toString());
                } catch (SignatureException e) {
                    e.printStackTrace();
                }*/

                //无法获取指纹信息，只能判断当前指纹是否是手机已记录的指纹
                KLog.d(result.toString());
                ToastUtils.showToast("指纹识别正确");
            }
        });
    }
}
