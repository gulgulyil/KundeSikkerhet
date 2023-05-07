package oslomet.kundesikkerhet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KundeRepository {
    @Autowired
    private JdbcTemplate db;

    private Logger logger= LoggerFactory.getLogger(KundeRepository.class);

    private String kryterPassord(String passord){
        BCryptPasswordEncoder bCrypt=new BCryptPasswordEncoder(14);
        String hashedPassord=bCrypt.encode(passord);
        return hashedPassord;
    }

    private boolean sjekkPassord(String passord, String hashPassord){
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        if(bCrypt.matches(hashPassord,passord)){
            return true;
        }
        return false;
    }

    public boolean lagreKunde(Kunde kunde){
        String hash=kryterPassord(kunde.getPassord());
        String sql = "INSERT INTO kunde (navn,adresse,passord) VALUES(?,?,?)";
        try{
            db.update(sql,kunde.getNavn(),kunde.getAdresse(),hash);
            return true;
        }
        catch(Exception e){
            logger.error("feil i lagreKunde :"+e);
            return false;
        }
    }

    public List<Kunde> hentAlleKunder(){
        String sql="SELECT * FROM Kunde";
        try{
            List<Kunde> alleKunder=db.query(sql,new BeanPropertyRowMapper(Kunde.class));
            return alleKunder;
        }
        catch (Exception e){
            logger.error("Feil i hentAlleKunder :"+e);
            return null;
        }
    }

    public Kunde hentEnKunde(int id){
        String sql="SELECT * FROM Kunde WHERE id=?";
        try{
            Kunde enKunde=db.queryForObject(sql, BeanPropertyRowMapper.newInstance(Kunde.class),new Object[]{id});
            return enKunde;
        }
        catch(Exception e){
            logger.error("Feil i hentEnKunde :"+e);
            return null;
        }
    }

    public boolean endreEnKunde(Kunde kunde) {
        String hash = kryterPassord(kunde.getPassord());
        String sql = "UPDATE Kunde SET navn=?,adresse=?,passord=? where id=?";
        try {
            db.update(sql,kunde.getNavn(),kunde.getAdresse(),hash,kunde.getId());
            return true;
        } catch (Exception e) {
            logger.error("Feil i endreEnKunde : " + e);
            return false;
        }
    }

    public boolean slettEnKunde(int id){
        String sql="DELETE FROM Kunde WHERE id=?";
        try{
            db.update(sql,id);
            return true;
        }
        catch (Exception e){
            logger.error("Feil i slettEnKunde : "+e);
            return false;
        }
    }

    public boolean slettAlleKunder(){
        String sql="DELETE FROM Kunde";
        try{
            db.update(sql);
            return true;
        }
        catch(Exception e){
            logger.error("Feil i slettAlleKunder : "+e);
            return false;
        }
    }

    public boolean sjekkNavnOgPassord (Kunde kunde){
        String sql="SELECT * FROM Kunde WHERE navn=?";
        try{
            Kunde dbKunde=db.queryForObject(sql, BeanPropertyRowMapper.newInstance(Kunde.class),new Object[]{kunde.getNavn()});
            return sjekkPassord(dbKunde.getPassord(),kunde.getPassord());
        }
        catch(Exception e){
            logger.error("Feil i sjekkNavnOgPassord : "+e);
            return false;
        }
    }
}
