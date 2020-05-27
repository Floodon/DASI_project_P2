package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.service.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DashboardClientAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        Service service = new Service();
        
        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("id");
        
        if (id == null) {
            request.setAttribute("connexion", false);
            return;
        } else {
            request.setAttribute("connexion", true);
        }
        
        Client client = service.rechercherClientParId(id);
        
        request.setAttribute("client", client);
    }
    
}
