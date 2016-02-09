package proof.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.ConstructorExpression;

import proof.model.Pet;
import proof.model.PetNameBirthDate;
import proof.model.QPet;

@Transactional(readOnly = true)
public class PetRepositoryImpl extends QueryDslRepositorySupport implements PetRepositoryCustom {

	public PetRepositoryImpl() {
		super(Pet.class);
	}

	/**
	 * Sample method implementation based on QueryDSL.
	 */
	@Override
	public List<Pet> findBornBetweenDates(Date dateFirst, Date dateLast) {
		QPet pet = QPet.pet;
		BooleanBuilder where = new BooleanBuilder();

		if (dateFirst != null && (dateLast == null || dateFirst.before(dateLast))) {
			where.and(pet.birthDate.after(dateFirst));
		}

		if (dateLast != null) {
			where.and(pet.birthDate.before(dateLast));
		}

		return from(pet).where(where).innerJoin(pet.type).fetch().list(pet);
	}

	@Override
	public List<PetNameBirthDate> findAllNameAndBirthDate() {
		QPet pet = QPet.pet;
		return from(pet).list(ConstructorExpression.create(PetNameBirthDate.class, 
				pet.name, pet.birthDate));
	}
}
