package sg.irtest.neoapi;

import org.springframework.web.reactive.function.client.WebClient;

public class NeoapiClient {
    

    private final String apiUrl;
    private final String apiKey;
    private final WebClient webClient;

    public NeoapiClient(String url, String key) {
        this.apiUrl =  url;
        this.apiKey =  key;
        this.webClient = WebClient.builder().baseUrl(apiUrl).build();
    }

    public String neodata(String startDate, String endDate) {
        String resp = webClient.get()
        .uri(uriBuilder -> uriBuilder.path("feed").queryParam("start_date",startDate).queryParam("end_date", endDate).queryParam("api_key", apiKey).build())
        .retrieve()
        .bodyToMono(String.class)
        .block();
        return new String(resp);
    }

}
