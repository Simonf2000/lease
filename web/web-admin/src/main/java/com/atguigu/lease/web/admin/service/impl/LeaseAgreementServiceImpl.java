package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.web.admin.mapper.ApartmentInfoMapper;
import com.atguigu.lease.web.admin.mapper.LeaseAgreementMapper;
import com.atguigu.lease.web.admin.service.*;
import com.atguigu.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.atguigu.lease.web.admin.vo.agreement.AgreementVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {
    @Autowired
    private LeaseAgreementMapper agreementMapper;
    @Autowired
    private ApartmentInfoService apartmentInfoService;
    @Autowired
    private RoomInfoService roomInfoService;
    @Autowired
    private PaymentTypeService paymentTypeService;
    @Autowired
    private LeaseTermService leaseTermService;
    @Override
    public void customLeaseAgreementPage(Page<AgreementVo> page, AgreementQueryVo queryVo) {
        agreementMapper.customLeaseAgreementPage(page,queryVo);
    }
    /**
     * @Description:
     * AgreementVo含：
     * lease_agreement表 √
     * apartmentInfo
     * roomInfo
     * PaymentType
     * LeaseTerm
     * @Author: simonf
     * @Date: 2024/1/24
     */
    @Override
    public AgreementVo getAgreementById(Long id) {
        //1.查询租约信息
        LeaseAgreement leaseAgreement = super.getById(id);

        //2.查询公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoService.getApartmentDetailById(leaseAgreement.getApartmentId());

        //3.查询房间信息
        RoomInfo roomInfo = roomInfoService.getRoomDetailById(leaseAgreement.getRoomId());

        //4.查询支付方式
        PaymentType paymentType = paymentTypeService.getById(leaseAgreement.getPaymentTypeId());

        //5.查询租期
        LeaseTerm leaseTerm = leaseTermService.selectById(leaseAgreement.getLeaseTermId());

        AgreementVo adminAgreementVo = new AgreementVo();
        BeanUtils.copyProperties(leaseAgreement, adminAgreementVo);
        adminAgreementVo.setApartmentInfo(apartmentInfo);
        adminAgreementVo.setRoomInfo(roomInfo);
        adminAgreementVo.setPaymentType(paymentType);
        adminAgreementVo.setLeaseTerm(leaseTerm);
        return adminAgreementVo;
    }
}




