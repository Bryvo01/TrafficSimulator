import java.util.*;

public class RoadData {
    private final Edge edge;
    private final Integer greenOn;
    private final Integer greenOff;
    private Boolean greenStatus; // true=green, false=red
    private final Integer cycleResets;
    private List<Car> roadFill;
    private List<Car> carQueue;
    private final Node fromNode;
    private final Node toNode;

    public RoadData(Edge tempEdge, Integer greenOn, Integer greenOff, Integer cycleResets) {
        this.edge = tempEdge;
        this.fromNode = edge.getFrom();
        this.toNode = edge.getTo();
        this.greenOn = greenOn;
        this.greenOff = greenOff;
        this.greenStatus = false;
        this.cycleResets = cycleResets;
        int tempLength = (int) tempEdge.getLength();
        this.roadFill = new ArrayList<Car>();
        for(int i=0;i<tempLength;i++){
            roadFill.add(null);
        }
        this.carQueue = new ArrayList<Car>();
    }

    public Edge getEdge() {
        return edge;
    }

    public Node getFromNode() {
        return fromNode;
    }

    public Node getToNode() {
        return toNode;
    }

    public int getGreenOff() {
        return greenOff;
    }

    public int getCycleResets() {
        return cycleResets;
    }

    public Boolean getGreenStatus() {
        return greenStatus;
    }

    public void setGreenStatus(boolean greenStatus) {
        this.greenStatus = greenStatus;
    }

    public boolean isGreenStatus() {
        return greenStatus;
    }

    public String getRoadFillString() {
        StringBuilder result = new StringBuilder();
        for(Car i : roadFill) {
            if(i == null) {
                result.append("- ");
            } else {
                result.append(i.getDest()).append(" ");
            }
        }
        return result.toString();
    }

    public List<Car> getRoadFill() {
        return roadFill;
    }

    public void setRoadFill(List<Car> roadFill) {
        this.roadFill = roadFill;
    }

    public void setCarQueue(List<Car> carQueue) {
        if(carQueue.isEmpty()) {
            this.carQueue = carQueue;
        } else {
            this.carQueue.addAll(carQueue);
        }
    }

    public List<Car> getCarQueue() {
        return carQueue;
    }

    public boolean isRoadEmpty() {
        boolean returnBoolean = true;
        for (Car i : roadFill) {
            if (!(i == null)) {
                returnBoolean = false;
                break;
            }
        }
        return returnBoolean;
    }

    public boolean isCarQueueEmpty() {return  carQueue.isEmpty();}
}

