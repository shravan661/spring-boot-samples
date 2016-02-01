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
	
	@Query(value = "SELECT * FROM SBDATAAC_PETS", nativeQuery = true)
	List<Pet> findAllUsingNativeQuery() throws DataAccessException;
	
	@Query(value = "SELECT pet.*, type.PET_TYPE_ID AS typeid, type.NAME as typename FROM SBDATAAC_PETS pet INNER JOIN SBDATAAC_PET_TYPES type ON pet.PET_TYPE_ID=type.PET_TYPE_ID", nativeQuery = true)
	List<Object[]> findAllUsingJoinAndNativeQuery() throws DataAccessException;
	
	//
	// Procedures defined into the schema-hsqldb.sql file 
	//
	
	// Using @NamedStoredProcedureQuery
	
	@Procedure("PRC_PLUS1INOUT")
	Integer namedProcedure(Integer arg);
	
	@Procedure(name = "Pet.plus1")
	Integer customNamedProcedure(@Param("arg") Integer arg);

	@Procedure
	Integer plus1(@Param("arg") Integer arg);
	
	// Direct procedure call
	
	@Procedure("PRC_PLUS2INOUT")
	Integer notNamedProcedure(@Param("arg") Integer arg);
	
	// Method name equal to procedure name replacing camelcase with underscores
	@Procedure()
	Integer prc_plus2inout(@Param("arg") Integer arg);
}
