package org.inspirerobotics.sumobots.driverstation.util;

import org.inspirerobotics.sumobots.driverstation.BackendWorker;

public interface BackendEvent {

    void run(BackendWorker backendThread);
}
