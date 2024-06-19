package com.trip.biz.otherBoard;

import java.util.List;

import com.trip.dto.otherBoard.FavoriteDto;

public interface FavoriteBiz {

	List<FavoriteDto> favoriteList(String f_id);
	int favoriteCheck(String f_id, int f_pno, int f_cate);
	int favoriteInsert(FavoriteDto fDto);
	int favoriteDelete(String f_id, int f_pno);
	
}
