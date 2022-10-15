package hidenseek.model.worlds;

import java.util.Set;

import hidenseek.model.entities.Entity;
import javafx.scene.input.KeyCode;

public interface GameWorld extends EntityWorld{

    void updateInput(final Set<KeyCode> keysPressed);
    
    Set<Entity> getDeadEntities();
}
