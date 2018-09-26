package com.codewarsapi.controller;


import com.codewarsapi.model.Kata;
import com.codewarsapi.service.KataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class PlotlyController {

    @Autowired
    private KataService kataService;

    @GetMapping("/plotly")
    public Map<String, Long> getDataForPlotly(@RequestParam("codewars_username") String codewarsUsername,
                                              @RequestParam("from") String from,
                                              @RequestParam("to") String to) throws IOException {

        List<Kata> allKatas = kataService.allKatasResolvedByUser(codewarsUsername);
        List<Kata> latestKatas = kataService.getKatasForAGivenPeriod(allKatas, LocalDate.parse(from), LocalDate.parse(to));
        return kataService.getPlotlyData(latestKatas);

    }
}
