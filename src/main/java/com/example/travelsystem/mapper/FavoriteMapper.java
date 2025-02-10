package com.example.travelsystem.mapper;

import com.example.travelsystem.model.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FavoriteMapper {

    void addFavorite(@Param("userId") Integer userId, @Param("tourLineId") Integer tourLineId);

    void removeFavorite(@Param("userId") Integer userId, @Param("tourLineId") Integer tourLineId);

    List<Integer> findFavoriteTourLinesByUserId(@Param("userId") Integer userId);

    Favorite findByUserIdAndTourLineId(@Param("userId") Integer userId, @Param("tourLineId") Integer tourLineId);
}
