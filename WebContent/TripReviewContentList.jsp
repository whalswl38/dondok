<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<div class="dayContentList">
	<c:forEach var="dto" items="${trcList}">
		<div class="dayRead" data-tvc_no ="${dto.tvc_no}" data-tvc_reviewid = "${dto.tvc_reviewid}">
			<div class="dayLeft">
				<div class="markerImg">
					<span></span>
				</div>
				<div></div>
			</div>
			<div class="line"></div>
			<div class="dayInfoBar">
				<div class="dayInfoBox">
					<div class="dayInfoHeader">
						<a> <span><span
								style="font-weight: 400; font-size: 10pt; color: #bbb; padding-right: 10px;">
								<c:choose>
									<c:when
										test="${dto.tvc_reviewid == 0}">
										<c:out value="메모"></c:out>
									</c:when>
									<c:otherwise>
										<c:out value="리뷰"></c:out>
									</c:otherwise>
								</c:choose></span>${dto.tvc_title}</span> <span></span>
						</a>
					</div>
					<div class="dayInfoContents">
						<div>
							<p>
								<span style="font-weight:600;">${dto.tvc_userid}</span>
								<span style="color: #565656;font-weight: 100;margin-left: 20px;"><fmt:formatDate
							value="${dto.tvc_date}" pattern="yyyy/MM/dd"/></span>
								<c:if test="${dto.tvc_userid eq user.m_id}">
									<a class="dayDelete">삭제</a>
								</c:if>
							</p>
						</div>
						<div style="padding: 25px 15px 25px 15px;">
						${dto.tvc_contents}
						<c:if test="${dto.tvc_reviewid > 0}">
							<div><a href="CategoryReviewRead?cr_no=${dto.tvc_reviewid}">원문 리뷰 보기...</a></div>
						</c:if>
						</div>
					</div>
				</div>
				<div>
					<img src="./images/board_icon/trangle.svg" />
				</div>
			</div>
		</div>
	</c:forEach>
</div>