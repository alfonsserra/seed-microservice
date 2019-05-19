package com.systelab.seed.rest;

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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
public class PatientTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TokenProvider tokenProvider;

    @MockBean
    private PatientRepository mockPatientRepository;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }


    @Test
    @WithMockUser(roles = "USER")
    public void testGetPatient() throws Exception {
        String id="a98b8fe5-7cc5-4348-8f99-4860f5b84b13";

        Optional<Patient> patient = Optional.of(createPatient(id,"A"));
        when(mockPatientRepository.findById(isA(UUID.class))).thenReturn(patient);
        mvc.perform(get("/seed/v1/patients/{id}", id)
                .header("Authorization", "Bearer " + tokenProvider.generateToken("user", "USER", 20)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(id))).andExpect(jsonPath("$.surname", is("surnameA")));
    }

    private Patient createPatient(String id,String patientName) {
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

}
