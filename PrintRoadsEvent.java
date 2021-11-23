import java.util.List;

public class PrintRoadsEvent extends Event{
    public PrintRoadsEvent(Integer eventType, Integer timeStep, List<RoadData> roadOrder) {
        super(eventType, timeStep);
        super.setRoadOrder(roadOrder);
        super.setPrintable();
    }

    @Override
    public double getTimeStep() {
        return super.getTimeStep();
    }

    @Override
    public Integer getEventType() {
        return super.getEventType();
    }

    @Override
    public List<RoadData> getRoadOrder() {
        return super.getRoadOrder();
    }
}
