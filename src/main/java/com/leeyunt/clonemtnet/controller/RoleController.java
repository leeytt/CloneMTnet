package com.leeyunt.clonemtnet.controller;


import com.leeyunt.clonemtnet.service.RoleService;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色管理 前端控制器
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    /**
     * 获取所有角色
     */
    @GetMapping("getAll")
    @ApiOperation(value="获取所有角色",notes="得到所有角色数据")
    public ResultUtil loadAll(){
        if (0==roleService.count()) {
            return ResultUtil.ofFailMsg("未查询到结果");
        }
        return ResultUtil.ofSuccess(roleService.list());
    }
}
