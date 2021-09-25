package model;

import clock.Clock;
import model.fortress.Fortress;
import model.fortress.configs.DifficultyLevel;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public interface GameFactory<T> {
    T setupGame(
            Clock clock,
            DifficultyLevel difficultyLevel,
            BiFunction<Fortress, Fortress, T> resultConsumer);
}
