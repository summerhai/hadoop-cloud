package containerselection;

import java.util.ArrayList;
import java.util.List;

import org.jgap.BulkFitnessFunction;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.IInitializer;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.audit.IEvolutionMonitor;
import org.jgap.event.GeneticEvent;
import org.jgap.impl.GABreeder;

public class MyBreeder extends GABreeder {
	  private transient Configuration m_lastConf;


	private transient Population m_lastPop;

	public Population evolve(Population a_pop, Configuration a_conf) {
		System.out.println("Calling evolve from Extended Breeder..");
	    Population pop = a_pop;
	    BulkFitnessFunction bulkFunction = a_conf.getBulkFitnessFunction();
	    boolean monitorActive = a_conf.getMonitor() != null;
	    IChromosome fittest = null;
	    // If first generation: Set age to one to allow genetic operations,
	    // see CrossoverOperator for an illustration.
	    // ----------------------------------------------------------------
	    if (a_conf.getGenerationNr() == 0) {
	      int size = pop.size();
	      for (int i = 0; i < size; i++) {
	        IChromosome chrom = pop.getChromosome(i);
	        chrom.increaseAge();
	      }
	      // If a bulk fitness function has been provided, call it.
	      // ------------------------------------------------------
	      if (bulkFunction != null) {
	        try {
	          pop = bulkFunctionEvaluation(a_conf, bulkFunction, pop, monitorActive);
	        } catch (InvalidConfigurationException ex) {
	          throw new RuntimeException(ex);
	        }
	        // Increase number of generations.
	        // -------------------------------
	        a_conf.incrementGenerationNr();
	        // Fire an event to indicate we've performed an evolution.
	        // -------------------------------------------------------
	        m_lastPop = pop;
	        m_lastConf = a_conf;
	        System.out.println("..fire genetic event..");
	        a_conf.getEventManager().fireGeneticEvent(
	            new GeneticEvent(GeneticEvent.GENOTYPE_EVOLVED_EVENT, this));
	        return pop;
	      }
	    }
	    else {
	      // Select fittest chromosome in case it should be preserved and we are
	      // not in the very first generation.
	      // -------------------------------------------------------------------
	      if (a_conf.isPreserveFittestIndividual()) {
	    	  System.out.println("Determine Fittest Chromosome..");
	        /**@todo utilize jobs. In pop do also utilize jobs, especially for fitness
	         * computation*/
	        fittest = pop.determineFittestChromosome(0, pop.size() - 1);
	      }
	    }
	    
	    if (a_conf.getGenerationNr() > 0 && bulkFunction == null) {
	    	System.out.println("Keep size constant..");
	      keepPopSizeConstant(pop, a_conf);
	    }
	    // Ensure fitness value of all chromosomes is udpated.
	    // ---------------------------------------------------
	    if (monitorActive) {
	      // Monitor that fitness value of chromosomes is being updated.
	      // -----------------------------------------------------------
	      a_conf.getMonitor().event(
	          IEvolutionMonitor.MONITOR_EVENT_BEFORE_UPDATE_CHROMOSOMES1,
	          a_conf.getGenerationNr(), new Object[] {pop});
	    }
	    System.out.println("..Update Chromosome..");
	    updateChromosomes(pop, a_conf); 
	    
	    // Apply certain NaturalSelectors before GeneticOperators will be executed.
	    // ------------------------------------------------------------------------
	    System.out.println("Selection before GeneticOperators");
	    pop = applyNaturalSelectors(a_conf, pop, true);
	    int newChromIndex = pop.size();
	    // Execute all of the Genetic Operators.
	    // -------------------------------------
	    System.out.println("Genetic Operators start");
	    applyGeneticOperators(a_conf, pop);
	    System.out.println("Genetic Operators done");
	    // Reset fitness value of genetically operated chromosomes.
	    
	    int currentPopSize = pop.size();
	    for (int i = newChromIndex; i < currentPopSize; i++) {
	      IChromosome chrom = pop.getChromosome(i);
	      chrom.setFitnessValueDirectly(FitnessFunction.NO_FITNESS_VALUE);
	      // Mark chromosome as new-born.
	      // ----------------------------
	      chrom.resetAge();
	      // Mark chromosome as being operated on.
	      // -------------------------------------
	      chrom.increaseOperatedOn();
	    }
	    // Increase age of all chromosomes which are not modified by genetic
	    // operations.
	    // -----------------------------------------------------------------
	    int size = Math.min(newChromIndex, currentPopSize);
	    for (int i = 0; i < size; i++) {
	      IChromosome chrom = pop.getChromosome(i);
	      chrom.increaseAge();
	      // Mark chromosome as not being operated on.
	      // -----------------------------------------
	      chrom.resetOperatedOn();
	    }
	    // If a bulk fitness function has been provided, call it.
	    // ------------------------------------------------------
	    if (bulkFunction != null & a_conf.getGenerationNr() > 0) {
	      try {
	        pop = bulkFunctionEvaluation(a_conf, bulkFunction, pop, monitorActive);
	      } catch (InvalidConfigurationException ex) {
	        throw new RuntimeException(ex);
	      }
	    }
	    // Ensure fitness value of all chromosomes is udpated.
	    // ---------------------------------------------------
	    System.out.println(".. Update Chromosomes after Genetic ops");
	   // updateChromosomes(pop, a_conf);
	    // Apply certain NaturalSelectors after GeneticOperators have been applied.
	    // ------------------------------------------------------------------------
	    System.out.println("Selection after GeneticOperators");
	    pop = applyNaturalSelectors(a_conf, pop, false);
	    // Fill up population randomly if size dropped below specified percentage
	    // of original size.
	    // ----------------------------------------------------------------------
	    fillPopulationRandomlyToOriginalSize(a_conf, pop);
	    IChromosome newFittest = reAddFittest(pop, fittest);
	    if (monitorActive && newFittest != null) {
	      // Monitor that fitness value of chromosomes is being updated.
	      // -----------------------------------------------------------
	      a_conf.getMonitor().event(
	          IEvolutionMonitor.MONITOR_EVENT_READD_FITTEST,
	          a_conf.getGenerationNr(), new Object[] {pop, fittest});
	    }
	    // Increase number of generations.
	    // -------------------------------
	    a_conf.incrementGenerationNr();
	    // Fire an event to indicate we've performed an evolution.
	    // -------------------------------------------------------
	    m_lastPop = pop;
	    m_lastConf = a_conf;
	    a_conf.getEventManager().fireGeneticEvent(
	        new GeneticEvent(GeneticEvent.GENOTYPE_EVOLVED_EVENT, this));
	    return pop;
	  }

private void fillPopulationRandomlyToOriginalSize(Configuration a_conf,
	      Population pop) {
	    boolean monitorActive = a_conf.getMonitor() != null;
	    // Fill up population randomly if size dropped below specified percentage
	    // of original size.
	    // ----------------------------------------------------------------------
	    if (a_conf.getMinimumPopSizePercent() > 0) {
	      int sizeWanted = a_conf.getPopulationSize();
	      int popSize;
	      int minSize = (int) Math.round(sizeWanted *
	                                     (double) a_conf.getMinimumPopSizePercent()
	                                     / 100);
	      popSize = pop.size();
	      if (popSize < minSize) {
	        IChromosome newChrom;
	        IChromosome sampleChrom = a_conf.getSampleChromosome();
	        Class sampleChromClass = sampleChrom.getClass();
	        IInitializer chromIniter = a_conf.getJGAPFactory().
	            getInitializerFor(sampleChrom, sampleChromClass);
	        while (pop.size() < minSize) {
	          try {
	            /**@todo utilize jobs as initialization may be time-consuming as
	             * invalid combinations may have to be filtered out*/
	            newChrom = (IChromosome) chromIniter.perform(sampleChrom,
	                sampleChromClass, null);
	            if (monitorActive) {
	              // Monitor that fitness value of chromosomes is being updated.
	              // -----------------------------------------------------------
	              a_conf.getMonitor().event(
	                  IEvolutionMonitor.MONITOR_EVENT_BEFORE_ADD_CHROMOSOME,
	                  a_conf.getGenerationNr(), new Object[] {pop, newChrom});
	            }
	            pop.addChromosome(newChrom);
	          } catch (Exception ex) {
	            throw new RuntimeException(ex);
	          }
	        }
	      }
	    }
	  }


protected void updateChromosomes(Population a_pop, Configuration a_conf){
    int currentPopSize = a_pop.size();
    //---------------------------------
    //System.out.println("a_pop.size()"+a_pop.size());
    List<IChromosome> listOfChromosomes = a_pop.getChromosomes();
    //System.out.println("listOfChromosomes.size()"+listOfChromosomes.size());
	
	try{
		CreateSeqFile.createSequnceFile(listOfChromosomes);
		MapReducer.runJob();
		a_pop.clear();
		listOfChromosomes = CreateSeqFile.readSequnceFile();
		a_pop.setChromosomes(listOfChromosomes);
	}
	catch (Exception e){
		e.printStackTrace();
	}
  }

// Override applyGeneticOps for the second job
protected void applyGeneticOperators(Configuration a_config, Population a_pop) {
    int currentPopSize = a_pop.size();
    //---------------------------------
    //System.out.println("a_pop.size()"+a_pop.size());
    List<IChromosome> listOfChromosomes = a_pop.getChromosomes();
    List <Population> subpop = new ArrayList<Population>();
    int numberOfSubPops =Constants.NUMBER_OF_SUBPOPULATIONS;
    int subPopSize = currentPopSize/numberOfSubPops;
    
    for (int i=0;i<numberOfSubPops;i++){
    	try{
    		Population newPop = new Population(a_config,subPopSize);
	    		for (int j=0;j<subPopSize;j++){
	    			newPop.addChromosome(listOfChromosomes.get((i*subPopSize)+j));
	    		}
	    		subpop.add(newPop);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
	
	try{ //************************************//
		// Please add a line to delete the existing sequence file
		CreateSeqFile.createPopSequnceFile(subpop);
		MapReducer.runJob2();
		List <Population> returnPops= CreateSeqFile.readPopSequnceFile();
		a_pop.clear();
		for(int i=0;i<returnPops.size();i++)
			a_pop.setChromosomes(returnPops.get(i).getChromosomes());
	}
	catch (Exception e){
		e.printStackTrace();
	}
  }

}

