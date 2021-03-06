package com.gluonhq.gaf.attach.views;

import com.gluonhq.attach.pictures.PicturesService;
import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.BottomNavigation;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.gaf.attach.GluonGAfAttach;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class PicturesPresenter extends GluonPresenter<GluonGAfAttach> {

    @FXML
    private View pictures;

    @FXML
    private ImageView imageView;
    
    @FXML
    private BottomNavigation bottomNavigation;
    
    public void initialize() {
        pictures.setShowTransitionFactory(BounceInRightTransition::new);
        
        pictures.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = getApp().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        getApp().getDrawer().open()));
                appBar.setTitleText(AppViewManager.PICTURES_VIEW.getTitle());
            }
        });
        pictures.setOnShown( e-> {
            imageView.fitWidthProperty().bind(pictures.widthProperty().subtract(10));
                imageView.fitHeightProperty().bind(pictures.getScene().heightProperty()
                        .subtract(getApp().getAppBar().getHeight() + bottomNavigation.prefHeight(-1) + 10));
        });
        
        pictures.setOnHidden(e -> {
            imageView.fitWidthProperty().unbind();
            imageView.fitHeightProperty().unbind();
        });
        
        final BottomNavigationButton takePicButton = new BottomNavigationButton("Take Picture", MaterialDesignIcon.PHOTO_CAMERA.graphic(), null);
        
        
        takePicButton.setOnMousePressed(e -> PicturesService.create()
                .flatMap(p -> p.takePhoto(true))
                .ifPresent(imageView::setImage));
        PicturesService.create().ifPresent(service -> takePicButton.setOnMousePressed(e -> service.takePhoto(true).ifPresent(imageView::setImage)));
        
        final BottomNavigationButton retrievePicButton = new BottomNavigationButton("Retrieve Picture", MaterialDesignIcon.PHOTO_ALBUM.graphic(), null);
        retrievePicButton.setOnMousePressed(e -> PicturesService.create()
                .flatMap(PicturesService::loadImageFromGallery)
                .ifPresent(imageView::setImage));

        bottomNavigation.getActionItems().addAll(takePicButton, retrievePicButton);

    }
}
