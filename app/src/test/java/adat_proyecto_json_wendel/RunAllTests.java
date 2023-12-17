package adat_proyecto_json_wendel;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class RunAllTests {

    public static void main(String[] args) {
        // Configurar la solicitud
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                    // Agregar clases a ejecutar
                        selectClass(GestionCSVWriterTest.class),
                        selectClass(AppTest.class),
                        selectClass(GestionPrediccionTest.class),
                        selectClass(DescripcionParserTest.class)
                )
                .build();

        // Configurar el lanzador
        Launcher launcher = LauncherFactory.create();

        // Configurar el listener para recopilar resultados
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);

        // Ejecutar las pruebas
        launcher.execute(request);

        // Obtener y mostrar el resumen de ejecución
        TestExecutionSummary summary = listener.getSummary();
        System.out.println("Total de pruebas ejecutadas: " + summary.getTestsFoundCount());
        System.out.println("Total de pruebas exitosas: " + summary.getTestsSucceededCount());
        System.out.println("Total de pruebas fallidas: " + summary.getTestsFailedCount());
        System.out.println("Total de pruebas abortadas: " + summary.getTestsAbortedCount());
    }
}
