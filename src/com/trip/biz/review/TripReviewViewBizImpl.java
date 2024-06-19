package com.trip.biz.review;

import java.util.List;

import com.trip.dao.review.TripReviewViewDao;
import com.trip.dao.review.TripReviewViewDaoImpl;
import com.trip.dto.review.TripReviewViewDto;

public class TripReviewViewBizImpl implements TripReviewViewBiz{

	TripReviewViewDao tripReviewViewDao = new TripReviewViewDaoImpl();
	
	@Override
	public List<TripReviewViewDto> selectList(String start, String end, String keyword, String m_id) {
		// TODO Auto-generated method stub
		
		if(keyword == null) {
			keyword = "%%";
		} else {
			keyword = "%" + keyword + "%";
		}
		
		List<TripReviewViewDto> output = tripReviewViewDao.selectList(start, end, keyword, m_id);
		
		for(TripReviewViewDto out : output) {
			if(out.getCr_path() != null) {
				String[] tmp = out.getCr_path().split("\\|");
				out.setCr_path(tmp[0]);
			}
			if(out.getTvc_path() != null) {
				String[] tmp = out.getTvc_path().split("\\|");
				out.setTvc_path(tmp[0]);
			}
		}
		return output;
	}

}
