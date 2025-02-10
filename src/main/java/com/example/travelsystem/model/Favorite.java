package com.example.travelsystem.model;
import lombok.Data;
@Data
public class Favorite {
    private Integer id;
    private Integer userId;
    private Integer tourLineId;
    private String createdAt;
}
