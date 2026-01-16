package software.ulpgc.imageviewer.architecture;

public class ZoomOutCommand implements Command {
    private final ZoomableImageDisplay display;

    public ZoomOutCommand(ZoomableImageDisplay display) {
        this.display = display;
    }

    @Override
    public void execute() {
        display.zoomOut();
    }
}
