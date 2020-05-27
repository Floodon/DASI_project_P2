package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DASI Team
 */
public abstract class Serialisation {
    
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject toWrite = createJson(request, response);
        write(toWrite, response);
    }
    
    protected abstract JsonObject createJson(HttpServletRequest request, HttpServletResponse response) throws IOException;
    
    private void write(JsonObject object, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gson.toJson(object, out);
        }
    }
    
}
