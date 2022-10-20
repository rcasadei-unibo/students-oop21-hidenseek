package hidenseek.view.entities;

import hidenseek.model.enums.PowerUpType;
import hidenseek.view.GraphicsDevice;
import javafx.scene.image.Image;

public class PowerUpViewImpl extends AbstractEntityView implements PowerUpView {
    
    private static final String INCREASE_SPEED_SPRITE = "assets/potion_02.png";
    private static final String INCREASE_VISIBILITY_SPRITE = "assets/potion_03.png";
    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;
    private final PowerUpType type;

    public PowerUpViewImpl(PowerUpType type) {
        this.type = type;
    }
    
    @Override
    public void draw(GraphicsDevice device) {
        if (this.type.equals(PowerUpType.INCREASE_SPEED)) {
            device.drawImage(new Image(INCREASE_SPEED_SPRITE), WIDTH, HEIGHT, this.getPosition());        
        } else if (this.type.equals(PowerUpType.INCREASE_VISIBILITY)) {
            device.drawImage(new Image(INCREASE_VISIBILITY_SPRITE), WIDTH, HEIGHT, this.getPosition());   
        }
    }

    @Override
    public PowerUpType getPowerUpType() {
        return this.type;
    }

}
