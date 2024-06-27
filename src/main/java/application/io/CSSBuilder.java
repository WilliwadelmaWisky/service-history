package application.io;

/**
 *
 */
public final class CSSBuilder {

    private final StringBuilder _stringBuilder;


    /**
     *
     */
    public CSSBuilder() {
        _stringBuilder = new StringBuilder();
        reset();
    }

    /**
     *
     */
    private void reset() {
        _stringBuilder.setLength(0);
    }


    /**
     * @return
     */
    @Override
    public String toString() {
        String css = _stringBuilder.toString();
        reset();
        return css;
    }


    /**
     * @param tag
     * @param content
     */
    public void style(String tag, String content) {
        _stringBuilder.append(tag);
        _stringBuilder.append('{');
        _stringBuilder.append(content);
        _stringBuilder.append('}');
    }

    /**
     * @param className
     * @param content
     */
    public void styleByClass(String className, String content) {
        _stringBuilder.append('.');
        style(className, content);
    }

    /**
     * @param id
     * @param content
     */
    public void styleByID(String id, String content) {
        _stringBuilder.append('#');
        style(id, content);
    }


    /**
     * @param css
     */
    public void custom(String css) {
        _stringBuilder.append(css);
    }
}
