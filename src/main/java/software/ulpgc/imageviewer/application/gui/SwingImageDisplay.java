package software.ulpgc.imageviewer.application.gui;

import software.ulpgc.imageviewer.architecture.Canvas;
import software.ulpgc.imageviewer.architecture.Image;
import software.ulpgc.imageviewer.architecture.ZoomableImageDisplay;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class SwingImageDisplay extends JPanel implements ZoomableImageDisplay {
    private static final double MIN_ZOOM = 0.25;
    private static final double MAX_ZOOM = 5.0;
    private static final double ZOOM_STEP = 0.25;
    private static final double WHEEL_ZOOM_STEP = 0.1;

    private Image image;
    private BufferedImage bitmap;
    private double zoomLevel = 1.0;
    private int panX = 0;
    private int panY = 0;
    private Point lastDragPoint;

    public SwingImageDisplay() {
        setupMouseListeners();
    }

    private void setupMouseListeners() {
        // Mouse wheel zoom
        addMouseWheelListener(e -> {
            if (bitmap == null) return;
            
            if (e.getWheelRotation() < 0) {
                // Scroll up - zoom in
                if (zoomLevel < MAX_ZOOM) {
                    zoomLevel = Math.min(MAX_ZOOM, zoomLevel + WHEEL_ZOOM_STEP);
                    repaint();
                }
            } else {
                // Scroll down - zoom out
                if (zoomLevel > MIN_ZOOM) {
                    zoomLevel = Math.max(MIN_ZOOM, zoomLevel - WHEEL_ZOOM_STEP);
                    // Reset pan when zooming out to fit
                    if (zoomLevel <= 1.0) {
                        panX = 0;
                        panY = 0;
                    }
                    repaint();
                }
            }
        });

        // Panning with mouse drag
        MouseAdapter panAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (bitmap != null && zoomLevel > 1.0) {
                    lastDragPoint = e.getPoint();
                    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                lastDragPoint = null;
                setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (lastDragPoint != null && bitmap != null && zoomLevel > 1.0) {
                    int dx = e.getX() - lastDragPoint.x;
                    int dy = e.getY() - lastDragPoint.y;
                    
                    panX += dx;
                    panY += dy;
                    
                    lastDragPoint = e.getPoint();
                    repaint();
                }
            }
        };

        addMouseListener(panAdapter);
        addMouseMotionListener(panAdapter);
    }

    @Override
    public Image image() {
        return image;
    }

    @Override
    public void show(Image image) {
        this.image = image;
        this.bitmap = readBitmap();
        // Reset pan and zoom when showing new image
        this.panX = 0;
        this.panY = 0;
        this.zoomLevel = 1.0;
        this.repaint();
    }

    @Override
    public void zoomIn() {
        if (zoomLevel < MAX_ZOOM) {
            zoomLevel = Math.min(MAX_ZOOM, zoomLevel + ZOOM_STEP);
            repaint();
        }
    }

    @Override
    public void zoomOut() {
        if (zoomLevel > MIN_ZOOM) {
            zoomLevel = Math.max(MIN_ZOOM, zoomLevel - ZOOM_STEP);
            // Reset pan when zooming out to fit
            if (zoomLevel <= 1.0) {
                panX = 0;
                panY = 0;
            }
            repaint();
        }
    }

    @Override
    public void resetZoom() {
        zoomLevel = 1.0;
        panX = 0;
        panY = 0;
        repaint();
    }

    @Override
    public double getZoomLevel() {
        return zoomLevel;
    }

    @Override
    public void paint(Graphics g) {
        if (bitmap == null) {
            g.setColor(new Color(30, 30, 30));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(new Color(150, 150, 150));
            g.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            String message = "No images loaded. Click 'Load Image' to get started.";
            FontMetrics fm = g.getFontMetrics();
            int x = (this.getWidth() - fm.stringWidth(message)) / 2;
            int y = this.getHeight() / 2;
            g.drawString(message, x, y);
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Canvas canvas = Canvas.ofSize(this.getWidth(), this.getHeight())
                .fit(bitmap.getWidth(), bitmap.getHeight());
        
        int scaledWidth = (int) (canvas.width() * zoomLevel);
        int scaledHeight = (int) (canvas.height() * zoomLevel);
        
        // Apply panning offset
        int x = (this.getWidth() - scaledWidth) / 2 + panX;
        int y = (this.getHeight() - scaledHeight) / 2 + panY;
        
        g2d.setColor(new Color(30, 30, 30));
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2d.drawImage(bitmap, x, y, scaledWidth, scaledHeight, null);
    }

    private BufferedImage readBitmap() {
        try {
            return ImageIO.read(new ByteArrayInputStream(this.image.bitmap()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

