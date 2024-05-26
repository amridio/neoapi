package sg.irtest.neoapi.model;

public class Neo {
    
    String id;
    float magnitude;
    NeoDiameter neoDiameter;
    boolean isPha;
    String closeApproachDate;
    float relativeVelocity;
    float missDistance;
    String orbitingBody;
    boolean isSentryObject;

    public Neo(String id, float magnitude, NeoDiameter neoDiameter, boolean isPha, String closeApproachDate,
            float relativeVelocity, float missDistance, String orbitingBody, boolean isSentryObject) {
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

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    public float getRelativeVelocity() {
        return relativeVelocity;
    }
    public void setRelativeVelocity(float relativeVelocity) {
        this.relativeVelocity = relativeVelocity;
    }
    public float getMissDistance() {
        return missDistance;
    }
    public void setMissDistance(float missDistance) {
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
