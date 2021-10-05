package pages.AlgorithmOperation;

import javax.swing.plaf.synth.SynthColorChooserUI;



import EvolutionAlgorithmProperty.EvolutionAlgorithmPropertyManager;
import clientServer.HttpClientETT;
import evolution.algorithm.AlgorithmProgress;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressBar;
import wrappers.AtomicString;

public class AlgorithmProgressThread extends Thread {
	
	EvolutionAlgorithmPropertyManager manager = null;
	HttpClientETT client;
	private boolean running = true;
	private AtomicString problemId;
	private XYChart.Series graphSeries;
	
	
	public synchronized boolean isRunning() {
		return running;
	}
	
	public synchronized void killThread()
	{
		running = false;
	}
	public AlgorithmProgressThread(EvolutionAlgorithmPropertyManager algorithmPropertyManager,HttpClientETT client,AtomicString problemId,XYChart.Series graphSeries)
	{
		this.manager = algorithmPropertyManager;
		this.client = client;
		this.problemId = problemId;
		this.graphSeries = graphSeries;
		setDaemon(true);
	}
	public void run()
	{
		while(isRunning())
		{
			try {
				AlgorithmProgress progress = client.getAlgorithmProgress(problemId.getString());
				if(progress == null)
				{
					continue;
				}
				Platform.runLater(()->{
					manager.updateProperties(progress);	
					
					addToFitnessGraph(progress.getGeneration(),progress.getFitness());
					
				});
				Thread.sleep(100);
				
			} catch (Exception e) {
				//System.out.println("algorithm progress thread exception dammiasdajdklsajdl");
			}
		}
	}
	
	private void addToFitnessGraph(int generation,double fitness)
	{
		int graphSize = graphSeries.getData().size();
		if(graphSize != 0)
		{
			  XYChart.Data item = ( XYChart.Data)graphSeries.getData().get(0);
			int lastGenerationInGraph = (int)item.getXValue();
			if(lastGenerationInGraph == generation)
			{
				return;//not new data
			}
		}
		
		graphSeries.getData().add(new XYChart.Data(generation, fitness));
	}

}
