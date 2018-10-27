package com.hzqing.system.controller;

import com.github.pagehelper.PageInfo;
import com.hzqing.common.base.controller.BaseController;
import com.hzqing.common.response.ResponseMessage;
import com.hzqing.system.domain.Role;
import com.hzqing.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @description 角色管理
 * @author hzqing.com
 */
@RestController
@RequestMapping("/hzq/system/role")
public class RoleController extends BaseController{
    @Autowired
    private IRoleService roleService;

    /**
     * 获取角色管理的列表信息，带分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public ResponseMessage<PageInfo<Role>> list(int pageNum,int pageSize,Role role){
        startPage(pageNum,pageSize);
        List<Role> roles = roleService.selectTableList(role);
        return successPage(roles);
    }

    /**
    * 新增角色管理信息
    * @param role
    * @return
    */
    @PostMapping("/add")
    public ResponseMessage add(@RequestBody  Role role) {
        int res = roleService.insertRole(role);
        return new ResponseMessage().success();
    }

    /**
    * 根据Role Id修改角色管理
    * @param roleId
    * @return
    */
    @GetMapping("/edit/{roleId}")
    public ResponseMessage<Role> edit(@PathVariable String roleId){
        Role role = roleService.selectRoleById(roleId);
        return new ResponseMessage<Role>().success(role);
    }

    /**
    * 修改角色管理
    * @param role
    * @return
    */
    @PutMapping("/edit")
    public ResponseMessage editSave(@RequestBody Role role) {
        int res = roleService.updateRole(role);
        return new ResponseMessage().success(res);
    }


    /**
    * 删除角色管理
    * @param roleIds 多个ID,使用'，'分割
    * @return
    */
    @DeleteMapping("/remove")
    public ResponseMessage remove(String roleIds) {
        int res = roleService.deleteRoleByIds(roleIds);
        return new ResponseMessage().success();
    }

    /**
     * 根据用户id获取用户的角色
     * @param userId
     * @return
     */
    @GetMapping("/selectRolesByUserId/{userId}")
    public ResponseMessage selectRolesByUserId(@PathVariable String userId) {
        List<Role> roles = roleService.selectRolesByUserId(userId);
        return new ResponseMessage().success();
    }

}
