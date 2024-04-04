package components.waitingline;

import components.standard.Standard;

/**
 * First-in-first-out (FIFO) waitingLine kernel component with primary methods.
 * (Note: by package-wide convention, all references are non-null.)
 *
 * @param <T>
 *            type of {@code WaitingLineKernel} entries
 * @mathmodel type WaitingLineKernel is modeled by string of T
 * @initially <pre>
 * default:
 *  ensures
 *   this = <>
 * </pre>
 * @iterator ~this.seen * ~this.unseen = this
 */
public interface WaitingLineKernel<T>
        extends Standard<WaitingLine<T>>, Iterable<T> {

    /**
     * Adds {@code x} to the end of {@code this}.
     *
     * @param x
     *            the entry to be added
     * @aliases reference {@code x}
     * @updates this
     * @requires x is not in this
     * @ensures this = #this * <x>
     */
    void enqueue(T x);

    /**
     * Removes and returns the entry at the front of {@code this}.
     *
     * @return the entry removed
     * @updates this
     * @requires this /= <>
     * @ensures #this = <dequeue> * this
     */
    T dequeue();

    /**
     * Reports length of {@code this}.
     *
     * @return the length of {@code this}
     * @ensures length = |this|
     */
    int length();

    /**
     * Reports whether {@code x} is in {@code this}.
     *
     * @param x
     *            the element to be checked
     * @return true iff element is in {@code this}
     * @ensures contains = (x is in this)
     */
    boolean contains(T x);
}
