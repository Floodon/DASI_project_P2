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
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Consultation.ConsultationState;
import fr.insalyon.dasi.metier.modele.Personne.Genre;
import fr.insalyon.dasi.metier.modele.ProfilAstral;
import fr.insalyon.dasi.metier.service.Service;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public class DashboardClientSerialisation extends Serialisation{
    
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Service service = new Service();
        
        SimpleDateFormat sdfDateOnly = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfDateHeure = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        Boolean connexion = (Boolean) request.getAttribute("connexion");
        
        JsonObject container = new JsonObject();

        if (connexion == null) {
            container.addProperty("connexion", false);
        } else {
            
            if (connexion) {
                Client client = (Client) request.getAttribute("client");
                ProfilAstral profil = client != null ? client.getProfilAstral() : null;
                
                if (profil != null) {
                    container.addProperty("connexion", connexion);
                
                    // Partie "infos personnelles"
                    JsonObject jsonPersonne = new JsonObject();
                    jsonPersonne.addProperty("prenom", client.getPrenom());
                    jsonPersonne.addProperty("nom", client.getNom());
                    String genre = client.getGenre() == Genre.HOMME ? "H" : client.getGenre() == Genre.FEMME ? "F" : "X";
                    jsonPersonne.addProperty("genre", genre);
                    jsonPersonne.addProperty("telephone", client.getTelephone());
                    jsonPersonne.addProperty("dateNaissance", sdfDateOnly.format(client.getDateNaissance()));
                    jsonPersonne.addProperty("mail", client.getMail());

                    container.add("personne", jsonPersonne);

                    // Partie "profil astral"
                    JsonObject jsonProfil = new JsonObject();
                    jsonProfil.addProperty("signeChinois", profil.getSigneChinois());
                    jsonProfil.addProperty("signeZodiaque", profil.getSigneZodiaque());
                    jsonProfil.addProperty("couleur", profil.getCouleur());
                    jsonProfil.addProperty("animal", profil.getAnimal());

                    container.add("profilAstral", jsonProfil);

                    // Partie "historique des consultations"
                    JsonArray jsonConsultations = new JsonArray();
                    for (Consultation c : service.listerConsultations(null, client, null, null, null, null)) {
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
        
        write(container, response);
    }
    
}
