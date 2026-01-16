package software.ulpgc.imageviewer.architecture;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.function.Consumer;

public class LoadImageCommand implements Command {
    private final Consumer<File[]> imageLoader;

    public LoadImageCommand(Consumer<File[]> imageLoader) {
        this.imageLoader = imageLoader;
    }

    @Override
    public void execute() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Images");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileFilter(new FileNameExtensionFilter(
                "Image Files", "jpg", "jpeg", "png", "gif", "bmp"));

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            if (selectedFile.isDirectory()) {
                // Load all images from directory
                File[] imageFiles = selectedFile.listFiles((dir, name) -> {
                    String lower = name.toLowerCase();
                    return lower.endsWith(".jpg") || lower.endsWith(".jpeg") || 
                           lower.endsWith(".png") || lower.endsWith(".gif") || 
                           lower.endsWith(".bmp");
                });
                if (imageFiles != null && imageFiles.length > 0) {
                    imageLoader.accept(imageFiles);
                }
            } else {
                // Load selected files
                File[] selectedFiles = fileChooser.getSelectedFiles();
                if (selectedFiles.length == 0) {
                    selectedFiles = new File[]{selectedFile};
                }
                imageLoader.accept(selectedFiles);
            }
        }
    }
}
