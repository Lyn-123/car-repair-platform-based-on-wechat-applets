package com.platform.vehicle_repair.util;

import com.platform.vehicle_repair.VO.RepairVO;
import com.platform.vehicle_repair.VO.ResultVO;

import java.util.HashMap;
import java.util.Map;

public class ResultVOUtil {

    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static ResultVO error(Integer code,String msg,Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO error(Integer code,String msg,String errorMsg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);

        Map error = new HashMap();
        error.put("errorMsg",errorMsg);
        resultVO.setData(error);

        return resultVO;
    }

}
