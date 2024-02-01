package com.atguigu.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.FacilityInfo;
import com.atguigu.lease.web.app.service.FacilityInfoService;
import com.atguigu.lease.web.app.mapper.FacilityInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【facility_info(配套信息表)】的数据库操作Service实现
* @createDate 2023-07-26 11:12:39
*/
@Service
public class FacilityInfoServiceImpl extends ServiceImpl<FacilityInfoMapper, FacilityInfo>
    implements FacilityInfoService{
    @Autowired
    private FacilityInfoMapper facilityInfoMapper;
    @Override
    public List<FacilityInfo> selectListByRoomId(Long id) {
        return facilityInfoMapper.selectListByRoomId(id);
    }

    @Override
    public List<FacilityInfo> selectListByApartmentId(Long id) {
        return facilityInfoMapper.selectListByApartmentId(id);
    }
}




