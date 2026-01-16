package software.ulpgc.imageviewer.application.gui;

import software.ulpgc.imageviewer.architecture.Command;
import software.ulpgc.imageviewer.architecture.ZoomableImageDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import static java.awt.BorderLayout.*;

public class Desktop extends JFrame {
    private static final int BORDER_RADIUS = 15;
    private static final Color BG_DARK = new Color(40, 40, 40);
    private static final Color BG_MEDIUM = new Color(60, 60, 60);
    private static final Color BORDER_COLOR = new Color(70, 70, 70);
    private static final Color ACCENT_COLOR = new Color(76, 175, 80);
    private static final Color TEXT_COLOR = new Color(200, 200, 200);

    private final Map<String, Command> commands;
    private final ZoomableImageDisplay imageDisplay;
    private final DecimalFormat zoomFormat;
    private JLabel statusLabel;
    private JLabel zoomLabel;
    private boolean hasRoundedBorders;

    public static Desktop create(ZoomableImageDisplay imageDisplay) {
        return new Desktop(imageDisplay);
    }

    private Desktop(ZoomableImageDisplay imageDisplay) {
        this.commands = new HashMap<>();
        this.imageDisplay = imageDisplay;
        this.zoomFormat = new DecimalFormat("0%");
        this.hasRoundedBorders = true;
        
        initializeWindow();
        buildUI();
        setupKeyboardShortcuts();
    }
    
    private void initializeWindow() {
        setTitle("Image Viewer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setUndecorated(true);
        
        updateBorderRadius(true);
        addWindowStateListener();
        applySystemLookAndFeel();
    }

    private void addWindowStateListener() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateBorderRadius(hasRoundedBorders);
            }
        });
    }

    private void applySystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBorderRadius(boolean rounded) {
        this.hasRoundedBorders = rounded;
        if (rounded) {
            setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), BORDER_RADIUS, BORDER_RADIUS));
        } else {
            setShape(null);
        }
    }
    
    private void buildUI() {
        setLayout(new BorderLayout());
        
        add(new CustomTitleBar(this), NORTH);
        add((Component) imageDisplay, CENTER);
        add(createBottomPanel(), SOUTH);
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_DARK);
        panel.add(buildToolbar(), NORTH);
        panel.add(buildStatusBar(), SOUTH);
        return panel;
    }

    // === TOOLBAR CONSTRUCTION ===
    
    private JPanel buildToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        toolbar.setBackground(BG_DARK);
        toolbar.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, BORDER_COLOR));

        addNavigationButtons(toolbar);
        toolbar.add(createSeparator());
        addZoomButtons(toolbar);
        toolbar.add(createSeparator());
        addFileButtons(toolbar);

        return toolbar;
    }

    private void addNavigationButtons(JPanel toolbar) {
        toolbar.add(createSectionLabel("Navigate"));
        toolbar.add(createToolbarButton("⬅", "prev", "Previous Image (Left Arrow)"));
        toolbar.add(createToolbarButton("➡", "next", "Next Image (Right Arrow)"));
    }

    private void addZoomButtons(JPanel toolbar) {
        toolbar.add(createSectionLabel("Zoom"));
        toolbar.add(createToolbarButton("➕", "zoomIn", "Zoom In (Ctrl+Plus | Wheel Up)"));
        toolbar.add(createToolbarButton("➖", "zoomOut", "Zoom Out (Ctrl+Minus | Wheel Down)"));
        toolbar.add(createToolbarButton("🔍", "resetZoom", "Reset Zoom (Ctrl+0)"));
    }

    private void addFileButtons(JPanel toolbar) {
        toolbar.add(createSectionLabel("File"));
        toolbar.add(createToolbarButton("📁", "load", "Load Folder/Images (Ctrl+O)"));
    }

    // === UI COMPONENT FACTORIES ===
    
    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(150, 150, 150));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        return label;
    }

    private JSeparator createSeparator() {
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(2, 30));
        separator.setForeground(BORDER_COLOR);
        return separator;
    }

    private JButton createToolbarButton(String icon, String commandName, String tooltip) {
        JButton button = new JButton(icon);
        styleButton(button);
        button.setToolTipText(tooltip);
        addButtonHoverEffects(button);
        button.addActionListener(e -> executeCommand(commandName));
        return button;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        button.setPreferredSize(new Dimension(50, 50));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(BG_MEDIUM);
        button.setForeground(new Color(220, 220, 220));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private void addButtonHoverEffects(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_COLOR);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(96, 195, 100), 1, true),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BG_MEDIUM);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(80, 80, 80), 1, true),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
            }
        });
    }
    
    private JPanel buildStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(BG_DARK);
        statusBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR));
        statusBar.setPreferredSize(new Dimension(0, 30));

        statusLabel = new JLabel(" Ready");
        statusLabel.setForeground(TEXT_COLOR);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        statusBar.add(statusLabel, WEST);

        zoomLabel = new JLabel("Zoom: 100% ");
        zoomLabel.setForeground(TEXT_COLOR);
        zoomLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        statusBar.add(zoomLabel, EAST);

        return statusBar;
    }
    
    private void setupKeyboardShortcuts() {
        JRootPane rootPane = getRootPane();
        
        registerShortcut(rootPane, "prev", KeyEvent.VK_LEFT, 0);
        registerShortcut(rootPane, "next", KeyEvent.VK_RIGHT, 0);
        registerShortcut(rootPane, "zoomIn", KeyEvent.VK_PLUS, ActionEvent.CTRL_MASK);
        registerShortcut(rootPane, "zoomIn", KeyEvent.VK_EQUALS, ActionEvent.CTRL_MASK);
        registerShortcut(rootPane, "zoomOut", KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK);
        registerShortcut(rootPane, "resetZoom", KeyEvent.VK_0, ActionEvent.CTRL_MASK);
        registerShortcut(rootPane, "load", KeyEvent.VK_O, ActionEvent.CTRL_MASK);
    }

    private void registerShortcut(JRootPane rootPane, String commandName, int keyCode, int modifiers) {
        String actionKey = commandName + "_" + keyCode;
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(keyCode, modifiers), actionKey);
        rootPane.getActionMap().put(actionKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeCommand(commandName);
            }
        });
    }
    
    private void executeCommand(String commandName) {
        if (commands.containsKey(commandName)) {
            commands.get(commandName).execute();
            updateStatusBar();
        }
    }

    public void put(String name, Command command) {
        commands.put(name, command);
    }
    
    public void updateStatusBar() {
        updateImageStatus();
        updateZoomStatus();
    }

    private void updateImageStatus() {
        if (imageDisplay.image() != null) {
            statusLabel.setText(" Image: " + imageDisplay.image().id());
        } else {
            statusLabel.setText(" No image loaded");
        }
    }

    private void updateZoomStatus() {
        zoomLabel.setText("Zoom: " + zoomFormat.format(imageDisplay.getZoomLevel()) + " ");
    }
}
