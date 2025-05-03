package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class ManualSQLRunner implements ApplicationRunner {

    @Autowired
    private DataSource dataSource;

    @Value("${sql.runner.enabled:true}")
    private boolean sqlRunnerEnabled;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!sqlRunnerEnabled) {
            System.out.println("ℹ️ SQL Runner is disabled. Skipping data.sql execution.");
            return;
        }

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery("SELECT COUNT(*) FROM customer");
            resultSet.next();
            int rowCount = resultSet.getInt(1);

            if (rowCount == 0) {
                long startTime = System.currentTimeMillis();

                ResourceDatabasePopulator populator = new ResourceDatabasePopulator(false, false, "UTF-8",
                        new ClassPathResource("data.sql"));
                populator.execute(dataSource);

                long duration = System.currentTimeMillis() - startTime;
                System.out.println("✅ data.sql executed in " + duration + " ms.");
            } else {
                System.out.println("ℹ️ data.sql skipped: 'customer' table already contains data.");
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to execute data.sql: " + e.getMessage());
            e.printStackTrace();
        }
    }
}