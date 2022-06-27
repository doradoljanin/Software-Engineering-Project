package hr.fer.progi.oneclick.humanitarnisetacipasa.controllers;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.*;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.AnimalRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.CitizenRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.WalkRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.security.WebSecurityConfig;
import hr.fer.progi.oneclick.humanitarnisetacipasa.security.services.UserDetailsImpl;
import hr.fer.progi.oneclick.humanitarnisetacipasa.security.services.UserDetailsServiceImpl;
import liquibase.pro.packaged.B;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(WalkController.class)
@ActiveProfiles({"test"})
public class WalkControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AnimalRepository animalRepository;

    @MockBean
    private WalkRepository walkRepository;

    @MockBean
    private CitizenRepository citizenRepository;

    @MockBean
    private Clock clock;

    private Clock fixedClock;

    @BeforeEach
    public void setUp() {
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(new UserDetailsImpl(1L,
                "bhorvat",
                "bbhorvatt@gmail.com",
                "keks",
                List.of(new SimpleGrantedAuthority("ROLE_CITIZEN"))));
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        fixedClock = Clock.fixed(LocalDateTime.of(2020,
                1,
                8,
                0,
                0).toInstant(ZoneOffset.ofHours(0)), ZoneId.systemDefault());
        doReturn(fixedClock.getZone()).when(clock).getZone();
        doReturn(fixedClock.instant()).when(clock).instant();

        Citizen citizen = new Citizen(1, "Benjamin", "Horvat", null);
        Citizen alternateCitizen = new Citizen(2, "Vid", "Muzevic", null);
        Association association = new Association();
        Breed breed = new Breed(1, "Jazavcar", "12-15cm", "15-25kg", "12-16", "Toy");
        association.setId(1);

        Animal animal1 = new Animal(1, null, "Descr", "Kyra", breed, 2013, WalkType.SINGLE, Gender.FEMALE, association);
        Animal animal2 = new Animal(2, null, "Descr", "Snoopy", breed, 2013, WalkType.SINGLE, Gender.MALE, association);
        Animal animal3 = new Animal(3, null, "Descr", "Felix", breed, 2013, WalkType.GROUP, Gender.MALE, association);
        Animal animal4 = new Animal(4, null, "Descr", "Gina", breed, 2013, WalkType.GROUP, Gender.FEMALE, association);

        List<Animal> animals = List.of(animal1, animal2, animal3, animal4);

        Walk walk1 = new Walk(1L, citizen, 1, LocalDateTime.of(2020, 1, 10, 13, 00), WalkType.GROUP, List.of(animal3, animal4));
        Walk walk2 = new Walk(2L, citizen, 1, LocalDateTime.of(2020, 1, 10, 12, 00), WalkType.SINGLE, List.of(animal1));
        Walk walk3 = new Walk(3L, citizen, 2, LocalDateTime.of(2020, 1, 10, 12, 00), WalkType.SINGLE, List.of(animal2));
        Walk walk4 = new Walk(4L, citizen, 1, LocalDateTime.of(2020, 1, 10, 16, 00), WalkType.SINGLE, List.of(animal1));
        Walk walk5 = new Walk(5L, alternateCitizen, 1, LocalDateTime.of(2020, 1, 10, 8, 00), WalkType.SINGLE, List.of(animal1));

        animal1.setWalks(List.of(walk2, walk4, walk5));
        animal2.setWalks(List.of(walk3));
        animal3.setWalks(List.of(walk1));
        animal4.setWalks(List.of(walk1));

        citizen.setWalks(List.of(walk1, walk2, walk3, walk4));
        alternateCitizen.setWalks(List.of(walk5));

        given(animalRepository.getByAssociationId(1L))
                .willReturn(animals);
        given(animalRepository.existsById(1L))
                .willReturn(true);
        given(animalRepository.existsById(2L))
                .willReturn(true);
        given(animalRepository.existsById(3L))
                .willReturn(true);
        given(animalRepository.existsById(4L))
                .willReturn(true);
        given(animalRepository.findById(1L))
                .willReturn(Optional.of(animal1));
        given(animalRepository.findById(2L))
                .willReturn(Optional.of(animal2));
        given(animalRepository.findById(3L))
                .willReturn(Optional.of(animal3));
        given(animalRepository.findById(4L))
                .willReturn(Optional.of(animal4));


        given(citizenRepository.getByUserId(1L))
                .willReturn(citizen);
    }

    @Test
    public void test_addWalk() throws Exception {
        mvc.perform(post("/api/walk")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startTime\":\"2020-01-10T10:00:00\", \"duration\":1, \"animalIds\":[1]}"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_addWalk_noAnimals() throws Exception {
        mvc.perform(post("/api/walk")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startTime\":\"2020-01-10T09:00:00\", \"duration\":1, \"animalIds\":[]}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_addWalk_timePassed() throws Exception { //Vrijeme je postavljeno na 8.1.2020. 00:00
        mvc.perform(post("/api/walk")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startTime\":\"2020-01-07T09:00:00\", \"duration\":1, \"animalIds\":[1]}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_addWalk_citizenNotAvailable() throws Exception {
        mvc.perform(post("/api/walk")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startTime\":\"2020-01-10T11:00:00\", \"duration\":1, \"animalIds\":[1]}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_addWalk_animalNotAvailable() throws Exception {
        mvc.perform(post("/api/walk")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startTime\":\"2020-01-10T07:00:00\", \"duration\":1, \"animalIds\":[1]}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_getAnimals() throws Exception {
        mvc.perform(get("/api/walk/animals")
                .accept(MediaType.APPLICATION_JSON)
                .param("startTime", "2020-01-10T12:00:00") //pretvori se u 13:00 radi vremenskih zona...
                .param("duration", "1")
                .param("associationId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[1]"));

        mvc.perform(get("/api/walk/animals")
                .accept(MediaType.APPLICATION_JSON)
                .param("startTime", "2020-01-10T13:00:00")
                .param("duration", "1")
                .param("associationId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[2,3,4,1]"));

        mvc.perform(get("/api/walk/animals")
                .accept(MediaType.APPLICATION_JSON)
                .param("startTime", "2020-01-10T11:00:00")
                .param("duration", "1")
                .param("associationId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[3,4]"));
    }
}