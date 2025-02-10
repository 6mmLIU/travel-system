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
        tourLineMapper.insertTourLine(tourLine);
    }

    @Override
    public List<TourLine> searchTourLines(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Search keyword cannot be null or empty");
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
    public List<TourLine> filterTourLines(String destination, Double minPrice, Double maxPrice,
                                          Integer minDuration, Integer maxDuration,
                                          String sortField, String sortDirection,
                                          int page, int size) {
        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("Page and size must be greater than 0");
        }
        int offset = (page - 1) * size;
        return tourLineMapper.filterTourLines(destination, minPrice, maxPrice,
                minDuration, maxDuration, sortField, sortDirection, offset, size);
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
        return tourLineMapper.findAllNoPaging(); // 调用 Mapper 层实现
    }
    @Override
    public int getTotalRecords() {
        return tourLineMapper.countAll(); // 调用 Mapper 方法获取总记录数
    }

}
