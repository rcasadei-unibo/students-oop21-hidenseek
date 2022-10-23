package hidenseek.controller;

import java.util.LinkedHashSet;
import java.util.Set;

import hidenseek.controller.entities.EntityController;
import hidenseek.model.GameLevel;
import hidenseek.model.entities.Entity;
import hidenseek.model.enums.GameState;
import hidenseek.model.statistics.StatisticsManager;
import hidenseek.model.statistics.numeric.Numeric;
import hidenseek.model.statistics.numeric.NumericStatistic;
import hidenseek.model.statistics.score.ScoreStatistic;
import hidenseek.model.worlds.GameWorld;
import hidenseek.model.worlds.GameWorldImpl;
import hidenseek.view.Renderer;

public final class GameWorldControllerImpl implements GameWorldController {
    
    private final GameSceneController mainController;
    private final Gameloop loop;
    private final Set<EntityController> entities;
    private final Set<HudController> huds;
    private final Renderer view;
    private final InputScheme input;
    private final GameWorld model;
    private final LevelHandler level;
    private final StatisticsManager statisticsManager;
    private long levelPlayTime;
    
    public GameWorldControllerImpl(final GameSceneController mainController, final Renderer view, 
                                final InputScheme input, final LevelHandler level, StatisticsManager statisticsManager) {
        this.view = view;
        this.mainController = mainController;
        this.entities = new LinkedHashSet<>();
        this.huds = new LinkedHashSet<>();
        this.input = input;
        this.model = new GameWorldImpl();
        this.level = level;
        this.statisticsManager = statisticsManager;
        
        this.loop = new GameloopFXImpl() {

            @Override
            public void tick(final double delta) {
                update(delta);
            }

        };
        
        this.loadLevel(level.getCurrentLevel().get());
        this.loop.start();
    }


    @Override
    public void update(final double delta) {

        // handle inputs
        model.updateInput(this.input.getCurrentPressedKeys());
        
        // update logic
        model.update(delta);
        
        this.removeDeadEntities(model.getDeadEntities());
        
        //Draw game
        view.refresh();
        
        // update entities
        this.entities.forEach(entity -> {
            // update view
            entity.update();
            // draw entity
            this.view.drawEntity(entity.getView());
        });
        
        this.huds.forEach(hud -> {
            // update view
            hud.update();
            // draw hud
            this.view.drawHud(hud.getView());
        });
        
        if (this.model.getState() == GameState.OVER_LOSE) {
            this.handleGameOver();
        }
        
        if (this.model.getState() == GameState.OVER_WIN) {
            this.handleWin();
        }
    }
    
    @Override
    public void addEntity(final EntityController entityController) {
        this.entities.add(entityController);
        this.model.addEntity(entityController.getModel());
    }

    @Override
    public void addHud(final HudController hudController) {
        this.huds.add(hudController);
    }

    @Override
    public void pause() {
        this.loop.stop();
    }

    @Override
    public void resume() {
        this.loop.start();
    }
    
    private void removeDeadEntities(final Set<Entity> entities) {
        this.entities.removeIf(controller -> entities.contains(controller.getModel()));
        entities.forEach(entity -> model.removeEntity(entity));
    }
    
    private void handleGameOver() {
        ((NumericStatistic)statisticsManager.getStatistic("total_loose").findFirst().get()).getProperty().increase(1);
        ((NumericStatistic)statisticsManager.getStatistic("total_play_time").findFirst().get()).getProperty().increase((int) levelPlayTime);
        setWinPercentage();
        
        //No more levels
        this.mainController.goToGameOver();
        this.loop.stop();
    }
    
    private void handleWin() {
        //Add collected key
        final GameLevel level = this.level.getCurrentLevel().get();
        final int levelKeysCount = level.getKeysNumber();
        final String levelID = Integer.toString(level.getLevelID());
        final double levelScore = 5 * (levelPlayTime / level.getLevelMaximumTime());
        
        ((NumericStatistic)statisticsManager.getStatistic("collected_keys").findFirst().get()).getProperty().increase(levelKeysCount);
        
        ((NumericStatistic)statisticsManager.getStatistic("total_win").findFirst().get()).getProperty().increase(1);
        
        ((NumericStatistic)statisticsManager.getStatistic("total_attempts", levelID).findFirst().get()).getProperty().increase(1);
        
        ((ScoreStatistic)statisticsManager.getStatistic("actual_score", levelID).findFirst().get()).getProperty().setValue(levelScore);
        
        ((ScoreStatistic)statisticsManager.getStatistic("best_score", levelID).findFirst().get()).getProperty().setValue(
                Math.max(levelScore, ((ScoreStatistic)statisticsManager.getStatistic("best_score", levelID).findFirst().get()).getProperty().getValue())
        );        
        
        this.setWinPercentage();
        
        //There's a next level
        if (this.level.hasNext()) {
            this.level.next();
            this.loadLevel(this.level.getCurrentLevel().get());
            final int currentLevelID = this.level.getCurrentLevel().get().getLevelID();
            ((NumericStatistic)statisticsManager.getStatistic("curr_level").findFirst().get()).getProperty().setValue(currentLevelID);
            return;
        }
        this.mainController.goToMenu();
        this.loop.stop();
    }
    
    private void loadLevel(final GameLevel gameLevel) {
        this.entities.clear();
        this.huds.clear();
        this.model.clearEntities();
        gameLevel.getEntities().forEach(entityController -> this.addEntity(entityController));
        gameLevel.getHuds().forEach(hudController -> this.addHud(hudController));
        this.model.setKeys(gameLevel.getKeysNumber());
        
        levelPlayTime = System.currentTimeMillis();
    }
    
    private void setWinPercentage() {
        int totalWin = ((Numeric)statisticsManager.getStatistic("total_win").findFirst().get().getProperty()).getValue();
        int totalLoose = ((Numeric)statisticsManager.getStatistic("total_loose").findFirst().get().getProperty()).getValue();
        int winPercentage = totalWin / (totalWin + totalLoose);
        
        ((NumericStatistic)statisticsManager.getStatistic("win_percentage").findFirst().get()).getProperty().setValue(winPercentage);;
    }
}
