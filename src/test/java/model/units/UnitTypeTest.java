package model.units;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UnitTypeTest {

    Collection<UnitType> unitTypes;

    @BeforeEach
    void setUp() {
        unitTypes = Arrays.stream(UnitType.values()).filter(x->!UnitType.FORTRESS.equals(x)).collect(Collectors.toList());
    }
    @Test
    void upgrade() {
        for(UnitType type : unitTypes){
            assertThat(type.upgrade()).isNotNull();
        }
    }
}