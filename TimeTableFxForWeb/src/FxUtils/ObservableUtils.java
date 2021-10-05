package FxUtils;

import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableUtils {

	public static <T> ObservableList<T> CollectionToObseravbleList(Collection<T> collection)
	{
		ObservableList<T> obserrvableList = FXCollections.observableArrayList();
		
		for(T item : collection)
		{
			obserrvableList.add(item);
		}
		
		return obserrvableList;
	}
}
