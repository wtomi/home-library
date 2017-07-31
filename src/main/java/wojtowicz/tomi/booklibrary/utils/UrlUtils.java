package wojtowicz.tomi.booklibrary.utils;

import javax.servlet.http.HttpServletRequest;

public class UrlUtils {

    public static String getAppUrl(final HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
