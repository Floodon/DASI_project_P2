package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Consultation.ConsultationState;
import fr.insalyon.dasi.metier.modele.ProfilAstral;
import fr.insalyon.dasi.metier.service.Service;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DashboardClientSerialisation extends Serialisation{
    
    @Override
    protected JsonObject createJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Service service = new Service();
        
        Boolean connexion = (Boolean) request.getAttribute("connexion");
        
        JsonObject container = new JsonObject();

        if (connexion == null) {
            container.addProperty("connexion", false);
        } else {
            
            if (connexion) {
                Client client = (Client) request.getAttribute("client");
                ProfilAstral profil = client == null ? null : client.getProfilAstral();
                
                if (profil != null) {
                    container.addProperty("connexion", connexion);
                
                    // Partie "infos personnelles"
                    JsonObject jsonPersonne = serializePersonne(client);
                    container.add("personne", jsonPersonne);

                    // Partie "profil astral"
                    JsonObject jsonProfil = serializeProfilAstral(profil);
                    container.add("profilAstral", jsonProfil);

                    // Partie "historique des consultations"
                    JsonArray jsonConsultations = new JsonArray();
                    for (Consultation c : service.listerConsultations(null, client, null)) {
                        if (c.getState() != ConsultationState.TERMINEE) {
                            continue;
                        }

                        JsonObject jsonCons = new JsonObject();
                        jsonCons.addProperty("dateDebut", sdfDateHeure.format(c.getDateDebut()));
                        jsonCons.addProperty("dateFin", sdfDateHeure.format(c.getDateFin()));

                        JsonObject jsonMedium = new JsonObject();
                        jsonMedium.addProperty("denomination", c.getMedium().getDenomination());
                        jsonMedium.addProperty("type", c.getMedium().getType());

                        jsonCons.add("medium", jsonMedium);

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
