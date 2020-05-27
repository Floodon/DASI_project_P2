/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Medium;
import java.io.IOException;
import java.io.PrintWriter;
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
                JsonObject jsonMedium = new JsonObject();

                jsonMedium.addProperty("id", m.getId());
                jsonMedium.addProperty("denomination", m.getDenomination());
                jsonMedium.addProperty("presentation", m.getPresentation());
                jsonMedium.addProperty("type", m.getType());

                mediums.add(jsonMedium);
            }
        } else {
            container.addProperty("connexion", false);
        }
        
        container.add("mediums", mediums);
        
        return container;
    }
}
