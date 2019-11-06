package main;

/*****************************************************/
/*** Sort class currently contains some initial    ***/
/*** methods for implementing sorting algorithms   ***/
/***                                               ***/
/***     Initial Author: Jason Steggles 20/09/19   ***/
/***     Extended by: Adanna Obibuaku 19/10/19     ***/
/*****************************************************/

import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Scanner;

public class Sort {

	/** Size of array **/
	private int size;

	/** Number of used elements in array **/
	private int usedSize;

	/** Array of integers **/
	private int[] A;

	/** Global variables for counting sort comparisons **/
	public int compIS;
	/** Global comparison count for Insertion Sort **/
	public int compQS;
	/** Global comparison count for quick sort **/
	public int compNS;

	/** Global comparison count for new sort **/

	/*****************/
	/** Constructor **/
	/*****************/
	Sort(int max) {
		/** Initialise global sort count variables **/
		compIS = 0;
		compQS = 0;
		compNS = 0;

		/** Initialise size variables **/
		usedSize = 0; // counts the amount of elements its actually put into the array.
		size = max;

		/** Create Array of Integers **/
		A = new int[size];
	}

	/*********************************************/
	/*** Read a file of integers into an array ***/
	/*********************************************/
	public void readIn(String file) {
		try {
			/** Initialise loop variable **/
			usedSize = 0;

			/** Set up file for reading **/
			FileReader reader = new FileReader(file);
			Scanner in = new Scanner(reader);

			/** Loop round reading in data while array not full **/
			while (in.hasNextInt() && (usedSize < size)) {
				A[usedSize] = in.nextInt();
				usedSize++;
			}

		} catch (IOException e) {
			System.out.println("Error processing file " + file);
		}

	}

	/**********************/
	/*** Display array ***/
	/**********************/
	public void display(int line, String header) {
		/*** Integer Formatter - three digits ***/
		NumberFormat FI = NumberFormat.getInstance();
		FI.setMinimumIntegerDigits(3);

		/** Print header string **/
		System.out.print("\n" + header);

		/** Display array data **/
		for (int i = 0; i < usedSize; i++) {
			/** Check if new line is needed **/
			if (i % line == 0) {
				System.out.println();
			}

			/** Display an array element **/
			System.out.print(FI.format(A[i]) + " ");
		}
	}

	/* Used to get the actually size of elements in the array (not array size */
	public int getUsedSize() {
		return usedSize;
	}

	/* Used to get the array */
	public int[] getArray() {
		return A;
	}

	/*
	 * A swap needed in the partition method and new sort method
	 */
	private void swap(int lhs, int rhs) {
		int temp = getArray()[lhs]; // Holds the value in the left hand side in temp, as its about to be overwritten
		getArray()[lhs] = getArray()[rhs]; // The left side pointer now points to the value in the right handside
		getArray()[rhs] = temp; // Let the right hand side pointer point to now point to temp (The old value on
								// the left handside)
	}

	/*********************************/
	/*** Insertion sort algorithm ***/
	/*******************************/
	public void insertion() {
		/*** A loop to loop around the unsorted values in the array ***/
		/*** Beginning at element 1; as we assume is relatively sorted ***/
		for (int i = 1; i < getUsedSize(); i++) {
			int key = getArray()[i]; // Key is a copy of the element we are trying to move into the sorted array
			int j = i; // j is a copy of i, to move elements around

			compIS += 1; // Adds one to compIS for when it does not go into while loop.
			/* Here were fitting our key into the sorted elements */
			while (j > 0 && key < getArray()[j - 1]) { // while our key is less than the left element (ascending order)

				compIS += 1; // There is a comparison between elements so we add one

				getArray()[j] = getArray()[j - 1]; // makes a space in the array to shift our elements right
				j = j - 1; // goes to the next element to check if key is less than
			}
			getArray()[j] = key; // once key position is found put it in.
		}
	}

	private int partition(int left, int right) {
		int pivot = getArray()[right]; // select the right most element as pivot
		int leftPointer = left; // Left pointer starts from left side to begin and scans from right
		int rightPointer = right; // Right pointer starts from right to begin and scans from left

		// Loops while until pointers cross
		while (leftPointer < rightPointer) {
			// while left pointer scans from right make sure elements are less than pivot
			compQS += 1;
			while (getArray()[leftPointer] < pivot) {
				compQS += 1;
				leftPointer += 1; // increments by one to move right; on to the next element
			}
			/*
			 * this while loop has an extra guard to prevent the while right pointer from
			 * going beyond the first element (-1). This happens when the pivot is less than
			 * all the elements This guard is not on the left pointer as the left pointer
			 * can never be less than pivot. Therefore it doesn't go beyond the right most
			 * element.
			 */
			compQS += 1;
			while (getArray()[rightPointer] >= pivot && rightPointer > left) {
				compQS += 1;
				rightPointer -= 1; // decrements by one to move left; on to next element
			}

			if (leftPointer < rightPointer) { // Ensures that left pointer is less than right pointer
				swap(leftPointer, rightPointer); // If correct we can swap
			}
		}
		swap(leftPointer, right); // When finished swap the pivot with left pointer.
		return leftPointer; // Left pointer is now pivot, so return that

	}

	/**
	 * 
	 * quick sorts uses partition algorithm to partition the elements its a
	 * recursive algorithm to then call the other loops
	 * 
	 */

	public void quickSort(int left, int right) {
		if (left < right) {
			int pivotLocation = partition(left, right); // Partitions the array
			quickSort(left, pivotLocation - 1); // Quick sort applied to the left hand side
			quickSort(pivotLocation + 1, right); // Quick sort applied to the right hand side
		}
	}

	/*
	 * This algorithm finds the minimum value in the array from the specified
	 * position indicated by pos vairable. This algorithm would be used for the new
	 * sort
	 */
	private int findMinFrom(int pos) {
		int min = getArray()[pos]; // The pos variable is our assumed minimum
		for (int i = pos + 1; i < getUsedSize(); i++) { // While counting from next adjacent element to end of array
			compNS += 1; // Needs to increment here, as no matter the case there is always a comparison
			if (getArray()[i] < min) { // Check to see if any value is less than the assumed minimum
				min = getArray()[i]; // If so change the minimum
			}
		}
		return min;
	}

	/*
	 * New sort goes through each element in the array finds the minimum from the
	 * specified elements in the current loop then calls findMinFrom algorithm to
	 * find the minimum value from the specified loop It then swaps the values
	 * together.
	 */

	public void newSort() {
		int pos = 0; // start from position from 0
		/*
		 * loops until position is less than length of array. To allow it to loop to the
		 * end of the array.
		 */
		while (pos < getUsedSize()) { // n2
			int min = findMinFrom(pos); // Find the minimum from the specified position up to the end
			/*
			 * Finding the minimum in i and puts it on the correct position in array
			 */
			for (int i = pos; i < getUsedSize(); i++) { // Each loop checks if our value i, is the same as minimum
				compNS += 1; // A comparison is perfumed at each loop
				if (getArray()[i] == min) { // checks if i is the same as minimum
					swap(pos, i); // puts minimum into the correct place
					pos += 1; // go to the next position
				}
			}
		}
	}

} /** End of Sort Class **/
