package slimes.util;

/**
 * A tuple data structure that holds two different values of different types.
 *
 * @param <A> the data type of the first object.
 * @param <B> the data type of the second object.
 *
 * @author Rene
 */

public class Tuple<A, B>
{
    private A firstValue;
    private B secondValue;

    public Tuple(A a, B b) {
        firstValue = a;
        secondValue = b;
    }

    /**
     * this gets the data stored in the tuple.
     *
     * @param index the index of the object you want to get (0 or 1).
     * @return the object in the tuple from the given index.
     */
    public Object get(int index) {
        switch (index) {
            case 0:
                return firstValue;
            case 1:
                return  secondValue;
            default:
                throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for tuple");
        }
    }

}
