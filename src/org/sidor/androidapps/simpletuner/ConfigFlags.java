package org.sidor.androidapps.simpletuner;

public class ConfigFlags {
	/* To retrieve use the following command:
	 * X=<graph_number_from_log>; adb pull  /data/data/packages.real/files/Chart_$X.data ./ && 
	 * cat Chart_$X.data | head -25000 | cat > xxx && echo "plot \"./xxx\"" > command && 
	 * gnuplot command -persist
	 */
	public static boolean menuKeyCausesAudioDataDump = false;
	
	/* This will cause ui to inform user about data it gets. */
	public static boolean uiControlerInformsWhatItKnowsAboutSound = false;
	
	/* This number should be between 0 and 1. */
	public static double howOftenLogNotifyRate = 0.0;
	
	public static boolean shouldLogAnalyzisTooSlow = false;
}
