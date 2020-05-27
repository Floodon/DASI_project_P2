/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Medium;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author MrFlo
 */
public class ListeEmployesSerialisation extends Serialisation {
    
    @Override
    protected JsonObject createJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Boolean connexion = (Boolean) request.getAttribute("connexion");
        List<Employe> listeEmployes = (List<Employe>)request.getAttribute("employes");
        
        JsonObject container = new JsonObject();
        JsonArray employes = new JsonArray();
        
        if (connexion != null && connexion && listeEmployes != null) {
            container.addProperty("connexion", true);
            for (Employe e : listeEmployes) {
                JsonObject jsonMedium = serializePersonne(e);
                employes.add(jsonMedium);
            }
        } else {
            container.addProperty("connexion", false);
        }
        
        container.add("mediums", employes);
        
        return container;
    }
}
