import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class PigeonGenerator {
    private final PrintWriter writer;
    private final int n;
    private int count;
    public PigeonGenerator(int n) throws IOException {
        this.n=n;
        count=0;
        writer=new PrintWriter("testGenerator/pigeon.txt", StandardCharsets.UTF_8);
        setGenerator();
    }
    private void setGenerator() {
        int nbClause = n+n*(n-1)*(2*n-3)/2;
        writer.println("nbVariable: "+ n*(n-1) +" / nbClause: "+ nbClause);
        for(int i=0; i<n; i++) writer.println(createClause());
        for(int i=1;i<=n;i++) {
            for(int j=1;j<n;j++) {
                for(int k=j+1;k<n;k++) {
                    writer.println(transform(i,j)+" "+transform(i,k));
                }
            }
        }
        for(int k=1;k<n;k++) {
            for(int i=1;i<=n;i++) {
                for(int j=i+1;j<=n;j++) {
                    writer.println(transform(i,k)+" "+transform(j,k));
                }
            }
        }
        writer.close();
    }
    private int transform(int i, int j) {
        return (2*(j-1)+(i-1)*(2*n-2))+1;
    }
    private String createClause() {
        StringBuilder clause = new StringBuilder();
        for(int i=0; i<n-1; i++) {
            clause.append(count);
            clause.append(" ");
            count=count+2;
        }
        clause.deleteCharAt(clause.length()-1);
        return clause.toString();
    }
}
