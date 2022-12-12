import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class DameGenerator {
    private final PrintWriter writer;
    private final int n;
    public DameGenerator(int n) throws IOException {
        this.n=n;
        writer=new PrintWriter("testGenerator/dame.txt", StandardCharsets.UTF_8);
        setGenerator();
    }
    private void setGenerator() {
        int nbClause = (5*n*n*n-6*n*n+4*n)/3;
        writer.println("nbVariable: "+ n*n +" / nbClause: "+ nbClause);
        // 1
        for(int i=1;i<=n;i++) {
            for(int j=1;j<=n;j++) {
                for(int k=j+1;k<=n;k++) {
                    writer.println(transform(i,j)+" "+transform(i,k));
                }
            }
        }
        // 2
        for(int i=1;i<=n;i++) {
            for(int j=1;j<=n;j++) {
                for(int k=j+1;k<=n;k++) {
                    writer.println(transform(j,i)+" "+transform(k,i));
                }
            }
        }
        // 3
        for(int d=0;d<=n-2;d++) {
            for(int j=1;j<=n-d;j++) {
                for(int k=j+1;k<=n-d;k++) {
                    writer.println(transform(d+j,j)+" "+transform(d+k,k));
                }
            }
        }
        // 4
        for(int d=-n+2;d<=-1;d++) {
            for(int j=1;j<=n+d;j++) {
                for(int k=j+1;k<=n+d;k++) {
                    writer.println(transform(j,j-d)+" "+transform(k,k-d));
                }
            }
        }
        // 5
        for(int d=3;d<=n+1;d++) {
            for(int j=1;j<=d-1;j++) {
                for(int k=j+1;k<=d-1;k++) {
                    writer.println(transform(j,d-j)+" "+transform(k,d-k));
                }
            }
        }
        // 6
        for(int d=n+2;d<=2*n-1;d++) {
            for(int j=d-n;j<=n;j++) {
                for(int k=j+1;k<=n;k++) {
                    writer.println(transform(j,d-j)+" "+transform(k,d-k));
                }
            }
        }
        // 7
        for(int i=1;i<=n;i++) {
            for(int j=1;j<=n;j++) {
                if(j==n) writer.println(transform(j,i)-1);
                else writer.print((transform(j,i)-1)+" ");
            }
        }
        writer.close();
    }
    private int transform(int i, int j) {
        return (2*(j-1)+2*n*(i-1))+1;
    }
}
