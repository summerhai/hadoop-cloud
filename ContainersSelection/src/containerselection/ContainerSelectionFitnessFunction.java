package containerselection;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;

public class ContainerSelectionFitnessFunction
						extends FitnessFunction{
	Payload payload;
	double maxWeight;
	double error;
	 public ContainerSelectionFitnessFunction(Payload payload,double maxWeight) {
		super();
		this.payload =payload;
		this.maxWeight=maxWeight;
	}

	public double evaluate(IChromosome a_subject){
		 System.out.println("Evaluation of Fitness");
		 double a_Subject_Weight = 0.0;
		 int[] bits;
		 for (int i=0;i<a_subject.size();i++){
			 bits = (int[])a_subject.getGene(i).getAllele();
			 if (bits[0] == 1){
				 a_Subject_Weight = a_Subject_Weight+payload.getShipContainer().get(i).getWeight();
			 }
		 }
		 
		 error = Math.abs(maxWeight-a_Subject_Weight);
		 
		 if (error < 0){
			 // something must be done
		 }
		 else{}
			 return error;
	}
}
