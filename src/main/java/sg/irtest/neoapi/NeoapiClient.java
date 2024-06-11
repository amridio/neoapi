package sg.irtest.neoapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;

import sg.irtest.neoapi.exception.BadRequestException;
import sg.irtest.neoapi.exception.UnauthorizedException;

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
        .onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals, response -> response.bodyToMono(String.class).map(BadRequestException::new))
        .onStatus(HttpStatus.BAD_GATEWAY::equals, response -> response.bodyToMono(String.class).map(BadRequestException::new))
        .onStatus(HttpStatus.UNAUTHORIZED::equals, response -> response.bodyToMono(String.class).map(UnauthorizedException::new))
        .onStatus(HttpStatus.FORBIDDEN::equals, response -> response.bodyToMono(String.class).map(UnauthorizedException::new))
        .bodyToMono(String.class)
        .block();
        return new String(resp);
    }

}
