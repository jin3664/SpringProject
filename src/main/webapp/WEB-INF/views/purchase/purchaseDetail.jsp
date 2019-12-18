<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<html>
	<head>
		<meta charset="UTF-8">
		<title>쿠폰 구매 상세페이지</title>
	</head>
	<body>
		<p>쿠폰명: ${couponDto.couponName}</p>
		<p>음식점 이름: ${couponDto.foodName}</p>
		<p>쿠폰 사용가능 기간: ${couponDto.couponStartdate}부터 ${couponDto.couponEnddate}까지</p>
		<p>쿠폰가격: ${couponDto.couponCostori}</p>
		<p>쿠폰할인율: ${couponDto.couponCostsale}</p>
		<p>쿠폰할인가: ${couponDto.couponSalerate}</p>
		
		<p>결제금액: ${couponDto.couponSalerate}</p>
		
		<!-- 결제 -->
		<form action="${root}/purchase/purchaseOk.go" method="post">
			<input type="hidden" name="couponCode">
			<!-- <input type="hidden" name="memberCode"> -->
			
			<p><input type="submit" value="결제하기"></p>
		</form>
	</body>
</html>