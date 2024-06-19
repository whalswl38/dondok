package trip.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.trip.biz.review.TripReviewBiz;
import com.trip.biz.review.TripReviewBizImpl;
import com.trip.dto.member.MemberLoginDto;

import trip.biz.PlanBiz;
import trip.dao.AccountDao;
import trip.dao.LocationDao;
import trip.dao.MemberDao;
import trip.dao.RegionDao;
import trip.dao.RouteDao;
import trip.dao.RouteSelectDao;
import trip.dao.TeamDao;
import trip.dao.TeamMemberDao;
import trip.dao.VoRoomDao;
import trip.dto.AccountDto;
import trip.dto.LocationDto;
import trip.dto.RegionDto;
import trip.dto.RouteDto;
import trip.dto.RouteSelectDto;
import trip.dto.TeamDto;
import trip.dto.TeamMemberDto;
import trip.dto.VoRoomDto;

/**
 * Servlet implementation class TeamController
 */
@WebServlet("/TeamMemberController")
public class TeamMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TeamMemberController() {
		super();
	}

	protected MemberLoginDto sessionUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (MemberLoginDto)session.getAttribute("user");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String command = request.getParameter("command");
		TeamDao teamDao = new TeamDao();
		TeamMemberDao teammemberDao = new TeamMemberDao();
		LocationDao locationDao = new LocationDao();
		MemberDao memberDao = new MemberDao();
		AccountDao accountDao=new AccountDao();
		RegionDao regionDao=new RegionDao();
		VoRoomDao voroomDao=new VoRoomDao();
		RouteDao routeDao=new RouteDao();
		PlanBiz planBiz = new PlanBiz();
		RouteSelectDao routeselectDao = new RouteSelectDao();
		
		HttpSession session = request.getSession();
		if(sessionUser(request) != null) session.setAttribute("my_id", sessionUser(request).getM_id());
		else response.sendRedirect("NotAccess.jsp");
		
		
		if (command.equals("createTeam")) {//새로운 일정짜기(본인이 리더)
			
			String leader = (String)session.getAttribute("my_id");
			String days ="0";
			int res = teamDao.createTeam(leader, days);// 리더아이디, 일차
			int team_id=teamDao.selectTeamId(leader);
			session.setAttribute("team_id", team_id);//만들어진 팀 아이디 사용
			if (res > 0) {
				teammemberDao.insertMember(leader,(int)session.getAttribute("team_id"));
				dispatch("invitation_form.jsp", request, response);
			}
		} else if (command.equals("deleteTeam")){
			
			
			int tid;
			if(request.getParameter("tid")==null) {
				tid=(int)session.getAttribute("team_id");
			}else {
				tid=Integer.parseInt(request.getParameter("tid"));
			}
			teamDao.deleteTeam(tid);
			dispatch("TeamMemberController?command=tripSearch", request, response);
			
		} else if (command.equals("idCheck")) {//초대 아이디 체크
			
			String id = request.getParameter("id");
			if(id.length()>0) {
			String res = teammemberDao.idChk(id);
			if (res != null) {// id가 존재할때
				if (teammemberDao.insertMember(id,(int)session.getAttribute("team_id")) > 0) {// 팀에 uid가 없다면
					response.getWriter().write(id);//초대가능
				}else {
					response.getWriter().write("이미초대");
				}
			}else {
				response.getWriter().write("비회원");
			}
			}else {
				response.getWriter().write("비입력");
			}
		}else if (command.equals("complete_0")) {//여행 제목, 계좌 등록
			int t_id = (int)session.getAttribute("team_id");
			
			String schedule_name = request.getParameter("schedule_name");
			String acc_holder=request.getParameter("acc_holder");
			String acc_bank=request.getParameter("acc_bank");
			String acc_num=request.getParameter("acc_num");
			
			teamDao.updateTeamName(schedule_name, t_id);
			AccountDto dto=new AccountDto(t_id,acc_holder,acc_bank,acc_num);
			accountDao.insertAcc(dto);
			
			response.sendRedirect("TeamMemberController?command=upStage&stage=1");//setRegion
		}else if(command.equals("addRegion")){//지역 추가
			int tid;
			if(request.getParameter("tid")==null) {
				tid=(int)session.getAttribute("team_id");
			}else {
				tid=Integer.parseInt(request.getParameter("tid"));
			}
			String day=request.getParameter("day");
			String region=request.getParameter("region");
			RegionDto rdto=new RegionDto(tid,day,region);
			int res=regionDao.insertRegion(rdto);
			if(res==0) {
				regionDao.updateRegion(rdto);
			}
			List<RegionDto> list=regionDao.selectRegion(tid);
			
			JSONObject jsonObj=new JSONObject();
			HashMap<String,Object> map=new HashMap<String,Object>();
			for(int i=0;i<list.size();i++) {
				map.put(list.get(i).getR_day()+"", list.get(i).getR_location());
			jsonObj.putAll(map);
			}
			map.put("days", teamDao.selectOneTeam(tid).getT_days());
			jsonObj.putAll(map);
			
			response.getWriter().write(jsonObj.toJSONString());
		}else if (command.equals("addlist")) {//장소 추가 페이지
				
				int loc_tid = (int)session.getAttribute("team_id");
				String loc_mid = (String)session.getAttribute("my_id");
				String loc_day = request.getParameter("loc_day");

				LocationDto locDto = new LocationDto(loc_tid, loc_mid, loc_day);
				RegionDto rDto=new RegionDto(loc_tid,loc_day);
			
				List<LocationDto> loc_list = locationDao.selectTeamLoc(locDto);
				request.setAttribute("loc_list", loc_list);
				request.setAttribute("loc_day", loc_day);
				request.setAttribute("region", regionDao.selectOneRegion(rDto));
				request.setAttribute("mileage", Integer.parseInt(accountDao.selectAcc(loc_tid).getAcc_mileage()));
				request.setAttribute("days", teamDao.selectOneTeam(loc_tid).getT_days());
				request.setAttribute("teammember", planBiz.Memberids(loc_tid));//리더제외
				request.setAttribute("l_name", planBiz.teamLeadername(loc_tid));
				request.setAttribute("m_names", planBiz.teamMembernames(loc_tid));// list
				request.setAttribute("region_day1", regionDao.selectRegion(loc_tid).get(0).getR_location());//첫째날의 장소
				request.setAttribute("l_id",teamDao.selectOneTeam(loc_tid).getT_leaderId());
			
				dispatch("addlist.jsp", request, response);
				
		} else if (command.equals("addplace")) {//장소 담기
			
			String data=request.getParameter("data");
			String[] arr_data=data.split(",");
			
			LocationDto locDto= new LocationDto(
					Integer.parseInt(arr_data[0]),
					arr_data[1].equals("AD5") ? 1 : arr_data[1].equals("FD6") ? 2 : 3,
					arr_data[2],
					arr_data[3],
					arr_data[4],
					arr_data[5],
					arr_data[6],
					(int)session.getAttribute("team_id"),
					(String)session.getAttribute("my_id"),
					arr_data[7]
					);	
			locationDao.insertPlace(locDto);
		
			List<LocationDto> selectTeamLoc=locationDao.selectTeamLoc(locDto);
			JSONObject jsonObj=new JSONObject();
			HashMap<String,Object> map2=new HashMap<String,Object>();
			for(int i=0;i<selectTeamLoc.size();i++) {
				HashMap<String,Object> map=new HashMap<String,Object>();
				map.put("m_id", selectTeamLoc.get(i).getLoc_mid());
				map.put("loc_id", selectTeamLoc.get(i).getLoc_id());
				map.put("loc_name", selectTeamLoc.get(i).getLoc_name());
				map.put("loc_day", selectTeamLoc.get(i).getLoc_day());
				map.put("loc_cate",(selectTeamLoc.get(i).getLoc_cate()==1)?"숙박":(selectTeamLoc.get(i).getLoc_cate()==2)?"음식점":"명소");
				map.put("loc_addr", selectTeamLoc.get(i).getLoc_addr());
				map.put("loc_url", selectTeamLoc.get(i).getLoc_url());
				
				map2.put("location"+i, map);
			}
			jsonObj.putAll(map2);
			response.getWriter().write(jsonObj.toJSONString());
			
		} else if (command.equals("delplace")) {//장소 삭제
			int loc_id = Integer.parseInt(request.getParameter("loc_id"));
			String loc_day = request.getParameter("loc_day");
			int loc_tid = (int)session.getAttribute("team_id");

			LocationDto locDto = new LocationDto(loc_id, loc_tid, loc_day);
			locationDao.deletePlace(locDto);
			
			List<LocationDto> selectTeamLoc=locationDao.selectTeamLoc(locDto);
			JSONObject jsonObj=new JSONObject();
			HashMap<String,Object> map2=new HashMap<String,Object>();
			for(int i=0;i<selectTeamLoc.size();i++) {
				HashMap<String,Object> map=new HashMap<String,Object>();
				map.put("m_id", selectTeamLoc.get(i).getLoc_mid());
				map.put("loc_id", selectTeamLoc.get(i).getLoc_id());
				map.put("loc_name", selectTeamLoc.get(i).getLoc_name());
				map.put("loc_day", selectTeamLoc.get(i).getLoc_day());
				map.put("loc_cate",(selectTeamLoc.get(i).getLoc_cate()==1)?"숙박":(selectTeamLoc.get(i).getLoc_cate()==2)?"음식점":"명소");
				map.put("loc_addr", selectTeamLoc.get(i).getLoc_addr());
				map.put("loc_url", selectTeamLoc.get(i).getLoc_url());
				
				map2.put("location"+i, map);
			}
			jsonObj.putAll(map2);
			response.getWriter().write(jsonObj.toJSONString());
			}else if(command.equals("updateList")){
				String loc_day = request.getParameter("loc_day");
				int loc_tid = (int)session.getAttribute("team_id");
				
				List<LocationDto> selectTeamLoc=locationDao.selectTeamLoc(new LocationDto(loc_tid, loc_day));
				JSONObject jsonObj=new JSONObject();
				HashMap<String,Object> map2=new HashMap<String,Object>();
				for(int i=0;i<selectTeamLoc.size();i++) {
					HashMap<String,Object> map=new HashMap<String,Object>();
					map.put("m_id", selectTeamLoc.get(i).getLoc_mid());
					map.put("loc_id", selectTeamLoc.get(i).getLoc_id());
					map.put("loc_name", selectTeamLoc.get(i).getLoc_name());
					map.put("loc_day", selectTeamLoc.get(i).getLoc_day());
					map.put("loc_cate",(selectTeamLoc.get(i).getLoc_cate()==1)?"숙박":(selectTeamLoc.get(i).getLoc_cate()==2)?"음식점":"명소");
					map.put("loc_addr", selectTeamLoc.get(i).getLoc_addr());
					map.put("loc_url", selectTeamLoc.get(i).getLoc_url());
					
					map2.put("location"+i, map);
				}
				jsonObj.putAll(map2);
				response.getWriter().write(jsonObj.toJSONString());
				
			}
			else if(command.equals("setRooms")){
			int loc_tid;
			if(request.getParameter("tid")!=null) {
				loc_tid=Integer.parseInt(request.getParameter("tid"));
			}else {
				loc_tid=(int)session.getAttribute("team_id");
			}
			String loc_mid=(String)session.getAttribute("my_id");
			String loc_day=request.getParameter("day");
			List<LocationDto> loc_rooms_list = locationDao.selectTeamRooms(loc_tid,loc_day);
			VoRoomDto voroomsDto=new VoRoomDto(loc_tid,(String)session.getAttribute("my_id"),loc_day);
			int loc_id=voroomDao.selectVoRoom(voroomsDto);
			
			request.setAttribute("days", teamDao.selectOneTeam(loc_tid).getT_days());
			request.setAttribute("loc_rooms_list", loc_rooms_list);
			request.setAttribute("vote_id", loc_id);

			request.setAttribute("mileage", Integer.parseInt(accountDao.selectAcc(loc_tid).getAcc_mileage()));
			request.setAttribute("teammember", planBiz.Memberids(loc_tid));//리더제외
			request.setAttribute("l_name", planBiz.teamLeadername(loc_tid));
			request.setAttribute("m_names", planBiz.teamMembernames(loc_tid));// list
			request.setAttribute("l_id",teamDao.selectOneTeam(loc_tid).getT_leaderId());
			

			VoRoomDto voroomDto=new VoRoomDto(loc_tid,loc_mid,loc_id,loc_day);
			int voCnt=voroomDao.selectVoRoomCnt(voroomDto);
			if(Integer.parseInt(teamDao.selectOneTeam(loc_tid).getT_days())==voCnt) {
				request.setAttribute("next", "true");
			}else {
				request.setAttribute("next", "false");
			}
			dispatch("setRooms.jsp", request, response);
		} else if(command.equals("setRooms_bnt")){
			int loc_tid=(int)session.getAttribute("team_id");
			String loc_day=request.getParameter("loc_day");//1일차
			
			VoRoomDto voroomsDto=new VoRoomDto(loc_tid,(String)session.getAttribute("my_id"),loc_day);
			int loc_id=voroomDao.selectVoRoom(voroomsDto);
			
			List<LocationDto> loc_rooms_list = locationDao.selectTeamRooms(loc_tid,loc_day);
			JSONObject jsonObj=new JSONObject();
			
			for(int i=0;i<loc_rooms_list.size();i++) {
				int id=loc_rooms_list.get(i).getLoc_id();
				String title=loc_rooms_list.get(i).getLoc_name();
				String url=loc_rooms_list.get(i).getLoc_url();
				String y=loc_rooms_list.get(i).getLoc_y();
				String x=loc_rooms_list.get(i).getLoc_x();
				
				HashMap<String,Object> map=new HashMap<String,Object>();
				HashMap<String,Object> map2=new HashMap<String,Object>();
				
				map.put("id", id);
				map.put("title", title);
				map.put("url", url);
				map.put("y", y);
				map.put("x", x);
				if(id==loc_id) {
					map.put("vote",id);
				}else {
					map.put("vote",0);
				}
				map2.put("room"+i, map);
				jsonObj.putAll(map2);
			}
			
			response.getWriter().write(jsonObj.toJSONString());
			
		}else if(command.equals("setRooms_vote")) {
			int loc_tid = (int)session.getAttribute("team_id");
			String loc_mid=(String)session.getAttribute("my_id");
			int loc_id = Integer.parseInt(request.getParameter("loc_id"));
			String loc_day = request.getParameter("loc_day");
			
			VoRoomDto voroomDto=new VoRoomDto(loc_tid,loc_mid,loc_id,loc_day);
			
			int res=voroomDao.insertVoRoom(voroomDto);
			if(res==0) {
			voroomDao.updateVoRoom(voroomDto);
			}
			
			int voCnt=voroomDao.selectVoRoomCnt(voroomDto);
			if(Integer.parseInt(teamDao.selectOneTeam(loc_tid).getT_days())==voCnt)
			response.getWriter().write("next");
		}else if(command.equals("setRoom_vote_res")) {
			int loc_tid = (int)session.getAttribute("team_id");
			List<VoRoomDto> roomVoteList=voroomDao.resVoRoom(loc_tid);
			request.setAttribute("roomVoteList", roomVoteList);
			request.setAttribute("roomVoteList_info", planBiz.resVoRoom_info(roomVoteList));

			int[] arr=new int[roomVoteList.size()];
			for(int i=0;i<roomVoteList.size();i++) {
				arr[Integer.parseInt(roomVoteList.get(i).getVoroom_day())-1]++;	
			}
			List<String> n_uniqueList=new ArrayList<String>();
			for(int i=0;i<roomVoteList.size();i++) {
				if(arr[i]>1)
					n_uniqueList.add((i+1)+"");
			}
			request.setAttribute("n_uniqueList", n_uniqueList);  
			
			dispatch("roomVoteRes.jsp",request,response);
		}else if(command.equals("delRoom_vote_res")) {
			int rooms_tid = (int)session.getAttribute("team_id");
			int rooms_id = Integer.parseInt(request.getParameter("rooms_id"));
			String rooms_day = request.getParameter("rooms_day");
			
			VoRoomDto voroomsDto=new VoRoomDto(rooms_tid,rooms_id,rooms_day);
			voroomDao.deleteVoRoom(voroomsDto);
		}else if(command.equals("setRoute")){
	         int loc_tid= (int)session.getAttribute("team_id");
	         String loc_mid=(String)session.getAttribute("my_id");
	         request.setAttribute("days",teamDao.selectOneTeam(loc_tid).getT_days());
	         
	         LocationDto locDto=new LocationDto();
	         locDto.setLoc_tid(loc_tid);
	         locDto.setLoc_day(request.getParameter("day"));
	         
	         List<LocationDto> loc_list = locationDao.selectTeamLoc(locDto);
	         request.setAttribute("loc_list", loc_list);
	         
	         RouteDto routeDto=new RouteDto(loc_tid,loc_mid,"1");
	         
	         if(routeDao.selectOneRoute(routeDto)!=null) {
	         String route=routeDao.selectOneRoute(routeDto).getR_route(); 
	         String[] arr_route=route.split("\\|");
	         String latlng=""; 
	         for( int x = 0; x < arr_route.length; x++ ){
	            latlng+=" new kakao.maps.LatLng("+
	               locationDao.selectOneLoc(Integer.parseInt(arr_route[x])).getLoc_y().toString()+
	               ","+
	               locationDao.selectOneLoc(Integer.parseInt(arr_route[x])).getLoc_x().toString()+
	               "),";
	            }
	         if (latlng.length() > 0 && latlng.charAt(latlng.length()-1)==',') {
	            latlng = latlng.substring(0, latlng.length()-1);
	             }
	            request.setAttribute("latlng", latlng);
	         }else {
	            request.setAttribute("latlng", null);
	         }
	         
	         int saveCnt=routeDao.selectRouteCnt(new RouteDto(loc_tid,loc_mid));
	         if(Integer.parseInt(teamDao.selectOneTeam(loc_tid).getT_days())==saveCnt) {
	            request.setAttribute("next", "true");
	         }else {
	            request.setAttribute("next", "false");
	         }
	         request.setAttribute("mileage", Integer.parseInt(accountDao.selectAcc(loc_tid).getAcc_mileage()));
	         request.setAttribute("teammember", planBiz.Memberids(loc_tid));//리더제외
	         request.setAttribute("l_name", planBiz.teamLeadername(loc_tid));
	         request.setAttribute("m_names", planBiz.teamMembernames(loc_tid));// list
	         request.setAttribute("l_id",teamDao.selectOneTeam(loc_tid).getT_leaderId());
	         
	         dispatch("setRoute.jsp", request, response);
	      }else if(command.equals("setRoute_bnt")){
			int loc_tid=(int)session.getAttribute("team_id");
			String day=request.getParameter("loc_day");
			
			LocationDto locDto=new LocationDto();
			locDto.setLoc_tid(loc_tid);
			locDto.setLoc_day(day);
			List<LocationDto> loc_list = locationDao.selectTeamLoc(locDto);
			
			JSONObject jsonObj=new JSONObject();
			LinkedHashMap<String,Object> map2=new LinkedHashMap<String,Object>();
			for(int i=0;i<loc_list.size();i++) {
				int id=loc_list.get(i).getLoc_id();
				String title=loc_list.get(i).getLoc_name();
				String url=loc_list.get(i).getLoc_url();
				String y=loc_list.get(i).getLoc_y();
				String x=loc_list.get(i).getLoc_x();
				int cate=loc_list.get(i).getLoc_cate();
				
				HashMap<String,Object> map=new HashMap<String,Object>();
				
				map.put("id", id);
				map.put("title", title);
				map.put("url", url);
				map.put("y", y);
				map.put("x", x);
				map.put("cate",cate);
				
				map2.put("room"+i, map);
			}
			jsonObj.putAll(map2);          
			
			response.getWriter().write(jsonObj.toJSONString());
		}else if(command.equals("setRoute_loading")){
			int loc_tid=(int)session.getAttribute("team_id");
			String day=request.getParameter("loc_day");
			RouteDto routeDto=new RouteDto(loc_tid,(String)session.getAttribute("my_id"),day);		

			JSONObject jsonObj=new JSONObject();
			if(routeDao.selectOneRoute(routeDto)!=null) { 
			String route=routeDao.selectOneRoute(routeDto).getR_route(); 
			String[] arr_route=route.split("\\|");
			LinkedHashMap<String,Object> map2=new LinkedHashMap<String,Object>();
			for( int i = 0; i < arr_route.length; i++ ){
				HashMap<String,Object> map=new HashMap<String,Object>();

				map.put("x",locationDao.selectOneLoc(Integer.parseInt(arr_route[i])).getLoc_x());
				map.put("y",locationDao.selectOneLoc(Integer.parseInt(arr_route[i])).getLoc_y());
				
				map2.put("latlng"+i, map);
				}
			jsonObj.putAll(map2);
			
			response.getWriter().write(jsonObj.toJSONString());
			}else {
				jsonObj.putAll(new HashMap());
				response.getWriter().write(jsonObj.toJSONString());
			}
			}else if(command.equals("setRoute_save")){
			int loc_tid=(int)session.getAttribute("team_id");
			String r_day=request.getParameter("r_day");
			String route=request.getParameter("clickPosition_id");

			RouteDto rdto=new RouteDto(loc_tid,(String)session.getAttribute("my_id"),r_day,route);
			int res=routeDao.insertRoute(rdto);
			if(res==0) {
				routeDao.updateRoute(rdto);
			}
			
			int saveCnt=routeDao.selectRouteCnt(rdto);
			if(Integer.parseInt(teamDao.selectOneTeam(loc_tid).getT_days())==saveCnt)
			response.getWriter().write("next");
		}else if(command.equals("setRoute_clear")){
			int loc_tid=(int)session.getAttribute("team_id");
			String day=request.getParameter("r_day");
			
			LocationDto locDto=new LocationDto();
			locDto.setLoc_tid(loc_tid);
			locDto.setLoc_day(day);
			List<LocationDto> loc_list = locationDao.selectTeamLoc(locDto);
			
			JSONObject jsonObj=new JSONObject();
			LinkedHashMap<String,Object> map2=new LinkedHashMap<String,Object>();
			for(int i=0;i<loc_list.size();i++) {
				int id=loc_list.get(i).getLoc_id();
				String title=loc_list.get(i).getLoc_name();
				String url=loc_list.get(i).getLoc_url();
				String y=loc_list.get(i).getLoc_y();
				String x=loc_list.get(i).getLoc_x();
				int cate=loc_list.get(i).getLoc_cate();
				
				HashMap<String,Object> map=new HashMap<String,Object>();
				
				map.put("id", id);
				map.put("title", title);
				map.put("url", url);
				map.put("y", y);
				map.put("x", x);
				map.put("cate",cate);
				
				map2.put("room"+i, map);
			}
			jsonObj.putAll(map2);          
			
			response.getWriter().write(jsonObj.toJSONString());
		}else if(command.equals("payread")){
			int tid=(int)session.getAttribute("team_id");
			request.setAttribute("accountDto", accountDao.selectAcc(tid));
			request.setAttribute("person", planBiz.tamMemberCount(tid));
			dispatch("payment_leader.jsp", request, response);			
		}else if(command.equals("updateacc")){
			int acc_tid=(int)session.getAttribute("team_id");
			String acc_bank=request.getParameter("acc_bank");
			String acc_num=request.getParameter("acc_num");
			String acc_holder=request.getParameter("acc_holder");
			String acc_price=request.getParameter("acc_price");
			AccountDto accDto=new AccountDto(acc_tid,acc_holder,acc_bank,acc_num,acc_price);
			accountDao.updateAcc(accDto);
			dispatch("TeamMemberController?command=upStage&stage=5", request, response);
		}else if(command.equals("payment")){
			int acc_tid=(int)session.getAttribute("team_id");
			
			AccountDto accDto=accountDao.selectAcc(acc_tid);	
			request.setAttribute("accountDto",accDto);
			//request.setAttribute("person", planBiz.tamMemberCount(acc_tid));
			request.setAttribute("acc_tid",acc_tid);
			System.out.println(acc_tid);
			System.out.println(accDto);
			//request.setAttribute("acc_mid",(String)session.getAttribute("my_id"));
			dispatch("kakaoPay?command=request", request, response);		
		}else if(command.equals("paymentfinished")) {
			int pay_tid=(int)session.getAttribute("team_id");
			String pay_uid = (String)session.getAttribute("my_id");
			TeamMemberDto dto=new TeamMemberDto(pay_tid,pay_uid);
			teammemberDao.updatePayment(dto);
			dispatch("response.jsp?command=close", request, response);
		}
		
		
		
		
		if(command.equals("tripSearch")) {//일정 조회
			
				String my_id = sessionUser(request).getM_id();
				
				teamDao.deleteNull();
				
				List<TeamDto> tList=planBiz.idToTeam(my_id);//개인id속한 팀정보
				List<TeamDto> u_list=new ArrayList<TeamDto>();
				List<TeamDto> c_list=new ArrayList<TeamDto>();
			
				if(tList.size() > 0)
					for(int i=0;i<tList.size();i++) {
						if(tList.get(i) != null) {
							if(tList.get(i).getT_flag() != null && tList.get(i).getT_flag().equals("Y")) {
								c_list.add(tList.get(i));
							}else {
								u_list.add(tList.get(i));
							}
					}
				}
				request.setAttribute("c_list", c_list);
				request.setAttribute("u_list", u_list);
				dispatch("checkTrip.jsp", request, response);
				
			
		}else if(command.equals("deleteTeamMember")) {
			int tid=Integer.parseInt(request.getParameter("tid"));
			String id=(String)session.getAttribute("my_id");
			TeamMemberDto dto=new TeamMemberDto();
			dto.setTm_tid(tid);
			dto.setTm_uid(id);
			teammemberDao.deleteMember(dto);
			dispatch("TeamMemberController?command=tripSearch", request, response);
		}else if (command.equals("del_mem")) {//멤버 삭제
			int t_id = (int)session.getAttribute("team_id");
			
			String id=request.getParameter("id");
			TeamMemberDto dto=new TeamMemberDto();
			dto.setTm_tid(t_id);
			dto.setTm_uid(id);
			teammemberDao.deleteMember(dto);
		
			List<String> teamMembernames=planBiz.teamMembernames(t_id);
			List<String> teamMemberids=planBiz.Memberids(t_id);
		
			JSONObject jsonObj=new JSONObject();
			HashMap<String,Object> map2=new HashMap<String,Object>();
			for(int i=0;i<teamMembernames.size();i++) {
				HashMap<String,Object> map=new HashMap<String,Object>();
				map.put("name", teamMembernames.get(i));
				map.put("id", teamMemberids.get(i));
				map2.put("member"+i, map);
			}
			jsonObj.putAll(map2);
			response.getWriter().write(jsonObj.toJSONString());	
		}else if (command.equals("add_mem")) {//멤버 추가
			int t_id = (int)session.getAttribute("team_id");
			
			String id = request.getParameter("id");
			int stage=Integer.parseInt(request.getParameter("stage"));
			if(id.length()>0) {
			String res = teammemberDao.idChk(id);
			if (res != null) {// id가 존재할때
				if (teammemberDao.insertAddMember(new TeamMemberDto(t_id,id,stage)) > 0) {// 팀에 uid가 없다면
					List<String> teamMembernames=planBiz.teamMembernames(t_id);
					List<String> teamMemberids=planBiz.Memberids(t_id);
				
					JSONObject jsonObj=new JSONObject();
					HashMap<String,Object> map2=new HashMap<String,Object>();
					for(int i=0;i<teamMembernames.size();i++) {
						HashMap<String,Object> map=new HashMap<String,Object>();
						map.put("name", teamMembernames.get(i));
						map.put("id", teamMemberids.get(i));
						map2.put("member"+i, map);
					}
					jsonObj.putAll(map2);
					response.getWriter().write(jsonObj.toJSONString());
				}else {
					response.getWriter().write("이미초대");
				}
			}else {
				response.getWriter().write("비회원");
			}
			}else {
				response.getWriter().write("비입력");
			}
			
			
		}else if (command.equals("upStage")) {
				
			String my_id = (String)session.getAttribute("my_id");
			int tid = (int)session.getAttribute("team_id");
			int stage=Integer.parseInt(request.getParameter("stage"));
			
			int mileage=Integer.parseInt(accountDao.selectAcc(tid).getAcc_mileage());
			
			TeamMemberDto dto=new TeamMemberDto(tid,my_id,stage);
			teammemberDao.upStage(dto);
	
			
			int res=planBiz.upStage(tid,stage);
			if(res>0) {//모두가 참여했다면->마일리지 500원씩..
				switch(stage) {
				
				case 1:
					
					request.setAttribute("team", teamDao.selectOneTeam(tid));
					
					dispatch("selectDay.jsp", request, response);
					break;
				case 15:
					if(my_id.equals(teamDao.selectOneTeam(tid).getT_leaderId())) {
						request.setAttribute("team", teamDao.selectOneTeam(tid));
						dispatch("setRegion.jsp", request, response);
					}else {
						dispatch("waiting2.jsp", request, response);
					}
					break;
				case 2:
					accountDao.upMileage((mileage+500)+"", tid);
					dispatch("TeamMemberController?command=addlist&loc_day=1", request, response);
					break;
				case 3://숙소 정하기
					accountDao.upMileage((mileage+500)+"", tid);
					dispatch("TeamMemberController?command=setRooms&day=1", request, response);
					break;
				case 35://숙소 결정(리더)			
//					if(my_id.equals(teamDao.selectOneTeam(tid).getT_leaderId())) {
//						dispatch("TeamMemberController?command=setRoom_vote_res", request, response);
//					}else {
//						dispatch("waiting2.jsp", request, response);
//					}
//					break;
					List<VoRoomDto> roomVoteList=voroomDao.resVoRoom(tid);
					if(roomVoteList.size()==Integer.parseInt(teamDao.selectOneTeam(tid).getT_days())) {
						dispatch("TeamMemberController?command=upStage&stage=4", request, response);
					}else {	//만약 동률이 나왔다면
					if(my_id.equals(teamDao.selectOneTeam(tid).getT_leaderId())) {
						dispatch("TeamMemberController?command=setRoom_vote_res", request, response);
					}else {
						dispatch("waiting2.jsp", request, response);
					}
					}
					break;
				case 4:
					accountDao.upMileage((mileage+500)+"", tid);
					dispatch("TeamMemberController?command=setRoute&day=1", request, response);
					break;
				case 45://계좌,금액 입력
					if(my_id.equals(teamDao.selectOneTeam(tid).getT_leaderId())) {
						dispatch("TeamMemberController?command=payread", request, response);
					}else {
						dispatch("waiting2.jsp", request, response);
					}
					break;
				case 5:
					accountDao.upMileage((mileage+500)+"", tid);
					dispatch("TeamMemberController?command=payment", request, response);
					break;
				case 6:///완료
					dispatch("TeamMemberController?command=tripSearch", request, response);
					break;
				}
			}else{
				
				if(stage==2) {
					accountDao.upMileage((mileage+500)+"", tid);
					teamDao.upStage(tid, 2);
					dispatch("TeamMemberController?command=addlist&loc_day=1", request, response);
				}else if(stage==4) {
					accountDao.upMileage((mileage+500)+"", tid);
					teamDao.upStage(tid, 4);
					dispatch("TeamMemberController?command=setRoute&day=1", request, response);
					
				}else if(stage==5) {
					accountDao.upMileage((mileage+500)+"", tid);
					teamDao.upStage(tid, 5);
					dispatch("TeamMemberController?command=payment", request, response);
					
				}else {
					dispatch("waiting.jsp", request, response);
				}
			}
			
		}else if(command.equals("go")) {
			
			
			int tid=Integer.parseInt(request.getParameter("tid"));
			session.setAttribute("team_id", tid);
			String my_id = (String)session.getAttribute("my_id");
			int stage=Integer.parseInt(request.getParameter("stage"));
			switch(stage) {
			case 1:
				
				request.setAttribute("team", teamDao.selectOneTeam(tid));
				dispatch("selectDay.jsp", request, response);
				break;
				
			case 15:
				if(my_id.equals(teamDao.selectOneTeam(tid).getT_leaderId())) {
					request.setAttribute("team", teamDao.selectOneTeam(tid));
					dispatch("setRegion.jsp", request, response);
				}else {
					dispatch("waiting2.jsp", request, response);
				}
				break;
			case 2:
				dispatch("TeamMemberController?command=addlist&loc_day=1", request, response);
				break;
			case 3:
				dispatch("TeamMemberController?command=setRooms&day=1", request, response);
				break;
			case 35:
				if(my_id.equals(teamDao.selectOneTeam(tid).getT_leaderId())) {
					dispatch("TeamMemberController?command=setRoom_vote_res", request, response);
				}else {
					dispatch("waiting2.jsp", request, response);
				}
				break;
			case 4:
				dispatch("TeamMemberController?command=setRoute&day=1", request, response);
				break;
			case 45:
				if(my_id.equals(teamDao.selectOneTeam(tid).getT_leaderId())) {
					dispatch("TeamMemberController?command=payread", request, response);
				}else {
					dispatch("waiting2.jsp", request, response);
				}
				break;
			case 5:
				dispatch("TeamMemberController?command=payment", request, response);
				break;
			}
			
		}else if (command.equals("delTeam")){
			
			int tid=Integer.parseInt(request.getParameter("tid"));
			teamDao.deleteTeam(tid);
			dispatch("TeamMemberController?command=tripSearch", request, response);
			
		} else if(command.equals("viewTeamInfo")) {
			      
				  int tid=Integer.parseInt(request.getParameter("tid"));
			      session.setAttribute("team_id", tid);
			      
			      ///////팀정보
			      
			      request.setAttribute("teamInfo", teamDao.selectOneTeam(tid));
			      request.setAttribute("teammemberInfo",teammemberDao.teamMemberList(tid));
			      request.setAttribute("l_name", planBiz.teamLeadername(tid));
			      
			      //request.setAttribute("t_names", planBiz.teamMembernames(tid));
			      
			      request.setAttribute("accInfo", accountDao.selectAcc(tid));
			      
			      ////////지도
			      int loc_tid= (int)session.getAttribute("team_id");
			      String day=request.getParameter("day");
			      
			      // tripReview 조회
			      TripReviewBiz trBiz = new TripReviewBizImpl();
			      int trRes = trBiz.selectTeamCount(tid);
			      if(trRes > 0)
		    	  request.setAttribute("trDto", trBiz.selectTeam(tid));
		    		 
			      
			      
			      request.setAttribute("days",teamDao.selectOneTeam(loc_tid).getT_days());
			      LocationDto locDto=new LocationDto();
			      locDto.setLoc_tid(loc_tid);
			      locDto.setLoc_day(day);
			      
			      //?일자 위치 정보
			      request.setAttribute("loc_list", locationDao.selectTeamLoc(locDto));//음식점+명소
			      request.setAttribute("room_info", planBiz.selectVoRoom(tid,day));
			      
			      if(routeselectDao.selectOneRoute(new RouteSelectDto(loc_tid, loc_tid))!=null) {
			      String routeId=routeselectDao.selectOneRoute(new RouteSelectDto(loc_tid, loc_tid)).getRs_route();
			      String[] arr_routeId=routeId.split("\\|");
			      List<String> routeIds=new ArrayList<String>();
			      for(int i=0;i<arr_routeId.length;i++) {
			         routeIds.add(arr_routeId[i]);
			      }
			         request.setAttribute("clickId", routeIds);
			      }else {
			         request.setAttribute("clickId", null);
			      }
			      
			      dispatch("viewTeamInfo.jsp", request, response);
			
		} else if(command.equals("viewTeamInfo_day")){
			    	  
			    		 
			    		 int loc_tid=(int)session.getAttribute("team_id");
			    		 String day=request.getParameter("loc_day");
			    		 
			    		 LocationDto locDto=new LocationDto();
			    		 locDto.setLoc_tid(loc_tid);
			    		 locDto.setLoc_day(day);
			    		 List<LocationDto> loc_list = locationDao.selectTeamLoc(locDto);
			         
			    		 JSONObject jsonObj=new JSONObject();
			    		 LinkedHashMap<String,Object> map2=new LinkedHashMap<String,Object>();
			    		 
			    		 for(int i=0;i<loc_list.size();i++) {
			    			 
			    			 int id=loc_list.get(i).getLoc_id();
			    			 String title=loc_list.get(i).getLoc_name();
			    			 String url=loc_list.get(i).getLoc_url();
			    			 String y=loc_list.get(i).getLoc_y();
			    			 String x=loc_list.get(i).getLoc_x();
			    			 int cate=loc_list.get(i).getLoc_cate();
			            
			    			 HashMap<String,Object> map=new HashMap<String,Object>();
			            
			    			 map.put("id", id);
			    			 map.put("title", title);
			    			 map.put("url", url);
			    			 map.put("y", y);
			    			 map.put("x", x);
			    			 map.put("cate",cate);
			    			 
			    			 map2.put("room"+i, map);
			    		 }
			         
			    		 HashMap<String,Object> map=new HashMap<String,Object>();
			    		 
			    		 map.put("id", planBiz.selectVoRoom(loc_tid,day).getLoc_id());
			    		 map.put("title",planBiz.selectVoRoom(loc_tid,day).getLoc_name());
			    		 map.put("url", planBiz.selectVoRoom(loc_tid,day).getLoc_url());
			    		 map.put("y",planBiz.selectVoRoom(loc_tid,day).getLoc_y());
			    		 map.put("x",planBiz.selectVoRoom(loc_tid,day).getLoc_x());
			    		 map.put("cate",planBiz.selectVoRoom(loc_tid,day).getLoc_cate());
			         
			    		 map2.put("hotel", map);
			         
			    		 jsonObj.putAll(map2);          
			         
			    		 response.getWriter().write(jsonObj.toJSONString());

		}else if(command.equals("selectRoute_loading")) {
			         int loc_tid=(int)session.getAttribute("team_id");
			         String day=request.getParameter("loc_day");
			         RouteSelectDto routeSelectDto=new RouteSelectDto(loc_tid,Integer.parseInt(day));
			      
			         JSONObject jsonObj=new JSONObject();
			         LinkedHashMap<String,Object> map2=new LinkedHashMap<String,Object>();
			         
			         if(routeselectDao.selectOneRoute(routeSelectDto)!=null) { 
			         String route=routeselectDao.selectOneRoute(routeSelectDto).getRs_route(); 
			         String[] arr_route=route.split("\\|");
			         for( int i = 0; i < arr_route.length; i++ ){
			            HashMap<String,Object> map=new HashMap<String,Object>();
			            map.put("x",locationDao.selectOneLoc(Integer.parseInt(arr_route[i])).getLoc_x());
			            map.put("y",locationDao.selectOneLoc(Integer.parseInt(arr_route[i])).getLoc_y());
			            
			            map2.put("latlng"+i, map);
			         }
			            map2.put("rootId",route);
			         }
			            jsonObj.putAll(map2);
			            response.getWriter().write(jsonObj.toJSONString());   
			         }else if(command.equals("selectRoute_save")) {
			            int loc_tid=(int)session.getAttribute("team_id");
			            String r_day=request.getParameter("r_day");
			            String route=request.getParameter("clickPosition_id");

			            RouteSelectDto rsdto=new RouteSelectDto(loc_tid,route,Integer.parseInt(r_day));
			            
			            if(routeselectDao.selectOneRoute(rsdto)==null){
			            routeselectDao.insertRoute(rsdto);
			            }else {
			            routeselectDao.updateRoute(rsdto);
			            }
			         }else if(command.equals("selectRoute_clear")){
			            int loc_tid=(int)session.getAttribute("team_id");
			            String day=request.getParameter("r_day");
			            
			            LocationDto locDto=new LocationDto();
			            locDto.setLoc_tid(loc_tid);
			            locDto.setLoc_day(day);
			            List<LocationDto> loc_list = locationDao.selectTeamLoc(locDto);
			            
			            JSONObject jsonObj=new JSONObject();
			            LinkedHashMap<String,Object> map2=new LinkedHashMap<String,Object>();
			            for(int i=0;i<loc_list.size();i++) {
			               int id=loc_list.get(i).getLoc_id();
			               String title=loc_list.get(i).getLoc_name();
			               String url=loc_list.get(i).getLoc_url();
			               String y=loc_list.get(i).getLoc_y();
			               String x=loc_list.get(i).getLoc_x();
			               int cate=loc_list.get(i).getLoc_cate();
			               
			               HashMap<String,Object> map=new HashMap<String,Object>();
			               
			               map.put("id", id);
			               map.put("title", title);
			               map.put("url", url);
			               map.put("y", y);
			               map.put("x", x);
			               map.put("cate",cate);
			               
			               map2.put("room"+i, map);
			            }
			            
			            HashMap<String,Object> map=new HashMap<String,Object>();
			            map.put("id", planBiz.selectVoRoom(loc_tid,day).getLoc_id());
			            map.put("title",planBiz.selectVoRoom(loc_tid,day).getLoc_name());
			            map.put("url", planBiz.selectVoRoom(loc_tid,day).getLoc_url());
			            map.put("y",planBiz.selectVoRoom(loc_tid,day).getLoc_y());
			            map.put("x",planBiz.selectVoRoom(loc_tid,day).getLoc_x());
			            map.put("cate",planBiz.selectVoRoom(loc_tid,day).getLoc_cate());
			            
			            map2.put("hotel", map);
			            
			            jsonObj.putAll(map2);          
			            
			            response.getWriter().write(jsonObj.toJSONString());
			         }else if(command.equals("memDetail")){
			            int tid=(int)session.getAttribute("team_id");
			            String mid=request.getParameter("mid"); 
			            
			            request.setAttribute("mid", mid);
			            request.setAttribute("days", teamDao.selectOneTeam(tid).getT_days());
			            
			            List<LocationDto> loc_list = locationDao.selectTeamLoc(new LocationDto(tid,"1"));
			            request.setAttribute("loc_list", loc_list);
			            RouteDto routeDto=new RouteDto(tid,mid,"1");   
			            if(routeDao.selectOneRoute(routeDto)!=null) {
			            String route=routeDao.selectOneRoute(routeDto).getR_route(); 
			            String[] arr_route=route.split("\\|");
			            String latlng=""; 
			            for( int x = 0; x < arr_route.length; x++ ){
			               latlng+=" new kakao.maps.LatLng("+
			                  locationDao.selectOneLoc(Integer.parseInt(arr_route[x])).getLoc_y().toString()+
			                  ","+
			                  locationDao.selectOneLoc(Integer.parseInt(arr_route[x])).getLoc_x().toString()+
			                  "),";
			               }
			            if (latlng.length() > 0 && latlng.charAt(latlng.length()-1)==',') {
			               latlng = latlng.substring(0, latlng.length()-1);
			                }
			               request.setAttribute("latlng", latlng);
			            }else {
			               request.setAttribute("latlng", null);
			            }
			            dispatch("teamMemDetail.jsp",request,response);
			         }else if(command.equals("memDetail_day")){
			            int tid=(int)session.getAttribute("team_id");
			            String day=request.getParameter("loc_day");
			            
			            LocationDto locDto=new LocationDto();
			            locDto.setLoc_tid(tid);
			            locDto.setLoc_day(day);
			            List<LocationDto> loc_list = locationDao.selectTeamLoc(locDto);
			            
			            JSONObject jsonObj=new JSONObject();
			            LinkedHashMap<String,Object> map2=new LinkedHashMap<String,Object>();
			            for(int i=0;i<loc_list.size();i++) {
			               int id=loc_list.get(i).getLoc_id();
			               String title=loc_list.get(i).getLoc_name();
			               String url=loc_list.get(i).getLoc_url();
			               String y=loc_list.get(i).getLoc_y();
			               String x=loc_list.get(i).getLoc_x();
			               int cate=loc_list.get(i).getLoc_cate();
			               
			               HashMap<String,Object> map=new HashMap<String,Object>();
			               
			               map.put("id", id);
			               map.put("title", title);
			               map.put("url", url);
			               map.put("y", y);
			               map.put("x", x);
			               map.put("cate",cate);
			               
			               map2.put("room"+i, map);
			            }
			            
			            jsonObj.putAll(map2);          
			            
			            response.getWriter().write(jsonObj.toJSONString());
			         }else if(command.equals("memDetail_loading")){
			            int tid=(int)session.getAttribute("team_id");
			            String day=request.getParameter("loc_day");
			            String mid=request.getParameter("mid");
			            RouteDto routeDto=new RouteDto(tid,mid,day);      

			            JSONObject jsonObj=new JSONObject();
			            if(routeDao.selectOneRoute(routeDto)!=null) { 
			            String route=routeDao.selectOneRoute(routeDto).getR_route(); 
			            String[] arr_route=route.split("\\|");
			            LinkedHashMap<String,Object> map2=new LinkedHashMap<String,Object>();
			            for( int i = 0; i < arr_route.length; i++ ){
			               HashMap<String,Object> map=new HashMap<String,Object>();

			               map.put("x",locationDao.selectOneLoc(Integer.parseInt(arr_route[i])).getLoc_x());
			               map.put("y",locationDao.selectOneLoc(Integer.parseInt(arr_route[i])).getLoc_y());
			               
			               map2.put("latlng"+i, map);
			               }
			            jsonObj.putAll(map2);
			            
			            response.getWriter().write(jsonObj.toJSONString());
			            }else {
			               jsonObj.putAll(new HashMap());
			               response.getWriter().write(jsonObj.toJSONString());
			            }
			      } else if (command.equals("insertDate")) {
			    	  
			    	  String t_enddateStr = request.getParameter("t_enddate");
			    	  String t_startdateStr = request.getParameter("t_startdate");
			    	  int tid = Integer.parseInt(request.getParameter("t_id"));
			    	  
			    	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			    	  
			    	  Date t_enddate = null, t_startdate = null;
			    	  
			    	  try {
						t_enddate = formatter.parse(t_enddateStr);
						t_startdate = formatter.parse(t_startdateStr);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	  
			    	  int res = teamDao.updateDate(t_enddate, t_startdate, tid);
			    	  
			    	  if(res > 0) {
			    		  res = teamDao.updateDays(tid);
			    		  if(res > 0) {
			    			  response.getWriter().append(""+res);  
			    		  }
			    	  } else {
			    		  
			    		  response.sendRedirect("NotAccess.jsp");
			    	  }
			    	  
			    	  
			      }
	 }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void dispatch(String url, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher(url);
		dispatch.forward(request, response);
	}
}
