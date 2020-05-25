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
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Personne.Genre;
import fr.insalyon.dasi.metier.service.Service;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public class DashboardEmployeSerialisation extends Serialisation{
    
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
                Employe employe = (Employe) request.getAttribute("employe");
                
                if (employe != null) {
                    container.addProperty("connexion", connexion);
                
                    // Partie "infos personnelles"
                    JsonObject jsonPersonne = new JsonObject();
                    jsonPersonne.addProperty("prenom", employe.getPrenom());
                    jsonPersonne.addProperty("nom", employe.getNom());
                    String genre = employe.getGenre() == Genre.HOMME ? "H" : employe.getGenre() == Genre.FEMME ? "F" : "X";
                    jsonPersonne.addProperty("genre", genre);
                    jsonPersonne.addProperty("telephone", employe.getTelephone());
                    jsonPersonne.addProperty("dateNaissance", sdfDateOnly.format(employe.getDateNaissance()));
                    jsonPersonne.addProperty("mail", employe.getMail());

                    container.add("personne", jsonPersonne);

                    // Partie "historique des consultations"
                    JsonArray jsonConsultations = new JsonArray();
                    for (Consultation c : service.listerConsultations(employe, null, null, null, null, null)) {
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
        
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gson.toJson(container, out);
        }
    }
    
}
