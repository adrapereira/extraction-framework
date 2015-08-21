package org.dbpedia.extraction.live.administrative;

import org.dbpedia.extraction.live.queue.LiveQueue;
import org.dbpedia.extraction.live.queue.LiveQueueItem;
import org.dbpedia.extraction.live.queue.LiveQueuePriority;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Andre on 31/07/2015.
 */
public class AddItemServlet extends HttpServlet {
    public AddItemServlet(){}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        String password = request.getParameter("password");
        String item = request.getParameter("item");
        boolean result = false;
        String message = "";

        String path = getServletContext().getRealPath("/") + "/../adminPassword.txt";
        String passw = new Scanner(new File(path)).nextLine();

        if(password == null || !password.equals(passw)){
            result = false;
            message = "Wrong password!";
        }else{
            LiveQueueItem newItem = new LiveQueueItem(Long.parseLong(item), "");
            newItem.setPriority(LiveQueuePriority.ManualPriority);
            LiveQueue.add(newItem);
            result = true;
            message = "The item was successfully added to the queue";
        }
        response.getWriter().println("{\"result\":" + result + ", \"message\": \"" + message + "\"}");
    }
}