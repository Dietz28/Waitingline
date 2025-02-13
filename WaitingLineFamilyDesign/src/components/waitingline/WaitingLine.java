package components.waitingline;

import java.util.Comparator;

/**
 * {@code WaitingLineKernel} enhanced with secondary methods.
 *
 * @param <T>
 *            type of {@code WaitingLine} entries
 * @mathdefinitions <pre>
 * IS_TOTAL_PREORDER (
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y, z: T
 *   ((r(x, y) or r(y, x))  and
 *    (if (r(x, y) and r(y, z)) then r(x, z)))
 *
 * IS_SORTED (
 *   s: string of T,
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y: T where (<x, y> is substring of s) (r(x, y))
 * </pre>
 */
public interface WaitingLine<T> extends WaitingLineKernel<T> {

    /**
     * Reports the front of {@code this}.
     *
     * @return the front entry of {@code this}
     * @aliases reference returned by {@code front}
     * @requires this /= <>
     * @ensures <front> is prefix of this
     */
    T front();

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
    T replaceFront(T x);

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
    void append(WaitingLine<T> q);

    /**
     * Reverses ("flips") {@code this}.
     *
     * @updates this
     * @ensures this = rev(#this)
     */
    void flip();

    /**
     * Sorts {@code this} according to the ordering provided by the
     * {@code compare} method from {@code order}.
     *
     * @param order
     *            ordering by which to sort
     * @updates this
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * perms(this, #this)  and
     * IS_SORTED(this, [relation computed by order.compare method])
     * </pre>
     */
    void sort(Comparator<T> order);

    /**
     * Rotates {@code this}.
     *
     * @param distance
     *            distance by which to rotate
     * @updates this
     * @ensures <pre>
     * if #this = <> then
     *  this = #this
     * else
     *  this = #this[distance mod |#this|, |#this|) * #this[0, distance mod |#this|)
     * </pre>
     */
    void rotate(int distance);

    /**
     * Removes the pair whose first component is {@code key} and returns it.
     *
     * @param value
     *            the value to be removed
     * @updates this
     * @requires value is in DOMAIN(this)
     * @ensures <pre>
     * remove is in #this  and
     * this = #this \ {remove}
     * </pre>
     */
    void remove(T value);

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
    int position(T entry);

}
