package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @InjectMocks
    OwnerController ownerController;

    @Mock
    OwnerService ownerService;

    @Mock
    Model model;

    Set<Owner> retOwners;
    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        retOwners = new HashSet<>();
        retOwners.add(Owner.builder().id(1L).lastName("First").build());
        retOwners.add(Owner.builder().id(15L).lastName("Second").build());

        //setup the controller context
        mockMvc = MockMvcBuilders
                .standaloneSetup(ownerController)
                .build();
    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));
        verifyNoInteractions(ownerService);
    }

    @Test
    void processFindFormReturnMany() throws Exception {

        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(new ArrayList<Owner>(retOwners));

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));
    }

    @Test
    void processFindFormReturnOne() throws Exception {

        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Arrays.asList(Owner.builder().
                        id(255L).
                        lastName("test").
                        build()));

        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/255"));

    }


    @Test
    void showOwner() throws Exception {

        // given
        // when
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).lastName("First").build());

        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))));
    }


    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwner"))
                .andExpect(model().attributeExists("owner"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void processCreationForm() throws Exception {
        when(ownerService.save(ArgumentMatchers.any())).thenReturn(Owner.builder().id(1l).build());

        mockMvc.perform(post("/owners/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("owner"));

    }

    @Test
    void initUpdateOwnerForm() throws Exception {

        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).lastName("First").build());

        mockMvc.perform(get("/owners/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwner"))
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    void processUpdateOwnerForm() throws Exception {

        when(ownerService.save(ArgumentMatchers.any())).thenReturn(Owner.builder().id(255L).lastName("First").build());

        mockMvc.perform(post("/owners/255/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/255"))
                .andExpect(model().attributeExists("owner"));
    }
}