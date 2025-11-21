package ru.vsu.cs.kiselev.kg2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class InteractiveEllipseEditor extends JFrame {
    private List<EllipseDrawer> ellipses = new ArrayList<>();
    private EllipseDrawer currentEllipse;
    private ControlPoint selectedControlPoint = null;
    private Point lastMousePoint = null;

    private JPanel controlPanel;
    private JTextField centerXField, centerYField, aField, bField;
    private JCheckBox showControlPointsCheck;
    private JLabel statusLabel;

    public InteractiveEllipseEditor() {
        setTitle("Ellipse");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        initializeUI();
        createSampleEllipses();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        controlPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Ellipse Parameters"));

        JPanel centerPanel = new JPanel(new FlowLayout());
        centerPanel.add(new JLabel("Center X:"));
        centerXField = new JTextField(5);
        centerPanel.add(centerXField);

        centerPanel.add(new JLabel("Y:"));
        centerYField = new JTextField(5);
        centerPanel.add(centerYField);

        centerPanel.add(new JLabel("a:"));
        aField = new JTextField(5);
        centerPanel.add(aField);

        centerPanel.add(new JLabel("b:"));
        bField = new JTextField(5);
        centerPanel.add(bField);

        JButton createCenterBtn = new JButton("Create");
        createCenterBtn.addActionListener(e -> createEllipseFromCenter());
        centerPanel.add(createCenterBtn);


        JPanel controlSettingsPanel = new JPanel(new FlowLayout());
        showControlPointsCheck = new JCheckBox("Show Control Points", true);
        controlSettingsPanel.add(showControlPointsCheck);

        JButton clearBtn = new JButton("Clear All");
        clearBtn.addActionListener(e -> clearAll());
        controlSettingsPanel.add(clearBtn);

        statusLabel = new JLabel("Click and drag control points to modify ellipses");
        controlSettingsPanel.add(statusLabel);

        controlPanel.add(centerPanel);
        controlPanel.add(controlSettingsPanel);

        add(controlPanel, BorderLayout.NORTH);

        JPanel drawingPanel = new DrawingPanel();
        drawingPanel.setBackground(Color.WHITE);
        add(drawingPanel, BorderLayout.CENTER);

        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point mousePoint = new Point(e.getX(), e.getY());

                for (EllipseDrawer ellipse : ellipses) {
                    ellipse.setShowControlPoints(true);
                    ControlPoint cp = ellipse.getControlPointAt(mousePoint);
                    if (cp != null) {
                        selectedControlPoint = cp;
                        currentEllipse = ellipse;
                        lastMousePoint = mousePoint;
                        updateStatus();
                        repaint();
                        return;
                    }
                }
                createEllipseAt(mousePoint);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedControlPoint = null;
                lastMousePoint = null;
            }
        });

        drawingPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedControlPoint != null && lastMousePoint != null) {
                    Point currentPoint = new Point(e.getX(), e.getY());
                    currentEllipse.moveControlPoint(selectedControlPoint, currentPoint);
                    lastMousePoint = currentPoint;
                    updateStatus();
                    repaint();
                }
            }
        });
    }

    private void createEllipseFromCenter() {
        try {
            double cx = Double.parseDouble(centerXField.getText());
            double cy = Double.parseDouble(centerYField.getText());
            double a = Double.parseDouble(aField.getText());
            double b = Double.parseDouble(bField.getText());

            EllipseDrawer ellipse = new EllipseDrawer();
            ellipse.setEllipse(new Point(cx, cy), a, b);
            ellipse.setShowControlPoints(true);
            ellipse.setColor(getRandomColor());
            ellipses.add(ellipse);
            currentEllipse = ellipse;
            updateStatus();
            repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers");
        }
    }

    private void createEllipseAt(Point center) {
        EllipseDrawer ellipse = new EllipseDrawer();
        ellipse.setEllipse(center, 50, 30);
        ellipse.setShowControlPoints(true);
        ellipse.setColor(getRandomColor());
        ellipses.add(ellipse);
        currentEllipse = ellipse;
        updateStatus();
        repaint();
    }

    private void clearAll() {
        ellipses.clear();
        currentEllipse = null;
        updateStatus();
        repaint();
    }

    private void createSampleEllipses() {
        EllipseDrawer ellipse1 = new EllipseDrawer();
        ellipse1.setEllipse(new Point(200, 150), 80, 40);
        ellipse1.setColor(Color.BLUE);
        ellipse1.setShowControlPoints(true);
        ellipses.add(ellipse1);

        EllipseDrawer ellipse2 = new EllipseDrawer();
        ellipse2.setEllipse(400, 100, 120, 80);
        ellipse2.setColor(Color.RED);
        ellipse2.setShowControlPoints(true);
        ellipses.add(ellipse2);

        currentEllipse = ellipse1;
    }

    private void updateStatus() {
        if (currentEllipse != null) {
            statusLabel.setText(currentEllipse.getInfo());
        } else {
            statusLabel.setText("No ellipse selected");
        }
    }

    private Color getRandomColor() {
        return new Color(
                (int)(Math.random() * 255),
                (int)(Math.random() * 255),
                (int)(Math.random() * 255)
        );
    }

    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            drawCoordinateSystem(g2d);
            for (EllipseDrawer ellipse : ellipses) {
                ellipse.setShowControlPoints(showControlPointsCheck.isSelected());
                ellipse.draw(g2d);
            }
        }

        private void drawCoordinateSystem(Graphics2D g2d) {
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.setStroke(new BasicStroke(1));

            for (int y = 0; y < getHeight(); y += 50) {
                g2d.drawLine(0, y, getWidth(), y);
                if (y % 100 == 0) {
                    g2d.drawString(String.valueOf(y), 5, y - 5);
                }
            }

            for (int x = 0; x < getWidth(); x += 50) {
                g2d.drawLine(x, 0, x, getHeight());
                if (x % 100 == 0) {
                    g2d.drawString(String.valueOf(x), x - 15, 15);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InteractiveEllipseEditor().setVisible(true);
        });
    }
}