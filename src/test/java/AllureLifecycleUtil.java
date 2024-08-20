import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.FixtureResult;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResultContainer;

import java.util.Arrays;
import java.util.UUID;

public class AllureLifecycleUtil {

    private static String testResultUuid;
    private static final AllureLifecycle lifecycle = Allure.getLifecycle();

    public static String createCustomPrepareFixture(String containerName, String fixtureName, String fixtureUuid) {
        testResultUuid = getCurrentTestResultUuid();
        TestResultContainer container = createTestResultContainer(containerName, testResultUuid);
        lifecycle.startTestContainer(container);
        lifecycle.startPrepareFixture(container.getUuid(), fixtureUuid, new FixtureResult().setName(fixtureName));
        return container.getUuid();
    }

    public static String createCustomTeardownFixture(String containerName, String fixtureName, String fixtureUuid) {
        TestResultContainer container = createTestResultContainer(containerName, testResultUuid);
        lifecycle.startTestContainer(container);
        lifecycle.startTearDownFixture(container.getUuid(), fixtureUuid, new FixtureResult().setName(fixtureName));
        return container.getUuid();
    }

    public static void stopCustomFixture(String fixtureUuid, String containerUuid) {
        lifecycle.updateFixture(fixtureUuid, fixtureResult -> {
            boolean allStepsPassed = fixtureResult.getSteps().stream()
                    .allMatch(step -> step.getStatus() == Status.PASSED);

            fixtureResult.setStatus(allStepsPassed ? Status.PASSED : Status.FAILED);
        });

        lifecycle.stopFixture(fixtureUuid);
        lifecycle.stopTestContainer(containerUuid);
        lifecycle.writeTestContainer(containerUuid);
        lifecycle.setCurrentTestCase(testResultUuid);
    }

    private static String getCurrentTestResultUuid() {
        return lifecycle.getCurrentTestCase()
                .orElseThrow(() -> new RuntimeException("No current test case found"));
    }

    private static TestResultContainer createTestResultContainer(String name, String... children) {
        return new TestResultContainer().setUuid(UUID.randomUUID().toString())
                .setName(name)
                .setChildren(Arrays.asList(children));
    }
}
