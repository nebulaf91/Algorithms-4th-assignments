import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int dim; 
    private boolean[][] grid;
    private int numberOpen;
    private WeightedQuickUnionUF uf;
    public Percolation(int n){
        dim = n;
        grid = new boolean[dim][dim];
        numberOpen = 0;
        uf = new WeightedQuickUnionUF(dim*dim);
        int i;
        for(i = 1; i < dim; i++){
            uf.union(gridConvertToUF(1, i), gridConvertToUF(1, i+1));
            uf.union(gridConvertToUF(dim, i), gridConvertToUF(dim, i+1));
        }
    }
    
    public int numberOfOpenSites(){
        return numberOpen;
    }

    public boolean isOpen(int row, int col){
        checkDimension(row, col);
        return grid[row-1][col-1];
    }

    private void checkDimension(int row, int col){
        if (row < 1 || row > dim) {
            throw new IllegalArgumentException("row index out of dimension");  
        }
        if (col < 1 || col > dim) {
            throw new IllegalArgumentException("col index out of dimension");  
        }
    }

    private int gridConvertToUF(int row, int col){
        return (row-1) * dim + col - 1;
    }


    public void open(int row, int col){
    //row,col is between 1~n, to pass it into grid[][], requires to be row-1, col-1
        checkDimension(row, col);
        if(isOpen(row, col))
            return;
        grid[row-1][col-1] = true;
        //left
        if(col > 1 && grid[row-1][col-2]==true){
            uf.union(gridConvertToUF(row, col), gridConvertToUF(row, col-1));
        }
        //right
        if(col < dim && grid[row-1][col]==true){
            uf.union(gridConvertToUF(row, col), gridConvertToUF(row, col+1));
        }
        //up
        if(row > 1 && grid[row-2][col-1]==true){
            uf.union(gridConvertToUF(row, col), gridConvertToUF(row-1, col));
        }
        //down
        if(row < dim && grid[row][col-1]==true){
            uf.union(gridConvertToUF(row, col), gridConvertToUF(row+1, col));
        }
        numberOpen++;
    }

    public boolean percolates(){
        return uf.connected(gridConvertToUF(1, 1), gridConvertToUF(dim, dim));
    }

    public boolean isFull(int row, int col){
        return uf.connected(gridConvertToUF(row, col), gridConvertToUF(1, 1));
    }

    public static void main(String[] args){
        Percolation p = new Percolation(3);
        p.open(1,1);
        p.open(2,1);
        System.out.println(p.numberOfOpenSites());        
        p.open(3,1);
        System.out.println(p.isFull(2,2));                
        System.out.println(p.percolates());
    }
}
