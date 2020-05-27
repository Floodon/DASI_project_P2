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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author berth
 */
public class Top5MediumsSerialisation extends Serialisation {

    @Override
    protected JsonObject createJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Medium> topMediums = (List<Medium>) request.getAttribute("top-mediums");
        
        JsonObject container = new JsonObject();
        JsonArray jsonMediumsArray = new JsonArray();
        
        if (topMediums != null) {
            topMediums.stream().map(this::serializeMedium).forEach(jsonMediumsArray::add);
        }
        
        return container;
    }
    
}
