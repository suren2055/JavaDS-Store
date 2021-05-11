package Helpers;

import CollectionsADT.LinkedList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class SortingHelper {

    //region All have quadratic complexity,i.e O(n^2)
    public static <T extends Comparable> void selectionSort(T[] data) {
        int n = data.length;
        for (int k = 0; k < n - 1; k++) {
            int min = k;
            for (int j = k + 1; j < n; j++) {
                if (data[j].compareTo(data[min]) < 0)
                    min = j;

                T temp = data[k];
                data[k] = data[min];
                data[min] = temp;

            }
        }

    }

    public static <T extends Comparable> void insertionSort(T[] data, Comparator cmp) {
        int n = data.length;
        for (int k = 1; k < n; k++) {
            T cur = data[k];
            int j = k;
            while (j > 0 && (cmp == null ? data[j - 1].compareTo(cur) : cmp.compare(data[j - 1], cur)) > 0) {
                data[j] = data[j - 1];
                j--;
            }
            data[j] = cur;
        }
    }

    public static <T extends Comparable> void bubbleSort(T[] data) {

        int n = data.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {

                if (data[j].compareTo(data[j + 1]) > 0) {

                    T temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                }

            }

        }

    }

    public static <T extends Comparable> void bubbleSortOptimized(T[] data) {
        int n = data.length;
        for (int k = 0; k < n - 1; n++) {
            boolean swapped = false;//
            for (int j = 0; j < n - 1 - k; j++) {
                if (data[j].compareTo(data[j + 1]) > 0) {
                    T temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                    swapped = true;//
                }
            }
            if (!swapped)//
                break;
        }
    }
    //endregion


    //region N-log-N and linear time
    public static <T extends Comparable> void mergeSort(T[] S) {
        int n = S.length;
        if (n <= 1)
            return;
        if (n < 2) return; // array is trivially sorted
        int mid = n / 2;
        T[] S1 = Arrays.copyOfRange(S, 0, mid);// copy of first half
        T[] S2 = Arrays.copyOfRange(S, mid, n);// copy of second half
        // conquer (with recursion)

        mergeSort(S1);
        mergeSort(S2);

        merge(S1, S2, S);

    }

    private static <T extends Comparable> void merge(T[] S1, T[] S2, T[] S) {
        int i = 0, j = 0;
        while (i + j < S.length) {
            if (j == S2.length || (i < S1.length && S1[i].compareTo(S2[j]) < 0))
                S[i + j] = S1[i++];//copy ith element of S1 and increment i
            else
                S[i + j] = S2[j++];//copy jth element of S2 and increment j

        }

    }

    public static <T extends Comparable> void quickSort(T[] S) {
        int n = S.length;
        if (n < 2) return;// array is trivially sorted
        {
            // divide
            T pivot = S[n - 1];// using last as arbitrary pivot
            int m = 0, k = n;
            T[] temp = (T[]) new Object[n];
            for (int i = 0; i < n - 1; i++)// divide original into L, E, and G
                if (S[i].compareTo(pivot) < 0)// element is less than pivot
                    temp[m++] = S[i];
                else if (S[i].compareTo(pivot) > 0)// element is greater than pivot
                    temp[--k] = S[i];
            T[] L = Arrays.copyOfRange(temp, 0, m);
            T[] E = (T[]) new Object[k - m];
            Arrays.fill(E, pivot);
            T[] G = Arrays.copyOfRange(temp, k, n);
            // conquer (with recursion)
            quickSort(L); //
            quickSort(G); //
            // concatenate results
            System.arraycopy(L, 0, S, 0, m);
            System.arraycopy(E, 0, S, m, k - m);
            System.arraycopy(G, 0, S, k, n - k);
        }
    }

    //QuickSort in place option 1
    public static <T extends Comparable> void quickSortInPlace(T[] S, int a, int b) {
        // subarray is trivially sorted
        if (a >= b)
            return;
        int left = a;
        int right = b - 1;
        T pivot = S[b];
        T temp;
        while (left <= right) {
            // scan until reaching value equal or larger than pivot (or right marker)
            while (left <= right && S[left].compareTo(pivot) < 0)
                left++;
            // scan until reaching value equal or smaller than pivot (or left marker)
            while (left <= right && S[right].compareTo(pivot) > 0)
                right--;
            if (left <= right) { // indices did not strictly cross
                // so swap values and shrink range
                temp = S[left];
                S[left] = S[right];
                S[right] = temp;
                left++;
                right--;
            }
        }
        // put pivot into its final place (currently marked by left index)
        temp = S[left];
        S[left] = S[b];
        S[b] = temp;
        // make recursive calls
        quickSortInPlace(S, a, left - 1);
        quickSortInPlace(S, left + 1, b);
    }

    //QuickSort in place option 2
    public static <T extends Comparable> void quickSortInPlaceOption2(T[] arr, int s, int e) {

        if (e - s <= 1)
            return;
        int m = s + (e - s) / 2;
        int i = s;
        int j = e - 1;
        T piv = arr[m];

        while (i < j) {

            if (arr[i].compareTo(piv) < 0)
                i++;
            else {
                if (arr[j].compareTo(piv) > 0)
                    j--;
                else {
                    T tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                    i++;
                    j--;

                }
            }

            quickSortInPlaceOption2(arr, s, i - 1);
            quickSortInPlaceOption2(arr, j + 1, arr.length);


        }


    }

    //endregion

    //Bucket sort O(n)

    public static void bucketSort(int[] arr, int p) {

        ArrayList<LinkedList<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            buckets.add(new LinkedList<Integer>());

        for (int i = 0; i < arr.length; i++)
            buckets.get((int) (arr[i] / Math.pow(10, p) % 10)).addLast(arr[i]);

        int j = 0;
        for (LinkedList<Integer> b : buckets) {
            for (Integer e : b) {
                arr[j] = e;
                j++;
            }
        }
    }


    //Radix sort uses the bucket sort
    public static void radixSort(int[] arr) {

        int maxNumberOfDigits = 0;

        for (int i = 0; i < arr.length; i++) {
            int n = getNumberOfDigits(arr[i]);
            if (maxNumberOfDigits < n) {
                maxNumberOfDigits = n;
            }
        }

        for (int pos = 0; pos < maxNumberOfDigits; pos++) {
            bucketSort(arr, pos);
        }
    }


    private static int getNumberOfDigits(int x) {
        int counter = 0;
        while (x > 0) {
            counter++;
            x = x / 10;
        }
        return counter;
    }

    //TODO:See also counter sort
}
