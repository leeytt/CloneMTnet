package com.leeyunt.clonemtnet.controller;

import com.leeyunt.clonemtnet.constant.CommonConstant;
import com.leeyunt.clonemtnet.jwt.JwtTokenUtil;
import com.leeyunt.clonemtnet.security.UserDetailServiceImpl;
import com.leeyunt.clonemtnet.service.UserService;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import com.leeyunt.clonemtnet.utils.VerifyCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <p>
 * 用户授权 前端控制器
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-09
 */
@Slf4j
@Api(tags = "用户授权")
@RestController
@RequestMapping("/userAuth")
public class UserAuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 测试token
     */
    @GetMapping("/testToken")
    public String test(String what){
        log.info("通过权限认证");
        return String.format("hello request test %s",what);
    }

    @GetMapping("/login")
    public String loginTest(String username){
        log.info("不需要权限校验..成功");
        /*查询数据库*/
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        String token = jwtTokenUtil.generateToken(userDetails);
        return String.format(String.format("%s %s", CommonConstant.TOKEN_PREFIX,token));
    }


    /**
     * 登录
     * @param username
     * @param password
     * @return User
     */
    @PostMapping("/dologin")
	@ApiOperation(value="登录",notes="根据用户名和密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", defaultValue = "admin", required = true),
            @ApiImplicitParam(name = "password", value = "密码", defaultValue = "123456", required = true)
    })
	public ResultUtil getByUsernamePasswordLogin(@RequestParam("username") String username, @RequestParam("password") String password){
        /*登录检查*/
        ResultUtil resultUtil = userService.checkLogin(username, password);
		return resultUtil;
	}

    /**
     * 获取验证码
     */
    @GetMapping("/verCode")
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    public void getVercode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        VerifyCodeUtil vc = new VerifyCodeUtil();
        BufferedImage image = vc.getImage();
        String text = vc.getText();
        HttpSession session = req.getSession();
        session.setAttribute("index_code", text);
        VerifyCodeUtil.output(image, resp.getOutputStream());
    }

}
