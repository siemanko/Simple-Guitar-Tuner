package org.sidor.androidapps.simpletuner;

import org.sidor.androidapps.simpletuner.SoundAnalyzer.AnalyzedSound;

public class FrequencySmoothener {
	static final double frequencyForgetting = 0.9; // how fast forget frequency
	static final int invalidDataAllowed = 6; // if this many uncertain data sets, assume frequency
	                                   // not available.
	static private double smoothFrequency=0.0;
	static private int invalidDataCounter;
	
	public static double getSmoothFrequency(AnalyzedSound result) {
		if(!result.frequencyAvailable) {
			invalidDataCounter=Math.min(invalidDataCounter+1, 2*invalidDataAllowed);
		} else {
			if(smoothFrequency == 0.0) {
				smoothFrequency = result.frequency;
			} else { 
				smoothFrequency = (1-frequencyForgetting)*smoothFrequency +
								   frequencyForgetting*result.frequency;
			}
			invalidDataCounter=Math.max(invalidDataCounter-invalidDataAllowed, 0);
		}
		if(invalidDataCounter <= invalidDataAllowed) {
			return smoothFrequency;
		} else {
			smoothFrequency = 0.0;
			return Double.NaN;
		}
	}
}
