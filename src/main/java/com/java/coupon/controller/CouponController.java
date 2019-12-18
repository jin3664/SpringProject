package com.java.coupon.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.java.coupon.dto.CouponDto;
import com.java.coupon.service.CouponService;
import com.java.image.dto.ImageDto;

/**
 * @작성자 : 전지원
 * @작업일 : 2019. 12. 12.
 * @작업 내용 : CouponController 생성
 */
@Controller
public class CouponController {
	@Autowired
	private CouponService couponService;
	
	//쿠폰상품 등록 페이지
	@RequestMapping(value="/coupon/couponInsert.go", method= RequestMethod.GET)
	public ModelAndView couponInsert(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("coupon/couponInsert.tiles");
		
		return mav;
	}
	
	//쿠폰상품 등록
	@RequestMapping(value="/coupon/couponInsertOk.go", method= RequestMethod.POST)
	public ModelAndView couponInsertOk(HttpServletRequest request, HttpServletResponse response, CouponDto couponDto) {
		String imageFile = request.getParameter("imageFile");
		//JejuAspect.logger.info(JejuAspect.logMsg + "imageFile: "+ imageFile);
		ModelAndView mav = new ModelAndView();
		mav.addObject("request", request);
		mav.addObject("couponDto", couponDto);
		mav.addObject("imageFile", imageFile);
		
		couponService.couponInsertOk(mav);
		
		return mav;
	}
	
	//식당코드 검색 
	@RequestMapping(value="/coupon/searchFoodCode.go", method=RequestMethod.GET)
	public ModelAndView readFoodCode(HttpServletRequest request, HttpServletResponse response, CouponDto couponDto) {
		String foodName = request.getParameter("foodName");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("request", request);
		mav.addObject("foodName", foodName);
		couponService.searchFoodCode(mav);
		
		return mav;
	}
	
	//쿠폰리스트
	@RequestMapping(value="/coupon/couponList.go", method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView couponList(HttpServletRequest request, HttpServletResponse response, CouponDto couponDto) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("request", request);
		
		couponService.couponList(mav);
		
		
		
		
		return mav;
	}
	
	//쿠폰 상세페이지
	@RequestMapping(value="/coupon/couponRead.go", method=RequestMethod.GET)
	public ModelAndView couponRead(HttpServletRequest request, HttpServletResponse response, CouponDto couponDto) {
		ModelAndView mav= new ModelAndView();
		mav.addObject("request", request);
		mav.addObject("response", response);	// 임시
		
		couponService.couponRead(mav);
		
		return mav;
	}
	
	//쿠폰상품 불러오기(수정)
	@RequestMapping(value="/coupon/couponUpdate.go", method=RequestMethod.GET)
	public ModelAndView couponUpdate(HttpServletRequest request, HttpServletResponse response, CouponDto couponDto) {
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		
		String couponCode = request.getParameter("couponCode");
		ModelAndView mav = new ModelAndView();
		mav.addObject("request", request);
		mav.addObject("couponCode", couponCode);
		mav.addObject("pageNumber", pageNumber);
		couponService.couponUpdate(mav);
		
		return mav;
	}
	
	//쿠폰상품 수정
	@RequestMapping(value="/coupon/couponUpdateOk.go", method=RequestMethod.POST)
	public ModelAndView couponUpdateOk(HttpServletRequest request, HttpServletResponse response, CouponDto couponDto, ImageDto imageDto) {
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("request", request);
		mav.addObject("couponDto", couponDto);
		mav.addObject("imageDto", imageDto);
		mav.addObject("pageNumber", pageNumber);
		
		couponService.couponUpdateOk(mav);
		
		return mav;
	}
	
	
	//쿠폰상품 삭제창
	@RequestMapping(value="/coupon/couponDelete.go", method=RequestMethod.GET)
	public ModelAndView couponDelete(HttpServletRequest request, HttpServletResponse response) {
		String couponCode = request.getParameter("couponCode");
		String couponName = request.getParameter("couponName");
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("request", request);
		mav.addObject("couponCode", couponCode);
		mav.addObject("couponName", couponName);
		mav.addObject("pageNumber", pageNumber);
		
		
		mav.setViewName("coupon/couponDelete.empty");
		
		return mav;	
		
	}
	
	//쿠폰상품 삭제
	@RequestMapping(value="/coupon/couponDeleteOk.go", method=RequestMethod.POST)
	public ModelAndView couponDeleteOk(HttpServletRequest request, HttpServletResponse response) {
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		
		ModelAndView mav = new  ModelAndView();
		mav.addObject("request", request);
		mav.addObject("pageNumber", pageNumber);
		
		couponService.couponDeleteOk(mav);
		
		return mav;
	}
	
}