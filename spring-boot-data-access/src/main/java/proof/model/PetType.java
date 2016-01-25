package proof.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PET_TYPES")
public class PetType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_type_seq")
	@SequenceGenerator(name = "pet_type_seq", sequenceName = "PET_TYPE_SEQ")
	protected Long id;
	
	@Column(name = "NAME")
	private String name;

	public Long getId() {
	    return id;
	}

	public void setId(Long id) {
	    this.id = id;
	}

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
