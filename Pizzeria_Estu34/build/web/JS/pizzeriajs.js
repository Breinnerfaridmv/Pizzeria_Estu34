
function limpiar(attr1, attr2){
    document.getElementById(attr1).value = '';
    document.getElementById(attr2).innerHTML = '';
    remplazar('cargarOpciones', 'py-12 justify-end py-4 w-10/12 mx-auto hidden'); // Esconder botón
}

function crearSelect(grande,mediano,peque){
    var cantidad = document.getElementById("cantidad").value;
    var result = "";
    var component = document.getElementById("mostrarSelects");

    for(var i = 1; i <= cantidad; i++) {
        result += "<div class='flex items-center inter py-4 px-6 mt-12 w-10/12 border border-gray-300 rounded-lg mx-auto'>"
        + "<p>Tamaño de la pizza " + i + " :</p>"
        + "<div class='relative ml-4'>"
        + "<select name='pizza' class='py-2 border border-gray-300 rounded-md appearance-none pr-8 pl-3'>"
        + "<option value='" + peque + "'selected>" + peque + "</option>"
        + "<option value='" + mediano + "'>" + mediano + "</option>"
        + "<option value='"+ grande + "'>" + grande + "</option>"
        + "</select>"
        + "<div class='pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700'>"
        + "<svg class='fill-current h-4 w-4' xmlns='http://www.w3.org/2000/svg' viewBox='0 0 20 20'><path d='M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z'/></svg>"
        + "</div></div></div>";
    }
    component.innerHTML = result;
    if(cantidad > 0) remplazar('cargarOpciones', 'flex py-12 justify-end py-4 w-10/12 mx-auto'); // Mostrar botón
}

function remplazar(id, attr){
    document.getElementById(id).className = attr;
}

function crearOptionsSabores(sabores){
    var msg = "";
    for(let i = 0; i < sabores.length; i++){
        msg += "<option value='" + sabores[i] + "'>" + sabores[i] + "</option>";
    }
    return msg;
}



function setSecondName(i, value){ // acomodar el nombre de la pizza en el segundo componente
    var id = "p_" + i;
    var msg = "<p class='mt-2' id='" + id + "'>Ingredientes adicionales ";
    if(value === "Ninguno") msg += "(Escogió ninguno):";
    else msg += "(Pizza " + value + "):";
    msg += "</p>";
    document.getElementById(id).innerHTML = msg;
}

function setName(i, value){ // Acomodar el nombre de la pizza en el primer componente 
    var id = "p-" + i;
    var msg = "<p class='mt-2' id='" + id + "'>Ingredientes adicionales ";
    if(value === "Ninguno") msg += "(Escogió ninguno):";
    else msg += "(Pizza " + value + "):";
    msg += "</p>";
    document.getElementById(id).innerHTML = msg;
}

function listener(i, value){
    let select = document.getElementsByName("pizza1")[i - 1];
    let select2 = document.getElementsByName("pizza2")[i - 1];
    var img = "img" + i;
    var bool = false;

    for(let j = 0; j < select.length - 1; j++){
        if(select.options[j].value !== select2.options[j + 1].value){
            bool = true;
            let option = document.createElement("option");
            option.text = select.options[j].value;
            option.value = select.options[j].value;
            select2.add(option, (j + 1));
            break;
        }
    }

    if(select.options[select.length - 1].value !== select2.options[select2.length - 1].value && !bool){
        let option = document.createElement("option");
        option.text = select.options[select.length - 1].value;
        option.value = select.options[select.length - 1].value;
        select2.add(option, select2.length);
    }
    
    document.getElementsByName("pizza2")[i - 1].remove(value + 1);
}

function optionsValue(id){ // Deshabilitar los checkbox de los adicionales, segunda opción si elige ninguno
    var name = "check" + "_" + id;
    if(document.getElementById(id).value === "Ninguno") document.getElementsByName(name).forEach(element => element.setAttribute("disabled", ""));
    else document.getElementsByName(name).forEach(element => element.removeAttribute("disabled"));
}
    
    function changeImageURL(i, value){ // Setear la URL de la imagen dinámicamente
    var id = "img-" + i;
    document.getElementById(id).classList.remove("hidden");
    switch(value){
        case 'Napolitana':
            document.getElementById(id).setAttribute("src", "http://madarme.co/persistencia/pizza/napolitana.jpg");
            break;
        case 'Mexicana':
            document.getElementById(id).setAttribute("src", "http://madarme.co/persistencia/pizza/mexicana.jpg");
            break;
        case 'Hawayana':
            document.getElementById(id).setAttribute("src", "http://madarme.co/persistencia/pizza/hawaiana.jpg");
            break;
        case 'Vegetariana':
            document.getElementById(id).setAttribute("src", "http://madarme.co/persistencia/pizza/vegetariana.png");
            break;
        default:
            break;
    }
}

function changeSecondURL(i, value){ // Setear la URL de la imagen # 2 dinámicamente
    var id = "img_" + i;
    if(value === "Ninguno") document.getElementById(id).classList.add("hidden"); // Quitar la imagen
    else document.getElementById(id).classList.remove("hidden"); // Mostrar las imágenes de los sabores
    switch(value){
        case 'Napolitana':
            document.getElementById(id).setAttribute("src", "http://madarme.co/persistencia/pizza/napolitana.jpg");
            break;
        case 'Mexicana':
            document.getElementById(id).setAttribute("src", "http://madarme.co/persistencia/pizza/mexicana.jpg");
            break;
        case 'Hawayana':
            document.getElementById(id).setAttribute("src", "http://madarme.co/persistencia/pizza/hawaiana.jpg");
            break;
        case 'Vegetariana':
            document.getElementById(id).setAttribute("src", "http://madarme.co/persistencia/pizza/vegetariana.png");
            break;
        default:
            break;
    }
}
