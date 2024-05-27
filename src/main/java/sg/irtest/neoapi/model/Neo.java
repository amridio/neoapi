package sg.irtest.neoapi.model;

import java.math.BigDecimal;

public class Neo {
    
    String id;
    String name;
    

    float magnitude;
    NeoDiameter neoDiameter;
    boolean isPha;
    String closeApproachDate;
    double relativeVelocity;
    BigDecimal missDistance;
    String orbitingBody;
    boolean isSentryObject;

    public Neo(String id, float magnitude, NeoDiameter neoDiameter, boolean isPha, String closeApproachDate,
            double relativeVelocity, BigDecimal missDistance, String orbitingBody, boolean isSentryObject) {
        this.id = id;
        this.magnitude = magnitude;
        this.neoDiameter = neoDiameter;
        this.isPha = isPha;
        this.closeApproachDate = closeApproachDate;
        this.relativeVelocity = relativeVelocity;
        this.missDistance = missDistance;
        this.orbitingBody = orbitingBody;
        this.isSentryObject = isSentryObject;
    }

    public Neo(){

    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getMagnitude() {
        return magnitude;
    }
    public void setMagnitude(float magnitude) {
        this.magnitude = magnitude;
    }
    public NeoDiameter getNeoDiameter() {
        return neoDiameter;
    }
    public void setNeoDiameter(NeoDiameter neoDiameter) {
        this.neoDiameter = neoDiameter;
    }
    public boolean isPha() {
        return isPha;
    }
    public void setPha(boolean isPha) {
        this.isPha = isPha;
    }
    public String getCloseApproachDate() {
        return closeApproachDate;
    }
    public void setCloseApproachDate(String closeApproachDate) {
        this.closeApproachDate = closeApproachDate;
    }
    public double getRelativeVelocity() {
        return relativeVelocity;
    }
    public void setRelativeVelocity(double relativeVelocity) {
        this.relativeVelocity = relativeVelocity;
    }
    public BigDecimal getMissDistance() {
        return missDistance;
    }
    public void setMissDistance(BigDecimal missDistance) {
        this.missDistance = missDistance;
    }
    public String getOrbitingBody() {
        return orbitingBody;
    }
    public void setOrbitingBody(String orbitingBody) {
        this.orbitingBody = orbitingBody;
    }
    public boolean isSentryObject() {
        return isSentryObject;
    }
    public void setSentryObject(boolean isSentryObject) {
        this.isSentryObject = isSentryObject;
    }

    @Override
    public String toString() {
        return "Neo [id=" + id + ", magnitude=" + magnitude + ", neoDiameter=" + neoDiameter.toString() + ", isPha=" + isPha
                + ", closeApproachDate=" + closeApproachDate + ", relativeVelocity=" + relativeVelocity
                + ", missDistance=" + missDistance + ", orbitingBody=" + orbitingBody + ", isSentryObject="
                + isSentryObject + "]";
    }
    
}
