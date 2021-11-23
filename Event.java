import java.util.*;

public class Event implements Comparable<Event> {
    private Integer eventType;
    private double timeStep = Double.POSITIVE_INFINITY;
    private final RoadData roadData;
    private List<RoadData> roadOrder;
    boolean printable;
    /*
    eventType = 1: ADD_CAR_EVENT
    eventType = 2: PRINT_ROADS_EVENT
    eventType = 3: Car successfully traveled
    eventType = 4: advance cars
    eventType = 5: change light
     */

    public Event(Integer eventType, Integer timeStep) {
        this.eventType = eventType;
        this.timeStep = timeStep;
        this.roadData = null;
        this.roadOrder = null;
        this.printable = false;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setTimeStep(double timeStep) {
        this.timeStep = timeStep;
    }

    public double getTimeStep() {
        return timeStep;
    }

    public RoadData getRoadData() {
        return roadData;
    }

    public List<RoadData> getRoadOrder() {
        return roadOrder;
    }

    public void setRoadOrder(List<RoadData> roadOrder) {
        this.roadOrder = roadOrder;
    }

    public void setPrintable() {printable = !printable;}

    public List<Object> advanceCars() {

        List<Object> returnList = new ArrayList<Object>(2);
        returnList.set(0, false);
        return returnList;
    }

    @Override
    public int compareTo(Event o) {
        if(o.timeStep > this.timeStep) return -1;
        if(o.timeStep < this.timeStep) return  1;
        if(o.timeStep == this.timeStep) {
            if(o.eventType > this.eventType) return -1;
            if(o.eventType < this.eventType) return 1;
        }
        return 0;
    }

    public void changeGreenStatus() {
    }
}

