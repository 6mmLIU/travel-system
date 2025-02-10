package com.example.travelsystem.service;

import java.util.List;

public interface FavoriteService {

    // 添加收藏
    void addFavorite(Integer userId, Integer tourLineId);

    // 移除收藏
    void removeFavorite(Integer userId, Integer tourLineId);

    // 根据用户 ID 查询收藏的线路 ID 列表
    List<Integer> findFavoriteTourLinesByUserId(Integer userId);

    // 判断是否收藏
    boolean isFavorite(Integer userId, Integer tourLineId);
}
