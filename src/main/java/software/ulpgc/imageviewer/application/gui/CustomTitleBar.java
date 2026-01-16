package software.ulpgc.imageviewer.application.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomTitleBar extends JPanel {
    private final JFrame frame;
    private Point initialClick;
    private boolean isMaximized = false;

    public CustomTitleBar(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(40, 40, 40));
        setPreferredSize(new Dimension(0, 40));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(70, 70, 70)));

        JLabel titleLabel = new JLabel("Image Viewer");
        titleLabel.setForeground(new Color(200, 200, 200));
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        add(titleLabel, BorderLayout.WEST);

        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        controlsPanel.setBackground(new Color(40, 40, 40));

        JButton minimizeButton = createControlButton("−", e -> frame.setState(JFrame.ICONIFIED));
        controlsPanel.add(minimizeButton);

        JButton maximizeButton = createControlButton("□", e -> toggleMaximize());
        controlsPanel.add(maximizeButton);

        JButton closeButton = createControlButton("×", e -> System.exit(0));
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setBackground(new Color(232, 17, 35));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setBackground(new Color(40, 40, 40));
            }
        });
        controlsPanel.add(closeButton);

        add(controlsPanel, BorderLayout.EAST);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isMaximized) {
                    return;
                }
                int thisX = frame.getLocation().x;
                int thisY = frame.getLocation().y;

                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                frame.setLocation(X, Y);
            }
        });
    }

    private JButton createControlButton(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setForeground(new Color(220, 220, 220));
        button.setBackground(new Color(40, 40, 40));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setPreferredSize(new Dimension(46, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!text.equals("×")) {
                    button.setBackground(new Color(60, 60, 60));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!text.equals("×")) {
                    button.setBackground(new Color(40, 40, 40));
                }
            }
        });

        button.addActionListener(action);
        return button;
    }

    private void toggleMaximize() {
        if (isMaximized) {
            frame.setExtendedState(JFrame.NORMAL);
            isMaximized = false;
            if (frame instanceof Desktop) {
                ((Desktop) frame).updateBorderRadius(true);
            }
        } else {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            isMaximized = true;
            if (frame instanceof Desktop) {
                ((Desktop) frame).updateBorderRadius(false);
            }
        }
    }
}
