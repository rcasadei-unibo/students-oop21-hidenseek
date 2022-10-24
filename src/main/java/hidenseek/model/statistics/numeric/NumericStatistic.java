package hidenseek.model.statistics.numeric;

import org.w3c.dom.Element;
import hidenseek.model.statistics.AbstractStatistic;

public class NumericStatistic extends AbstractStatistic<Numeric> {
    
    private final String units;
    
    public NumericStatistic(String name, String tag, String title, String units, Element element) {
        super(name, tag, title, new Numeric(0), element);
        
        this.units = units;
    }
    
    public NumericStatistic(String name, String tag, String title, String units) {
        this(name, tag, title, units, null);
    }
    
    public NumericStatistic(String name, String tag, String title) {
        this(name, tag, title, "", null);
    }

    public String getValue() {
        return getProperty().toString();
    }
    
    public String getUnits() {
        return units;
    }


}
