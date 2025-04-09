package utils;


import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;


import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AfterExecutionCallbacks implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        if (context.getExecutionException().isPresent()) {

            String captureName = String.format("%s-%s-%s",
                    context.getRequiredTestClass().getSimpleName(),
                    context.getDisplayName(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss"))
            );

            // Cast the test instance into BaseTest and call takeScreenshot
            ((BaseTest) context.getRequiredTestInstance()).takeScreenshot(captureName);
        }
    }
}