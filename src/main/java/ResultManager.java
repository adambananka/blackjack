import Entities.ResultList;
import Enums.Result;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;

/**
 * @author Adam Bananka
 */
public class ResultManager {
    private static EntityManager em;

    public ResultManager() {
        //to hide hibernate logs from console to not interfere with game, user don't need them
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("resultList");
        em = emf.createEntityManager();
    }

    public void addResultList(String player, String cards, int score, int bet, Result result){
        em.getTransaction().begin();
        ResultList resultList = new ResultList(player, cards, score, bet, result);
        em.persist(resultList);
        em.getTransaction().commit();
    }

    public List<ResultList> getAllResultLists( ){
        return em.createQuery("SELECT r FROM ResultList r", ResultList.class).getResultList();
    }

    public void saveAllResultLists() {
        try (PrintWriter writer = new PrintWriter("ResultList.txt", "UTF-8")){
            List<ResultList> res = getAllResultLists();
            for (ResultList result : res) {
                writer.println(result);
            }
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
