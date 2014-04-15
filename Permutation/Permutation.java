import java.util.Random;

public class Permutation extends Object {
    private int[] perm;

    /*
        This method demonstrates the material in this class.
    */
    public static void main (String[] args) {
    }

    /*
        Constructs an n-permutation object, representing a permutation of 0, 1, ..., n-1, 
        and initializes it to the lexicographically first such permutation.
    */
    public Permutation (int n) {
        this.perm = new int[n];
        for (int i = 0; i < this.perm.length; i++) {
            this.perm[i] = i;
        }
    }

    /*
        Constructs a specific permutation.
    */
    public Permutation (int[] perm) {
        if (isValid(perm)) {
            this.perm = new int[perm.length];
            for (int i = 0; i < this.perm.length; i++) {
                this.perm[i] = perm[i];
            }
        } else {
            throw new IllegalArgumentException("Invalid permutation.");
        }
    }

    /*
        Advances this permutation to the lexicographically next one (cycling from last back to first).
    */
    public void advance () {
        if (this.isLastPerm()) {
            this.reset();
        } else {
            int k = 0;
            int l = 0;
            int temp = 0;
            for (int i = 0; i < (this.perm.length - 1); i++) {
                if (this.perm[i] < this.perm[i + 1]) {
                    k = i;
                }
            }
            for (int i = 0; i < this.perm.length; i++) {
                if (this.perm[k] < this.perm[i]) {
                    l = i;
                }
            }
            temp = this.perm[k];
            this.perm[k] = this.perm[l];
            this.perm[l] = temp;
            for (int i = (k + 1); i < (((this.perm.length - (k + 1)) / 2) + (k + 1)); i++) {
                temp = this.perm[i];
                this.perm[i] = this.perm[(this.perm.length - 1) - (i - (k + 1))];
                this.perm[(this.perm.length - 1) - (i - (k + 1))] = temp;
            }
        }
    }

    /*
        Returns the i-th element of the permutation (indexed from zero).
    */
    public int getElement (int i) {
        if ((i < 0) || (i >= this.perm.length)) {
            throw new IllegalArgumentException("Index (" + i + ") is out of bounds.");
        } else {
            return this.perm[i];
        }
    }

    /*
        Returns true iff the permutation is identical to the lexicographically first one.
    */
    public boolean isFirstPerm () {
        for (int i = 0; i < (this.perm.length - 1); i++) {
            if (this.perm[i] > this.perm[i + 1]) {
                return false;
            }
        }
            return true;
    }
	
    /*
        Returns true iff the permutation is identical to the lexicographically last one.
    */
    public boolean isLastPerm () {
        for (int i = 0; i < (this.perm.length - 1); i++) {
            if (this.perm[i] < this.perm[i + 1]) {
                return false;
            }
        }
        return true;
    }

    /*
        Returns true iff the argument represents a valid permutation.
    */
    public static boolean isValid (int[] perm) {
        int[] a = mergeSort(perm);
        for (int i = 0; i < a.length; i++) {
            if (a[i] != i) {
                return false;
            } 
        }
        return true;
    }
	
    /*
        Returns a random n-permutation. An exception is thrown if n <= 0.
    */
    public static Permutation randomPermutation (int n) {
        Random randomElement = new Random(System.currentTimeMillis());
        if (n <= 0) {
            throw new IllegalArgumentException("Argument must be positive.");
        } else {
            Permutation perm = new Permutation(n);
            int[] a = perm.toArray();
            int d = 0;
            int temp = 0;
            for (int i = n; i >= 2; i--) {
                d = randomElement.nextInt(i);
                temp = a[i - 1];
                a[i - 1] = a[d];
                a[d] = temp;
            }
            return new Permutation(a);
        }
    }

    /*
        Resets this permutation to the lexicographically first one.
    */
    public void reset () {
        this.perm = new int[this.perm.length];
        for (int i = 0; i < this.perm.length; i++) {
            this.perm[i] = i;
        }
    }

    /*
        Returns an array representing the elements in the permutation.
    */
    public int[] toArray () {
        int[] arrayRepresentation = new int[this.perm.length];
        for (int i = 0; i < this.perm.length; i++) {
            arrayRepresentation[i] = this.perm[i];
        }
            return arrayRepresentation;
    }

    /*
        Returns a string that represents this permutation.
    */
    public String toString () {
        String stringRepresentation = "[";
        for (int i = 0; i < this.perm.length; i++) {
            stringRepresentation += this.perm[i];
            if (i < (this.perm.length - 1)) {
                stringRepresentation += ", ";
            } else {
                stringRepresentation += "]";
            }
        }
        return stringRepresentation;
    }

    /*
        Returns the number of distinct n-permutations.
    */
    public static long totalPermutations (int n) {
        long numberOfPermutations = 1;
        if (n <= 0) {
            throw new IllegalArgumentException("Argument must be positive.");
        } else if (n == 1) {
            return numberOfPermutations;
        } else {
            for (int i = 0; i < n; i++) {
	    numberOfPermutations *= (i + 1);
	    }
	    return (numberOfPermutations / 2);
        }
    }

    /*
        Performs a merge sort on an array.  Expected run time is O(n*log(n)).
    */
    public static int[] mergeSort (int[] a) {
        if (a.length <= 1) {
            return a;
        } else {
            int[] leftArray = new int[a.length / 2];
            int[] rightArray = new int[a.length - leftArray.length];
            for (int i = 0; i < leftArray.length; i++) {
                leftArray[i] = a[i];
            }
            for (int i = 0; i < rightArray.length; i++) {
                rightArray[i] = a[i + leftArray.length];
            }
            leftArray = mergeSort(leftArray);
            rightArray = mergeSort(rightArray);
            return merge(leftArray, rightArray);
        }
    }

    /*
        Merges the left and right subarrays.
    */
    public static int[] merge (int[] leftArray, int[] rightArray) {
        int[] mergedArray = new int[leftArray.length + rightArray.length];
        int leftIndex = 0;
        int rightIndex = 0;
        int mergedIndex = 0;
        while (mergedIndex < mergedArray.length) {
            if ((leftIndex < leftArray.length) && (rightIndex < rightArray.length)) {
                if (leftArray[leftIndex] <= rightArray[rightIndex]) {
                    mergedArray[mergedIndex] = leftArray[leftIndex];
                    leftIndex++;
                } else {
                    mergedArray[mergedIndex] = rightArray[rightIndex];
                    rightIndex++;
                }
            } else if (leftIndex < leftArray.length) {
                mergedArray[mergedIndex] = leftArray[leftIndex];
                leftIndex++;
            } else {
                mergedArray[mergedIndex] = rightArray[rightIndex];
                rightIndex++;
            } 
            mergedIndex++;
        }
        return mergedArray;
    }
}
