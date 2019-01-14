package net.cgro.votemanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.cgro.votemanager.controller.MainWindowController;


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
    }

}
