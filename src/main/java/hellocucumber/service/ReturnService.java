package hellocucumber.service;

import hellocucumber.LibraryException;
import hellocucumber.dao.*;
import hellocucumber.domain.Loan;
import hellocucumber.memorydao.LoanDAOMemory;
import hellocucumber.util.Money;

/**
 * Η υπηρεσία επιστροφής αντιτύπου.
 * @author Νίκος Διαμαντίδης
 *
 */
public class ReturnService {

    /**
     * Πραγματοποιεί την επιστροφή ενός αντιτύπου και
     * επιστρέφει το τυχόν πρόστιμο που πρέπει να καταβληθεί.
     * @param itemNo Ο αριθμός εισαγωγής του αντιτύπου που επιστρέφεται.
     * @return Το πρόστιμο που πρέπει να πληρωθεί ή {@code null}
     * αν δεν υπάρχει πρόστιμο.
     */
    public Money returnItem(int itemNo) {
        LoanDAO loanDAO = new LoanDAOMemory();
        Money fine = null;

        Loan loan = loanDAO.findPending(itemNo);
        if (loan == null) {
            System.out.println("the loan is null");
            throw new LibraryException();
        }

        loan.returnItem();
        if (loan.getOverdue() > 0) {
            fine = loan.getFine();
        }

        loanDAO.save(loan);
        return fine;
    }
}
