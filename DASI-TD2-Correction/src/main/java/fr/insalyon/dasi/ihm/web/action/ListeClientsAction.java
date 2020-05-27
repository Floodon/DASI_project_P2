/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.service.Service;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ListeClientsAction extends Action{
    
    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("id");
        
        if (id == null) {
            request.setAttribute("connexion", false);
            return;
        } else {
            request.setAttribute("connexion", true);
        }
        
        Service service = new Service();
        List<Client> mediums = service.listerClients();
        request.setAttribute("clients",mediums);
       
    }
}
