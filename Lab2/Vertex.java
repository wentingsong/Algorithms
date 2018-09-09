public class Vertex {
    private int node;
    private int dist;
    private Vertex prev;

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public Vertex(int node, int dist, Vertex prevVertex){
        setNode(node);
        setDistance(dist);
        setPrevVertex(prevVertex);
    }

    public int getDistance() {
        return dist;
    }

    public void setDistance(int distance) {
        this.dist = distance;
    }

    public Vertex getPrevVertex() {
        return prev;
    }

    public void setPrevVertex(Vertex prevVertex) {
        this.prev = prevVertex;
    }
}