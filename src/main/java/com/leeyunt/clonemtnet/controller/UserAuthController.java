package com.leeyunt.clonemtnet.controller;

import com.leeyunt.clonemtnet.service.UserService;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import com.leeyunt.clonemtnet.utils.VerifyCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 授权
     * @param username
     * @param password
     * @return User
     */
    @PostMapping("/auth")
	@ApiOperation(value="授权",notes="根据用户名和密码获取token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", defaultValue = "admin", required = true),
            @ApiImplicitParam(name = "password", value = "密码", defaultValue = "123456", required = true)
    })
	public ResultUtil getByUsernamePasswordLogin(String username, String password){
        /*登录检查*/
        return userService.checkLogin(username, password);
	}

    /**
     * 获取当前登录用户的信息（角色、权限、菜单）
     */
    @PostMapping("/getUserInfo")
    @ApiOperation(value="获取用户信息",notes="登录成功后获取用户信息（角色、权限、菜单）")
    public ResultUtil getUserInfo() {
        return userService.getUserInfo();
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    @ApiOperation(value="登出",notes="登出系统")
    public ResultUtil logout() {
        return userService.logout();
    }


//    /**
//     * 测试通过权限认证
//     */
//    @GetMapping("/TestLoginPass")
//    public String test(String what){
//        log.info("通过权限认证");
//        return String.format("hello request test %s",what);
//    }

//    /**
//     * 获取token
//     */
//    @GetMapping("/getToken")
//    public String login(String username){
//        log.info("不需要权限校验..成功");
//        /*查询数据库*/
//        UserDetails userDetails = userDetailService.loadUserByUsername(username);
//        String token = jwtTokenUtil.generateToken(userDetails);
//        return String.format(String.format("%s %s", CommonConstant.TOKEN_PREFIX,token));
//    }

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
