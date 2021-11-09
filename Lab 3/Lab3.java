import java.io.*;
import java.util.*;

public class Lab3
{
    /**
     *  Problem: Sort multi-digit integers (with n total digits) in O(n) time.
     *  (Technically, it is O(n * b) time. However, since our base b = 128 is constant, it is O(n).)
     */
    private static void problem(byte[][] arr)
    {
        int[] c = new int[129];
        int length = arr.length;

        byte[][] newArr = new byte[length][];
        int max = 0;
        int start;
        int temp = 0;

        for(int i = 0; i < length; i++){
            if(arr[i].length > max) max = arr[i].length;
        }
        //outer loop starting with least significant digit
        for(int i = 0; i < max; i++){
            start = temp;
            //fill counting array with zeros
            Arrays.fill(c, start);

            //count
            for(int j = start; j < length; j++){
                if(i == arr[j].length){
                    c[0]++;
                    temp += 1;
                }
                else c[arr[j][i] + 1]++;
            }

            //counting sort stuff
            for(int j = 1; j < 129; j++){
                c[j] += c[j - 1] - start;
            }
            for(int j = 128; j > 0; j--){
                c[j] = c[j - 1];
            }
            c[0] = start;

            //build newArr
            for(int j = start; j < length; j++){
                if(i == arr[j].length) {
                    newArr[c[0]] = arr[j];
                    c[0]++;
                }
                else {
                    newArr[c[arr[j][i] + 1]] = arr[j];
                    c[arr[j][i] + 1]++;
                }
            }

            //copy newArr to arr
            for(int j = start; j < length; j++){
                arr[j] = newArr[j];
            }

        }

    }

    // ---------------------------------------------------------------------
    // Do not change any of the code below!

    private static final int LabNo = 3;
    private static final String quarter = "Spring 2020";
    private static final Random rng = new Random(654321);

    private static boolean testProblem(byte[][] testCase)
    {
        byte[][] numbersCopy = new byte[testCase.length][];

        // Create copy.
        for (int i = 0; i < testCase.length; i++)
        {
            numbersCopy[i] = testCase[i].clone();
        }

        // Sort
        problem(testCase);
        Arrays.sort(numbersCopy, new numberComparator());

        // Compare if both equal
        if (testCase.length != numbersCopy.length)
        {
            return false;
        }

        for (int i = 0; i < testCase.length; i++)
        {
            if (testCase[i].length != numbersCopy[i].length)
            {
                return false;
            }

            for (int j = 0; j < testCase[i].length; j++)
            {
                if (testCase[i][j] != numbersCopy[i][j])
                {
                    return false;
                }
            }
        }

        return true;
    }

    // Very bad way of sorting.
    private static class numberComparator implements Comparator<byte[]>
    {
        @Override
        public int compare(byte[] n1, byte[] n2)
        {
            // Ensure equal length
            if (n1.length < n2.length)
            {
                byte[] tmp = new byte[n2.length];
                for (int i = 0; i < n1.length; i++)
                {
                    tmp[i] = n1[i];
                }
                n1 = tmp;
            }

            if (n1.length > n2.length)
            {
                byte[] tmp = new byte[n1.length];
                for (int i = 0; i < n2.length; i++)
                {
                    tmp[i] = n2[i];
                }
                n2 = tmp;
            }

            // Compare digit by digit.
            for (int i = n1.length - 1; i >=0; i--)
            {
                if (n1[i] < n2[i]) return -1;
                if (n1[i] > n2[i]) return 1;
            }

            return 0;
        }
    }

    public static void main(String args[])
    {
        System.out.println("CS 302 -- " + quarter + " -- Lab " + LabNo);
        testProblems();
    }

    private static void testProblems()
    {
        int noOfLines = 10000;

        System.out.println("-- -- -- -- --");
        System.out.println(noOfLines + " test cases.");

        boolean passedAll = true;

        for (int i = 1; i <= noOfLines; i++)
        {
            byte[][] testCase =  createTestCase(i);

            boolean passed = false;
            boolean exce = false;

            try
            {
                passed = testProblem(testCase);
            }
            catch (Exception ex)
            {
                passed = false;
                exce = true;
            }

            if (!passed)
            {
                System.out.println("Test " + i + " failed!" + (exce ? " (Exception)" : ""));
                passedAll = false;

                break;
            }
        }

        if (passedAll)
        {
            System.out.println("All test passed.");
        }

    }

    private static byte[][] createTestCase(int testNo)
    {
        int maxSize = Math.min(100, testNo) + 5;
        int size = rng.nextInt(maxSize) + 5;

        byte[][] numbers = new byte[size][];

        for (int i = 0; i < size; i++)
        {
            int digits = rng.nextInt(maxSize) + 1;
            numbers[i] = new byte[digits];

            for (int j = 0; j < digits - 1; j++)
            {
                numbers[i][j] = (byte)rng.nextInt(128);
            }

            // Ensures that the most significant digit is not 0.
            numbers[i][digits - 1] = (byte)(rng.nextInt(127) + 1);
        }

        return numbers;
    }

}
