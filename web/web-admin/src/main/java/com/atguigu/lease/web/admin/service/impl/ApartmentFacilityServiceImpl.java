package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.FacilityInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.ApartmentFacility;
import com.atguigu.lease.web.admin.service.ApartmentFacilityService;
import com.atguigu.lease.web.admin.mapper.ApartmentFacilityMapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【apartment_facility(公寓&配套关联表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class ApartmentFacilityServiceImpl extends ServiceImpl<ApartmentFacilityMapper, ApartmentFacility>
    implements ApartmentFacilityService{
    @Autowired
    ApartmentFacilityMapper apartmentFacilityMapper;
    @Override
    public List<FacilityInfo> queryFacilityInfoList(Long id) {
        return apartmentFacilityMapper.queryFacilityInfoList(id);
    }
}




