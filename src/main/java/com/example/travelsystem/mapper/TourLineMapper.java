package com.example.travelsystem.mapper;

import com.example.travelsystem.model.TourLine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TourLineMapper {

    void insertTourLine(TourLine tourLine);

    List<TourLine> findByDestination(String keyword);

    List<TourLine> findAll(@Param("offset") int offset, @Param("limit") int limit);

    void publishTourLine(Integer id);

    void unpublishTourLine(Integer id);

    List<TourLine> findAllPublished();

    List<TourLine> filterTourLines(@Param("destination") String destination,
                                   @Param("minPrice") Double minPrice,
                                   @Param("maxPrice") Double maxPrice,
                                   @Param("minDuration") Integer minDuration,
                                   @Param("maxDuration") Integer maxDuration,
                                   @Param("sortField") String sortField,
                                   @Param("sortDirection") String sortDirection,
                                   @Param("offset") int offset,
                                   @Param("limit") int limit);

    TourLine findById(Integer id);

    // 新增方法：获取所有线路（不分页）
    @Select("SELECT * FROM tour_line ORDER BY created_at DESC")
    List<TourLine> findAllNoPaging();

    // 新增方法：获取总记录数
    @Select("SELECT COUNT(*) FROM tour_line")
    int countAll();
}
