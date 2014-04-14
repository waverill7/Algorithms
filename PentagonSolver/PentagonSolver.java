/*
    A brute-force program that uses Permutation.java to enumerate all 
    solutions (up to rotational symmetry) for the pentagon problem.
*/
public class PentagonSolver {

    public static void main (String[] args) {
        solve();
    }

    public static void solve () {
    	int n = 10;
        Permutation pentagon = new Permutation(n);
        int numberOfSides = 5;
        int[] sideSums = new int[numberOfSides];
        boolean solution = true;
        for (int permutation = 0; permutation < Permutation.totalPermutations(n); permutation++) {
        	for (int side = 0; side < numberOfSides; side++) {
        		sideSums[side] = 0;
        	}
            for (int side = 0; side < numberOfSides; side++) {
                for (int pocket = (side * 2); pocket <= ((side + 1) * 2); pocket++) {
                    sideSums[side] += pentagon.getElement(pocket % n);
                }
                if (sideSums[side] != sideSums[0]) {
                	solution = false;
                	break;
                }
            }
            if (solution) {
            	System.out.println(pentagon.toString());
            } else {
            	solution = true;
            }
            pentagon.advance();
        }
    }
}