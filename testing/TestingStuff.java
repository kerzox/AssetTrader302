package testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Asset;
import server.Marketplace;
import server.Organisation;
import server.User;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestingStuff {
    Asset CPU_TIME = new Asset("cpu_time");

    User admin;

	@BeforeEach @Test
	public void setUpMovieList() {
        admin = new User(UUID.randomUUID(),
                "test",
                "test",
                new Organisation("Accounting"),
                User.AccountType.ADMIN);
	}

	@Test
    public void addCredits() {
	    Organisation uint = admin.getUnit();
	    uint.addCredits(1500);
	    System.out.println(uint.getBudget());
    }

    @Test
    public void addAssetAndAmount() {
        Organisation uint = admin.getUnit();
        uint.getTotalAssets().put(CPU_TIME, 150);
        for (Map.Entry<Asset, Integer> assetIntegerEntry : uint.getTotalAssets().entrySet()) {
            System.out.println(assetIntegerEntry.getKey().getName() + ": " + assetIntegerEntry.getValue());
        }
    }

    @Test
    public void createListingTooExpensive() {
        Organisation uint = admin.getUnit();
        addCredits();
        addAssetAndAmount();
        assertThrows(IllegalArgumentException.class, () -> uint.createBuyOrder(CPU_TIME, 100, 1501));
    }
}
