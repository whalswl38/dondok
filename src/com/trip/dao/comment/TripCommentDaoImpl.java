package com.trip.dao.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.trip.db.review.ReviewReadSqlMapConfig;
import com.trip.db.review.TripReviewSqlMapConfig;
import com.trip.dto.comment.TripCommentDto;

public class TripCommentDaoImpl extends ReviewReadSqlMapConfig implements TripCommentDao {

	String namespace = "com.trip.db.review.ReviewRead-mapper.";

	@Override
	public List<TripCommentDto> selectList(int rv_crno, int page) {

		SqlSession session = getSqlSessionFactory().openSession();

		Map<String, String> params = new HashMap<String, String>();

		params.put("rv_crno", "" + rv_crno);
		params.put("page", "" + page);

		List<TripCommentDto> list = session.selectList(namespace + "commentList", params);

		session.close();

		return list;
	}

	@Override
	public int insert(TripCommentDto dto) {

		SqlSession session = getSqlSessionFactory().openSession();

		int res = session.insert(namespace + "commentInsert", dto);

		if (res > 0) {
			session.commit();
		} else {
			session.rollback();
		}
		session.close();
		return res;
	}

	@Override
	public int update(TripCommentDto dto) {

		SqlSession session = getSqlSessionFactory().openSession();

		int res = session.update(namespace + "commentUpdate", dto);

		if (res > 0) {
			session.commit();
		} else {
			session.rollback();
		}
		session.close();
		return res;
	}

	@Override
	public int delete(int rv_no) {

		SqlSession session = getSqlSessionFactory().openSession();

		int res = session.update(namespace + "commentDelete", rv_no);

		if (res > 0) {
			session.commit();
		} else {
			session.rollback();
		}
		session.close();
		return res;
	}

	@Override
	public int commentCount(int rv_crno) {

		SqlSession session = getSqlSessionFactory().openSession();

		int res = session.selectOne(namespace + "commentCount", rv_crno);
		System.out.println("[commentCount]" + res);
		session.close();
		return res;
	}

}
