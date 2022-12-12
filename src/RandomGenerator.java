import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class RandomGenerator {
    private final Random random = new Random();
    private final PrintWriter writer;  // Pair -> literaux positifs // Impair -> literaux negatifs
    private final int nbVariable;
    private final int nbClause;
    public RandomGenerator(int nbVariable, int nbClause) throws IOException {
        this.nbVariable = nbVariable;
        this.nbClause = nbClause;
        writer = new PrintWriter("testGenerator/test.txt", StandardCharsets.UTF_8);
        setGenerator();
    }
    private void setGenerator() {
        writer.println("nbVariable: "+nbVariable+" / nbClause: "+nbClause);
        for(int i=0; i<nbClause-1; i++) writer.println(createClause());
        writer.print(createClause());
        writer.close();
    }
    private String createClause() {  // literaux entre 0 et 2n-1
        StringBuilder clause = new StringBuilder();
        for(int i=0; i<3; i++) { // nb aleatoire de literaux par ligne
            int randomTampon = random.nextInt(2*nbVariable);
            while (clause.toString().contains(Integer.toString(randomTampon)))
                randomTampon = random.nextInt(2*nbVariable);
            clause.append(randomTampon);
            clause.append(" ");
        }
        return clause.toString();
    }
}
