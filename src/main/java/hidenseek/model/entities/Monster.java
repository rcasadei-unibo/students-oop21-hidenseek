package hidenseek.model.entities;

import hidenseek.model.components.CollisionComponent;
import hidenseek.model.components.CollisionComponentImpl;
import hidenseek.model.components.LifeComponent;
import hidenseek.model.components.LifeComponentImpl;
import hidenseek.model.components.LinearMovementComponentImpl;
import hidenseek.model.components.MaterialComponent;
import hidenseek.model.components.MaterialComponentImpl;
import hidenseek.model.components.MoveComponent;
import hidenseek.model.components.ObservableComponent;
import hidenseek.model.components.PositionComponent;
import hidenseek.model.components.PositionComponentImpl;
import hidenseek.model.components.Trigger;
import hidenseek.model.components.TriggerImpl;
import hidenseek.model.components.brains.BrainComponent;
import hidenseek.model.components.brains.NaiveBrainComponentImpl;
import hidenseek.model.components.hearts.EvilHeartComponentImpl;
import hidenseek.model.components.hearts.HeartComponent;
import hidenseek.model.components.senses.SenseComponent;
import hidenseek.model.components.senses.SightSenseComponentImpl;
import hidenseek.model.events.CollisionEvent;
import javafx.geometry.Point2D;

public class Monster extends AbstractEntity {
    
    private static final double MONSTER_SPEED = 125;
    
    public Monster() {
        super();
        
        this.attach(new LifeComponentImpl(1));  
        
        //Position component
        final PositionComponent positionComponent = new PositionComponentImpl();
        positionComponent.setPosition(new Point2D(0, 0));
        this.attach(positionComponent);
        
        //Move component
        final MoveComponent moveComponent = new LinearMovementComponentImpl(MONSTER_SPEED);
        this.attach(moveComponent);
        
        //Material component
        final MaterialComponent materialComponent = new MaterialComponentImpl();
        this.attach(materialComponent);
        
        //Collision component
        final CollisionComponent collisionComponent = new CollisionComponentImpl();
        collisionComponent.addHitboxPoint(new Point2D(0, 0));
        collisionComponent.addHitboxPoint(new Point2D(0, 50));
        collisionComponent.addHitboxPoint(new Point2D(50, 50));
        collisionComponent.addHitboxPoint(new Point2D(50, 0));
        this.attach(collisionComponent);  
        
        //Trigger for collisions (Hurt if I touch the player)
        final Trigger<CollisionEvent> collisionListener = new TriggerImpl<>(CollisionEvent.class, (event, powerup) -> {
            if (Player.class.isInstance(event.getCollider())) {
                event.getCollider().getComponent(LifeComponent.class).ifPresent(component -> component.hurt(1));
            }
        });
        
        ObservableComponent collisionObserver = (ObservableComponent) collisionComponent;
        collisionObserver.attachListener(collisionListener);
        
        // heart
        final HeartComponent heart = new EvilHeartComponentImpl();
        this.attach(heart);

        // sight sense
        final SenseComponent sight = new SightSenseComponentImpl(200);
        this.attach(sight);
        
        // brain sense
        final BrainComponent brain = new NaiveBrainComponentImpl();
        this.attach(brain);
    }
}
