/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Personne;
import fr.insalyon.dasi.metier.service.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ConsultationActuelleAction extends Action{
    
    @Override
    public void executer(HttpServletRequest request) {
        Service service = new Service();
        
        // Récuperer client
        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("id");
        Personne personne;
        if (id != null) {
            personne = service.rechercherEmployeParId(id);
            personne = (personne != null) ? personne : service.rechercherClientParId(id);
        } else {
            personne = null;
        }
        
        
        if (personne == null) {
            request.setAttribute("connexion", false);
        } else {
            request.setAttribute("connexion", true);
            
            Consultation consult = service.obtenirConsultationEnCours(personne);
            request.setAttribute("consultation", consult);
        }
    }
}
