package com.example.travelsystem.service.impl;

import com.example.travelsystem.mapper.TourLineMapper;
import com.example.travelsystem.model.TourLine;
import com.example.travelsystem.service.TourLineService;
import com.example.travelsystem.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourLineServiceImpl implements TourLineService {

    @Autowired
    private TourLineMapper tourLineMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void addTourLine(TourLine tourLine) {
        if (tourLine == null || tourLine.getTitle() == null || tourLine.getDestination() == null) {
            throw new IllegalArgumentException("TourLine or required fields cannot be null");
        }
        // —— 新增：给 status 一个默认值，防止插入时为 null 导致 SQLIntegrityConstraintViolation
        if (tourLine.getStatus() == null) {
            tourLine.setStatus(0);  // 0 表示“未发布”
        }
        tourLineMapper.insertTourLine(tourLine);
    }

    @Override
    public List<TourLine> searchTourLines(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return tourLineMapper.findAllNoPaging();
        }
        return tourLineMapper.findByDestination(keyword.trim());
    }

    @Override
    public List<TourLine> getAllTourLines(int page, int size) {
        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("Page and size must be greater than 0");
        }
        int offset = (page - 1) * size;
        return tourLineMapper.findAll(offset, size);
    }

    @Override
    public void publishTourLine(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid TourLine ID");
        }
        tourLineMapper.publishTourLine(id);
        redisUtil.delete("popular_tour_lines");
    }

    @Override
    public void unpublishTourLine(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid TourLine ID");
        }
        tourLineMapper.unpublishTourLine(id);
        redisUtil.delete("popular_tour_lines");
    }

    @Override
    public List<TourLine> getAllPublishedTourLines() {
        return tourLineMapper.findAllPublished();
    }

    @Override
    public List<TourLine> filterTourLines(String destination,
                                          Double minPrice,
                                          Double maxPrice,
                                          Integer minDuration,
                                          Integer maxDuration,
                                          String sortField,
                                          String sortDirection,
                                          int page,
                                          int size) {
        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("Page and size must be greater than 0");
        }
        int offset = (page - 1) * size;
        return tourLineMapper.filterTourLines(
                destination, minPrice, maxPrice,
                minDuration, maxDuration,
                sortField, sortDirection,
                offset, size
        );
    }

    @Override
    public TourLine findById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid TourLine ID");
        }
        return tourLineMapper.findById(id);
    }

    @Override
    public List<TourLine> getPopularTourLines() {
        String cacheKey = "popular_tour_lines";
        if (redisUtil.hasKey(cacheKey)) {
            return (List<TourLine>) redisUtil.get(cacheKey);
        }
        List<TourLine> popularTourLines = tourLineMapper.findAllPublished();
        redisUtil.set(cacheKey, popularTourLines, 30 * 60);
        return popularTourLines;
    }

    @Override
    public List<TourLine> getAllTourLinesNoPaging() {
        return tourLineMapper.findAllNoPaging();
    }

    @Override
    public int getTotalRecords() {
        return tourLineMapper.countAll();
    }

    @Override
    public int countFilter(String destination,
                           Double minPrice,
                           Double maxPrice,
                           Integer minDuration,
                           Integer maxDuration) {
        return tourLineMapper.countFilter(
                destination, minPrice, maxPrice, minDuration, maxDuration
        );
    }

    @Override
    public List<TourLine> getFavoritesByUser(Integer id) {
        return List.of();
    }

    @Override
    public boolean toggleFavorite(Integer id, Integer lineId) {
        return false;
    }
}
