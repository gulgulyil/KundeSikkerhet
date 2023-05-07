package oslomet.kundesikkerhet;


import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class KundeController {

    @Autowired
    private HttpSession session;
    @Autowired
    KundeRepository rep;

    Logger logger= LoggerFactory.getLogger(KundeController.class);

    private boolean validerKunde(Kunde kunde){
        String regexNavn="[a-zA-ZæøåÆØÅ\\.\\ \\-]{2,20}";
        String regexAdresse="[0-9a-zA-ZæøåÆØÅ\\ \\.\\-]{2,50}";
        String regexPassord="(?=.*[a-zA-ZæøåÆØÅ])(?=.*\\d)[a-zA-ZæøåÆØÅ\\d]{8,}"; //minumum 8 tegn, en bokstav og et tall
        boolean navnOK=kunde.getNavn().matches(regexNavn);
        boolean adresseOK=kunde.getAdresse().matches(regexAdresse);
        boolean passordOK=kunde.getPassord().matches(regexPassord);
        if(navnOK && adresseOK && passordOK){
            return true;
        }
        logger.error("Valideringsfeil");
        return false;
    }

    @PostMapping("/lagreKunde")
    public void lagreKunde(Kunde kunde, HttpServletResponse response) throws IOException {
        if(!validerKunde(kunde)){
            response.sendError(HttpStatus.NOT_ACCEPTABLE.value());
        }
        else{
            if(!rep.lagreKunde(kunde)){
                response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
            }
        }
    }


    @GetMapping("/hentKunder")
    public List<Kunde> hentAlle(HttpServletResponse response) throws IOException{
        List<Kunde>  alleKunder=new ArrayList<>();
        if(session.getAttribute("Innlogget")!=null){
            alleKunder=rep.hentAlleKunder();
            if(alleKunder==null){
                response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
            }
            return alleKunder;
        }
        response.sendError(HttpStatus.NOT_FOUND.value());
        return null;
    }

    @GetMapping("/hentEnKunde")
    public Kunde hentEnKunde(int id, HttpServletResponse response) throws IOException{
        Kunde kunden=rep.hentEnKunde(id);
        if(kunden==null){
            response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
        }
        return kunden;
    }

    @PostMapping("/endreEnKunde")
    public void endreEnKunde(Kunde kunde,HttpServletResponse response) throws IOException{
        if(!validerKunde(kunde)){
            response.sendError(HttpStatus.NOT_ACCEPTABLE.value());
        }
        else{
            if(!rep.endreEnKunde(kunde)){
                response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
            }
        }
    }

    @GetMapping("/slettEnKunde")
    public void slettEnKunde(int id,HttpServletResponse response) throws IOException{
       if(!rep.slettEnKunde(id)){
           response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
       }
    }

    @GetMapping("/slettAlleKunder")
    public void slettAlle(HttpServletResponse response) throws IOException{
        if(!rep.slettAlleKunder()){
            response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
        }
    }



    @GetMapping("/login")
    public boolean login(Kunde kunde){
        if(rep.sjekkNavnOgPassord(kunde)){
            session.setAttribute("Innlogget",kunde);
            return true;
        }
        return false;
    }

    @GetMapping("/logout")
    public void logout(){session.removeAttribute("Innlogget");}
}
