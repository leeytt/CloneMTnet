package com.leeyunt.clonemtnet.controller;


import com.leeyunt.clonemtnet.service.PermissionService;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 权限管理 前端控制器
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
@Api(tags = "权限管理")
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    /**
     * 获取所有权限
     */
    @GetMapping("/getAll")
    @ApiOperation(value="获取所有权限",notes="得到所有权限数据")
    public ResultUtil loadAll(){
        if (0==permissionService.count()) {
            return ResultUtil.ofFailMsg("未查询到结果");
        }
        return ResultUtil.ofSuccess(permissionService.list());
    }

    /**
     * 动态查询
     */
    @PostMapping("/selectPermission")
    @ApiOperation(value = "动态查询", notes = "通过各项参数查询数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "权限ID"),
            @ApiImplicitParam(name = "parentId", value = "父权限ID"),
            @ApiImplicitParam(name = "menuCode", value = "菜单标识"),
            @ApiImplicitParam(name = "menuName", value = "菜单名称"),
            @ApiImplicitParam(name = "icon", value = "图标"),
            @ApiImplicitParam(name = "type", value = "菜单类型 0为目录，1为菜单，2为按钮 默认0"),
            @ApiImplicitParam(name = "sort", value = "分类排序"),
            @ApiImplicitParam(name = "permissionCode", value = "权限标识"),
            @ApiImplicitParam(name = "permissionName", value = "权限名称"),
            @ApiImplicitParam(name = "requiredPermission", value = "是否必选权限"),
            @ApiImplicitParam(name = "orderByCase", value = "排序", defaultValue = "sort"),
            @ApiImplicitParam(name = "desc", value = "排序方式", defaultValue = "0"),
            @ApiImplicitParam(name = "pageNow", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", defaultValue = "10")})
    public ResultUtil selectPermission(Integer id, Integer parentId, String menuCode, String menuName, String icon, Integer type, Integer sort, String permissionCode, String permissionName, Boolean requiredPermission, String orderByCase, Boolean desc, Integer pageNow, Integer pageSize) {
        if (null != orderByCase) {
            if (desc == null) {
                desc = false;
            }
            orderByCase += desc ? " desc" : " asc";
        } else {
            orderByCase = "sort";
            if (desc == null) {
                desc = false;
            }
            orderByCase += desc ? " desc" : " asc";
        }
        return permissionService.selectPermission(id, parentId, menuCode, menuName, icon, type, sort, permissionCode, permissionName, requiredPermission, orderByCase, desc, pageNow, pageSize);
    }

    /**
     * 添加权限
     */
    @PostMapping("/addPermission")
    @ApiOperation(value = "添加权限", notes = "添加记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父权限ID", defaultValue = "0", required = true),
            @ApiImplicitParam(name = "menuCode", value = "菜单标识"),
            @ApiImplicitParam(name = "menuName", value = "菜单名称"),
            @ApiImplicitParam(name = "icon", value = "图标"),
            @ApiImplicitParam(name = "type", value = "菜单类型 0为目录，1为菜单，2为按钮 默认0"),
            @ApiImplicitParam(name = "sort", value = "分类排序"),
            @ApiImplicitParam(name = "permissionCode", value = "权限标识"),
            @ApiImplicitParam(name = "permissionName", value = "权限名称"),
            @ApiImplicitParam(name = "requiredPermission", value = "是否必选权限")})
    public ResultUtil addPermission(Integer parentId, String menuCode, String menuName, String icon, Integer type, Integer sort, String permissionCode, String permissionName, Boolean requiredPermission) {
        if (null == parentId) {
            return ResultUtil.ofFailMsg("父权限ID不能为空");
        }
        return permissionService.addPermission(parentId, menuCode, menuName, icon, type, sort, permissionCode, permissionName, requiredPermission);
    }


    /**
     * 动态更新权限
     */
    @PutMapping("/updatePermission")
    @ApiOperation(value = "更新权限", notes = "根据id动态更新记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "权限ID", required = true),
            @ApiImplicitParam(name = "parentId", value = "父权限ID"),
            @ApiImplicitParam(name = "menuCode", value = "菜单标识"),
            @ApiImplicitParam(name = "menuName", value = "菜单名称"),
            @ApiImplicitParam(name = "icon", value = "图标"),
            @ApiImplicitParam(name = "type", value = "菜单类型 0为目录，1为菜单，2为按钮 默认0"),
            @ApiImplicitParam(name = "sort", value = "分类排序"),
            @ApiImplicitParam(name = "permissionCode", value = "权限标识"),
            @ApiImplicitParam(name = "permissionName", value = "权限名称"),
            @ApiImplicitParam(name = "requiredPermission", value = "是否必选权限")})
    public ResultUtil updatePermissionById(Integer id, Integer parentId, String menuCode, String menuName, String icon, Integer type, Integer sort, String permissionCode, String permissionName, Boolean requiredPermission) {
        if (null == id) {
            return ResultUtil.ofFailMsg("权限id不能为空");
        }
        return permissionService.updatePermissionById(id, parentId, menuCode, menuName, icon, type, sort, permissionCode, permissionName, requiredPermission);
    }

    /**
     * 根据id查询权限
     */
    @GetMapping("/getPermission")
    @ApiOperation(value = "根据id查询", notes = "根据id查询记录")
    @ApiImplicitParam(name = "id", value = "权限id", required = true)
    public ResultUtil getPermissionById(Integer id) {
        return permissionService.getPermissionById(id);
    }

    /**
     * 根据id删除权限
     */
    @DeleteMapping("/removePermission")
    @ApiOperation(value = "根据id删除", notes = "根据id删除记录")
    @ApiImplicitParam(name = "id", value = "权限id", required = true)
    public ResultUtil removePermissionById(Integer id) {
        try {
            boolean res = permissionService.removeById(id);
            if (res) {
                return ResultUtil.ofSuccessMsg("删除成功");
            }
            return ResultUtil.ofFailMsg("删除失败");
        }catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.ofFailMsg("删除Permission出错！");
        }
    }

    /**
     * 获取权限树
     */
    @PostMapping("/selectPermissionTree")
    @ApiOperation(value="获取权限树",notes="得到权限树")
    public ResultUtil selectPermissionTree(){
        return permissionService.selectPermissionTree();
    }

    /**
     * 保存角色的权限
     */
    @PostMapping("/updatePermissionTree")
    @ApiOperation(value="保存权限树",notes="保存权限树")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色ID", required = true),
            @ApiImplicitParam(name = "menus", value = "菜单id列表", required = true)})
    public ResultUtil updatePermissionTree(Integer id, Integer[] menus){
        if (null == id) {
            return ResultUtil.ofFailMsg("角色id不能为空");
        }
        if (null == menus) {
            return ResultUtil.ofFailMsg("菜单列表不能为空");
        }
        return permissionService.updatePermissionTree(id, menus);
    }

}
