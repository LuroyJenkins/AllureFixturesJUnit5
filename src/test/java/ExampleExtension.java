import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ExampleExtension implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        String containerUuid = AllureLifecycleUtil.createCustomPrepareFixture("beforeEachCallback",
                "Before Each from JUnit Extension", context.getUniqueId());

        Allure.step("Step from beforeEach in JUnit Extension");

        AllureLifecycleUtil.stopCustomFixture(context.getUniqueId(), containerUuid);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        String containerUuid = AllureLifecycleUtil.createCustomTeardownFixture("afterEachCallback",
                "After Each from JUnit Extension", context.getUniqueId());

        Allure.step("Step from afterEach in JUnit Extension");

        AllureLifecycleUtil.stopCustomFixture(context.getUniqueId(), containerUuid);
    }
}
