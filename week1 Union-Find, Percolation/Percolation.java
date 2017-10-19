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
        // two more special points
        uf = new WeightedQuickUnionUF(dim*dim + 2);
        int i;

        // //这个逻辑有错 想一来把第一行和最后一行都union了
        // for(i = 1; i < dim; i++){
        //     uf.union(gridConvertToUF(1, i), dim*dim);
        //     uf.union(gridConvertToUF(dim, i), dim*dim + 1);
        // }
    }
    
    public int numberOfOpenSites(){
        return numberOpen;
    }

    private void checkDimension(int row, int col){
        if (row < 1 || row > dim) {
            throw new IllegalArgumentException("row index out of dimension");  
        }
        if (col < 1 || col > dim) {
            throw new IllegalArgumentException("col index out of dimension");  
        }
    }

    public boolean isOpen(int row, int col){
        checkDimension(row, col);
        return grid[row-1][col-1];
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
        // first row
        if(row == 1){
            uf.union(gridConvertToUF(row, col), dim*dim);
        }
        // last row
        if(row == dim){
            uf.union(gridConvertToUF(row, col), dim*dim + 1);
        }
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
        return uf.connected(dim*dim , dim*dim + 1);
    }

    public boolean isFull(int row, int col){
        return uf.connected(gridConvertToUF(row, col), dim*dim);
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
