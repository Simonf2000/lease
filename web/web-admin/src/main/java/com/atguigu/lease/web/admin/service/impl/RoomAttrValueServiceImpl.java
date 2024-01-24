package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.AttrValue;
import com.atguigu.lease.web.admin.mapper.AttrValueMapper;
import com.atguigu.lease.web.admin.vo.attr.AttrValueVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.RoomAttrValue;
import com.atguigu.lease.web.admin.service.RoomAttrValueService;
import com.atguigu.lease.web.admin.mapper.RoomAttrValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【room_attr_value(房间&基本属性值关联表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class RoomAttrValueServiceImpl extends ServiceImpl<RoomAttrValueMapper, RoomAttrValue>
    implements RoomAttrValueService{
    @Autowired
    private AttrValueMapper attrValueMapper;
    @Override
    public List<AttrValueVo> selectListByRoomId(Long id) {
        return attrValueMapper.selectListByRoomId(id);
    }
}




