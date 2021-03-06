<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
<title>PhysioClinic</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
body {
	overflow-y: hidden;
}
</style>
</head>
<body style="overflow-y: auto;">
	<t:template>

		<div class="d-flex justify-content-center">
			<img  src="resources/images/rehab.jpg" style="opacity: 0.2;
  filter: alpha(opacity=20);" />
		</div>

	</t:template>

</body>
</html>
