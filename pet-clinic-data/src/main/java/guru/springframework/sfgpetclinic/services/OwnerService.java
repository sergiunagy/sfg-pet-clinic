package guru.springframework.sfgpetclinic.services;

import guru.springframework.sfgpetclinic.model.Owner;

import java.util.List;

public interface OwnerService extends CrudService<Owner, Long>{

    Owner findByLastName(String lastName);

    @Override
    Owner findById(Long aLong);

    List<Owner> findAllByLastNameLike(String lastName);

}
