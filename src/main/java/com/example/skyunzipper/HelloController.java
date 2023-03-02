package com.example.skyunzipper;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class HelloController implements Initializable {

    private String zipPath;
    private static String answerDir;

    private static String dir2;
    @FXML
    private ListView<String> list;
    @FXML
    private Label label;

    @FXML
    private ImageView done;

    @FXML
    private ImageView folderPic;

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> searchlist;
    @FXML
    private ImageView img1;

    @FXML
    private TextField addField;

    @FXML
    private Label check;


    @FXML
    private TextField removeField;

    @FXML
    void add(MouseEvent event) {


        String nameNew = "";
        String addFile = addField.getText();
        nameFiles.add(addFile);
        ObservableList<String> items = null;
        items = FXCollections.observableArrayList(nameFiles);
        list.setItems(items);
        if (addFile != "") {
            check.setText("* Done ! *");
        }
        nameNew = addField.getText();
        try {
            File myObj = new File(Files.createDirectories(Path.of(answerDir)) + "//" + addField.getText());
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        addField.setPromptText("file name to add");


        RotateTransition fight = new RotateTransition();
        fight.setNode(folderPic);
        fight.setDuration(Duration.millis(800));
        fight.setByAngle(360);
        fight.play();


        TranslateTransition translate = new TranslateTransition();
        translate.setNode(done);
        translate.setDuration(Duration.millis(2100));
        translate.setCycleCount(1);
        translate.setByX(790);
        translate.play();
        translate.setFromX(0);
        translate.setFromY(0);
        translate.playFromStart();

    }

    @FXML
    void remove(MouseEvent event) {
        String removeFile = removeField.getText();
        for (int i = 0; i < nameFiles.size(); i++) {
            if (nameFiles.get(i).equals(removeField.getText())) {
                nameFiles.remove(i);
                ObservableList<String> items = null;
                items = FXCollections.observableArrayList(nameFiles);
                list.setItems(items);
            } else check.setText("* Error ! *");
        }
        if (removeFile != "") {
            check.setText("* Done ! *");
        } else {
            check.setText("* Error, No file ! *");
        }

        String path = null;
        try {
            Files.delete(Path.of(answerDir + "\\" + removeField.getText()));
            path = String.valueOf(Path.of(answerDir + "\\" + removeField.getText()));
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (IOException x) {
            System.err.println(x);
        }
        removeField.setPromptText("file name to remove");


        RotateTransition fight = new RotateTransition();
        fight.setNode(folderPic);
        fight.setDuration(Duration.millis(800));
        fight.setByAngle(360);
        fight.play();

    }

    @FXML
    void button1(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null); // you could pass a stage reference here if you wanted.
        if (file != null) {
            label.setText("*" + file.getName() + " selected*");
        }
        String zipFilePath = file.getAbsolutePath();
        zipPath = file.getAbsolutePath();

    }


    static ArrayList<String> nameFiles = new ArrayList();

    @FXML
    void button2(MouseEvent event) throws IOException {

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(null); // you could pass a stage reference here if you wanted.
        answerDir = file.getAbsolutePath();
        extractFolder(zipPath, answerDir);
        File copy = new File(answerDir);
        listOfFiles(copy);
        Collections.sort(nameFiles);

        ObservableList<String> items = null;
        items = FXCollections.observableArrayList(nameFiles);
        list.setItems(items);

        //--------------------------------delete the directories
        File unzippedFolder = new File(answerDir);
        File directoryToBeDeleted = unzippedFolder.listFiles()[0];
        deleteDirectory(directoryToBeDeleted);
        //-----------------------------------show files without folder
        File file1;
        for (int i = 0; i < nameFiles.size(); i++) {
            file1 = new File(answerDir + "//" + nameFiles.get(i));
            file1.createNewFile();
        }

    }

    static boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    @FXML
    void filter(MouseEvent event) throws IOException {


        RotateTransition fight = new RotateTransition();
        fight.setNode(folderPic);
        fight.setDuration(Duration.millis(800));
        fight.setByAngle(360);
        fight.play();


        TranslateTransition translate = new TranslateTransition();
        translate.setNode(done);
        translate.setDuration(Duration.millis(2100));
        translate.setCycleCount(1);
        translate.setByX(790);
        translate.play();
        translate.setFromX(0);
        translate.setFromY(0);
        translate.playFromStart();

        String[] str;
        String path;
        for (int i = 0; i < nameFiles.size(); i++) {
            //----------------------------------------
            File unzippedFolder = new File(answerDir);
            for (File file : unzippedFolder.listFiles())
                file.delete();
            //-------------------------------------manage the files with date

            str = nameFiles.get(i).split("[.]");
            path = (answerDir + "//" + str[1]);
            File dateFolder = new File(path);
            dateFolder.mkdir();
            File innerFile = new File(dateFolder.getPath() + "//" + nameFiles.get(i));
            innerFile.createNewFile();

            //--------------------------------------manage yhe files with format
            for (File file : dateFolder.listFiles())
                if (!file.isDirectory()) {
                    if (str[2].equals("png") || str[2].equals("jpeg") || str[2].equals("jpg") || str[2].equals("gif")) {
                        path = dateFolder.getPath() + "//" + "photo";
                        File formatFolder = new File(path);
                        formatFolder.mkdir();
                        File innerFile2 = new File(formatFolder.getPath() + "//" + nameFiles.get(i));
                        innerFile2.createNewFile();
                    } else if (str[2].equals("mp4") || str[2].equals("mov") || str[2].equals("mkv") || str[2].equals("avl")) {
                        path = dateFolder.getPath() + "//" + "video";
                        File formatFolder = new File(path);
                        formatFolder.mkdir();
                        File innerFile2 = new File(formatFolder.getPath() + "//" + nameFiles.get(i));
                        innerFile2.createNewFile();
                    } else if (str[2].equals("wav") || str[2].equals("aiff")) {
                        path = dateFolder.getPath() + "//" + "voice";
                        File formatFolder = new File(path);
                        formatFolder.mkdir();
                        File innerFile2 = new File(formatFolder.getPath() + "//" + nameFiles.get(i));
                        innerFile2.createNewFile();
                    } else if (str[2].equals("txt")) {
                        path = dateFolder.getPath() + "//" + "text";
                        File formatFolder = new File(path);
                        formatFolder.mkdir();
                        File innerFile2 = new File(formatFolder.getPath() + "//" + nameFiles.get(i));
                        innerFile2.createNewFile();
                    } else if (str[2].equals("pdf")) {
                        path = dateFolder.getPath() + "//" + "pdf";
                        File formatFolder = new File(path);
                        formatFolder.mkdir();
                        File innerFile2 = new File(formatFolder.getPath() + "//" + nameFiles.get(i));
                        innerFile2.createNewFile();
                    }

                }
            //--------------------------------------delete the files
            for (File file : dateFolder.listFiles())
                if (!file.isDirectory())
                    file.delete();
        }

    }

    @FXML
    void sortDateButton(MouseEvent event) {

        ArrayList<MyFile> sortedList = new ArrayList<>();

        String[] str = new String[3];
        for (int i = 0; i < nameFiles.size(); i++) {
            str = nameFiles.get(i).split("[.]");
            sortedList.add(new MyFile(nameFiles.get(i), Integer.parseInt(str[1])));
        }
        MyFile.sortDate(sortedList);// chap
        for (int i = 0; i < sortedList.size(); i++)
            if (sortedList.get(i).date > 2023 || sortedList.get(i).date < 1970)
                sortedList.remove(i);


        for (int i = 0; i < sortedList.size(); i++)
            System.out.println(sortedList.get(i).name);


        ArrayList<String> sortedList2 = new ArrayList<>();
        for (int j = 0; j < sortedList.size(); j++) {
            sortedList2.add(sortedList.get(j).getName());
        }
        ObservableList<String> items = null;
        items = FXCollections.observableArrayList(sortedList2);
        list.setItems(items);


        RotateTransition fight = new RotateTransition();
        fight.setNode(folderPic);
        fight.setDuration(Duration.millis(800));
        fight.setByAngle(360);
        fight.play();

        TranslateTransition translate = new TranslateTransition();
        translate.setNode(done);
        translate.setDuration(Duration.millis(2100));
        translate.setCycleCount(1);
        translate.setByX(790);
        translate.play();
        translate.setFromX(0);
        translate.setFromY(0);
        translate.playFromStart();

    }

    @FXML
    void sortformatButton(MouseEvent event) {
        ArrayList<MyFile> sortedList = new ArrayList<>();
        String[] str = new String[3];
        for (int i = 0; i < nameFiles.size(); i++) {
            str = nameFiles.get(i).split("[.]");
            sortedList.add(new MyFile(nameFiles.get(i), str[2]));
        }
        MyFile.sortFormat(sortedList);// chap

        for (int i = 0; i < sortedList.size(); i++)
            System.out.println(sortedList.get(i).name);

        ArrayList<String> sortedList2 = new ArrayList<>();
        for (int j = 0; j < sortedList.size(); j++) {
            sortedList2.add(sortedList.get(j).getName());
        }
        ObservableList<String> items = null;
        items = FXCollections.observableArrayList(sortedList2);
        list.setItems(items);

        RotateTransition fight = new RotateTransition();
        fight.setNode(folderPic);
        fight.setDuration(Duration.millis(800));
        fight.setByAngle(360);
        fight.play();

        TranslateTransition translate = new TranslateTransition();
        translate.setNode(done);
        translate.setDuration(Duration.millis(2100));
        translate.setCycleCount(1);
        translate.setByX(790);
        translate.play();
        translate.setFromX(0);
        translate.setFromY(0);
        translate.playFromStart();
    }


    @FXML
    void back(MouseEvent event) throws IOException {
        nameFiles.removeAll(nameFiles);
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("welcome.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void back1(MouseEvent event) throws IOException {
        nameFiles.removeAll(nameFiles);
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("welcome.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();

    }


    @FXML
    void search(MouseEvent event) {

        boolean found = false;
        ObservableList<String> items = null;
        String findFile = searchField.getText();
        for (int i = 0; i < nameFiles.size(); i++)
            if (nameFiles.get(i).equals(searchField.getText())) {
                found = true;
                items = FXCollections.observableArrayList(nameFiles.get(i));
                searchlist.setItems(items);
                break;
            }

        if (!found) {
            items = FXCollections.observableArrayList("FILE NOT FOUND !");
            searchlist.setItems(items);
        }

        RotateTransition fight = new RotateTransition();
        fight.setNode(folderPic);
        fight.setDuration(Duration.millis(800));
        fight.setByAngle(360);
        fight.play();
    }

    public static void extractFolder(String zipFile, String extractFolder) {
        try {
            int BUFFER = 2048;
            File file = new File(zipFile);

            ZipFile zip = new ZipFile(file);
            String newPath = extractFolder;

            new File(newPath).mkdir();
            Enumeration zipFileEntries = zip.entries();

            while (zipFileEntries.hasMoreElements()) {

                ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
                String currentEntry = entry.getName();

                File destFile = new File(newPath, currentEntry);

                File destinationParent = destFile.getParentFile();


                destinationParent.mkdirs();

                if (!entry.isDirectory()) {
                    BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry));
                    int currentByte;

                    byte data[] = new byte[BUFFER];


                    FileOutputStream fos = new FileOutputStream(destFile);
                    BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);

                    while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, currentByte);
                    }
                    dest.flush();
                    dest.close();
                    is.close();
                }

            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public static File[] listOfFiles(File dirPath) {
        File filesList2[] = dirPath.listFiles();
        for (File file : filesList2) {
            if (file.isFile()) {
                nameFiles.add(file.getName());
                System.out.println("File : " + file.getName());
            } else {
                listOfFiles(file);
            }
        }
        return filesList2;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TranslateTransition translate = new TranslateTransition();
        translate.setNode(folderPic);
        translate.setDuration(Duration.millis(1500));
        translate.setCycleCount(1);
        translate.setByX(195);
        translate.play();
    }


}


class MyFile {

    public String getName() {
        return name;
    }

    String name;
    int date;
    String format;

    MyFile(String name, int date) {
        this.name = name;
        this.date = date;
    }

    MyFile(String name, String format) {
        this.name = name;
        this.format = format;
    }


    static void sortDate(ArrayList<MyFile> al) {
        for (int z = 0; z < al.size() - 1; z++)
            for (int i = z + 1; i < al.size(); i++) {
                if (al.get(z).date > al.get(i).date) {
                    MyFile temp = al.get(z);
                    al.set(z, al.get(i));
                    al.set(i, temp);
                }

            }
    }

    static void sortFormat(ArrayList<MyFile> al) {
        for (int z = 0; z < al.size() - 1; z++)
            for (int i = z + 1; i < al.size(); i++) {
                if (al.get(z).format.compareTo(al.get(i).format) > 0) {
                    MyFile temp = al.get(z);
                    al.set(z, al.get(i));
                    al.set(i, temp);
                }

            }
    }
}