package com.radaee.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import com.radaee.pdfex.PDFRecent;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
//import android.telephony.TelephonyManager;
//import android.net.wifi.WifiInfo;
//import android.net.wifi.WifiManager;
import android.os.Environment;

/**
 * class for Global setting.
 * 
 * @author Radaee
 * @version 1.1
 */
public class Global {
	private static native void setCMapsPath(String cmaps, String umaps);
	private static native void fontfileListStart();
	private static native void fontfileListAdd(String font_file);
	private static native void fontfileListEnd();
	private static native void loadStdFont( int index, String path );

	/**
	 * map a face name to another name.<br/>
	 * invoke after fontfileListEnd and before setDefaultFont.
	 * 
	 * @param map_name
	 *            mapping name
	 * @param name
	 *            name in face-list, developer may list all face names by
	 *            getFaceCount and getFaceName
	 * @return false if name is not in face-list, or map_name is empty.
	 */
	private static native boolean fontfileMapping(String map_name, String name);

	private static native boolean setDefaultFont(String collection,
			String fontname, boolean fixed);

	private static native boolean setAnnotFont(String fontname);

	private static native int getFaceCount();

	private static native String getFaceName(int index);

	/**
	 * active license for premium version.<br/>
	 * this is full version for all features.
	 * 
	 * @param context
	 *            Context object
	 * @param company
	 *            company name, exapmle "radaee"
	 * @param mail
	 *            address, example "radaee_com@yahoo.cn"
	 * @param serial
	 *            serial number you got or buy.
	 * @return true or false
	 */
	private static native boolean activePremium(ContextWrapper context,
			String company, String mail, String serial);

	/**
	 * active license for professional version.<br/>
	 * this is for annotation editing version but no form features.
	 * 
	 * @param context
	 *            Context object
	 * @param company
	 *            company name, exapmle "radaee"
	 * @param mail
	 *            address, example "radaee_com@yahoo.cn"
	 * @param serial
	 *            serial number you got or buy.
	 * @return true or false
	 */
	private static native boolean activeProfessional(ContextWrapper context,
			String company, String mail, String serial);

	/**
	 * active license for standard version.<br/>
	 * this can't save and edit and no reflow function.
	 * 
	 * @param context
	 *            Context object
	 * @param company
	 *            company name, exapmle "radaee"
	 * @param mail
	 *            address, example "radaee_com@yahoo.cn"
	 * @param serial
	 *            serial number you got or buy.
	 * @return true or false
	 */
	private static native boolean activeStandard(ContextWrapper context,
			String company, String mail, String serial);

	/**
	 * active license for time limit. features same as professional version, but
	 * actived only in date range from dt1 to dt2.
	 * 
	 * @param context
	 *            Context object
	 * @param company
	 *            company name, exapmle "radaee"
	 * @param mail
	 *            mail address, example "radaee_com@yahoo.cn"
	 * @param dt1
	 *            start date example "2012-12-31", must formated "yyyy-mm-dd" 10
	 *            length.
	 * @param dt2
	 *            end date example "2012-12-31", must formated "yyyy-mm-dd" 10
	 *            length.
	 * @param serial
	 *            serial number you got or buy.
	 * @return true or false
	 */
	private static native boolean activeTime(ContextWrapper context, String company,
			String mail, String dt1, String dt2, String serial);
	private static native boolean activeStandardTitanium(ContextWrapper context,
			String company, String mail, String serial);
	private static native boolean activeProfessionalTitanium(ContextWrapper context,
			String company, String mail, String serial);
	private static native boolean activePremiumTitanium(ContextWrapper context,
			String company, String mail, String serial);

	/**
	 * hide all annotations when render pages?
	 * 
	 * @param hide
	 *            true to hide, false to show.
	 */
	private static native void hideAnnots(boolean hide);

	/**
	 * lock Bitmap object, and get a handle.
	 * 
	 * @param bitmap
	 *            Bitmap object.
	 * @return hand handle value.
	 */
	public static native int lockBitmap(Bitmap bitmap);

	/**
	 * unlock Bitmap object, and free the handle.
	 * 
	 * @param bitmap
	 *            Bitmap object that passed to lockBitmap.
	 * @param bmp
	 *            handle value, that returned by lockBitmap.
	 */
	public static native void unlockBitmap(Bitmap bitmap, int bmp);
	/**
	 * draw Bitmap object to a dib
	 * @param dib
	 * @param bmp handle value, that returned by lockBitmap.
	 * @param x
	 * @param y
	 */
	public static native void drawBmpToDIB( int dib, int bmp, int x, int y );
	/**
	 * draw a dib to another dib
	 * @param dst_dib
	 * @param src_dib
	 * @param x
	 * @param y
	 */
	public static native void drawToDIB( int dst_dib, int src_dib, int x, int y );
	/**
	 * draw dib to bmp.
	 * 
	 * @param bmp
	 *            handle value, that returned by lockBitmap.
	 * @param dib
	 * @param x
	 *            origin position in bmp.
	 * @param y
	 *            origin position in bmp.
	 */
	public static native void drawToBmp(int bmp, int dib, int x, int y);
	/**
	 * draw dib to bmp, with scale
	 * @param bmp
	 * @param dib
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public static native void drawToBmp2(int bmp, int dib, int x, int y, int w, int h);

	/**
	 * fill solid rectangle to bmp.
	 * 
	 * @param bmp
	 *            handle value, that returned by lockBitmap.
	 * @param color
	 *            the color to fill, formatted: 0xAARRGGBB, AA: alpha value.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param mode
	 * <br/>
	 *            0: mix color by source pixels with alpha channel. <br/>
	 *            1: replace pixels.
	 */
	public static native void drawRect(int bmp, int color, int x, int y,
			int width, int height, int mode);
	public static native void drawRectToDIB( int dib, int color, int x, int y, int width, int height, int mode );

	/**
	 * invert all colors for locked bmp.
	 * 
	 * @param bmp
	 *            handle value, that returned by lockBitmap.
	 */
	public static native void invertBmp(int bmp);

	/**
	 * not used for developer
	 */
	public static native void drawScroll(Bitmap bmp, int dib1, int dib2, int x,
			int y, int style);

	/**
	 * create or resize dib, and reset all pixels in dib.<br/>
	 * if dib is 0, function create a new dib object.<br/>
	 * otherwise function resize the dib object.
	 */
	public static native int dibGet(int dib, int width, int height);

	/**
	 * free dib object.
	 */
	public static native int dibFree(int dib);

	private static native void toDIBPoint(int matrix, float[] ppoint,
			float[] dpoint);

	private static native void toPDFPoint(int matrix, float[] dpoint,
			float[] ppoint);

	private static native void toDIBRect(int matrix, float[] prect,
			float[] drect);

	private static native void toPDFRect(int matrix, float[] drect,
			float[] prect);

	/**
	 * set annotation transparency color.<br/>
	 * default value: 0x200040FF
	 * 
	 * @param color
	 *            formated as 0xAARRGGBB
	 */
	private static native void setAnnotTransparency(int color);

	/**
	 * not used for developer
	 */
	public static PDFRecent recentFiles = null;

	/**
	 * color for ink annotation
	 */
	public static int inkColor = 0x80404040;
	/**
	 * width for ink lines.
	 */
	public static float inkWidth = 4;
	/**
	 * color for rect annotation.
	 */
	public static int rectColor = 0x80C00000;
	/**
	 * selection color.
	 */
	public static int selColor = 0x400000C0;// selection color
	/**
	 * is text selection start from right to left in one line?
	 */
	public static boolean selRTOL = false;
	/**
	 * max zoom level; valid values: [2, 5]
	 */
	public static float zoomLevel = 3;
	public static float zoomStep = 1;
	/**
	 * fling distance: 0.5-2
	 */
	public static float fling_dis = 1.0f;// 0.5-2
	/**
	 * fling speed: 0.1-0.4
	 */
	public static float fling_speed = 0.2f;// 0.1 - 0.4
	/**
	 * default view:<br/>
	 * 0:vertical<br/>
	 * 2:scroll<br/>
	 * 3:single<br/>
	 * 4:SingleEx<br/>
	 * 5:ReFlow<br/>
	 * 6:2 page in landscape
	 */
	public static int def_view = 0;
	/**
	 * render mode: 0:draft 1:normal 2:best
	 */
	public static int render_mode = 2;
	/**
	 * render as dark mode?
	 */
	public static boolean dark_mode = false;

	/**
	 * temp path, able after Init() invoked
	 */
	public static String tmp_path = null;
	public static boolean need_time_span = true;

	/**
	 * global initialize function. it load JNI library and write some data to
	 * memory.
	 * 
	 * @param act
	 *            Activity object, must be called super.onCreate().
	 */
	public static void Init(Activity act)
	{
		/*
		 * String devID =
		 * ((TelephonyManager)act.getSystemService(act.TELEPHONY_SERVICE
		 * )).getDeviceId(); if( devID == null ) { WifiManager wm =
		 * (WifiManager)act.getSystemService(act.WIFI_SERVICE); if( wm != null )
		 * { WifiInfo wi = wm.getConnectionInfo(); if( wi != null ) devID =
		 * wi.getMacAddress(); } }
		 */
		// load library
		System.loadLibrary("rdpdf");

		// save assets to files path for application.
		// assets mainly include encoding map data.
		AssetManager assets = act.getAssets();
		byte buf[] = new byte[4096];
		File sub;
		int read;
		File files = act.getFilesDir();
		String cmaps_path = files.getAbsolutePath() + "/cmaps";// get destiny
																// cmaps file
																// path
		String umaps_path = files.getAbsolutePath() + "/umaps";// get destiny
																// umaps file
																// path

        String fonts_path = files.getAbsolutePath() + "/rdf013";

		// create temporary dictionary, to save media or attachment data.
		File sdDir = Environment.getExternalStorageDirectory();
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			tmp_path = sdDir.toString() + "/rdtmp";
			File dir = new File(tmp_path);
			if (!dir.exists())// not exist? make it!
				dir.mkdir();
			else if (!dir.isDirectory())
				tmp_path = sdDir.getAbsolutePath();
		} else if (sdDir != null)
			tmp_path = sdDir.getAbsolutePath();// not mount? get sdcard path
		else
			tmp_path = files.getAbsolutePath() + "/rdtmp";
		files = null;
		Global.RemoveTmp();// clear temporary dictionary
		files = new File(tmp_path);
		files.mkdir();
		files = null;

		// save cmaps data from assets to files path
		sub = new File(cmaps_path);
		if (!sub.exists()) {
			try {
				InputStream src;

				FileOutputStream dst = new FileOutputStream(
						new File(cmaps_path));

				src = assets.open("cmaps1");
				while ((read = src.read(buf)) > 0)
					dst.write(buf, 0, read);
				src.close();
				src = null;
				src = assets.open("cmaps2");
				while ((read = src.read(buf)) > 0)
					dst.write(buf, 0, read);
				src.close();
				src = null;

				dst.close();
				dst = null;
				src = null;
			} catch (Exception e) {
			}
		}
		sub = null;

		// save umaps data from assets to files path
		sub = new File(umaps_path);
		if (!sub.exists()) {
			try {
				InputStream src;

				FileOutputStream dst = new FileOutputStream(
						new File(umaps_path));

				src = assets.open("umaps1");
				while ((read = src.read(buf)) > 0)
					dst.write(buf, 0, read);
				src.close();
				src = null;
				src = assets.open("umaps2");
				while ((read = src.read(buf)) > 0)
					dst.write(buf, 0, read);
				src.close();
				src = null;

				dst.close();
				dst = null;
				src = null;
			} catch (Exception e) {
			}
		}
		sub = null;

		sub = new File(fonts_path);
		if( !sub.exists() )
		{
			try
	    	{
				InputStream src = assets.open("rdf013");
    			FileOutputStream dst = new FileOutputStream( new File(fonts_path) );
   				while( (read = src.read( buf )) > 0 )
   					dst.write( buf, 0, read );
   				dst.close();
   				src.close();
   				dst = null;
   				src = null;
	    	}
			catch(Exception e)
			{
			}
		}
		sub = null;

		buf = null;
		assets = null;

		// active library, or WaterMark will displayed on each page.
		// boolean succeeded = activeStandard(act, "radaee",
		// "radaee_com@yahoo.cn", "HV8A19-WOT9YC-9ZOU9E-OQ31K2-FADG6Z-XEBCAO");
		// boolean succeeded = activeProfessional( act, "radaee",
		// "radaee_com@yahoo.cn", "Z5A7JV-5WQAJY-9ZOU9E-OQ31K2-FADG6Z-XEBCAO" );
		boolean succeeded = activePremium(act, "radaee", "radaee_com@yahoo.cn",
				"LNJFDN-C89QFX-9ZOU9E-OQ31K2-FADG6Z-XEBCAO");

		// set cmaps and umaps data.
		setCMapsPath(cmaps_path, umaps_path);

		loadStdFont( 13, fonts_path );
		
		// add system external fonts.
		fontfileListStart();
		fontfileListAdd("/system/fonts/DroidSans.ttf");
		fontfileListAdd("/system/fonts/DroidSansFallback.ttf");
		fontfileListEnd();
		int face_first = 0;
		int face_count = getFaceCount();
		String face_name = null;
		while (face_first < face_count) {
			face_name = getFaceName(face_first);
			if (face_name != null)
				break;
			face_first++;
		}
		// set default font for fixed width font.
		if (!setDefaultFont(null, "DroidSans", true) && face_name != null)
			setDefaultFont(null, face_name, true);
		// set default font for non-fixed width font.
		if (!setDefaultFont(null, "DroidSans", false) && face_name != null)
			setDefaultFont(null, face_name, false);

		// set default font for Chinese simplified language.
		if (!setDefaultFont("GB1", "DroidSansFallback", true)
				&& face_name != null)
			setDefaultFont(null, face_name, true);
		if (!setDefaultFont("GB1", "DroidSansFallback", false)
				&& face_name != null)
			setDefaultFont(null, face_name, false);

		// set default font for Chinese traditional language.
		if (!setDefaultFont("CNS1", "DroidSansFallback", true)
				&& face_name != null)
			setDefaultFont(null, face_name, true);
		if (!setDefaultFont("CNS1", "DroidSansFallback", false)
				&& face_name != null)
			setDefaultFont(null, face_name, false);

		// set default font for Japanese.
		if (!setDefaultFont("Japan1", "DroidSansFallback", true)
				&& face_name != null)
			setDefaultFont(null, face_name, true);
		if (!setDefaultFont("Japan1", "DroidSansFallback", false)
				&& face_name != null)
			setDefaultFont(null, face_name, false);

		// set default font for Korean.
		if (!setDefaultFont("Korea1", "DroidSansFallback", true)
				&& face_name != null)
			setDefaultFont(null, face_name, true);
		if (!setDefaultFont("Korea1", "DroidSansFallback", false)
				&& face_name != null)
			setDefaultFont(null, face_name, false);

		if (!setAnnotFont("DroidSansFallback") && face_name != null) {
			setAnnotFont(face_name);
		}

		// set text font for edit-box and combo-box editing.
		if (!setAnnotFont("DroidSansFallback") && face_name != null) {
			setAnnotFont(face_name);
		}

		// set configure to default value
		default_config();
	}

	/**
	 * reset to default configure.
	 */
	public static void default_config() {
		selColor = 0x400000C0;// selection color
		fling_dis = 1.0f;// 0.5-2
		fling_speed = 0.1f;// 0.05 - 0.2
		def_view = 0;// 0,1,2,3,4,5,6 0:vertical 1:horizon 2:curl effect 3:single
						// 4:SingleEx 5:Reflow, 6:show 2 page as 1 page in land
						// scape mode
		render_mode = 1;// 0,1,2 0:draft 1:normal 2:best
		dark_mode = false;// dark mode
		zoomLevel = 3;
		need_time_span = true;
		setAnnotTransparency(0x200040FF);
	}

	/**
	 * map PDF point to DIB point.
	 * 
	 * @param mat
	 *            Matrix object defined scale, rotate, tranlate operations.
	 * @param ppoint
	 *            input point in PDF coordinate system. [x, y]
	 * @param dpoint
	 *            output point in DIB coordinate system. [x, y]
	 */
	public static void ToDIBPoint(Matrix mat, float[] ppoint, float[] dpoint) {
		toDIBPoint(mat.hand, ppoint, dpoint);
	}

	/**
	 * map DIB point to PDF point.
	 * 
	 * @param mat
	 *            Matrix object defined scale, rotate, tranlate operations.
	 * @param dpoint
	 *            input point in DIB coordinate system. [x, y]
	 * @param ppoint
	 *            output point in PDF coordinate system. [x, y]
	 */
	public static void ToPDFPoint(Matrix mat, float[] dpoint, float[] ppoint) {
		toPDFPoint(mat.hand, dpoint, ppoint);
	}

	/**
	 * map PDF rectangle to DIB rectangle.
	 * 
	 * @param mat
	 *            Matrix object defined scale, rotate, tranlate operations.
	 * @param prect
	 *            input rect in PDF coordinate system. [left, top, right,
	 *            bottom]
	 * @param drect
	 *            output rect in DIB coordinate system. [left, top, right,
	 *            bottom]
	 */
	public static void ToDIBRect(Matrix mat, float[] prect, float[] drect) {
		toDIBRect(mat.hand, prect, drect);
	}

	/**
	 * map DIB rectangle to PDF rectangle.
	 * 
	 * @param mat
	 *            Matrix object defined scale, rotate, tranlate operations.
	 * @param drect
	 *            input rect in DIB coordinate system. [left, top, right,
	 *            bottom]
	 * @param prect
	 *            output rect in PDF coordinate system. [left, top, right,
	 *            bottom]
	 */
	public static void ToPDFRect(Matrix mat, float[] drect, float[] prect) {
		toPDFRect(mat.hand, drect, prect);
	}

	/**
	 * map PDF point to DIB point.
	 * 
	 * @param ratio
	 *            scale value apply to page rendering.
	 * @param dib_h
	 *            height of render bitmap.
	 * @param ppoint
	 *            input point in PDF coordinate system. [x, y]
	 * @param dpoint
	 *            output point in DIB coordinate system. [x, y]
	 */
	public static void ToDIBPoint(float ratio, int dib_h, float[] ppoint,
			float[] dpoint) {
		dpoint[0] = ppoint[0] * ratio;
		dpoint[1] = dib_h - ppoint[1] * ratio;
	}

	/**
	 * map DIB point to PDF point.
	 * 
	 * @param ratio
	 *            scale value apply to page rendering.
	 * @param dib_h
	 *            height of render bitmap.
	 * @param dpoint
	 *            input point in DIB coordinate system. [x, y]
	 * @param ppoint
	 *            output point in PDF coordinate system. [x, y]
	 */
	public static void ToPDFPoint(float ratio, int dib_h, float[] dpoint,
			float[] ppoint) {
		ppoint[0] = dpoint[0] / ratio;
		ppoint[1] = (dib_h - dpoint[1]) / ratio;
	}

	/**
	 * map PDF rectangle to DIB rectangle.
	 * 
	 * @param ratio
	 *            scale value apply to page rendering.
	 * @param dib_h
	 *            height of render bitmap.
	 * @param prect
	 *            input rect in PDF coordinate system. [left, top, right,
	 *            bottom]
	 * @param drect
	 *            output rect in DIB coordinate system. [left, top, right,
	 *            bottom]
	 */
	public static void ToDIBRect(float ratio, int dib_h, float[] prect,
			float[] drect) {
		drect[0] = prect[0] * ratio;
		drect[1] = dib_h - prect[3] * ratio;
		drect[2] = prect[2] * ratio;
		drect[3] = dib_h - prect[1] * ratio;
	}

	/**
	 * map DIB rectangle to PDF rectangle.
	 * 
	 * @param ratio
	 *            scale value apply to page rendering.
	 * @param dib_h
	 *            height of render bitmap.
	 * @param drect
	 *            input rect in DIB coordinate system. [left, top, right,
	 *            bottom]
	 * @param prect
	 *            output rect in PDF coordinate system. [left, top, right,
	 *            bottom]
	 */
	public static void ToPDFRect(float ratio, int dib_h, float[] drect,
			float[] prect) {
		prect[0] = drect[0] / ratio;
		prect[1] = (dib_h - drect[3]) / ratio;
		prect[2] = drect[2] / ratio;
		prect[3] = (dib_h - drect[1]) / ratio;
	}

	/**
	 * remove all tmp files that "pdfex" library generated.
	 */
	public static void RemoveTmp() {
		File tmp = new File(tmp_path);
		File files[] = tmp.listFiles();
		if (files != null) {
			int index;
			for (index = 0; index < files.length; index++)
				files[index].delete();
			tmp.delete();
		}
	}
}
