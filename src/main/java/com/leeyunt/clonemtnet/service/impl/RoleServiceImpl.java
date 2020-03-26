package com.leeyunt.clonemtnet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leeyunt.clonemtnet.dao.RoleDao;
import com.leeyunt.clonemtnet.entity.Role;
import com.leeyunt.clonemtnet.exception.StatusEnum;
import com.leeyunt.clonemtnet.service.RoleService;
import com.leeyunt.clonemtnet.utils.Page;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台角色表 服务实现类
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {
    @Resource
    private RoleDao roleDao;

    /**
     * 动态查询
     */
    @Override
    public ResultUtil selectRole(Integer id, String roleName, String description, Boolean status, String orderByCase, Boolean desc, Integer pageNow, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        if (null != id)
            map.put("id", id);
        if (null!= roleName)
            map.put("roleName", roleName);
        if (null!= description)
            map.put("description", description);
        if (null!= status)
            map.put("status", status);
        if (null != orderByCase)
            map.put("orderByCase", orderByCase);
        if (null != pageNow && null != pageSize) {
            map.put("startPos", (pageNow - 1) * pageSize);
            map.put("pageSize", pageSize);
        }
        try {
            long count = roleDao.getRoleCount(map);
            if (count ==0 ) {
                return ResultUtil.ofFailMsg("未查询到结果");
            }
            List<Role> list = roleDao.dynamicSelect(map);
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
            return ResultUtil.ofFailMsg("dynamic查询Role出错！");
        }
    }

    /**
     * 添加角色
     */
    @Override
    public ResultUtil addRole(String roleName, String description, Boolean status) {
        if (null != roleDao.findByRoleName(roleName)) {
            return ResultUtil.ofFailMsg("角色名已存在");
        } else {
            Role role = new Role();
            role.setRoleName(roleName);
            role.setDescription(description);
            role.setStatus(status);
            try {
                int res = roleDao.insert(role);
                if (res == 1) {
                    return ResultUtil.ofSuccessMsg("添加成功");
                } else {
                    return ResultUtil.ofFailMsg("添加失败");
                }
            }catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.ofFailMsg("insertRole出错！");
            }
        }
    }

    /**
     * 动态更新角色
     */
    @Override
    public ResultUtil updateRoleById(Integer id, String roleName, String description, Boolean status) {
        Role role = roleDao.findById(id);
        if (null !=role) {
            role.setId(id);
            role.setRoleName(roleName);
            role.setDescription(description);
            role.setStatus(status);
            try {
                int res = roleDao.dynamicUpdate(role);
                if (res == 1) {
                    return ResultUtil.ofSuccessMsg("更新成功");
                } else {
                    return ResultUtil.ofFailMsg("更新失败");
                }
            }catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.ofFailMsg("dynamicUpdateRole出错！");
            }
        }else {
            return ResultUtil.ofFailMsg("ID不存在");
        }
    }

    /**
     * 根据id查询角色
     */
    @Override
    public ResultUtil getRoleById(Integer id) {
        Role role = roleDao.findById(id);
        if (null != role) {
            return ResultUtil.ofSuccess(role);
        }
        return ResultUtil.ofFailMsg("未能获取id=" + id + "的数据");
    }
}
