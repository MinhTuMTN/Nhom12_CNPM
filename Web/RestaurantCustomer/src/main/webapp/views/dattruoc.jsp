<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>The Moon Restaurant</title>
<link rel="icon" href="view/logo_small.png" />

<link rel="stylesheet"
	href="https://unpkg.com/swiper@7/swiper-bundle.min.css" />

<!-- font awesome cdn link  -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
<c:url var="styleSheet" value="/views/style.css"></c:url>
<link rel="stylesheet" type="text/css" href="${styleSheet }" />
</head>

<body>
	<!-- header section starts  -->

	<header class="header">
		<a href="#" class="logo"> <i class="fas fa-solid fa-moon"></i> The
			Moon Restaurant
		</a>

		<div class="nav-main">
			<ul class="nav-main--ul">
				<li class="nav-main--ul-li"><c:url var="styleSheet"
						value="/views/home.jsp"></c:url> <a href="${styleSheet }">Trang
						chủ</a></li>
				<li class="nav-main--ul-li"><a href="#">Menu chính</a></li>
				<li class="nav-main--ul-li"><c:url var="styleSheet"
						value="/dat-truoc"></c:url> <a href="${styleSheet }">Đặt bàn</a></li>
				<li class="nav-main--ul-li"><a href="#">Liên hệ</a></li>
			</ul>
		</div>
	</header>

	<!--Body section starts-->
	<!-- features section starts  -->

	<section class="features">
		<div class="box-container">
			<div class="box">
				<h3>The Moon xin được phục vụ quý khách tại:</h3>
				<a href="#" class="links"> <i class="fas fa-home"></i> Số 1 Võ
					Văn Ngân, Phường Linh Chiểu, Thủ Đức, thành phố Hồ Chí Minh
				</a> <a href="#" class="links"> <i class="fas fa-phone"></i>
					+111-222-3333
				</a> <a href="#" class="links"> <i class="fas fa-envelope"></i>
					contact@themoon.vn
				</a> <a href="#" class="links"> <i class="fas fa-calendar"></i> Thứ
					2 - Chủ Nhật: từ 7h30 đến 22h00
				</a>
			</div>

			<div class="box">
				<form action="" method="post" onsubmit="return validateForm()">
					<h3></h3>
					<label>Họ tên :</label> <br /> <input type="text" id=""
						name="hoTen" placeholder="Nguyễn Văn A" required="required" /> <br />
					<label>Số điện thoại : </label> <br /> <input type="text" id=""
						name="soDienThoai" placeholder="0987654123" required="required"
						pattern="[0][0-9]{9}" />
					<div class="grid-container">
						<div class="grid-item">
							<label>Ngày sinh : </label> <br /> <input type="date"
								id="dateOfBirth" name="ngaySinh" required="required">
						</div>
						<div class="grid-item">
							<label>Giới tính : </label> <br /> <label><input
								type="radio" name="GioiTinh" id="nu" checked="checked"
								value="true" /> Nữ</label> <label><input type="radio"
								name="GioiTinh" id="nam" value="false" /> Nam</label>
						</div>
						<div class="grid-item">
							<label>Chọn ngày tháng check-in : </label> <br /> <input
								type="date" id="dateOfCheckIn" name="ngayCheckIn" required />
						</div>
						<div class="grid-item">
							<label>Chọn thời gian check-in : </label> <br /> <input
								type="time" id="" name="thoiGianCheckIn" min="07:30" max="21:30"
								value="07:30" required />
						</div>
						<div class="grid-item">
							<label>Số lượng người : </label> <br /> <input type="number"
								id="chooseSoLuongNguoi" name="soLuongNguoi" min="1" value="1"
								required />
						</div>
						<div class="grid-item">
							<label>Bàn : </label> <br /> <select name="soBan" id="SoBan"
								onchange="updateInput(this.options[this.selectedIndex])"
								required>
								<c:forEach var="ban" items="${listBan }">
									<option value="${ban.maBan }" soCho=${ban.soLuongGheToiDa }>${ban.loaiBan }
										- ${ban.maBan } - ${ban.soLuongGheToiDa } chỗ</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<button type="submit">Đặt bàn</button>
				</form>
			</div>
		</div>
	</section>

	<!-- features section ends -->
	<!--Body section ends-->

	<!-- footer section starts  -->

	<section class="footer"></section>

	<!-- footer section ends -->
	<script>
		var date = new Date();
		var day = ("0" + date.getDate()).slice(-2);
		var month = ("0" + (date.getMonth() + 1)).slice(-2);
		var today = date.getFullYear() + "-" + (month) + "-" + (day);
		
		document.getElementById("dateOfCheckIn").setAttribute('min', today);
		document.getElementById("dateOfBirth").setAttribute('max', today);

		document.getElementById("dateOfBirth").value = today;
		document.getElementById("dateOfCheckIn").value = today;
		function updateInput(ish) {
			var soChoToiDa = ish.getAttribute("soCho");
			document.getElementById("chooseSoLuongNguoi").max = soChoToiDa;
		}
		function validateForm() {
			alert("Đặt bàn thành công");
			return true;
		}
		updateInput(document.getElementById("SoBan").options[0]);
	</script>
</body>
</html>
