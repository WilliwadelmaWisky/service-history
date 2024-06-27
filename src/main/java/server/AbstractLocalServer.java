package server;

import api.Request;
import api.Response;
import api.StatusCode;
import util.io.FileUtil;
import util.event.ActionI;

import java.io.File;
import java.nio.file.Paths;

/**
 *
 */
public abstract class AbstractLocalServer implements Server {

    private final String _directoryPath;


    /**
     * @param directoryPath
     */
    public AbstractLocalServer(String directoryPath) {
        _directoryPath = directoryPath;
        File rootDirectory = new File(_directoryPath);
        if (!rootDirectory.exists())
            rootDirectory.mkdirs();
    }


    /**
     * @param request
     * @param onRespond
     */
    @Override
    public void onReceiveRequest(Request request, ActionI<Response> onRespond) {
        switch (request.getType()) {
            case GET:
                onRespond.invoke(get(request.getURL()));
                break;
            case POST:
                onRespond.invoke(post(request.getURL(), request.getPayload()));
                break;
            case DELETE:
                onRespond.invoke(delete(request.getURL()));
                break;
        }
    }


    /**
     * @param url
     * @return
     */
    private Response get(String url) {
        String path = Paths.get(_directoryPath, url.split("/")).toString();
        File file = new File(path);
        if (!file.exists()) {
            return new Response(StatusCode.NOT_FOUND, null);
        }

        if (file.isDirectory()) {
            StringBuilder stringBuilder = new StringBuilder();
            File[] files = file.listFiles();
            if (files == null || files.length == 0)
                return new Response(StatusCode.NOT_FOUND, null);

            for (File f : files) {
                stringBuilder.append(f.getName());
                stringBuilder.append('\n');
            }

            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String content = stringBuilder.toString();
            return new Response(StatusCode.OK, content);
        }

        String content = FileUtil.read(file);
        return new Response(StatusCode.OK, content);
    }

    /**
     * @param url
     * @param payload
     * @return
     */
    private Response post(String url, String payload) {
        String path = Paths.get(_directoryPath, url.split("/")).toString();
        File file = new File(path);
        if (FileUtil.write(file, payload))
            return new Response(StatusCode.OK, null);

        return new Response(StatusCode.NOT_FOUND, null);
    }

    /**
     * @param url
     * @return
     */
    private Response delete(String url) {
        String path = Paths.get(_directoryPath, url.split("/")).toString();
        File file = new File(path);
        if (!file.exists())
            return new Response(StatusCode.NOT_FOUND, null);

        if (file.isDirectory()) {
            return new Response(StatusCode.NOT_FOUND, null);
        }

        file.delete();
        return new Response(StatusCode.OK, null);
    }
}
