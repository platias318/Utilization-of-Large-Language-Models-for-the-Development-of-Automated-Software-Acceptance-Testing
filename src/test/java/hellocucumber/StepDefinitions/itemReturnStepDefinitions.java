package hellocucumber.StepDefinitions;

import hellocucumber.dao.BorrowerDAO;
import hellocucumber.dao.ItemDAO;
import hellocucumber.dao.LoanDAO;
import hellocucumber.domain.*;
import hellocucumber.memorydao.BorrowerDAOMemory;
import hellocucumber.memorydao.ItemDAOMemory;
import hellocucumber.memorydao.LoanDAOMemory;
import hellocucumber.service.LoanService;
import hellocucumber.service.ReturnService;
import hellocucumber.support.LibraryWorld;
import hellocucumber.util.Money;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

public class itemReturnStepDefinitions {
    // Instance variables
    private Money fine;
    private Borrower georgeRed;
    private Item kingdomItem;
    private final LibraryWorld world;
    public itemReturnStepDefinitions(LibraryWorld world){
        this.world=world;
    }
    @Before
    public void setUp(){
        world.clearBorrowers();
        world.clearItems();
        world.clearLoans();
    }
    @Given("{borrower} borrowed the item Animal Kingdom {int} days prior to today's date")
    public void givenBorrowerBorrowedItemWithReturnDate(Borrower borrower,Integer daysBefore) {
        georgeRed = borrower;
        georgeRed.setCategory(new BorrowerCategory());
        georgeRed.getCategory().setMaxLendingItems(5);
        georgeRed.getCategory().setDailyFine(new Money(new BigDecimal(Integer.toString(5)),Currency.getInstance("EUR")));

        // Create and save the item/book Animal Kingdom
        kingdomItem=world.createItem("Animal Kingdom",1001114 );
        if(world.loanService.findBorrower(georgeRed.getBorrowerNo())){
            world.loanService.borrow(kingdomItem.getItemNumber());
            //changing to a custom loan date than the one created, for the scenario to be right
            world.loanDao.findPending(kingdomItem.getItemNumber()).setLoanDate(LocalDate.now().minusDays(daysBefore));
        }

    }
    @Given("George Red has been assigned maximum lending days of {int}")
    public void georgeRedHasBeenAssignedMaximumLendingDaysOf(int maxLendingDays) {
        georgeRed.getCategory().setMaxLendingDays(maxLendingDays);
    }
    @When("the return of Animal Kingdom is processed")
    public void whenItemReturnIsProcessed() {
        fine = world.returnService.returnItem(kingdomItem.getItemNumber());
    }
    @Then("the system marks the state of Animal Kingdom as AVAILABLE")
    public void thenNewStateMarked() {
        Assertions.assertEquals(ItemState.AVAILABLE, kingdomItem.getState());
    }
    @Then("George Red has one less pending item")
    public void thenDecreasePendingItemsOfGeorgeRed() {
        // Assert that the borrower has no pending items(he had 1 before)
        Assertions.assertEquals(georgeRed.countPendingItems(),0);
    }
    @Then("George Red does not pay a fine")
    public void georgeRedDoesNotPayAFine() {
        Assertions.assertNull(fine);
    }
    @Then("the return date of the loan is set to today's date")
    public void thenNewReturnDateSet() {
        Assertions.assertEquals(LocalDate.now(),world.loanDao.findLoan(kingdomItem.getItemNumber()).getReturnDate());
    }
    @Then("George Red pays a fine based on the borrower category")
    public void thenFinePaymentIsDone() {
        Assertions.assertNotNull(fine);
    }

}