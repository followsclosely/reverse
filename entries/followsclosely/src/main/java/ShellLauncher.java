import io.github.followsclosley.reverse.Simulation;
import io.github.followsclosley.reverse.ai.ScoreStrategy;
import io.github.followsclosley.reverse.impl.ai.Dummy;

public class ShellLauncher {
    public static void main(String[] args) {
        new Simulation()
                .number(200000)
                .addArtificialIntelligence(new Dummy())
                .addArtificialIntelligence(new ScoreStrategy())
                .run()
                .printSummary();
    }
}