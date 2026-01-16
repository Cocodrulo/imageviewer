package software.ulpgc.imageviewer.architecture;

public class ZoomInCommand implements Command {
    private final ZoomableImageDisplay display;

    public ZoomInCommand(ZoomableImageDisplay display) {
        this.display = display;
    }

    @Override
    public void execute() {
        display.zoomIn();
    }
}
