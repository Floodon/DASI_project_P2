/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Client;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author MrFlo
 */
public class ListeClientsSerialisation extends Serialisation {
    
    @Override
    protected JsonObject createJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Boolean connexion = (Boolean) request.getAttribute("connexion");
        List<Client> listeClients = (List<Client>)request.getAttribute("clients");
        
        JsonObject container = new JsonObject();
        JsonArray clients = new JsonArray();
        
        if (connexion != null && connexion && listeClients != null) {
            container.addProperty("connexion", true);
            for (Client c : listeClients) {
                JsonObject jsonMedium = serializePersonne(c);
                clients.add(jsonMedium);
            }
        } else {
            container.addProperty("connexion", false);
        }
        
        container.add("mediums", clients);
        
        return container;
    }
}
