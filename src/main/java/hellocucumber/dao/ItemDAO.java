package hellocucumber.dao;


import hellocucumber.domain.Item;

import java.util.List;

/**
 * Η διεπαφή DAO για την κλάση {@link Item}.
 * @author Νίκος Διαμαντίδης
 *
 */
public interface ItemDAO {

    /**
     * Επιστρέφει το αντίτυπο για κάποιο αριθμό εισαγωγής.
     * @param itemNo Ο αριθμός εισαγωγής του αντιτύπου
     * @return Το αντίτυπο
     */
    Item find(int itemNo);

       
    /**
     * Αποθηκεύει ένα αντικείμενο στην εξωτερική πηγή
     * δεδομένων. Το αντικείμενο μπορεί να είναι κάποιο
     * νέο αντικείμενο που δεν υπάρχει στην πηγή δεδομένων
     * ή κάποιο το οποίο ήδη υπάρχει και ενημερώνεται η
     * κατάστασή του.
     * @param entity Το αντικείμενο του οποίου η κατάσταση
     * αποθηκεύεται στην εξωτερική πηγή δεδομένων.
     */
    void save(Item entity);
    
    /**
     * Διαγράφει το αντικείμενο από την εξωτερική πηγή δεδομένων.
     * @param entity Το αντικείμενο προς διαγραφή.
     */
    void delete(Item entity);
    
    /**
     * Επιστρέφει όλα τα αντικείμενα  από την εξωτερική πηγή δεδομένων.
     * @return Ο κατάλογος των αντικειμένων
     */
    List<Item> findAll();

}
