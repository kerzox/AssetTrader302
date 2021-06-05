package server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TestClasses {
    Organisation o1, o2;
    User u1;
    Asset a1;
    Listing l1;

    @BeforeEach
    public void initClasses() {
        o1 = new Organisation("unit1");
        u1 = new User("User1", "pwd", o1);
    }

    @Test
    public void testNewBudget() throws BudgetException {
        // Equivalence Classes
        assertThrows(BudgetException.class, () -> {
           o2 = new Organisation("unit3", -10);
        });
        o2 = new Organisation("unit3", 10);
        assertEquals(10, o2.getBudget());

        // Boundary Classes
        assertThrows(BudgetException.class, () -> {
            o2 = new Organisation("unit3", -1);
        });
        o2 = new Organisation("unit3", 0);
        assertEquals(0, o2.getBudget());
    }

    @Test
    public void testAddBudget() throws BudgetException {
        // Equivalence Classes
        assertThrows(BudgetException.class, () -> {
            o1.addBudget(-10);
        });
        o1.addBudget(10);
        assertEquals(10, o1.getBudget());

        // Boundary Classes
        assertThrows(BudgetException.class, () -> {
            o1.addBudget(-1);
        });
        o1.addBudget(0);
        assertEquals(10, o1.getBudget());
    }

    @Test
    public void testSubtractBudget() throws BudgetException {
        // Equivalence Classes
        o1.addBudget(100);
        assertThrows(BudgetException.class, () -> {
            o1.subtractBudget(-10);
        });
        assertThrows(BudgetException.class, () -> {
            o1.subtractBudget(110);
        });
        o1.subtractBudget(10);
        assertEquals(90, o1.getBudget());

        // Boundary Classes
        o1.addBudget(10);
        assertThrows(BudgetException.class, () -> {
            o1.subtractBudget(-1);
        });
        assertThrows(BudgetException.class, () -> {
            o1.subtractBudget(101);
        });
        o1.subtractBudget(1);
        assertEquals(99, o1.getBudget());
        o1.subtractBudget(98);
        assertEquals(1, o1.getBudget());
    }

    @Test
    public void testNewUser() throws TextInputException {
        // Equivalence Classes
        assertThrows(TextInputException.class, () -> {
            u1 = new User("12345678901234567890123456789012345", "pwd", o1);
        });
        u1 = new User("user1", "pwd", o1);
        assertEquals("user1", u1.getUsername());
        assertEquals("pwd", u1.getPassword());

        // Boundary Classes
        assertThrows(TextInputException.class, () -> {
            u1 = new User("1234567890123456789012345678901", "pwd", o1);
        });
        assertThrows(TextInputException.class, () -> {
            u1 = new User("", "pwd", o1);
        });
        assertThrows(TextInputException.class, () -> {
            u1 = new User("uname", "", o1);
        });
        assertThrows(TextInputException.class, () -> {
            u1 = new User("u name", "pwd", o1);
        });
        assertThrows(TextInputException.class, () -> {
            u1 = new User("uname", "p wd", o1);
        });
        u1 = new User("1", "1", o1);
        assertEquals("1", u1.getUsername());
        assertEquals("1", u1.getPassword());
        u1 = new User("123456789012345678901234567890",
                "123456789012345678901234567890", o1);
        assertEquals("123456789012345678901234567890", u1.getUsername());
        assertEquals("123456789012345678901234567890", u1.getPassword());

    }

    @Test
    public void testAddListing() {
        // equivalence

        // quantity -5
        assertThrows(ListingException.class, () -> {
            l1 = new Listing(UUID.randomUUID(), Listing.enumType.BUY, -5, 10, "user", "organisation", "asset");
        });

        // quantity 5
        l1 = new Listing(UUID.randomUUID(), Listing.enumType.BUY, 5, 10, "user", "organisation", "asset");
        assertEquals(5, l1.getAssetQuantity());

        // quantity -5
        assertThrows(ListingException.class, () -> {
            l1 = new Listing(UUID.randomUUID(), Listing.enumType.BUY, 10, -5, "user", "organisation", "asset");
        });

        // quantity 5
        l1 = new Listing(UUID.randomUUID(), Listing.enumType.BUY, 10, 5, "user", "organisation", "asset");
        assertEquals(5, l1.getAssetPrice());

        // boundary

        // negative boundary quantity
        assertThrows(ListingException.class, () -> {
            l1 = new Listing(UUID.randomUUID(), Listing.enumType.BUY, -10, 10, "user", "organisation", "asset");
        });

        // 0 quantity

        assertThrows(ListingException.class, () -> {
            l1 = new Listing(UUID.randomUUID(), Listing.enumType.BUY, 0, 10, "user", "organisation", "asset");
        });

        // negative boundary price
        assertThrows(ListingException.class, () -> {
            l1 = new Listing(UUID.randomUUID(), Listing.enumType.BUY, 10, -10, "user", "organisation", "asset");
        });

        // 0 price

        assertThrows(ListingException.class, () -> {
            l1 = new Listing(UUID.randomUUID(), Listing.enumType.BUY, 10, 0, "user", "organisation", "asset");
        });

    }

    @Test
    public void testNewAsset() {
        // equivalence

        // size of 25
        a1 = new Asset("alkenylidenecyclopropanes");
        assertEquals(25, a1.getName().length());
        assertEquals("alkenylidenecyclopropanes", a1.getName());

        // size of 35
        assertThrows(TextInputException.class, () -> {
            a1 = new Asset("hippopotomonstrosesquipedaliophobic");
        });

        // boundary

        // length of 0
        assertThrows(TextInputException.class, () -> {
            a1 = new Asset("");
        });

        // length of 31
        assertThrows(TextInputException.class, () -> {
            a1 = new Asset("quinquagintaquadringentillionth");
        });

        //Whitespaces are invalid
        assertThrows(TextInputException.class, ()-> {
            a1 = new Asset("test ");
        });

        //Symbols are invalid
        assertThrows(TextInputException.class, ()-> {
            a1 = new Asset("test!");
        });

        //Numbers are invalid
        assertThrows(TextInputException.class, ()-> {
            a1 = new Asset("test1");
        });
    }
}
