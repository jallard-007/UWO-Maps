package mapsJavaFX;

import maps.POILocation;

public class ControllerMediator {
    private MapViewController mapViewController;

    void registerMapViewController(MapViewController controller) {
        mapViewController = controller;
    }

    void mapViewControllerGoToPOI(POILocation poiLocation) {
        mapViewController.goToPOI(poiLocation);
    }
    private ControllerMediator() {}

    public static ControllerMediator getInstance() {
        return ControllerMediatorHolder.INSTANCE;
    }

    private static class ControllerMediatorHolder {
        private static final ControllerMediator INSTANCE = new ControllerMediator();
    }
}
