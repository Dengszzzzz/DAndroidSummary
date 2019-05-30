package com.sz.dzh.dandroidsummary.utils.cryptoUtils;

import android.annotation.TargetApi;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.Log;

import java.security.Key;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class CryptoObjectHelper {
    // This can be key name you want. Should be unique for the app.
    static final String KEY_NAME = "com.createchance.android.sample.fingerprint_authentication_key";//密码名字
    // We always use this keystore on Android.
    static final String KEYSTORE_NAME = "AndroidKeyStore";//密码库


    // Should be no need to change these values.
    static final String KEY_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES;//加密算法
    static final String BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC;//模式
    static final String ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7;//填充模式
    static final String TRANSFORMATION = KEY_ALGORITHM + "/" + BLOCK_MODE + "/" + ENCRYPTION_PADDING;//转换规则
    final KeyStore _keystore;

    /**
     * 创建keystore
     * @throws Exception
     */
    public CryptoObjectHelper() throws Exception {
        _keystore = KeyStore.getInstance(KEYSTORE_NAME);
        _keystore.load(null);
        Log.e("密码库", _keystore.toString());

    }

    /**
     * 获取加密对象
     * @return
     * @throws Exception
     */
    public FingerprintManagerCompat.CryptoObject buildCryptoObject() throws Exception {
        Cipher cipher = createCipher(true);
        return new FingerprintManagerCompat.CryptoObject(cipher);
    }

    /**
     *
     * @param retry 是否重试
     * @return
     * @throws Exception
     * 密码生成
     * 递归实现
     */
    @TargetApi(Build.VERSION_CODES.M)
    Cipher createCipher(boolean retry) throws Exception {
        Key key = GetKey();
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        try {
            cipher.init(Cipher.ENCRYPT_MODE | Cipher.DECRYPT_MODE, key);
        } catch (KeyPermanentlyInvalidatedException e) {
            _keystore.deleteEntry(KEY_NAME);//删除获取的码，保留生成的密码
            if (retry) {
                createCipher(false);
            } else {
                throw new Exception("Could not create the cipher for fingerprint authentication.", e);
            }
        }
        Log.e("生成码", cipher.toString());
        return cipher;
    }

    /**
     * 获取秘钥
     * @return 秘钥
     * @throws Exception
     */
    Key GetKey() throws Exception {
        Key secretKey;
        if (!_keystore.isKeyEntry(KEY_NAME)) {
            CreateKey();
        }

        secretKey = _keystore.getKey(KEY_NAME, null);
        Log.e("指纹码", secretKey.toString());
        return secretKey;
    }

    /**
     * 获取秘钥生成器，用于生成秘钥
     * @throws Exception
     */
    @TargetApi(Build.VERSION_CODES.M)
    void CreateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM, KEYSTORE_NAME);
        KeyGenParameterSpec keyGenSpec =
                new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(BLOCK_MODE)
                        .setEncryptionPaddings(ENCRYPTION_PADDING)
                        .setUserAuthenticationRequired(true)
                        .build();
        keyGen.init(keyGenSpec);
        keyGen.generateKey();
        Log.e("秘钥", keyGen.toString());
    }
}
