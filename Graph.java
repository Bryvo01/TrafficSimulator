import java.util.*;

public class Graph{
    private ArrayList<Node> nodeList;
    private HashMap<Node, ArrayList<Edge>> adjacencyList;
    private HashMap<Pair<Node,Node>,Pair<Node,Double>> pathDistance; //For a given pair of Nodes record the distance between them and the next Node on the path between them
    private int numEdges=0;
    private boolean changed=true;

    public Graph()
    {
        this.nodeList = new ArrayList<Node>();
        this.adjacencyList = new HashMap<Node, ArrayList<Edge>>();
        this.pathDistance = new HashMap<Pair<Node,Node>,Pair<Node,Double>>();
    }

    /* Returns the number of nodes in the graph
     */
    public int numNodes( )
    {
        return nodeList.size();
    }

    /* Returns the number of edges in the graph
     */
    public int numEdges( )
    {
        return numEdges;
    }

    /* Returns the number of outgoing edges from node in the graph
     */
    public int numEdgesFromNode( Integer from )
    {
        Node fromNode = new Node( from );
        ArrayList<Edge> edges = adjacencyList.get(fromNode);
        return edges.size();
    }

    /* 
     * Adds a new node with the specified data to the graph
     */
    public void addNode( Integer data )
    {
        //Add the node to the hashMap and associate it with new LL of edges
        Node temp = new Node(data); 
        if( adjacencyList.get( temp )==null ){
            nodeList.add( temp );
            adjacencyList.put( temp, new ArrayList<Edge>() ); //adds a node containing data if it wasn't already in the graph
            changed = true;
        }
    }

    /* 
     * Returns the index-th Node in the graph
     */
    public Node getNode( int index )
    {
        return nodeList.get( index ); /* causes an exception if index<0 || index>=nodeList.size()*/
    }

    /* 
     * Adds an new edge(from, to) 
     */
    public void addEdge( Integer from, Integer to, double length )
    {
        if( !nodeList.contains( new Node(from) ) )
            addNode( from ); //adds the fromNode if it wasn't already in the graph
        if( !nodeList.contains( new Node(to) ) )
            addNode( to ); //adds the toNode if it wasn't already in the graph
            
        //find the nodes in the nodeList
        Node fromNode = nodeList.get( nodeList.indexOf(new Node(from)) );
        Node toNode = nodeList.get( nodeList.indexOf(new Node(to)) );

        //addEdge( nodeList.get( from ), nodeList.get( to ), length );
        addEdge( fromNode, toNode, length );
    }  
    
    public void addEdge( Node fromNode, Node toNode, double length )
    {
        //addEdge( nodeList.get( from ), nodeList.get( to ), length );
        ArrayList<Edge> edges = adjacencyList.get(fromNode);
        edges.add( new Edge( fromNode, toNode, length ) );
        numEdges++;
        changed = true;
    }  
    
    /* 
     * Returns the edge (from,to) from the graph or null if it doesn't exist
     */
    public Edge getEdge( Integer from, Integer to )
    {
        Node fromNode = new Node( from );
        Node toNode = new Node( to );

        return getEdge( fromNode, toNode );
    }  

    public Edge getEdge( Node fromNode, Node toNode )
    {
        ArrayList<Edge> edges = adjacencyList.get(fromNode);
        int index = edges.indexOf( new Edge( fromNode, toNode ) );

        if( index>=0 )
            return edges.get( index );
        else
            return null;
    }  

    /* 
     * Returns the index-th edge of the from node in the graph
     */
    public Edge getEdge( Integer from, int index )
    {
        Node fromNode = new Node( from );
        return getEdge( fromNode, index );
    }  

    public Edge getEdge( Node fromNode, int index )
    {
        ArrayList<Edge> edges = adjacencyList.get(fromNode);
        return edges.get( index ); /* causes an exception if index<0 || index>=edges.size()*/
    } 
    
    public Double getDistance( Node fromNode, Node toNode )
    {
        updatePathDistance();
        Pair<Node,Double> p = pathDistance.get( new Pair<Node,Node>(fromNode,toNode) );
        return p.y;
    } 

    public Node getNextOnPath( Node fromNode, Node toNode )
    {
        updatePathDistance();
        Pair<Node,Double> p = pathDistance.get( new Pair<Node,Node>(fromNode,toNode) );
        return p.x;
    } 
    
    //Update the pathDistance with all shortest paths
    private void updatePathDistance( ){
        if(changed)
            for( int i=0; i<this.numNodes(); i++ )
                this.dijkstras( this.getNode(i) );
        changed = false;
    }

    private void dijkstras( Node start ){
        PriorityQueue<PQNode> pq = new PriorityQueue<PQNode>();

        //Add an arbitrary node with weight 0 to serve as start node
        pq.add( new PQNode(start, 0, start) );
        for( int i=0; i<this.numNodes(); i++){
            Node next = this.getNode(i);
            pq.add( new PQNode(next, Double.MAX_VALUE, next) );
        }

        //Loop until all nodes have been visited
        while( !pq.isEmpty() ){
            PQNode pqFrom = pq.remove();
            
            //If this node is unreachable then no path to exists and we should set it to Double.MAX_VALUE
            if( pqFrom.getVisited()==false && pqFrom.getPriority()==Double.MAX_VALUE ){
                pqFrom.setVisited( true );
                pathDistance.put( new Pair(start,pqFrom.getNode()), new Pair(null,Double.MAX_VALUE) );
            }

            //If this is the best path found so far to pqFrom from start
            else if( pqFrom.getVisited()==false ){
                pqFrom.setVisited( true );
                pathDistance.put( new Pair(start,pqFrom.getNode()), new Pair(pqFrom.getPrevious(),pqFrom.getPriority()) );

                for( int j=0; j<this.numEdgesFromNode( pqFrom.getNodeData() ); j++){
                    Edge e = this.getEdge( pqFrom.getNodeData(), j );
                    if( e.getTo().getVisited()==false ){//if not yet visited (not required for correctness but speeds up execution by skipping uneeded edges)
                        Node prev = pqFrom.getPrevious();
                        if( start.equals(pqFrom.getNode()) )
                            prev = e.getTo();
                        PQNode next = new PQNode(e.getTo(), e.getLength()+pqFrom.getPriority(), prev );
                        pq.add( next );
                    }
                }
            }

        }
        
        this.resetVisited();
    }

    //Set all of the visited values of the Nodes back to false
    public void resetVisited( ){
        for( int i=0; i<this.numNodes(); i++){
            Node from = this.getNode(i);
            from.setVisited(false);
        }
    }
}
