<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="list_view">
		<c:forEach varStatus="num" begin="0" end="7" step="1" var="dto"
			items="${ReviewView_List}">
			<c:choose>
				<c:when test="${empty dto.tvc_path}">
					<c:choose>
						<c:when test="${empty dto.cr_path}">
							<c:set var="path" value="${dto.default_path}"></c:set>
						</c:when>
						<c:otherwise>
							<c:set var="path" value="${dto.cr_path}"></c:set>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:set var="path" value="${dto.tvc_path}"></c:set>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${num.index < 4}">
					<c:if test="${num.index == 0 }">
						<div>
					</c:if>
					<a href="TripReviewRead?tv_no=${dto.tv_no}"><div class="view_item"
							style="background-image : url('${path}');">
							<div class="view_back"></div>
							<div class="view_content">
								<div><p>
									<span class="view_title">${dto.tv_title}</span>
								</p></div>
								<hr/>
								<p>
									<span class="view_teamid">${dto.tv_tname}</span><span
										class="view_date"><fmt:formatDate
											value="${dto.tv_date}" pattern="yyyy/MM/dd" /></span>
								</p>
								<p>
									<span class="view_count"><span></span>${dto.tv_count}</span>
								</p>
							</div>
						</div></a>
					<c:if test="${empty dto or num.index == 3}">
	</div>
	</c:if>
	</c:when>
	<c:otherwise>
		<c:if test="${num.index == 4 }">
			<div>
		</c:if>
		<a href="TripReviewRead?tv_no=${dto.tv_no}"><div class="view_item"
				style="background-image : url('${path}');">
				<div class="view_back"></div>
				<div class="view_content">
					<div><p>
						<span class="view_title">${dto.tv_title}</span>
					</p></div>
					<hr />
					<p>
						<span class="view_teamid">${dto.tv_tname}</span><span
							class="view_date"><fmt:formatDate value="${dto.tv_date}"
								pattern="yyyy/MM/dd" /></span>
					</p>
					<p>
						<span class="view_count"><span></span>${dto.tv_count}</span>
					</p>
				</div>
			</div></a>
		<c:if test="${empty dto or num.index == 7}">
			</div>
		</c:if>
	</c:otherwise>
	</c:choose>
	</c:forEach>
	</div>
</body>
</html>