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
public class MediumSerialisation extends Serialisation {
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Medium> liste_mediums = (List<Medium>)request.getAttribute("liste_mediums");
        
        JsonArray mediums = new JsonArray();
        
        if (liste_mediums != null) {
            for (Medium m : liste_mediums) {
                JsonObject jsonMedium = new JsonObject();

                jsonMedium.addProperty("id", m.getId());
                jsonMedium.addProperty("denomination", m.getDenomination());
                jsonMedium.addProperty("genre", m.getGenre().toString());
                jsonMedium.addProperty("presentation", m.getPresentation());

                mediums.add(jsonMedium);
            }
        }

        JsonObject super_container = new JsonObject();
        super_container.add("liste_mediums", mediums);
        
        Boolean connexion = (liste_mediums != null);
        super_container.addProperty("connexion", connexion);
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(super_container, out);
        out.close();
    }
}
