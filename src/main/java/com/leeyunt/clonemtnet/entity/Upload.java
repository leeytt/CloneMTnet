package com.leeyunt.clonemtnet.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Upload implements Serializable{
	private static final long serialVersionUID = 1L;

	private String token;//七牛云token
	private String fileUrl;//文件外链
}
