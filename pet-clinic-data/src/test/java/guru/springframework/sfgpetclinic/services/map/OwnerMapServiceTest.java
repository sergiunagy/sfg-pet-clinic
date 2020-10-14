package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
    private final Long ownerId =1L;
    private final String ownerLastName= "Smith" ;
    private final String ownerLastName2= "White" ;

    @BeforeEach
    void setUp() {
        // replicate dep inj for the map service
        ownerMapService=new OwnerMapService( new PetTypeMapService(), new PetMapService());

        //save one Owner to the repository
        ownerMapService.save(Owner.builder().id(ownerId).lastName(ownerLastName).build());
    }

    @Test
    void findAll() {

        Set<Owner> owners=ownerMapService.findAll();

        assertEquals(1, owners.size());
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(ownerId);

        assertEquals(ownerId, owner.getId());
    }

    @Test
    void saveExistingId() {
        Long id = ownerId + 1;
        Owner owner2= Owner.builder().id(id).firstName("Sucker").build();
        ownerMapService.save(owner2);

        assertEquals(id, ownerMapService.findById(id).getId());
    }

    @Test
    void saveNotExistingId() {

        Owner owner3= Owner.builder().lastName("Sucker3").build();
        Owner savedOwner= ownerMapService.save(owner3);

        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(ownerId));

        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(ownerId);

        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void findByLastName() {

//        Owner smith = ownerMapService.findByLastName(ownerLastName);
//
//        assertNotNull(smith);
        Owner owner = Owner.builder().id(ownerId+1).lastName(ownerLastName2).build();
        Owner savedOwner= ownerMapService.save(owner);
        assertEquals(ownerId+1, ownerMapService.findByLastName(ownerLastName2).getId());
    }
}