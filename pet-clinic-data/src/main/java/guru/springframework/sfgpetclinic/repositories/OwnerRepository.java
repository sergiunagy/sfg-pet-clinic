package guru.springframework.sfgpetclinic.repositories;

import guru.springframework.sfgpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

    Owner findByLastName(String lastName);

    @Override
    <S extends Owner> S save(S entity);

    @Override
    <S extends Owner> Iterable<S> saveAll(Iterable<S> entities);

    @Override
    Optional<Owner> findById(Long aLong);

    @Override
    Iterable<Owner> findAll();

    List<Owner> findAllByLastNameLike(String lastName);

}
