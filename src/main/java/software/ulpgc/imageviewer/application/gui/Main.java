package software.ulpgc.imageviewer.application.gui;

import software.ulpgc.imageviewer.application.FileImageStore;
import software.ulpgc.imageviewer.architecture.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static FileImageStore store;
    private static Map<String, File> loadedImages;
    private static ImageProvider imageProvider;
    private static SwingImageDisplay imageDisplay;
    private static Desktop desktop;

    public static void main(String[] args) {
        loadedImages = new HashMap<>();
        
        // Initialize with optional images folder
        File imagesFolder = new File("images");
        store = new FileImageStore(imagesFolder.exists() ? imagesFolder : null);
        imageProvider = ImageProvider.with(store.images());
        
        // Create UI
        imageDisplay = new SwingImageDisplay();
        
        // Show first image if available
        if (!store.isEmpty()) {
            imageDisplay.show(imageProvider.first(Main::readImage));
        }
        
        // Create desktop and wire up commands
        desktop = Desktop.create(imageDisplay);
        
        // Navigation commands
        desktop.put("next", new NextCommand(imageDisplay));
        desktop.put("prev", new PrevCommand(imageDisplay));
        
        // Zoom commands
        desktop.put("zoomIn", new ZoomInCommand(imageDisplay));
        desktop.put("zoomOut", new ZoomOutCommand(imageDisplay));
        desktop.put("resetZoom", new ResetZoomCommand(imageDisplay));
        
        // Load command - CLEARS queue and loads new folder/files
        desktop.put("load", new LoadImageCommand(Main::loadNewImages));
        
        desktop.setVisible(true);
        desktop.updateStatusBar();
    }

    private static void loadNewImages(File[] files) {
        if (files == null || files.length == 0) return;
        
        try {
            // CLEAR existing images
            loadedImages.clear();
            
            // Get parent folder
            File parentFolder = files[0].getParentFile();
            
            // Create new store and clear it
            store = new FileImageStore(parentFolder);
            store.clear();
            
            // Add only the new files
            for (File file : files) {
                if (file.exists() && file.isFile()) {
                    String fileName = file.getName();
                    loadedImages.put(fileName, file);
                    store.addImage(file);
                }
            }
            
            // Rebuild provider with ONLY new images
            imageProvider = ImageProvider.with(store.images());
            
            // Display first image
            if (!store.isEmpty()) {
                imageDisplay.show(imageProvider.first(Main::readImage));
                desktop.updateStatusBar();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] readImage(String id) {
        try {
            // Try loaded images first
            if (loadedImages.containsKey(id)) {
                return Files.readAllBytes(loadedImages.get(id).toPath());
            }
            
            // Try store folder
            if (store.getFolder() != null) {
                File imageFile = new File(store.getFolder(), id);
                if (imageFile.exists()) {
                    return Files.readAllBytes(imageFile.toPath());
                }
            }
            
            throw new RuntimeException("Image not found: " + id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
