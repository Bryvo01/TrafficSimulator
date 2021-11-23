import java.util.ArrayList;
import java.util.List;

public class AdvanceCarsEvent extends Event{
    private final RoadData roadData;
    private final List<RoadData> roadOrder;
    private final List<Car> roadStatus;
    private final List<Car> destinations;
    private final List<Car> newRoadStatus;
    private final boolean greenStatus;//<--------------COMMENT OUT TO IGNORE GREEN LIGHT
    private final Integer timeStep;

    public AdvanceCarsEvent(Integer eventType, Integer timeStep, RoadData roadData, List<RoadData> roadOrder) {
        super(eventType, timeStep);
        this.roadData = roadData;
        this.roadOrder = roadOrder;
        this.roadStatus = roadData.getRoadFill();
        this.destinations = roadData.getCarQueue();
        this.newRoadStatus = new ArrayList<Car>(roadStatus.size());
        this.greenStatus = roadData.isGreenStatus();//<--------------COMMENT OUT TO IGNORE GREEN LIGHT
        this.timeStep = timeStep;
        newRoadStatus.addAll(roadStatus);
    }
    @Override
    public List<Object> advanceCars() {
        for(Car car : destinations){
            if(!(car == null)) { car.traveledPlus();}
            }
        List<Object> returnList = new ArrayList<Object>(4);
        returnList.add(0, false);
        if(!(roadStatus.get(0) == null)){
            Node newDestination = roadStatus.get(0).getDestNode();
            //Need to make this an event
            if((roadData.getToNode().equals(newDestination))) {
                System.out.println("CYCLE "+ timeStep + " - Car successfully traveled from " +
                        roadStatus.get(0).getStart() + " to " + roadStatus.get(0).getDest() +
                        " in " + roadStatus.get(0).getTraveled() + " time steps.");
            } else {
                Node fromNode = roadData.getToNode();
                for(RoadData edgy : roadOrder) {
                    if(edgy.getToNode().equals(newDestination)) {
                        Node toNode = edgy.getToNode();
                        returnList.set(0, true);
                        returnList.add(fromNode);
                        returnList.add(toNode);
                        returnList.add(roadStatus.get(0));
                    }
                }
            }
        }
        if((roadStatus.get(roadStatus.size()-1) == null) &&
                greenStatus &&        //<--------------COMMENT OUT TO IGNORE GREEN LIGHT
                !destinations.isEmpty()) {
            destinations.get(0).traveledPlus();
            newRoadStatus.set(roadStatus.size() - 1, destinations.remove(0));
        } else newRoadStatus.set(roadStatus.size() - 1, null);
        for(int i = roadStatus.size()-2; i >= 0; i--) {
            if(!(roadStatus.get(i+1) == null)) roadStatus.get(i+1).traveledPlus();
            newRoadStatus.set(i, roadStatus.get(i+1));
        }
        roadData.setRoadFill(newRoadStatus);
        return returnList;
    }
}
