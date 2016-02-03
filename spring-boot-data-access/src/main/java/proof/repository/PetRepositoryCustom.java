package proof.repository;

import java.util.Date;
import java.util.List;

import proof.model.Pet;
import proof.model.PetNameBirthDate;

public interface PetRepositoryCustom {

	List<Pet> findBornBetweenDates(Date dateFirst, Date dateLast);
	
	List<PetNameBirthDate> findAllNameAndBirthDate();
}
