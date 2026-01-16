package software.ulpgc.imageviewer.application.gui;

import software.ulpgc.imageviewer.architecture.Canvas;
import software.ulpgc.imageviewer.architecture.Image;
import software.ulpgc.imageviewer.architecture.ImageDisplay;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class SwingImageDisplay extends JPanel implements ImageDisplay {
    private Image image;
    private BufferedImage bitmap;

    @Override
    public Image image() {
        return image;
    }

    @Override
    public void show(Image image) {
        this.image = image;
        this.bitmap = readBitmap();
        this.repaint();
    }
    @Override
    public void paint(Graphics g) {
        Canvas canvas = Canvas.ofSize(this.getWidth(), this.getHeight())
                .fit(bitmap.getWidth(), bitmap.getHeight());
        int x = (this.getWidth() - canvas.width()) / 2;
        int y = (this.getHeight() - canvas.height()) / 2;
        g.setColor(Color.GRAY);
        g.fillRect(0,0,this.getWidth(), this.getHeight());
        g.drawImage(bitmap, x, y, canvas.width(), canvas.height(), null);
    }

    private BufferedImage readBitmap() {
        try {
            return ImageIO.read(new ByteArrayInputStream(this.image.bitmap()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
