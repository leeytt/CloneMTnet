package com.leeyunt.clonemtnet.controller;


import com.leeyunt.clonemtnet.service.RoleService;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    /**
     * 动态查询
     */
    @PostMapping("/selectRole")
    @ApiOperation(value = "动态查询", notes = "通过各项参数查询数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色ID"),
            @ApiImplicitParam(name = "roleName", value = "角色名"),
            @ApiImplicitParam(name = "description", value = "角色描述"),
            @ApiImplicitParam(name = "status", value = "状态"),
            @ApiImplicitParam(name = "orderByCase", value = "排序", defaultValue = "create_time"),
            @ApiImplicitParam(name = "desc", value = "排序方式", defaultValue = "1"),
            @ApiImplicitParam(name = "pageNow", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", defaultValue = "10")})
    public ResultUtil selectUser(Integer id, String roleName, String description, Boolean status, String orderByCase, Boolean desc, Integer pageNow, Integer pageSize) {
        if (null != orderByCase) {
            if (desc == null) {
                desc = false;
            }
            orderByCase += desc ? " desc" : " asc";
        } else {
            orderByCase = "create_time";
            if (desc == null) {
                desc = false;
            }
            orderByCase += desc ? " desc" : " asc";
        }
        return roleService.selectRole(id, roleName, description, status, orderByCase, desc, pageNow, pageSize);
    }

    /**
     * 添加角色
     */
    @PostMapping("/addRole")
    @ApiOperation(value = "添加角色", notes = "添加记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名", defaultValue = "普通用户01", required = true),
            @ApiImplicitParam(name = "description", value = "角色描述", defaultValue = "我是普通用户01"),
            @ApiImplicitParam(name = "status", value = "状态")})
    public ResultUtil addRole(String roleName, String description, Boolean status) {
        if (null == roleName) {
            return ResultUtil.ofFailMsg("角色名不能为空");
        }
        return roleService.addRole(roleName, description, status);
    }

    /**
     * 动态更新角色
     */
    @PutMapping("/updateRole")
    @ApiOperation(value = "更新角色", notes = "根据id动态更新记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色ID", required = true),
            @ApiImplicitParam(name = "roleName", value = "角色名"),
            @ApiImplicitParam(name = "description", value = "角色描述"),
            @ApiImplicitParam(name = "status", value = "状态")})
    public ResultUtil updateRoleById(Integer id, String roleName, String description, Boolean status) {
        if (null == id) {
            return ResultUtil.ofFailMsg("角色id不能为空");
        }
        return roleService.updateRoleById(id, roleName, description, status);
    }

    /**
     * 根据id查询角色
     */
    @GetMapping("/getRole")
    @ApiOperation(value = "根据id查询", notes = "根据id查询记录")
    @ApiImplicitParam(name = "id", value = "角色id", required = true)
    public ResultUtil getRoleById(Integer id) {
        return roleService.getRoleById(id);
    }

    /**
     * 根据id删除角色
     */
    @DeleteMapping("/removeRole")
    @ApiOperation(value = "根据id删除", notes = "根据id删除记录")
    @ApiImplicitParam(name = "id", value = "角色id", required = true)
    public ResultUtil removeRoleById(Integer id) {
        try {
            boolean res = roleService.removeById(id);
            if (res) {
                return ResultUtil.ofSuccessMsg("删除成功");
            }
            return ResultUtil.ofFailMsg("删除失败");
        }catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.ofFailMsg("删除Role出错！");
        }
    }

}
