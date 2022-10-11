package hidenseek.model.components;

import java.util.Set;
import java.util.function.Consumer;

import hidenseek.model.entities.Entity;
import javafx.scene.input.KeyCode;

public interface InputHandlerComponent extends Component {
    
    /**
     * Maps a keyboard key to a callback
     * @param key, the keycode of the key
     * @param action, a consumer
     */
    void mapKeyToAction(KeyCode key, Consumer<Entity> action);
    
    /**
     * Maps a keyboard key to a callback following a 'onTyped' type of behaviour.
     * The action callback is executed once, even if the key has not been released yet.
     * The releaseAction is executed after key is released
     * @param key
     * @param action
     * @param releaseAction
     */
    void mapKeyToOneTimeAction(KeyCode key, Consumer<Entity> action, Consumer<Entity> releaseAction);
    
    
    void mapKeyToOneTimeAction(KeyCode key, Consumer<Entity> action);
   
    /**
     * Executes the callbacks mapped to the relatives keys
     * @param keysPressed, set of keys currently pressed
     */
    void computeScheme(Set<KeyCode> keysPressed);
}
