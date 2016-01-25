package proof.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import proof.model.Pet;

public interface PetRepository extends JpaRepository<Pet, Long>, PetRepositoryCustom {

	@Query("SELECT pet FROM Pet pet JOIN FETCH pet.type")
	List<Pet> findAllUsingJoinFetch() throws DataAccessException;

	@EntityGraph(value = "Pet.type")
	@Query("SELECT pet FROM Pet pet")
	List<Pet> findAllUsingEntityGraph() throws DataAccessException;
	
	@Query(value = "SELECT * FROM PETS", nativeQuery = true)
	List<Pet> findAllUsingNativeQuery() throws DataAccessException;
	
	@Query(value = "SELECT pet.*, type.id AS typeid, type.name as typename FROM PETS pet INNER JOIN PET_TYPES type ON pet.type_id=type.id", nativeQuery = true)
	List<Object[]> findAllUsingJoinAndNativeQuery() throws DataAccessException;
	
	//
	// Procedures defined into the schema-hsqldb.sql file 
	//
	
	// Using @NamedStoredProcedureQuery
	
	@Procedure("plus1inout")
	Integer namedProcedure(Integer arg);
	
	@Procedure(name = "Pet.plus1")
	Integer customNamedProcedure(@Param("arg") Integer arg);

	@Procedure
	Integer plus1(@Param("arg") Integer arg);
	
	// Direct procedure call
	
	@Procedure("plus2inout")
	Integer notNamedProcedure(@Param("arg") Integer arg);
	
	@Procedure()
	Integer plus2inout(@Param("arg") Integer arg);
}
