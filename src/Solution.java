import java.util.*;

public class Solution
{
    public static int GetPeakBunnyCapacity(int[] entrances, int[] exits, int[][] graph)
    {
        int peakCapacity = 0;
        Graph convertedGraph = Convert2DArrayToGraph(graph);
        ArrayList<Integer> exitsPossible = new ArrayList<>();
        for(int i = 0; i < exits.length; i++)
            exitsPossible.add(exits[i]);
        for(int i = 0; i < entrances.length; i++)
        {
            for(int j = 0; j < graph[entrances[i]].length; j++)
            {
                if(graph[entrances[i]][j] > 0)
                {
                    peakCapacity += BreadthFirstSearchMaximumCost(j, graph[entrances[i]][j], convertedGraph, exitsPossible, i);
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
    private static int BreadthFirstSearchMaximumCost(int source, int startingCost, Graph graph, ArrayList<Integer> terminalNodes, int trueSource)
    {
        int endCost;
        HashSet<Integer> vertices = new HashSet<>();
        vertices.add(trueSource);
        endCost = RecursiveMaxPath(graph, source, startingCost, terminalNodes, vertices);
        return endCost;
    }

    private static Graph Convert2DArrayToGraph(int[][] graph)
    {
        for(int i = 0; i < graph.length;i++);
        return new Graph(graph);
    }

    public static int RecursiveMaxPath(Graph graph, int sourceNode, int startingBunnies, ArrayList<Integer> terminalNodes, HashSet<Integer> verticiesVisited)
    {
        if(terminalNodes.contains(sourceNode))
            return startingBunnies;
        else
        {
            int maxOfAllPossibilities = 0;
            for (Edge edge: graph.adjVertices.get(sourceNode).edges)
            {
                if(!verticiesVisited.contains(edge.leadstoRoom))
                {
                    int bunniesCapablePassing = Math.min(startingBunnies, edge.cost);
                    verticiesVisited.add(sourceNode);
                    maxOfAllPossibilities = Math.max(maxOfAllPossibilities, RecursiveMaxPath(graph, edge.leadstoRoom, bunniesCapablePassing, terminalNodes, verticiesVisited));
                }
            }
            return Math.min(maxOfAllPossibilities,startingBunnies);
        }
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
