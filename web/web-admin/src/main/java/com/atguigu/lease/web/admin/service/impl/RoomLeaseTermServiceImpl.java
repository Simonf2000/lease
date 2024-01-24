package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.LeaseTerm;
import com.atguigu.lease.web.admin.mapper.LeaseTermMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.RoomLeaseTerm;
import com.atguigu.lease.web.admin.service.RoomLeaseTermService;
import com.atguigu.lease.web.admin.mapper.RoomLeaseTermMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【room_lease_term(房间租期管理表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class RoomLeaseTermServiceImpl extends ServiceImpl<RoomLeaseTermMapper, RoomLeaseTerm>
    implements RoomLeaseTermService{
    @Autowired
    private RoomLeaseTermMapper roomLeaseTermMapper;
    @Override
    public List<LeaseTerm> selectListByRoomId(Long id) {
        return roomLeaseTermMapper.selectListByRoomId(id);
    }
}




