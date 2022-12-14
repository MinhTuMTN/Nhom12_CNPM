<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>The Moon Restaurant</title>
    <link rel="icon" href="view/logo_small.png" />

    <link
      rel="stylesheet"
      href="https://unpkg.com/swiper@7/swiper-bundle.min.css"
    />

    <!-- font awesome cdn link  -->
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
    />
	<c:url var="styleSheet" value="/views/style.css"></c:url>
    <link rel="stylesheet" type="text/css" href="${styleSheet }" />
  </head>

  <body>
    <!-- header section starts  -->

    <header class="header">
      <a href="#" class="logo">
        <i class="fas fa-solid fa-moon"></i> The Moon Restaurant
      </a>
      
      <div class="nav-main">
        <ul class="nav-main--ul">
          <li class="nav-main--ul-li">
          	<c:url var="styleSheet" value="/views/home.jsp"></c:url>
            <a href="${styleSheet }">Trang chủ</a>
          </li>
          <li class="nav-main--ul-li">
            <a href="#">Menu chính</a>
          </li>
          <li class="nav-main--ul-li">
          	<c:url var="styleSheet" value="/dat-truoc"></c:url>
            <a href="${styleSheet }">Đặt bàn</a>
          </li>
          <li class="nav-main--ul-li">
            <a href="#">Liên hệ</a>
          </li>
        </ul>
      </div>
    </header>

    
     <div class="main-index">
      <div class="main-index--hello" style="color: black">
        Chào mừng quý khách đến với <br />The Moon Restaurant
      </div>
    </div>
    
    <section class="footer"></section>