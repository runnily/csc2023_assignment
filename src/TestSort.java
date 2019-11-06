package main;

/*************************************************/
/***  Simple test class for Sort class         ***/
/***                                           ***/
/***  Author: Jason Steggles    20/09/2019     ***/
/*** Extended by: Adanna Obibuaku 19/10/19 	   ***/
/*************************************************/

public class TestSort {
	static final int MAX_TESTS = 6;

	/*
	 * A test method would take a file and apply the insertions sort, quick sort and
	 * new sort to return the number of comparisons for each sort depending on data
	 */
	public String tests(String file, String testName, int fileSize) {
		Sort test = new Sort(fileSize); // Create a new sort with the indicated file size
		// ** Test data on insertion **//
		test.readIn(file); // Read in the name of the file
		test.insertion(); // First use insertion to sort the file

		// **Test data on quick sort**//
		test.readIn(file); // Read in the file again, to clear the sorted data
		test.quickSort(0, test.getUsedSize() - 1); // Apply the quick sort algorithm

		// **Test data on new sort**//
		test.readIn(file); // Again read in file to override the data
		test.newSort(); // Perform the new sorts algorithm

		// Display the test of array and the number of comparisons for each sort

		String displayTest = ("-----------------" + testName + "--------------------\n"); // creates table equivalent to
																							// test name
		displayTest += String.format("|%1$-20s|%2$-20s|\n", "Sort Type", "No. of comparisons");
		displayTest += String.format("|%1$-20s|%2$-20s|\n", "Insertion Sort", test.compIS);
		displayTest += String.format("|%1$-20s|%2$-20s|\n", "Quick Sort", test.compQS);
		displayTest += String.format("|%1$-20s|%2$-20s|\n", "New Sort", test.compNS);
		displayTest += "-------------------------------------------";

		return displayTest;
	}

	public static void main(String[] args) {
		Sort sortTest = new Sort(50);

		/**
		 * Below are test. These test each sort To check if all of them are working and
		 * sorting the elements.
		 */

		/** Test the insertion sort **/
		sortTest.readIn("test1.txt");
		System.out.print("**Insertion Sort***");
		sortTest.display(5, "---Unsorted data---");
		sortTest.insertion();
		sortTest.display(5, "----Sorted Data----");
		System.out.println("\n*******************\n");

		/** Test quick sort **/
		sortTest.readIn("test1.txt");
		System.out.print("****Quick Sort*****");
		sortTest.display(5, "---Unsorted data---");
		sortTest.quickSort(0, sortTest.getUsedSize() - 1);
		sortTest.display(5, "----Sorted Data----");
		System.out.println("\n*******************\n");

		/** Test new sort **/
		sortTest.readIn("test1.txt");
		System.out.print("*****New Sort******");
		sortTest.display(5, "---Unsorted data---");
		sortTest.newSort();
		sortTest.display(5, "----Sorted Data----");
		System.out.println("\n*******************\n");

		/* This would test the number of comparison for each sorting algorithm */
		/*
		 * We will be testing the files: test1, test2, test3, test 4, test 5 and test 6
		 * which have the number of array size 1000 (not actual used size)
		 */
		for (int i = 1; i <= MAX_TESTS; i++) {
			TestSort test = new TestSort();
			System.out.println(test.tests("test" + i + ".txt", "Test" + i, 1000));
		}

	}
} /** End of TestSort class **/
