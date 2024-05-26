package sg.irtest.neoapi.model;

public class NeoDiameter {
    float max_diameter;
    float min_diameter;

    public NeoDiameter(float max_diameter, float min_diameter) {
        this.max_diameter = max_diameter;
        this.min_diameter = min_diameter;
    }
    public float getMax_diameter() {
        return max_diameter;
    }
    
    public void setMax_diameter(float max_diameter) {
        this.max_diameter = max_diameter;
    }
    public float getMin_diameter() {
        return min_diameter;
    }
    public void setMin_diameter(float min_diameter) {
        this.min_diameter = min_diameter;
    }

    @Override
    public String toString() {
        return "NeoDiameter [max_diameter=" + max_diameter + ", min_diameter=" + min_diameter + "]";
    }
}
