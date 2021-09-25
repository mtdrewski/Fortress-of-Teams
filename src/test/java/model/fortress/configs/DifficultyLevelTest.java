package model.fortress.configs;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DifficultyLevelTest {

    @Test
    void nonNullTest() {
        for(DifficultyLevel level : DifficultyLevel.values()){
            assertThat(level.getPlayerConfigs()).isNotNull();
            assertThat(level.getBotConfigs()).isNotNull();

            assertThat(level.createBotFortressStatistics()).isNotNull();
            assertThat(level.createPlayerFortressStatistics()).isNotNull();

            assertThat(level.createBotFortressMoney()).isNotNull();
            assertThat(level.createPlayerFortressMoney()).isNotNull();
        }
    }
}