package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.web.admin.mapper.FeeValueMapper;
import com.atguigu.lease.web.admin.vo.fee.FeeValueVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.ApartmentFeeValue;
import com.atguigu.lease.web.admin.service.ApartmentFeeValueService;
import com.atguigu.lease.web.admin.mapper.ApartmentFeeValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【apartment_fee_value(公寓&杂费关联表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class ApartmentFeeValueServiceImpl extends ServiceImpl<ApartmentFeeValueMapper, ApartmentFeeValue>
    implements ApartmentFeeValueService{
    @Autowired
    private FeeValueMapper feeValueMapper;
    @Override
    public List<FeeValueVo> selectListByApartmentId(Long id) {
        return feeValueMapper.selectListByApartmentId(id);
    }
}




