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
import spark.Spark;

import javax.xml.bind.JAXB;
import java.io.StringWriter;
import java.util.Optional;

import static spark.Spark.get;


public class MainApp extends Application {

    private static MainWindowController controller;

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
        //Wahl testwahl = Wahl.getInstance();

        //Urne testurne = new Urne("Testurne 1");
        //testwahl.addUrne(testurne);

        //Liste testliste = new Liste("Die tolle Testliste","TEST");
        //Kandidat testkandidat = new Kandidat("Max Mustermann");
        //testliste.addKandidat(testkandidat);
        //testwahl.addListe(testliste);

        //Ergebnis ergebnis = new Ergebnis(testurne);
        //Listenergebnis testlst = new Listenergebnis();
        //testlst.setListe(testliste);
        //Kandidatenergebnis testknd = new Kandidatenergebnis(testkandidat);
        //testlst.addKandidatenergebnis(testknd);
        //ergebnis.addListenergebnis(testlst);
        //testwahl.setErgebnis(ergebnis);

        //File file = new File("testwahl.xml");
        //JAXB.marshal(testwahl,file);
        //Wahl testwahl2 = JAXB.unmarshal(file, Wahl.class);
        //System.out.println(testwahl.getListen().get(0).getName());

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

    @Override
    public void stop() throws Exception {
        super.stop();
        stopServer();
    }

    private void startServer() {
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
