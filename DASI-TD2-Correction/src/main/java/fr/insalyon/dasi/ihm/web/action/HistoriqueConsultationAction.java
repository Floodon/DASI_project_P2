package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.ihm.web.serialisation.Serialisation;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.service.Service;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HistoriqueConsultationAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        Service service = new Service();
            
        // Récuperer employé
        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("id");
        Employe employe = (id == null) ? null : service.rechercherEmployeParId(id);
        
        // Vérifier l'état de connexion
        Boolean connexion = employe != null;
        request.setAttribute("connexion", connexion);
        
        // Coeur du service (seulement si connecté)
        if (connexion) {
            String clientName = request.getParameter("clientName");
            String mediumName = request.getParameter("mediumName");
            String employeName = request.getParameter("employeName");
            String date = request.getParameter("date");
            
            List<Consultation> historique = null;
            try {
                Date dateBegin = null;
                if (!"".equals(date)) {
                    dateBegin = Serialisation.sdfDateOnly.parse(date);
                }
                historique = service.listerConsultations(employeName, clientName, mediumName, null, dateBegin, null);
            } catch (ParseException ex) {
                Logger.getLogger(HistoriqueConsultationAction.class.getName()).log(Level.SEVERE, null, ex);
                historique = new ArrayList<>();
            }
                
            request.setAttribute("historique", historique);
        }
    }
    
}
