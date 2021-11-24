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
        //if the first spot on the road is a car
        if(!(roadStatus.get(0) == null)){
            Node newDestination = roadStatus.get(0).getDestNode();
            //Need to make this an event
            if((roadData.getToNode().equals(newDestination))) {
                System.out.println("CYCLE "+ (timeStep-1) + " - Car successfully traveled from " +
                        roadStatus.get(0).getStart() + " to " + roadStatus.get(0).getDest() +
                        " in " + roadStatus.get(0).getTraveled() + " time steps.");
                //mark the new spot as open
                newRoadStatus.set(0, null);
            } else {
                //Not home yet, check if there is a free spot on the next road
                Node fromNode = roadData.getToNode();
                for(RoadData edgy : roadOrder) {
                    if(edgy.getToNode().equals(newDestination)) {
                        Node toNode = edgy.getToNode();
                        returnList.set(0, true);
                        returnList.add(fromNode);
                        returnList.add(toNode);
                        returnList.add(roadStatus.get(0));
                        List<Car> edgyFill = edgy.getRoadFill();
                        //check if green, road is open and Q is empty
                        if(edgy.getGreenStatus() && (edgyFill.get(edgyFill.size()-1) == null) &&
                                edgy.isCarQueueEmpty()) {
                            edgyFill.set(edgyFill.size() - 1, roadStatus.get(0));
                            edgy.setRoadFill(edgyFill);
                        }
                    }
                }
                //no free spot, leave the car at the front
                newRoadStatus.set(0, roadStatus.get(0));
                roadStatus.get(0).traveledPlus();
            }
        }
        //the first spot wasn't a car, move everyone else
        //check the last spot, if it is empty and the light is green grab something from the Q
        if((roadStatus.get(roadStatus.size()-1) == null) &&
                //greenStatus &&        //<--------------COMMENT OUT TO IGNORE GREEN LIGHT
                !destinations.isEmpty()) {
            destinations.get(0).traveledPlus();
            newRoadStatus.set(roadStatus.size() - 1, destinations.remove(0));
            //the light is red or the Q is full, leave the end empty
        } else newRoadStatus.set(roadStatus.size() - 1, null);
        //now move every other car.
//        for(int i = 1; i < roadStatus.size()-1; i++) {
//            if(!(roadStatus.get(i) == null)) {
//                newRoadStatus.set(i, roadStatus.get(i));
//                roadStatus.get(i).traveledPlus();
//            } else {
//                newRoadStatus.set(i, roadStatus.get(i+1));
//                if(!(roadStatus.get(i+1) == null)) roadStatus.get(i+1).traveledPlus();
//                if(i+1 == (roadStatus.size() - 1)) {
//                    break;
//                } else {
//                    newRoadStatus.set(i+1, null);
//                }
//            }
//        }
        for(int i = roadStatus.size()-2; i >= 0; i--) {
            if(!(roadStatus.get(i+1) == null)) roadStatus.get(i+1).traveledPlus();
            newRoadStatus.set(i, roadStatus.get(i+1));
        }
        roadData.setRoadFill(newRoadStatus);
        return returnList;
    }
}
