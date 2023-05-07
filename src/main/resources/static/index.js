$(function(){
    hentAlleKunder();
});

function hentAlleKunder(){
    $.get("/hentKunder",function(kunder){
        formaterKunder(kunder);
    })
        .fail(function(status){
            if(status.status="404")
                $("#feil").html("Må logge inn for å vise kundene!");
        });
};

function formaterKunder(kunder){
    var ut="<table class='table table-striped'>"+
        "<tr>"+"" +
            "<th>Navn</th><th>Adresse</th><th>Passord</th><th></th><th></th>"+
        "</tr>";
    for(let i in kunder){
        ut+="<tr>"+
            "<td>"+kunder[i].navn+"</td>"+
            "<td>"+kunder[i].adresse+"</td>"+
            "<td>"+kunder[i].passord+"</td>"+
            "<td> <a class='btn btn-primary' href='endreKunde.html?id="+kunder[i].id+"'>Endre</a></td>"+
            "<td> <button class='btn btn-danger' onclick='slettEnKunde("+kunder[i].id+")'>Slett</button></td>"+
            "</tr>";
    }
    $("#kundene").html(ut);
}

function slettEnKunde(id){
    const url="/slettEnKunde?id="+id;
    $.get(url, function(){
        window.location.href='index.html';
    })
        .fail(function(){
            $("#feil").html("Feil i db - prøv igjen senere");
        });
};

function slettKundene(){
    $.get("/slettAlleKunder", function(){
        window.location.href='index.html';
    })
        .feil(function (){
            $("#feil").html("Feil i db - prøv igjen senere");
        });
};