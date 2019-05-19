package com.systelab.seed.model.events;

import com.systelab.seed.model.Patient;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PatientEvent extends Event<Patient> {
    public PatientEvent(Action action, Patient patient) {
        super(action, patient);
    }
}
