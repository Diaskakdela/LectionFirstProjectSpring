package org.step.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class CharsetFilter implements Filter {

    public static final String CONTEXT_TYPE = "text/html";

    private String encoding;
    private String encodingToUTF = "UTF-8";

    public void init(FilterConfig config) {
        String requestEncoding = "requestEncoding";

        encoding = config.getInitParameter(requestEncoding);
        if (encoding == null) {
            encoding = encodingToUTF;
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String contentType = CONTEXT_TYPE + ";charset=utf-8";

        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(encoding);
        }

        response.setContentType(contentType);
        response.setCharacterEncoding(encodingToUTF);

        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
