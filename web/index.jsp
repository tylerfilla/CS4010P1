<%@ page contentType="text/html;charset=UTF-8" %>
<%
    RequestDispatcher dispatcher;
    String userAgent = request.getHeader("User-Agent");

    if (userAgent == null) {
        // Unknown OS, so default to Windows
        dispatcher = request.getRequestDispatcher("/input-win");
    } else {
        if (userAgent.contains("Mac OS")) {
            // Probably macOS
            dispatcher = request.getRequestDispatcher("/input-mac");
        } else if (userAgent.contains("Windows NT")) {
            // Probably Windows
            dispatcher = request.getRequestDispatcher("/input-win");
        } else {
            // Unknown OS, so default to Windows
            dispatcher = request.getRequestDispatcher("/input-win");
        }
    }

    dispatcher.forward(request, response);
%>
