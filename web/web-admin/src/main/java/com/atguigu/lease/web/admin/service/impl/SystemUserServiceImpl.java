package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.SystemPost;
import com.atguigu.lease.model.entity.SystemUser;
import com.atguigu.lease.web.admin.mapper.SystemUserMapper;
import com.atguigu.lease.web.admin.service.SystemPostService;
import com.atguigu.lease.web.admin.service.SystemUserService;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【system_user(员工信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser>
        implements SystemUserService {

    @Autowired
    SystemPostService systemPostService;
    @Autowired
    SystemUserMapper systemUserMapper;

    @Override
    public void systemUserPage(Page<SystemUserItemVo> page, SystemUserQueryVo queryVo) {
        systemUserMapper.systemUserPage(page, queryVo);
    }

    /**
    * @Description:
     * `system_user`表中的`password`字段不应查询，需要在`SystemUser`的`password`字段的`@TableField`注解中增加`select=false`参数。
    * @Param: [id]
    * @return: com.atguigu.lease.web.admin.vo.system.user.SystemUserItemVo
    * @Author: simonf
    * @Date: 2024/1/25
    */
    @Override
    public SystemUserItemVo getSystemUserById(Long id) {
        SystemUser systemUser = super.getById(id);

        SystemPost systemPost = systemPostService.getById(systemUser.getPostId());
        SystemUserItemVo systemUserItemVo = new SystemUserItemVo();
        BeanUtils.copyProperties(systemPost, systemUserItemVo);
        systemUserItemVo.setPostName(systemUserItemVo.getPostName());

        return systemUserItemVo;
    }
}




