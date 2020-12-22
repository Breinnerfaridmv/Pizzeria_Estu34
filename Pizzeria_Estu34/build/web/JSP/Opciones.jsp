<%-- 
    Document   : opciones.jsp
    Created on : 18/12/2020, 09:51:57 PM
    Author     : PC
--%>

<%@page import="DAO.SaborJpaController"%>
<%@page import="DTO.Ingredienteadicional"%>
<%@page import="java.util.ArrayList"%>
<%@page import="DTO.Sabor"%>
<%@page import="java.util.List"%>
<%@page import="NEGOCIO.SistemaPizzeria"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://unpkg.com/tailwindcss@^2/dist/tailwind.min.css" rel="stylesheet">
        <script src="JS/pizzeriajs.js"></script>
        <title>Opciones</title>
    </head>
    <body>
         
            <!-- Encabezado -->      
            <div class=" m-3 p-5 flex justify-around bg-gray-100">
                <img src="IMGS/logopizza.jpg" alt="logo-pizzeria" class="w-24 h-24 rounded-full object-cover" > 
             <div class="flex-col p-3">
                <h1 class="text-center text-2xl font-serif tracking-widest">BREI'PIZZAS</h1>
                <small>Realizado por : Breinner Farid Moreno Vera, Codigo : 1151837</small>    
             </div>    
            </div>
            <!-- Fin Encabezado --> 
            <% 
              String [] tam = (String[])request.getAttribute("tama");
              SistemaPizzeria sistema = new SistemaPizzeria();
              List<Sabor> listaSabor = sistema.consultarSabor();
              List<Ingredienteadicional> listaAdicional = sistema.cansultarAdicional();
              ArrayList<String> sabores = new ArrayList<>();
              ArrayList<String> imgs = new ArrayList<>();
              for (Sabor s : listaSabor) {
                   sabores.add(s.getDescripcion());
                  }
              ArrayList<String> adicional = new ArrayList<>();
              for(Ingredienteadicional ing : listaAdicional){
                 adicional.add(ing.getDescripcion());
              }
              String [] arr = new String [sabores.size()];
              arr = sabores.toArray(arr);
            %>
            
    <form action="Facturar" method="GET" id="form">
           <% for(int i = 1 ; i <= tam.length ;i++){ %> 
            <div class='flex flex-col inter py-4 px-6 w-10/12 mx-auto border border-gray-300 rounded-lg mt-12 justify-start'>
            <div class='flex items-center'>
            <p> Escoja sabores para la pizza <%= i %> (puede escoger uno o dos):</p>
            <div class='relative ml-4'>  
                <select name='pizza1' class='py-2 border border-gray-300 rounded-md appearance-none pr-8 pl-3' onclick='setName(<%= i %>, this.value), changeImageURL(<%= i %>,this.value)' onchange='listener(<%= i %>, this.selectedIndex)'> 
            <%= sistema.crearSabores(arr) %>
            </select>
            <div class='pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700'>
            <svg class='fill-current h-4 w-4' xmlns='http://www.w3.org/2000/svg' viewBox='0 0 20 20'><path d='M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z'/></svg>
            </div></div>
            <div class='relative ml-4'>
            <select name='pizza2' id='<%= i %>' onclick='optionsValue(this.id), setSecondName(<%= i %>, this.value), changeSecondURL(<%= i %>, this.value)' class='py-2 border border-gray-300 rounded-md appearance-none pr-8 pl-3'>
            <%= sistema.crearSabores2(arr) %>
            </select>
            <div class='pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700'>
            <svg class='fill-current h-4 w-4' xmlns='http://www.w3.org/2000/svg' viewBox='0 0 20 20'><path d='M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z'/></svg>
            </div></div></div><div class='flex items-center'><p>Tamaño de la pizza: </p> <input class='ml-2 focus:outline-none' type='text' readonly='readonly' value='<%= tam[i-1] %>' name='tamaño'/></div>
            <p class='mt-2' id='p-<%= i %>'>Ingredientes adicionales</p>
            <div class='flex items-center w-1/2'>
            <div class='flex items-center w-1/4'>
            <input type='checkbox' name='check-<%= i %>' value='<%= adicional.get(0)%>'>
            <label for='tocineta' class='ml-2'> <%= adicional.get(0) %>   </label><br>
            </div>
            <div class='flex items-center w-1/4'>
            <input type='checkbox' name='check-<%= i %>' value='<%= adicional.get(1)%>'>
            <label for='salami' class='ml-2'> <%= adicional.get(1) %>  </label><br>
            </div>
            <div class='flex items-center w-1/4'>
            <input type='checkbox' name='check-<%= i %>' value='<%= adicional.get(2)%>'>
            <label for='oregano' class='ml-2'> <%= adicional.get(2) %>  </label><br>
            </div>
            <div class='flex items-center w-1/4'>
            <input type='checkbox' name='check-<%= i %>' value='<%= adicional.get(3)%>'>
            <label for='salchicha' class='ml-2'><%= adicional.get(3) %> </label><br>
            </div>
            </div>
            <p class='mt-2' id='p_<%= i %>'>Ingredientes adicionales</p>
            <div class='flex items-center w-1/2'>
            <div class='flex items-center w-1/4'>
            <input type='checkbox' name='check_<%= i %>' value='<%= adicional.get(0)%>' disabled>
            <label for='tocineta' class='ml-2'> <%= adicional.get(0) %> </label><br>
            </div>
            <div class='flex items-center w-1/4'>
            <input type='checkbox' name='check_<%= i %>' value='<%= adicional.get(1)%>' disabled>
            <label for='salami' class='ml-2'> <%= adicional.get(1) %> </label><br>
            </div>
            <div class='flex items-center w-1/4'>
            <input type='checkbox' name='check_<%= i %>' value='<%= adicional.get(2)%>' disabled>
            <label for='oregano' class='ml-2'> <%= adicional.get(2) %> </label><br>
            </div>
            <div class='flex items-center w-1/4'>
            <input type='checkbox' name='check_<%= i %>' value='<%= adicional.get(3)%>' disabled>
            <label for='salchicha' class='ml-2'> <%= adicional.get(3) %> </label><br>
            </div>
            </div>
            <input type='hidden' name='cantidad' value='<%= tam.length %>'>
            <div class='flex items-center mt-4'><div class='w-1/3'><img class='object-cover object-center rounded-md h-64 hidden' src='http://madarme.co/persistencia/pizza/napolitana.jpg' id='img-<%= i %>'></div>
            <div class='w-1/3 ml-8'><img class='object-cover object-center rounded-md h-64 hidden' src='' id='img_<%= i %>'></div></div>
            </div>   
            <% } %>
            <div id="pizza_data"></div>
    </form>

    <div class="flex justify-center inter py-12">
        <button type="submit" class="px-6 py-3 ml-4 font-bold tracking-tight rounded-sm leading-none focus:outline-none bg-gray-400 text-lg text-center text-white " form="form">Calcular factura</button>
    </div>

    </body>
</html>
