--팀멤버 테이블
--팀ID, 개인ID, 입금여부, 진행단계
DROP TABLE TEAMMEMBER;
CREATE TABLE TEAMMEMBER(
TM_TID NUMBER NOT NULL,
TM_UID VARCHAR2(200) NOT NULL,
TM_DEPFLAG VARCHAR2(2),--입금 여부
TM_STAGE NUMBER NOT NULL,--진행 단계
	CONSTRAINT TEAMMEMBER_PK PRIMARY KEY(TM_TID,TM_UID),
	CONSTRAINT TEAMMEMBER_CHK_ENABLED CHECK(TM_DEPFLAG IN ('Y', 'N'))
);
select * FROM TEAMMEMBER;
delete from TEAMMEMBER where TM_UID='test1';
--팀 테이블
DROP SEQUENCE TEAMSEQ;
CREATE SEQUENCE TEAMSEQ;
DROP TABLE TEAM;
--팀ID, 팀이름(제목), 리더ID, 일차, 여행시작, 여행종료, (목표)종료날짜(YYYYMMDD), 진행단계, 일정완료여부
CREATE TABLE TEAM(
T_ID NUMBER,
T_NAME VARCHAR2(200),
T_LEADERID VARCHAR2(200),
T_DAYS VARCHAR2(200),
T_STARTDATE date,
T_ENDDATE date,
T_STAGE number,
T_DEADLINE VARCHAR2(200),
T_FLAG VARCHAR2(2),
	CONSTRAINT TEAM_PK PRIMARY KEY(T_ID),
	CONSTRAINT TEAM_CHK_ENABLED CHECK(T_FLAG IN ('Y', 'N'))
);

SELECT * FROM TEAM;

-- 경로 확정 테이블 [ seq | 팀 ID | 경로 ID | 일자 ]
drop sequence routeselect_seq;
create sequence routeselect_seq;
drop table routeselect;
create table routeselect (
rs_no number primary key,
rs_tno number not null,
rs_route varchar2(1000) not null,
rs_accdate varchar2(100) not null,
constraint rs_tno_fk foreign key(rs_tno) references team(t_id)
);



DROP SEQUENCE TRIPREVIEW_ID;
DROP SEQUENCE TRIPREVIEW_CONTENTS_ID;

DROP TABLE TRIPREVIEW;
DROP TABLE TRIPREVIEW_CONTENTS;

-- 여행 후기 뷰 정보

CREATE SEQUENCE TRIPREVIEW_ID;

create table TRIPREVIEW (
	TV_NO NUMBER PRIMARY KEY,
	TV_TEAMID NUMBER NOT NULL,
	TV_TITLE VARCHAR2(250) NOT NULL,
	TV_DATE DATE NOT NULL,
	TV_MODIFYDATE DATE,
	TV_COUNT NUMBER NOT NULL,
	TV_DELFLAG VARCHAR2(5) NOT NULL,
	CONSTRAINTS TV_DELFLAG_CONSTRANTS CHECK( TV_DELFLAG IN ('Y','N') )
);


-- 여행 후기 내용

CREATE SEQUENCE TRIPREVIEW_CONTENTS_ID;

create table TRIPREVIEW_CONTENTS(
	TVC_NO NUMBER PRIMARY KEY,
	TVC_TVNO NUMBER NOT NULL,
	TVC_DAY NUMBER NOT NULL,
	TVC_ROUTEID NUMBER,
	TVC_REVIEWID NUMBER,
	TVC_CONTENTS CLOB,
	TVC_PATH VARCHAR2(300),
	TVC_DELFLAG VARCHAR2(10),
	TVC_DATE DATE NOT NULL,
	TVC_MODIFYDATE DATE,
	CONSTRAINTS TVC_DELFLAG_CHK CHECK(TVC_DELFLAG in ('Y', 'N')),
	CONSTRAINTS TVC_TVNO_CONSTRANTS FOREIGN KEY(TVC_TVNO) REFERENCES TRIPREVIEW(TV_NO)
);

alter table TRIPREVIEW_CONTENTS add (tvc_userid varchar2(250));


SELECT tvc_no, tvc_tvno, tvc_day, tvc_routeid, tvc_reviewid,
nvl2(tvc_reviewid, (select CR_CONTENTS from CATEGORYREVIEW where CR_NO = tvc_reviewid), tvc_contents) tvc_contents,
nvl2(tvc_reviewid, (select CR_TITLE from CATEGORYREVIEW where CR_NO = tvc_reviewid),(
case when DBMS_LOB.GETLENGTH(tvc_contents) <= 13 then DBMS_LOB.SUBSTR(tvc_contents,13,1) else DBMS_LOB.SUBSTR(tvc_contents,13,1) || '...' end)) tvc_title
FROM TRIPREVIEW_CONTENTS WHERE TVC_TVNO = 1 and TVC_ROUTEID = 3 and TVC_DAY = 1 ORDER BY TVC_DATE ASC

-- 카테고리별 리뷰

create table category (
	c_cateNum number primary key,
	c_cateName varchar2(100) not null
);

insert into category values(1,'숙소');
insert into category values(2,'맛집');
insert into category values(3,'명소');



drop sequence CATEGORYREVIEW_ID;
create sequence CATEGORYREVIEW_ID;

create table CATEGORYREVIEW (
	CR_NO number primary key,
	CR_ID varchar2(100) not null,
	CR_TITLE varchar2(100) not null,
	CR_CONTENTS CLOB not null,
	CR_DATE date not null,
	CR_COUNT number not null,
	CR_DELFLAG varchar2(5) not null,
	CR_CATEGORY number not null,
	CR_PLACEID varchar2(100) not null,
	CR_PATH varchar2(300),
	CONSTRAINTS CR_DELFLAG_CHK CHECK(CR_DELFLAG in ('Y', 'N')),
	CONSTRAINTS CR_CATEGORY_FK FOREIGN KEY(CR_CATEGORY) REFERENCES CATEGORY(c_cateNum)
);


-- 여행 후기 댓글 기능

drop sequence tripreviewcomment_id;
drop table tripreviewcomment;

create sequence tripreviewcomment_id;

create table tripreviewcomment (
rv_no number primary key,
rv_crno number not null,
rv_pno number,
rv_id varchar2(250) not null,
rv_content clob not null,
rv_date date not null,
rv_delflag varchar2(5) not null,
CONSTRAINTS rv_delflag_chk1 CHECK(rv_delflag in ('Y', 'N')),
CONSTRAINTS rv_crno_fk1 FOREIGN KEY(rv_crno) REFERENCES TRIPREVIEW(tv_no)
);


-- 리뷰 댓글 기능

drop sequence categoryreviewcomment_id;
drop table categoryreviewcomment;

create sequence categoryreviewcomment_id;

create table categoryreviewcomment (
rv_no number primary key,
rv_crno number not null,
rv_pno number,
rv_id varchar2(250) not null,
rv_content clob not null,
rv_date date not null,
rv_delflag varchar2(5) not null,
CONSTRAINTS rv_delflag_chk CHECK(rv_delflag in ('Y', 'N')),
CONSTRAINTS rv_crno_fk FOREIGN KEY(rv_crno) REFERENCES categoryreview(cr_no)
);

select rownum no, level, rv.* from categoryreviewcomment rv where rv_delflag = '' and rv_crno = #{rv_crno} start with rv_pno is null connect by prior rv_no = rv_pno

select * from (select rownum no, level, CONNECT_BY_ISLEAF , rv.* from categoryreviewcomment rv where rv_delflag = 'N' and rv_crno = 91 start with rv_pno is null connect by prior rv_no = rv_pno) where no between 1 + (8*(1-1)) and 1 * 8

select * from (select rownum no, level,rv.* from categoryreviewcomment rv where rv_delflag = 'N' start with rv_pno is null connect by prior rv_no = rv_pno) where no between 1 and 8 ;


select * from (select rownum no, level, rv.* from categoryreviewcomment rv where rv_delflag = 'N' and rv_crno = 91 start with rv_pno is null connect by prior rv_no = rv_pno) where no between 1 + (8*(1-1)) and 1 * 8;

create view testddd as select * from (select rownum no, level, rv.* from categoryreviewcomment rv where rv_delflag = 'N' and rv_crno = 91 start with rv_pno is null connect by prior rv_no = rv_pno) where no between 1 and 8;
--


drop view categoryreview_read;
create view categoryreview_read as select CR_NO, CR_ID, CR_TITLE, CR_CONTENTS, CR_DATE, CR_COUNT, CR_DELFLAG, c_cateName as CR_CATEGORY, CR_PLACEID, CR_PATH from category c inner join categoryreview cr on (c.c_cateNum = cr.CR_CATEGORY) order by cr_no;


-- 알람 테이블

drop table alarmCategory;
drop table alarm;
drop sequence alarm_seq;

create sequence alarm_seq;

create table alarmCategory (
	alc_cate number primary key,
	alc_cateName varchar2(100) not null,
	CONSTRAINTS alc_cateName_uni unique(alc_cateName)
);

create table alarm (	
	al_no number primary key,
	al_id varchar2(250) not null,
	al_rno number ,
	al_cate number  not null,
	al_aflag varchar2(5)  not null,
	al_date date,
	CONSTRAINTS al_aflag_chk CHECK(al_aflag in ('Y', 'N')),
	CONSTRAINTS al_cate_fk foreign key(al_cate) references alarmCategory(alc_cate)
);


-- 알람 리스트 테스트

insert into alarm values (alarm_seq.nextval,'user1',91,2,'N',sysdate);


-- 알림 카테고리 (필요한거 추가해서 사용)
-- 0 | 시스템 ( 점검같은거 )
-- 1 | 이벤트
-- 2 | 카테고리 리뷰 댓글
-- 3 | 여행  후기 댓글

insert into alarmCategory values (0, '시스템');
insert into alarmCategory values (1, '이벤트');
insert into alarmCategory values (2, '카테고리 리뷰 댓글');
insert into alarmCategory values (3, '여행 후기 댓글');


-- 알람 리스트 뷰 테이블

drop view alarmList;
create view alarmList as select * from alarm al join alarmCategory alc on(al.al_cate = alc.alc_cate); 

-- 뷰 조회
select * from alarmList; 


-- 스크랩 테이블

-- 0 이면 후기
-- 1 이면 리뷰

drop table favorite;

create sequence favorite_seq;
create table favorite (
	f_no number primary key,
	f_id varchar2(250) not null,
	f_pno number not null,
	f_cate number not null,
	f_date date not null
);

insert into favorite values (favorite_seq.nextval, 'user1', 91, 1, sysdate);



-- 여행 후기 메인 읽기

drop view TRIPREVIEW_MAIN_VIEW;

create view TRIPREVIEW_MAIN_VIEW as
select tv.tv_no, tv.TV_TEAMID, tv.tv_title, tv.tv_date, tv.tv_count, tvc.tvc_no, tvc.tvc_path, cr.cr_path
from tripreview tv
left outer join TRIPREVIEW_CONTENTS tvc on ( tv.tv_delflag = 'N' and tv.tv_no = tvc.tvc_tvno and tvc.tvc_delflag = 'N' )
left outer join categoryreview_read cr on ( tvc.tvc_reviewid = cr.cr_no and cr.cr_delflag = 'N') order by tv_no


-- count

	update categoryreview set cr_count = (select cr_count from categoryreview where cr_no = 92 )+1 where cr_no = 92;


-- main view
	select tv_no,tv_teamid, tv_title, tv_date, tv_count, tvc_no, tvc_path, cr_path, default_path from (select rownum no, tv_no,tv_teamid, tv_title, tv_date, tv_count, tvc_no, tvc_path, cr_path, nvl2(tvc_path,null,nvl2(cr_path,null,'images/trip_review/default.jpg')) default_path  from TRIPREVIEW_MAIN_VIEW where (tv_no, nvl(tvc_no,0)) in
	(select tv_no, max(nvl(tvc_no,0)) from TRIPREVIEW_MAIN_VIEW where tvc_path is not null or cr_path is not null group by tv_no union 
	select tv_no, max(nvl(tvc_no,0)) from TRIPREVIEW_MAIN_VIEW where tvc_path is null and cr_path is null and tv_no not in (select tv_no from TRIPREVIEW_MAIN_VIEW where tvc_path is not null or cr_path is not null group by tv_no) group by tv_no) and tv_title like '%3%' order by tv_no) where no between 1 and 8


-- category view
	select * from (select rownum no, cr_no, cr_id, cr_title, cr_contents, cr_date, cr_count, cr_delflag, cr_category, cr_placeid, cr_path from (select *  from categoryreview_read where cr_delflag='N' and cr_title like '%%' and cr_category = '명소' order by cr_no))where no between 1 and 8;
	select * from (select rownum no, CR_NO, CR_ID, CR_TITLE, CR_CONTENTS, CR_DATE, CR_COUNT, CR_DELFLAG, CR_CATEGORY, CR_PLACEID, CR_PATH from (select *  from categoryreview_read where cr_title like '%%' order by cr_no))where no between 1 and 8;
