package com.trip.controller.search;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.trip.dao.search.SearchDao;
import com.trip.dao.search.SearchDaoImpl;
import com.trip.dto.member.MemberLoginDto;
import com.trip.dto.search.SearchDto;

@WebServlet("/SearchServlet")
public class UserSearchServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

      doPost(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      request.setCharacterEncoding("UTF-8");
      response.setContentType("text/html; charset=UTF-8");

      String uri = request.getRequestURI();

      HttpSession session;
      session = request.getSession();

      SearchDao dao = new SearchDaoImpl();
      SearchDto dto = new SearchDto();
      if (uri.endsWith("SearchServlet")) {
         String[] particle = { "에서", "으로", "부터", "까지", "에", "서", "은", "는", "이", "가", "을", "를", "로", "와", "사이" };

         if (session.getAttribute("user") == null ) {
//            dto.setSearch(request.getParameter("search"));
//            List<SearchDto> list = new ArrayList<SearchDto>();
//
//            list = dao.searchList(dto.getSearch());
        	 String keyword = URLEncoder.encode(request.getParameter("search"),"UTF-8");
             response.sendRedirect(request.getParameter("listpage")+"?keyword="+keyword);
         } else {
            dto.setSearch(request.getParameter("search"));
            String myid = ((MemberLoginDto)session.getAttribute("user")).getM_id();
            dto.setMyid(myid);

            String[] array = dto.getSearch().split(" ");

            int res = 0;

            for (int i = 0; i < array.length; i++) {
               for (int j = 0; j < particle.length; j++) {
                  if (array[i].endsWith(particle[j])) {
                     int index = array[i].indexOf(particle[j]);
                     array[i] = array[i].substring(0, index);
                     System.out.println(array[i]);
                  }

               }
               res = dao.insertSearch(dto.getMyid(), array[i]);
            }

            if (res > 0) {
               System.out.println("검색어 추가 성공!");
            }
            
            String keyword = URLEncoder.encode(request.getParameter("search"),"UTF-8");
            response.sendRedirect(request.getParameter("listpage")+"?keyword="+keyword);
              
              //            List<SearchDto> list = new ArrayList<SearchDto>();
//
//            list = dao.searchList(dto.getSearch());
            
         }
      }
   }

}