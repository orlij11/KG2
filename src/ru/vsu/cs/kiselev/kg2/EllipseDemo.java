package ru.vsu.cs.kiselev.kg2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EllipseDemo extends JFrame {
    private List<EllipseDrawer> ellipses = new ArrayList<>();

    public EllipseDemo() {
        setTitle("Ellipse Drawing Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        createDemoEllipses();

        add(new DemoPanel());
    }

    private void createDemoEllipses() {
        EllipseDrawer ellipse1 = new EllipseDrawer();
        ellipse1.setEllipse(new Point(200, 150), 100, 50);
        ellipse1.setColor(Color.BLUE);
        ellipse1.setAlgorithm(new BresenhamAlgorithm());
        ellipses.add(ellipse1);

        EllipseDrawer ellipse2 = new EllipseDrawer();
        ellipse2.setEllipse(new Point(500, 300), 400, 200);
        ellipse2.setColor(Color.RED);
        ellipse2.setAlgorithm(new BresenhamAlgorithm());
        ellipses.add(ellipse2);

        EllipseDrawer ellipse3 = new EllipseDrawer();
        ellipse3.setEllipse(new Point(500, 350), 60, 60);
        ellipse3.setColor(Color.GREEN);
        ellipse3.setLineWidth(2);
        ellipses.add(ellipse3);


        EllipseDrawer ellipse4 = new EllipseDrawer();
        ellipse4.setEllipse(100, 300, 200, 100);
        ellipse4.setColor(Color.MAGENTA);
        ellipse4.setLineWidth(3);
        ellipses.add(ellipse4);
    }

    private class DemoPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Очистка фона
            Graphics2D g2d = (Graphics2D) g;

            drawCoordinateSystem(g2d);

            for (int i = 0; i < ellipses.size(); i++) {
                EllipseDrawer ellipse = ellipses.get(i);
                ellipse.draw(g2d);
                g2d.setColor(Color.BLACK);
                g2d.drawString(ellipse.getAlgorithmInfo(),
                        (int)ellipse.getControlPoints().get(0).getPosition().getX() - 20,
                        (int)ellipse.getControlPoints().get(0).getPosition().getY());
            }
        }

        private void drawCoordinateSystem(Graphics2D g2d) {
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.setStroke(new BasicStroke(1));

            for (int y = 0; y < getHeight(); y += 50) {
                g2d.drawLine(0, y, getWidth(), y);
            }

            for (int x = 0; x < getWidth(); x += 50) {
                g2d.drawLine(x, 0, x, getHeight());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EllipseDemo().setVisible(true);
        });
    }
}