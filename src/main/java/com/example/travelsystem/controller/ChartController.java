package com.example.travelsystem.controller;

import com.example.travelsystem.model.TourLine;
import com.example.travelsystem.service.TourLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/charts")
public class ChartController {

    @Autowired
    private TourLineService tourLineService;

    @GetMapping("/city-count")
    public List<CityCountDTO> getCityCount() {
        // 假设你有个方法一次查出所有线路
        List<TourLine> allLines = tourLineService.getAllTourLinesNoPaging();

        // 用Java流统计每个城市的线路数
        Map<String, Long> cityCountMap = allLines.stream()
                .collect(Collectors.groupingBy(TourLine::getDestination, Collectors.counting()));

        // 转成 DTO 列表
        List<CityCountDTO> result = new ArrayList<>();
        for (Map.Entry<String, Long> entry : cityCountMap.entrySet()) {
            result.add(new CityCountDTO(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    // DTO 类 (可放外部文件)
    public static class CityCountDTO {
        private String city;
        private Long count;

        public CityCountDTO(String city, Long count) {
            this.city = city;
            this.count = count;
        }

        public String getCity() { return city; }
        public Long getCount() { return count; }
    }
}
