package application.io;

/**
 *
 */
public final class HTMLBuilder {

    private static final String DEFAULT_TITLE = "Document";

    private final StringBuilder _stringBuilder;


    /**
     *
     */
    public HTMLBuilder() {
        _stringBuilder = new StringBuilder();
        reset();
    }

    /**
     *
     */
    private void reset() {
        _stringBuilder.setLength(0);
        _stringBuilder.append("<!DOCTYPE html>");
        _stringBuilder.append("<html lang=\"en\">");
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        _stringBuilder.append("</html>");
        String html = _stringBuilder.toString();
        //reset();

        return html;
    }


    /**
     * @param title
     * @param style
     */
    public void head(String title, String style) {
        _stringBuilder.append("<header>");
        _stringBuilder.append("<meta charset=\"UTF-8\">");
        _stringBuilder.append("<title>");
        _stringBuilder.append((title == null || title.isEmpty()) ? DEFAULT_TITLE : title);
        _stringBuilder.append("</title>");

        if (style != null && !style.isEmpty()) {
            _stringBuilder.append("<style>");
            _stringBuilder.append(style);
            _stringBuilder.append("</style>");
        }

        _stringBuilder.append("</header>");
    }


    public void beginBody() { _stringBuilder.append("<body>"); }
    public void endBody() { _stringBuilder.append("</body>"); }


    /**
     * @param tag
     * @param className
     */
    public void beginTag(String tag, String className) {
        _stringBuilder.append("<");
        _stringBuilder.append(tag);

        if (className != null && !className.isEmpty()) {
            _stringBuilder.append(" class=\"");
            _stringBuilder.append(className);
            _stringBuilder.append('\"');
        }
        _stringBuilder.append(">");
    }

    /**
     * @param tag
     */
    public void endTag(String tag) {
        _stringBuilder.append("</");
        _stringBuilder.append(tag);
        _stringBuilder.append(">");
    }

    /**
     * @param tag
     * @param content
     * @param className
     */
    public void createElement(String tag, String content, String className) {
        beginTag(tag, className);
        _stringBuilder.append(content);
        endTag(tag);
    }


    /**
     * @param content
     * @param className
     */
    public void p(String content, String className) { createElement("p", content, className); }

    /**
     * @param content
     * @param className
     */
    public void h1(String content, String className) { createElement("h1", content, className); }
}
