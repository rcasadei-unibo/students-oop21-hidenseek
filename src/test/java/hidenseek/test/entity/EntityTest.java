/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package hidenseek.test.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import hidenseek.model.Entity;
import hidenseek.model.EntityImpl;
import hidenseek.model.components.Component;
import hidenseek.model.components.LifeComponent;
import hidenseek.model.components.LifeComponentImpl;
import hidenseek.model.components.LinearMovementComponentImpl;
import hidenseek.model.components.MoveComponent;
import hidenseek.model.components.ObservableComponent;
import hidenseek.model.components.Trigger;
import hidenseek.model.components.TriggerImpl;
import hidenseek.model.events.CollisionEvent;
import hidenseek.model.events.DamageEvent;
import javafx.geometry.Point2D;

public class EntityTest {
    @Test public void testEntityComponents() {
        //Detach and Attach test
        Entity e = new EntityImpl();
        assertTrue(e.getComponent(LifeComponent.class).isEmpty());
        e.attach(new LifeComponentImpl(100));
        assertFalse(e.getComponent(LifeComponent.class).isEmpty());
        e.detach(LifeComponent.class);
        assertTrue(e.getComponents().isEmpty());
        
        e.attach(new LifeComponentImpl(100));
        e.attach(new LinearMovementComponentImpl(new Point2D(0, 0), 2));  
        e.detach(LifeComponent.class);
        Set<Component> components = e.getComponents();
        assertTrue(components.size() == 1);
        assertTrue(MoveComponent.class.isInstance(components.stream().findFirst().get()));
        assertTrue(e == e.getComponent(MoveComponent.class).get().getOwner().get());
    
        //One component type per entity test
        Entity e2 = new EntityImpl();
        e2.attach(new LifeComponentImpl(100));
        e2.attach(new LinearMovementComponentImpl(new Point2D(0, 0), 2));
        e2.attach(new LifeComponentImpl(50));
        //The new life component should replace the old one
        assertEquals(e2.getComponents().size(), 2);
    }
    
    //NOTE: do further testing, if a component does not have an owner the test fails
    @Test public void testLifeComponent() {
        Entity e = new EntityImpl();
        LifeComponent l = new LifeComponentImpl(100);
        e.attach(l);
        assertTrue(l.getMaxHealth() == 100);
        l.hurt(10);
        assertTrue(l.getHealth() == 90);
        l.hurt(100);
        assertTrue(l.getHealth() == 0);  
    }
    
}
