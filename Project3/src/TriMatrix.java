/*
 * PROJECT III: TriMatrix.java
 *
 * This file contains a template for the class TriMatrix. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file. You will also need to have completed
 * the Matrix class.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file!
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 */

public class TriMatrix extends Matrix {
    /**
     * An array holding the diagonal elements of the matrix.
     */
    private double[] diag;

    /**
     * An array holding the upper-diagonal elements of the matrix.
     */
    private double[] upper;

    /**
     * An array holding the lower-diagonal elements of the matrix.
     */
    private double[] lower;

    /**
     * Constructor function: should initialise m and n through the Matrix
     * constructor and set up the data array.
     *
     * @param N  The dimension of the array.
     */
    public TriMatrix(int N) {
        // You need to fill in this method.
        super(N,N);
        if (N<1)
            throw new MatrixException("Invalid dimensions");
        diag = new double[N];
        upper = new double[N-1];
        lower = new double[N-1];

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
        if (i <= 0 || j <= 0){
            throw new MatrixException("Coordinates not valid");
        }
        else {
            if (i <= m && j <= m) {
                if (i == j)
                    return diag[i - 1];
                else if (i > j) {
                    if (i == j + 1)
                        return lower[j - 1];
                    else
                        return 0;
                } else {
                    if (j == i + 1)
                        return upper[i - 1];
                    else
                        return 0;
                }
            }
            else
                throw new MatrixException("Coordinates not valid");
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
        if (i <= 0 || j <= 0){
            throw new MatrixException("Coordinates not valid");
        }
        else {
            if (i <= m && j <= m) {
                if (i == j)
                    diag[i - 1] = val;
                else if (i > j) {
                    if (i == j + 1)
                        lower[j - 1] = val;
                    else
                        throw new MatrixException("Entry in this coordinate must be 0 by default");
                } else {
                    if (j == i + 1)
                        upper[i - 1]=val;
                    else
                        throw new MatrixException("Entry in this coordinate must be 0 by default");
                }
            }
            else
                throw new MatrixException("Coordinates not valid");
        }
    }

    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {
        // You need to fill in this method.
        double det = 1;
        for(int i =1; i<=this.n; i++){
            det*= this.decomp().getIJ(i,i);
        }
        return det;
    }

    /**
     * Returns the LU decomposition of this matrix. See the formulation for a
     * more detailed description.
     *
     * @return The LU decomposition of this matrix.
     */
    public TriMatrix decomp() {
        // You need to fill in this method.
        TriMatrix A = new TriMatrix(this.n);
        A.setIJ(1,1, this.getIJ(1,1));
        for(int c=1; c<this.n; c++){
            A.setIJ(c,c+1, this.getIJ(c,c+1));
        }
        for(int b =2; b<=this.n; b++){
            A.setIJ(b,b-1,this.getIJ(b,b-1)/A.getIJ(b-1,b-1));
            A.setIJ(b,b,this.getIJ(b,b)-(A.getIJ(b,b-1)*A.getIJ(b-1,b)));
        }
        return A;

    }

    /**
     * Add the matrix to another matrix A.
     *
     * @param A  The Matrix to add to this matrix.
     * @return   The sum of this matrix with the matrix A.
     */
    public Matrix add(Matrix A){
        // You need to fill in this method.
        if(A instanceof GeneralMatrix)
            return A.add(this);
        else if (A instanceof TriMatrix) {
            if(A.n==this.n){
                TriMatrix B = new TriMatrix(this.n);
                for(int a =1; a<this.n; a++){
                    B.setIJ(a+1, a, A.getIJ(a+1,a)+this.getIJ(a+1,a));
                }
                for(int b=1; b<=this.n; b++){
                    B.setIJ(b,b,A.getIJ(b,b)+this.getIJ(b,b));
                }
                for(int c=1; c<this.n; c++){
                    B.setIJ(c,c+1, A.getIJ(c,c+1)+this.getIJ(c,c+1));
                }
                return B;
            }else
                throw new MatrixException("Dimensions of matrices do not match");
        }else
            throw new MatrixException("Invalid object type");
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
        TriMatrix P = new TriMatrix(this.n);
        for(int x =1; x<this.n; x++){
            P.setIJ(x+1, x, this.getIJ(x+1,x)*a);
        }
        for(int b=1; b<=this.n; b++){
            P.setIJ(b,b,a*this.getIJ(b,b));
        }
        for(int c=1; c<this.n; c++){
            P.setIJ(c,c+1, a*this.getIJ(c,c+1));
        }
        return P;
    }

    /**
     * Populates the matrix with random numbers which are uniformly
     * distributed between 0 and 1.
     */
    public void random() {
        // You need to fill in this method.
        for(int a =1; a<this.n; a++){
            this.setIJ(a+1, a, Math.random());
        }
        for(int b=1; b<=this.n; b++){
            this.setIJ(b,b,Math.random());
        }
        for(int c=1; c<this.n; c++){
            this.setIJ(c,c+1, Math.random());
        }
    }

    /*
     * Your tester function should go here.
     */
    public static void main(String[] args) {
        // You need to fill in this method.
        TriMatrix A = new TriMatrix(4);
        A.setIJ(1,1,1);
        A.setIJ(1,2,8);
        A.setIJ(2,1,5);
        A.setIJ(2,2,2);
        A.setIJ(2,3,9);
        A.setIJ(3,2,6);
        A.setIJ(3,3,3);
        A.setIJ(3,4,1);
        A.setIJ(4,3,7);
        A.setIJ(4,4,4);
        System.out.println(A);
        GeneralMatrix B = new GeneralMatrix(4,4);
        B.setIJ(1,1,4);
        B.setIJ(1,2,-1);
        B.setIJ(1,3,3);
        B.setIJ(1,4,7);
        B.setIJ(2,1,0);
        B.setIJ(2,2,1);
        B.setIJ(2,3,9);
        B.setIJ(2,4,2);
        B.setIJ(3,1,5);
        B.setIJ(3,2,6);
        B.setIJ(3,3,1);
        B.setIJ(3,4,1);
        B.setIJ(4,1,0);
        B.setIJ(4,2,3);
        B.setIJ(4,3,2);
        B.setIJ(4,4,5);
        System.out.println(B);
        System.out.println(B.multiply(A));
        System.out.println(A.multiply(2));
        System.out.println(A.determinant());


    }
}
