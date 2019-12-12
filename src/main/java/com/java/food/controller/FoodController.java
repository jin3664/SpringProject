package com.java.food.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.food.dto.FoodDto;
import com.java.food.service.FoodService;

/**
 * @작성자 : 한문구
 * @작성일 : 2019. 12. 12.
 * @설명 : 음식점 컨트롤러 . 
 */

@Controller
public class FoodController {
	/*
	 * @Autowired private FoodService foodService;
	 */
	
	// 음식점 정보 등록 관련
	@RequestMapping(value = "food/insert.go", method = RequestMethod.GET)
	public ModelAndView foodInsert(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("food/insert.tiles");		
		return mav;		
	}

	@RequestMapping(value = "food/insertOk.go", method = RequestMethod.POST)
	public ModelAndView foodInsertOk(HttpServletRequest request, HttpServletResponse response, FoodDto foodDto) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("request",request);
		mav.addObject("foodDto",foodDto);
		// 서비스에 model and view 보냄
		return mav;
	}
	
	// 음식점 정보 수정 관련
	@RequestMapping(value = "food/update.go", method = RequestMethod.GET)
	public ModelAndView foodUpdate(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("request",request);
		return mav;
	}
	
	@RequestMapping(value = "food/updateOk.go", method = RequestMethod.POST)
	public ModelAndView foodUpdateOk(HttpServletRequest request, HttpServletResponse response, FoodDto foodDto) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("request",request);
		mav.addObject("foodDto",foodDto);
		// 서비스에 model and view 보냄
		return mav;
	}
	
	
	// 음식점 정보 삭제 관련
	@RequestMapping(value = "food/delete.go", method = RequestMethod.GET)
	public ModelAndView foodDelete(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("request",request);
		return mav;
	}	
	
	
	// 음식점 상세페이지로 이동
	@RequestMapping(value = "food/detail.go", method = RequestMethod.GET)
	public ModelAndView foodDetail(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("request",request);
		mav.setViewName("food/detail.tiles");	
		return mav;
	}
}
