import java.awt.desktop.AppReopenedEvent;
import java.util.List;
import java.util.ArrayList; // import the ArrayList class


public class LinkedListDeque<T> implements Deque<T> {

    private class Node {
        public Node prev;
        public Node next;
        public T item;
        public Node(T item, Node prev, Node next){
            this.prev = prev;
            this.next = next;
            this.item = item;
        }
    }
    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        Node sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        this.sentinel = sentinel;
        this.size = 0;
    }

    @Override
    public void addFirst(T x) {
        sentinel.next = new Node(x, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        this.size += 1;
    }

    @Override
    public void addLast(T x) {
        sentinel.prev = new Node(x, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        this.size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node S = sentinel.next;
        for (int i = 0; i < this.size; i++){
            returnList.add(S.item);
            S = S.next;
        }

        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return this.sentinel.prev == null && this.sentinel.next == null;

    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T removeFirst() {
        Node S1 = this.sentinel;
        Node S2 = S1.next;
        if (S1.next.item != null) {
            S1.next = S1.next.next;
            S1.next.prev = S1;
            this.size -= 1;
            return S2.item;
        }
        return null;
    }


    @Override
    public T removeLast() {
        Node S1 = this.sentinel;
        Node S2 = S1.prev;
        if (S1.prev.item != null){
            S1.prev = S1.prev.prev;
            S1.prev.next = S1;
            this.size -= 1;
            return S2.item;
        }

        return null;

    }

    @Override
    public T get(int index) {
        Node S = this.sentinel;
        for (int i = 0; i < this.size; i++) {
            if (i == index) {
                return S.item;
            }
            S = S.next;
        }
        return null;
    }

    @Override
    public T getRecursive(int index) {
        Node S = this.sentinel;
        if (index <= 0)
            return null;
        else {
            return Recurisve(index, S, this.size, 0);
        }
    }
    public T Recurisve(int i, Node S, int size, int counter){
        if (counter == i){
            return S.item;
        }
        else {
            return Recurisve(i,S.next,size,counter+1);
        }
    }
}