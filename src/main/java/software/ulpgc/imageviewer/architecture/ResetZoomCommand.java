package software.ulpgc.imageviewer.architecture;

public class ResetZoomCommand implements Command {
    private final ZoomableImageDisplay display;

    public ResetZoomCommand(ZoomableImageDisplay display) {
        this.display = display;
    }

    @Override
    public void execute() {
        display.resetZoom();
    }
}
