package sg.irtest.neoapi.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import sg.irtest.neoapi.NeoapiClient;
import sg.irtest.neoapi.exception.BadRequestException;
import sg.irtest.neoapi.exception.UnauthorizedException;
import sg.irtest.neoapi.model.Neo;
import sg.irtest.neoapi.model.NeoDiameter;
import sg.irtest.neoapi.model.NeoSimple;

@RestController
public class NeoapiController {

    @Value("${nasa.baseurl}") String URLClient;

    @Value("${nasa.apikey}") String apiKeyClient;

    @GetMapping("/top10neo")
    public List<Neo> top10neo(@RequestParam(value = "start_date", required = true) String startDate,
            @RequestParam(value = "end_date", required = true) String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try{
            DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(startDate);
            DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(endDate);
            Date sDate = sdf.parse(startDate);
            Date eDate = sdf.parse(endDate);
            if (eDate.before(sDate)){
                throw new BadRequestException("Invalid Parameter, end_date can't be earlier than start_date ");
            }
            long dateDiff = (eDate.getTime() - sDate.getTime())/3600/1000/24;
            if (dateDiff > 7){
                throw new BadRequestException("Invalid Parameter, date range can't more than 7 days");
            }
        }
        catch (DateTimeParseException dtpe){
            System.out.println("Masuk catch DTPE");
            throw new BadRequestException("Invalid Parameter, Invalid Date Format");
        }
        catch (ParseException dtpe){
            System.out.println("PE");
            throw new BadRequestException("Invalid Parameter, Invalid Date Format");
        }
        NeoapiClient neoApiClient = new NeoapiClient(this.URLClient, this.apiKeyClient);
        String neodata = neoApiClient.neodata(startDate, endDate);
        //JSONObject jsonNeodata= new JSONObject(neodata);
        JsonObject jsonNeodata = JsonParser.parseString(neodata).getAsJsonObject();
        int elementCount = jsonNeodata.get("element_count").getAsInt();
        System.out.println("Jumlah NEO:" + elementCount);
        JsonObject jsonNEO = jsonNeodata.get("near_earth_objects").getAsJsonObject();
        Set<String> it = jsonNEO.keySet();
        List<Neo> listNeo = new ArrayList<Neo>();
        for (String keyDate : it) {
            System.out.println("NEO Date:"+keyDate);
            JsonArray JsonListNEO = jsonNEO.get(keyDate).getAsJsonArray();
            for (JsonElement jeNeo : JsonListNEO) {
                JsonObject jsNeo = jeNeo.getAsJsonObject();
                //String id = jsNeo.get("id").getAsString();
                Neo neo = new Neo();
                //neo.setCloseApproachDate(jsNeo.get().getAsString());
                neo.setId(jsNeo.get("id").getAsString());
                neo.setName(jsNeo.get("name").getAsString());
                neo.setMagnitude(jsNeo.get("absolute_magnitude_h").getAsFloat());
                NeoDiameter neoD = new NeoDiameter();
                neoD.setMax_diameter(jsNeo.get("estimated_diameter").getAsJsonObject().get("kilometers").getAsJsonObject().get("estimated_diameter_max").getAsDouble());
                neoD.setMin_diameter(jsNeo.get("estimated_diameter").getAsJsonObject().get("kilometers").getAsJsonObject().get("estimated_diameter_min").getAsDouble());
                neo.setNeoDiameter(neoD);
                neo.setPha(jsNeo.get("is_potentially_hazardous_asteroid").getAsBoolean());
                JsonArray cad = jsNeo.get("close_approach_data").getAsJsonArray();
                for (JsonElement jecad : cad){
                    JsonObject jscad = jecad.getAsJsonObject();
                    neo.setCloseApproachDate(jscad.get("close_approach_date").getAsString());
                    neo.setRelativeVelocity(jscad.get("relative_velocity").getAsJsonObject().get("kilometers_per_hour").getAsDouble());
                    neo.setMissDistance(jscad.get("miss_distance").getAsJsonObject().get("kilometers").getAsBigDecimal());
                    neo.setOrbitingBody(jscad.get("orbiting_body").getAsString());
                }
                neo.setSentryObject(jsNeo.get("is_sentry_object").getAsBoolean());
                listNeo.add(neo);
                System.out.println(neo.toString());
            }
        } 
        System.out.println("Jumlah Neo dalam list:"+listNeo.size());
        listNeo.sort((o1,o2) -> o1.getMissDistance().compareTo(o2.getMissDistance()));
        List<Neo> top10neol = new ArrayList<Neo>();
        int count = 0;
        for (Neo neo : listNeo) {
            top10neol.add(neo);
            count++;
            System.out.println(neo.toString());
            if (count >= 10){
                break;
            }
        }
        return top10neol;
    }

    @GetMapping("/browsebyradius")
    public List<Neo> browsebyradius(@RequestParam(value = "start_date", required = true) String startDate,
            @RequestParam(value = "end_date", required = true) String endDate,
            @RequestParam(value = "radius", required = true) int radius) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try{
            DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(startDate);
            DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(endDate);
            Date sDate = sdf.parse(startDate);
            Date eDate = sdf.parse(endDate);
            if (eDate.before(sDate)){
                throw new BadRequestException("Invalid Parameter, end_date can't be earlier than start_date ");
            }
            long dateDiff = (eDate.getTime() - sDate.getTime())/3600/1000/24;
            if (dateDiff > 7){
                throw new BadRequestException("Invalid Parameter, date range can't more than 7 days");
            }
            if (radius < 0){
                throw new BadRequestException("Invalid Parameter, radius can't be negative int");
            }
        
            NeoapiClient neoApiClient = new NeoapiClient(this.URLClient, this.apiKeyClient);
            String neodata = neoApiClient.neodata(startDate, endDate);
            //JSONObject jsonNeodata= new JSONObject(neodata);
            JsonObject jsonNeodata = JsonParser.parseString(neodata).getAsJsonObject();
            int elementCount = jsonNeodata.get("element_count").getAsInt();
            System.out.println("Jumlah NEO:" + elementCount);
            JsonObject jsonNEO = jsonNeodata.get("near_earth_objects").getAsJsonObject();
            Set<String> it = jsonNEO.keySet();
            List<Neo> listNeo = new ArrayList<Neo>();
            for (String keyDate : it) {
                System.out.println("NEO Date:"+keyDate);
                JsonArray JsonListNEO = jsonNEO.get(keyDate).getAsJsonArray();
                for (JsonElement jeNeo : JsonListNEO) {
                    JsonObject jsNeo = jeNeo.getAsJsonObject();
                    //String id = jsNeo.get("id").getAsString();
                    Neo neo = new Neo();
                    //neo.setCloseApproachDate(jsNeo.get().getAsString());
                    neo.setId(jsNeo.get("id").getAsString());
                    neo.setName(jsNeo.get("name").getAsString());
                    neo.setMagnitude(jsNeo.get("absolute_magnitude_h").getAsFloat());
                    NeoDiameter neoD = new NeoDiameter();
                    neoD.setMax_diameter(jsNeo.get("estimated_diameter").getAsJsonObject().get("kilometers").getAsJsonObject().get("estimated_diameter_max").getAsDouble());
                    neoD.setMin_diameter(jsNeo.get("estimated_diameter").getAsJsonObject().get("kilometers").getAsJsonObject().get("estimated_diameter_min").getAsDouble());
                    neo.setNeoDiameter(neoD);
                    neo.setPha(jsNeo.get("is_potentially_hazardous_asteroid").getAsBoolean());
                    JsonArray cad = jsNeo.get("close_approach_data").getAsJsonArray();
                    for (JsonElement jecad : cad){
                        JsonObject jscad = jecad.getAsJsonObject();
                        neo.setCloseApproachDate(jscad.get("close_approach_date").getAsString());
                        neo.setRelativeVelocity(jscad.get("relative_velocity").getAsJsonObject().get("kilometers_per_hour").getAsDouble());
                        neo.setMissDistance(jscad.get("miss_distance").getAsJsonObject().get("kilometers").getAsBigDecimal());
                        neo.setOrbitingBody(jscad.get("orbiting_body").getAsString());
                    }
                    neo.setSentryObject(jsNeo.get("is_sentry_object").getAsBoolean());
                    listNeo.add(neo);
                    //System.out.println(neo.toString());
                }
            } 
            System.out.println("Jumlah Neo dalam:"+listNeo.size());
            listNeo.sort((o1,o2) -> o1.getMissDistance().compareTo(o2.getMissDistance()));
            List<Neo> inRadiusNeo = new ArrayList<Neo>();
            for (Neo neo : listNeo) {
                if (radius >= neo.getMissDistance().intValue()){
                    inRadiusNeo.add(neo);
                }
                else{
                    break;
                }           
                //System.out.println(neo.toString());
            }
            System.out.println("Jumlah Neo dalam radius:"+inRadiusNeo.size());
            return inRadiusNeo;
        }
        catch (DateTimeParseException dtpe){
            System.out.println("Masuk catch DTPE");
            throw new BadRequestException("Invalid Parameter, Invalid Date Format");
        }
        catch (ParseException dtpe){
            System.out.println("PE");
            throw new BadRequestException("Invalid Parameter, Invalid Date Format");
        }
        catch (UnauthorizedException ue){
            throw new UnauthorizedException("Invalid API Key");
        }
    }

    @GetMapping("/simple")
    public List<NeoSimple> simple(@RequestParam(value = "start_date", required = true) String startDate,
            @RequestParam(value = "end_date", required = true) String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try{
            DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(startDate);
            DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(endDate);
            Date sDate = sdf.parse(startDate);
            Date eDate = sdf.parse(endDate);
            if (eDate.before(sDate)){
                throw new BadRequestException("Invalid Parameter, end_date can't be earlier than start_date ");
            }
            long dateDiff = (eDate.getTime() - sDate.getTime())/3600/1000/24;
            if (dateDiff > 7){
                throw new BadRequestException("Invalid Parameter, date range can't more than 7 days");
            }
        
            NeoapiClient neoApiClient = new NeoapiClient(this.URLClient, this.apiKeyClient);
            String neodata = neoApiClient.neodata(startDate, endDate);
            //JSONObject jsonNeodata= new JSONObject(neodata);
            JsonObject jsonNeodata = JsonParser.parseString(neodata).getAsJsonObject();
            int elementCount = jsonNeodata.get("element_count").getAsInt();
            System.out.println("Jumlah NEO:" + elementCount);
            JsonObject jsonNEO = jsonNeodata.get("near_earth_objects").getAsJsonObject();
            Set<String> it = jsonNEO.keySet();
            List<NeoSimple> listNeo = new ArrayList<NeoSimple>();
            for (String keyDate : it) {
                System.out.println("NEO Date:"+keyDate);
                JsonArray JsonListNEO = jsonNEO.get(keyDate).getAsJsonArray();
                for (JsonElement jeNeo : JsonListNEO) {
                    JsonObject jsNeo = jeNeo.getAsJsonObject();
                    //String id = jsNeo.get("id").getAsString();
                    NeoSimple neo = new NeoSimple(jsNeo);
                    //neo.setCloseApproachDate(jsNeo.get().getAsString());
                    listNeo.add(neo);
                    //System.out.println(neo.toString());
                }
            } 
            System.out.println("Jumlah Neo dalam:"+listNeo.size());
            return listNeo;
        }
        catch (DateTimeParseException dtpe){
            System.out.println("Masuk catch DTPE");
            throw new BadRequestException("Invalid Parameter, Invalid Date Format");
        }
        catch (ParseException dtpe){
            System.out.println("PE");
            throw new BadRequestException("Invalid Parameter, Invalid Date Format");
        }
        catch (UnauthorizedException ue){
            throw new UnauthorizedException("Invalid API Key");
        }
    }
    
}
