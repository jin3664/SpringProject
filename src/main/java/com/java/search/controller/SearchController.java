package com.java.search.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.aop.JejuAspect;
import com.java.search.service.SearchService;


/**
 * @작업자 : 준혁
 * @작업일 : 2019. 12. 12.
 * @작업내용 : SearchController 생성
 */

@Controller
public class SearchController {
	
	@Autowired
	private SearchService searchService;

	@RequestMapping(value = "/search.go", method = RequestMethod.GET)
	public String search(HttpServletRequest request, HttpServletResponse response) {
		return "search/search.tiles";
	}
	
	// 음식점 리스트
	@RequestMapping(value="/food/list.go", method= RequestMethod.GET)
	public ModelAndView foodList(HttpServletRequest request, HttpServletResponse reponse) {
		ModelAndView mav = new ModelAndView();
		
		String tagValue = request.getParameter("tagValue");
		String tagType = request.getParameter("tagType");
		
		mav.addObject("request", request);
		mav.addObject("hashTag", tagValue);
		mav.addObject("hashTag", tagType);
		
		searchService.foodList(mav);
		return mav;
	}
	
	// 키워드 검색
	@RequestMapping(value="/searchKeyword.go", method= RequestMethod.GET)
	public ModelAndView searchKeyword(HttpServletRequest request, HttpServletResponse reponse) {
		ModelAndView mav = new ModelAndView();
		
		String keyword = request.getParameter("keyword");
		
		mav.addObject("request", request);
		mav.addObject("keyword", keyword);
		
		searchService.searchKeyword(mav);
		return mav;
	}
	
	// 키워드 자동완성
//	@RequestMapping(value = "/searchKeyword.do", method = RequestMethod.GET)
	public ModelAndView searchAutoComplete(/* Vo vo, */ HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView();
		
		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
		
		// 리스트 or 배열로 받아서 넘기기 resultList = searchService.searchKeyword(request);
		
		HashMap<String, String> test = new HashMap<String, String>();
		
		test.put("foodName", "test1");
		test.put("foodName", "test2");
		test.put("foodName", "test3");
		test.put("foodName", "test4");
		test.put("foodName", "test5");
		test.put("foodName", "test6");
		test.put("foodName", "test7");
		
		resultList.add(test);
		
		mav.addObject("resultList", resultList);
		
		return mav;
	}
}