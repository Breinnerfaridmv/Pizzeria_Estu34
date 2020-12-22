/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import NEGOCIO.SistemaPizzeria;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PC
 */
public class Facturar extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     
         
         String msg = "<div class='flex flex-col inter mt-12 mx-5'>"
                    + "<table class='min-w-full divide-y divide-gray-200'>"
                    + "<thead>"
                    + "<tr class='bg-gray-100'>"
                    + "<th class='px-6 py-3 bg-gray-50 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider'>descripción</th>"
                    + "<th class='px-6 py-3 bg-gray-50 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider'>valor</th>"
                    + "</tr>"
                    + "</thead>"
                    + "<tbody class='bg-white divide-y divide-gray-200'>";
        
         int cantidad = Integer.parseInt(request.getParameter("cantidad"));
         String [] pizza1 = request.getParameterValues("pizza1");
         String [] pizza2 = request.getParameterValues("pizza2");
         String [] tamaño = request.getParameterValues("tamaño");
         SistemaPizzeria sistema = new SistemaPizzeria();
         double total = 0;
        for (int i = 1; i <= cantidad; i++) {
         String ch1 = "check-"+ i; 
         String ch2 = "check_"+ i;
         String [] check1 = request.getParameterValues(ch1);
         String [] check2 = request.getParameterValues(ch2);
         
        
              
         msg += sistema.facturar(i, pizza1, pizza2 , tamaño , check1, check2); 
         total += sistema.getTotal();
        }
        
         msg += "<tr class='bg-gray-100'><td class='px-6 py-4 whitespace-no-wrap'>Total:</td>"
                +"<td class='px-6 py-4 whitespace-no-wrap'>" +"$ " + total + "</td></tr>";
         msg += "</tbody></table>";
         
         
         request.setAttribute("imprimir",msg);
         request.getRequestDispatcher("JSP/Factura.jsp").forward(request, response);
        
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
