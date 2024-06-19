package com.trip.biz.otherBoard;

import java.util.List;

import com.trip.dao.otherBoard.FavoriteDao;
import com.trip.dao.otherBoard.FavoriteDaoImpl;
import com.trip.dto.otherBoard.FavoriteDto;

public class FavoriteBizImpl implements FavoriteBiz{

	FavoriteDao dao = new FavoriteDaoImpl();
	
	@Override
	public List<FavoriteDto> favoriteList(String f_id) {
		// TODO Auto-generated method stub
		return dao.favoriteList(f_id);
	}

	@Override
	public int favoriteCheck(String f_id, int f_pno, int f_cate) {
		// TODO Auto-generated method stub
		return dao.favoriteCheck(f_id, f_pno, f_cate);
	}

	@Override
	public int favoriteInsert(FavoriteDto fDto) {
		// TODO Auto-generated method stub
		return dao.favoriteInsert(fDto);
	}

	@Override
	public int favoriteDelete(String f_id, int f_pno) {
		// TODO Auto-generated method stub
		return dao.favoriteDelete(f_id, f_pno);
	}

	
	
}
