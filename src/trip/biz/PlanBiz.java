package trip.biz;

import java.util.ArrayList;
import java.util.List;

import trip.dao.LocationDao;
import trip.dao.MemberDao;
import trip.dao.TeamDao;
import trip.dao.TeamMemberDao;
import trip.dao.VoRoomDao;
import trip.dto.LocationDto;
import trip.dto.TeamDto;
import trip.dto.TeamMemberDto;
import trip.dto.VoRoomDto;

public class PlanBiz {
   TeamMemberDao TMdao=new TeamMemberDao();
   TeamDao Tdao=new TeamDao();
   MemberDao Mdao=new MemberDao();
   LocationDao LoDao=new LocationDao();
   VoRoomDao VRdao=new VoRoomDao();
   
   public String teamLeadername(int tm_tid) {//팀id-->멤버id-->사용자 이름
      String leaderId=Tdao.selectOneTeam(tm_tid).getT_leaderId();
      return Mdao.selectOneMember(leaderId).getM_name();
   }
   
   public List<String> teamMembernames(int tm_tid) {//팀id-->멤버id-->사용자 이름
      String leaderId=Tdao.selectOneTeam(tm_tid).getT_leaderId();
      List<TeamMemberDto> list=TMdao.teamMemberList(tm_tid);
      List<String> m_names=new ArrayList<String>();
      for(int i=0; list.size()>i;i++) {
         if(list.get(i).getTm_uid().equals(leaderId)) {
            
         }else {
         m_names.add(Mdao.selectOneMember(list.get(i).getTm_uid()).getM_name());
      }
         }
   return m_names;
   }
   
   public List<String> Memberids(int tm_tid) {//리더제외
      String leaderId=Tdao.selectOneTeam(tm_tid).getT_leaderId();
      List<TeamMemberDto> list=TMdao.teamMemberList(tm_tid);
      List<String> m_ids=new ArrayList<String>();
      for(int i=0; list.size()>i;i++) {
         if(list.get(i).getTm_uid().equals(leaderId)) {
            
         }
         else {
         m_ids.add(list.get(i).getTm_uid());
      }
      }
   return m_ids;
   }
   public List<String> teamMemberids(int tm_tid) {//팀id-->멤버id-->사용자 이름
      List<TeamMemberDto> list=TMdao.teamMemberList(tm_tid);
      List<String> m_ids=new ArrayList<String>();
      for(int i=0; list.size()>i;i++) {
         m_ids.add(list.get(i).getTm_uid());
      }
   return m_ids;
   }
   public int tamMemberCount(int tm_tid) {
      List<TeamMemberDto> list=TMdao.teamMemberList(tm_tid);
   return list.size();
   } 
   public List<LocationDto> resVoRoom_info(List<VoRoomDto> roomVoteList){
      List<LocationDto> resVoRoom_info=new ArrayList<LocationDto>();
      for(int i=0;i<roomVoteList.size();i++) {
         resVoRoom_info.add(LoDao.selectOneLoc(roomVoteList.get(i).getVoroom_id()));
      }
      return resVoRoom_info;
   }
   public List<TeamDto> idToTeam(String my_id){
      List<TeamMemberDto> idList=TMdao.teamList(my_id);
      List<TeamDto> list=new ArrayList<TeamDto>();
      if(idList!=null) {
      for(int i=0;i<idList.size();i++) {
         list.add(Tdao.selectOneTeam(idList.get(i).getTm_tid()));
         }
      }
      return list;
   }
   public int upStage(int tid, int stage) {
      int res=1;
      List<TeamMemberDto> teamMemberList=TMdao.teamMemberList(tid);
      for(int i=0;i<teamMemberList.size();i++   ){
         if(teamMemberList.get(i).getTm_stage()!=stage){
            res=0;
         }
      }
      if(res>0) {
         Tdao.upStage(tid,stage);
         if(stage==6)
            Tdao.updateFlag(tid);
      }
      return res;
   }
   
   public LocationDto selectVoRoom(int tid,String day){
      List<VoRoomDto> room_list=VRdao.resVoRoom(tid);
      LocationDto loDto=new LocationDto();
      
      for(int i=0;i<room_list.size();i++) {
         if(room_list.get(i).getVoroom_day().equals(day))
         loDto=LoDao.selectOneLoc(room_list.get(i).getVoroom_id());
      }
      
      return loDto;
   }
}