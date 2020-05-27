/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.modele.ProfilAstral;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConsultationActuelleSerialisation extends Serialisation {

    @Override
    protected JsonObject createJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Boolean connexion = (Boolean) request.getAttribute("connexion");
        Consultation consult = (Consultation) request.getAttribute("consultation");
        
        JsonObject container = new JsonObject();
        
        if (connexion != null) {
            container.addProperty("connexion", connexion);
            if (connexion) {
                String etat = "aucune";
                if (consult != null) {
                    switch (consult.getState()) {
                        case EN_ATTENTE: etat = "en-attente"; break;
                        case DEMARREE: etat = "en-cours"; break;
                    }
                    
                    // * Construction du json de la consultation * //
                    
                    JsonObject jsonConsult = new JsonObject();

                    // Infos personnelles du client
                    Client client = consult.getClient();
                    JsonObject jsonClient = serializePersonne(client);
                    jsonConsult.add("client", jsonClient);
                    
                    // Infos sur le profil astral du client
                    ProfilAstral profil = client.getProfilAstral();
                    JsonObject jsonProfil = serializeProfilAstral(profil);
                    jsonConsult.add("profil-astral", jsonProfil);

                    // Infos sur le medium
                    Medium medium = consult.getMedium();
                    JsonObject jsonMedium = serializeMedium(medium);
                    jsonConsult.add("medium", jsonMedium);
                    
                    // Ajout de la consultation au container
                    container.add("consultation", jsonConsult);
                }
                
                container.addProperty("etat", etat);
            }
        } else {
            container.addProperty("connexion", false);
        }
        
        return container;
    }
    
}
