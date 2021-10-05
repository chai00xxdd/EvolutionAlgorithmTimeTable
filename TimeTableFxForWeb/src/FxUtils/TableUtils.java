package FxUtils;

import java.util.concurrent.atomic.AtomicLong;

import javafx.application.Platform;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableUtils {

	
	public static void FitToDataContent(TableView<?> view)
	{
		//view.setColumnResizePolicy((param) -> true);
		Platform.runLater(()-> customResize(view) );
	}
	
	public static void customResize(TableView<?> view) {
        
	    AtomicLong width = new AtomicLong();
	    view.getColumns().forEach(col -> {
	        width.addAndGet((long) col.getWidth());
	    });
	    double tableWidth = view.getWidth();

	    if (tableWidth > width.get()) {
	        TableColumn<?, ?> col = view.getColumns().get(view.getColumns().size()-1);
	        col.setPrefWidth(col.getWidth()+(tableWidth-width.get()));
	    }

	}
}
