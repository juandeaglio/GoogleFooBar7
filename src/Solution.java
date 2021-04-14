import java.util.*;

public class Solution
{
    public static int GetPeakBunnyCapacity(int[] entrances, int[] exits, int[][] graph)
    {
        int peakCapacity = 0;
        Graph convertedGraph = Convert2DArrayToGraph(graph);
        for(int i = 0; i < entrances.length; i++)
        {
            for(int j = 0; j < graph[entrances[i]].length; j++)
            {
                if(graph[entrances[i]][j] > 0)
                {
                    BreadthFirstSearchMaximumCost(i, graph[entrances[i]][j], convertedGraph, exits);
                }
            }
        }
        return peakCapacity;
    }
    private boolean IsNotTerminal(Node node, int[] terminalNodes)
    {
        boolean result = false;
        if (node != null)
        {
            for (int i = 0; i < terminalNodes.length && !result; i++)
            {
                if(terminalNodes[i] == node.vertex)
                    result = true;
            }
        }
        return result;
    }
    private static int BreadthFirstSearchMaximumCost(int source, int startingCost, Graph graph, int[] terminalNodes)
    {
        int endCost;

        endCost = modifiedBFS(graph, source, startingCost, terminalNodes);
        return endCost;
    }

    private static Graph Convert2DArrayToGraph(int[][] graph)
    {
        for(int i = 0; i < graph.length;i+=)

    }

    public static int modifiedBFS(Graph g, int src, int k, int[] terminalNodes)
    {
        // create a queue for doing BFS
        Queue<Node> q = new ArrayDeque<>();

        // add source vertex to set and enqueue it
        Set<Integer> vertices = new HashSet<>();
        vertices.add(0);
        q.add(new Node(src, 0, vertices));

        // stores maximum cost of a path from the source
        int maxCost = k;

        // loop till queue is empty
        Node node;
        while (!q.isEmpty() && IsNotTerminal(node,terminalNodes))
        {
            // dequeue front node
            node = q.poll();

            int v = node.vertex;
            int cost = node.weight;
            vertices = new HashSet<>(node.s);

            // if the destination is reached and BFS depth is equal to `m`,
            // update the minimum cost calculated so far
            maxCost = Math.min(maxCost, cost);

            // do for every adjacent edge of `v`
            for (Edge edge: g.adj.get(v))
            {
                // check for a cycle
                if (!vertices.contains(edge.dest))
                {
                    // add current node to the path
                    Set<Integer> s = new HashSet<>(vertices);
                    s.add(edge.dest);

                    // push every vertex (discovered or undiscovered) into
                    // the queue with a cost equal to the
                    // parent's cost plus the current edge's weight
                    q.add(new Node(edge.dest, cost + edge.weight, s));
                }
            }
        }

        return maxCost;
    }
    static class Graph
    {
        private Map<Vertex, List<Vertex>> adjVertices;
        Graph(int[][] asIntArr)
        {
            for(int i = 0; i < asIntArr.length; i++)
            {

            }
        }
        void addVertex(int room)
        {
            adjVertices.putIfAbsent(new Vertex(room), new ArrayList<>());
        }

        void removeVertex(int room) {
            Vertex v = new Vertex(room);
            adjVertices.values().stream().forEach(e -> e.remove(v));
            adjVertices.remove(new Vertex(room));
        }
        void addEdge(int from, int to) {
            Vertex v1 = new Vertex(from);
            Vertex v2 = new Vertex(to);
            adjVertices.get(v1).add(v2);
        }
        void removeEdge(int from, int to)
        {
            Vertex v1 = new Vertex(from);
            Vertex v2 = new Vertex(to);
            List<Vertex> eV1 = adjVertices.get(v1);
            if (eV1 != null)
                eV1.remove(v2);
        }
        List<Vertex> getAdjVertices(int room)
        {
            return adjVertices.get(new Vertex(room));
        }
    }
    static class Vertex
    {
        int room;
        Vertex(int roomNumber)
        {
            this.room = roomNumber;
        }
    }

}
