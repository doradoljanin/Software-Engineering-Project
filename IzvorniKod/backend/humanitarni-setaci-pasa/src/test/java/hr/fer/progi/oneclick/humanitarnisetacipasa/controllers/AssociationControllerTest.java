package hr.fer.progi.oneclick.humanitarnisetacipasa.controllers;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.*;
import hr.fer.progi.oneclick.humanitarnisetacipasa.models.AssociationShortInfoModel;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.AssociationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AssociationController.class)
@ActiveProfiles({"test"})
public class AssociationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AssociationRepository associationRepository;

    @BeforeEach
    public void setUp() {
        Association association = new Association(1L,
                "01234567891",
                "Udruga Spas Varazdin",
                "Nemetin 3",
                new Place(1L, "Varazdin"),
                new User(1L, "bhorvat", "bbhorvatt@gmail.com", null, null, "049123912", true),
                new ArrayList<>());

        when(associationRepository.getAllAsShortInfoModels())
                .thenReturn(List.of(new AssociationShortInfoModel(association)));
        when(associationRepository.findById(1L))
                .thenReturn(Optional.of(association));
        when(associationRepository.findById(2L))
                .thenReturn(Optional.empty());
    }

    @Test
    public void test_getAll() throws Exception {
        mvc.perform(get("/api/associations")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Udruga Spas Varazdin\",\"address\":\"Nemetin 3\", \"place\":\"Varazdin\"}]"));
    }

    @Test
    public void test_getAssociation() throws Exception {
        mvc.perform(get("/api/associations/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test_getAssociation_notExists() throws Exception {
        mvc.perform(get("/api/associations/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}