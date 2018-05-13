package org.smartboot.socket.transport;

import java.nio.ByteBuffer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

final class ArrayBlockingQueue {

    /**
     * The queued items
     */
    final ByteBuffer[] items;
    /**
     * Main lock guarding all access
     */
    final ReentrantLock lock = new ReentrantLock(false);
    ;
    /**
     * Condition for waiting takes
     */
    private final Condition notEmpty = lock.newCondition();
    /**
     * Condition for waiting puts
     */
    private final Condition notFull = lock.newCondition();

    /*
     * Concurrency control uses the classic two-condition algorithm
     * found in any textbook.
     */
    /**
     * items index for next take, poll, peek or remove
     */
    int takeIndex;
    /**
     * items index for next put, offer, or add
     */
    int putIndex;
    /**
     * Number of elements in the queue
     */
    int count;

    int remaining;

    /**
     * Creates an {@code ArrayBlockingQueue} with the given (fixed)
     * capacity and default access policy.
     *
     * @param capacity the capacity of this queue
     * @throws IllegalArgumentException if {@code capacity < 1}
     */
    public ArrayBlockingQueue(int capacity) {
        this.items = new ByteBuffer[capacity];
    }

    /**
     * Inserts element at current put position, advances, and signals.
     * Call only when holding lock.
     */
    private void enqueue(ByteBuffer x) {
        // assert lock.getHoldCount() == 1;
        // assert items[putIndex] == null;
        items[putIndex] = x;
        if (++putIndex == items.length)
            putIndex = 0;
        count++;
        remaining += x.remaining();
        notEmpty.signal();
    }

    /**
     * Extracts element at current take position, advances, and signals.
     * Call only when holding lock.
     */
    private ByteBuffer dequeue() {
        // assert lock.getHoldCount() == 1;
        // assert items[takeIndex] != null;
        ByteBuffer x = items[takeIndex];
        items[takeIndex] = null;
        if (++takeIndex == items.length)
            takeIndex = 0;
        count--;
        remaining -= x.remaining();
        notFull.signal();
        return x;
    }

    public int expectRemaining(int maxSize) {
        lock.lock();
        try {
            if (remaining <= maxSize || count == 1) {
                return remaining;
            }

            int takeIndex = this.takeIndex;
            int preCount = 0;
            int remain = items[takeIndex].remaining();
            do {
                if (++takeIndex == items.length) {
                    takeIndex = 0;
                }
                remain += (preCount = items[takeIndex].remaining());
            } while (remain <= maxSize);
            return remain - preCount;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Inserts the specified element at the tail of this queue, waiting
     * for space to become available if the queue is full.
     *
     * @throws InterruptedException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public void put(ByteBuffer e) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (count == items.length)
                notFull.await();
            enqueue(e);
        } finally {
            lock.unlock();
        }
    }

    public ByteBuffer poll() {
        lock.lock();
        try {
            return (count == 0) ? null : dequeue();
        } finally {
            lock.unlock();
        }
    }

    public void pollInto(ByteBuffer destBuffer) {
        lock.lock();
        try {
            while (destBuffer.hasRemaining()) {
                destBuffer.put(dequeue());
            }
        } finally {
            lock.unlock();
        }
    }
    // this doc comment is overridden to remove the reference to collections
    // greater in size than Integer.MAX_VALUE

    /**
     * Returns the number of elements in this queue.
     *
     * @return the number of elements in this queue
     */
    public int size() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }
}