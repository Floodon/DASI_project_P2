/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Medium;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author MrFlo
 */
public class ListeMediumsSerialisation extends Serialisation {
    
    @Override
    protected JsonObject createJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Boolean connexion = (Boolean) request.getAttribute("connexion");
        List<Medium> listeMediums = (List<Medium>)request.getAttribute("mediums");
        
        JsonObject container = new JsonObject();
        JsonArray mediums = new JsonArray();
        
        if (connexion != null && connexion && listeMediums != null) {
            container.addProperty("connexion", true);
            for (Medium m : listeMediums) {
                JsonObject jsonMedium = serializeMedium(m);
                mediums.add(jsonMedium);
            }
        } else {
            container.addProperty("connexion", false);
        }
        
        container.add("mediums", mediums);
        
        return container;
    }
}
