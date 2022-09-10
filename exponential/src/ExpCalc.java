public class ExpCalc {
    public static void main(String[] args) {
        double tol=1.0e-8, exp=1.0, fac=1.0, x=1.2;
        double n=1;
        while (Math.abs(exp-Math.E)>tol) {
            exp+=(java.lang.Math.pow(x,n))/fac;
            System.out.println(exp);
            n++;
            fac*=n;
        }
        System.out.println(exp);
    }
}
