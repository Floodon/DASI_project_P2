package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Personne;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DASI Team
 */
public class AuthentificationSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Personne personne = (Personne) request.getAttribute("personne");
        
        JsonObject container = new JsonObject();

        Boolean connexion = (personne != null);
        container.addProperty("connexion", connexion);

        if (personne != null) {
            JsonObject jsonPersonne = new JsonObject();
            
            jsonPersonne.addProperty("id", personne.getId());
            jsonPersonne.addProperty("type", personne instanceof Client ? "client" : "employe");
            
            container.add("personne", jsonPersonne);
        }

        write(container, response);
    }

}
