package hooks;

import cucumber.api.event.*;
import utils.ExcelUtility;

public class CucumberHooks implements ConcurrentEventListener {

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestRunStarted.class, beforeAll);
        eventPublisher.registerHandlerFor(TestRunFinished.class, afterAll);
    }

    private EventHandler<TestRunStarted> beforeAll = event -> {
        ExcelUtility.createExcelAndSheet();
    };

    private EventHandler<TestRunFinished> afterAll = event -> {
        // something that needs doing after everything
    };
}
