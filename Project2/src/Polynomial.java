/*
 * PROJECT II: Polynomial.java
 *
 * This file contains a template for the class Polynomial. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file.
 *
 * This class is designed to use Complex in order to represent polynomials
 * with complex co-efficients. It provides very basic functionality and there
 * are very few methods to implement! The project formulation contains a
 * complete description.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file! You should also test this class using the main()
 * function.
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 */

class Polynomial {
    /**
     * An array storing the complex co-efficients of the polynomial.
     */
    Complex[] coeff;

    // ========================================================
    // Constructor functions.
    // ========================================================

    /**
     * General constructor: assigns this polynomial a given set of
     * co-efficients.
     *
     * @param coeff  The co-efficients to use for this polynomial.
     */
    public Polynomial(Complex[] coeff) {
        // You need to fill in this function.
        int b = coeff.length;
        int counter = 0;
        for (int i = b - 1; i >= 0; i--) {
            if (coeff[i].getReal()==0 && coeff[i].getImag()==0) {
                counter++;
            } else {
                break;
            }
        }
        this.coeff = new Complex[b-counter];
        for(int x =0; x<b-counter; x++) {
            this.coeff[x] = coeff[x];
        }
    }

    /**
     * Default constructor: sets the Polynomial to the zero polynomial.
     */
    public Polynomial() {
        // You need to fill in this function.
        Complex a = new Complex(0,0);
        coeff = new Complex[] {a};
    }

    // ========================================================
    // Operations and functions with polynomials.
    // ========================================================

    /**
     * Create a string representation of the polynomial.
     *
     * For example: (1.0+1.0i)+(1.0+2.0i)X+(1.0+3.0i)X^2
     */
    public String toString() {
        // You need to fill in this function.
        String b = "";
        String c = "("+coeff[0]+")+("+coeff[1]+")X";
        for(int a =2; a<coeff.length; a++) {
            b+="+("+coeff[a]+")X^"+a;
        }
        return c+b;
    }

    /**
     * Returns the degree of this polynomial.
     */
    public int degree() {
        // You need to fill in this function.
        int b = coeff.length-1;
        int counter = 0;
        Complex a = new Complex(0,0);
        for (int i = b; i >= 0; i--) {
            if (coeff[i].getReal()==0 && coeff[i].getImag()==0) {
                counter++;
            } else {
                break;
            }
        }
        return b-counter;

    }

    /**
     * Evaluates the polynomial at a given point z.
     *
     * @param z  The point at which to evaluate the polynomial
     * @return   The complex number P(z).
     */
    public Complex evaluate(Complex z) {
        // You need to fill in this function.
        int n = coeff.length-1;
        Complex s = coeff[n];
        for (int i = n-1; i >= 0; i--) {
            s = coeff[i].add(z.multiply(s));
        }
        return s;
    }

    /**
     * Calculate and returns the derivative of this polynomial.
     *
     * @return The derivative of this polynomial.
     */
    public Polynomial derivative() {
        // You need to fill in this function.
        Complex[] c = new Complex[coeff.length-1];
        for(int i =0; i<coeff.length-1; i++) {
            c[i] = coeff[i + 1].multiply(i + 1);
        }
        return new Polynomial(c);
    }


    // ========================================================
    // Tester function.
    // ========================================================

    public static void main(String[] args) {
        // You need to fill in this function.
        Complex a = new Complex(-1,0);
        Complex b = new Complex(0,0);
        Complex c = new Complex(0,0);
        Complex d = new Complex(1,0);
        Complex e = new Complex(0,0);
        Complex f = new Complex(0.9999999999999994,-4.556244717939637E-16);
        Complex[] arr = {a,b,c,d};
        Polynomial p = new Polynomial(arr);
        System.out.println(p.toString());
        System.out.println(p.degree());
        System.out.println(p.derivative().toString());
        System.out.println(p.evaluate(e));
        System.out.println(e.add(((p.evaluate(e)).divide(p.derivative().evaluate(e))).minus()));
        //Complex f = (p.evaluate(e));
        Complex g = (p.derivative().evaluate(e));
        System.out.println(f);
        System.out.println(g);
        System.out.println(f.divide(g));
        System.out.println((f.divide(g)).minus());
        System.out.println(e.add((f.divide(g)).minus()));
        System.out.println((p.evaluate(f).divide(p.derivative().evaluate(f))).abs());
    }
}