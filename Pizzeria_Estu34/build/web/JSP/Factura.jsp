<%-- 
    Document   : factura
    Created on : 20/12/2020, 05:11:51 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title> Detalles de Facturacion </title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://unpkg.com/tailwindcss@^2/dist/tailwind.min.css" rel="stylesheet">
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
            
            <% String html = (String)request.getAttribute("imprimir");%>
            <%= html %>
    </body>
</html>
