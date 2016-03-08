package proof.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import proof.SpringBootDataAccessApplication;
import proof.model.Pet;
import proof.model.PetNameBirthDate;
import proof.model.PetType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootDataAccessApplication.class)
public class PetRepositoryIntegrationTest {
	private static final Logger LOG = 
			LoggerFactory.getLogger(PetRepositoryIntegrationTest.class);

	@Autowired
	PetRepository repository;

	@Autowired
	PetTypeRepository petTypeRepository;

	@Test
	public void testPetTypes() {
		LOG.debug("TEST PetTypes");
		List<PetType> findPetTypes = petTypeRepository.findAll();
		assertNotNull(findPetTypes);
		assertEquals(6, findPetTypes.size());
	}
	
	@Test
	public void testFindAllDefault() throws Exception {
		LOG.debug("TEST Find All Pets default method");
		List<Pet> findAllPets = repository.findAll();
		assertNotNull(findAllPets);
		assertEquals(13, findAllPets.size());
	}
	
	@Test
	public void testFindAllUsingJoinFetch() throws Exception {
		LOG.debug("TEST Find All Pets using join fetch");
		List<Pet> findAllPets = repository.findAllUsingJoinFetch();
		assertNotNull(findAllPets);
		assertEquals(13, findAllPets.size());
	}
	
	@Test
	public void testFindAllUsingEntityGraph() throws Exception {
		LOG.debug("TEST Find All Pets using EntityGraph");
		List<Pet> findAllPets = repository.findAllUsingEntityGraph();
		assertNotNull(findAllPets);
		assertEquals(13, findAllPets.size());
	}
	
	@Test
	public void testFindAllUsingNativeQuery() throws Exception {
		LOG.debug("TEST Find All Pets using native query");
		List<Pet> findAllPets = repository.findAllUsingNativeQuery();
		assertNotNull(findAllPets);
		assertEquals(13, findAllPets.size());
	}
	
	@Test
	public void testFindAllUsingJoinAndNativeQuery() throws Exception {
		LOG.debug("TEST Find All Pets using native query and a join with PET_TYPES");
		List<Object[]> findAllPets = repository.findAllUsingJoinAndNativeQuery();
		assertNotNull(findAllPets);
		assertEquals(13, findAllPets.size());
	}

	@Test
	public void testCreatePet() {
		LOG.debug("TEST create a new Pet");		
		
		Pet pet = new Pet();
		pet.setBirthDate(new Date());
		pet.setName("Test pet");
		pet.setType(petTypeRepository.findOne(new Long(2)));
		
		Pet savedPet = repository.save(pet);
		
		LOG.debug("Created pet with id = {}", savedPet.getId());
		
		assertEquals(pet.getBirthDate(), savedPet.getBirthDate());
		assertEquals(pet.getName(), savedPet.getName());
		assertEquals(pet.getType(), savedPet.getType());
		
		repository.delete(savedPet);
	}
	
	@Test
	public void testFindBornBetweenDates() {
		LOG.debug("TEST find Pets born between dates");
		
		List<Pet> pets = repository.findBornBetweenDates(null, null);
		assertEquals(13, pets.size());
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2011, 01, 01);
		Date dateFirst = calendar.getTime();
		calendar.set(2011, 12, 31);
		Date dateLast = calendar.getTime();
		
		pets = repository.findBornBetweenDates(dateFirst, null);
		assertEquals(6, pets.size());
		
		pets = repository.findBornBetweenDates(null, dateLast);
		assertEquals(9, pets.size());
		
		pets = repository.findBornBetweenDates(dateFirst, dateLast);
		assertEquals(2, pets.size());
		
	}
	
	@Test
	public void testNamedProcedure() throws Exception {
		LOG.debug("TEST Calling a named procedure by its database name");
		assertEquals(new Integer(2), repository.namedProcedure(1));
	}
	
	@Test
	public void testCustomNamedProcedure() throws Exception {
		LOG.debug("TEST Calling a named procedure by its custom name");
		assertEquals(new Integer(2), repository.customNamedProcedure(1));
	}
	
	@Test
	public void testMethodNamedProcedure() throws Exception {
		LOG.debug("TEST Calling a named procedure by the repository method name");
		assertEquals(new Integer(2), repository.plus1(1));
	}
	
	@Test
	public void testNotNamedProcedure() throws Exception {
		LOG.debug("TEST Calling a not named procedure by its database name");
		assertEquals(new Integer(3), repository.getValuePlus2(1));
	}

	@Test
	public void testNotNamedProcedure2() throws Exception {
		LOG.debug("TEST Calling a not named procedure 2 by its database name");
		assertEquals(new Integer(3), repository.prc_plus2inout(1));
	}

	@Test
	public void testLogging() throws Exception {
		LOG.debug("TEST logging usage with parameters value1={}, value2={}", 10, 20);
	}

	@Test
	public void testPage() throws Exception {
		LOG.debug("TEST query data pagination");
		
		Sort sortById = new Sort(Direction.ASC, "id");
		Sort sortByName = new Sort(Direction.ASC, "name");
				
		Page<Pet> findAllPets = repository.findAll(new PageRequest(0, 4, sortById));
		assertNotNull(findAllPets);
		assertEquals(4, findAllPets.getSize());
		
		findAllPets = repository.findAll(new PageRequest(0, 4, sortByName.and(sortById)));
		assertNotNull(findAllPets);
		assertEquals(4, findAllPets.getSize());
	}
	
	@Test
	public void testProjection() {
		LOG.debug("TEST find Pets using PetNameBirthDate projection");
		
		List<PetNameBirthDate> pets = repository.findAllNameAndBirthDate();
		assertEquals(13, pets.size());
	}

}
