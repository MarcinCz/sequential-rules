package pl.mczpk.med.sr.util;

import java.util.LinkedList;

/**
 * FIFO queue with fixed size limit. If new element is added to the full queue then first element is removed.
 */

@SuppressWarnings("serial")
public class LimitedQueue<E> extends LinkedList<E> {
	private int limit;

    public LimitedQueue(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean add(E o) {
        super.add(o);
        while (size() > limit) { super.remove(); }
        return true;
    }
}
