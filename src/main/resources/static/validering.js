function validerNavn(navn){
    const regexp=/[a-zA-ZæøåÆØÅ\.\ \-]{2,20}/;
    const ok=regexp.test(navn);
    if(!ok){
        $("#feilNavn").html("Navnet må bestå av 2 til 20 bokstaver");
        return false;
    }
    else{
        $("#feilNavn").html("");
        return true;
    }
}

function validerAdresse(adresse){
    var regexp=/[0-9a-zA-ZæøåÆØÅ\ \.\-]{2,50}/;
    var ok=regexp.test(adresse);
    if(!ok){
        $("#feilAdresse").html("Adressen må bestå av 2 til 20 bokstaver og tall");
        return false;
    }
    else{
        $("#feilAdresse").html("");
        return true;
    }
}

function validerPassord(passord){
    var regexp=/(?=.*[a-zA-ZæøåÆØÅ])(?=.*\d)[a-zA-ZæøåÆØÅ\d]{8,}/;
    var ok=regexp.test(passord);
    if(!ok){
        $("#feilPassord").html("Passordet må være minimum 8 tegn, et av de en bokstav og et tall");
        return false;
    }
    else{
        $("#feilPassord").html("");
        return true;
    }
}