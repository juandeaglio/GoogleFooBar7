import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
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
                    peakCapacity += BreadthFirstSearchMaximumCost(j, graph[entrances[i]][j], convertedGraph, exits, i);
                }
            }
        }
        return peakCapacity;
    }
    private static boolean IsNotTerminal(Vertex node, int[] terminalNodes)
    {
        boolean result = false;
        if (node != null)
        {
            for (int i = 0; (i < terminalNodes.length) && !result; i++)
                if (terminalNodes[i] == node.room)
                    result = true;
        }
        return result;
    }
    private static int BreadthFirstSearchMaximumCost(int source, int startingCost, Graph graph, int[] terminalNodes, int trueSource)
    {
        int endCost;
        endCost = BFSMaxPath(graph, source, startingCost, terminalNodes, trueSource);
        return endCost;
    }

    private static Graph Convert2DArrayToGraph(int[][] graph)
    {
        for(int i = 0; i < graph.length;i++);
        return new Graph(graph);
    }

    public static int BFSMaxPath(Graph graph, int sourceNode, int startingBunnies, int[] terminalNodes, int trueSource)
    {
        Queue<Vertex> vertexArrayDeque = new ArrayDeque<>();

        Set<Integer> vertices = new HashSet<>();
        vertices.add(sourceNode);
        vertices.add(trueSource);
        vertexArrayDeque.add(new Vertex(sourceNode));
        int maximumBunniesPossible = startingBunnies;
        int maximumEdgeCost = 0;
        boolean terminal = false;
        while (!vertexArrayDeque.isEmpty()&& !terminal)
        {
            Vertex node = vertexArrayDeque.poll();

            int v = node != null ? node.room : 0;
            vertices = new HashSet<>(v);

            for (Edge edge: graph.adjVertices.get(v).edges)
            {
                maximumEdgeCost = Math.max(maximumEdgeCost, edge.cost);
                if (!vertices.contains(edge.leadstoRoom))
                {
                    //vertices = new HashSet<>(vertices);
                    vertices.add(edge.leadstoRoom);
                    vertexArrayDeque.add(new Vertex(edge.leadstoRoom));
                }
            }
            maximumBunniesPossible = Math.min(maximumBunniesPossible, maximumEdgeCost);
            terminal = IsNotTerminal(node,terminalNodes);
        }

        return maximumBunniesPossible;
    }
    static class Graph
    {
        private final Map<Integer, Vertex> adjVertices;
        Graph(int[][] asIntArr)
        {
            adjVertices = new HashMap<>();
            for(int i = 0; i < asIntArr.length; i++)
            {
                addVertex(i);
                for(int j = 0; j < asIntArr[i].length; j++)
                {
                    if(asIntArr[i][j] != 0)
                        addEdge(i,j,asIntArr[i][j]);
                }
            }
        }
        void addVertex(int room)
        {
            adjVertices.putIfAbsent(room, new Vertex(room));
        }
        void addEdge(int from, int to, int cost)
        {
            adjVertices.get(from).AddEdge(to, cost);
        }
    }
    static class Edge
    {
        int leadstoRoom;
        int cost;
        Edge(int roomNumber, int cost)
        {
            this.leadstoRoom = roomNumber;
            this.cost = cost;
        }
    }
    static class Vertex
    {
        int room;
        List<Edge> edges;
        Vertex(int roomNumber)
        {
            this.room = roomNumber;
            edges = new ArrayList<>();
        }
        public void AddEdge(int to, int cost)
        {
            edges.add(new Edge(to,cost));
        }
    }

}
