package com.java.coupon.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.coupon.dto.CouponDto;

/**
 * @작성자 : 전지원
 * @작업일 : 2019. 12. 12.
 * @작업 내용 :  CouponDaoImp 생성
 */
@Component
public class CouponDaoImp implements CouponDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	//쿠폰상품 등록
	@Override
	public CouponDao couponInsert(CouponDto couponDto) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert("dao.CouponMapper.insert", couponDto);
		sqlSessionTemplate.selectOne("dao.CouponMapper.codeSelect");
		return null;
	}
	
}
