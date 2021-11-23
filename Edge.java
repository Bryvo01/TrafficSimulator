import java.util.*;

public class Edge {
    private Node from;
    private Node to;
    private double length;

    /*
     * Constructor to build an Edge
     */
    public Edge( Node from, Node to ){
        this.from = from;
        this.to = to;
        this.length = 1; //Defaults to length 1
    }
    
    public Edge( Node from, Node to, double length ){
        this.from = from;
        this.to = to;
        this.length = length;
    }

    public Node getFrom(){
        return this.from;
    }

    public Node getTo(){
        return this.to;
    }

    public double getLength(){
        return this.length;
    }

            /*
     * Compares the x in the given Pair to the x in this Pair
     * Returns -1 if this Pair comes first
     * Returns 0 if these pairs have equal x values
     * Returns 1 if the given Pair comes first
     */
    public int compareTo( Edge aThat ){
        if( this.from.compareTo( aThat.from ) == 0 )
            return this.to.compareTo( aThat.to );
        return this.from.compareTo( aThat.from );
    }

    public boolean equals( Edge aThat ){
        return this.compareTo( aThat ) == 0;
    }

    @Override 
    @SuppressWarnings("unchecked") //Suppresses warning for cast to Pair<S,T>
    public boolean equals(Object aThat) {
        if (this == aThat) //Shortcut the future comparisons if the locations in memory are the same
            return true;
        if (!(aThat instanceof Edge))
            return false;
        Edge that = (Edge)aThat;
        return this.equals( that ); //Use above equals method
    }
}
