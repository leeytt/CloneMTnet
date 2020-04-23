package com.leeyunt.clonemtnet.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class QiniuCloudUtil {
    // 设置七牛云账号的AK和SK
    private static final String ACCESS_KEY = "mhXPXU64_JbeLJqpUKX4QV_HgkIBNrIdJCB9Lxki";
    private static final String SECRET_KEY = "w93Onga1jPUNkCaf-C6zr4-WOruDaJnsjY3WC461";
    
    // 要上传的空间bucket
    private static final String BUCKET = "leeytt";
    
    //外链地址：这里要改成自己的域名
    private static final String DOMAIN = "http://qiniu.leeyunt.top/";
    
    
    /**
     * 获取七牛云Token
     * */
    public static String getupToken() {
	    //秘钥配置上传的凭证
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		StringMap putPolicy = new StringMap();
		putPolicy.put("returnBody", "{\"fileUrl\": \""+DOMAIN+"$(key)\"}");//上传成功后是返回这个json数据，fileUrl就是的地址
		long expireSeconds = 3600;//过期时间
		
		String upToken = auth.uploadToken(BUCKET, null, expireSeconds, putPolicy);
	    return upToken;
	}
    
    /**
     * @param  文件名
     * @return  外键地址
     * */
    //普通上传
    public static String uploadFile(){
        //文件的外链地址
        StringBuffer fileUrl  = new StringBuffer(DOMAIN);

        //构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone2());
        
        UploadManager uploadManager = new UploadManager(cfg);
        
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "/Users/leeyunt/Downloads/1864602-3d5d1de100e459b8.png";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        
        try {
            //上传文件
            Response response = uploadManager.put(localFilePath, key, getupToken());
            
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            fileUrl.append(putRet.key);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return fileUrl.toString();
    }
    
    //base64方式上传
    public static String uploadFileBase64(byte[] base64, String key) throws Exception{
        String file64 = Base64.encodeToString(base64, 0);
        Integer l = base64.length;
        String uploadUrl = "http://up-z2.qiniup.com/putb64/" + l + "/key/"+ UrlSafeBase64.encodeToString(key);      
        //非华南空间需要修改上传域名
        //上传文件
        RequestBody rb = RequestBody.create(null, file64);
        Request request = new Request.Builder().
        		url(uploadUrl)
                .addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + getupToken())
                .post(rb).build();
        //System.out.println(request.headers());
        OkHttpClient client = new OkHttpClient();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println(response);
        return DOMAIN + key;
    }
    
    //字节数组上传
    public static String uploadFileBytes(byte[] uploadBytes, String fileName) throws Exception{
    	//文件的外链地址
        StringBuffer fileUrl  = new StringBuffer(DOMAIN);

        //构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone2());
        
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = fileName;
        
        //秘钥配置上传的凭证
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(BUCKET);
        
        try {
        	//上传文件
            Response response = uploadManager.put(uploadBytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            fileUrl.append(putRet.key);
            //MyPutRet myPutRet=response.jsonToObject(MyPutRet.class);
            //System.out.println(myPutRet);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return fileUrl.toString();
    }

    public static void main(String[] args) {
    	
    }

}
