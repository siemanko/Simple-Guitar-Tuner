package org.sidor.androidapps.simpletuner;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Tuning {
	public static final String TAG = "RealGuitarTuner";

	private static class TuningType {
		public String humanReadableName;
		public double [] freqs;
		public String [] stringNames;
		public TuningType(String name, double [] f, String [] sn) {
			humanReadableName = name;
			freqs = f;
			stringNames = sn;
		}
	}
	
	private static TuningType [] tuningTypes = new TuningType[]{
		new TuningType("Standard",
				new double[]{82.41, 110.00, 146.83, 196.00, 246.94, 329.63},
				new String[]{"E","A","D","G","B","E"}) ,
		new TuningType("Down a half step",
				new double[]{77.78, 103.83, 138.59, 185.00, 233.08, 311.13},
				new String[]{"D#","G#","C#","F#","A#","D#"}) ,
		new TuningType("Dropped D",
				new double[]{73.42, 110.00, 146.83, 196.00, 246.94, 329.63},
				new String[]{"D","A","D","G","B","E"}) ,
		new TuningType("Double Dropped D",
				new double[]{73.42, 110.00, 146.83, 196.00, 246.94, 293.66},
				new String[]{"D","A","D","G","B","D"}) ,
		new TuningType("Open A",
				new double[]{82.41, 110.00, 164.81, 220.00, 277.18, 329.63},
				new String[]{"E","A","E","A","C#","E"}) ,
		new TuningType("Open C",
				new double[]{65.41, 98.00, 130.81, 196.00, 261.63, 329.63},
				new String[]{"C","G","C","G","C","E"}) ,
		new TuningType("Open D",
				new double[]{73.42, 110.00, 146.83, 185.00, 220.00, 293.66},
				new String[]{"D","A","D","F#","A","D"}) ,
		new TuningType("Open E",
				new double[]{82.41, 123.47, 164.81, 207.65, 246.94, 329.63},
				new String[]{"E","B","E","G#","B","E"}) ,
		new TuningType("Open Em",
				new double[]{82.41, 123.47, 164.81, 196.00, 246.94, 329.63},
				new String[]{"E","B","E","G","B","E"}) ,
		new TuningType("Open G",
				new double[]{98.00, 123.47, 146.83, 196.00, 246.94, 293.66},
				new String[]{"G","B","D","G","B","D"}) ,
	};
	
	public static void populateSpinner(Activity parent, Spinner s) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(parent, 
				android.R.layout.simple_spinner_item);
		for(int i=0; i<tuningTypes.length; ++i) {
			String label=tuningTypes[i].humanReadableName + " (";
			for(int j=0; j<tuningTypes[i].stringNames.length; ++j) {
				label+=tuningTypes[i].stringNames[j] + 
				((j==tuningTypes[i].stringNames.length -1) ? ")": ",");
			}
			adapter.add(label);
		}
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    s.setAdapter(adapter);
	}
	
	public class GuitarString {
		public int stringId; // no of string in the order of ascending frequency
		public double minFreq;
		public double maxFreq;
		public double freq;
		public String name;
		public GuitarString(int s,double f, double mif, double maf, String n) {
			stringId=s;
			freq=f;
			minFreq=mif;
			maxFreq=maf;
			name=n;
		}
	}
	private final GuitarString zeroString = new GuitarString(0,0.0,0.0,0.0,"0");
	private GuitarString [] strings;
	private String humanReadableName;
	private int tuningId = 0;
	
	public int getTuningId() {
		return tuningId;
	}
	public void initStrings(double [] freqs, String [] names) {
		strings = new GuitarString[freqs.length];
		for(int i=0; i<freqs.length; ++i) {
			double ldist = (i==0) ? 0.75*(2*freqs[i]-(freqs[i]+freqs[i+1])/2) 
					              : (freqs[i]+freqs[i-1])/2;
			double rdist = (i==freqs.length-1) ? 1.5*(2*freqs[i] - (freqs[i]+freqs[i-1])/2)
					                           : (freqs[i]+freqs[i+1])/2;
			//Log.e(TAG, "" + freqs[i] + ": " + (ldist) + " " + rdist);
			strings[i]=new GuitarString(i+1,freqs[i],ldist,rdist, names[i]);
		}
	}
/*
	private void outputStringsFrequencies() {
		for(int i=0; i<strings.length; ++i) {
			Log.d(TAG, strings[i].name + ": " + strings[i].freq + " e [" + 
					strings[i].minFreq + "," + strings[i].maxFreq + "]");
		}
	}
*/
	public Tuning(int tuningNumber) {
		initStrings(tuningTypes[tuningNumber].freqs,
				    tuningTypes[tuningNumber].stringNames);
		humanReadableName = tuningTypes[tuningNumber].humanReadableName;
		tuningId = tuningNumber;
		//outputStringsFrequencies();
	}
	
	public String getName() {
		return humanReadableName;
	}
	
	GuitarString getString(double frequency) {
		for(int i=0; i<strings.length; ++i) {
			if(strings[i].minFreq <=frequency && frequency<=strings[i].maxFreq)
				return strings[i];
		}
		return zeroString;
	}
}
