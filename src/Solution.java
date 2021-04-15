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
        Deque<Vertex> vertexArrayDeque = new ArrayDeque<>();

        HashMap<Integer,HashSet<Integer>> vertices = new HashMap<>();
        vertices.put(sourceNode, new HashSet<>(trueSource));
        vertexArrayDeque.add(new Vertex(sourceNode));
        int maximumBunniesPossible = startingBunnies;
        int maximumEdgeCost = 0;
        boolean terminal = false;
        while (!vertexArrayDeque.isEmpty()&& !terminal)
        {
            Vertex node = vertexArrayDeque.poll();

            int nodeRoom = node != null ? node.room : 0;

            for (Edge edge: graph.adjVertices.get(nodeRoom).edges)
            {

                if(vertices.get(nodeRoom) != null && !vertices.get(nodeRoom).contains(edge.leadstoRoom))
                {
                    vertices.get(nodeRoom).add(edge.leadstoRoom);
                    maximumEdgeCost = Math.max(maximumEdgeCost, edge.cost);
                    vertexArrayDeque.addLast(new Vertex(edge.leadstoRoom));
                }
                else vertices.computeIfAbsent(nodeRoom, k -> new HashSet<>(edge.leadstoRoom));
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
