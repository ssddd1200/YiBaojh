package com.yjs.yibaofw.controller;

import com.alibaba.fastjson.JSON;
import com.yjs.yibaofw.dllinter.BargaingApplyV3;
import com.yjs.yibaofw.entity.*;
import com.yjs.yibaofw.helper.SQLiteConfigHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@RestController
@RequestMapping(value = "/yibaofw")
public class YibaofwController {

    Logger log = LoggerFactory.getLogger(YibaofwController.class);

    @Autowired
    public YibaofwController(){

        Runtime.getRuntime().loadLibrary("ybjy/dcrf32");
        Runtime.getRuntime().loadLibrary("ybjy/mt_32");
        Runtime.getRuntime().loadLibrary("ybjy/libghttp");
        Runtime.getRuntime().loadLibrary("ybjy/ssse32");

        Runtime.getRuntime().loadLibrary("ybjy/iccinter01059");
        Runtime.getRuntime().loadLibrary("ybjy/HttpInter_ZJZS_SB");
        Runtime.getRuntime().loadLibrary("ybjy/ICCDevInter_ZS_SB");
        Runtime.getRuntime().loadLibrary("ybjy/iccinter_zs_sb");

        Runtime.getRuntime().loadLibrary("ybjy/sscarddriver");
    }

    // 80号交易获取体检人员信息
    @PostMapping(value = "/getYibaory/{yiyuanbh}", produces = {"application/json;charset=UTF-8"})
    public String getYibaory(@RequestBody TijianryhqPO tijianryhqPO, @PathVariable String yiyuanbh, HttpServletRequest request){
        Map<String, String> resMap = new HashMap<>();
        boolean initStatus = this.initYBSource(yiyuanbh);
        if (initStatus){
            String pushData = buildInputStr(TijianryhqPO.class, tijianryhqPO);
            byte[] data1 = pushData.getBytes();
            byte[] retMsg = new byte[1024];
            byte[] data2 = yiyuanbh.getBytes();
            int code = BargaingApplyV3.INSTANCE.f_UserBargaingApply(80, 0.0, data1, retMsg, data2);
            resMap.put("code", String.valueOf(code));
            String resultStr = new String(retMsg).trim();
            resMap.put("outmsg", new String(retMsg).trim());
            String[] d = resultStr.split("~");
            log.info("Return Value:"+resultStr);
            if (code >= 0){
                resMap.put("errmsg", d[1]);
                resMap.put("kahao", d[2]);
                resMap.put("tongchouq", d[3]);
                resMap.put("xingming", d[4]);
                resMap.put("xingbie", d[5]);
                resMap.put("shehuibzh", d[6]);
                resMap.put("tijiandah", d[7]);
                resMap.put("danweimc", d[8]);
                resMap.put("tijiannf", d[9]);
                resMap.put("tijiankssj", d[10]);
                resMap.put("tijianjssj", d[11]);
                resMap.put("jiatingzz", d[12]);
                resMap.put("lianxidh", d[13]);
                resMap.put("xianzhong", d[14]);

            }else{
                resMap.put("errmsg", d[1]);
            }
            DBModelPO dbModelPO = new DBModelPO();
            dbModelPO.setPostIP(getIPAddr(request));
            dbModelPO.setPostJson(pushData);
            dbModelPO.setResult(resultStr);
            dbModelPO.setJiaoyih("80");
            dbModelPO.setJigousbh(yiyuanbh);
            try {
                SQLiteConfigHelper.saveDBModel(dbModelPO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            resMap.put("code", "-1");
            resMap.put("errMsg","初始化失败，请查看日志");
        }
        this.closeYBSource(yiyuanbh);
        return JSON.toJSONString(resMap);
    }
    // 81号交易体检请求
    @PostMapping(value = "/getYibaojyqq/{yiyuanbh}", produces = {"application/json;charset=UTF-8"})
    public String getYibaojyqq(@PathVariable String yiyuanbh, @RequestBody TijianjyqqPO tijianjyqqPO, HttpServletRequest request){
        Map<String, String> resMap = new HashMap<>();
        boolean initStatus = this.initYBSource(yiyuanbh);
        if (initStatus){
            String pushData = buildInputStr(TijianjyqqPO.class, tijianjyqqPO);
            byte[] data1 = pushData.getBytes();
            byte[] retMsg = new byte[1024];
            byte[] data2 = yiyuanbh.getBytes();
            int code = BargaingApplyV3.INSTANCE.f_UserBargaingApply(81, 0.0, data1, retMsg, data2);
            resMap.put("code", String.valueOf(code));
            String resultStr = new String(retMsg).trim();
            resMap.put("outmsg", new String(retMsg).trim());
            String[] d = resultStr.split("~");
            log.info("Return Value:"+resultStr);
            if (code >= 0){
                resMap.put("errmsg", d[1]);
                resMap.put("tijianlsh", d[2]);
                resMap.put("tijianjysj", d[3]);
            }else{
                resMap.put("errmsg", d[1]);
            }
            DBModelPO dbModelPO = new DBModelPO();
            dbModelPO.setPostIP(getIPAddr(request));
            dbModelPO.setPostJson(pushData);
            dbModelPO.setResult(resultStr);
            dbModelPO.setJiaoyih("81");
            dbModelPO.setJigousbh(yiyuanbh);
            try {
                SQLiteConfigHelper.saveDBModel(dbModelPO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            resMap.put("code", "-1");
            resMap.put("errMsg","初始化失败，请查看日志");
        }
        this.closeYBSource(yiyuanbh);
        return JSON.toJSONString(resMap);
    }

    // 82号交易体检确认
    @PostMapping(value = "/getYibaojyqr/{yiyuanbh}", produces = {"application/json;charset=UTF-8"})
    public String getYibaojyqr(@PathVariable String yiyuanbh, @RequestBody TijianjyqrPO tijianjyqrPO, HttpServletRequest request){
        Map<String, String> resMap = new HashMap<>();
        boolean initStatus = this.initYBSource(yiyuanbh);
        if (initStatus){
            String pushData = buildInputStr(TijianjyqrPO.class, tijianjyqrPO);
            System.out.println(pushData);
            System.out.println(pushData.getBytes().length);
            byte[] data1 = pushData.getBytes();
            byte[] retMsg = new byte[1024];
            byte[] data2 = yiyuanbh.getBytes();
            int code = BargaingApplyV3.INSTANCE.f_UserBargaingApply(82, 0.0, data1, retMsg, data2);
            resMap.put("code", String.valueOf(code));
            String resultStr = new String(retMsg).trim();
            resMap.put("outmsg", new String(retMsg).trim());
            String[] d = resultStr.split("~");
            log.info("Return Value:"+resultStr);
            if (code >= 0){
                resMap.put("errmsg", d[1]);
                resMap.put("tijianqrsj", d[2]);
            }else{
                resMap.put("errmsg", d[1]);
            }
            DBModelPO dbModelPO = new DBModelPO();
            dbModelPO.setPostIP(getIPAddr(request));
            dbModelPO.setPostJson(pushData);
            dbModelPO.setResult(resultStr);
            dbModelPO.setJiaoyih("82");
            dbModelPO.setJigousbh(yiyuanbh);
            try {
                SQLiteConfigHelper.saveDBModel(dbModelPO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            resMap.put("code", "-1");
            resMap.put("errMsg","初始化失败，请查看日志");
        }
        this.closeYBSource(yiyuanbh);
        return JSON.toJSONString(resMap);
    }
    // 83号交易体检作废
    @PostMapping(value = "/getYibaojyzf/{yiyuanbh}", produces = {"application/json;charset=UTF-8"})
    public String getYibaojyzf(@PathVariable String yiyuanbh, @RequestBody TijianjyzfPO tijianjyzfPO, HttpServletRequest request){
        Map<String, String> resMap = new HashMap<>();
        boolean initStatus = this.initYBSource(yiyuanbh);
        if (initStatus){
            String pushData = buildInputStr(TijianjyzfPO.class, tijianjyzfPO);
            byte[] data1 = pushData.getBytes();
            byte[] retMsg = new byte[1024];
            byte[] data2 = yiyuanbh.getBytes();
            int code = BargaingApplyV3.INSTANCE.f_UserBargaingApply(83, 0.0, data1, retMsg, data2);
            resMap.put("code", String.valueOf(code));
            String resultStr = new String(retMsg).trim();
            resMap.put("outmsg", new String(retMsg).trim());
            String[] d = resultStr.split("~");
            log.info("Return Value:"+resultStr);
            if (code >= 0){
                resMap.put("errmsg", d[1]);
                resMap.put("kahao", d[2]);
                resMap.put("tongchouq", d[3]);
                resMap.put("xingming", d[4]);
                resMap.put("xingbie", d[5]);
                resMap.put("shehuibzh", d[6]);
                resMap.put("tijiandah", d[7]);
                resMap.put("danweimc", d[8]);
                resMap.put("tijiannf", d[9]);
                resMap.put("zuofeisj", d[10]);

            }else{
                resMap.put("errmsg", d[1]);
            }
            DBModelPO dbModelPO = new DBModelPO();
            dbModelPO.setPostIP(getIPAddr(request));
            dbModelPO.setPostJson(pushData);
            dbModelPO.setResult(resultStr);
            dbModelPO.setJiaoyih("83");
            dbModelPO.setJigousbh(yiyuanbh);
            try {
                SQLiteConfigHelper.saveDBModel(dbModelPO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            resMap.put("code", "-1");
            resMap.put("errMsg","初始化失败，请查看日志");
        }
        this.closeYBSource(yiyuanbh);
        return JSON.toJSONString(resMap);
    }


    public boolean initYBSource(String yiyuanbh){
        byte[] data1 = new byte[1024];
        byte[] retMsg = new byte[1024];
        byte[] data2 = yiyuanbh.getBytes();

        int code = BargaingApplyV3.INSTANCE.f_UserBargaingInit(data1, retMsg, data2);
        log.info("code:"+String.valueOf(code)+",医保初始化结果：" + new String(retMsg).trim());
        if (code >= 0){
            return true;
        }

        return false;
    }

    public boolean closeYBSource(String yiyuanbh){
        byte[] data1 = new byte[1024];
        byte[] retMsg = new byte[1024];
        byte[] data2 = yiyuanbh.getBytes();

        int code = BargaingApplyV3.INSTANCE.f_UserBargaingClose(data1, retMsg, data2);
        log.info("code:" + String.valueOf(code)+",医保关闭结果：" + new String(retMsg).trim());
        if (code >= 0){
            return true;
        }
        return false;
    }

    private <T> String buildInputStr(Class<T> clz, T o){
        StringBuffer sb = new StringBuffer();
        sb.append("$$");
        Field[] fs = clz.getDeclaredFields();
        for (int i = 0; i < fs.length;i++){
            fs[i].setAccessible(true);
            if(!fs[i].getGenericType().getTypeName().equals("java.util.List")){
                try {
                    sb.append(fs[i].get(o));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (i != fs.length - 1){
                    sb.append("~");
                }

            }
        }
        sb.append("$$");
        return sb.toString();
    }

    private Map regResult(byte[] result, String[] objKey, String... keys){

        Map retMap = new LinkedHashMap();
        String retStr = new String(result).trim();
        retStr = retStr.replace("$$","");
        String[] sp_str = retStr.split("~");
        for (int i=0;i<sp_str.length;i++){
            if (sp_str[i].indexOf("^")>=0){
                List list = new ArrayList();
                String[] objs = sp_str[i].split("^");
                for (int j=0;j<objs.length;j++){
                    if (objs[i] != null){
                        Map<String, String> object = new LinkedHashMap<>();
                        String[] obj = objs[i].split("|");
                        for (int k =0;k<obj.length;k++){
                            object.put(objKey[k], obj[k]);
                        }
                        list.add(obj);
                    }
                }
                retMap.put(keys[i], list);
            }else if(sp_str[i].indexOf("|")>=0){
                Map<String, String> object = new LinkedHashMap<>();
                String[] o = sp_str[i].split("|");
                for (int j=0;j<o.length;j++){
                    object.put(objKey[j], o[j]);
                }
                retMap.put(keys[i], object);
            }else{
                retMap.put(keys[i], sp_str[i]);
            }
        }
        return retMap;
    }

    private static String getIPAddr(HttpServletRequest request){
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = request.getRemoteAddr();
            String localIP = "127.0.0.1";
            String localIPV6 = "0:0:0:0:0:0:0:1";
            if (ipAddress.equals(localIP) || ipAddress.equals(localIPV6)){
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }

        String ipSeparate = ",";
        int ipLength = 15;
        if (ipAddress != null && ipAddress.length() > ipLength) {
            if (ipAddress.indexOf(ipSeparate) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(ipSeparate));
            }
        }
        return ipAddress;
    }
}
