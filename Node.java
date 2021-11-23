import java.util.*;

public class Node implements Comparable<Node> {
    private Integer data;
    private boolean visited;

    /*
     * Constructors to build a Node
     */
    public Node( ){
        visited = false;
        data = null;
    }

    public Node( Integer data ){
        visited = false;
        this.data = data;
    }

    public void setVisited( boolean visited )
    {
        this.visited = visited;
    }

    public boolean getVisited( )
    {
        return this.visited;
    }
    
    public Integer getNodeData( )
    {
        return this.data;
    }

    /*
     * Compares the data in the given node to the data in this node
     * Returns -1 if this node comes first
     * Returns 0 if these nodes have equal keys
     * Returns 1 if the given node comes first
     */
    public int compareTo( Node node ){
        return data.compareTo( node.data );
    }

    public boolean equals( Node node ){
        return this.compareTo( node ) == 0;
    }

    @Override 
    public boolean equals(Object aThat) {
        if (this == aThat) //Shortcut the future comparisons if the locations in memory are the same
            return true;
        if (!(aThat instanceof Node))
            return false;
        Node that = (Node)aThat;
        return this.equals( that ); //Use above equals method
    }
    
    @Override
    public int hashCode() {
        return data.hashCode(); /* use the hashCode for data stored in this node */
    }
    
    //Function to convert a node to a String to allow it to be printed for debugging
    public String toString( ){
        return data.toString();
    }
}
