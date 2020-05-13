/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.ihm.web.action.Action;
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.service.Service;
import java.util.List;


import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author MrFlo
 */
public class ListeMediumAction extends Action{
    
    @Override
    public void executer(HttpServletRequest request) {

       Service service = new Service();
       List<Medium> mediums = service.listerMediums();
       request.setAttribute("liste_mediums",mediums);
       
    }
}
