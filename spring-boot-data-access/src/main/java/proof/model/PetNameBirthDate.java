package proof.model;

import java.util.Date;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class PetNameBirthDate {

	private final String name;
    private final Date birthDate;
    
    public PetNameBirthDate(String name, Date birthDate) {
		this.name = name;
		this.birthDate = birthDate;
	}

	public String getName() {
		return name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

}
