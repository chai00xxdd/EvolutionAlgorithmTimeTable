package pages.ShecdulePage;


import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import school.Leacture;

public class ShecdulePageController {
	
	private List<ShecduleData> dataList = new ArrayList<>();
	
    @FXML
    private Button collitionButton;

    @FXML
    private VBox shecduleBox;

	
	public void addShecdule(ShecduleData data)
	{
		dataList.add(data);
		setShecduleData(dataList);
	}
	
	public List<ShecduleData> getShecduleDatListCopy()
	{
		return new ArrayList<>(dataList);
	}
	
	public void setShecduleData(List<ShecduleData> newData)
	{
		
		shecduleBox.getChildren().clear();
		if(newData==null)
		{
			dataList= new ArrayList<>();
		}
		else
		{
			dataList =newData;
			if(dataList.size()>0)
			{
				for(String data : dataList.get(0).getData())
				{
				//	System.out.println("Here");
					shecduleBox.getChildren().add(new Label(data));
				//	System.out.println(data);
				}
			}
			
		}
		
	}
	
	
	
}
