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
import fr.insalyon.dasi.metier.modele.Personne;
import fr.insalyon.dasi.metier.modele.ProfilAstral;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConsultationActuelleSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SimpleDateFormat sdfDateSeule = new SimpleDateFormat("yyyy-MM-dd");
        
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
                    JsonObject jsonClient = new JsonObject();
                    jsonClient.addProperty("prenom", client.getPrenom());
                    jsonClient.addProperty("nom", client.getNom());
                    String genre = client.getGenre() == Personne.Genre.HOMME ? "H" : client.getGenre() == Personne.Genre.FEMME ? "F" : "X";
                    jsonClient.addProperty("genre", genre);
                    jsonClient.addProperty("telephone", client.getTelephone());
                    jsonClient.addProperty("dateNaissance", sdfDateSeule.format(client.getDateNaissance()));
                    jsonClient.addProperty("mail", client.getMail());
                    
                    jsonConsult.add("client", jsonClient);
                    
                    // Infos sur le profil astral du client
                    ProfilAstral profil = client.getProfilAstral();
                    JsonObject jsonProfil = new JsonObject();
                    jsonProfil.addProperty("signeChinois", profil.getSigneChinois());
                    jsonProfil.addProperty("signeZodiaque", profil.getSigneZodiaque());
                    jsonProfil.addProperty("couleur", profil.getCouleur());
                    jsonProfil.addProperty("animal", profil.getAnimal());
                    
                    jsonConsult.add("profil-astral", jsonProfil);

                    // Infos sur le medium
                    Medium medium = consult.getMedium();
                    JsonObject jsonMedium = new JsonObject();
                    jsonMedium.addProperty("id", medium.getId());
                    jsonMedium.addProperty("denomination", medium.getDenomination());
                    jsonMedium.addProperty("type", medium.getType());
                    jsonMedium.addProperty("presentation", medium.getPresentation());
                    
                    jsonConsult.add("medium", jsonMedium);
                    
                    // Ajout de la consultation au container
                    container.add("consultation", jsonConsult);
                }
                
                container.addProperty("etat", etat);
            }
        } else {
            container.addProperty("connexion", false);
        }
        
        write(container, response);
    }
    
}
