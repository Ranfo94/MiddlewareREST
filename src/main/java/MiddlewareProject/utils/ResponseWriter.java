package MiddlewareProject.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseWriter {

    public ResponseWriter(){}

    public void sendResponse(String string, HttpServletResponse response) throws IOException {

        java.io.PrintWriter wr = response.getWriter();
        wr.print(string);
        wr.flush();
        wr.close();

    }

}
