package sg.irtest.neoapi.model;

public class NeoDiameter {
    double max_diameter;
    double min_diameter;

    public NeoDiameter(float max_diameter, float min_diameter) {
        this.max_diameter = max_diameter;
        this.min_diameter = min_diameter;
    }

    public NeoDiameter() {
    
    }

    public double getMax_diameter() {
        return max_diameter;
    }
    
    public void setMax_diameter(double max_diameter) {
        this.max_diameter = max_diameter;
    }
    public double getMin_diameter() {
        return min_diameter;
    }
    public void setMin_diameter(double min_diameter) {
        this.min_diameter = min_diameter;
    }

    @Override
    public String toString() {
        return "NeoDiameter [max_diameter=" + max_diameter + ", min_diameter=" + min_diameter + "]";
    }
}
