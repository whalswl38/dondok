package com.trip.controller.review;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.select.Elements;

import com.trip.biz.categoryreview.CategoryReviewBiz;
import com.trip.biz.categoryreview.CategoryReviewBizImpl;
import com.trip.biz.comment.CategoryCommentBiz;
import com.trip.biz.comment.CategoryCommentBizImpl;
import com.trip.biz.comment.TripCommentBiz;
import com.trip.biz.comment.TripCommentBizImpl;
import com.trip.biz.otherBoard.AlarmBiz;
import com.trip.biz.otherBoard.AlarmBizImpl;
import com.trip.biz.otherBoard.FavoriteBiz;
import com.trip.biz.otherBoard.FavoriteBizImpl;
import com.trip.biz.review.ReviewReadBiz;
import com.trip.biz.review.ReviewReadBizImpl;
import com.trip.biz.review.TripReviewBiz;
import com.trip.biz.review.TripReviewBizImpl;
import com.trip.biz.review.TripReviewContentsBiz;
import com.trip.biz.review.TripReviewContentsBizImpl;
import com.trip.biz.review.TripReviewViewBiz;
import com.trip.biz.review.TripReviewViewBizImpl;
import com.trip.dto.categoryreview.CategoryReviewDto;
import com.trip.dto.comment.CategoryCommentDto;
import com.trip.dto.comment.TripCommentDto;
import com.trip.dto.member.MemberLoginDto;
import com.trip.dto.otherBoard.AlarmDto;
import com.trip.dto.otherBoard.FavoriteDto;
import com.trip.dto.review.RouteSelectDto;
import com.trip.dto.review.TeamDto;
import com.trip.dto.review.TeamMemberDto;
import com.trip.dto.review.TripReviewContentsDto;
import com.trip.dto.review.TripReviewDto;
import com.trip.util.Crawler;
import com.trip.util.KaKaoApi;
import com.trip.util.ReviewUtil;

/**
 * Servlet implementation class TripReviewServlet
 * 
 * 
 * TripReview + TripReviewContents
 * 
 */
@WebServlet({ "/MainList","/TripReviewList", "/TripReviewView", "/RestaurantReviewList", "/RestaurantReviewView",
		"/RoomsReviewList", "/RoomsReviewView", "/TouristReviewList", "/TouristReviewView", "/CategoryReviewWrite",
		"/CategoryReviewWriteRes", "/CateTmpImagesUpload", "/CateTmpImagesDelete", "/CateTmpDelete",
		"/ReviewCateSearchList", "/kakaoPlaceList", "/kakaoPlaceMainImgSearch", "/CategoryReviewRead",
		"/CategoryReviewDelete", "/CategoryReviewModify", "/CategoryReviewModifyRes", "/metaDateCrawling",
		"/CommentList", "/CommentCount", "/CommentWrite", "/CommentDelete", "/CommentModify",
		"/FavoriteInsert","/FavoriteDelete","/sessionTest","/TripReviewRead", "/RouteSelect", "/RouteInfo", "/TripReviewContent", "/SelectMyReviewList",
		"/ContentsInsert","/ContentsDelete","/TeamMember","/TripReviewModify","/TripReviewWrite", "/TripeReviewWirteRes"})
public class ReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ReviewServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		
		request.getSession().setMaxInactiveInterval(20*60);
		
		String uri = request.getRequestURI();
		
		String serverPath = getServletContext().getRealPath("/");

		if(uri.endsWith("MainList")) {
			
			listGo(request, response);
			
		} else if (uri.endsWith("TripReviewList")) {
			
			listGo(request, response, "TripReviewView");

		} else if (uri.endsWith("RestaurantReviewList")) {

			listGo(request, response, "RestaurantReviewView");

		} else if (uri.endsWith("RoomsReviewList")) {

			listGo(request, response, "RoomsReviewView");

		} else if (uri.endsWith("TouristReviewList")) {

			listGo(request, response, "TouristReviewView");

		} else if (uri.endsWith("TripReviewView")) {

			listView(request, response, new TripReviewViewBizImpl());

		} else if (uri.endsWith("RestaurantReviewView")) {

			listView(request, response, new CategoryReviewBizImpl(), "2");

		} else if (uri.endsWith("RoomsReviewView")) {

			listView(request, response, new CategoryReviewBizImpl(), "1");

		} else if (uri.endsWith("TouristReviewView")) {

			listView(request, response, new CategoryReviewBizImpl(), "3");

		} else if (uri.endsWith("CategoryReviewWrite")) {
			
			if(sessionUser(request) != null) { categoryReviewWrite(request, response); }
			else {notAccess(response);}
			
		} else if (uri.endsWith("CategoryReviewWriteRes")) {

			if(sessionUser(request) != null) { categoryReviewWriteRes(request, response, "category_review");} 
			else {notAccess(response);}
			
		} else if (uri.endsWith("CateTmpImagesUpload")) {

			if(sessionUser(request) != null) { tmpImagesUpload(request, response, "category_review"); }
			else {notAccess(response);}
			
		} else if (uri.endsWith("CateTmpImagesDelete")) {

			if(sessionUser(request) != null) { tmpImagesDelete(request, response, "category_review"); }
			else {notAccess(response);}
			
		} else if (uri.endsWith("CateTmpDelete")) {

			if(sessionUser(request) != null) { tmpDirDelete(request, response, "category_review"); }
			else { notAccess(response); }
			
		} else if (uri.endsWith("ReviewCateSearchList")) {

			if(sessionUser(request) != null) {
				request.setAttribute("category", request.getParameter("category"));
				go(request, response, "CategoryPlaceSearch.jsp");
			} else { notAccess(response); }
			
		} else if (uri.endsWith("kakaoPlaceList")) {
			
			if(sessionUser(request) != null) { searchPlaceList(request, response);}
			else {notAccess(response);}
			
		} else if (uri.endsWith("kakaoPlaceMainImgSearch")) {
			
			kakaoPlaceMainImgSearch(request, response);
			
		} else if (uri.endsWith("CategoryReviewRead")) {
			
			categoryReviewRead(request, response);

		} else if (uri.endsWith("CategoryReviewDelete")) {

			if(sessionUser(request) != null) categoryReviewDelete(request, response);
			else notAccess(response);
			
		} else if (uri.endsWith("CategoryReviewModify")) {
			
			if(sessionUser(request) != null) {
				CategoryReviewBiz biz = new CategoryReviewBizImpl();

				request.setAttribute("dto", biz.selectOne(Integer.parseInt(request.getParameter("cr_no"))));

				go(request, response, "CategoryReviewModify.jsp");
			} else notAccess(response);

		} else if (uri.endsWith("CategoryReviewModifyRes")) {

			if(sessionUser(request) != null) categoryReviewModifyRes(request, response, "category_review");
			else notAccess(response);
			
		} else if (uri.endsWith("metaDateCrawling")) {

			metaDateGet(request, response);

		} else if (uri.endsWith("CommentList")) {

			commentList(request, response);

		} else if (uri.endsWith("CommentCount")) {
			commentCount(request, response);

		} else if (uri.endsWith("CommentWrite")) {
			
			if(sessionUser(request) != null) commentWrite(request, response);
			else notAccess(response);
			
		} else if (uri.endsWith("CommentDelete")) {
			
			if(sessionUser(request) != null) commentDelete(request, response);
			else notAccess(response);
			
		} else if (uri.endsWith("CommentModify")) {
			
			if(sessionUser(request) != null) commentModify(request, response);
			else notAccess(response);
			
		} else if (uri.endsWith("FavoriteInsert")) {
			
			if(sessionUser(request) != null) favoriteInsert(request, response);
			else notAccess(response);
			
		} else if (uri.endsWith("FavoriteDelete")) {
			if(sessionUser(request) != null) favoriteDelete(request, response);
			else notAccess(response);
			
		} else if (uri.endsWith("TripReviewRead")) {
			tripReviewRead(request, response);
			
		} else if (uri.endsWith("RouteSelect")) {
			
			routeSelect(request, response);
			
		} else if (uri.endsWith("TripReviewContent")) {
			
			tripReviewContent(request, response);
			
		} else if (uri.endsWith("RouteInfo")) {
			
			routeInfo(request, response);
			
		} else if (uri.endsWith("SelectMyReviewList")) {
			
			if(sessionUser(request) != null) selectMyReviewList(request, response);
			else notAccess(response);
			
		} else if(uri.endsWith("ContentsInsert")) {
			
			if(sessionUser(request) != null) contentsInsert(request, response);
			else notAccess(response);
			
		} else if(uri.endsWith("ContentsDelete")) {
			
			if(sessionUser(request) != null) contentsDelete(request, response);
			else notAccess(response);
			
		} else if(uri.endsWith("TeamMember")) {
			
			teamMember(request, response);
			
		} else if(uri.endsWith("TripReviewModify")) {
		
			if(sessionUser(request) != null)  tripReviewModify(request, response);
			else notAccess(response);
			
		} else if (uri.endsWith("TripReviewWrite")){
		
			if(sessionUser(request) != null)  tripReviewWrite(request, response);
			else notAccess(response);
			
		} else if (uri.endsWith("TripeReviewWirteRes")) {
			
			if(sessionUser(request) != null)  tripeReviewWirteRes(request, response);
			else notAccess(response);
			
		} else if (uri.endsWith("sessionTest")) {
			
			HttpSession session = request.getSession();
			MemberLoginDto mDto = new MemberLoginDto();
			mDto.setM_id("user1");
			session.setAttribute("user", mDto);
			if (ReviewUtil.tmpImgDirDelete(serverPath)) {
				System.out.println("[ 크롤링용  임시디렉터리 삭제 ]");
			}
			tmpDirDelete(request, response, "category_review");
			tmpDirDelete(request, response, "trip_review");
		}

	}

	// 명소 3 | 숙소1 | 맛집 2
	
	protected void tripeReviewWirteRes(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String tv_title = request.getParameter("tv_title");
		int tv_teamid = Integer.parseInt(request.getParameter("tv_teamid"));
		
		TripReviewDto trDto = new TripReviewDto();
		
		trDto.setTv_teamid(tv_teamid);
		trDto.setTv_title(tv_title);
		
		TripReviewBiz trBiz = new TripReviewBizImpl();
		
		int res = trBiz.insert(trDto);
		
		if (res > 0) {
			
			trDto = trBiz.selectTeam(tv_teamid);
			
			int tv_no = trDto.getTv_no();
			
			response.sendRedirect("TripReviewRead?tv_no="+tv_no);
			
		} else {
			
			response.sendRedirect("TripReviewList");
		}
	}
	
	protected void tripReviewWrite(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		ReviewReadBiz rrBiz = new ReviewReadBizImpl();
		String tm_uid = sessionUser(request).getM_id();
		
		System.out.println(tm_uid);
		
		List<TeamDto> trwList = rrBiz.tripReviewWrite(tm_uid);
		List<List<TeamMemberDto>> tmlList = new ArrayList<List<TeamMemberDto>>(); 
		
		for (TeamDto outDto : trwList) {
			List<TeamMemberDto> tmList = rrBiz.teamMemberSelect(outDto.getT_id());
			tmlList.add(tmList);
		}
		
		request.setAttribute("tmlList", tmlList);
		request.setAttribute("trwList", trwList);
		
		go(request, response, "TripReviewWrite.jsp");
	}
	
	protected void tripReviewModify(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		int tv_no = Integer.parseInt(request.getParameter("tv_no"));
		String tv_title = request.getParameter("tv_title");
		
		System.out.println(tv_no + tv_title);
		
		TripReviewDto tDto = new TripReviewDto();
		tDto.setTv_title(tv_title);
		tDto.setTv_no(tv_no);
		
		TripReviewBiz tBiz = new TripReviewBizImpl();
		
		int res = tBiz.modify(tDto);
		
		response.getWriter().append(""+res);
	}
	
	
	@SuppressWarnings("unchecked")
	protected void teamMember(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int tm_tid = Integer.parseInt(request.getParameter("tm_tid"));
		
		ReviewReadBiz rrBiz = new ReviewReadBizImpl();
		
		List<TeamMemberDto> tmList = rrBiz.teamMemberSelect(tm_tid);
		
		JSONObject jObj1 = new JSONObject();
		JSONArray jArray = new JSONArray();
		for(int i = 0 ; i < tmList.size() ; i ++) {
			JSONObject jObj2 = new JSONObject();
			jObj2.put("tm_uid", tmList.get(i).getTm_uid());
			jArray.add(jObj2);
		}
		jObj1.put("user", jArray);
		
		response.getWriter().append(jObj1.toJSONString());
		
	}
	
	protected void contentsDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		int tvc_no = Integer.parseInt(request.getParameter("tvc_no"));
		TripReviewContentsBiz tvcBiz = new TripReviewContentsBizImpl();
		int res = tvcBiz.delete(tvc_no);
		response.getWriter().append(""+res);
		
	}
	
	protected void contentsInsert(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		System.out.println("dddd");
		TripReviewContentsDto tvcDto = new TripReviewContentsDto();
		
		int tvc_reviewid = 0;
		if(request.getParameter("tvc_reviewid") !=null) {
			tvc_reviewid = Integer.parseInt(request.getParameter("tvc_reviewid"));
		}
		tvcDto.setTvc_reviewid(tvc_reviewid);
		String tvc_contents = null;
		if(request.getParameter("tvc_contents") != null) {
			tvc_contents = request.getParameter("tvc_contents");
		}
		tvcDto.setTvc_contents(tvc_contents);
		String tvc_path = null;
		if(request.getParameter("tvc_path") != null) {
			tvc_contents = request.getParameter("tvc_path");
		}
		tvcDto.setTvc_path(tvc_path);
		
		int tvc_tvno = Integer.parseInt(request.getParameter("tvc_tvno"));
		int tvc_day = Integer.parseInt(request.getParameter("tvc_day"));
		int tvc_routeid = Integer.parseInt(request.getParameter("tvc_routeid"));
		
		String tvc_userid = sessionUser(request).getM_id();
		
		tvcDto.setTvc_day(tvc_day);
		tvcDto.setTvc_tvno(tvc_tvno);
		tvcDto.setTvc_routeid(tvc_routeid);
		tvcDto.setTvc_userid(tvc_userid);
		
		System.out.println(tvcDto);
		
		TripReviewContentsBiz tvcBiz = new TripReviewContentsBizImpl();
		int res = tvcBiz.insert(tvcDto);
		
		response.getWriter().append(""+res);
		
	}
	
	
	@SuppressWarnings("unchecked")
	protected void selectMyReviewList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String cr_id = sessionUser(request).getM_id();
		String cr_placeid = request.getParameter("cr_placeid");

		CategoryReviewBiz cBiz = new CategoryReviewBizImpl();
		
		List<CategoryReviewDto> cList = cBiz.selectMyReview(cr_id, cr_placeid);
		
		JSONObject jObj = new JSONObject();
		
		JSONArray jArray = new JSONArray();
		
		/*
			String  cr_id, cr_title, cr_contents, cr_delflag, cr_category, cr_path, cr_placeid;
			int cr_no,  cr_count;
			Date cr_date;
		*/
		for(int i = 0 ; i < cList.size(); i++) {
			
			JSONObject jObj2 = new JSONObject();
			jObj2.put("cr_id", cList.get(i).getCr_id());
			jObj2.put("cr_title", cList.get(i).getCr_title());
			jObj2.put("cr_contents", cList.get(i).getCr_contents());
			jObj2.put("cr_delflag", cList.get(i).getCr_delflag());
			jObj2.put("cr_category", cList.get(i).getCr_category());
			jObj2.put("cr_path", cList.get(i).getCr_path());
			jObj2.put("cr_placeid", cList.get(i).getCr_placeid());
			jObj2.put("cr_no", cList.get(i).getCr_no());
			jObj2.put("cr_count", cList.get(i).getCr_count());
			jObj2.put("cr_date", "\""+cList.get(i).getCr_date()+"\"");
			jObj2.put("cr_commentCount",cList.get(i).getCr_commentCount());
			System.out.println(jObj2.toString());
			jArray.add(jObj2);
		
		}
		
		jObj.put("cList", jArray);
		response.getWriter().append(jObj.toJSONString());
		
	}
	protected void routeInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		routeInfo(request, response, 1, 15);
	}
	protected void routeInfo(HttpServletRequest request, HttpServletResponse response, int page, int size) throws IOException, ServletException {
		
		JSONObject Jobj = null;
		
		JSONParser paser = new JSONParser();
		
		JSONArray Jarray = null;
		
		String routeid = request.getParameter("routeid");
		
		System.out.println("[장소 ID] "+ routeid);
		String des = Crawler.crawlingMeta("https://place.map.kakao.com/" + routeid, "meta[property=og:description]").get(0).attr("content");
		
		System.out.println("[장소 주소] " + des);
		
		try {
			Jobj = (JSONObject) paser.parse(KaKaoApi.localSearch("address.json", des, 1, 30));
			System.out.println("[장소 검색 결과]" + Jobj.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Jarray = (JSONArray) Jobj.get("documents");
		
		Jobj = (JSONObject) Jarray.get(0);
		String x = (String) Jobj.get("x");
		String y = (String) Jobj.get("y");
		
		System.out.println("[장소 X,Y 좌표] " + x +", " + y);
		
		String title = Crawler.crawlingMeta("https://map.kakao.com/?itemId=" + routeid, "meta[property=og:title]").get(0).attr("content");
		
		System.out.println("[장소ID 타이틀] " + title);
		
		try {
			Jobj = (JSONObject) paser.parse(KaKaoApi.localSearch("keyword.json?x="+x+"&y="+y+"&radius=20000&", title, page, size));
			System.out.println("[장소 키워드 상세 검색 결과]" + Jobj.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Jarray = (JSONArray) Jobj.get("documents");
		JSONObject resultJSON = null;
		
		for(int i = 0 ; i < Jarray.size() ; i++) {
			JSONObject jOut = (JSONObject) Jarray.get(i);
			
			if(jOut.get("id").equals(routeid)) {
				resultJSON = (JSONObject) Jarray.get(i);
				System.out.println("[장소 ID 결과]" + resultJSON.toString());
				break;
			}
			
		}
		response.getWriter().append(resultJSON.toString());
		
	}
	protected void routeSelect(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		ReviewReadBiz rrBiz = new ReviewReadBizImpl();
		
		int rs_tno = Integer.parseInt(request.getParameter("rs_tno"));
		
		System.out.println("[rs_tno] " + rs_tno);
		
		String rs_accdate = request.getParameter("rs_accdate");
		
		System.out.println("[rs_accdate] " + rs_accdate);
		
		RouteSelectDto rsDto = rrBiz.teamRouteSelect(rs_tno, rs_accdate);
		
		System.out.println(rsDto);
		
		response.getWriter().append(""+rsDto);
		
		
	}

	protected void tripReviewContent(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		int tvc_day = Integer.parseInt(request.getParameter("tvc_day"));
		int tvc_tvno = Integer.parseInt(request.getParameter("tvc_tvno"));
		int tvc_routeid = Integer.parseInt(request.getParameter("tvc_routeid"));
		
		System.out.println("["+tvc_day+"|"+tvc_tvno+"|"+tvc_routeid+"]");
		
		TripReviewContentsBiz trcBiz = new TripReviewContentsBizImpl();
		
		List<TripReviewContentsDto> trcList = trcBiz.selectList(tvc_tvno, tvc_day, tvc_routeid);
		
		request.setAttribute("trcList", trcList);
		
		System.out.println("[trcList] " + trcList);
		
		go(request, response, "TripReviewContentList.jsp");
		
	}
	
	protected void tripReviewRead(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	
		int tv_no = Integer.parseInt(request.getParameter("tv_no"));
		
		TripReviewBiz trBiz = new TripReviewBizImpl();
		
		trBiz.countUpdate(tv_no);
		
		TripReviewDto trDto = trBiz.select(tv_no); // 팀 일정 후기 정보 호출
		
		int t_id = trDto.getTv_teamid();
		
		
		
		ReviewReadBiz rrBiz = new ReviewReadBizImpl();
		
		TeamDto tDto = rrBiz.teamSelect(t_id); // 선택한 팀 정보 호출
		
		int tm_tid = tDto.getT_id();
		
		List<TeamMemberDto> tmList = rrBiz.teamMemberSelect(tm_tid); // 맴버 호출
		
		HttpSession session = request.getSession();
		if(session.getAttribute("user") !=null) {
			favoriteCheck(request, response, tv_no, 0);
		}
		
		response.getWriter().append(trDto + "<br/>" + tDto + "<br/>" + tmList);
		
		request.setAttribute("trDto", trDto);
		request.setAttribute("tDto", tDto);
		request.setAttribute("tmList", tmList);
		
		System.out.println(tmList);
		
		go(request, response, "TripReviewRead.jsp");
	}
	
	
	protected void favoriteDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		FavoriteBiz biz = new FavoriteBizImpl();
		String f_id = (String) sessionUser(request).getM_id();
		int f_pno = Integer.parseInt(request.getParameter("f_pno"));
		int res = biz.favoriteDelete(f_id, f_pno);
		response.getWriter().append(""+res);
	}
	
	protected void favoriteInsert(HttpServletRequest request, HttpServletResponse response) throws IOException {
		FavoriteBiz biz = new FavoriteBizImpl();
		String f_id = (String) sessionUser(request).getM_id();
		int f_pno = Integer.parseInt(request.getParameter("f_pno"));
		int f_cate = Integer.parseInt(request.getParameter("f_cate"));
		FavoriteDto fDto = new FavoriteDto();
		fDto.setF_cate(f_cate);
		fDto.setF_id(f_id);
		fDto.setF_pno(f_pno);
		int res = biz.favoriteInsert(fDto);
		response.getWriter().append(""+res);
	}
	
	protected void favoriteCheck(HttpServletRequest request, HttpServletResponse response, int f_pno, int f_cate) throws IOException {
		FavoriteBiz biz = new FavoriteBizImpl();
		String f_id = (String) sessionUser(request).getM_id();
		request.setAttribute("favoriteCheck", biz.favoriteCheck(f_id, f_pno, f_cate));
		
	}

	protected void categoryReviewRead(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		CategoryReviewBiz biz = new CategoryReviewBizImpl();
		int cr_no = Integer.parseInt(request.getParameter("cr_no"));

		request.setAttribute("dto", biz.selectOne(cr_no));
		
		HttpSession session = request.getSession();
		if(session.getAttribute("user") !=null) {
			favoriteCheck(request, response, cr_no, 1);
		}
		
		go(request, response, "CategoryReviewRead.jsp");
	}
	
	
	protected MemberLoginDto sessionUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (MemberLoginDto)session.getAttribute("user");
	}
	
	protected void notAccess(HttpServletResponse response) throws IOException {
		response.getWriter().append("<script>alert('올바르지 못한 접근 입니다.'); history.back(-1);</script>");
	}
	
	protected void commentDelete(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String selectComment = request.getParameter("selectComment");
		int rv_no = Integer.parseInt(request.getParameter("rv_no"));
		int rv_crno = Integer.parseInt(request.getParameter("rv_crno"));

		if (selectComment.equals("trip")) {
			TripCommentBiz biz = new TripCommentBizImpl();
			int res = biz.delete(rv_no);
			if (res > 0) {
				response.getWriter().append("" + biz.commentCount(rv_crno));
			}
		} else {
			CategoryCommentBiz biz = new CategoryCommentBizImpl();
			int res = biz.delete(rv_no);
			if (res > 0) {
				response.getWriter().append("" + biz.commentCount(rv_crno));
			}
		}
	}

	protected void commentModify(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String selectComment = request.getParameter("selectComment");
		String rv_content = request.getParameter("rv_content");
		int rv_no = Integer.parseInt(request.getParameter("rv_no"));
		int rv_crno = Integer.parseInt(request.getParameter("rv_crno"));

		if (selectComment.equals("trip")) {
			
			TripCommentBiz biz = new TripCommentBizImpl();
			TripCommentDto dto = new TripCommentDto();

			dto.setRv_content(rv_content);
			dto.setRv_crno(rv_crno);
			dto.setRv_no(rv_no);

			int res = biz.update(dto);
			if (res > 0) {
				response.getWriter().append("" + biz.commentCount(rv_crno));
			}
			
			
		} else {
			CategoryCommentBiz biz = new CategoryCommentBizImpl();
			CategoryCommentDto dto = new CategoryCommentDto();

			dto.setRv_content(rv_content);
			dto.setRv_crno(rv_crno);
			dto.setRv_no(rv_no);

			int res = biz.update(dto);
			if (res > 0) {
				response.getWriter().append("" + biz.commentCount(rv_crno));
			}
		}
	}

	protected void commentWrite(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String selectComment = request.getParameter("selectComment");
		String rv_content = request.getParameter("rv_content");
		String rv_id = (String) sessionUser(request).getM_id();
		
		int rv_pno = 0;
		if (request.getParameter("rv_pno") != null) {
			rv_pno = Integer.parseInt(request.getParameter("rv_pno"));
		}

		int rv_crno = Integer.parseInt(request.getParameter("rv_crno"));

		System.out.println("[rv_content] " + rv_content);
		System.out.println("[rv_id] " + rv_id);
		System.out.println("[rv_crno] " + rv_crno);
		System.out.println("[rv_pno] " + rv_pno);

		if (selectComment.equals("trip")) {

			AlarmBiz aBiz = new AlarmBizImpl();
			AlarmDto aDto = new AlarmDto();
			aDto.setAl_id(request.getParameter("al_id"));
			aDto.setAl_rno(rv_crno);
			aDto.setAl_cate(2);
			int aRes = aBiz.insert(aDto);

			if (aRes > 0) {
				TripCommentBiz biz = new TripCommentBizImpl();
				TripCommentDto dto = new TripCommentDto();

				if (rv_pno != 0) {
					dto.setRv_pno(rv_pno);
				}
				dto.setRv_content(rv_content);
				dto.setRv_crno(rv_crno);
				dto.setRv_id(rv_id);

				int res = biz.insert(dto);
				if (res > 0) {
					response.getWriter().append("" + biz.commentCount(rv_crno));
				}
			} else {
				response.getWriter().append("<script>alert('올바르지 못한 접근 입니다.');</script>");
			}
			
		} else {

			AlarmBiz aBiz = new AlarmBizImpl();
			AlarmDto aDto = new AlarmDto();
			aDto.setAl_id(request.getParameter("al_id"));
			aDto.setAl_rno(rv_crno);
			aDto.setAl_cate(2);
			int aRes = aBiz.insert(aDto);

			if (aRes > 0) {
				CategoryCommentBiz biz = new CategoryCommentBizImpl();
				CategoryCommentDto dto = new CategoryCommentDto();

				if (rv_pno != 0) {
					dto.setRv_pno(rv_pno);
				}
				dto.setRv_content(rv_content);
				dto.setRv_crno(rv_crno);
				dto.setRv_id(rv_id);

				int res = biz.insert(dto);
				if (res > 0) {
					response.getWriter().append("" + biz.commentCount(rv_crno));
				}
			} else {
				response.getWriter().append("<script>alert('올바르지 못한 접근 입니다.');</script>");
			}
		}
	}

	
	
	
	protected void commentCount(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String selectComment = request.getParameter("selectComment");
		int rv_crno = Integer.parseInt(request.getParameter("rv_crno"));

		if (selectComment.equals("trip")) {
			TripCommentBiz biz = new TripCommentBizImpl();
			int res = biz.commentCount(rv_crno);
			response.getWriter().append("" + res);
			
		} else {
			CategoryCommentBiz biz = new CategoryCommentBizImpl();
			int res = biz.commentCount(rv_crno);
			response.getWriter().append("" + res);
		}
	}

	protected void commentList(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String selectComment = request.getParameter("selectComment");
		int rv_crno = Integer.parseInt(request.getParameter("rv_crno"));
		int page = Integer.parseInt(request.getParameter("page"));

		System.out.println(rv_crno + "|" + page);

		if (selectComment.equals("trip")) {
			TripCommentBiz biz = new TripCommentBizImpl();
			request.setAttribute("comment_list", biz.selectList(rv_crno, page));
		} else {
			CategoryCommentBiz biz = new CategoryCommentBizImpl();
			request.setAttribute("comment_list", biz.selectList(rv_crno, page));
		}
		go(request, response, "ReviewComment.jsp");
	}

	protected void metaDateGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String url = request.getParameter("url");
		String selector = request.getParameter("selector");

		Elements els = Crawler.crawlingMeta(url, selector);

		String resStr = els.get(0).attr("content");
		
		response.getWriter().append(resStr);

	}

	protected void categoryReviewModifyRes(HttpServletRequest request, HttpServletResponse response, String reviewPath)
			throws IOException, ServletException {

		String cr_contents = request.getParameter("cr_contents");
		String cr_id = (String) sessionUser(request).getM_id();
		int cr_no = Integer.parseInt(request.getParameter("cr_no"));

		String now_cr_path = request.getParameter("cr_path");

		String serverPath = getServletContext().getRealPath("/");

		CategoryReviewBiz biz = new CategoryReviewBizImpl();

		CategoryReviewDto dtoSelect = biz.selectOne(Integer.parseInt(request.getParameter("cr_no")));
		String category = request.getParameter("category");

		if (dtoSelect.getCr_id().equals(cr_id)) {
			String old_cr_path = ReviewUtil.searchDir(serverPath, reviewPath, "tmp_" + cr_id);
			old_cr_path = (old_cr_path == null) ? null : old_cr_path;

			String cr_path = null;

			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String no = format.format(date);

			if (ReviewUtil.renameDir(serverPath, reviewPath, cr_id, no + "_" + cr_id))
				cr_path = ReviewUtil.searchDir(serverPath, reviewPath, no + "_" + cr_id);

			System.out.println("[old_cr_path] " + old_cr_path);
			System.out.println("[cr_path] " + cr_path);

			CategoryReviewDto dto = new CategoryReviewDto();
			String cr_category = null;
			if (category.equals("명소")) {
				cr_category = "3";
			} else if (category.equals("숙소")) {
				cr_category = "1";
			} else if (category.equals("맛집")) {
				cr_category = "2";
			}

			String cr_title = request.getParameter("cr_title");
			String cr_placeid = request.getParameter("cr_placeid");

			dto.setCr_no(cr_no);
			dto.setCr_category(cr_category);
			dto.setCr_title(cr_title);
			dto.setCr_placeid(cr_placeid);
			dto.setCr_contents(cr_contents);
			dto.setCr_path(now_cr_path);

			int res = biz.update(dto, cr_path, old_cr_path, serverPath);

			if (res == 1) {
				if (cr_category == "1") {
					response.sendRedirect("RoomsReviewList");
				} else if (cr_category == "2") {
					response.sendRedirect("RestaurantReviewList");
				} else if (cr_category == "3") {
					response.sendRedirect("TouristReviewList");
				}
			}
		} else {
			String where = null;
			if (category.equals("숙소")) {
				where = "RoomsReviewList";
			} else if (category.equals("맛집")) {
				where = "RestaurantReviewList";
			} else if (category.equals("명소")) {
				where = "TouristReviewList";
			} else {
				where = "TripReviewList";
			}
			response.getWriter().append("<script>alert('부적절한 접근입니다.'); location.href ='" + where + "';</script>");
		}
	}

	protected void categoryReviewDelete(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int cr_no = Integer.parseInt(request.getParameter("cr_no"));
		String user = (String) sessionUser(request).getM_id();
		String where = null;

		CategoryReviewBiz biz = new CategoryReviewBizImpl();

		CategoryReviewDto dto = biz.selectOne(cr_no);

		if (dto.getCr_category().equals("숙소")) {
			where = "RoomsReviewList";
		} else if (dto.getCr_category().equals("맛집")) {
			where = "RestaurantReviewList";
		} else if (dto.getCr_category().equals("명소")) {
			where = "TouristReviewList";
		} else {
			where = "TripReviewList";
		}

		if (dto.getCr_id().equals(user)) {
			biz.delete(cr_no);
			response.getWriter().append("<script>alert('글이 성공적으로 삭제되었습니다.'); location.href ='" + where + "';</script>");
		} else {
			response.getWriter().append("<script>alert('부적절한 접근입니다.'); location.href ='" + where + "';</script>");
		}
	}

	protected void kakaoPlaceMainImgSearch(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		kakaoPlaceMainImgSearch(request, response,0);
	}
	protected void kakaoPlaceMainImgSearch(HttpServletRequest request, HttpServletResponse response, int num)
			throws IOException {

//		KaKao REST API
		String imgObjJson = KaKaoApi.search("image", request.getParameter("place_name"), 1, num+1);

		JSONObject jsonObj = null;

		JSONParser paser = new JSONParser();
		try {
			jsonObj = (JSONObject) paser.parse(imgObjJson);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray array = (JSONArray) jsonObj.get("documents");
		jsonObj = (JSONObject) array.get(num);

		String targetURL = (String) jsonObj.get("image_url");
		String serverPath = getServletContext().getRealPath("/");
		String user = null;
		
		if(sessionUser(request) == null) {
			user = null;
		} else {
			user = sessionUser(request).getM_id();
		}
		
		String path = ReviewUtil.tmpImgSave(serverPath, targetURL, user );
		
		if(path.equals("false")) {
		
			kakaoPlaceMainImgSearch(request, response, num+1);
		
		} else {
			
			response.getWriter().append(path);
		
		}
//		카카오 페이지 크롤링
//		String path = Crawler.crawlingMeta("https://place.map.kakao.com/"+request.getParameter("id"), "meta[property=og:image]").get(0).attr("content");

//		System.out.println("[MainImgPaht] " + path);
		
	}

	protected void searchPlaceList(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String page = request.getParameter("page");
		String category = request.getParameter("category");
		String out = null;
		if (page == null) {
			out = KaKaoApi.searchPlace(request.getParameter("keyword"), category);
		} else {
			out = KaKaoApi.searchPlace(request.getParameter("keyword"), category, page);
		}
		response.getWriter().append(out);
	}

	protected void go(HttpServletRequest request, HttpServletResponse response, String path)
			throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);
	}

	
	protected void listGo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("keyword") != null) {
			request.setAttribute("keyword", request.getParameter("keyword"));
		}
		go(request, response, "MainList.jsp");
	}
	
	protected void listGo(HttpServletRequest request, HttpServletResponse response, String reviewLocation)
			throws ServletException, IOException {
		if (request.getParameter("keyword") != null) {
			request.setAttribute("keyword", request.getParameter("keyword"));
		}
		request.setAttribute("reviewLocation", reviewLocation);
		go(request, response, "ReviewList.jsp");

	}

	protected void listView(HttpServletRequest request, HttpServletResponse response, Object biz)
			throws ServletException, IOException {
		listView(request, response, biz, null);
	}

	protected void listView(HttpServletRequest request, HttpServletResponse response, Object biz, String category)
			throws ServletException, IOException {

		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String keyword = null;
		String m_id = null;
		if(request.getSession().getAttribute("user") != null) {
			m_id = sessionUser(request).getM_id();
		}
		
		List<?> list = null;

		if (request.getParameter("keyword") != null || !(request.getParameter("keyword").equals(""))) {
			keyword = request.getParameter("keyword");
		}

		if (biz instanceof TripReviewViewBizImpl) {

			TripReviewViewBiz tripReviewViewBiz = (TripReviewViewBizImpl) biz;
			list = tripReviewViewBiz.selectList(start, end, keyword, m_id);

		} else if (biz instanceof CategoryReviewBizImpl) {

			CategoryReviewBiz categoryReviewViewBiz = (CategoryReviewBizImpl) biz;
			list = categoryReviewViewBiz.selectList(start, end, keyword, category, m_id);

		}

		if (list.size() != 0) {
			System.out.println("[Category] " + category + " - ListRendering");
			request.setAttribute("ReviewView_List", list);
			if (category != null) {
				go(request, response, "CategoryReviewView.jsp");
			} else {
				go(request, response, "TripReviewView.jsp");
			}
			
		} else {
			response.getWriter().append("<div style='display:block;'><span class='notList'>조회 한 게시글 없습니다.<span><div>");
		}

	}

	protected void categoryReviewWrite(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String from = request.getParameter("category");

		if (from.equals("RestaurantReviewView")) {
			request.setAttribute("category", "맛집");
		} else if (from.equals("TouristReviewView")) {
			request.setAttribute("category", "명소");
		} else if (from.equals("RoomsReviewView")) {
			request.setAttribute("category", "숙소");
		}

		go(request, response, "CategoryReviewWrite.jsp");
	}

	protected void categoryReviewWriteRes(HttpServletRequest request, HttpServletResponse response, String reviewPath)
			throws IOException, ServletException {

		String cr_contents = request.getParameter("cr_contents");
		String cr_id = (String) sessionUser(request).getM_id();;

		String serverPath = getServletContext().getRealPath("/");

		CategoryReviewBiz biz = new CategoryReviewBizImpl();

		String old_cr_path = ReviewUtil.searchDir(serverPath, reviewPath, "tmp_" + cr_id);
		old_cr_path = (old_cr_path == null) ? "" : old_cr_path;

		String cr_path = null;

		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String no = format.format(date);

		if (ReviewUtil.renameDir(serverPath, reviewPath, cr_id, no + "_" + cr_id))
			cr_path = ReviewUtil.searchDir(serverPath, reviewPath, no + "_" + cr_id);

		System.out.println("[old_cr_path] " + old_cr_path);
		System.out.println("[cr_path] " + cr_path);

		CategoryReviewDto dto = new CategoryReviewDto();

		String category = request.getParameter("category");
		String cr_category = null;
		if (category.equals("명소")) {
			cr_category = "3";
		} else if (category.equals("숙소")) {
			cr_category = "1";
		} else if (category.equals("맛집")) {
			cr_category = "2";
		}

		String cr_title = request.getParameter("cr_title");
		String cr_placeid = request.getParameter("cr_placeid");

		dto.setCr_category(cr_category);
		dto.setCr_title(cr_title);
		dto.setCr_path(old_cr_path);
		dto.setCr_placeid(cr_placeid);
		dto.setCr_contents(cr_contents);
		dto.setCr_id(cr_id);

		int res = biz.insert(dto, cr_path);

		if (res == 1) {
			if (cr_category == "1") {
				response.sendRedirect("RoomsReviewList");
			} else if (cr_category == "2") {
				response.sendRedirect("RestaurantReviewList");
			} else if (cr_category == "3") {
				response.sendRedirect("TouristReviewList");
			}
		}

	}

	protected void tmpImagesUpload(HttpServletRequest request, HttpServletResponse response, String reviewPath)
			throws IOException {

		ReviewUtil util = new ReviewUtil();

		String user = (String) sessionUser(request).getM_id();

		String serverPath = getServletContext().getRealPath("/");

		String outPath = util.tmpImagesUpload(request, serverPath, reviewPath, user);

		response.getWriter().append(outPath);

	}

	protected void tmpImagesDelete(HttpServletRequest request, HttpServletResponse response, String reviewPath)
throws IOException {

		String url = request.getParameter("url");

		String user = (String) sessionUser(request).getM_id();

		String serverPath = getServletContext().getRealPath("/");

		if (ReviewUtil.tmpImagesDelete(serverPath, reviewPath, user, url))
			response.getWriter().append("1");
		else
			response.getWriter().append("0");
	}

	protected void tmpDirDelete(HttpServletRequest request, HttpServletResponse response, String reviewPath)
			throws IOException {

		String user = (String) sessionUser(request).getM_id();

		String serverPath = getServletContext().getRealPath("/");

		if (ReviewUtil.tmpDirDelete(serverPath, reviewPath, user))
			response.getWriter().append("1");
		else
			response.getWriter().append("0");

	}

}
