package com.trip.biz.categoryreview;

import java.util.List;


import com.trip.dao.categoryreview.CategoryReviewDao;
import com.trip.dao.categoryreview.CategoryReviewDaoImpl;
import com.trip.dto.categoryreview.CategoryReviewDto;
import com.trip.util.ReviewUtil;

public class CategoryReviewBizImpl implements CategoryReviewBiz{

	CategoryReviewDao dao = new CategoryReviewDaoImpl();
	
	@Override
	public List<CategoryReviewDto> selectList(String start, String end, String keyword, String category, String m_id) {
		if(keyword == null) {
			keyword = "%%";
		} else {
			keyword = "%" + keyword + "%";
		}
		
		String changeStr = (category == "1")? "숙소" : (category == "2")? "맛집" : "명소";
		
		List<CategoryReviewDto> output = dao.selectList(start, end, keyword, changeStr, m_id);
		
		for(CategoryReviewDto out : output) {
			
			if(out.getCr_path() != null) {
				String[] tmp = out.getCr_path().split("\\|");
				out.setCr_path(tmp[0]);
			}
		}
		
		return output;
	}

	@Override
	public CategoryReviewDto selectOne(int cr_no) {
		
		if(count(cr_no) == 1) {
			return dao.selectOne(cr_no);
		} else {
			return null;
		}
	}

	@Override
	public int maxListNum() {
		return dao.maxListNum();
	}

	@Override
	public int insert(CategoryReviewDto dto, String cr_path) {
		
		String old_cr_path = dto.getCr_path();
		if(cr_path !=null && old_cr_path != null ) {
			String[] cr_pathArray = cr_path.split("\\|");
			String[] old_cr_pathArray = old_cr_path.split("\\|");
			dto.setCr_path(cr_path);
			for(int i = 0 ; i < old_cr_pathArray.length ; i ++) {
			
				System.out.println("[old_cr_pathArray["+i+"]] " + old_cr_pathArray[i]);
				System.out.println("[cr_pathArray["+i+"]] " + cr_pathArray[i]);
			
			
				dto.setCr_contents(dto.getCr_contents().replace(old_cr_pathArray[i], cr_pathArray[i]));
		
			}
		}
		System.out.println(dto.getCr_contents());
		
		return dao.insert(dto);
	}

	@Override
	public int delete(int cr_no) {
		return dao.delete(cr_no);
	}

	@Override
	public int update(CategoryReviewDto dto, String cr_path, String cr_tmp_path, String serverPath) {
		
		CategoryReviewDto oldDto = dao.selectOne(dto.getCr_no());
		String tmp = "";
		
		System.out.println("[oldPath] " + oldDto.getCr_path());
		System.out.println("[modifyPaht] " + dto.getCr_path());
		if(oldDto.getCr_path() != null) {
			String now_path = dto.getCr_path();
			String[] now_pathArray = now_path.split("\\|");
			String old_path = oldDto.getCr_path();
			String[] old_pathArray = old_path.split("\\|");
			
			for(int i = 0 ; i < now_pathArray.length ; i ++) {
				System.out.println("[now_pathArray["+i+"]] " + now_pathArray[i]);
				for(int j = 0 ; j < old_pathArray.length ; j++ ) {
					System.out.println("[old_pathArray["+j+"]] " + old_pathArray[j]);
					if(now_pathArray[i].equals(old_pathArray[j])) {
						tmp +=((tmp.equals(""))? "" : "|") + now_pathArray[i];
						System.out.println("[tmp] " +tmp);
						old_pathArray[j] = null;
					}
				}
			}
			
			for(String path : old_pathArray) {
				if(path != null) {
					ReviewUtil.imagesDelete(serverPath, path);
					System.out.println("[deleteimage] " + path);
				}
			}
			
			System.out.println("[re_path] " + tmp);
		}
		
		if(cr_path !=null && cr_tmp_path != null ) {
			String[] cr_pathArray = cr_path.split("\\|");
			String[] tmp_cr_pathArray = cr_tmp_path.split("\\|");
			dto.setCr_path(tmp + "|"+ cr_path);
			for(int i = 0 ; i < tmp_cr_pathArray.length ; i ++) {
			
				System.out.println("[tmp_cr_pathArray["+i+"]] " + tmp_cr_pathArray[i]);
				System.out.println("[cr_pathArray["+i+"]] " + cr_pathArray[i]);
			
			
				dto.setCr_contents(dto.getCr_contents().replace(tmp_cr_pathArray[i], cr_pathArray[i]));
		
			}
		}
		System.out.println("[final_path] " + dto.getCr_path());
		return dao.update(dto);
	}

	@Override
	public int count(int cr_no) {
		return dao.count(cr_no);
	}

	@Override
	public List<CategoryReviewDto> selectMyReview(String cr_id, String cr_placeid) {
		// TODO Auto-generated method stub
		return dao.selectMyReview(cr_id, cr_placeid);
	}



	
}
