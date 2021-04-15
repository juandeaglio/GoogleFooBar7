import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MaximumCostPathsInGraphUnitTests
{
    @Test
    public void GivenGraphFromExample1ShouldFindMaximumCostPaths()
    {
        int[] entrances = {0,1};
        int[] exits = {4,5};
        int[][] graph = {
                {0,0,4,6,0,0},
                {0,0,5,2,0,0},
                {0,0,0,0,4,4},
                {0,0,0,0,6,6},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
        };
        int expectedTotalBunnies = 16;
        int actualExpectedTotal = Solution.GetPeakBunnyCapacity(entrances, exits, graph);
        Assertions.assertEquals(expectedTotalBunnies, actualExpectedTotal);
    }
    @Test
    public void GivenGraphFromExample1ButWithLoopShouldFindMaximumCostPaths()
    {
        int[] entrances = {0,1};
        int[] exits = {4,5};
        int[][] graph = {
                {0,0,4,6,0,0},
                {0,0,5,2,0,0},
                {0,0,0,0,4,4},
                {0,0,0,0,6,6},
                {100,0,0,0,0,0},
                {0,0,0,0,0,0},
        };
        int expectedTotalBunnies = 16;
        int actualExpectedTotal = Solution.GetPeakBunnyCapacity(entrances, exits, graph);
        Assertions.assertEquals(expectedTotalBunnies, actualExpectedTotal);
    }
    @Test
    public void GivenGraphFromExample1ButWithDifferentLoopShouldFindMaximumCostPaths()
    {
        int[] entrances = {0,1};
        int[] exits = {4,5};
        int[][] graph = {
                {0,0,4,6,0,0},
                {0,0,5,2,0,0},
                {100,0,0,0,4,4},
                {0,0,0,0,6,6},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
        };
        int expectedTotalBunnies = 17;
        int actualExpectedTotal = Solution.GetPeakBunnyCapacity(entrances, exits, graph);
        Assertions.assertEquals(expectedTotalBunnies, actualExpectedTotal);
    }
    @Test
    public void GivenGraphFromExample1ButWithSomeLoopShouldFindMaximumCostPaths()
    {
        int[] entrances = {0,1};
        int[] exits = {4,5};
        int[][] graph = {
                {0,0,4,6,0,0},
                {0,0,5,2,0,0},
                {0,0,0,0,4,4},
                {100,0,0,0,6,6},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
        };
        int expectedTotalBunnies = 16;
        int actualExpectedTotal = Solution.GetPeakBunnyCapacity(entrances, exits, graph);
        Assertions.assertEquals(expectedTotalBunnies, actualExpectedTotal);
    }
    @Test
    public void GivenGraphFromExample2ShouldFindMaximumCostPaths()
    {
        int[] entrances = {0};
        int[] exits = {3};
        int[][] graph = {
                {0,7,0,0},
                {0,0,6,0},
                {0,0,0,8},
                {9,0,0,0}
        };
        int expectedTotalBunnies = 6;
        int actualExpectedTotal = Solution.GetPeakBunnyCapacity(entrances, exits, graph);
        Assertions.assertEquals(expectedTotalBunnies, actualExpectedTotal);
    }
    @Test
    public void GivenLargeGraphShouldFindMaximumCostPaths()
    {
        int[] entrances = {0,1};
        int[] exits = {6,7};
        int[][] graph = {
                {0,0,8,10,0,0,0,0},
                {0,0,10,8,0,0,0,0},
                {11,9,0,0,7,8,0,0},
                {8,11,0,0,7,6,0,0},
                {0,0,0,0,0,0,13,12},
                {0,0,0,0,0,0,15,12},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}
        };
        int expectedTotalBunnies = 7+8+7+8;
        int actualExpectedTotal = Solution.GetPeakBunnyCapacity(entrances, exits, graph);
        Assertions.assertEquals(expectedTotalBunnies, actualExpectedTotal);
    }
}

