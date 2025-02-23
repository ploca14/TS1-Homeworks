package cz.cvut.fel.ts1;

public class Plocivoj {
    public static void main(String[] args) {
        System.out.println(factorial(5));
    }

    public static long factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }

    public static int factorialRecursive(int n){
        if(n <= 1){
            return 1;
        }
        else{
            return n*factorialRecursive(n-1);
        }
    }
}
