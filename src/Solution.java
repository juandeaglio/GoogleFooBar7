import java.util.*;

public class Solution
{
    public static int solution(int[] entrances, int[] exits, int[][] path)
    {
        return GetPeakBunnyCapacity(entrances, exits, path);
    }
    public static int GetPeakBunnyCapacity(int[] entrances, int[] exits, int[][] graph)
    {
        Graph flowGraph = ConvertEntrancesExitsIntoFlowGraph(entrances,exits,graph);
        entrances = new int[1];
        exits = new int[1];
        exits[0] = graph.length+1;
        Path shortestPath = BFSForShortestPath(flowGraph, exits);
        int bottleNeckForPath = FindBottleNeckValueOf(shortestPath, flowGraph);
        AugmentGraphUsingShortestPath(flowGraph,shortestPath, bottleNeckForPath);
        int bottleNeckSum = bottleNeckForPath;
        while(bottleNeckForPath != 0)
        {
            shortestPath = BFSForShortestPath(flowGraph, exits);
            bottleNeckForPath = FindBottleNeckValueOf(shortestPath, flowGraph);
            AugmentGraphUsingShortestPath(flowGraph,shortestPath, bottleNeckForPath);
            bottleNeckSum += bottleNeckForPath;
        }

        return bottleNeckSum;
    }

    private static void AugmentGraphUsingShortestPath(Graph flowGraph, Path shortestPath, int bottleNeckValue)
    {
        Path currentPath = shortestPath;
        if(shortestPath.next != null && !flowGraph.adjVertices.get(currentPath.roomNumber).GetEdge(currentPath.next.roomNumber).IsNowResidual())
        {
            flowGraph.adjVertices.get(currentPath.roomNumber).GetEdge(currentPath.next.roomNumber).Augment(bottleNeckValue);
            currentPath = currentPath.next;
            while (currentPath.next != null && !flowGraph.adjVertices.get(currentPath.roomNumber).GetEdge(currentPath.next.roomNumber).IsNowResidual())
            {
                flowGraph.adjVertices.get(currentPath.roomNumber).GetEdge(currentPath.next.roomNumber).Augment(bottleNeckValue);
                currentPath = currentPath.next;
            }
        }
    }

    private static int FindBottleNeckValueOf(Path shortestPath, Graph graph)
    {
        Path currentPath = shortestPath;
        int bottleNeckValue = 0;
        if(shortestPath.next != null)
        {
            List<Edge> edgesReachable = graph.adjVertices.get(currentPath.roomNumber).edges;
            bottleNeckValue = GetCapacityFromAGivenRoom(edgesReachable,currentPath.next.roomNumber);
            currentPath = currentPath.next;
            while (currentPath.next != null)
            {
                edgesReachable = graph.adjVertices.get(currentPath.roomNumber).edges;
                bottleNeckValue = Math.min(bottleNeckValue, GetCapacityFromAGivenRoom(edgesReachable,currentPath.next.roomNumber));
                currentPath = currentPath.next;
            }
        }
        if(graph.adjVertices.size()-1!=currentPath.roomNumber)
            return 0;
        return  bottleNeckValue;
    }

    private static int GetCapacityFromAGivenRoom(List<Edge> edgesReachable, int roomNumber)
    {
        int capacity = 0;
        for (Edge edge: edgesReachable)
        {
            if(edge.leadstoRoom == roomNumber && edge.RemainingCapacity()>0)
                capacity = edge.RemainingCapacity();
        }
        return capacity;
    }

    private static Graph ConvertEntrancesExitsIntoFlowGraph(int[]entrances, int[]exits, int[][] graph)
    {
        int[][] newGraph = new int[graph.length+2][graph.length+2];
        for(int i = 0; i < entrances.length;i++)
        {
            for(int j = 0; j < graph[entrances[i]].length; j++)
            {
                if (graph[entrances[i]][j] > 0)
                    newGraph[0][1 + i] += graph[entrances[i]][j];
            }
        }

        for(int i = 0; i < graph.length;i++)
            System.arraycopy(graph[i], 0, newGraph[1 + i], 1, graph[0].length);

        for(int i = 0; i < exits.length;i++)
        {
            newGraph[newGraph.length-1-exits.length+i][newGraph[0].length-1] = Integer.MAX_VALUE;

        }
        return Convert2DArrayToGraph(newGraph);
    }
    public static Path BFSForShortestPath(Graph graph, int[] terminalNodes)
    {
        Queue<Vertex> vertexArrayQueue = new ArrayDeque<>();
        Queue<Path> pathTaken = new ArrayDeque<>();
        HashSet<Integer> vertices = new HashSet<>();
        vertexArrayQueue.add(new Vertex(0));
        pathTaken.add(new Path(0, null));
        Path pathBeingEvaluated = null;
        boolean terminal = false;
        while (!vertexArrayQueue.isEmpty() && !terminal)
        {
            Vertex node = vertexArrayQueue.poll();
            pathBeingEvaluated = pathTaken.poll();
            int nodeRoom = node != null ? node.room : 0;
            for (Edge edge: graph.adjVertices.get(nodeRoom).edges)
            {
                if(edge.RemainingCapacity() > 0 && !edge.IsNowResidual())
                {
                    Path currentPath = pathBeingEvaluated.CreateCopy();
                    if (!vertices.contains(edge.leadstoRoom))
                    {
                        vertexArrayQueue.add(new Vertex(edge.leadstoRoom));
                        currentPath.AddPath(new Path(edge.leadstoRoom, null));
                        pathTaken.add(currentPath);
                        vertices.add(edge.leadstoRoom);
                    }
                }
            }
            terminal = IsNotTerminal(node,terminalNodes);
        }
        return pathBeingEvaluated;
    }

    private static boolean IsNotTerminal(Vertex node, int[] terminalNodes)
    {
        boolean result = false;
        if (node != null)
        {
            for (int i = 0; (i < terminalNodes.length); i++)
                if (terminalNodes[i] == node.room)
                    return true;
        }
        return result;
    }
    private static Graph Convert2DArrayToGraph(int[][] graph)
    {
        return new Graph(graph);
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
                    {
                        Edge newEdge = addEdge(i, j, asIntArr[i][j]);
                        addVertex(j);
                        Edge residualEdge = addEdge(j, i, 0);
                        newEdge.residualEdge = residualEdge;
                        residualEdge.residualEdge = newEdge;
                    }
                }
            }
        }
        void addVertex(int room)
        {
            adjVertices.putIfAbsent(room, new Vertex(room));
        }
        Edge addEdge(int from, int to, int cost)
        {
            return adjVertices.get(from).AddEdge(to, cost);
        }
    }
    static class Edge
    {
        int leadstoRoom;
        int startsFromRoom;
        int totalCapacitySupported;
        Edge residualEdge;
        int currentBunnies;
        Edge(int fromNumber, int toRoomNumber, int capacity)
        {
            startsFromRoom = fromNumber;
            leadstoRoom = toRoomNumber;
            totalCapacitySupported = capacity;
        }
        public boolean IsNowResidual()
        {
            return totalCapacitySupported == 0;
        }
        public void Augment(int bottleNeck)
        {
            currentBunnies += bottleNeck;
            residualEdge.currentBunnies -= bottleNeck;
        }
        public int RemainingCapacity()
        {
            return totalCapacitySupported-currentBunnies;
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
        public Edge AddEdge(int to, int cost)
        {
            Edge newEdge = new Edge(room,to,cost);
            edges.add(newEdge);
            return newEdge;
        }
        public Edge GetEdge(int to)
        {
            Edge found = null;
            for (Edge edge: edges)
            {
                if(edge.leadstoRoom == to)
                    found = edge;
            }
            return found;
        }
    }

    private static class Path
    {
        int roomNumber;
        Path next;
        Path(int room, Path nextPath)
        {
            roomNumber = room;
            next = nextPath;
        }
        void AddPath(Path pathToAdd)
        {
            Path currentPath = this;
            while(currentPath.next!=null)
                currentPath = currentPath.next;
            currentPath.next = pathToAdd;
        }

        public boolean Contains(int leadstoRoom)
        {
            boolean hasOccurence = false;
            Path currentPath = this;
            while(currentPath.next!=null && !hasOccurence)
            {
                if(currentPath.next.roomNumber == leadstoRoom)
                    hasOccurence = true;
                currentPath = currentPath.next;
            }
            return hasOccurence;
        }

        public Path CreateCopy()
        {
            Path currentPath = this;
            Path newPath = new Path(this.roomNumber, null);
            Path newCurrentPath = newPath;
            currentPath = currentPath.next;
            while(currentPath != null)
            {
                newCurrentPath.next = new Path(currentPath.roomNumber, null);
                newCurrentPath = newCurrentPath.next;
                currentPath = currentPath.next;
            }
            return newPath;
        }
    }
}
