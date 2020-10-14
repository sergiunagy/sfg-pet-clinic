package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJPAServiceImplTest {

    public static final String SMITH = "Smith";
    @InjectMocks
    OwnerSDJPAServiceImpl ownerSDJPAService;

    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PetTypeRepository petTypeRepository;

    private Owner retOwner;

    @BeforeEach
    void setUp() throws Exception{
        retOwner = Owner.builder().id(255L).lastName(SMITH).build();
    }

    @Test
    void findByLastName() {

        when(ownerRepository.findByLastName(any())).thenReturn(retOwner);

        Owner smith = ownerSDJPAService.findByLastName(SMITH);

        assertNotNull(smith);
        assertEquals(retOwner.getId(), smith.getId());
        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {
        Set<Owner> retOwners = new HashSet<>();
        retOwners.add(Owner.builder().id(2L).lastName("White").build());
        retOwners.add(Owner.builder().id(3L).lastName("Loco").build());

        when(ownerRepository.findAll()).thenReturn(retOwners);

        Set<Owner> owners = ownerSDJPAService.findAll();

        assertNotNull(owners);
        assertEquals(2, owners.size());
    }

    @Test
    void findById() {

        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(retOwner));

        Owner owner = ownerSDJPAService.findById(255L);

        assertNotNull(owner);
        assertEquals(255L, owner.getId());

    }

    @Test
    void findByIdNotFound() {

        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Owner owner = ownerSDJPAService.findById(255L);

        assertNull(owner);
    }

    @Test
    void save() {

        when(ownerRepository.save(any())).thenReturn(retOwner);

        Owner owner = ownerSDJPAService.save(retOwner);

        assertNotNull(owner);
        assertEquals(retOwner.getId(), owner.getId());

    }

    @Test
    void delete() {

        ownerSDJPAService.delete(retOwner);

        verify(ownerRepository, times(1)).delete(retOwner);
    }

    @Test
    void deleteById() {
        ownerSDJPAService.deleteById(retOwner.getId());

        verify(ownerRepository).deleteById(retOwner.getId());
    }
}