import java.util.NoSuchElementException;

class Node {
    String data;
    Node next;

    public Node(String d) {
        data = d;
        next = null;
    }
}

public class LLQueue {
    private Node head = null;
    private Node tail = null;
    private int listSize = 0;

    public void enqueList(String data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }

        listSize++;
        // System.out.println(tail.data + " ");
    }

    public String dequeList() {
        if (head == null) {
            // throw new NoSuchElementException("List is empty");
            return null;
        }

        String temp = head.data;
        head = head.next;

        if (head == null) {
            tail = null;
        }

        listSize--;
        return temp;
    }

    public boolean isSorted() {
        return true;
    }

    public String peak() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }

        String peaking = head.data;
        return peaking;
    }

    public String tail() {
        String end = tail.data;
        return end;
    }

    public boolean isEmpty() {
        return listSize == 0;
    }

    public void isEmptyString(String data) {
        if (data.isEmpty() && data == "") {
            System.out.println(data);
            System.exit(0);
        }
    }

    public void isOneWord(String data) {
        if (listSize == 1) {
            System.out.print(data);
            System.exit(0);
        }
    }

    public void printList() {
        Node curr = head;
        while (curr != null) {
            System.out.println(curr.data + " ");
            curr = curr.next;
        }
    }
}
