package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.web.app.vo.fee.FeeValueVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.FeeValue;
import com.atguigu.lease.web.app.service.FeeValueService;
import com.atguigu.lease.web.app.mapper.FeeValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【fee_value(杂项费用值表)】的数据库操作Service实现
* @createDate 2023-07-26 11:12:39
*/
@Service
public class FeeValueServiceImpl extends ServiceImpl<FeeValueMapper, FeeValue>
    implements FeeValueService{
    @Autowired
    private FeeValueMapper feeValueMapper;
    @Override
    public List<FeeValueVo> selectListByApartmentId(Long apartmentId) {
        return feeValueMapper.selectListByApartmentId(apartmentId);
    }
}




