package application;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Controller{
	@FXML
	private Label positive;
	@FXML
	private Label negative;
	@FXML
	private Label file;
	@FXML
    private BarChart<?, ?> chart;
	@FXML
    private CategoryAxis x;
	@FXML
    private NumberAxis y;
	@FXML
	private TextArea mailBody;
	@FXML
	private ProgressBar pBar;
	


	
	@FXML
	public void processData(ActionEvent e) {
		final File folder = new File("/Users/pallavsaxena/Desktop/XYZ");
		TrainMachine Tm = new TrainMachine();
		Tm.prepareHashmap(folder);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	public void processFile(ActionEvent e) {
		String x[]=new String[2];
		String extension="";
		int i=file.getText().lastIndexOf('.');
		if(i>0) {
			extension=file.getText().substring(i+1);
			System.out.println(extension);
		}
		try {
			File file1 = new File(file.getText());
			BufferedReader BF = new BufferedReader(new FileReader(file1));
			if(extension.equals("txt")) {
            	    String temp="";
                String s;
                while ((s = BF.readLine()) != null) {
                    temp=temp+s;
                }
                
                mailBody.setText(temp);
                mailBody.setEditable(false);
            Testing t = new Testing();
    			t.getList();
    			String tempS = t.getProbability(temp);
    			x = tempS.split("\\s");
    			positive.setText(x[0]);
    			negative.setText(x[1]);
    			
    			chart.getData().clear();
    			chart.layout();
    			XYChart.Series set1=new XYChart.Series<>();
    			chart.setAnimated(false);
    			set1.getData().add(new XYChart.Data("Ham",Double.parseDouble(x[0])));
    			set1.getData().add(new XYChart.Data("Spam",Double.parseDouble(x[1])));
    			chart.getData().addAll(set1);
    			BF.close();
    			}
            else {
            	TesseractExample TE=new TesseractExample();
            String temp=	TE.getTextFromImage(file.getText());
            mailBody.setEditable(false);
            mailBody.setText(temp);
            mailBody.setEditable(false);
            Testing t = new Testing();
			t.getList();
			String tempS = t.getProbability(temp);
			x = tempS.split("\\s");
			positive.setText(x[0]);
			negative.setText(x[1]);
			chart.getData().clear();
			chart.layout();
			XYChart.Series set1=new XYChart.Series<>();
			chart.setAnimated(false);
			set1.getData().add(new XYChart.Data("Ham",Double.parseDouble(x[0])));
			set1.getData().add(new XYChart.Data("Spam",Double.parseDouble(x[1])));
			chart.getData().addAll(set1);
            }
            
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	public void dataSet(ActionEvent e) {
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("Dataset.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	@FXML
	public void chooseFile(ActionEvent e) {
		FileChooser fc=new FileChooser();
		fc.setInitialDirectory(new File("/Users/pallavsaxena/Desktop"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("Email Files","*.txt","*.jpg","*.jpeg","*.png"));
		File selectedFile=fc.showOpenDialog(null);
		
		if(selectedFile!=null) {
			file.setText(selectedFile.getAbsolutePath());
		}else {
			file.setText("file is not valid");
		}
	}
	@FXML
	public void showImage(ActionEvent e) {
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("ImageFile.fxml").openStream());
			ImageFileController IFC=(ImageFileController)loader.getController();
			IFC.getImage(file.getText());
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	
}