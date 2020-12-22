/*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
 */
package NEGOCIO;

import DAO.Conexion;
import DAO.IngredienteadicionalJpaController;
import DAO.SaborJpaController;
import DAO.TipoJpaController;
import DTO.Ingredienteadicional;
import DTO.Sabor;
import DTO.Tipo;
import java.util.List;

/**
 *
 * @author PC
 */
public class SistemaPizzeria {

    double total;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Tipo> consultarTipo() {
        Conexion con = Conexion.getConexion();
        TipoJpaController tipoDAO = new TipoJpaController(con.getBd());
        return tipoDAO.findTipoEntities();
    }

    public List<Sabor> consultarSabor() {
        Conexion con = Conexion.getConexion();
        SaborJpaController saborDAO = new SaborJpaController(con.getBd());
        return saborDAO.findSaborEntities();
    }

    public List<Ingredienteadicional> cansultarAdicional() {
        Conexion con = Conexion.getConexion();
        IngredienteadicionalJpaController adicionalDAO = new IngredienteadicionalJpaController(con.getBd());
        return adicionalDAO.findIngredienteadicionalEntities();
    }

    public String crearSabores2(String[] sabores) {
        String msg = "<option value='Ninguno' selected>Ninguno</option>";
        for (int i = 1; i < sabores.length; i++) {
            msg += "<option value='" + sabores[i] + "'>" + sabores[i] + "</option>";
        }
        return msg;

    }

    public String crearSabores(String[] sabores) {
        String msg = "";
        for (int i = 0; i < sabores.length; i++) {
            msg += "<option value='" + sabores[i] + "'>" + sabores[i] + "</option>";
        }
        return msg;
    }

    public String urlImg(String[] sabores, String[] imgs, String value) {
        for (int j = 0; j < sabores.length; j++) {
            if (sabores[j].equalsIgnoreCase(value)) {
                return imgs[j];
            }
        }
        return null;
    }
    
    public String getUrlImagen(String value){
     String url ="";
     List<Sabor> listSabores = consultarSabor();
        for (Sabor s : listSabores) {
          if(s.getDescripcion().equals(value)){
             url = s.getImagen();
            }
        }
     return url;
    }

    public String facturar(int i, String[] pizza1, String[] pizza2, String[] tamaños, String[] check1, String[] check2) {

        List<Ingredienteadicional> listaIngredientes = cansultarAdicional();
        double total = 0;
        String msgHtml = "";
      
        String tamaño = tamaños[i - 1];
        String adicional1 = "Adicional-";
        String adicional2 = "Adicional-";
        String msg = "Pizza " + tamaño;
        double tmp1 = 0;
        double tmp2 = 0;
        double mayor = 0;

        String sabor1 = pizza1[i - 1];
        String sabor2 = pizza2[i - 1];

        if (sabor2.equals("Ninguno")) {
            switch (sabor1) {
                case "Napolitana": {
                    msg += " Napolitana";
                    adicional1 += "Napolitana";
                    if (tamaño.equals("Pequeña")) {
                        tmp1 = 16666.666;
                    }
                    if (tamaño.equals("Mediano")) {
                        tmp1 = 25000;
                    }
                    if (tamaño.equals("Grande")) {
                        tmp1 = 50000;
                    }
                    break;
                }

                case "Mexicana": {
                    msg += " Mexicana";
                    adicional1 += "Mexicana";
                    if (tamaño.equals("Pequeña")) {
                        tmp1 = 16000;
                    }
                    if (tamaño.equals("Mediano")) {
                        tmp1 = 24000;
                    }
                    if (tamaño.equals("Grande")) {
                        tmp1 = 48000;
                    }
                    break;
                }

                case "Hawayana": {
                    msg += " Hawayana";
                    adicional1 += "Hawayana";
                    if (tamaño.equals("Pequeña")) {
                        tmp1 = 14000;
                    }
                    if (tamaño.equals("Mediano")) {
                        tmp1 = 21000;
                    }
                    if (tamaño.equals("Grande")) {
                        tmp1 = 42000;
                    }
                    break;
                }

                case "Vegetariana": {
                    msg += " Vegetariana";
                    adicional1 += "Vegetariana";
                    if (tamaño.equals("Pequeña")) {
                        tmp1 = 10000;
                    }
                    if (tamaño.equals("Mediano")) {
                        tmp1 = 15000;
                    }
                    if (tamaño.equals("Grande")) {
                        tmp1 = 30000;
                    }
                    break;
                }

                default:
                    break;
            }
        } else {
            msg += " Mitad";
            switch (sabor1) {
                case "Napolitana": {
                    msg += " Napolitana ";
                    adicional1 += "Napolitana";
                    if (tamaño.equals("Pequeña")) {
                        tmp1 = 16666.666;
                    }
                    if (tamaño.equals("Mediano")) {
                        tmp1 = 25000;
                    }
                    if (tamaño.equals("Grande")) {
                        tmp1 = 50000;
                    }
                    break;
                }

                case "Mexicana": {
                    msg += " Mexicana ";
                    adicional1 += "Mexicana";
                    if (tamaño.equals("Pequeña")) {
                        tmp1 = 16000;
                    }
                    if (tamaño.equals("Mediano")) {
                        tmp1 = 24000;
                    }
                    if (tamaño.equals("Grande")) {
                        tmp1 = 48000;
                    }
                    break;
                }

                case "Hawayana": {
                    msg += " Hawayana ";
                    adicional1 += "Hawayana";
                    if (tamaño.equals("Pequeña")) {
                        tmp1 = 14000;
                    }
                    if (tamaño.equals("Mediano")) {
                        tmp1 = 21000;
                    }
                    if (tamaño.equals("Grande")) {
                        tmp1 = 42000;
                    }
                    break;
                }

                case "Vegetariana": {
                    msg += " Vegetariana ";
                    adicional1 += "Vegetariana";
                    if (tamaño.equals("Pequeña")) {
                        tmp1 = 10000;
                    }
                    if (tamaño.equals("Mediano")) {
                        tmp1 = 15000;
                    }
                    if (tamaño.equals("Grande")) {
                        tmp1 = 30000;
                    }
                    break;
                }

                default:
                    break;
            }

            msg += "y Mitad ";

            switch (sabor2) {
                case "Napolitana": {
                    msg += "Napolitana ";
                    adicional2 += "Napolitana";
                    if (tamaño.equals("Pequeña")) {
                        tmp2 = 16666.666;
                    }
                    if (tamaño.equals("Mediano")) {
                        tmp2 = 25000;
                    }
                    if (tamaño.equals("Grande")) {
                        tmp2 = 50000;
                    }
                    break;
                }

                case "Mexicana": {
                    msg += "Mexicana ";
                    adicional2 += "Mexicana";
                    if (tamaño.equals("Pequeña")) {
                        tmp2 = 16000;
                    }
                    if (tamaño.equals("Mediano")) {
                        tmp2 = 24000;
                    }
                    if (tamaño.equals("Grande")) {
                        tmp2 = 48000;
                    }
                    break;
                }

                case "Hawayana": {
                    msg += "Hawayana ";
                    adicional2 += "Hawayana";
                    if (tamaño.equals("Pequeña")) {
                        tmp2 = 14000;
                    }
                    if (tamaño.equals("Mediano")) {
                        tmp2 = 21000;
                    }
                    if (tamaño.equals("Grande")) {
                        tmp2 = 42000;
                    }
                    break;
                }

                case "Vegetariana": {
                    msg += "Vegetariana ";
                    adicional2 += "Vegetariana";
                    if (tamaño.equals("Pequeña")) {
                        tmp2 = 10000;
                    }
                    if (tamaño.equals("Mediano")) {
                        tmp2 = 15000;
                    }
                    if (tamaño.equals("Grande")) {
                        tmp2 = 30000;
                    }
                    break;
                }

                default:
                    break;
            }
        }

        msgHtml += "<tr><td class='px-6 py-4 whitespace-no-wrap'>" + msg + "</td>";

        if (tmp1 >= tmp2) {
            total += tmp1;
            mayor = tmp1;
        } else {
            total += tmp2;
            mayor = tmp2;
        }

        msgHtml += "<td class='px-6 py-4 whitespace-no-wrap'>" + "$ " + mayor + "</td></tr>";

     
        if(check1 != null){
        if (check1.length > 0) {
            for (int x = 0; x < check1.length; x++) {
                if (check1[x] != null) {
                    msgHtml += "<tr><td class='px-6 py-4 whitespace-no-wrap'>" + adicional1 + "-" + check1[x] + "</td>";
                    for (Ingredienteadicional ingrediente : listaIngredientes) {
                        if (check1[x].equals(ingrediente.getDescripcion())) {
                            System.out.println("ENTRO AL IF 1 ");
                            tmp1 = ingrediente.getValor();
                            msgHtml += "<td class='px-6 py-4 whitespace-no-wrap'>" + "$ " + tmp1 + "</td></tr>";
                            total += tmp1;
                        }
                    }
                }
            }
        }
    }
        
        if (check2 != null) {
            if (check2.length > 0) {
                for (int j = 0; j < check2.length; j++) {
                    if (check2[j] != null) {
                        msgHtml += "<tr><td class='px-6 py-4 whitespace-no-wrap'>" + adicional2 + "-" + check2[j] + "</td>";
                        for (Ingredienteadicional ingrediente : listaIngredientes) {
                            if (check2[j].equals(ingrediente.getDescripcion())) {
                                System.out.println("ENTRO AL IF 2 ");
                                tmp2 = ingrediente.getValor();
                                msgHtml += "<td class='px-6 py-4 whitespace-no-wrap'>" + "$ " + tmp2 + "</td></tr>";
                                total += tmp2;
                            }
                        }
                    }
                }
            }
        }

        setTotal(total);

        return msgHtml;

    }
    
  
}
