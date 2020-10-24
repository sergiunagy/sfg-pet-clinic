package guru.springframework.sfgpetclinic.services;

import guru.springframework.sfgpetclinic.model.PetType;

import java.util.Set;

public interface PetTypeService extends CrudService<PetType, Long>{

    @Override
    Set<PetType> findAll();
}
