package hidenseek.view;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public final class CanvasDeviceImpl implements GraphicsDevice {

    private final GraphicsContext graphics;
    private final double width, height;
    
    public CanvasDeviceImpl(GraphicsContext graphics) {
        this.graphics = graphics;
        this.width = graphics.getCanvas().getWidth();
        this.height = graphics.getCanvas().getHeight();
    }

    @Override
    public void repaint() {
        this.graphics.clearRect(0, 0, this.width, this.height);
    }

    @Override
    public void fill(Color c) {
        graphics.setFill(c);
        graphics.fillRect(0, 0, this.width, this.height);
    }

    @Override
    public void drawImage(Image sprite, Point2D position) {
        graphics.drawImage(sprite, position.getX(), position.getY());
    }

    @Override
    public void drawRect(int w, int h, Point2D position, Color color) {
        graphics.setFill(color);
        graphics.fillRect(position.getX(), position.getY(), w, h);
    }

    @Override
    public void drawCircle(int radius, Point2D position, Color color) {
        graphics.setFill(color);
        graphics.fillOval(position.getX() - radius / 2, position.getY() - radius / 2, radius, radius);
    }
}