package hidenseek.model.entities;

import hidenseek.model.components.CollisionComponent;
import hidenseek.model.components.CollisionComponentImpl;
import hidenseek.model.components.LifeComponent;
import hidenseek.model.components.LifeComponentImpl;
import hidenseek.model.components.LinearMovementComponentImpl;
import hidenseek.model.components.MoveComponent;
import hidenseek.model.components.PositionComponent;
import hidenseek.model.components.PositionComponentImpl;
import javafx.geometry.Point2D;

public class Enemy extends AbstractEntity {
    
    public Enemy() {
        super();
        
        //Life component
        final LifeComponent lifeComponent = new LifeComponentImpl(1);
        this.attach(lifeComponent);
        
        //Position component
        final PositionComponent positionComponent = new PositionComponentImpl();
        positionComponent.setPosition(new Point2D(0, 0));
        this.attach(positionComponent);
        
        //Move component
        final MoveComponent moveComponent = new LinearMovementComponentImpl();
        this.attach(moveComponent);
        
        //Collision component
        final CollisionComponent collisionComponent = new CollisionComponentImpl();
        collisionComponent.addHitboxPoint(new Point2D(0, 0));
        collisionComponent.addHitboxPoint(new Point2D(100, 0));
        collisionComponent.addHitboxPoint(new Point2D(100, 100));
        collisionComponent.addHitboxPoint(new Point2D(0, 100));
        this.attach(collisionComponent);
    }
}