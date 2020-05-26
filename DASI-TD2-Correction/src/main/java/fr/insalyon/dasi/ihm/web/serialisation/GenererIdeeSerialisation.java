package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.service.Service;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DASI Team
 */
public class GenererIdeeSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Boolean connexion = (Boolean) request.getAttribute("connexion");
        Client client = (Client) request.getAttribute("client");
        Integer amour = (Integer) request.getAttribute("amour");
        Integer sante = (Integer) request.getAttribute("sante");
        Integer travail = (Integer) request.getAttribute("travail");
        
        JsonObject container = new JsonObject();
        
        if (connexion != null) {
            container.addProperty("connexion", connexion);
            if (connexion && client != null && amour != null && sante != null && travail != null) {
                Service service = new Service();
                
                List<String> predictions = service.getPredictions(client, amour, sante, travail);
                
                JsonArray jsonPredictions = new JsonArray();
                predictions.forEach(jsonPredictions::add);
                
                container.add("textes", jsonPredictions);
            }
        } else {
            container.addProperty("connexion", false);
        }
    }

}
