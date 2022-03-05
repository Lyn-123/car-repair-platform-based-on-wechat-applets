package com.platform.vehicle_repair.util;

import com.google.gson.Gson;
import com.platform.vehicle_repair.entity.WxSession;
import com.platform.vehicle_repair.enums.ResultEnum;
import com.platform.vehicle_repair.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class WxUtil {

    private final static String AppId = "wx1067327b1413bdeb";
    private final static String AppSecret="5368b284bdea841586c0ad620690a330";

    //后台获取微信用户openid
    public static WxSession getWxSession(String code){
        String url = "https://api.weixin.qq.com/sns/jscode2session"
                + "?appid=" + AppId
                + "&secret=" + AppSecret
                + "&js_code=" + code
                + "&grant_type=authorization_code"
                + "&connect_redirect=1";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url,String.class);
        WxSession wx = new WxSession();
        try{
            Gson gson = new Gson();
            wx = gson.fromJson(response, WxSession.class);
        }catch (Exception e){
            log.error("【对象转换】错误，string={}",response);
            throw new ServiceException(ResultEnum.PARAM_ERROR);
        }
        return wx;
    }
}
