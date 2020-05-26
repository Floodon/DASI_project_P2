/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.service.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DemanderConsultationAction extends Action{
    
    @Override
    public void executer(HttpServletRequest request) {
        Service service = new Service();
        
        // Récuperer client
        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("id");
        Client client = (id == null) ? null : service.rechercherClientParId(id);
        
        // Recuperer medium
        Long idMedium = (Long) request.getAttribute("medium");
        Medium medium = service.rechercherMediumParId(idMedium);
        
        if (client == null) {
            request.setAttribute("connexion", false);
        } else {
            request.setAttribute("connexion", true);
            
            Consultation consult = (medium == null) ? null : service.demanderConsultation(client, medium);
            // La consultation a été créée si consult ne vaut pas null. On prévient le client.
            request.setAttribute("consultation-ok", consult != null); 
        }
    }
}
