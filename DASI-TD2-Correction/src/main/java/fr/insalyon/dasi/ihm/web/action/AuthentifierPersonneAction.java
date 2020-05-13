package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Personne;
import fr.insalyon.dasi.metier.service.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DASI Team
 */
public class AuthentifierPersonneAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        Service service = new Service();
        Personne personne = service.authentifierPersonne(login, password);
        

        request.setAttribute("personne", personne);
        
        // Gestion de la Session: ici, enregistrer l'ID de la personne authentifié
        HttpSession session = request.getSession();
        if (personne != null) {
            session.setAttribute("id", personne.getId());
        }
        else {
            session.removeAttribute("id");
        }
    }
    
}
