package dev.github.gabrielmartins.calculator;

import javax.swing.*;
import java.awt.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.*;
import javax.script.ScriptEngineManager;

public class FunctionPlotter {
    private final JFrame frame = new JFrame();

    public FunctionPlotter() {
        frame.setUndecorated(true);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.BLACK);

        var inputField = new JTextField("Math.sin(x)");
        inputField.setFont(new Font("Consolas", Font.PLAIN, 18));
        inputField.setBackground(Color.BLACK);
        inputField.setForeground(Color.WHITE);
        inputField.setCaretColor(Color.WHITE);

        var plotButton = new JButton("Plotar");
        styleButton(plotButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        topPanel.add(new JLabel(" f(x) = ", JLabel.LEFT), BorderLayout.WEST);
        topPanel.add(inputField, BorderLayout.CENTER);
        topPanel.add(plotButton, BorderLayout.EAST);

        frame.add(topPanel, BorderLayout.NORTH);

        var chartPanel = new JPanel(new BorderLayout());
        chartPanel.setBackground(Color.BLACK);
        frame.add(chartPanel, BorderLayout.CENTER);

        plotButton.addActionListener(e -> {
            try {
                var series = new XYSeries("f(x)");
                var expr = inputField.getText();

                for (double x = -10; x <= 10; x += 0.1) {
                    double y = eval(expr, x);
                    series.add(x, y);
                }

                var dataset = new XYSeriesCollection(series);
                var chart = ChartFactory.createXYLineChart(
                        "Gráfico de f(x)", "x", "f(x)",
                        dataset, PlotOrientation.VERTICAL, false, true, false);

                chart.setBackgroundPaint(Color.BLACK);
                chart.getXYPlot().setBackgroundPaint(Color.DARK_GRAY);

                chartPanel.removeAll();
                chartPanel.add(new ChartPanel(chart));
                chartPanel.validate();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage());
            }
        });
    }

    private double eval(String expr, double x) throws Exception {
        var expression = expr.replace("x", "(" + x + ")");
        return ((Number) new ScriptEngineManager().getEngineByName("JavaScript").eval(expression)).doubleValue();
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0x005f9e));
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0x008cff));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0x005f9e));
            }
        });
    }

    public void show() {
        frame.setVisible(true);
    }
}
