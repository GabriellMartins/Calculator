package dev.github.gabrielmartins.calculator;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ScientificCalculator().show());
    }
}
