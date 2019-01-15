package net.cgro.votemanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.cgro.votemanager.controller.MainWindowController;
import net.cgro.votemanager.model.Wahl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import javax.xml.bind.JAXB;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import static spark.Spark.get;


public class MainApp extends Application {

    final Logger log = LoggerFactory.getLogger(MainApp.class);

    public static final int AUTOSAVE_MILLISECONDS = 5 * 60 * 1000;
    private static MainWindowController controller;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("YYYY-MM-dd_hh-mm-ss").withZone(ZoneOffset.UTC);
    private Timer timer = new Timer();
    private Path autosavePath = Paths.get("votemanager_autosave");

    public static MainWindowController getController() {
        return controller;
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        startAutosave();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
        fxmlLoader.load();
        controller = fxmlLoader.getController();

        Scene scene = new Scene(fxmlLoader.getRoot());
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("VoteManager");
        stage.setScene(scene);
        stage.show();
        scene.getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::handleClose);

        startServer();
    }

    private void startAutosave() {
        createAutosaveDirectoryIfNotExists();
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        autosave();
                    }
                }, AUTOSAVE_MILLISECONDS, AUTOSAVE_MILLISECONDS);
    }

    private void autosave() {
        try {
            Instant now = Instant.now();
            createAutosaveDirectoryIfNotExists();
            String filename = "VoteManager_autosave_" + FORMATTER.format(now) + ".xml";
            File file = autosavePath.resolve(filename).toFile();
            log.info("Automatically saving to {}", file.getAbsolutePath());
            JAXB.marshal(Wahl.getInstance(), file);
        } catch (Exception e) {
            log.warn("Could not autosave", e);
        }
    }

    private void createAutosaveDirectoryIfNotExists() {
        if (!Files.isDirectory(autosavePath)) {
            log.info("Creating directory {}", autosavePath.toAbsolutePath());
            try {
                Files.createDirectories(autosavePath);
            } catch (IOException e) {
                log.error("Could not create autosave directory {}", autosavePath.toAbsolutePath(), e);
            }
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        stopServer();
        stopAutosave();
    }

    private void stopAutosave() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void startServer() {
        get("/up", ((request, response) -> "up"));
        get("/status", (reqest, response) -> {
            response.type("text/xml; charset=utf-8");
            StringWriter stringWriter = new StringWriter();
            JAXB.marshal(Wahl.getInstance(), stringWriter);
            return stringWriter.toString();
        });
    }

    private void stopServer() {
        Spark.stop();
    }

    private void handleClose(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Programm schließen");
        alert.setHeaderText("Das Programm wird beendet.");
        alert.setContentText("Nicht gespeicherte Änderungen gehen dabei verloren.");
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(480, 150);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() != ButtonType.OK) {
            event.consume();
        }
    }

}
