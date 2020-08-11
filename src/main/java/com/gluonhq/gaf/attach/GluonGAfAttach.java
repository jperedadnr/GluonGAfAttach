package com.gluonhq.gaf.attach;

import com.gluonhq.attach.util.Constants;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;
import com.gluonhq.gaf.attach.views.AppViewManager;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GluonGAfAttach extends MobileApplication {

    @Override
    public void init() {
        AppViewManager.registerViewsAndDrawer(this);
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        scene.getStylesheets().add(GluonGAfAttach.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(GluonGAfAttach.class.getResourceAsStream("/icon.png")));
    }

    public static void main(String[] args) {
        System.setProperty(Constants.ATTACH_DEBUG, "true");
        launch(args);
    }
}
