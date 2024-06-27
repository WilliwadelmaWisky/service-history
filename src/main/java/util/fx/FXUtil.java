package util.fx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import java.io.IOException;
import java.io.InputStream;

/**
 * Very generic static helper functions for many different use cases (JavaFX).
 * This class is not meant to be constructed.
 *
 * @version 18.6.2024
 */
public final class FXUtil {

    /**
     * Load an .fxml file contents from resources
     * @param relativePath A path to the .fxml file (Relative to the resources directory)
     * @return Information about the loaded document
     */
    public static FXMLLoadData loadFxml(String relativePath) {
        try {
            FXMLLoader loader = new FXMLLoader(FXUtil.class.getResource(relativePath));
            Parent root = loader.load();
            return new FXMLLoadData(root, loader.getController());
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }


    /**
     *
     */
    public static class FXMLLoadData {

        private final Parent _root;
        private final Object _controller;


        /**
         * @param root
         * @param controller
         */
        public FXMLLoadData(Parent root, Object controller) {
            _root = root;
            _controller = controller;
        }

        /**
         * @return
         */
        public Parent getRoot() { return _root; }

        /**
         * @return
         */
        public Object getController() { return _controller; }
    }


    /**
     * Load an image from resources (.png, .jpg, ...)
     * @param relativePath A path to the image file (Relative to the resources directory)
     * @return Information about the loaded image
     */
    public static ImageLoadData loadImage(String relativePath) {
        InputStream imageStream = FXUtil.class.getResourceAsStream(relativePath);
        if (imageStream == null)
            return null;

        Image image = new Image(imageStream);
        return new ImageLoadData(image);
    }


    /**
     *
     */
    public static class ImageLoadData {
        private final Image _image;


        /**
         * @param image
         */
        public ImageLoadData(Image image) {
            _image = image;
        }

        /**
         * @return
         */
        public Image getImage() { return _image; }
    }
}
