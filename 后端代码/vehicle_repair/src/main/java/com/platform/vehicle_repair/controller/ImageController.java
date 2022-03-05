package com.platform.vehicle_repair.controller;

import com.platform.vehicle_repair.VO.ResultVO;
import com.platform.vehicle_repair.enums.ResultEnum;
import com.platform.vehicle_repair.util.ResultVOUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    @PostMapping("/update")
    public ResultVO update(@RequestParam("file") MultipartFile picFile){
        //判断文件是否存在
        if(picFile.isEmpty()){
            return ResultVOUtil.error(ResultEnum.FILE_NOT_EXIT.getCode(),
                    ResultEnum.FILE_NOT_EXIT.getMessage());
        }
        if(picFile.getSize()>=2097152){
            return ResultVOUtil.error(ResultEnum.SIZE_TOO_BIG.getCode(),
                    ResultEnum.SIZE_TOO_BIG.getMessage());
        }
        //设定上传地址和新文件名
        String fileName = System.currentTimeMillis()+picFile.getOriginalFilename().substring(picFile.getOriginalFilename().length()-4);
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"images";
        //user.dir指用户的当前工作目录 System.getProperty("...")获取系统属性 file.separator-----文件分隔符
        //判断地址中的文件夹是否存在，不存在生成该地址
        File fileDir = new File(filePath);
        if(!fileDir.exists()){
            fileDir.mkdir();
        }
        //创建新文件
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        String url = "/image/address/"+fileName;
        try {
            //复制文件
            picFile.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.FAIL_TO_TRANSFER.getCode(),
                    ResultEnum.FAIL_TO_TRANSFER.getMessage());
        }finally {
            return ResultVOUtil.success(url);
        }
    }
}
