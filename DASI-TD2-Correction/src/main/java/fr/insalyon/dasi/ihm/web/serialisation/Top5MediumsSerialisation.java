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
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author berth
 */
public class Top5MediumsSerialisation extends Serialisation {

    @Override
    protected JsonObject createJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Boolean connexion = (Boolean) request.getAttribute("connexion");
        List<Medium> topMediums = (List<Medium>) request.getAttribute("top-mediums");
        Map<Medium, Integer> topMediumsConsult = (Map<Medium, Integer>) request.getAttribute("top-mediums-map");
        
        JsonObject container = new JsonObject();
        
        if (connexion != null && connexion) {
            container.addProperty("connexion", true);
            
            JsonArray jsonMediumsArray = new JsonArray();
            if (topMediums != null && topMediumsConsult != null) {
                for (Medium m : topMediums) {
                    JsonObject jsonMedium = serializeMedium(m);
                    jsonMedium.addProperty("nConsultations", topMediumsConsult.get(m));
                    jsonMediumsArray.add(jsonMedium);
                }
            }
            container.add("top", jsonMediumsArray);
        } else {
            container.addProperty("connexion", false);
        }
        
        return container;
    }
    
}
