import java.util.*;

public class Solution
{
    public static int GetPeakBunnyCapacity(int[] entrances, int[] exits, int[][] graph)
    {
        int peakCapacity = 0;
        for(int i = 0; i < entrances.length; i++)
        {
            for(int j = 0; j < graph[entrances[i]].length; j++)
            {
                if(graph[entrances[i]][j] > 0)
                {
                    BreadthFirstSearchMaximumCost(i, graph[entrances[i]][j], graph, exits);
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
    private static int BreadthFirstSearchMaximumCost(int source, int startingCost, int[][] graph, int[] terminalNodes)
    {
        int endCost;
        endCost = modifiedBFS(graph, source, startingCost, terminalNodes);
        return startingCost;
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
    // A class to store a graph edge
    static class Edge
    {
        public final int src, dest, weight;

        private Edge(int src, int dest, int weight)
        {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        // Factory method for creating an immutable instance of `Edge`
        public static Edge of(int a, int b, int c) {
            return new Edge(a, b, c);        // calls private constructor
        }
    }

    // A BFS Node
    static class Node
    {
        // current vertex number and cost of the current path
        int vertex, weight;

        // set of nodes visited so far in the current path
        Set<Integer> s;

        Node(int vertex, int weight, Set<Integer> s)
        {
            this.vertex = vertex;
            this.weight = weight;
            this.s = s;
        }
    }

    // A class to represent a graph object
    static class Graph
    {
        // A list of lists to represent an adjacency list
        List<List<Edge>> adj = new ArrayList<>();

        // Graph Constructor
        public Graph(List<Edge> edges, int N)
        {
            // resize the list to `N` elements of type `List<Edge>`
            for (int i = 0; i < N; i++) {
                adj.add(new ArrayList<>());
            }

            // add edges to the undirected graph
            for (Edge e: edges)
            {
                adj.get(e.src).add(Edge.of(e.src, e.dest, e.weight));
                adj.get(e.dest).add(Edge.of(e.dest, e.src, e.weight));
            }
        }
    }

}
