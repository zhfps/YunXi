package com.admin.project.system.service.impl;
import com.admin.project.system.dao.PermissionDao;
import com.admin.project.system.dto.LoginUser;
import com.admin.project.system.entity.SysPermission;
import com.admin.project.system.entity.SysUser;
import com.admin.project.system.service.UserService;
import com.admin.project.system.untils.JwtTokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * spring security登陆处理<br>
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Resource
	private UserService userService;
	@Resource
	private PermissionDao permissionDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser sysUser = userService.getUser(username);
		if (sysUser == null) {
			throw new AuthenticationCredentialsNotFoundException("用户名不存在");
		} else if (sysUser.getStatus() == SysUser.Status.LOCKED) {
			throw new LockedException("用户被锁定,请联系管理员");
		} else if (sysUser.getStatus() == SysUser.Status.DISABLED) {
			throw new DisabledException("用户已作废");
		}

		LoginUser loginUser = new LoginUser();
		String token = JwtTokenUtil.createToken("system",username,1000L);
		BeanUtils.copyProperties(sysUser, loginUser);
		loginUser.setToken(token);
		List<SysPermission> permissions = permissionDao.listByUserId(sysUser.getId());
		loginUser.setPermissions(permissions);

		return loginUser;
	}

}
