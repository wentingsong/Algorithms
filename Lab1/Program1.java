/*
 * Name: <Wenting Song>
 * EID: <ws8496>
 */

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program1 extends AbstractProgram1 {
    /**
     * Determines whether a candidate Matching represents a solution to the
     * Stable Marriage problem. Study the description of a Matching in the
     * project documentation to help you with this.
     */
    public boolean isStableMatching(Matching marriage) {  //a verify function
    	int numS = marriage.getNumberOfAdvisers();
        
            
    	ArrayList<Double> stuGpa = marriage.getStudentGPAs();
    	ArrayList<Coordinate> stuLocation = marriage.getStudentLocations();
    	ArrayList<Coordinate> advLocation = marriage.getAdviserLocations();

    	ArrayList<Integer> stuMatch = marriage.getStudentMatching();
    	ArrayList<ArrayList<Integer>> stuPrefer = marriage.getStudentPreference();
    	for (int i = 0; i < numS; i++) {
    		int stuNow = i;
    		int advNow = stuMatch.get(i); //get the match advisor index
                    
    		int rank_of_adv = stuPrefer.get(stuNow).indexOf(advNow);
    		for (int j = 0; j < rank_of_adv; j++)
    		{
    			int advCompare = stuPrefer.get(stuNow).get(j);
    			int stuCompare = stuMatch.indexOf(advCompare);

    			if(stuGpa.get(stuCompare) > stuGpa.get(stuNow))
    				;
    			else if(stuGpa.get(stuCompare).equals(stuGpa.get(stuNow)))  //No use ==, type is double, need to use .equals()
    			{
    				double distanceCompare = getDistance(advLocation.get(advCompare), stuLocation.get(stuCompare));
    				double distanceNow = getDistance(advLocation.get(advCompare), stuLocation.get(stuNow));
    	            
    				if(distanceCompare < distanceNow)
    					;
    				else // now is nearer
    					return false;
    			}
    			else
    				return false;
    		}
    	}
    	return true;
}
public double getDistance(Coordinate c1, Coordinate c2) {    //new method set
    	return Math.pow((c1.x - c2.x),2) + Math.pow((c1.y - c2.y),2) ;
}

    
    
    
    /**
     * Determines a solution to the Stable Marriage problem from the given input
     * set. Study the project description to understand the variables which
     * represent the input to your solution.
     * 
     * @return A stable Matching.
     */
    
public Matching stableMarriageGaleShapley(Matching marriage) {
    /* TODO implement this function */
    int numA = marriage.getNumberOfAdvisers();
    int numS = marriage.getNumberOfStudents();
    ArrayList<Double> stuGpa = marriage.getStudentGPAs();
    ArrayList<Coordinate> stuLocation = marriage.getStudentLocations();
    ArrayList<Coordinate> advLocation = marriage.getAdviserLocations();
    ArrayList<ArrayList<Integer>> stuPrefer = marriage.getStudentPreference();
    // ArrayList<Integer> stuMatch = marriage.getStudentMatching();
    
    ArrayList<Integer> finalPair = new ArrayList<>();

    LinkedList<Integer> waitlist = new LinkedList<>();
    
    ArrayList<Integer> count = new ArrayList<>();
    
    ArrayList<Integer> stuPair = new ArrayList<>();  //present match result, not final
    ArrayList<Integer> advPair = new ArrayList<>(); //present match result, not final
    
    for (int i = 0; i < numS; i++) {
        //initialize all advisors are unmatched
        advPair.add(i, -1); // -1 means null  pre-set the size of the arraylist
        stuPair.add(i, -1); // -1 means null  pre-set the size of the arraylist

        //all students are added to waitlist
        waitlist.add(i);
        //the number of proposal of all the students are all 0 initially
        count.add(i, 0); 
    }
        
          /* G-S algorithms  Starts here     */
        while (waitlist.size() != 0) {
            // get a free student
            int stuNow = waitlist.remove();  //removes the head (first element) of this list.
            int c = count.get(stuNow); //the number of the current students' proposal
            int advNow = stuPrefer.get(stuNow).get(c); // select the student's favorite advisor that he haven't proposed yet
            // if the advisor is free
            if ( advPair.get(advNow) == -1) {  //not stuNow
                //then the advisor is matched with this student
                advPair.set(advNow, stuNow);  // advPair.add(advNow, stuNow)
                stuPair.set(stuNow, advNow);
            } 
            else {
                // if the advisor has already matched with someone, get advisor's current matching
          	  	  /* note that stuMatched is the student that the advisor currently pair with 
          	  	   and stuNow is the professor that we are trying to find a match
          	  	   */
                int stuMatched = advPair.get(advNow);  
                // if advisor prefers the student he is currently pairing with
                if (stuGpa.get(stuMatched) > stuGpa.get(stuNow)) {
                	//***case1_student remains free, stuMatched has better GPA ***//
                    waitlist.add(stuNow);
                }
                else if (stuGpa.get(stuMatched).equals(stuGpa.get(stuNow)) ) {
                		double distance5 = getDistance(advLocation.get(advNow), stuLocation.get(stuMatched));
                		double distance6 = getDistance(advLocation.get(advNow), stuLocation.get(stuNow));
//    				double distance5 = Math.pow((advLocation.get(advNow).x - stuLocation.get(stuMatched).x), 2) + Math.pow((advLocation.get(advNow).y - stuLocation.get(stuMatched).y), 2);
//    				double distance6 = Math.pow((advLocation.get(advNow).x - stuLocation.get(stuNow).x), 2) + Math.pow((advLocation.get(advNow).y - stuLocation.get(stuNow).y), 2); 
    				if (distance5 < distance6) {
    					//***case2_student remains free, GPA equal, but the original matched student has closer location to the advisor ***//
                    waitlist.add(stuNow);
						}
                } 
                else {
                    // if advisor prefer this new student, his current pair student becomes free
                    waitlist.add(stuMatched);
                    // change both advisor's and student's status : advPair stuPair
                    advPair.set(advNow, stuNow); //advPair.add(advNow, stuNow)
                    stuPair.set(stuNow, advNow); //
                }
            }
            // increase the number of propose of student
            int numProp = count.get(stuNow);
            numProp++;
            count.add(stuNow, numProp);
        }

        //add all the student's status to the final result
        for (int s = 0; s < numS; s++) {
        		finalPair.add(s, stuPair.get(s));
        }     
    
        
    return new Matching(marriage, finalPair);
//    return finalPair;
    
   }
}




//    public Matching stableMarriageGaleShapley(Matching marriage) {
//        /* TODO implement this function */
//        int numA = marriage.getNumberOfAdvisers();
//        int numS = marriage.getNumberOfStudents();
//        ArrayList<Double> stuGpa = marriage.getStudentGPAs();
//        ArrayList<Coordinate> stuLocation = marriage.getStudentLocations();
//        ArrayList<Coordinate> advLocation = marriage.getAdviserLocations();
//        ArrayList<ArrayList<Integer>> stuPrefer = marriage.getStudentPreference();
//        // ArrayList<Integer> stuMatch = marriage.getStudentMatching();
//        
//        ArrayList<Integer> finalPair = new ArrayList<>();
//
//        LinkedList<Integer> waitlist = new LinkedList<>();
//        ArrayList<Integer> count = new ArrayList<>();
//        
//        ArrayList<Integer> stuPair = new ArrayList<>();  //present match result, not final
//        ArrayList<Integer> advPair = new ArrayList<>(); //present match result, not final
//        
//        for (int i = 0; i < numS; i++) {
//            //initialize all advisors are unmatched
//            advPair.add(i, -1); // -1 means null
//            //all students are added to waitlist
//            waitlist.add(i);
//            //the number of proposal of all the advisors are all 0 initially
//            count.add(i, 0); 
//        }
//            
//              /* G-S algorithms  Starts here     */
//            while (waitlist.size() != 0) {
//                // get a free student
//                int stuNow = waitlist.remove();  //removes the head (first element) of this list.
//                // select the student's favorite advisor that he haven't proposed yet
//                int c = count.get(stuNow);
//                int advNow = stuPrefer.get(stuNow).get(c); 
//                // if the advisor is free
//                if ( advPair.get(stuNow) == -1) { 
//                    //the advisor is matched with this student
//                    advPair.add(advNow, stuNow);
//                    stuPair.add(stuNow, advNow);
//                } 
//                else {
//                    // if the advisor has already matched with someone, get advisor's current matching
//              	  	  /* note that stuMatched is the student that the advisor currently pair with 
//              	  	   and stuNow is the professor that we are trying to find a match
//              	  	   */
//                    int stuMatched = advPair.get(advNow);  
//                    // if advisor prefers the student he is currently pairing with
//                    if (stuGpa.get(stuMatched) > stuGpa.get(stuNow)) {
//                    	//***case1_student remains free, stuMatched has better GPA ***//
//                        waitlist.add(stuNow);
//                    }
//                    else if (stuGpa.get(stuMatched).equals(stuGpa.get(stuNow)) ) {
//        				double distance5 = Math.pow((advLocation.get(advNow).x - stuLocation.get(stuMatched).x), 2) + Math.pow((advLocation.get(advNow).y - stuLocation.get(stuMatched).y), 2);
//        				double distance6 = Math.pow((advLocation.get(advNow).x - stuLocation.get(stuNow).x), 2) + Math.pow((advLocation.get(advNow).y - stuLocation.get(stuNow).y), 2); 
//        				if (distance5 < distance6) {
//        					//***case2_student remains free, GPA equal, but the original matched student has closer location to the advisor ***//
//                        waitlist.add(stuNow);
//    						}
//                    } 
//                    else {
//                        // if advisor prefer this new student, his current pair student becomes free
//                        waitlist.add(stuMatched);
//                        // change both advisor's and student's status : advPair stuPair
//                        advPair.add(advNow, stuNow);
//                        stuPair.add(stuNow, advNow);
//                    }
//                }
//                // increase the number of propose of student
//                int numProp = count.get(stuNow) + 1;
//                count.add(stuNow, numProp);
//            }
//
//            //add all the student's status to the final result
//            for (int s = 0; s < numS; s++) {
//            		finalPair.add(s, stuPair.get(s));
//            }     
//        
//            
//        return new Matching(marriage, finalPair);
////        return finalPair;
//        
//       }
//}
    
 