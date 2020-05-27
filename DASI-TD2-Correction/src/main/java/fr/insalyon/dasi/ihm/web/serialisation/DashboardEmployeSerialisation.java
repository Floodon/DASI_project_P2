package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.service.Service;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DashboardEmployeSerialisation extends Serialisation{
    
    @Override
    protected JsonObject createJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Service service = new Service();
        
        Boolean connexion = (Boolean) request.getAttribute("connexion");
        
        JsonObject container = new JsonObject();

        if (connexion == null) {
            container.addProperty("connexion", false);
        } else {
            
            if (connexion) {
                Employe employe = (Employe) request.getAttribute("employe");
                
                if (employe != null) {
                    container.addProperty("connexion", connexion);
                
                    // Partie "infos personnelles"
                    JsonObject jsonPersonne = serializePersonne(employe);
                    container.add("personne", jsonPersonne);

                    // Partie "historique des consultations"
                    JsonArray jsonConsultations = new JsonArray();
                    for (Consultation c : service.listerConsultations(employe, null, null)) {
                        if (c.getState() != Consultation.ConsultationState.TERMINEE) {
                            continue;
                        }

                        JsonObject jsonCons = new JsonObject();
                        jsonCons.addProperty("dateDebut", sdfDateHeure.format(c.getDateDebut()));
                        jsonCons.addProperty("dateFin", sdfDateHeure.format(c.getDateFin()));

                        JsonObject jsonMedium = new JsonObject();
                        jsonMedium.addProperty("denomination", c.getMedium().getDenomination());
                        jsonMedium.addProperty("type", c.getMedium().getType());
                        jsonCons.add("medium", jsonMedium);
                        
                        JsonObject jsonClient = new JsonObject();
                        jsonClient.addProperty("prenom", c.getClient().getPrenom());
                        jsonClient.addProperty("nom", c.getClient().getNom());
                        jsonCons.add("client", jsonClient);
                        
                        jsonCons.addProperty("commentaire", c.getCommentaire());

                        jsonConsultations.add(jsonCons);
                    }

                    container.add("historique", jsonConsultations);
                    
                } else {
                    container.addProperty("connexion", false);
                }
            }
        }
        
        return container;
    }
    
}
