package application.io;

import util.io.FileUtil;
import util.net.NetUtil;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 *
 */
public class DocumentPrinter {

    private static final String TMP_DIRECTORY_PATH = System.getProperty("java.io.tmpdir");
    private static final String TMP_FILE_NAME = "sh_servicedoc_2e3t0f30-9f84-405e-b6ca-d9a9d63c815b";

    private final File _file;
    private final Document _document;


    /**
     * @param document
     */
    public DocumentPrinter(Document document) {
        _document = document;
        _file = new File(Paths.get(TMP_DIRECTORY_PATH, TMP_FILE_NAME + "." + document.getFormat()).toString());
    }


    /**
     *
     */
    public void print() {
        FileUtil.write(_file, _document.getContent());
        NetUtil.browse(_file);
    }
}
