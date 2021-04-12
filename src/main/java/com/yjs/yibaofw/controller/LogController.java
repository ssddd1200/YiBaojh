package com.yjs.yibaofw.controller;

import com.yjs.yibaofw.entity.DBModelPO;
import com.yjs.yibaofw.entity.LogPagePO;
import com.yjs.yibaofw.entity.ReturnPageVO;
import com.yjs.yibaofw.entity.ReturnVO;
import com.yjs.yibaofw.helper.SQLiteConfigHelper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/log")
public class LogController {

    @PostMapping(value = "/getsLogs", produces = {"application/json;charset=UTF-8"})
    public ReturnPageVO getsLogs(@RequestBody LogPagePO logPagePO){
        ReturnPageVO pageVO = null;
        try {
            pageVO = SQLiteConfigHelper.getsLogInfo(logPagePO.getRiqi1(), logPagePO.getRiqi2(), logPagePO.getPage(), logPagePO.getRows());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageVO;
    }

    @GetMapping(value = "/getLog", produces = {"application/json;charset=UTF-8"})
    public ReturnVO getLog(@RequestParam int id){
        ReturnVO returnVO = new ReturnVO();
        try {
            DBModelPO modelPO = SQLiteConfigHelper.getDBModelPO(id);
            if (modelPO != null){
                returnVO.setAppcode("0");
                returnVO.setDatabuffer("成功");
                returnVO.setResultlist(modelPO);
            }else{
                returnVO.setAppcode("-1");
                returnVO.setDatabuffer("失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnVO;
    }
}
