import java.util.ArrayList;
import java.util.List;

public class AdvanceCarsEvent extends Event{
    private final RoadData roadData;
    private final List<RoadData> roadOrder;
    private final List<Car> roadStatus;
    private final List<Car> destinations;
    private final List<Car> newRoadStatus;
    //private final List<Double> carDistances;
    private final boolean greenStatus;//<--------------COMMENT OUT TO IGNORE GREEN LIGHT
    private final Integer timeStep;

    public AdvanceCarsEvent(Integer eventType, Integer timeStep, RoadData roadData, List<RoadData> roadOrder) {
        super(eventType, timeStep);
        this.roadData = roadData;
        this.roadOrder = roadOrder;
        this.roadStatus = roadData.getRoadFill();
        this.destinations = roadData.getCarQueue();
        this.newRoadStatus = new ArrayList<Car>(roadStatus.size());
        //this.carDistances = new ArrayList<Double>();
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
        //Integer[] temp = new Integer[newRoadStatus.size()];
        //System.out.println("RoadStatus 0: " + roadStatus.get(0));
        if(!(roadStatus.get(0) == null)){
            //roadStatus.get(0).traveledPlus();
            Node newDestination = roadStatus.get(0).getDestNode();
            //System.out.println("The next if: " +(roadData.getToNode().equals(newDestination)));
            if((roadData.getToNode().equals(newDestination))) {
                //need to turn this into an event
                System.out.println("CYCLE "+ timeStep + " - Car successfully traveled from " +
                        roadStatus.get(0).getStart() + " to " + roadStatus.get(0).getDest() +
                        " in " + roadStatus.get(0).getTraveled() + " time steps.");
                //carDistances.add((double)roadStatus.get(0).getTraveled());
            } else {
                //System.out.println(roadOrder.get(0));
                //Edge current = roadData.getEdge();
                Node fromNode = roadData.getToNode();
                for(RoadData edgy : roadOrder) {
                    //System.out.println("Edgy: "+ (edgy.getToNode().equals(newDestination)));
                    if(edgy.getToNode().equals(newDestination)) {
                        Node toNode = edgy.getToNode();
                        //System.out.println(toNode);
//                        List<Integer> addQueue = new ArrayList<>();
//                        addQueue.add(roadStatus.get(0));
//                        System.out.println("edgyFrom " + edgy.getEdge().getFrom() + "edgyTo " +
//                                edgy.getEdge().getTo() + "edgyQ " + edgy.getCarQueue());
                        //AddCarEvent addCarEvent = new AddCarEvent(1, timeStep, edgy, addQueue);
                        returnList.set(0, true);
                        returnList.add(fromNode);
                        returnList.add(toNode);
                        returnList.add(roadStatus.get(0));
                    }
                }

            }
            //System.out.println("CYCLE: "+ timeStep + "Hey look ma - I made it!!");
        }
        //System.out.println((roadStatus.get(roadStatus.size()-1)==null) + " " + greenStatus + " " + !destinations.isEmpty());
        if((roadStatus.get(roadStatus.size()-1) == null) &&
                greenStatus &&        //<--------------COMMENT OUT TO IGNORE GREEN LIGHT
                !destinations.isEmpty()) {
            destinations.get(0).traveledPlus();
            newRoadStatus.set(roadStatus.size() - 1, destinations.remove(0));
            //System.out.println("newRoadStatus: " + newRoadStatus);
        } else newRoadStatus.set(roadStatus.size() - 1, null);
        for(int i = roadStatus.size()-2; i >= 0; i--) {
            if(!(roadStatus.get(i+1) == null)) roadStatus.get(i+1).traveledPlus();
            newRoadStatus.set(i, roadStatus.get(i+1));
        }
//        System.out.println("Cycle: " + timeStep + " Road: " + roadData.getFromNode() + "," +
//                roadData.getToNode() + " Status: " + newRoadStatus + " GreenLight: " + roadData.getGreenStatus());
        roadData.setRoadFill(newRoadStatus);
        return returnList;
    }

//    public List<Double> getCarDistances() {
//        return carDistances;
//    }
}
