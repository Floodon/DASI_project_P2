package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.service.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class TerminerConsultationAction extends Action{
    
    @Override
    public void executer(HttpServletRequest request) {
        Service service = new Service();
        
        // Récuperer client
        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("id");
        Employe employe = (id == null) ? null : service.rechercherEmployeParId(id);
        
        // Récupérer commentaire
        String commentaire = request.getParameter("commentaire");
        
        if (employe == null) {
            request.setAttribute("connexion", false);
        } else {
            request.setAttribute("connexion", true);
            
            Consultation consult = service.obtenirConsultationEnCours(employe);
            boolean consultOK = (consult == null || commentaire == null) ? false : service.terminerConsultation(consult, commentaire);
            request.setAttribute("consultation-ok", consultOK);
        }
    }
}
