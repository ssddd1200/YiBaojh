package com.yjs.yibaofw.dllinter;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface BargaingApplyV3 extends Library {

    BargaingApplyV3 INSTANCE = Native.load(System.getProperty("java.library.path")+"\\bin\\ybjy\\bargaingapplyv3_01059", BargaingApplyV3.class);
//    static {
//        Native.register(System.getProperty("java.library.path")+"\\bin\\ybjy\\bargaingapplyv3_01059");
//    }

    //初始化交易
//    public static native int f_UserBargaingInit(byte[] Data1, byte[] retMsg, byte[] Data2);
    int f_UserBargaingInit(byte[] Data1, byte[] retMsg, byte[] Data2);

    //交易接受
//    public static native int f_UserBargaingApply(int Code, double No, byte[] Data, byte[] retMsg, byte[] Data2);
    int f_UserBargaingApply(int Code, double No, byte[] Data, byte[] retMsg, byte[] Data2);

    //关闭交易
//    public static native int f_UserBargaingClose(byte[] Data1, byte[] retMag, byte[] Data2);
    int f_UserBargaingClose(byte[] Data1, byte[] retMag, byte[] Data2);
}
