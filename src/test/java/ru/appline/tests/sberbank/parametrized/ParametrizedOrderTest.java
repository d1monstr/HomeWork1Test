package ru.appline.tests.parametrized;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.appline.tests.base.BaseTests;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ParametrizedOrderTest extends BaseTests {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Иванов", "Иван", "Иванович"},
                {"сидоров", "петр", "алексеевич"},
                {"Petrov", "Vasiliy", "Fedorovich"}});
    }

    @Parameterized.Parameter
    public String lastName;

    @Parameterized.Parameter(1)
    public String firstName;

    @Parameterized.Parameter(2)
    public String middleName;


    @Test
    public void test(){

    }

}
