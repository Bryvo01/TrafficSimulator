public class Car {
    private final Node start;
    private final Node dest;
    private Integer traveled;

    public Car(Node start, Node dest){
        this.start = start;
        this.dest = dest;
        this.traveled = 0; //Defaults to 0
    }

    public Integer getStart() {
        return start.getNodeData();
    }

    public Integer getDest() {
        return dest.getNodeData();
    }

    public Node getDestNode() {return dest; }

    public Integer getTraveled() {
        return traveled;
    }

    public void traveledPlus() {
        traveled++;
    }
}
