package sg.irtest.neoapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.irtest.neoapi.NeoapiClient;

@RestController
public class NeoapiController {

    @Value("${nasa.baseurl}") String URLClient;

    @Value("${nasa.apikey}") String apiKeyClient;

    @GetMapping("/top10neo")
    public String top10neo(@RequestParam(value = "start_date", required = true) String startDate,
            @RequestParam(value = "end_date", required = true) String endDate) {
        NeoapiClient neoApiClient = new NeoapiClient(this.URLClient, this.apiKeyClient);
        String resp = neoApiClient.neodata(startDate, endDate);
        return resp;
    }
    
}
