package edu.bbte.idde.leim2041.frontend;

import edu.bbte.idde.leim2041.backend.dao.AbstractDaoFactory;
import edu.bbte.idde.leim2041.backend.dao.OwnerDao;
import edu.bbte.idde.leim2041.backend.dao.RealEstateDao;
import edu.bbte.idde.leim2041.backend.model.Owner;
import edu.bbte.idde.leim2041.backend.model.RealEstate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Scanner;

// !!! Please run with ./gradlew run --console=plain, so the EXECUTING progress bar won't appear !!!

@Slf4j
public class RealEstateManager {
    private static final OwnerDao OWNER_DAO;

    private static final RealEstateDao REAL_ESTATE_DAO;

    static {
        OWNER_DAO = AbstractDaoFactory.getDaoFactory().getOwnerDao();
        REAL_ESTATE_DAO = AbstractDaoFactory.getDaoFactory().getRealEstateDao();
    }

    @SneakyThrows
    private static void loadInitialData() {
        Owner owner1 = new Owner();
        owner1.setFirstName("John");
        owner1.setSecondName("Bosh");
        owner1.setAge(46);
        owner1.setCnp(100101);
        OWNER_DAO.create(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Rachel");
        owner2.setSecondName("Torok");
        owner2.setAge(32);
        owner2.setCnp(200120);
        OWNER_DAO.create(owner2);

        RealEstate advertisement1 = new RealEstate();
        advertisement1.setCity("Kolozsvar");
        advertisement1.setNeighbourhood("Gheorgheni");
        advertisement1.setPrice(300000);
        advertisement1.setNumberOfRooms(3);
        advertisement1.setYearBuilt(2002);
        advertisement1.setHasElevator(true);
        advertisement1.setOwnerId(owner1.getId());
        REAL_ESTATE_DAO.create(advertisement1);

        RealEstate advertisement2 = new RealEstate();
        advertisement2.setCity("Kolozsvar");
        advertisement2.setNeighbourhood("Iris");
        advertisement2.setPrice(250000);
        advertisement2.setNumberOfRooms(4);
        advertisement2.setYearBuilt(2010);
        advertisement2.setHasElevator(false);
        advertisement2.setOwnerId(owner1.getId());
        REAL_ESTATE_DAO.create(advertisement2);

        RealEstate advertisement3 = new RealEstate();
        advertisement3.setCity("Kolozsvar");
        advertisement3.setNeighbourhood("Manastur");
        advertisement3.setPrice(80000);
        advertisement3.setNumberOfRooms(1);
        advertisement3.setYearBuilt(1984);
        advertisement3.setHasElevator(true);
        advertisement3.setOwnerId(owner2.getId());
        REAL_ESTATE_DAO.create(advertisement3);
    }

    @SneakyThrows
    private static void all(String parancs) {
        if (Objects.equals(parancs, "All") || Objects.equals(parancs, "ALL")
                || Objects.equals(parancs, "all")) {
            for (RealEstate advertisement : REAL_ESTATE_DAO.findAll()) {
                log.info(advertisement.toString() + " ID: " + advertisement.getId());
            }
        }
    }

    @SneakyThrows
    private static void create(String parancs) {
        Scanner input = new Scanner(System.in);
        if (Objects.equals(parancs, "Create") || Objects.equals(parancs, "CREATE")
                || Objects.equals(parancs, "create")) {
            log.info("Creating new ad\n");
            log.info("Please enter data:\n");
            log.info("City =");
            final String city = input.next();
            log.info("Neighbourhood =");
            final String neighbourhood = input.next();
            log.info("Price =");
            final Integer price = input.nextInt();
            log.info("Number of rooms =");
            final Integer numberOfRooms = input.nextInt();
            log.info("Year built =");
            final Integer yearBuilt = input.nextInt();
            log.info("Has elevator =");
            final Boolean hasElevator = input.nextBoolean();
            log.info("Owner id =");
            final Long ownerCnp = input.nextLong();
            RealEstate advertisement = new RealEstate();
            advertisement.setCity(city);
            advertisement.setNeighbourhood(neighbourhood);
            advertisement.setPrice(price);
            advertisement.setNumberOfRooms(numberOfRooms);
            advertisement.setYearBuilt(yearBuilt);
            advertisement.setHasElevator(hasElevator);
            advertisement.setOwnerId(ownerCnp);
            REAL_ESTATE_DAO.create(advertisement);
        }
    }

    @SneakyThrows
    private static void update(String parancs) {
        Scanner input = new Scanner(System.in);
        if (Objects.equals(parancs, "Update") || Objects.equals(parancs, "UPDATE")
                || Objects.equals(parancs, "update")) {
            log.info("Id of add you wish to update =");
            final Long id = input.nextLong();
            log.info("Updated data\n");
            log.info("City =");
            final String city = input.next();
            log.info("Neighbourhood =");
            final String neighbourhood = input.next();
            log.info("Price =");
            final Integer price = input.nextInt();
            log.info("Number of rooms =");
            final Integer numberOfRooms = input.nextInt();
            log.info("Year built =");
            final Integer yearBuilt = input.nextInt();
            log.info("Has elevator =");
            final Boolean hasElevator = input.nextBoolean();
            log.info("Owner id =");
            final Long ownerId = input.nextLong();
            RealEstate advertisement = new RealEstate();
            advertisement.setCity(city);
            advertisement.setNeighbourhood(neighbourhood);
            advertisement.setPrice(price);
            advertisement.setNumberOfRooms(numberOfRooms);
            advertisement.setYearBuilt(yearBuilt);
            advertisement.setHasElevator(hasElevator);
            advertisement.setOwnerId(ownerId);
            REAL_ESTATE_DAO.update(id, advertisement);
        }
    }

    @SneakyThrows
    private static void search(String parancs) {
        Scanner input = new Scanner(System.in);
        if (Objects.equals(parancs, "Search") || Objects.equals(parancs, "SEARCH")
                || Objects.equals(parancs, "search")) {
            log.info("ID of the ad=");
            Long id = input.nextLong();
            RealEstate oneAd = REAL_ESTATE_DAO.findById(id);
            log.info(oneAd.toString());
        }
    }

    private static void delete(String parancs) {
        Scanner input = new Scanner(System.in);
        if (Objects.equals(parancs, "Delete") || Objects.equals(parancs, "DELETE")
                || Objects.equals(parancs, "delete")) {
            log.info("ID of the ad you want to delete =");
            Long id = input.nextLong();
            REAL_ESTATE_DAO.delete(id);
            log.info("Ad successfully deleted");
        }
    }

    public static void main(String[] args) {
        loadInitialData();
        log.info("Welcome to the world's best real estate agency\n");
        log.info("Commands:\n");
        log.info("All: list all ads");
        log.info("Create: create new ad");
        log.info("Update: update existing ad");
        log.info("Search: search for ad by id");
        log.info("Delete: delete ad");
        log.info("Exit: exit console application");
        log.info("Command:");
        Scanner input = new Scanner(System.in);
        String parancs = input.next();
        while (!Objects.equals(parancs, "exit") && !Objects.equals(parancs, "EXIT")
                && !Objects.equals(parancs, "Exit")) {
            all(parancs);
            create(parancs);
            update(parancs);
            search(parancs);
            delete(parancs);
            log.info("Command:");
            parancs = input.next();
        }
    }
}