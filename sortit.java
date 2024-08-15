import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class sortit {

  static int compareCount = 0;

  // **WIP**
  // I'll want you to use a special `compareTo()` method in your code.
  // The method will increment a "global" variable to keep track of the number of
  // compares (a la PA01/SortAnal),
  // and then invoke and return whatever `String.compareTo()` returns. You'll
  // output (to System.err) the final count when you're done sorting.
  // I think there's a more elegant way to do this, but I don't have the time now
  // to figure this out.
  // (If you want to help with developing a more elegant solution; let me know. --
  // RI)
  static int siCompareTo(String str1, String str2) {
    compareCount++;
    return str1.compareTo(str2);
  }

  static LLQueue list0 = new LLQueue();
  static LLQueue list1 = new LLQueue();
  static LLQueue currList = list0;

  public static LLQueue switchList() {
    if (currList == list0) {
      return list1;
    } else {
      return list0;
    }
  }

  public static void main(String[] args) throws FileNotFoundException {
    try (Scanner sd = new Scanner(System.in)) {
      String currentLine = "";
      String prevLine = "";

      for (int i = 0; i < args.length; i++) {

        // If a hypen is typed, get user input (standard input)
        if (args[i].equals("-")) {
          String userInput = "";
          while (sd.hasNextLine()) {
            userInput = sd.nextLine();

            if (userInput.equals("")) {
              break;
            }
            currList.enqueList(userInput.toLowerCase());
          }
        } else {

          File fileName = new File(args[i]);

          if (fileName.exists()) {
            Scanner scan = new Scanner(fileName);
            while (scan.hasNextLine()) {

              // Update currentLine with current line
              currentLine = scan.nextLine();

              if (currentLine.compareTo(prevLine) >= 0) {
                currList.enqueList(currentLine.toLowerCase());
                compareCount++;
              } else {
                currList = switchList();
                currList.enqueList(currentLine.toLowerCase());
                compareCount++;
              }

              prevLine = currentLine; // Update prevLine with the old nums1 line
            }

            // Close scanner (file)
            scan.close();

          } else {
            System.err.print(fileName + " cant be opened");
            System.out.println();
            continue;
          }
        }
      }

      // Check is empty string
      currList.isEmptyString(currentLine);
      // Check in case of one word
      currList.isOneWord(currentLine);

      // Enque special null value
      String s = "'";
      list0.enqueList(s);
      list1.enqueList(s);

      String prev0 = list0.peak();
      String prev1 = list1.peak();

      int list0compareTolist1 = siCompareTo(list0.peak(), list1.peak());
      int list1compareTolist0 = siCompareTo(list1.peak(), list0.peak());
      int list0compareToTail = siCompareTo(list0.peak(), currList.tail());
      int list1comapreToTail = siCompareTo(list1.peak(), currList.tail());

      // STARTING case: Compare both heads swap for smaller onto currList
      if (list0.peak().compareTo(list1.peak()) < 0) {
        list0.dequeList();
        currList.enqueList(prev0);
        prev0 = list0.peak();
      } else {
        list1.dequeList();
        currList.enqueList(prev1);
        prev1 = list1.peak();
      }

      // Compare heads of list 0 and list1, enque, deque
      while (list0.peak() != null && list1.peak() != null) {

        // Update compare values
        list0compareTolist1 = siCompareTo(list0.peak(), list1.peak());
        list1compareTolist0 = siCompareTo(list1.peak(), list0.peak());
        list0compareToTail = siCompareTo(list0.peak(), currList.tail());
        list1comapreToTail = siCompareTo(list1.peak(), currList.tail());

        // Case 1: Both heads GREATER swap smaller
        if (list0compareToTail >= 0 && list1comapreToTail >= 0) {
          if (list0compareTolist1 < 0) {
            list0.dequeList();

            currList.enqueList(prev0);
            // Updating prev0 as the head
            prev0 = list0.peak();

          } else {
            list1.dequeList();

            currList.enqueList(prev1);
            prev1 = list1.peak();

          }

        }

        // Case 2: if One greater than or equal, One smaller than or equal choose larger
        else if (list0compareTolist1 >= 0 && list1compareTolist0 <= 0 || list0compareTolist1 <= 0
            && list1compareTolist0 >= 0) {
          if (list0compareTolist1 >= 0) {
            list0.dequeList();

            currList.enqueList(prev0);
            prev0 = list0.peak();

          } else {
            list1.dequeList();

            currList.enqueList(prev1);
            prev1 = list1.peak();

          }

        }

        // Case 3: Both smaller change list, swap smaller
        if (list0compareToTail < 0
            && list1comapreToTail < 0) {
          // Switch List
          currList = switchList();
        }

        // If one of the list hit the special marker, enqueue and dequeue from one list
        if (list0.peak() == s) {
          while (list1.peak() != s) {
            if (list1.peak().compareTo(currList.tail()) >= 0) {
              list1.dequeList();
              currList.enqueList(prev1);
              prev1 = list1.peak();
              compareCount++;
            } else {
              currList = switchList();
              list1.dequeList();
              currList.enqueList(prev1);
              prev1 = list1.peak();
              compareCount++;
            }
          }
        }

        if (list1.peak() == s) {
          while (list0.peak() != s) {
            if (list0.peak().compareTo(currList.tail()) >= 0) {
              list0.dequeList();
              currList.enqueList(prev0);
              prev0 = list0.peak();
              compareCount++;
            } else {
              currList = switchList();
              list0.dequeList();
              currList.enqueList(prev0);
              prev0 = list0.peak();
              compareCount++;
            }
          }
        }

        // If all elements on original list has been processed, first pass is done
        if (list0.peak() == s && list1.peak() == s) {
          list0.dequeList(); // dequeue special null value
          list1.dequeList();

          // Print list
          if (list0.isEmpty()) {
            list1.printList();
            System.err.print(compareCount + " comparisons");
            return;
          }

          if (list1.isEmpty()) {
            list0.printList();
            System.err.print(compareCount + " comparisons");
            return;
          }
          list0.enqueList(s); // enqueue special null value to end of list
          list1.enqueList(s);
          prev0 = list0.peak();
          prev1 = list1.peak();
        }
      }
    }
  }
}
