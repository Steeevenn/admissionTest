package com.sprint3.admission_test.application.useCases;

import com.sprint3.admission_test.application.ports.in.IMedicationUseCase;
import com.sprint3.admission_test.application.ports.out.IMedicationRepository;
import com.sprint3.admission_test.domain.exceptions.NotFoundException;
import com.sprint3.admission_test.domain.model.Category;
import com.sprint3.admission_test.domain.model.Medication;
import com.sprint3.admission_test.infrastructure.adapter.out.persistence.jpaRepository.CategoryJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicationUseCaseImpl implements IMedicationUseCase {

    @Autowired
    private IMedicationRepository medicationRepository;

    private final CategoryJpaRepository categoryJpaRepository;

    public MedicationUseCaseImpl(CategoryJpaRepository categoryJpaRepository) {
        this.categoryJpaRepository = categoryJpaRepository;
    }

    @Override
    public Medication getMedicationById(Long id) {
        return medicationRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "Could not find medication with ID: " + id
        ));
    }


    @Override
    public Medication enviarMedicamento(Medication medication) {

        List<Category> categoriasList = categoryJpaRepository.findAll();

        StringBuilder categoryDefined = new StringBuilder();
        if (!categoriasList.isEmpty()) {
            for (Category category : categoriasList) {
                categoryDefined.append(category.getName());
            }
        }

//Traer la categoria y no crear una nueva
        Long categoryId = medication.getCategory().getId();
        Category category = categoryJpaRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));

        medication.setCategory(category);


        Medication retornoMedication = null;

            Optional<Medication> medicamiento01 = medicationRepository.postMedication(medication);
            medicationRepository.saveMedication(medication).orElseThrow(() -> new NotFoundException("No se pudo agregar medicamento"));

            retornoMedication = medicamiento01.get();

        return retornoMedication;


    }


}
