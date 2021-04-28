/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.util.List;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.SwingWorker;

public class Flipper extends JFrame implements ActionListener {

    private final GridBagConstraints constraints;
    private final JTextField headsText, totalText, devText;
    private final Border border = BorderFactory.createLoweredBevelBorder();
    private final JButton startButton, stopButton;
    private FlipTask flipTask;

    // Display text labels and fields
    private JTextField makeText() {
        JTextField t = new JTextField(20);
        t.setEditable(false);
        t.setHorizontalAlignment(JTextField.RIGHT);
        t.setBorder(border);
        getContentPane().add(t, constraints);
        return t;
    }

    // Draw button on grid
    private JButton makeButton(String caption) {
        JButton b = new JButton(caption);
        b.setActionCommand(caption);
        b.addActionListener(this);
        getContentPane().add(b, constraints);
        return b;
    }

    public Flipper() {
        super("Flipper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Make text boxes
        getContentPane().setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        constraints.insets = new Insets(3, 10, 3, 10);

        // Create text fields
        headsText = makeText();
        totalText = makeText();
        devText = makeText();

        // Make buttons
        // At one time, only 1 button is active
        startButton = makeButton("Start");
        stopButton = makeButton("Stop");
        stopButton.setEnabled(false);

        // Display the window.
        pack();
        setVisible(true);
    }

    /*
     * This program tests the fairness of java.util.Random by generating a series of
     * random boolean values in a background task. This is equivalent to flipping a
     * coin; hence the name Flipper. To report its results, the background task uses
     * an object of type FlipPair
     */
    private static class FlipPair {

        private final long heads, total;

        FlipPair(long heads, long total) {
            this.heads = heads;
            this.total = total;
        }
    }

    /*
     * The background task. The task invokes publish after each "coin flip".
     * SwingWorker = Abstract generic class; must override methods.
     */
    private class FlipTask extends SwingWorker<Void, FlipPair> {

        @Override
        // Void = wrapper class
        protected Void doInBackground() {
            long heads = 0;
            long total = 0;
            Random random = new Random();
            while (!isCancelled()) {
                total++;
                if (random.nextBoolean()) {
                    heads++;
                }
                // Publish method = display
                publish(new FlipPair(heads, total));
            }
            return null;
        }

        /*
         * Because publish is invoked very frequently, a lot of FlipPair values will
         * probably be accumulated before process is invoked in the event dispatch
         * thread. process is only interested in the last value reported each time,
         * using it to update the GUI
         */
        @Override
        protected void process(List<FlipPair> pairs) {
            FlipPair pair = pairs.get(pairs.size() - 1);
            headsText.setText(String.format("%d", pair.heads));
            totalText.setText(String.format("%d", pair.total));
            // Output should be getting closer to 0
            devText.setText(String.format("%.10g", ((double) pair.heads) / ((double) pair.total) - 0.5));
        }
    }

    /*
     * To cancel a running background task, invoke SwingWorker.cancel. The task must
     * cooperate with its own cancellation. There are two ways it can do this: 1) By
     * terminating when it receives an interrupt. 2) By invoking
     * SwingWorker.isCancelled at short intervals. This method returns true if
     * cancel has been invoked for this SwingWorker.
     */
    public void actionPerformed(ActionEvent e) {
        // getActionCommand() = receive click signal from user
        if ("Start" == e.getActionCommand()) {
            // Must enable and disable buttons respectively
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            // Start FlipTask
            (flipTask = new FlipTask()).execute();
        } else if ("Stop" == e.getActionCommand()) {
            // Must enable and disable buttons respectively
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            // Stop FlipTask using cancel(true)
            flipTask.cancel(true);
            flipTask = null;
        }

    }

    public static void main(String[] args) {
        // Use InvokeLater to pass Runnable instance
        // Put into queue, only when event dispatch is ready then run
        SwingUtilities.invokeLater(new Runnable() { // initial thread
            public void run() {
                new Flipper();
            }
        });
    }
}
