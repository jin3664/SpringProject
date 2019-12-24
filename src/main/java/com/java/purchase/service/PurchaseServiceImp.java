package com.java.purchase.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.java.aop.JejuAspect;
import com.java.coupon.dao.CouponDao;
import com.java.coupon.dto.CouponDto;
import com.java.image.dao.ImageDao;
import com.java.image.dto.ImageDto;
import com.java.mailing.dto.MailDto;
import com.java.member.dao.MemberDao;
import com.java.member.dto.MemberDto;
import com.java.purchase.dao.PurchaseDao;
import com.java.purchase.dto.PurchaseDto;
import com.java.purchase.dto.PurchaseListDto;

/**
 * @작성자 : 전지원
 * @작업일 : 2019. 12. 19.
 * @작업 내용 :  
*/
@Component
public class PurchaseServiceImp implements PurchaseService {
	@Autowired
	private PurchaseDao purchaseDao;
	
	@Autowired
	private CouponDao couponDao;
	
	@Inject
	JavaMailSender mailSender;
	
	// 구매 페이지 연결
	@Override
	public void purchaseInsert(ModelAndView mav) {
		Map<String, Object> map = mav.getModelMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String couponCode = request.getParameter("couponCode");
		String purchasePhone = request.getParameter("purchasePhone");
		JejuAspect.logger.info(JejuAspect.logMsg + "purchasePhone: "+ purchasePhone);
		JejuAspect.logger.info(JejuAspect.logMsg + "couponCode: "+ couponCode);
		
		CouponDto couponDto = couponDao.purchaseSelect(couponCode);
		JejuAspect.logger.info(JejuAspect.logMsg + "couponDto: "+ couponDto.toString());
		
		
		//세션으로 회원정보 가져오기
		HttpSession session =  request.getSession(false);
		String memberCode = (String) session.getAttribute("memberCode");
		if(memberCode != null) {
			JejuAspect.logger.info(JejuAspect.logMsg + "memberCode: "+ memberCode);
			MemberDto memberDto = purchaseDao.purchaseMember(memberCode);
			
			JejuAspect.logger.info(JejuAspect.logMsg + "memberDto: "+ memberDto.toString());
			mav.addObject("memberDto", memberDto);
		}
		
		mav.addObject("purchasePhone", purchasePhone);
		mav.addObject("couponDto", couponDto);
		mav.setViewName("purchase/purchaseDetail.tiles");
	}
	
	//구매하기
	@Override
	public void purchaseInsertOk(ModelAndView mav) {
		Map<String, Object> map = mav.getModelMap();
		PurchaseDto purchaseDto = (PurchaseDto) map.get("purchaseDto");
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String memberMail = request.getParameter("memberMail");
		JejuAspect.logger.info(JejuAspect.logMsg + "memberMail: "+ memberMail);
		
		purchaseDto.setPurchaseDate(new Date());
		JejuAspect.logger.info(JejuAspect.logMsg + "purchaseDto: "+ purchaseDto.toString());
		
		String purchaseCode = purchaseDao.purchaseInsertOk(purchaseDto);
		
		int check = 0;
		CouponDto couponDto = null;
		if(purchaseCode != null) {
			check = 1;
			couponDto = purchaseDao.purchaseCouponSelect(purchaseCode);
			JejuAspect.logger.info(JejuAspect.logMsg + "couponDto: "+ couponDto.toString());
			mav.addObject("couponDto", couponDto);
		}
		
		String couponName = couponDto.getCouponName();	
		int couponCost = couponDto.getCouponCostsale();	
		
		String subject = "구매해주셔서 감사합니다.";
		String mailContent = "결제가 완료되었습니다." + "구매하신 상품: "+ couponName + "결제된 금액: "+ couponCost;
		
		if(check > 0) {
			try {
				MimeMessage msg = mailSender.createMimeMessage();
				MailDto mailDto = new MailDto();
				msg.addRecipient(RecipientType.TO, new InternetAddress(memberMail));
				msg.addFrom(new InternetAddress[] {new InternetAddress("labelle410@gmail.com")});
				msg.setSubject(subject, "utf-8");								
				msg.setText(mailContent, "utf-8");									
				
				mailSender.send(msg);
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		mav.addObject("check",check);
		mav.setViewName("purchase/purchaseInsertOk.tiles");
	}
	
	//구매내역
	@Override
	public void purchaseList(ModelAndView mav) {
		Map<String, Object> map = mav.getModelMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		HttpSession session =  request.getSession(false);
		String memberCode = (String) session.getAttribute("memberCode");
		
		String pageNumber = request.getParameter("pageNumber");
		if(pageNumber == null) pageNumber = "1";
		int currentPage = Integer.parseInt(pageNumber);
		JejuAspect.logger.info(JejuAspect.logMsg + "pageNumber/currentPage: "+ pageNumber +"/"+ currentPage);

		int count = purchaseDao.getCount(memberCode);
		JejuAspect.logger.info(JejuAspect.logMsg + "count: "+ count);
		
		int boardSize = 6;
		int startRow = (currentPage-1)*boardSize+1;
		int endRow = currentPage*boardSize;
		JejuAspect.logger.info(JejuAspect.logMsg + "startRow/endRow: "+ startRow +"/"+ endRow);
		
		List<PurchaseListDto> purchaseList = purchaseDao.purchaseSelectAll(memberCode, startRow, endRow);
		JejuAspect.logger.info(JejuAspect.logMsg + "purchaseList: "+ purchaseList.size());
		JejuAspect.logger.info(JejuAspect.logMsg + "purchaseList: "+ purchaseList.toString());
		
		mav.addObject("pageNumber", pageNumber);
		mav.addObject("boardSize", boardSize);
		mav.addObject("count", count);
		mav.addObject("purchaseList", purchaseList);
		mav.setViewName("purchase/purchaseList.tiles");
	}
	
	//구매내역전체(관리자)
	@Override
	public void purchaseListAll(ModelAndView mav) {
		Map<String, Object> map = mav.getModelMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String pageNumber = request.getParameter("pageNumber");
		if(pageNumber == null) pageNumber = "1";
		int currentPage = Integer.parseInt(pageNumber);
		JejuAspect.logger.info(JejuAspect.logMsg + "pageNumber/currentPage: "+ pageNumber +"/"+ currentPage);

		int count = purchaseDao.getCountAll();
		JejuAspect.logger.info(JejuAspect.logMsg + "count: "+ count);
		
		int boardSize = 10;
		int startRow = (currentPage-1)*boardSize+1;
		int endRow = currentPage*boardSize;
		JejuAspect.logger.info(JejuAspect.logMsg + "startRow/endRow: "+ startRow +"/"+ endRow);
		
		List<PurchaseListDto> purchaseList = purchaseDao.purchaseListAll(startRow, endRow);
		JejuAspect.logger.info(JejuAspect.logMsg + "purchaseList: "+ purchaseList.toString());
		
		mav.addObject("pageNumber", pageNumber);
		mav.addObject("boardSize", boardSize);
		mav.addObject("count", count);
		mav.addObject("purchaseList", purchaseList);
	}
	
	
	// 구매 취소
	@Override
	public void purchaseDeleteOk(ModelAndView mav) {
		Map<String, Object> map = mav.getModelMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String couponCode = (String) map.get("couponCode");
		int pageNumber = (Integer) map.get("pageNumber");
		
		HttpSession session =  request.getSession(false);
		String memberCode = (String) session.getAttribute("memberCode");
		
		JejuAspect.logger.info(JejuAspect.logMsg + "memberCode: "+ memberCode);
		int check = purchaseDao.purchaseDelete(couponCode, memberCode);
		JejuAspect.logger.info(JejuAspect.logMsg + "check: "+ check);
		
		mav.addObject("check", check);
		mav.addObject("pageNumber", pageNumber);
		mav.setViewName("purchase/purchaseDeleteOk.tiles");
	}
	
}
