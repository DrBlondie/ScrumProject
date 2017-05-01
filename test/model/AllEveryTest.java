package model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({getSurroundingValuesWB.class, getSurroundingValuesValid.class, getSurroundingValuesInvalid.class,performMoveValid.class, performMoveInvalid.class,
performMoveValidWB.class})
public class AllEveryTest {
}
