package hellocucumber.memorydao;

import hellocucumber.dao.ItemDAO;
import hellocucumber.domain.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Η υλοποίηση των αντικειμένων πρόσβασης δεδομένων (DAO) για
 * την κλάση {@link Item}
 * @author Νίκος Διαμαντίδης
 *
 */
public class ItemDAOMemory implements ItemDAO { 
    protected static List<Item> entities = new ArrayList<Item>();
    
    public void delete(Item entity) {
        entities.remove(entity);    
    }

    public List<Item> findAll() {
        return new ArrayList<Item>(entities);
    }


    public void save(Item entity) {
        if (! entities.contains(entity)) {
            entities.add(entity);    
        }        
    }
    public Item find(int itemNo) {
        for(Item item : entities) {
            if (item.getItemNumber() == itemNo) {
                return item;
            }
        }
        return null;
    }

}
