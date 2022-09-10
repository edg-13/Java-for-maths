/*
 * PROJECT III: GeneralMatrix.java
 *
 * This file contains a template for the class GeneralMatrix. Not all methods
 * are implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file. You will also need to have completed
 * the Matrix class.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file!
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 */

import java.util.Arrays;

public class GeneralMatrix extends Matrix {
    /**
     * This instance variable stores the elements of the matrix.
     */
    private double[][] data;

    /**
     * Constructor function: should initialise m and n through the Matrix
     * constructor and set up the data array.
     *
     * @param m  The first dimension of the array.
     * @param n  The second dimension of the array.
     */
    public GeneralMatrix(int m, int n) throws MatrixException {
        // You need to fill in this method.
        super(m, n);
        data = new double[m][n];
        if(m<1 || n<1){
            throw new MatrixException("Invalid dimensions");
        }
    }

    /**
     * Constructor function. This is a copy constructor; it should create a
     * copy of the matrix A.
     *
     * @param A  The matrix to create a copy of.
     */
    public GeneralMatrix(GeneralMatrix A) {
        // You need to fill in this method.
        super(A.m,A.n);
        data = A.data;
    }

    /**
     * Getter function: return the (i,j)'th entry of the matrix.
     *
     * @param i  The location in the first co-ordinate.
     * @param j  The location in the second co-ordinate.
     * @return   The (i,j)'th entry of the matrix.
     */
    public double getIJ(int i, int j) {
        // You need to fill in this method.
        if(i>m || j>n) {
            throw new MatrixException("Co-ordinates not inside the matrix");
        }
        else if(i<=0 || j<=0) {
            throw new MatrixException("Co-ordinates not inside the matrix");
        }
        else{
            return data[i-1][j-1];
        }
    }

    /**
     * Setter function: set the (i,j)'th entry of the data array.
     *
     * @param i    The location in the first co-ordinate.
     * @param j    The location in the second co-ordinate.
     * @param val  The value to set the (i,j)'th entry to.
     */
    public void setIJ(int i, int j, double val) {
        // You need to fill in this method.
        if (i > m || j > n) {
            throw new MatrixException("Co-ordinates not inside the matrix");
        }
        else if(i<=0 || j<=0) {
            throw new MatrixException("Co-ordinates not inside the matrix");
        }
        else {
            data[i - 1][j - 1] = val;
        }
    }

    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {
        // You need to fill in this method.
        double[] d = new double[1];
        GeneralMatrix A = this.decomp(d);
        double t=1;
        for(int a =1; a<=A.m; a++){
            t*=A.getIJ(a,a);
        }
        return (d[0]*t);
    }

    /**
     * Add the matrix to another matrix A.
     *
     * @param A  The Matrix to add to this matrix.
     * @return   The sum of this matrix with the matrix A.
     */
    public Matrix add(Matrix A) {
        // You need to fill in this method.
        GeneralMatrix s = new GeneralMatrix(m,n);
        if(A.m==s.m && A.n==s.n) {
            for(int a =1; a<m+1; a++) {
                for (int b =1; b<n+1; b++) {
                    s.setIJ(a,b, A.getIJ(a,b) + this.getIJ(a,b));
                }
            }
        }else {
            throw new MatrixException("Dimension of matrices do not match");
        }
        return s;
    }

    /**
     * Multiply the matrix by another matrix A. This is a _left_ product,
     * i.e. if this matrix is called B then it calculates the product BA.
     *
     * @param A  The Matrix to multiply by.
     * @return   The product of this matrix with the matrix A.
     */
    public Matrix multiply(Matrix A) {
        // You need to fill in this method.
        if(A.m==this.n) {
            GeneralMatrix M = new GeneralMatrix(this.m, A.n);
            for (int a = 1; a < A.n + 1; a++) {
                for (int b = 1; b < this.m + 1; b++) {
                    double s = 0;
                    for (int c = 1; c < A.m + 1; c++) {
                        s += this.getIJ(b, c) * A.getIJ(c, a);
                    }
                    M.setIJ(b,a,s);
                }
            }
            return M;
        }
        else {
            throw new MatrixException("Matrices are not multiplicatively conformable");
        }
    }

    /**
     * Multiply the matrix by a scalar.
     *
     * @param a  The scalar to multiply the matrix by.
     * @return   The product of this matrix with the scalar a.
     */
    public Matrix multiply(double a) {
        // You need to fill in this method.
        GeneralMatrix s = new GeneralMatrix(m, n);
        for (int x = 1; x < m + 1; x++) {
            for (int b = 1; b < n + 1; b++) {
                s.setIJ(x,b,this.getIJ(x, b) * a);
            }
        }
        return s;
    }




    /**
     * Populates the matrix with random numbers which are uniformly
     * distributed between 0 and 1.
     */
    public void random() {
        // You need to fill in this method.
        for(int a=1; a<=this.m; a++){
            for(int b=1; b<=this.n; b++){
                this.setIJ(a,b,Math.random());
            }
        }
    }

    /**
     * Returns the LU decomposition of this matrix; i.e. two matrices L and U
     * so that A = LU, where L is lower-diagonal and U is upper-diagonal.
     *
     * On exit, decomp returns the two matrices in a single matrix by packing
     * both matrices as follows:
     *
     * [ u_11 u_12 u_13 u_14 ]
     * [ l_21 u_22 u_23 u_24 ]
     * [ l_31 l_32 u_33 u_34 ]
     * [ l_41 l_42 l_43 l_44 ]
     *
     * where u_ij are the elements of U and l_ij are the elements of l. When
     * calculating the determinant you will need to multiply by the value of
     * d[0] calculated by the function.
     *
     * If the matrix is singular, then the routine throws a MatrixException.
     *
     * This method is an adaptation of the one found in the book "Numerical
     * Recipies in C" (see online for more details).
     *
     * @param d  An array of length 1. On exit, the value contained in here
     *           will either be 1 or -1, which you can use to calculate the
     *           correct sign on the determinant.
     * @return   The LU decomposition of the matrix.
     */
    public GeneralMatrix decomp(double[] d) {
        // This method is complete. You should not even attempt to change it!!
        if (n != m)
            throw new MatrixException("Matrix is not square");
        if (d.length != 1)
            throw new MatrixException("d should be of length 1");

        int           i, imax = -10, j, k;
        double        big, dum, sum, temp;
        double[]      vv   = new double[n];
        GeneralMatrix a    = new GeneralMatrix(this);

        d[0] = 1.0;

        for (i = 1; i <= n; i++) {
            big = 0.0;
            for (j = 1; j <= n; j++)
                if ((temp = Math.abs(a.data[i-1][j-1])) > big)
                    big = temp;
            if (big == 0.0)
                throw new MatrixException("Matrix is singular");
            vv[i-1] = 1.0/big;
        }

        for (j = 1; j <= n; j++) {
            for (i = 1; i < j; i++) {
                sum = a.data[i-1][j-1];
                for (k = 1; k < i; k++)
                    sum -= a.data[i-1][k-1]*a.data[k-1][j-1];
                a.data[i-1][j-1] = sum;
            }
            big = 0.0;
            for (i = j; i <= n; i++) {
                sum = a.data[i-1][j-1];
                for (k = 1; k < j; k++)
                    sum -= a.data[i-1][k-1]*a.data[k-1][j-1];
                a.data[i-1][j-1] = sum;
                if ((dum = vv[i-1]*Math.abs(sum)) >= big) {
                    big  = dum;
                    imax = i;
                }
            }
            if (j != imax) {
                for (k = 1; k <= n; k++) {
                    dum = a.data[imax-1][k-1];
                    a.data[imax-1][k-1] = a.data[j-1][k-1];
                    a.data[j-1][k-1] = dum;
                }
                d[0] = -d[0];
                vv[imax-1] = vv[j-1];
            }
            if (a.data[j-1][j-1] == 0.0)
                a.data[j-1][j-1] = 1.0e-20;
            if (j != n) {
                dum = 1.0/a.data[j-1][j-1];
                for (i = j+1; i <= n; i++)
                    a.data[i-1][j-1] *= dum;
            }
        }

        return a;
    }

    /*
     * Your tester function should go here.
     */
    public static void main(String[] args) {
        // You need to fill in this method.
        GeneralMatrix A = new GeneralMatrix(3,3);
        GeneralMatrix B = new GeneralMatrix(3, 2);
        GeneralMatrix C = new GeneralMatrix(2, 2);
        A.data=new double[][]{{1,-1,2},{3,4,2}, {2,0,-3}};
        B.data =new double[][]{{1,2},{3,4},{5,6}};
        C.data=new double[][]{{1,2},{3,4}};
        System.out.println(A.multiply(B));
        System.out.println(A.multiply(3));
        System.out.println(A.getIJ(2,3));
        System.out.println(A.determinant());
        GeneralMatrix D = new GeneralMatrix(2,2);
        D.setIJ(1,1,1);
        D.setIJ(1,2,0);
        D.setIJ(2,1,0);
        D.setIJ(2,2,1);
        System.out.println(D.add(A));

    }
}
