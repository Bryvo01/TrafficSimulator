import java.util.*;

public class PQNode implements Comparable<PQNode>
{
    private Node node;
    private Double priority;
    private Node prev = null;

    /*
     * Constructors to build a PQNode
     */
    public PQNode( Node node, double priority ){
        this.node = node;
        this.priority = priority;
    }

    /*
     * Constructors to build a PQNode
     */
    public PQNode( Node node, double priority, Node prev ){
        this.node = node;
        this.priority = priority;
        this.prev = prev;
    }
    
    public void setVisited( boolean visited )
    {
        node.setVisited( visited );
    }

    public boolean getVisited( )
    {
        return this.node.getVisited( );
    }
    
    public Double getPriority( )
    {
        return this.priority;
    }
    
    public Integer getNodeData( )
    {
        return this.node.getNodeData( );
    }

    public Node getNode( )
    {
        return this.node;
    }
    
    public void setPrevious( Node prev )
    {
        this.prev = prev;
    }

    public Node getPrevious( )
    {
        return this.prev;
    }
    /*
     * Compares the data in the given node to the data in this node
     * Returns -1 if this node comes first
     * Returns 0 if these nodes have equal priority
     * Returns 1 if the given node comes first
     */
    public int compareTo( PQNode node ){
        return this.priority.compareTo(node.priority);
    }

    public boolean equals( PQNode node ){
        return this.node.compareTo( node.node ) == 0;
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
        return node.hashCode(); /* use the hashCode for data stored in this node */
    }
}
