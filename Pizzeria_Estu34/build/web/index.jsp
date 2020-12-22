
<%-- 
        Document   : index
        Created on : 18/12/2020, 06:34:12 PM
        Author     : PC
    --%>
    <%@page import="DTO.Tipo"%>
    <%@page import="java.util.List"%>
    <%@page import="NEGOCIO.SistemaPizzeria"%>
    <%@page contentType="text/html" pageEncoding="UTF-8"%>
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link href="https://unpkg.com/tailwindcss@^2/dist/tailwind.min.css" rel="stylesheet">
            <link rel="preconnect" href="https://fonts.gstatic.com">
            <link href="https://fonts.googleapis.com/css2?family=Anton&display=swap" rel="stylesheet"> 
            <script src="JS/pizzeriajs.js"></script>
            <title>Pizzeria</title>
        </head>
        <body>
            <% 
             SistemaPizzeria sistema = new SistemaPizzeria();
             List<Tipo> listaTipo = sistema.consultarTipo();
             String [] tama = new String [3];
             int i = 0;
             for (Tipo t : listaTipo) {
                     tama[i] = t.getDescripcion();
                     i++;
                 }
            %>
            <!-- Encabezado -->      
            <div class=" m-3 p-5 flex justify-around bg-gray-100">
                <img src="IMGS/logopizza.jpg" alt="logo-pizzeria" class="w-24 h-24 rounded-full object-cover" > 
             <div class="flex-col p-3">
                <h1 class="text-center text-2xl font-serif tracking-widest">BREI'PIZZAS</h1>
                <small>Realizado por : Breinner Farid Moreno Vera, Codigo : 1151837</small>    
             </div>    
            </div>
            <!-- Fin Encabezado --> 
            <div class=" flex items-center justify-between border rounded-md m-3 p-2">

              <div class="flex items-center">
                <p class="p-5 font-mono">Digite la cantidad de pizzas: </p>
                <input class="bg-gray-50 mx-2 ml-4 px-2 py-2 rounded-sm border border-gray-300 " type="number" id="cantidad" name="cantidad" onkeypress="if(event.keyCode < 48 || event.keyCode > 57) return false;"> 
              </div>

              <div class="flex items-center">
                  <button type="button" class="px-6 py-3 ml-4 font-bold tracking-tight rounded-sm leading-none bg-gray-400 text-lg text-center text-white" onclick= "crearSelect('<%= tama[0] %>','<%= tama[1] %>','<%= tama[2] %>')"> Crear </button>
                  <button type="button" class="ml-4 border border-gray-300 px-5 py-3 hover:bg-gray-300 rounded-sm leading-none text-sm text-center text-lg" onclick="limpiar('cantidad', 'mostrarSelects')">Cargar de nuevo</button>
              </div>

            </div>

            <form action="Controlador" method="GET" id="canPizza">
             
            <div id="mostrarSelects"></div>
            
            </form>

           <div class="py-12 justify-end w-10/12 mx-auto hidden" id="cargarOpciones">
               <button type="submit" name="cargar" value="cargar" form="canPizza" class="px-6 py-3 font-bold tracking-tight rounded-sm leading-none inter bg-gray-400 text-lg text-center text-white">Cargar Opciones</button>
           </div>

        </body>
    </html>
