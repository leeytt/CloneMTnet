package com.leeyunt.clonemtnet.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 后台权限表
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_permission")
@ApiModel(value="Permission对象", description="后台权限表")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "父权限id ")
    @TableField("parentId")
    private Integer parentId;

    @ApiModelProperty(value = "归属菜单,前端判断并展示菜单使用,")
    private String menuCode;

    @ApiModelProperty(value = "菜单的中文释义")
    private String menuName;

    @ApiModelProperty(value = "分类排序")
    private Integer sort;

    @ApiModelProperty(value = "权限的代码/通配符,对应代码中@RequiresPermissions 的value")
    private String permissionCode;

    @ApiModelProperty(value = "权限的中文释义")
    private String permissionName;

    @ApiModelProperty(value = "是否本菜单必选权限  1必选 0非必选 通常是列表权限是必选")
    private Boolean requiredPermission;


}
