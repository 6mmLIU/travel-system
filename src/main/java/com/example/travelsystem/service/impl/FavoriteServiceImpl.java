package com.example.travelsystem.service.impl;

import com.example.travelsystem.mapper.FavoriteMapper;
import com.example.travelsystem.service.FavoriteService;
import com.example.travelsystem.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<Integer> findFavoriteTourLinesByUserId(Integer userId) {
        String cacheKey = "favorites_user_" + userId;

        // 检查 Redis 缓存
        if (redisUtil.hasKey(cacheKey)) {
            return (List<Integer>) redisUtil.get(cacheKey);
        }

        // 查询数据库
        List<Integer> favoriteTourLines = favoriteMapper.findFavoriteTourLinesByUserId(userId);

        // 设置缓存，有效期 10 分钟
        redisUtil.set(cacheKey, favoriteTourLines, 10 * 60);

        return favoriteTourLines;
    }

    @Override
    public void addFavorite(Integer userId, Integer tourLineId) {
        if (!isFavorite(userId, tourLineId)) {
            favoriteMapper.addFavorite(userId, tourLineId);

            // 更新缓存
            String cacheKey = "favorites_user_" + userId;
            if (redisUtil.hasKey(cacheKey)) {
                List<Integer> cachedFavorites = (List<Integer>) redisUtil.get(cacheKey);
                cachedFavorites.add(tourLineId);
                redisUtil.set(cacheKey, cachedFavorites, 10 * 60);
            }
        }
    }

    @Override
    public void removeFavorite(Integer userId, Integer tourLineId) {
        favoriteMapper.removeFavorite(userId, tourLineId);

        // 更新缓存
        String cacheKey = "favorites_user_" + userId;
        if (redisUtil.hasKey(cacheKey)) {
            List<Integer> cachedFavorites = (List<Integer>) redisUtil.get(cacheKey);
            cachedFavorites.remove(tourLineId);
            redisUtil.set(cacheKey, cachedFavorites, 10 * 60);
        }
    }

    @Override
    public boolean isFavorite(Integer userId, Integer tourLineId) {
        return favoriteMapper.findByUserIdAndTourLineId(userId, tourLineId) != null;
    }
}
