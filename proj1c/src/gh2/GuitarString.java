package gh2;

import deque.ArrayDeque;
import deque.Deque;
//Note: This file will not compile until you complete the Deque implementations
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        buffer = new ArrayDeque<>();
        for (int i = 0; i < SR / Math.round(frequency); i++) {
            buffer.addLast(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        Deque<Double> newbuffer = new ArrayDeque<>();
        for (int i = 0; i < buffer.size(); i++) {
            double r = Math.random() - 0.5;
            newbuffer.addLast(r);
        }
        buffer = newbuffer;
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double first = buffer.get(0);
        double second = buffer.get(1);
        buffer.removeFirst();
        double offset = 0.996;
        buffer.addLast(offset * 0.5 * (first + second));
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }
}
