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
        int actualExpectedTotal = Solution.GetPeakBunnyCapacity(entrances, exits, expectedTotalBunnies);
        Assertions.assertEquals(expectedTotalBunnies, actualExpectedTotal);
    }
    @Test
    public void GivenGraphFromExample2ShouldFindMaximumCostPaths()
    {
        int[] entrances = {0};
        int[] exits = {3};
        int[][] graph = {
                {0,0,7,0},
                {0,0,6,0},
                {0,0,0,8},
                {9,0,0,0}
        };
    }
}
