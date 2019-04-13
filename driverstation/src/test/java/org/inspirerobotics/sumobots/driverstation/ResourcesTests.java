package org.inspirerobotics.sumobots.driverstation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

public class ResourcesTests {

    @Test
    void guiIconPresentTest() {
        assertResourcePresent("icon.png");
    }

    @Test
    void assertResourcePresentFakeFileFailsTest() {
        Assertions.assertThrows(AssertionFailedError.class, () -> assertResourcePresent("fakeFile.hh"));
    }


    public void assertResourcePresent(String path){
        ClassLoader classLoader = getClass().getClassLoader();

        Assertions.assertNotNull(classLoader.getResource(path));
    }
}
