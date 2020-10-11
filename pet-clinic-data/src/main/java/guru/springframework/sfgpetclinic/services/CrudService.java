package guru.springframework.sfgpetclinic.services;

public interface CrudService <T, ID>{


    T findById(ID id);

    T save(T obj);

    void delete(T obj);

    void deleteById(ID id);
}
