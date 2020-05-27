/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Consultation;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author berth
 */
public class HistoriqueConsultationSerialisation extends Serialisation {

    @Override
    protected JsonObject createJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Boolean connexion = (Boolean) request.getAttribute("connexion");
        List<Consultation> historique = (List<Consultation>) request.getAttribute("historique");
        
        JsonObject container = new JsonObject();
        
        if (connexion != null && connexion) {
            container.addProperty("connexion", true);
            
            JsonArray jsonHistorique = new JsonArray();
            if (historique != null) {
                historique.stream().map(c -> {
                    JsonObject jsonCons = new JsonObject();
                    jsonCons.addProperty("debut", sdfDateHeure.format(c.getDateDebut()));
                    jsonCons.addProperty("fin", sdfDateHeure.format(c.getDateFin()));
                    jsonCons.addProperty("employe", c.getEmploye().getNom() + " " + c.getEmploye().getPrenom());
                    jsonCons.addProperty("client", c.getClient().getNom() + " " + c.getClient().getPrenom());
                    jsonCons.addProperty("medium", c.getMedium().getDenomination() + " (" + c.getMedium().getType() + ")");
                    jsonCons.addProperty("commentaire", c.getCommentaire());
                    return jsonCons;
                }).forEach(jsonHistorique::add);
            }
            container.add("historique", jsonHistorique);
        } else {
            container.addProperty("connexion", false);
        }
        
        return container;
    }
    
}
