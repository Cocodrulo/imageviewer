package software.ulpgc.imageviewer.application;

import software.ulpgc.imageviewer.architecture.ImageStore;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FileImageStore implements ImageStore {
    private final File folder;
    private final List<String> imageList;

    public FileImageStore(File folder) {
        this.folder = folder;
        this.imageList = new ArrayList<>();
        if (folder != null && folder.exists() && folder.isDirectory()) {
            String[] files = folder.list();
            if (files != null) {
                imageList.addAll(Arrays.asList(files));
            }
        }
    }

    @Override
    public Stream<String> images() {
        return imageList.stream();
    }

    public void addImage(File file) {
        if (file != null && file.exists() && file.isFile()) {
            String fileName = file.getName();
            if (!imageList.contains(fileName)) {
                imageList.add(fileName);
            }
        }
    }

    public File getFolder() {
        return folder;
    }

    public boolean isEmpty() {
        return imageList.isEmpty();
    }

    public void clear() {
        imageList.clear();
    }
}
