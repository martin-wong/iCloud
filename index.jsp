<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>iCloud云网盘</title>
		<!--Bootstrap-->
		<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="css/hover.css" />
		<style type="text/css">
			. {
				font-size: 25px;
			}
			
			.container {
				padding-right: 80px;
				padding-top: 200px
			}
			
			.container h2 {
				max-width: 500px;
				padding: 15px;
				margin: 0 auto;
			}
			.input-group{
				max-width: 800px;
				padding: 15px;
				margin: 0 auto;
			}
		</style>
	</head>

	<body>
		<nav class="nav nav-pills" role="navigation">
			<li role="presentation">
				<a class="hvr-underline-from-left" href="${pageContext.request.contextPath}/requestin.action">登录</a>
			</li>
			<li role="presentation">
				<a class="hvr-underline-from-left" href="${pageContext.request.contextPath}/requestup.action">注册</a>
			</li>
			<li role="presentation">
				<a class="hvr-underline-from-left" href="${pageContext.request.contextPath}/autologin.action?user_name=${user_name}">我的主页</a>
			</li>
			<li role="presentation">
				<a class="hvr-underline-from-leftt" href="${pageContext.request.contextPath}/help.jsp">帮助</a>
			</li>
		</nav>
		<!-- /input-group -->
		<div class="container form-group-lg">
			<form action="${pageContext.request.contextPath}/searchfile.action" method="post">
				<h2>Log in to upload files to iCloud !</h2>
				<div class="input-group">
					<input type="text" placeholder="Search" class="form-control" />
					<span class="input-group-btn btn-group-lg">
						<input type="button" id="" value="sumbit" class="btn btn-primary"/>
					</span>
				</div>
			</form>
		</div>
		<!--如果要使用Bootstrap的JS插件，则必须引入jQuery-->
		<script src="js/jquery-3.1.1.min.js"></script>
		<script src="js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
	</body>

</html>