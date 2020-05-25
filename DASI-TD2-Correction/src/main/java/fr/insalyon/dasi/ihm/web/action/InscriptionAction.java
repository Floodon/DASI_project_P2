/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Personne.Genre;
import fr.insalyon.dasi.metier.service.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author berth
 */
public class InscriptionAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        Service service = new Service();
        
        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String telephone = request.getParameter("telephone");
        String dateNaissance = request.getParameter("dateNaissance");
        String mail = request.getParameter("mail");
        String adresse = request.getParameter("adresse");
        String motDePasse = request.getParameter("motDePasse");
        
        Genre genre;
        switch (request.getParameter("genre")) {
            case "H":
                genre = Genre.HOMME;
                break;
            case "F":
                genre = Genre.FEMME;
                break;
            default:
                genre = Genre.AUTRE;
                break;
        }
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Client aInscrire = new Client(mail, motDePasse, nom, prenom, genre, adresse, telephone, sdf.parse(dateNaissance));
            Long id = service.inscrireClient(aInscrire);
            
            if (id == null) {
                throw new IllegalStateException("Incription du client raté");
            }
            
            HttpSession session = request.getSession();
            session.setAttribute("id", id);
            
            request.setAttribute("erreur", false);
        } catch (ParseException|IllegalStateException ex) {
            Logger.getLogger(InscriptionAction.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("erreur", true);
        }
    }
    
}
