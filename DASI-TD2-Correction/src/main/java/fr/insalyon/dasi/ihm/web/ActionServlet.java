package fr.insalyon.dasi.ihm.web;

import fr.insalyon.dasi.dao.JpaUtil;
import fr.insalyon.dasi.ihm.web.action.*;
import fr.insalyon.dasi.ihm.web.serialisation.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        JpaUtil.init();
    }

    @Override
    public void destroy() {
        JpaUtil.destroy();
        super.destroy();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        request.setCharacterEncoding("UTF-8");

        String todo = request.getParameter("todo");

        Action action = null;
        Serialisation serialisation = null;

        if (todo != null) {
            switch (todo) {
                case "connecter":
                    action = new AuthentifierPersonneAction();
                    serialisation = new AuthentificationSerialisation();
                    break;
                case "inscription":
                    action = new InscriptionAction();
                    serialisation = new InscriptionSerialisation();
                    break;
                case "dashboard-client":
                    action = new DashboardClientAction();
                    serialisation = new DashboardClientSerialisation();
                    break;
                case "dashboard-employe":
                    action = new DashboardEmployeAction();
                    serialisation = new DashboardEmployeSerialisation();
                    break;
                case "liste-mediums":
                    action = new ListeMediumsAction();
                    serialisation = new ListeMediumsSerialisation();
                    break;
                case "liste-employes":
                    action = new ListeEmployesAction();
                    serialisation = new ListeEmployesSerialisation();
                    break;
                case "liste-clients":
                    action = new ListeClientsAction();
                    serialisation = new ListeClientsSerialisation();
                    break;
                case "top-mediums":
                    action = new Top5MediumsAction();
                    serialisation = new Top5MediumsSerialisation();
                    break;
                case "recherche":
                    action = new HistoriqueConsultationAction();
                    serialisation = new HistoriqueConsultationSerialisation();
                    break;
                case "demande-consultation":
                    action = new DemanderConsultationAction();
                    serialisation = new ConsultationSerialisation();
                    break;
                case "accepter-consultation":
                    action = new AccepterConsultationAction();
                    serialisation = new ConsultationSerialisation();
                    break;
                case "terminer-consultation":
                    action = new TerminerConsultationAction();
                    serialisation = new ConsultationSerialisation();
                    break;
                case "consultation-actuelle":
                    action = new ConsultationActuelleAction();
                    serialisation = new ConsultationActuelleSerialisation();
                    break;
                case "generer-idee":
                    action = new GenererIdeeAction();
                    serialisation = new GenererIdeeSerialisation();
                    break;
                case "deconnexion":
                    action = new DeconnexionAction();
                    serialisation = new AucuneSerialisation();
                    break;
                default:
                    action = null;
                    break;
            }
        }
        
        if (action != null) {
            action.executer(request);
            serialisation.serialiser(request, response);
        }
        else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erreur dans les paramètres de la requête");
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
