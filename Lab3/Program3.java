
//Name: (Wenting Song)
//EID: (ws8496)


public class Program3 {

    EconomyCalculator calculator;
    VibraniumOreScenario vibraniumScenario;    

    public Program3() {
        this.calculator = null;
        this.vibraniumScenario = null;
    }

    /*
     * This method is used in lieu of a required constructor signature to initialize
     * your Program3. After calling a default (no-parameter) constructor, we
     * will use this method to initialize your Program3 for Part 1.
     */
    public void initialize(EconomyCalculator ec) {
        this.calculator = ec;
    }

    /*
     * This method is used in lieu of a required constructor signature to initialize
     * your Program3. After calling a default (no-parameter) constructor, we
     * will use this method to initialize your Program3 for Part 2.
     */
    public void initialize(VibraniumOreScenario vs) {
        this.vibraniumScenario = vs;
    }

    /*
     * This method returns an integer that is maximum possible gain in the Wakandan economy
     * given a certain amount of Vibranium
     */
    //TODO: Complete this function
    public int computeGain() {
    		int numProj = calculator.getNumProjects();
    		int numVibra = calculator.getNumVibranium();
    		int[][] dp = new int[numProj][numVibra + 1];
    		
    		for (int i = 0; i < numProj; i++) {
    			dp[i][0] = 0;
    		}
    		for (int i = 0; i < numVibra + 1; i++) {
    			dp[0][i] = calculator.calculateGain(0, i);
    		}
    		
    		for (int i = 1; i < numProj; i++) {
    			for (int j = 1; j < numVibra + 1; j++)  {
    				int tmpMax = 0;
    				int tmp = 0;
    				for (int k = 0; k < j+1; k++) {
    					tmp = dp[i-1][j-k]+ calculator.calculateGain(i, k);  // ith project takes k vibra
    					if (tmp > tmpMax) {
    						tmpMax = tmp;
    					}
    				}
    				dp[i][j] = tmpMax;
    			}
    		}
        return dp[numProj-1][numVibra];
        // need more to record
    }

    /*
     * This method returns an integer that is the maximum possible dollar value that a thief 
     * could steal given the weight and volume capacity of his/her bag by using the 
     * VibraniumOreScenario instance.
     */
     //TODO: Complete this method
     public int computeLoss() {
        int numOres = vibraniumScenario.getNumOres();
        int weightCapacity = vibraniumScenario.getWeightCapacity();
        int volumeCapacity = vibraniumScenario.getVolumeCapacity();
        //System.out.println("weightCapacity: " + weightCapacity);
       // System.out.println("volumeCapacity: " + volumeCapacity);
        
        int[][][] knapsack  = new int[numOres][weightCapacity+1][volumeCapacity+1];
        
        for (int i = 0; i < weightCapacity+1; i++) {
        		for (int j = 0; j < volumeCapacity+1; j++) {
    				int weight0 = vibraniumScenario.getVibraniumOre(0).getWeight();
    				int volume0 = vibraniumScenario.getVibraniumOre(0).getVolume();
    				int price0 = vibraniumScenario.getVibraniumOre(0).getPrice();
        			if (weight0 > i || volume0 > j) {
        				knapsack[0][i][j] = 0;
        			}
        			else {
        				knapsack[0][i][j] = price0;
        			}
        		}
        }
        
        for (int i = 1; i < numOres; i++) {
        		for (int w = 0; w < weightCapacity+1; w++) {
        			for (int v = 0; v < volumeCapacity+1; v++) {
        				int weight = vibraniumScenario.getVibraniumOre(i).getWeight();
        				int volume = vibraniumScenario.getVibraniumOre(i).getVolume();
        				int price = vibraniumScenario.getVibraniumOre(i).getPrice();
        				//System.out.println(price);
        				if (weight > w || volume > v) {
        					knapsack[i][w][v] = knapsack[i-1][w][v];
        					//System.out.println(knapsack[i][w][v]);
        				}
        				else {
        					knapsack[i][w][v] = Math.max(price + knapsack[i-1][w - weight][v - volume], knapsack[i-1][w][v]);
        				}
        				//System.out.println( "i: " + i + " w: " +w + " v: " +v + " knapsack "+knapsack[i][w][v]);
        			}
        		}
        }
        
        
        return knapsack[numOres - 1][weightCapacity][volumeCapacity];
     }
}


