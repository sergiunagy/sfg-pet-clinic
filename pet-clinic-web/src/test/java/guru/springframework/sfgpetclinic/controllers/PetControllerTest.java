package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
class PetControllerTest {

    @Mock
    PetTypeService petTypeService;

    @Mock
    PetService petService;

    @Mock
    OwnerService ownerService;

    MockMvc mockMvc;
    PetController petController;

    Set<PetType> petTypes;
    Set<Pet> pets = new HashSet<>();
    Owner owner;

    @BeforeEach
    void setUp() {

        owner = Owner.builder().id(1L).lastName("test pet add").build();
        petTypes = new HashSet<>();
        petTypes.add(PetType.builder().name("dog").build());
        petTypes.add(PetType.builder().name("chonk").build());
        petTypes.add(PetType.builder().name("spaghett").build());


        pets.add(Pet.builder().petType(petTypes.stream().findAny().get()).id(1L).name("Fluffy").owner(owner).build());

        petController = new PetController(petTypeService, petService,ownerService);

        mockMvc= MockMvcBuilders.standaloneSetup(petController).build();
    }

    void populatePetTypes() throws Exception{
        //given - the setup objects
        //when
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(get("/owners/1"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attribute("types", petTypes));
    }

    @Test
    void initCreationForm() throws Exception{
        //given - mocks and setup inits
        //when
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(get("/owners/1/pets/new"))
                . andExpect(status().isOk())
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attribute("types", petTypes))
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    void processCreationForm() throws Exception{
        //given - mocks and setup inits
        //when
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post("/owners/1/pets/new"))
                . andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(petService).save(any());

    }

    @Test
    void initUpdateForm() throws Exception{
        //given - mocks and setup inits
        //when
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);
        when(petService.findById(anyLong())).thenReturn(pets.stream().findFirst().get());

        mockMvc.perform(get("/owners/1/pets/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attribute("types", petTypes))
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    void processUpdateForm() throws Exception{
        //given - mocks and setup inits
        //when
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post("/owners/1/pets/1/edit"))
                . andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(petService).save(any());

    }
}