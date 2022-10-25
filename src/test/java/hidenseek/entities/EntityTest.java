package hidenseek.entities;
/*
 * This Java source file was generated by the Gradle 'init' task.
 */


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import hidenseek.model.components.Component;
import hidenseek.model.components.lives.LifeComponent;
import hidenseek.model.components.lives.LifeComponentImpl;
import hidenseek.model.components.physics.CollisionComponentImpl;
import hidenseek.model.components.physics.LinearMovementComponentImpl;
import hidenseek.model.components.physics.MoveComponent;
import hidenseek.model.entities.Entity;
import hidenseek.model.entities.AbstractEntity;

public class EntityTest {
    
    @Test public void testEntityComponents() {
        //Detach and Attach test
        final Entity e = new AbstractEntity(){};
        assertTrue(e.getComponent(LifeComponent.class).isEmpty());
        e.attach(new LifeComponentImpl(100));
        assertFalse(e.getComponent(LifeComponent.class).isEmpty());
        e.detach(LifeComponent.class);
        assertTrue(e.getComponents().isEmpty());
        
        e.attach(new LifeComponentImpl(100));
        e.attach(new LinearMovementComponentImpl(1));  
        e.detach(LifeComponent.class);
        final Set<Component> components = e.getComponents();
        assertEquals(1, components.size());
        assertTrue(MoveComponent.class.isInstance(components.stream().findFirst().get()));
        assertEquals(e, e.getComponent(MoveComponent.class).get().getOwner().get());
    }
    
    @Test
    public void testSingleComponentType() {
        final Entity e = new AbstractEntity(){};
        e.attach(new LinearMovementComponentImpl(1));
        e.attach(new LinearMovementComponentImpl(1));
        e.attach(new CollisionComponentImpl());
        e.attach(new CollisionComponentImpl());
        assertEquals(2, e.getComponents().size());
    }
    
    
}
