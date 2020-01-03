<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix ="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<html>
<head>
<meta charset="UTF-8">
<title>맛집 리스트</title>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}
a {
	text-decoration: none;
}
.button {
    color: #FFFFFF;
    border: #EFB730 solid 2px;
    border-radius: 5px;
    background-color: transparent;
    font-size: 23px;
    box-shadow: rgba(0, 0, 0, 0.15) 0px 2px 10px;
}
.button > a {
	color: #EFB730;
	font-size: 18px;
    line-height: 32px;
}
#content {
	margin : 10px auto;
	overflow: hidden;
	max-width : 1000px;
	margin-top: 40px;
}
.title {
	font-size: 30px;
	padding-bottom: 20px;
}
.tag {
	text-align: center;
	display: flex;
	justify-content: center;
	flex-wrap: wrap;
	margin-bottom: 40px;
	
}
.tag > button {
	width: 120px;
	margin: 0 10px 6px;
	line-height: 26px;
}
.tag > button:hover{
	background: #EFB730;
}
.tag > button:hover a {
	color: #fff;
}

.tag > .button.active {
	background: #EFB730;
}

.tag > .button.active a {
	color: #fff;
}
.list {
	display: flex;
	flex-flow: row wrap;
	margin: 0 auto;
}

.list > div {
	width: 300px;
	margin: 0 13px 40px;
	box-shadow: rgba(0, 0, 0, 0.15) 0px 2px 10px;
	flex-direction: column;
}
.list > div strong {
	margin: 0;
	line-height: 40px;
}

.list > div > div {
	padding: 0 10px;
}
.list > div > div:first-child {
	padding: 0;
	width: 300px;
	height: 300px;
	position: relative;
	overflow: hidden;
}

.list > div > div:first-child:after{
	content:"";
	background-color: rgba(0,0,0,0.08);
	width: 100%;
	height: 100%;
	position: absolute;
	top: 0;
	left:0;
	right: 0;
	bottom: 0;
}
.list > div > div:first-child:hover img{
	transform: scale(1.05);
	transition-duration: 0.5s;
}

.list > div > div:first-child:hover:after{
	
	background: transparent;
}

.list > div > div:last-child {
	margin-bottom: 10px;
}

.list img {
	height: 100%;
    width: 100%;
}
.list strong, .page span {
	margin: 0 5px;
	color: #9b9b9b;
}
.list strong {
	font-size: 23px;
	color: #EFB730;
}
.list span {
	font-size: 14px;
}
.list > div > div:first-child {
}

/* 
.page > div > span {
	display: block;
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
} */

.test {
	width: 430px;
	height: 300px;
	float: left;
	background: skyblue;
	margin: 10px;
}
.img {
	width: 430px;
	height: 250px;
}
.goTop {
	font-size: 22px;
	width: 50px;
	color: #EFB730;
	text-shadow: 2px 2px 2px rgba(0, 0, 0, 0.2);
	text-align: center;
	position: fixed;
	bottom: 30px;
	right: 100px;
}
</style>
<script type="text/javascript">
	$(function(){
		$('.button').click(function(){
			$('.button').removeClass("active");
			$(this).addClass("active");
		})
	})
</script>
</head>
<body>
	<div id="content">
		<div class="title">
			<span>믿고 보는 맛집 리스트</span>
		</div>
		<div class="tag">
			<button class="button"><a href="#">#푸드트럭</a></button>
			<button class="button"><a href="#">#중문</a></button>
			<button class="button"><a href="#">#성산</a></button>
			<button class="button"><a href="#">#김녕</a></button>
			<button class="button"><a href="#">#애월</a></button>
			<button class="button"><a href="#">#함덕</a></button>
			<button class="button"><a href="#">#협재</a></button>
			<button class="button"><a href="#">#전복</a></button>
			<button class="button"><a href="#">#전망</a></button>
			<button class="button"><a href="#">#여유</a></button>
			<button class="button"><a href="#">#데이트</a></button>
			<button class="button"><a href="#">#가족</a></button>
			<button class="button"><a href="#">#친구</a></button>
			<button class="button"><a href="#">#흑돼지</a></button>
			<button class="button"><a href="#">#고기국수</a></button>
		</div>
		<div class="list">
			<div class="page"></div>
		</div>
		<a href="#header" class="goTop">TOP</a>
	</div>
</body>
<script type="text/javascript">
var tagType = "";
var tagValue = "";
var root = "${root}";
var pageNumber = 1;


function updateList() {
	$.ajax({
		type : "POST",
		url : root + "/food/listAjax.go",
		data : { "tagType" : tagType, "tagValue" : tagValue, "pageNumber" : pageNumber},
		dataType : "json",
		success : function (data) {
			var cont = "";
			var image = "${root}/resources/css/list.jpg";
			for (var i = 0; i < data.length; i++) {
				var foodCode = "${root}/food/read.go?foodCode="+data[i].foodCode;
				/* cont += "<div style='background-image: url(" + data[i].imagePath + ")'>";
				cont += "<span>"+data[i].foodName+"</span><span>"+data[i].foodMenu+"</span>";
				cont += "</div>"; */
				
				cont += "<div style='cursor: pointer;' onclick="+"'location.href=\""+foodCode+"\"'>";
					cont += "<div>";
						var err = root + "/resources/css/list.jpg";
						cont += "<img alt='음식 이미지' src='"+root+"/resources/ftp/"+data[i].imageName+"' onerror='this.src=\""+err+"\"'>";
					cont += "</div>";
					cont += "<div>";
						cont += "<strong>"+data[i].foodName+"</strong>";
					cont += "</div>";
					cont += "<div>";
						cont += "<span>"+data[i].foodKind+"</span>";
					cont += "</div>";
					cont += "<div>";
						cont += "<span>";
						if (data[i].foodMenu != null) {
							cont += data[i].foodMenu;
						}
						cont += "</span>";
						cont += "</div>";	
						cont += "<div>";
						cont += "<span>";
						if (data[i].foodAddr != null) {
							cont += data[i].foodAddr;
						}
						cont += "</span>";
						cont += "</div>";
					cont += "</div>";
				cont += "</div>"
			}
			if (pageNumber == 1) {
				$(".list").html(cont);
			} else {
				$(".list").append(cont);
			}
		},
		error: function (request, status, error) {
			var str = 'code: '+request.status+'\n';
			str += 'message: ' + request.responseText+'\n';
			str += 'error: ' + error;
			alert(str);
		}
	});
}

$(function() {
	updateList();
});

$('button').click(function() {
	tagValue = $(this).find('a').text().substring(1);
	if (tagValue == "푸드트럭" || tagValue == "중문" || tagValue == "성산" || tagValue == "김녕" || tagValue == "애월" || tagValue == "함덕" || tagValue == "협재") {
		tagType = "area";
	} else if (tagValue == "흑돼지" || tagValue == "고기국수" || tagValue == "전복") {
		tagType = "menu";
	} else if (tagValue == "데이트" || tagValue == "가족" || tagValue == "친구" || tagValue == "여유" || tagValue == "전망") {
		tagType = "tag";
	}
	else {
		tagType = "read";
		tagValue = "refresh";
	}
	pageNumber = 1;
	updateList();
});

$(window).scroll(function(){
	if($(window).scrollTop() >= $(document).height()-$(window).height()){
		pageNumber++;
		updateList();
	}
})
</script>
</html>