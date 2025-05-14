package dev.github.gabrielmartins.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ScientificCalculator {

    private final JFrame frame = new JFrame();
    private final JTextField display = new JTextField();
    private Point initialClick;

    public ScientificCalculator() {
        frame.setUndecorated(true);
        frame.setSize(420, 620);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.BLACK);

        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen() - initialClick.x;
                int y = e.getYOnScreen() - initialClick.y;
                frame.setLocation(x, y);
            }
        });

        display.setFont(new Font("Consolas", Font.BOLD, 32));
        display.setBackground(Color.BLACK);
        display.setForeground(Color.WHITE);
        display.setCaretColor(Color.WHITE);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(display, BorderLayout.NORTH);

        var buttonPanel = new JPanel(new GridLayout(7, 4, 5, 5));
        buttonPanel.setBackground(Color.BLACK);

        String[] buttons = {
                "C", "⌫", "(", ")",
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "sin", "cos", "tan", "log",
                "√", "^", "📈", "✕"
        };

        for (String label : buttons) {
            var btn = new JButton(label);
            styleButton(btn, label.equals("="));
            btn.addActionListener(e -> handleInput(label));
            buttonPanel.add(btn);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
    }

    private void handleInput(String label) {
        switch (label) {
            case "=":
                try {
                    var input = display.getText()
                            .replace("√", "Math.sqrt")
                            .replace("sin", "Math.sin")
                            .replace("cos", "Math.cos")
                            .replace("tan", "Math.tan")
                            .replace("log", "Math.log")
                            .replace("^", "**");

                    var engine = new ScriptEngineManager().getEngineByName("JavaScript");
                    var result = engine.eval(input.replace("**", "^"));
                    display.setText(result.toString());
                } catch (Exception e) {
                    display.setText("Erro");
                }
                break;
            case "C":
                display.setText("");
                break;
            case "⌫":
                String current = display.getText();
                if (!current.isEmpty())
                    display.setText(current.substring(0, current.length() - 1));
                break;
            case "📈":
                new FunctionPlotter().show();
                break;
            case "✕":
                System.exit(0);
                break;
            default:
                display.setText(display.getText() + label);
                break;
        }
    }

    private void styleButton(JButton button, boolean isEqualButton) {
        button.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(isEqualButton ? new Color(0x005f9e) : new Color(0x1e1e1e));
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(isEqualButton ? new Color(0x008cff) : new Color(40, 40, 40));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(isEqualButton ? new Color(0x005f9e) : new Color(0x1e1e1e));
            }
        });
    }

    public void show() {
        frame.setVisible(true);
    }
}
