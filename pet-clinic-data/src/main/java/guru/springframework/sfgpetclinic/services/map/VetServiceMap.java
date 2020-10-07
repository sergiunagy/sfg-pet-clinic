package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.SpecialtyService;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VetServiceMap extends AbstractMapService<Vet, Long> implements VetService {

    private final SpecialtyService specialtyService;

    public VetServiceMap(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @Override
    public Set<Vet> findAll() {
        return super.findAll();
    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Vet save(Vet vet) {

        // check the vet for specialties
        // check each specialty for an ID - aka check if it is already in our databse
        // if not, then save and save the id to the current object id
        if (null != vet) {
            if (vet.getSpecialties().size() > 0) {
                vet.getSpecialties().forEach(specialty -> {
                    if (specialty.getId() == null) {
                        specialty.setId(specialtyService.save(specialty).getId());
                    }
                });
            }

            return super.save(vet);
        } else {
            return null;
        }

    }

    @Override
    public void delete(Vet vet) {
        super.delete(vet);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
