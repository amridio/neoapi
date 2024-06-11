package sg.irtest.neoapi.model;

import org.apache.tomcat.util.json.JSONParser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class NeoSimple {
    private String id;
    private String neo_reference_id;
    private String name;


    public NeoSimple() {   

    }

    public NeoSimple(JsonObject jsNeo) {   
        this.id = jsNeo.get("id").getAsString();
        this.neo_reference_id = jsNeo.get("neo_reference_id").getAsString();
        this.name = jsNeo.get("name").getAsString();
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNeo_reference_id() {
        return neo_reference_id;
    }
    public void setNeo_reference_id(String neo_reference_id) {
        this.neo_reference_id = neo_reference_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    
}
