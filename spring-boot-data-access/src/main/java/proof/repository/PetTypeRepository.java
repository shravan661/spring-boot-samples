package proof.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import proof.model.PetType;

public interface PetTypeRepository extends JpaRepository<PetType, Long> {
    // Nothing to add
}
