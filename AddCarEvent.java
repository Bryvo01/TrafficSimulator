import java.util.List;

public class AddCarEvent extends Event {

    private RoadData roadData;

    public AddCarEvent(Integer eventType, Integer timeStep, RoadData roadData, List<Car> destinations) {
        super(eventType, timeStep);
        this.roadData = roadData;
        roadData.setCarQueue(destinations);
    }

    @Override
    public RoadData getRoadData() {
        return roadData;
    }

    @Override
    public Integer getEventType() {
        return super.getEventType();
    }

    @Override
    public double getTimeStep() {
        return super.getTimeStep();
    }

    @Override
    public void setPrintable() {
        super.setPrintable();
    }
}
