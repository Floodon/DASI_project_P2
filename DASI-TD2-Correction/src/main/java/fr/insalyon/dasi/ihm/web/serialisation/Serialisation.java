package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.modele.Personne;
import fr.insalyon.dasi.metier.modele.ProfilAstral;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DASI Team
 */
public abstract class Serialisation {
    
    public final void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject toWrite = createJson(request, response);
        write(toWrite, response);
    }
    
    protected abstract JsonObject createJson(HttpServletRequest request, HttpServletResponse response) throws IOException;
    
    private void write(JsonObject object, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gson.toJson(object, out);
        }
    }
    
    /* DateFormat souvent utilisés */
    
        protected static final SimpleDateFormat sdfDateOnly = new SimpleDateFormat("yyyy-MM-dd");
        protected static final SimpleDateFormat sdfDateHeure = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    
    /* Sérialisations souvent utilisées */
    
    protected JsonObject serializeMedium(Medium m) {
        JsonObject jsonMedium = new JsonObject();

        jsonMedium.addProperty("id", m.getId());
        jsonMedium.addProperty("denomination", m.getDenomination());
        jsonMedium.addProperty("presentation", m.getPresentation());
        jsonMedium.addProperty("type", m.getType());
        
        return jsonMedium;
    }
    
    protected JsonObject serializePersonne(Personne p) {
        JsonObject jsonPersonne = new JsonObject();
        
        jsonPersonne.addProperty("prenom", p.getPrenom());
        jsonPersonne.addProperty("nom", p.getNom());
        String genre = p.getGenre() == Personne.Genre.HOMME ? "H" : (p.getGenre() == Personne.Genre.FEMME ? "F" : "X");
        jsonPersonne.addProperty("genre", genre);
        jsonPersonne.addProperty("telephone", p.getTelephone());
        jsonPersonne.addProperty("dateNaissance", sdfDateOnly.format(p.getDateNaissance()));
        jsonPersonne.addProperty("mail", p.getMail());
        
        return jsonPersonne;
    }
    
    protected JsonObject serializeProfilAstral(ProfilAstral profil) {
        JsonObject jsonProfil = new JsonObject();
        
        jsonProfil.addProperty("signeChinois", profil.getSigneChinois());
        jsonProfil.addProperty("signeZodiaque", profil.getSigneZodiaque());
        jsonProfil.addProperty("couleur", profil.getCouleur());
        jsonProfil.addProperty("animal", profil.getAnimal());
        
        return jsonProfil;
    }
    
}
