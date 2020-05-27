package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InscriptionSerialisation extends Serialisation{
    
    @Override
    protected JsonObject createJson(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Boolean erreur = (Boolean) request.getAttribute("erreur");
        
        JsonObject container = new JsonObject();

        if (erreur != null) {
            container.addProperty("erreur", erreur);
        }
        
        return container;
    }
    
}
