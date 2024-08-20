import io.qameta.allure.Allure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ExampleExtension.class)
public class SampleTest {

    @Test
    public void simpleTest() {
        Allure.step("Step in TestBody");
    }
}
