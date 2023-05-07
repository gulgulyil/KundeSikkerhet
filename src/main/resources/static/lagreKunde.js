function validerOgLagreKunde(){
    const navnOK=validerNavn($("#navn").val());
    const adresseOK=validerAdresse($("#adresse").val());
    const passordOK=validerPassord($("#passord").val());
    if(navnOK && adresseOK && passordOK){
        lagreKunde();
    }
}

function lagreKunde(){
    const kunde={
        navn : $("#navn").val(),
        adresse:$("#adresse").val(),
        passord:$("#passord").val()
    }

    const url="/lagreKunde";
    $.post(url,kunde,function(){
        window.location.href='index.html';
    })
        .fail(function(status){
            if(status.status==422){
                $("#feil").html("Feil i db - prøv igjen senere");
            }
            else{
                $("#feil").html("Valideringsfeil - prøv igjen senere");
            }
        });
};