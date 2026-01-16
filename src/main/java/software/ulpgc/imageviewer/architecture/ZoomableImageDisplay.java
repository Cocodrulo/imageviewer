package software.ulpgc.imageviewer.architecture;

public interface ZoomableImageDisplay extends ImageDisplay {
    void zoomIn();
    void zoomOut();
    void resetZoom();
    double getZoomLevel();
}
