package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConsultationSerialisation extends Serialisation {

    @Override
    protected JsonObject createJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Boolean connexion = (Boolean) request.getAttribute("connexion");
        Boolean consultOK = (Boolean) request.getAttribute("consultation-ok");
        
        JsonObject container = new JsonObject();
        
        if (connexion != null) {
            container.addProperty("connexion", connexion);
            if (connexion) {
                container.addProperty("consultation-ok", consultOK);
            }
        } else {
            container.addProperty("connexion", false);
        }
        
        return container;
    }
    
}
