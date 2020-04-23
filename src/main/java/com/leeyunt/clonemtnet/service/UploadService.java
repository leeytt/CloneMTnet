package com.leeyunt.clonemtnet.service;

import com.leeyunt.clonemtnet.utils.ResultUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface UploadService {
	//获取七牛云token
	ResultUtil getUpToken();
	//上传文件到七牛云
	ResultUtil uploadFileQiniu(MultipartFile file, HttpServletRequest request) throws Exception;
}
