import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable {
    final int x;
    final int y;
    Facing facing;

    boolean isObstacle;
    boolean isVirtualWall;
    int clearance;

    Node pathParent; // Where the robot came from
    Node up; // Node that is north of the robot
    Node down;
    Node left;
    Node right;
    List neighbors = new ArrayList<Node>(); // List of all nodes that are around the robot

    float costFromStart; // Path cost
    float estimatedCostToGoal; // Heuristic cost

    public float getCost() { // Get cost of node
        return costFromStart + estimatedCostToGoal;
    }

    // compare the f value and the lower f value put to the front
    public int compareTo(Object other) {
        float thisValue = this.getCost();
        float otherValue = ((Node) other).getCost();

        float v = thisValue - otherValue;
        return (v > 0) ? 1 : (v < 0) ? -1 : 0;
    }

    public Node() {
        x = 0;
        y = 0;
    }

    public Node(int xi, int yi) {
        this.x = xi;
        this.y = yi;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void addNeighbors(Node node) {
        neighbors.add(node);
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setUp(Node up) {
        this.up = up;
    }

    public void setDown(Node down) {
        this.down = down;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public Node getUp() {
        return up;
    }

    public Node getDown() {
        return down;
    }

    public float getCost(Node node, Node goalNode) {
        return this.costFromStart + getWeight(node);
    }

    public float getEstimatedCost(Node node) {
        Node goal = (Node) node;

        float dx = Math.abs(this.x - goal.x);
        float dy = Math.abs(this.y - goal.y);
        return (dx + dy);
    }

    public List getNeighbors() {
        return neighbors;
    }

    // if
    public int compareX(Node node) {
        return node.x > this.x ? 1 : node.x < this.x ? -1 : 0;
    }

    public int compareY(Node node) {
        return node.y > this.y ? 1 : node.y < this.y ? -1 : 0;
    }

    public float getWeight(Node anode) {
        Node node = (Node) anode;
        setFacing();

        if (compareX(node) == 1 && facing == Facing.RIGHT || compareX(node) == -1 && facing == Facing.LEFT
                || compareY(node) == 1 && facing == Facing.UP || compareY(node) == -1 && facing == Facing.DOWN) {
            return 0;
        }

        // Penalize turns by adding edge cost
        return 10000;
    }

    public void setFacing(Facing face) {
        this.facing = face;
    }

    public void setFacing() {
        if (this.pathParent == null) {
            // Set robot's initial orientation
            this.facing = Facing.RIGHT;
            return;
        }

        if (compareX((Node) this.pathParent) == 1) {
            this.facing = Facing.LEFT;
        } else if (compareX((Node) this.pathParent) == -1) {
            this.facing = Facing.RIGHT;
        } else if (compareY((Node) this.pathParent) == 1) {
            this.facing = Facing.DOWN;
        } else if (compareY((Node) this.pathParent) == -1) {
            this.facing = Facing.UP;
        }
    }

    public Facing getFacing() {
        return this.facing;
    }

    public void setObstacle(boolean val) {
        this.isObstacle = val;
    }

    public void setVirtualWall(boolean val) {
        if (val) {
            this.isVirtualWall = true;
        }
    }

    public boolean isObstacle() {
        return isObstacle;
    }

    public void setClearance(int clearance) {
        this.clearance = clearance;
    }

    public int getClearance() {
        return clearance;
    }

    // public Node returnRightNeighbors(){
    // Node neighbor;
    // List<Node> neighbors = this.getNeighbors();
    //
    // if (this.x > 0 )
    // }

    public Node getNode(Direction direction) {
        if (direction == Direction.UP && up != null)
            return up;
        else if (direction == Direction.DOWN && down != null)
            return down;
        else if (direction == Direction.LEFT && left != null)
            return left;
        else if (direction == Direction.RIGHT && right != null)
            return right;
        return null;
    }

    public Node getNode(Facing face) {
        if (face == Facing.UP && up != null)
            return up;
        else if (face == Facing.DOWN && down != null)
            return down;
        else if (face == Facing.LEFT && left != null)
            return left;
        else if (face == Facing.RIGHT && right != null)
            return right;
        return null;
    }

}