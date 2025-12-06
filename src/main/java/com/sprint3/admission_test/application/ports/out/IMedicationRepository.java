package com.sprint3.admission_test.application.ports.out;

import com.sprint3.admission_test.domain.model.Medication;

import java.util.Optional;

public interface IMedicationRepository {

    Optional<Medication> findById(Long id);

    Optional<Medication> postMedication(Medication medication);

    Optional<Medication> saveMedication(Medication medication);
}