package com.radaee.pdf;

/**
 * setting class for PDFV
 * @author com.radaee
 */
public class PDFVS {
	/**
	 * set dark mode
	 * @param dark true to set dark mode, and false to not.
	 * @return previous setting value
	 */
	public static native boolean setDarkMode(boolean dark);
	/**
	 * show annotations?
	 * @param show true to show and false to not.
	 * @return previous setting value
	 */
	public static native boolean showAnnots(boolean show);
	/**
	 * text selection or page text start from right to left?
	 * @param rtol true or false
	 * @return previous setting value
	 */
	public static native boolean textRtol(boolean rtol);
	/**
	 * set render quality for PDFV.
	 * @param q 0: poor, 1: normal, 2: best
	 * @return previous setting value
	 */
	public static native int renderQuality(int q);
	/**
	 * set background color for PDFV
	 * @param c background color
	 * @return previous setting value
	 */
	public static native int setBackColor(int c);
	/**
	 * set text selection color for PDFV
	 * @param c selection color
	 * @return previous setting value
	 */
	public static native int setSelColor(int c);
	/**
	 * distance for fling
	 * @param dis distance scale value for PDFV fling
	 * @return previous setting value
	 */
	public static native float setFlingDis(float dis);
	/**
	 *speed for fling 
	 * @param speed speed scale value for PDFV fling, range:[0.01 0.4] depends on timer frequency
	 * @return previous setting value
	 */
	public static native float setFlingSpeed(float speed);
}
