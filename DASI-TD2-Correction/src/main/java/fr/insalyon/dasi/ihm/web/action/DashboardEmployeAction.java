/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.service.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author berth
 */
public class DashboardEmployeAction extends Action {

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
        
        Employe employe = service.rechercherEmployeParId(id);
        
        request.setAttribute("employe", employe);
    }
    
}
