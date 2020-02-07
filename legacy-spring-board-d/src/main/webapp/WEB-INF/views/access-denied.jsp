<%@ page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>

<!DOCTYPE html>
<html lang="ko">

	<head>
		<meta charset="utf-8">

		<title>Access Denied</title>

	</head>

	<body>
		<h1>Access Denied</h1>
	
		<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
		<script type="text/javascript">
			$(function() {
				alert('페이지를 볼 수 있는 권한이 없습니다.');
				location.href="/legacy-spring-board-d/"
			})
		</script>
	</body>

</html>
