import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.awt.Color;
import java.util.Scanner;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
public class Main {
    public static Timer timer = new Timer("DPLL");
    public static boolean solvable;
    public static DPLL dpllSolver = new DPLL();
    public static void main(String[] args) throws IOException {
        myMain();
    }
    @SuppressWarnings("SameParameterValue")
    private static void add(XYSeries courbe1, int i, String heuristique, boolean oneSol, String file) throws IOException {
        new Parser(new FileInputStream(file));
        timer.reset();
        dpllSolver.solve(oneSol, heuristique);
        courbe1.add(i, timer.time_past());
    }
    @SuppressWarnings("unused, SameParameterValue")
    private static void timeSat(int nbVar, int minClause, int maxClause, boolean oneSol, String file) throws IOException {
        XYSeries courbe1 = new XYSeries("simple order");
        XYSeries courbe2 = new XYSeries("first satisfy");
        XYSeries courbe3 = new XYSeries("first fail");
        for(int nbClause=minClause;nbClause<=maxClause;nbClause=nbClause+nbVar) {
            new RandomGenerator(nbVar,nbClause);
            add(courbe1,nbClause/nbVar,"order",oneSol,file);
            add(courbe2,nbClause/nbVar,"satisfy",oneSol,file);
            add(courbe3,nbClause/nbVar,"fail",oneSol,file);
        }
        XYSeriesCollection xyDataset = new XYSeriesCollection(courbe1);
        xyDataset.addSeries(courbe2);
        xyDataset.addSeries(courbe3);
        JFreeChart graph = ChartFactory.createXYLineChart("Dpll Sat 3 aléatoire","nbClause/nbVar","temps",
                xyDataset,PlotOrientation.VERTICAL,true,true,false);
        graph.setBackgroundPaint(new Color(227, 226, 226));
        ChartUtilities.saveChartAsJPEG(new File("testGenerator/chart.JPEG"), graph, 1200, 800);
    }
    @SuppressWarnings("unused, SameParameterValue")
    private static void timePigeon(int min, int max, boolean oneSol, String file) throws IOException {
        XYSeries courbe1 = new XYSeries("simple order");
        XYSeries courbe2 = new XYSeries("first satisfy");
        XYSeries courbe3 = new XYSeries("first fail");
        for(int n=min;n<=max;n++) {
            new PigeonGenerator(n);
            add(courbe1,n,"order",oneSol,file);
            add(courbe2,n,"satisfy",oneSol,file);
            add(courbe3,n,"fail",oneSol,file);
        }
        XYSeriesCollection xyDataset = new XYSeriesCollection(courbe1);
        xyDataset.addSeries(courbe2);
        xyDataset.addSeries(courbe3);
        JFreeChart graph = ChartFactory.createXYLineChart("Dpll Pigeon","O(n)","temps",
                xyDataset,PlotOrientation.VERTICAL,true,true,false);
        graph.setBackgroundPaint(new Color(227, 226, 226));
        ChartUtilities.saveChartAsJPEG(new File("testGenerator/chart.JPEG"), graph, 1200, 800);
    }
    @SuppressWarnings("unused, SameParameterValue")
    private static void timeDame(int min, int max, boolean oneSol, String file) throws IOException {
        XYSeries courbe1 = new XYSeries("simple order");
        XYSeries courbe2 = new XYSeries("first satisfy");
        XYSeries courbe3 = new XYSeries("first fail");
        for(int n=min;n<=max;n++) {
            new DameGenerator(n);
            add(courbe1,n,"order",oneSol,file);
            add(courbe2,n,"satisfy",oneSol,file);
            add(courbe3,n,"fail",oneSol,file);
        }
        XYSeriesCollection xyDataset = new XYSeriesCollection(courbe1);
        xyDataset.addSeries(courbe2);
        xyDataset.addSeries(courbe3);
        JFreeChart graph = ChartFactory.createXYLineChart("Dpll Dame","O(n)","temps",
                xyDataset,PlotOrientation.VERTICAL,true,true,false);
        graph.setBackgroundPaint(new Color(227, 226, 226));
        ChartUtilities.saveChartAsJPEG(new File("testGenerator/chart.JPEG"), graph, 1200, 800);
    }
    @SuppressWarnings("unused, SameParameterValue")
    private static void print(boolean oneSol, String heuristique){
        solvable=dpllSolver.solve(oneSol, heuristique);
        System.out.println("Solutions du probleme (Pile des literaux mis a vrai) : "+(!solvable ? "Pas de solutions" : dpllSolver.solutions.size()+" solutions"));
        for(int i=0; i<dpllSolver.solutions.size();i++) System.out.println(Arrays.toString(dpllSolver.solutions.get(i)));
    }
    public static class Timer {
        long startTime;
        String name;
        public Timer(String name) {
            startTime = System.currentTimeMillis();
            this.name = name;
        }
        public long time_past() {
            return System.currentTimeMillis() - startTime;
        }
        @SuppressWarnings("unused")
        public void print_time_past() {
            System.out.printf("Timer %s : %f sec. past%n", name, ((double) time_past()) / 1000);
        }
        public void reset() {
            startTime=System.currentTimeMillis();
        }
    }
    public static void myMain() throws IOException {
        int nbVar,nbClause;boolean oneSol;String heuristique;
        System.out.println("DPLL SOLVER");
        System.out.println("What do you want to solve : sat -> 1 / pigeon -> 2 / dame -> 3");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        while (!line.equals("1") && !line.equals("2") && !line.equals("3")) {
            System.out.println("Valeur 1,2,3 accepte");
            line=scanner.nextLine();
        }
        if(line.equals("1")) {                                                  //SAT 3
            System.out.println("Voulez vous une afficher les solutions ou une comparaison des heuristiques ? solutions -> 1 / comparaison -> 0");
            line = scanner.nextLine();
            while (!line.equals("1") && !line.equals("0")) {
                System.out.println("1/0");
                line = scanner.nextLine();
            }
            if (line.equals("0")) {
                System.out.println("Entrez le nombre de variable : (1-20)");
                line = scanner.nextLine();
                while (!line.matches("\\d+")) {
                    System.out.println("Nombre entier requis");
                    line = scanner.nextLine();
                }
                while (Integer.parseInt(line) < 1 || Integer.parseInt(line) > 20) {
                    System.out.println("Entier entre 1 et 20 requis");
                    line = scanner.nextLine();
                }
                nbVar = Integer.parseInt(line);
                System.out.println("Voulez vous 1 solution ? yes -> 1 / no -> 0");
                line = scanner.nextLine();
                while (!line.equals("1") && !line.equals("0")) {
                    System.out.println("Format : 1 or 0");
                    line = scanner.nextLine();
                }
                oneSol = line.equals("1");
                System.out.println("DPLL is running !");
                timeSat(nbVar, 10, 150, oneSol, "testGenerator/test.txt");
            }
            else {
                System.out.println("Entrez le nombre de variable : (1-40)");
                line = scanner.nextLine();
                while (!line.matches("\\d+")) {
                    System.out.println("Nombre entier requis");
                    line = scanner.nextLine();
                }
                while (Integer.parseInt(line) < 1 || Integer.parseInt(line) > 40) {
                    System.out.println("Entier entre 1 et 40 requis");
                    line = scanner.nextLine();
                }
                nbVar = Integer.parseInt(line);
                System.out.println("Entrez le nombre de clause : (1-200)");
                line = scanner.nextLine();
                while (!line.matches("\\d+")) {
                    System.out.println("Nombre entier requis");
                    line = scanner.nextLine();
                }
                while (Integer.parseInt(line) < 1 || Integer.parseInt(line) > 200) {
                    System.out.println("Entier entre 1 et 200 requis");
                    line = scanner.nextLine();
                }
                nbClause = Integer.parseInt(line);
                System.out.println("Voulez vous 1 solution ? yes -> 1 / no -> 0");
                line = scanner.nextLine();
                while (!line.equals("1") && !line.equals("0")) {
                    System.out.println("Format : 1 or 0");
                    line = scanner.nextLine();
                }
                oneSol = line.equals("1");
                System.out.println("Souhaitez vous une heuristique ? (satisfy,fail,no)");
                line = scanner.nextLine();
                while (!line.equals("no") && !line.equals("satisfy") && !line.equals("fail")) {
                    System.out.println("Format : satisfy/fail/no");
                    line = scanner.nextLine();
                }
                heuristique = line.equals("no") ? "order" : line;
                System.out.println("DPLL is running !");
                new RandomGenerator(nbVar, nbClause);
                new Parser(new FileInputStream("testGenerator/test.txt"));
                timer.reset();
                print(oneSol, heuristique);
                timer.print_time_past();
            }
        }
        if(line.equals("2")) {                                                  //Pigeon
            System.out.println("Voulez vous une afficher les solutions ou une comparaison des heuristiques ? solutions -> 1 / comparaison -> 0");
            line = scanner.nextLine();
            while (!line.equals("1") && !line.equals("0")) {
                System.out.println("1/0");
                line = scanner.nextLine();
            }
            if(line.equals("0")) {
                System.out.println("Voulez vous 1 solution ? yes -> 1 / no -> 0");
                line=scanner.nextLine();
                while (!line.equals("1") && !line.equals("0")) {
                    System.out.println("Format : 1 or 0");
                    line=scanner.nextLine();
                }
                oneSol = line.equals("1");
                System.out.println("DPLL is running !");
                timePigeon(3, 8, oneSol, "testGenerator/pigeon.txt");
            }
            else {
                System.out.println("Entrez le nombre de pigeon : (2-9)");
                line=scanner.nextLine();
                while (!line.matches("\\d+")) {
                    System.out.println("Nombre entier requis");
                    line=scanner.nextLine();
                }
                while (Integer.parseInt(line)<2 || Integer.parseInt(line)>9) {
                    System.out.println("Entier entre 2 et 9 requis");
                    line=scanner.nextLine();
                }
                nbVar=Integer.parseInt(line);
                System.out.println("Voulez vous 1 solution ? yes -> 1 / no -> 0");
                line=scanner.nextLine();
                while (!line.equals("1") && !line.equals("0")) {
                    System.out.println("Format : 1 or 0");
                    line=scanner.nextLine();
                }
                oneSol = line.equals("1");

                System.out.println("Souhaitez vous une heuristique ? (satisfy,fail,no)");
                while (!line.equals("no") && !line.equals("satisfy") && !line.equals("fail")) {
                    System.out.println("Format : satisfy/fail/no");
                    line=scanner.nextLine();
                }
                heuristique = line.equals("no") ? "order" : line;
                System.out.println("DPLL is running !");
                new PigeonGenerator(nbVar);
                new Parser(new FileInputStream("testGenerator/pigeon.txt"));
                timer.reset();
                print(oneSol,heuristique);
                timer.print_time_past();
            }
        }
        if(line.equals("3")) {                                                  //Dame
            System.out.println("Voulez vous une afficher les solutions ou une comparaison des heuristiques ? solutions -> 1 / comparaison -> 0");
            line = scanner.nextLine();
            while (!line.equals("1") && !line.equals("0")) {
                System.out.println("1/0");
                line = scanner.nextLine();
            }
            if(line.equals("0")) {
                System.out.println("Voulez vous 1 solution ? yes -> 1 / no -> 0");
                line=scanner.nextLine();
                while (!line.equals("1") && !line.equals("0")) {
                    System.out.println("Format : 1 or 0");
                    line=scanner.nextLine();
                }
                oneSol = line.equals("1");
                System.out.println("DPLL is running !");
                timeDame(3, 8, oneSol, "testGenerator/dame.txt");
            }
            else {
                System.out.println("Entrez le nombre de dame : (3-9)");
                line=scanner.nextLine();
                while (!line.matches("\\d+")) {
                    System.out.println("Nombre entier requis");
                    line=scanner.nextLine();
                }
                while (Integer.parseInt(line)<3 || Integer.parseInt(line)>9) {
                    System.out.println("Entier entre 3 et 9 requis");
                    line=scanner.nextLine();
                }
                nbVar=Integer.parseInt(line);
                System.out.println("Voulez vous 1 solution ? yes -> 1 / no -> 0");
                line=scanner.nextLine();
                while (!line.equals("1") && !line.equals("0")) {
                    System.out.println("Format : 1 or 0");
                    line=scanner.nextLine();
                }
                oneSol = line.equals("1");
                System.out.println("Souhaitez vous une heuristique ? (satisfy,fail,no)");
                line=scanner.nextLine();
                while (!line.equals("no") && !line.equals("satisfy") && !line.equals("fail")) {
                    System.out.println("Format : satisfy/fail/no");
                    line=scanner.nextLine();
                }
                heuristique = line.equals("no") ? "order" : line;
                System.out.println("DPLL is running !");
                new DameGenerator(nbVar);
                new Parser(new FileInputStream("testGenerator/dame.txt"));
                timer.reset();
                print(oneSol,heuristique);
                timer.print_time_past();
            }
        }
    }
}