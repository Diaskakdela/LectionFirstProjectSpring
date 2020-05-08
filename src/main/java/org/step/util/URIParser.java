package org.step.util;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

public class URIParser {

    public static String param(HttpServletRequest request) {
        return request.getParameterMap()
                .entrySet()
                .stream()
                .map(entry -> {
                    String param = String.join(" and ", entry.getValue());
                    return entry.getKey() + " => " + param;
                })
                .collect(Collectors.joining("\n"));
    }
}
