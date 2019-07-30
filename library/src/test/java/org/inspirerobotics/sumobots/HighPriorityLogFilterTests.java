package org.inspirerobotics.sumobots;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HighPriorityLogFilterTests {

    @Test
    void filterInfoTest(){
        filterTest(Level.INFO, Filter.Result.ACCEPT);
    }

    @Test
    void filterFatalTest(){
        filterTest(Level.FATAL, Filter.Result.ACCEPT);
    }

    @Test
    void filterErrorTest(){
        filterTest(Level.ERROR, Filter.Result.ACCEPT);
    }

    @Test
    void filterWarningTest(){
        filterTest(Level.WARN, Filter.Result.ACCEPT);
    }

    @Test
    void filterDebugTest(){
        filterTest(Level.DEBUG, Filter.Result.DENY);
    }

    @Test
    void filterTraceTest(){
        filterTest(Level.TRACE, Filter.Result.DENY);
    }

    private void filterTest(Level level, Filter.Result expected){
        Assertions.assertEquals(expected, HighPriorityLogFilter.filter(level));
    }

}
