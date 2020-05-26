/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author berth
 */
public class ConsultationSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Boolean connexion = (Boolean) request.getAttribute("connexion");
        Boolean consultOK = (Boolean) request.getAttribute("consultation-ok");
        
        JsonObject container = new JsonObject();
        
        if (connexion != null) {
            container.addProperty("connexion", connexion);
            if (connexion) {
                container.addProperty("consultation-ok", consultOK);
            }
        } else {
            container.addProperty("connexion", false);
        }
        
        write(container, response);
    }
    
}
