package com.systelab.seed.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.systelab.seed.config.security.authentication.TokenProvider;
import com.systelab.seed.model.Address;
import com.systelab.seed.model.Patient;
import com.systelab.seed.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
public class PatientTest {
    private MockMvc mvc;
    private String token;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TokenProvider tokenProvider;

    @MockBean
    private PatientRepository mockPatientRepository;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        token = "Bearer " + tokenProvider.generateToken("user", "USER", 20);
    }


    @Test
    @WithMockUser(roles = "USER")
    public void testGetPatient() throws Exception {
        String id = "a98b8fe5-7cc5-4348-8f99-4860f5b84b13";

        when(mockPatientRepository.findById(isA(UUID.class))).thenReturn(Optional.of(createPatient(id, "A")));

        mvc.perform(get("/seed/v1/patients/{id}", id).header("Authorization", token))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.surname", is("surnameA")));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetPatientWithOutBearer() throws Exception {
        String id = "a98b8fe5-7cc5-4348-8f99-4860f5b84b13";
        token = "Bearer Invalid";

        when(mockPatientRepository.findById(isA(UUID.class))).thenReturn(Optional.of(createPatient(id, "A")));

        mvc.perform(get("/seed/v1/patients/{id}", id).header("Authorization", token))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetAllPatient() throws Exception {
        String id1 = "a98b8fe5-7cc5-4348-8f99-4860f5b84b13";
        String id2 = "a98b8fe5-7cc5-4348-8f99-4860f5b84b14";

        List<Patient> patients = Arrays.asList(createPatient(id1, "A"), createPatient(id2, "B"));

        when(mockPatientRepository.findAll(isA(Pageable.class))).thenReturn(new PageImpl<>(patients));

        mvc.perform(get("/seed/v1/patients").header("Authorization", token))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.content[0].id", is(id1)))
                .andExpect(jsonPath("$.content[0].name", is("patientA")))
                .andExpect(jsonPath("$.content[1].id", is(id2)))
                .andExpect(jsonPath("$.content[1].name", is("patientB")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testInsertPatient() throws Exception {
        String id = "a98b8fe5-7cc5-4348-8f99-4860f5b84b13";

        Patient patient = createPatient(id, "A");

        when(mockPatientRepository.save(any())).thenReturn(patient);

        mvc.perform(post("/seed/v1/patients/patient").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON).content(toJson(patient)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdatePatient() throws Exception {
        String id = "a98b8fe5-7cc5-4348-8f99-4860f5b84b13";

        Patient patient = createPatient(id, "A");

        when(mockPatientRepository.save(any())).thenReturn(patient);
        when(mockPatientRepository.findById(any())).thenReturn(Optional.of(patient));

        mvc.perform(put("/seed/v1/patients/{1}",id).header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON).content(toJson(patient)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(roles = "User")
    public void testDeletePatient() throws Exception {
        String id = "a98b8fe5-7cc5-4348-8f99-4860f5b84b13";

        when(mockPatientRepository.findById(isA(UUID.class))).thenReturn( Optional.of(createPatient(id, "A")));

        mvc.perform(delete("/seed/v1/patients/{1}", id).header("Authorization", token))
                .andExpect(status().is2xxSuccessful());
    }

    private Patient createPatient(String id, String patientName) {
        Patient patient = new Patient();
        patient.setId(UUID.fromString(id));
        patient.setName("patient" + patientName);
        patient.setSurname("surname" + patientName);
        patient.setEmail("patient" + patientName + "@systelab.com");
        patient.setDob(LocalDate.now());
        Address address = new Address();
        address.setCity("city" + patientName);
        address.setCoordinates("coordinates" + patientName);
        address.setStreet("street" + patientName);
        address.setZip("zip" + patientName);
        patient.setAddress(address);
        return patient;
    }

    private static String toJson(Patient patient) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.writeValueAsString(patient);
    }

}
