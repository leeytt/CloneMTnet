package com.leeyunt.clonemtnet.service.impl;

import com.leeyunt.clonemtnet.entity.Upload;
import com.leeyunt.clonemtnet.service.UploadService;
import com.leeyunt.clonemtnet.utils.QiniuCloudUtil;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Service
public class UploadImpl implements UploadService {
	
	//获取七牛云token
	@Override
	public ResultUtil getUpToken() {
		String upToken = QiniuCloudUtil.getupToken();
	    
	    Upload upload = new Upload();
	    upload.setToken(upToken);
	    return ResultUtil.ofSuccess(upload);
	}
	
	//上传文件到七牛云
	@Override
	public ResultUtil uploadFileQiniu(MultipartFile file, HttpServletRequest request) throws Exception {
		//字节数组方式上传文件
		byte[] uploadBytes = file.getBytes();
		String fileName = file.getOriginalFilename();//文件名
        String fileUrl = QiniuCloudUtil.uploadFileBytes(uploadBytes, fileName);//文件上传成功之后返回的URL
        
        //普通方式上传文件
        //String fikeUrl = QiniuCloudUtil.uploadFile();
        System.out.println(fileUrl);
        
        Upload upload = new Upload();
	    upload.setFileUrl(fileUrl);
	    return ResultUtil.ofSuccess(upload);
	}
	

}
