import java.util.List;

public class PrintRoadsEvent extends Event{
    public PrintRoadsEvent(Integer eventType, Integer timeStep, List<RoadData> roadOrder) {
        super(eventType, timeStep);
        super.setRoadOrder(roadOrder);
        super.setPrintable();
        //        System.out.println("CYCLE " + timeStep + " - PRINT_ROADS_EVENT - Current contents of the roads:");
//        for(RoadData road: roadOrder) {
//            System.out.println("Cars on the road from " +
//                    road.getEdge().getFrom() + " to "  + road.getEdge().getTo() +":");
//            System.out.println(road.getRoadFill());
//        }
//        System.out.println("\n");
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
