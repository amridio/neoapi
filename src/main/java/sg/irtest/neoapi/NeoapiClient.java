package sg.irtest.neoapi;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;



public class NeoapiClient {
    
    @Autowired
    private Environment env;

    private final String apiUrl = env.getProperty("nasa.baseurl");
    private final String apiKey = env.getProperty("nasa.apikey");
    private final WebClient webClient;

    public NeoapiClient() {
        this.webClient = WebClient.builder().baseUrl(apiUrl).build();
        System.out.println("URL:" + apiUrl);
        System.out.println("ApiKey:" + apiKey);
    }


}
