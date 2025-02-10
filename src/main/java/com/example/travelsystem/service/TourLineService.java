package com.example.travelsystem.service;

import com.example.travelsystem.model.TourLine;

import java.util.List;

public interface TourLineService {
    void addTourLine(TourLine tourLine); // 添加旅游线路

    List<TourLine> searchTourLines(String keyword); // 搜索旅游线路

    List<TourLine> getAllTourLines(int page, int size); // 分页获取旅游线路

    void publishTourLine(Integer id); // 发布线路

    void unpublishTourLine(Integer id); // 下架线路

    List<TourLine> getAllPublishedTourLines(); // 获取已发布的线路

    List<TourLine> filterTourLines(String destination, Double minPrice, Double maxPrice,
                                   Integer minDuration, Integer maxDuration,
                                   String sortField, String sortDirection,
                                   int page, int size);

    TourLine findById(Integer id); // 根据 ID 获取线路详情

    List<TourLine> getPopularTourLines();

    List<TourLine> getAllTourLinesNoPaging();

    int getTotalRecords(); // 新增：获取总记录数
}
