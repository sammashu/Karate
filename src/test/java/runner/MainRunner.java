package runner;

import com.intuit.karate.KarateOptions;
import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.junit4.Karate;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@KarateOptions(tags = {"~@ignore"}, features = {"src/test/java/features"})
public class MainRunner {

    @BeforeClass
    public static void beforeClass() throws Exception {

    }

    @Test
    public void testParallel() {
        System.setProperty("karate.env", "demo"); // ensure reset if other tests (e.g. mock) had set env in CI
        Results results = Runner.parallel(getClass(), 5);
        generateReport(results.getReportDir());
        Assert.assertTrue(results.getErrorMessages(), results.getFailCount() == 0);
    }

    public static void generateReport(String karateOutputPath) {
        Collection<File> jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[] {"json"}, true);
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(new File("target"), "demo");
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }
}
