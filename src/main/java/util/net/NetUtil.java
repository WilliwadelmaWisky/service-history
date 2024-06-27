package util.net;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 */
public final class NetUtil {

    /**
     * @param url
     * @return
     */
    public static boolean browse(String url) {
        try {
            URI uri = new URI(url);
            return browse(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param file
     * @return
     */
    public static boolean browse(File file) {
        URI uri = file.toURI();
        return browse(uri);
    }

    /**
     * @param uri
     * @return
     */
    public static boolean browse(URI uri) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(uri);
                return true;
            } catch (IOException ioException) {
                throw new RuntimeException(ioException);
            }
        }

        return false;
    }
}
