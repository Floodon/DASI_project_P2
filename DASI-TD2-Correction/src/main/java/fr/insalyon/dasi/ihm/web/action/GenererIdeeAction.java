/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.service.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GenererIdeeAction extends Action{
    
    @Override
    public void executer(HttpServletRequest request) {
        Service service = new Service();
        
        // Récuperer employé
        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("id");
        Employe employe = (id == null) ? null : service.rechercherEmployeParId(id);
        
        String amour = request.getParameter("amour");
        String sante = request.getParameter("sante");
        String travail = request.getParameter("travail");
        
        if (employe == null) {
            request.setAttribute("connexion", false);
        } else {
            request.setAttribute("connexion", true);
            
            Consultation consult = service.obtenirConsultationEnCours(employe);
            Client client = consult == null ? null : consult.getClient();
            request.setAttribute("client", client);
            request.setAttribute("amour", amour == null ? null : Integer.parseInt(amour));
            request.setAttribute("sante", sante == null ? null : Integer.parseInt(sante));
            request.setAttribute("travail", travail == null ? null : Integer.parseInt(travail));
        }
    }
}
