package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.repositories.VetsRepository;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class VetSDJPAService implements VetService{

    private final VetsRepository vetsRepository;

    public VetSDJPAService(VetsRepository vetsRepository) {
        this.vetsRepository = vetsRepository;
    }

    @Override
    public Set<Vet> findAll() {
        Set<Vet> vets = new HashSet<>();
        vetsRepository.findAll().forEach(vets::add);
        return vets;
    }

    @Override
    public Vet findById(Long aLong) {
        return vetsRepository.findById(aLong).orElse(null);
    }

    @Override
    public Vet save(Vet obj) {
        return vetsRepository.save(obj);
    }

    @Override
    public void delete(Vet obj) {
        vetsRepository.delete(obj);
    }

    @Override
    public void deleteById(Long aLong) {
        vetsRepository.deleteById(aLong);
    }
}
