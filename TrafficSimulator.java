import java.io.*;
import java.util.*;

public class TrafficSimulator
{
    public static void simulate( String inputFile )
    {
        //TODO: read in the graph information from the file and simulate traffic in the city
        File tempFile = new File(inputFile);
        List<String> tempList = new ArrayList<String>();
        List<String> inputList;
        List<RoadData> roadOrder = new ArrayList<RoadData>();
        List<Integer> carDestinations = new ArrayList<Integer>();
        //List<List<Object>> carCommandList = new ArrayList<List<Object>>();
        //List<Double> carDistances = new ArrayList<Double>();
        List<Car> allCars = new ArrayList<Car>();
        List<Integer> printCommandCycleNumber = new ArrayList<Integer>();
        PriorityQueue<Event> eventPriorityQueue = new PriorityQueue<Event>();
        Graph trafficGraph = new Graph();
        Integer cycle = 0;
        try (Scanner myReader = new Scanner(tempFile)) {
            while(myReader.hasNextLine()) {
                tempList.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        inputList = removeEmpty(tempList);
        String[] temp = inputList.remove(0).split("\\s+");
        Integer numVertices = Integer.parseInt(temp[0]);
        //Integer numEdges = Integer.parseInt(temp[1]);
        for(Integer i=0;i<numVertices;i++) {
            trafficGraph.addNode(i);
        }
        for(Integer i=0;i<numVertices;i++) {
            Integer incomingRoads = Integer.parseInt(inputList.remove(0));
            if(incomingRoads == 0) ;
            else {
                for(Integer j=0;j<incomingRoads;j++) {
                    String[] roadInfo = inputList.remove(0).split("\\s+");
                    Integer fromVertexInt = Integer.parseInt(roadInfo[0]);
                    Node fromVertex = trafficGraph.getNode(fromVertexInt);
                    Node toVertex = trafficGraph.getNode(i);
                    Integer roadLength = Integer.parseInt(roadInfo[1]);
                    Integer greenOn = Integer.parseInt(roadInfo[2]);
                    Integer greenOff = Integer.parseInt(roadInfo[3]);
                    Integer cycleResets = Integer.parseInt(roadInfo[4]);
                    trafficGraph.addEdge(fromVertex, toVertex, roadLength);
                    Edge tempEdge = trafficGraph.getEdge(fromVertex, toVertex);
                    RoadData tempRoadData = new RoadData(tempEdge, greenOn, greenOff, cycleResets);
                    roadOrder.add(tempRoadData);
                    GreenLightEvent startLight = new GreenLightEvent(5, greenOn, tempRoadData);
                    GreenLightEvent stopLight = new GreenLightEvent(5, greenOff, tempRoadData);
                    eventPriorityQueue.add(startLight);
                    eventPriorityQueue.add(stopLight);
                }
            }
            //System.out.println(incomingRoads);
        }
        Integer numCarCommands = Integer.parseInt(inputList.remove(0));
        if(numCarCommands.equals(0)) ;
        else {
            for (Integer k=0; k<numCarCommands;k++) {
                String[] carCommand = inputList.remove(0).split("\\s+");
                List<Object> tempList2 = new ArrayList<Object>();
                List<Integer> tempCarDest = new ArrayList<Integer>();
                Integer fromCarVertex = Integer.parseInt(carCommand[0]);
                Node fromCarVertexNode = trafficGraph.getNode(fromCarVertex);
                Integer toCarVertex = Integer.parseInt(carCommand[1]);
                //Node toCarVertexNode = trafficGraph.getNode(toCarVertex);
                Integer cycleStart = Integer.parseInt(carCommand[2]);
                makeIntList(inputList, tempCarDest);
                List<Node> tempNodeDestination = new ArrayList<Node>();
                for(Integer i : tempCarDest) {
                    Node tempNode = trafficGraph.getNode(i);
                    tempNodeDestination.add(tempNode);
                }
                List<Car> tempCarList = new ArrayList<Car>();
                for(Node n : tempNodeDestination) {
                    Car aNewCar = new Car(fromCarVertexNode, n);
                    tempCarList.add(aNewCar);
                    allCars.add(aNewCar);
                }
                Edge tempEdge = localGetEdge(fromCarVertex, toCarVertex, trafficGraph);
                for(RoadData rd : roadOrder) {
                    if (tempEdge == rd.getEdge()) {
                        Event newEvent = new AddCarEvent(1, cycleStart, rd, tempCarList);
                        newEvent.setPrintable();
                        eventPriorityQueue.add(newEvent);
                    }
                }
//                tempList2.add(fromCarVertex);
//                tempList2.add(toCarVertex);
//                tempList2.add(cycleStart);
//                tempList2.add(tempCarDest);
//                carCommandList.add(tempList2);
            }
        }
        makeIntList(inputList, printCommandCycleNumber);
        for(Integer item : printCommandCycleNumber) {
            //System.out.println("print command: " + item);
            Event newEvent = new PrintRoadsEvent(6, item, roadOrder);
            //newEvent.setPrintable();
            eventPriorityQueue.add(newEvent);
            //System.out.println(newEvent.getTimeStep());
        }
        //System.out.println(eventPriorityQueue.size());
        Boolean roadsEmpty = false;
        while(!eventPriorityQueue.isEmpty() && !roadsEmpty) {
            //System.out.println(eventPriorityQueue.size() + "next: " + eventPriorityQueue.peek().getEventType());
            while (true) {
                if(eventPriorityQueue.isEmpty()) break;
                if (!(eventPriorityQueue.peek().getTimeStep() <= cycle)) break;
                try {
//                    System.out.println(eventPriorityQueue.peek().getTimeStep() + "<-timestep|cycle->" + cycle + " Event: " +
//                            eventPriorityQueue.peek().getEventType());
                    Event tempEvent = eventPriorityQueue.poll();
                    assert tempEvent != null;
                    Integer eventType = tempEvent.getEventType();
                    switch (eventType){
                        case 1:
                            if(tempEvent.printable) {
                                System.out.println("CYCLE " + (int) tempEvent.getTimeStep() + " - ADD_CAR_EVENT - Cars enqueued on road from " +
                                        tempEvent.getRoadData().getEdge().getFrom() + " to " + tempEvent.getRoadData().getEdge().getTo());
                            }
                            break;
                        case 6:
                            if(tempEvent.printable) {
                                System.out.println("\nCYCLE " + (int) tempEvent.getTimeStep() + " - PRINT_ROADS_EVENT - Current contents of the roads:");
                                for (RoadData road : tempEvent.getRoadOrder()) {
                                    System.out.println("Cars on the road from " +
                                            road.getEdge().getFrom() + " to " + road.getEdge().getTo() + ":");
                                    System.out.println(road.getRoadFillString());
                                }
                                System.out.println("\n");
                            }
                            break;
                        case 3:
                            if(tempEvent.printable) {
                                System.out.println("CYCLE " + tempEvent.getTimeStep() + "Car successfully traveled from -- to -- in -- steps.");
                            }
                            break;
                        case 4:
                            List advance = tempEvent.advanceCars();
                            //System.out.println("Case 4 start: " + (boolean) advance.get(0));
                            if((boolean) advance.get(0)) {
                                Node fromNode = (Node) advance.get(1);
                                Node toNode = (Node) advance.get(2);
                                Car toCar = (Car) advance.get(3);
                                Node nextNode = trafficGraph.getNextOnPath(fromNode, toNode);
                                for (RoadData rd : roadOrder) {
                                    //find the next road to go on
                                    //System.out.println("other if: " + (rd.getToNode().equals(nextNode) && rd.getFromNode().equals(fromNode)));
                                    if (rd.getToNode().equals(nextNode) && rd.getFromNode().equals(fromNode)) {
                                        //if the light is green and there is space, drive onto the road
                                        List tempRoadFill = rd.getRoadFill();
                                        //System.out.println("before if: " + tempRoadFill + "GreenLight: " +rd.getGreenStatus());
                                        if (rd.getGreenStatus() && (tempRoadFill.get(tempRoadFill.size() - 1) == null) &&
                                                rd.isCarQueueEmpty()) {
                                            //System.out.println("inside if: " + tempRoadFill);
                                            tempRoadFill.set(tempRoadFill.size() - 1, toCar);
                                            rd.setRoadFill(tempRoadFill);
                                            eventPriorityQueue.add(new AdvanceCarsEvent(4, cycle, rd, roadOrder));
                                        } else {
                                            List<Car> newDestination = new ArrayList<Car>();
                                            newDestination.add(toCar);
                                            eventPriorityQueue.add(new AddCarEvent(1, cycle, rd, newDestination));
                                        }
                                    }
                                    //Event tempAddCar = (Event) advance.get(1);
                                    //Node nextNode = trafficGraph.getNextOnPath(tempAddCar.getRoadData().getEdge().getFrom(), )
                                    //eventPriorityQueue.add((Event) advance.get(1));
                                }
                            }
                            break;
                        case 5:
                            tempEvent.changeGreenStatus();
                            //System.out.println("Light change!!  " + tempEvent.getRoadData().getGreenStatus());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            cycle++;
            List<Boolean> findAll = new ArrayList<Boolean>();
            for(RoadData tempRd : roadOrder) {
                findAll.add(tempRd.isRoadEmpty());
                findAll.add(tempRd.isCarQueueEmpty());
                //System.out.println(tempRd.isRoadEmpty() + "<-road:queue->" + tempRd.isCarQueueEmpty());
                if(!tempRd.isCarQueueEmpty() || !tempRd.isRoadEmpty()) {
                    //System.out.println("HAI");
                    //System.out.println(tempRd.getRoadFillInt());
                    eventPriorityQueue.add(new AdvanceCarsEvent(4, cycle-1, tempRd, roadOrder));
                }
                if(cycle > tempRd.getGreenOff() && cycle % tempRd.getCycleResets() == 0 ){
                    eventPriorityQueue.add(new GreenLightEvent(5, cycle, tempRd));
                }
            }
            roadsEmpty = true;
            for(Boolean boo : findAll) {
                roadsEmpty  = roadsEmpty && boo;
            }
            findAll.clear();
        }
        List<Integer> allDistances = new ArrayList<Integer>();
        double avgDistance = 0;
        for(Car car: allCars){
            allDistances.add(car.getTraveled());
            avgDistance += car.getTraveled();
        }
        //Integer maxIndex = allDistances.indexOf(Collections.max(allDistances));
        int maxIndex = findMaxIndex(allDistances);
        avgDistance = avgDistance/allCars.size();
        System.out.println("\nAverage number of time steps to the reach their destination is " + String.format("%.2f",avgDistance));
        System.out.println("Maximum number of time steps to the reach their destination is " + maxIndex);

    }

    private static <T extends Comparable<T>> int findMaxIndex(final List<T> xs) {
        int maxIndex;
        if(xs.isEmpty()) {
            maxIndex = -1;
        } else {
            final ListIterator<T> itr = xs.listIterator();
            T max = itr.next(); //
            maxIndex = itr.previousIndex();
            while (itr.hasNext()) {
                final T curr = itr.next();
                if (curr.compareTo(max) > 0) {
                    max = curr;
                    maxIndex = itr.previousIndex();
                }
            }
        }
        return maxIndex;
    }

    private static Edge localGetEdge(Integer from, Integer to, Graph graph) {
        Node fromNode = graph.getNode(from);
        Node toNode = graph.getNode(to);
        return graph.getEdge(fromNode, toNode);
    }

    private static void makeIntList(List<String> inputList, List<Integer> printCommandCycleNumber) {
        Integer numPrintCommands = Integer.parseInt(inputList.remove(0));
        if(numPrintCommands.equals(0)) ;
        else {
            String[] tempPrintCycle = inputList.remove(0).split("\\s+");
            for(Integer m=0;m<numPrintCommands;m++) {
                printCommandCycleNumber.add(Integer.parseInt(tempPrintCycle[m]));
            }
        }
    }

    public static List<String> removeEmpty(List<String> tempList)
    {
        List<String> returnList = new ArrayList<String>();
        for(String str : tempList) {
            if(str != null && !str.isEmpty()){
                returnList.add(str);
            }
        }
        return returnList;
    }
}
