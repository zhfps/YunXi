package com.admin.project.system.service;

import com.admin.project.system.base.result.Results;
import com.admin.project.system.dto.UserDto;
import com.admin.project.system.entity.SysUser;
import com.admin.project.system.untils.MyPage;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

public interface UserService {

	SysUser getUser(String username);

	Results<SysUser> getAllUsersByPage(Integer startPosition, Integer limit);

	SysUser getUserByPhone(String phone);

	SysUser getUserByEmail(String email);

    Results save(SysUser user, Integer roleId);

    SysUser getUserById(Long id);

    Results updateUser(UserDto userDto, Integer roleId);

    int deleteUser(Long id);

    Results<SysUser> getUserByFuzzyUserNamePage(String username, Integer startPosition, Integer limit);

    Results changePassword(String username, String oldPassword, String newPassword);

    MyPage<SysUser> getUserByPage(Integer page, Integer pageSize);
}
