package components.waitingline;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Layered implementations of secondary methods for {@code Queue}.
 *
 * <p>
 * Assuming execution-time performance of O(1) for method {@code iterator} and
 * its return value's method {@code next}, execution-time performance of
 * {@code front} as implemented in this class is O(1). Execution-time
 * performance of {@code replaceFront} and {@code flip} as implemented in this
 * class is O(|{@code this}|). Execution-time performance of {@code append} as
 * implemented in this class is O(|{@code q}|). Execution-time performance of
 * {@code sort} as implemented in this class is O(|{@code this}| log
 * |{@code this}|) expected, O(|{@code this}|^2) worst case. Execution-time
 * performance of {@code rotate} as implemented in this class is
 * O({@code distance} mod |{@code this}|).
 *
 * @param <T>
 *            type of {@code Queue} entries
 */
public abstract class WaitingLineSecondary<T> implements WaitingLine<T> {

    /*
     * Common methods (from Object) -------------------------------------------
     */

    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof WaitingLine<?>)) {
            return false;
        }
        WaitingLine<?> q = (WaitingLine<?>) obj;
        if (this.length() != q.length()) {
            return false;
        }
        Iterator<T> it1 = this.iterator();
        Iterator<?> it2 = q.iterator();
        while (it1.hasNext()) {
            T x1 = it1.next();
            Object x2 = it2.next();
            if (!x1.equals(x2)) {
                return false;
            }
        }
        return true;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public int hashCode() {
        final int samples = 2;
        final int a = 37;
        final int b = 17;
        int result = 0;
        /*
         * This code makes hashCode run in O(1) time. It works because of the
         * iterator order string specification, which guarantees that the (at
         * most) samples entries returned by the it.next() calls are the same
         * when the two Queues are equal.
         */
        int n = 0;
        Iterator<T> it = this.iterator();
        while (n < samples && it.hasNext()) {
            n++;
            T x = it.next();
            result = a * result + b * x.hashCode();
        }
        return result;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("<");
        Iterator<T> it = this.iterator();
        while (it.hasNext()) {
            result.append(it.next());
            if (it.hasNext()) {
                result.append(",");
            }
        }
        result.append(">");
        return result.toString();
    }

    /**
     * Reports the front of {@code this}.
     *
     * @return the front entry of {@code this}
     * @aliases reference returned by {@code front}
     * @requires this /= <>
     * @ensures <front> is prefix of this
     */
    @Override
    public T front() {
        T x = this.dequeue();
        T y = x;
        int length = this.length();
        this.enqueue(x);
        for (int i = 0; i < length; i++) {
            this.enqueue(this.dequeue());
        }
        return y;
    }

    /**
     * Replaces the front of {@code this} with {@code x}, and returns the old
     * front.
     *
     * @param x
     *            the new front entry
     * @return the old front entry
     * @aliases reference {@code x}
     * @updates this
     * @requires this /= <>
     * @ensures <pre>
     * <replaceFront> is prefix of #this  and
     * this = <x> * #this[1, |#this|)
     * </pre>
     */
    @Override
    public T replaceFront(T x) {

        T front = this.dequeue();
        this.enqueue(x);
        while (!this.front().equals(x)) {
            this.enqueue(this.dequeue());
        }
        return front;
    }

    /**
     * Concatenates ("appends") {@code q} to the end of {@code this}.
     *
     * @param q
     *            the {@code WaitingLine} to be appended to the end of
     *            {@code this}
     * @updates this
     * @clears q
     * @ensures this = #this * #q
     */

    @Override
    public void append(WaitingLine<T> q) {
        while (q.length() != 0) {
            this.enqueue(q.dequeue());
        }
    }

    /**
     * Rotates {@code this}.
     *
     * @param distance
     *            distance by which to rotate
     * @return
     * @updates this
     * @ensures <pre>
     * if #this = <> then
     *  this = #this
     * else
     *  this = #this[distance mod |#this|, |#this|) * #this[0, distance mod |#this|)
     * </pre>
     */
    @Override
    public void remove(T value) {
        int pos = this.position(value);
        int length = this.length();
        for (int i = 0; i < pos; i++) {
            T x = this.dequeue();
            this.enqueue(x);
        }
        this.dequeue();
        for (int i = 0; i < (length - pos) - 1; i++) {
            this.enqueue(this.dequeue());
        }
    }

    /**
     * Find the position of the {@code entry} in {@code this}
     *
     * @param entry
     *            the entry being looked for
     * @return the position of the {@code entry} in {@code this}
     * @requires <pre>
     * {@code position = position of entry in this}
     * </pre>
     */
    @Override
    public int position(T entry) {
        int length = this.length();
        int position = 0;
        for (int i = 0; i < length; i++) {
            if (this.front().equals(entry)) {
                position = i;
                i = length;
            }
        }
        return position;
    }

    @Override
    public void sort(Comparator<T> order) {
        WaitingLine<T> temp = this.newInstance();
        temp.transferFrom(this);
        if (temp.length() > 1) {
            temp.sort(order);
        }

    }

}