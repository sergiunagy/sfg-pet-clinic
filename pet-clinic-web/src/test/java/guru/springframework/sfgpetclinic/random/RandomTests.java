package guru.springframework.sfgpetclinic.random;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


class Human {
    private String name;
    private int age;

    public Human(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // standard constructors, getters/setters, equals and hashcode
}

@ExtendWith(SpringExtension.class)
public class RandomTests {


    @Test
    public void givenPreLambda_whenSortingEntitiesByName_thenCorrectlySorted() {
        List<Human> humans = Lists.newArrayList(
                new Human("Sarah", 10),
                new Human("Jack", 12)
        );

        Collections.sort(humans, new Comparator<Human>() {
            @Override
            public int compare(Human h1, Human h2) {
                return h1.getName().compareTo(h2.getName());
            }
        });
    }

    @Test
    public void compareTest() {
        List<Human> humans = Lists.newArrayList(
                new Human("Sarah", 10),
                new Human("Jack", 12)
        );
        humans.sort((final Human h1, final Human h2) -> h1.getName().compareTo(h2.getName() ));

    }

    @Test
    public void compareDates(){
        // create a list of Activity objects containing dates.
            
        // get a list of dates
        // check what
    }
}
