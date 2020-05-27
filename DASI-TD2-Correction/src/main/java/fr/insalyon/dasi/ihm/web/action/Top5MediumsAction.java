package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.service.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Top5MediumsAction extends Action{
    
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
            List<Medium> topMediums = service.topMedium(5);
            Map<Medium, Integer> topMediumsConsult = 
                    topMediums.stream().collect(Collectors.toMap(m -> m, service::nbrConsultations));
            request.setAttribute("top-mediums", topMediums);
            request.setAttribute("top-mediums-map", topMediumsConsult);
        }
    }
}
