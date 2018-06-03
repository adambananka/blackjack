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
    private static final String FILE_NAME = "ResultList.txt";
    private static final String FILE_ENCODE = "UTF-8";

    private static EntityManager em;

    public ResultManager() {
        //to hide hibernate logs from console to not interfere with game, user don't need them
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("resultList");
        em = emf.createEntityManager();
    }

    /**
     * Creates result list from given parameters and adds it to DB.
     * @param player    name of player
     * @param cards     player's cards in string form
     * @param score     player's score
     * @param bet       player's bet
     * @param result    player's result
     */
    public void addResultList(String player, String cards, int score, int bet, Result result){
        em.getTransaction().begin();
        ResultList resultList = new ResultList(player, cards, score, bet, result);
        em.persist(resultList);
        em.getTransaction().commit();
    }

    /**
     * Retrieves all result lists from DB.
     * @return  list of result lists
     */
    public List<ResultList> getAllResultLists( ){
        return em.createQuery("SELECT r FROM ResultList r", ResultList.class).getResultList();
    }

    /**
     * Saves all result lists from DB to file.
     */
    public void saveAllResultLists() {
        try (PrintWriter writer = new PrintWriter(FILE_NAME, FILE_ENCODE)){
            List<ResultList> res = getAllResultLists();
            for (ResultList result : res) {
                writer.println(result);
            }
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
