package com.leeyunt.clonemtnet.controller;

import com.leeyunt.clonemtnet.service.UploadService;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 文件上传 前端控制器
 * </p>
 *
 * @author leeyunt
 * @since 2020-04-23
 */
@Api(tags = "上传文件")
@RestController
@RequestMapping("/upload")
public class UploadController {
	@Resource
	private UploadService uploadService;
	
	//获取七牛云token
	@GetMapping("/getQiniuToken")
	@ResponseBody
	@ApiOperation(value="获取七牛云token",notes="获取七牛云token")
	public ResultUtil getUpToken(
			@ApiParam(value = "空间名字", required = false) @RequestParam(required = false) String bucket) {
		return uploadService.getUpToken();
	}
	
	//上传文件到七牛云
	@PostMapping("/uploadFileQiniu")
	@ResponseBody
	@ApiOperation(value="上传文件到七牛云",notes="上传文件到七牛云")
	public ResultUtil uploadQiniu(
			@ApiParam(value = "file", required = false) @RequestParam(required = false) MultipartFile file,
			HttpServletRequest request)throws Exception {
		if (file.isEmpty()) {
			return ResultUtil.ofFailMsg("文件为空，请重新上传");
		}
		return uploadService.uploadFileQiniu(file, request);
	}
}