package com.example.travelsystem.service.impl;

import com.example.travelsystem.mapper.FavoriteMapper;
import com.example.travelsystem.model.TourLine;
import com.example.travelsystem.service.FavoriteService;
import com.example.travelsystem.service.TourLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private TourLineService tourLineService;

    @Override
    @Transactional
    public void addFavorite(Integer userId, Integer tourLineId) {
        // 防重复：先检查
        if (!isFavorite(userId, tourLineId)) {
            favoriteMapper.addFavorite(userId, tourLineId);
        }
    }

    @Override
    @Transactional
    public void removeFavorite(Integer userId, Integer tourLineId) {
        favoriteMapper.removeFavorite(userId, tourLineId);
    }

    @Override
    public List<Integer> findFavoriteTourLinesByUserId(Integer userId) {
        return favoriteMapper.findFavoriteTourLinesByUserId(userId);
    }

    @Override
    public boolean isFavorite(Integer userId, Integer tourLineId) {
        return favoriteMapper.findByUserIdAndTourLineId(userId, tourLineId) != null;
    }

    @Override
    public List<TourLine> listFavorites(Integer userId) {
        // 拿到收藏的线路 ID 列表，再查询实体
        List<Integer> ids = favoriteMapper.findFavoriteTourLinesByUserId(userId);
        return ids.stream()
                .map(tourLineService::findById)
                .filter(line -> line != null)
                .collect(Collectors.toList());
    }
}
