package guru.springframework.sfgpetclinic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "pets")
public class Pet extends BaseEntity{

    @Column(name="name")
    private String name;

    @Column(name="birth_date")
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "pet_type_id")
    private PetType petType;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    /*Why specify mapped by : https://stackoverflow.com/questions/24584411/what-if-we-do-not-specify-mappedby-attribute-in-onetomany-annotation
    * https://www.baeldung.com/jpa-joincolumn-vs-mappedby*/
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
    private Set<Visit> visits = new HashSet<>();

    @Builder
    public Pet(Long id, String name, LocalDate birthDate, PetType petType, Owner owner, Set<Visit> visits) {
        super(id);
        this.name = name;
        this.birthDate = birthDate;
        this.petType = petType;
        this.owner = owner;
        this.visits = visits;
    }

    public Pet() {
    }
}
