package com.leeyunt.clonemtnet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leeyunt.clonemtnet.dao.PermissionDao;
import com.leeyunt.clonemtnet.dao.RolePermissionDao;
import com.leeyunt.clonemtnet.entity.Permission;
import com.leeyunt.clonemtnet.entity.RolePermission;
import com.leeyunt.clonemtnet.exception.StatusEnum;
import com.leeyunt.clonemtnet.service.PermissionService;
import com.leeyunt.clonemtnet.utils.Page;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台权限表 服务实现类
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, Permission> implements PermissionService {
    @Resource
    private PermissionDao permissionDao;

    @Resource
    private RolePermissionDao rolePermissionDao;

    /**
     * 动态查询
     */
    @Override
    public ResultUtil selectPermission(Integer id, Integer parentId, String menuCode, String menuName, String icon, Integer type, Integer sort, String permissionCode, String permissionName, Boolean requiredPermission, String orderByCase, Boolean desc, Integer pageNow, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        if (null != id)
            map.put("id", id);
        if (null!= parentId)
            map.put("parentId", parentId);
        if (null!= menuCode)
            map.put("menuCode", menuCode);
        if (null!= menuName)
            map.put("menuName", menuName);
        if (null!= icon)
            map.put("icon", icon);
        if (null!= type)
            map.put("type", type);
        if (null!= sort)
            map.put("sort", sort);
        if (null!= permissionCode)
            map.put("permissionCode", permissionCode);
        if (null!= permissionName)
            map.put("permissionName", permissionName);
        if (null!= requiredPermission)
            map.put("requiredPermission", requiredPermission);
        if (null != orderByCase)
            map.put("orderByCase", orderByCase);
        if (null != pageNow && null != pageSize) {
            map.put("startPos", (pageNow - 1) * pageSize);
            map.put("pageSize", pageSize);
        }
        try {
            long count = permissionDao.getPermissionCount(map);
            if (count ==0 ) {
                return ResultUtil.ofFailMsg("未查询到结果");
            }
            List<Permission> list = permissionDao.dynamicSelect(map);
            HashMap<String, Object> data = new HashMap<>();
            pageSize = null != pageSize ? pageSize : (int) (count);
            pageNow = null != pageNow ? pageNow : 1;
            if (pageSize != 0) {
                Page page = new Page(count, pageNow);
                page.setPageSize(pageSize);
                data.put("page", page);
            }
            if (list.size() > 0) {
                data.put("list", list);
                return ResultUtil.ofSuccess(data);
            } else {
                return ResultUtil.ofStatusCode(StatusEnum.FAIL);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.ofFailMsg("dynamic查询Permission出错！");
        }
    }

    /**
     * 添加权限
     */
    @Override
    public ResultUtil addPermission(Integer parentId, String menuCode, String menuName, String icon, Integer type, Integer sort, String permissionCode, String permissionName, Boolean requiredPermission) {
        Permission permission = new Permission();
        permission.setParentId(parentId);
        permission.setMenuCode(menuCode);
        permission.setMenuName(menuName);
        permission.setIcon(icon);
        permission.setType(type);
        permission.setSort(sort);
        permission.setPermissionCode(permissionCode);
        permission.setPermissionName(permissionName);
        permission.setRequiredPermission(requiredPermission);
        try {
            int res = permissionDao.insert(permission);
            if (res == 1) {
                return ResultUtil.ofSuccessMsg("添加成功");
            } else {
                return ResultUtil.ofFailMsg("添加失败");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.ofFailMsg("insertPermission出错！");
        }
    }

    /**
     * 动态更新权限
     */
    @Override
    public ResultUtil updatePermissionById(Integer id, Integer parentId, String menuCode, String menuName, String icon, Integer type, Integer sort, String permissionCode, String permissionName, Boolean requiredPermission) {
        Permission permission = permissionDao.findById(id);
        if (null != permission) {
            permission.setId(id);
            permission.setParentId(parentId);
            permission.setMenuCode(menuCode);
            permission.setMenuName(menuName);
            permission.setIcon(icon);
            permission.setType(type);
            permission.setSort(sort);
            permission.setPermissionCode(permissionCode);
            permission.setPermissionName(permissionName);
            permission.setRequiredPermission(requiredPermission);
            try {
                int res = permissionDao.dynamicUpdate(permission);
                if (res == 1) {
                    return ResultUtil.ofSuccessMsg("更新成功");
                } else {
                    return ResultUtil.ofFailMsg("更新失败");
                }
            }catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.ofFailMsg("dynamicUpdatePermission出错！");
            }
        }else {
            return ResultUtil.ofFailMsg("ID不存在");
        }
    }

    /**
     * 根据id查询权限
     */
    @Override
    public ResultUtil getPermissionById(Integer id) {
        Permission permission = permissionDao.findById(id);
        if (null != permission) {
            return ResultUtil.ofSuccess(permission);
        }
        return ResultUtil.ofFailMsg("未能获取id=" + id + "的数据");
    }

    /**
     * 获取权限树
     */
    @Override
    public ResultUtil selectPermissionTree() {
        long count = permissionDao.getCount();
        if (count ==0 ) {
            return ResultUtil.ofFailMsg("未查询到结果");
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("totalCount", count);
        //接收所有的信息
        List<Permission> allList = permissionDao.findAll();
        //接收获取的父节点
        List<Permission> parentMapList = new ArrayList<>();
        try {
            //循环所有信息找出所有的根节点（parentId=0）
            for (int i = 0; i < allList.size(); i++) {
                if ("0".equals(allList.get(i).getParentId().toString())) {
                    //将找出来的根节点重新存到一个List<Permission>集合中
                    parentMapList.add(allList.get(i));
                    //从所有的数据中移除根节点
                    allList.remove(i);
                    //每移除一个根节点就将所有数据的个数减一
                    i--;
                }
            }
            //寻找子节点
            List<Permission> list = recursive(allList, parentMapList);
            if (list.size() > 0) {
                data.put("list", list);
                return ResultUtil.ofSuccess(data);
            } else {
                return ResultUtil.ofStatusCode(StatusEnum.FAIL);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.ofFailMsg("selectPermissionTree出错！");
        }
    }

    /**
     * 保存角色权限
     */
    @Override
    public ResultUtil updatePermissionTree(Integer id, Integer[] menus) {
        int count = rolePermissionDao.getCount(id);
        if (count != 0) {
            //移除当前角色的所有权限
            rolePermissionDao.deleteByRoleId(id);
        }
        //添加新的权限
        for (int i = 0; i < menus.length; i++){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(id);
            rolePermission.setPermissionId(menus[i]);
            try {
                rolePermissionDao.insert(rolePermission);
            }catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.ofFailMsg("保存失败");
            }
        }
        return ResultUtil.ofSuccessMsg("菜单保存成功");
    }


    /**
     * <p>寻找子节点</p>
     * @param parentMapList 所有父节点
     * @param allList       所有数据
     * @return parentMapList
     */
    private List<Permission> recursive(List<Permission> allList, List<Permission> parentMapList) {
        //循环根节点
        for (int j = 0; j < parentMapList.size(); j++) {
            List<Permission> tempList = new ArrayList<>();  //用来存取子节点
            //循环全部节点，判断节点中parentId是否与根节点中的id相同
            for (int k = 0; k < allList.size(); k++) {
                if (allList.get(k).getParentId().toString().equals(parentMapList.get(j).getId().toString())) {
                    tempList.add(allList.get(k));
                }
            }
            if (tempList.size() > 0) {
                parentMapList.get(j).setChildren(tempList);
                //此次循环又将tempList作为parentMapList（父节点去找其他的子节点），直到没有子节点在执行j+1
                recursive(allList, tempList);
            }
        }
        return parentMapList;
    }
}
