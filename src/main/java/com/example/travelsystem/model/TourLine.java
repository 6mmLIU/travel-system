package com.example.travelsystem.model;

import lombok.Data;

@Data
public class TourLine {
    private Integer id;         // ID
    private String title;       // 标题
    private String description; // 描述
    private String destination; // 目的地
    private Double price;       // 价格
    private Integer duration;   // 时长（天数）
    private String createdAt;   // 创建时间
    private Integer status;     //线路状态


    //判断是否上架
    public boolean isPublished(){
        return status !=null && status ==1;
    }

}
