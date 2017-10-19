import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats{
    private int trials;
    private int dim;
    private double[] probs;
    private double mean;
    private double stddev;

    public PercolationStats(int n, int trials){
      this.trials = trials;
      dim = n;
      probs = new double[trials];
      probs();
    }

    private double prob(){
      Percolation p = new Percolation(dim);      
      while(!p.percolates()){
        p.open(StdRandom.uniform(dim)+1, StdRandom.uniform(dim)+1);
      }
      return p.numberOfOpenSites() / ((double)dim*dim);
    }

    void probs(){
      int i;
      for(i=0; i<trials; i++){
        probs[i] = prob();
      }
    }

    public double mean(){
      mean = StdStats.mean(probs);
      return mean;
    }

    public double stddev(){
      stddev = StdStats.stddev(probs);
      return stddev;
    }

    public double confidenceLo(){
      return mean - 1.96 * stddev / Math.sqrt(dim);
    }

    public double confidenceHi(){
      return mean + 1.96 * stddev / Math.sqrt(dim);
    }

    public void plot(){
      StdStats.plotBars(probs);
    }

    public static void main(String args[]){
      PercolationStats s = new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
      System.out.println("mean:     " + s.mean());
      System.out.println("stddev:     " + s.stddev());
      System.out.println("95% confidence interval:     [" + s.confidenceLo() + "," +s.confidenceHi() + "]");
    }


}