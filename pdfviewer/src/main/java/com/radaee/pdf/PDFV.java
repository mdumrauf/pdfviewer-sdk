package com.radaee.pdf;
import android.graphics.Bitmap;

/**
 * Class for PDFView, which is written by C/C++ codes<br/>
 * this is a "timer-drive" view class, means that developers should call PDFV.draw in timer to show page-layouts.<br/>
 * options for this class see: PDFVS class.
 * @author com.radaee
 *
 */
public class PDFV
{
	private static native int open( int type, int doc, int page_gap );
	private static native void close( int hand );
	private static native void onDown( int hand, float x, float y );
	private static native void onMove( int hand, float x, float y );
	private static native void onUp( int hand, float x, float y );
	private static native int draw( int hand, Bitmap bitmap );
	private static native void setSize( int hand, int w, int h );
	private static native void setRatio( int hand, float ratio );
	private static native int getPos( int hand, float[] pt );
	private static native void setPos( int hand, int pageno, float x, float y );
	private static native void setSel( int hand, float x1, float y1, float x2, float y2 );
	private static native void enableSel( int hand, boolean enable );
	private static native void lock( int hand, int mode );
	private static native boolean zoomStart( int hand, float x1, float y1, float x2, float y2 );
	private static native float zoomGetRatio( int hand, float x1, float y1, float x2, float y2 );
	private static native void zoomEnd( int hand );
	private static native void invalidate( int hand );
	private static native void fling( int hand, float dx, float dy );
	private static native boolean findStart( int hand, String pat, boolean match_case, boolean whole_word );
	private static native int find( int hand, int dir );
	private static native int getPageCount( int hand );
	private static native int getPage( int hand, int index );
	private static native int getPageFromPoint( int hand, float x, float y, float[] rect );
	private static native boolean getPageRect( int hand, int p, float[] rect );
	private static native float getPageRatio( int hand, int page );
	private static native boolean pageRender( int hand, int page );
	private static native int pageGetAnnotCount( int hand, int page );
	private static native int pageGetAnnot( int hand, int page, int index );
	private static native int pageGetAnnotFromPoint( int hand, int page, float x, float y );
	private static native boolean pageGetAnnotRect( int hand, int page, int annot, float[] rect );
	private static native int pageGetAnnotFillColor( int hand, int page, int annot );
	private static native boolean pageSetAnnotFillColor( int hand, int page, int annot, int color );
	private static native int pageGetAnnotStrokeColor( int hand, int page, int annot );
	private static native boolean pageSetAnnotStrokeColor( int hand, int page, int annot, int color );
	private static native float pageGetAnnotStrokeWidth( int hand, int page, int annot );
	private static native boolean pageSetAnnotStrokeWidth( int hand, int page, int annot, float width );
	static private native int pageGetAnnotInkPath( int hand, int page, int annot );
	static private native boolean pageSetAnnotInkPath( int hand, int page, int annot, int path );
	private static native int pageGetAnnotType( int hand, int page, int annot );
	private static native boolean pageRemoveAnnot( int hand, int page, int annot );
	private static native boolean pageAddTextAnnot( int hand, int page, float x, float y );
	private static native String pageGetAnnotText( int hand, int page, int annot );
	private static native boolean pageSetAnnotText( int hand, int page, int annot, String txt );
	private static native String pageGetAnnotSubj( int hand, int page, int annot );
	private static native boolean pageSetAnnotSubj( int hand, int page, int annot, String subj );
	private static native int pageGetAnnotDest( int hand, int page, int annot );
	private static native String pageGetAnnotURI( int hand, int page, int annot );
	private static native String pageGetAnnot3D( int hand, int page, int annot );
	private static native String pageGetAnnotMovie( int hand, int page, int annot );
	private static native String pageGetAnnotSound( int hand, int page, int annot );
	private static native String pageGetAnnotAttach( int hand, int page, int annot );
	private static native boolean pageGetAnnot3DData( int hand, int page, int annot, String path );
	private static native boolean pageGetAnnotMovieData( int hand, int page, int annot, String path );
	private static native boolean pageGetAnnotSoundData( int hand, int page, int annot, int[] paras, String path );
	private static native boolean pageGetAnnotAttachData( int hand, int page, int annot, String path );
	private static native boolean pageAddGotoAnnot( int hand, int page, float[] rect, int pageno, float top );
	private static native boolean pageAddUriAnnot( int hand, int page, float[] rect, String uri );
	private static native boolean pageAddInkAnnot( int hand, int page, int ink );
	private static native boolean pageAddLineAnnot( int hand, int page, float[] pt1, float[] pt2, int style1, int style2, float width, int color, int icolor );
	private static native boolean pageAddRectAnnot( int hand, int page, float[] rect, float width, int color, int fill_color );
	private static native boolean pageAddEllipseAnnot( int hand, int page, float[] rect, float width, int color, int fill_color );
	private static native boolean pageAddBitmapAnnot( int hand, int page, Bitmap bitmap, boolean has_alpha, float[] rect );
	private static native boolean pageAddBitmap( int hand, int page, Bitmap bitmap, boolean has_alpha, float[] rect );
	private static native boolean pageAddMarkupAnnot( int hand, int page, int clr, int type );
	private static native boolean pageAddEditboxAnnot( int hand, int page, float[] rect, float tsize, int color );
	private static native int pageGetAnnotEditType( int hand, int page, int annot );
	private static native boolean pageGetAnnotEditTextRect( int hand, int page, int annot, float[] rect );
	private static native float pageGetAnnotEditTextSize( int hand, int page, int annot );
	private static native String pageGetAnnotEditText( int hand, int page, int annot );
	private static native boolean pageSetAnnotEditText( int hand, int page, int annot, String txt );
	private static native int pageGetAnnotComboItemCount( int hand, int page, int annot );
	private static native String pageGetAnnotComboItem( int hand, int page, int annot, int item );
	private static native int pageGetAnnotComboSel( int hand, int page, int annot );
	private static native boolean pageSetAnnotComboSel( int hand, int page, int annot, int item );
	private static native int pageGetAnnotCheckStatus( int hand, int page, int annot );
	private static native boolean pageSetAnnotCheckValue( int hand, int page, int annot, boolean check );
	private static native boolean pageSetAnnotRadio( int hand, int page, int annot );
	private static native boolean pageGetAnnotReset( int hand, int page, int annot );
	private static native boolean pageSetAnnotReset( int hand, int page, int annot );
	private static native String pageGetAnnotSubmitTarget( int hand, int page, int annot );
	private static native String pageGetAnnotSubmitPara( int hand, int page, int annot );
	private int m_hand = 0;
	/**
	 * create a view object from Document object
	 * @param type <br/>0: vertical <br/>1: horizontal <br/>2: right to left horizontal <br/>3: single <br/>4: right to left single
	 * @param doc Document object
	 * @param page_gap gap between pages.
	 */
	public void Open( int type, Document doc, int page_gap )
	{
		Close();
		m_hand = open( type, doc.hand_val, page_gap );
	}
	/**
	 * destroy and free memory.
	 */
	public void Close()
	{
		close( m_hand );
		m_hand = 0;
	}
	/**
	 * invoke this when press down
	 * @param x point in View coordinate
	 * @param y point in View coordinate
	 */
	public void OnDown( float x, float y )
	{
		onDown( m_hand, x, y );
	}
	/**
	 * invoke this when touch moving
	 * @param x point in View coordinate
	 * @param y point in View coordinate
	 */
	public void OnMove( float x, float y )
	{
		onMove( m_hand, x, y );
	}
	/**
	 * invoke this when touch up
	 * @param x point in View coordinate
	 * @param y point in View coordinate
	 */
	public void OnUp( float x, float y )
	{
		onUp( m_hand, x, y );
	}
	/**
	 * invoke this draw view to Bitmap object
	 * @param bitmap Bitmap object allocated by APP.
	 * @return as following:
	 * 0: no need draw to bitmap. means current status same as previous draw<br/>
	 * 1: update view to Bitmap.<br/>
	 * 2: find operation finished, and goto found result
	 * 3: find operation failed, means no more found.
	 */
	public int Draw( Bitmap bitmap )
	{
		return draw( m_hand, bitmap );
	}
	/**
	 * invoke this when size of view changed
	 * @param w view width
	 * @param h view height
	 */
	public void SetSize( int w, int h )
	{
		setSize( m_hand, w, h );
	}
	/**
	 * invoke this when need zoom in or out.
	 * @param ratio
	 */
	public void SetRatio( float ratio )
	{
		setRatio( m_hand, ratio );
	}
	/**
	 * return pageNO and point of current page.
	 * @param pt output value: left-top point in PDF coordinate
	 * @return 0 based page NO.
	 */
	public int GetPos( float[] pt )
	{
		return getPos( m_hand, pt );
	}
	/**
	 * goto a position.
	 * @param pageno 0 based page NO.
	 * @param x point of page in PDF coordinate
	 * @param y point of page in PDF coordinate
	 */
	public void SetPos( int pageno, float x, float y )
	{
		setPos( m_hand, pageno, x, y );
	}
	/**
	 * set text selection
	 * @param x1 first point in View coordinate
	 * @param y1 first point in View coordinate
	 * @param x2 second point in View coordinate
	 * @param y2 second point in View coordinate
	 */
	public void SetSel( float x1, float y1, float x2, float y2 )
	{
		setSel( m_hand, x1, y1, x2, y2 );
	}
	/**
	 * enable selecting?
	 * @param enable
	 */
	public void EnableSel( boolean enable )
	{
		enableSel( m_hand, enable );
	}
	/**
	 * set some locks
	 * @param mode 0: non-lock 1: lock side moving 2: lock all moving 3: lock resizing
	 */
	public void lock( int mode )
	{
		lock( m_hand, mode );
	}
	
	/**
	 * invoke this when 2 point touch down
	 * @param x1 first touched point in View coordinate
	 * @param y1 first touched point in View coordinate
	 * @param x2 second touched point in View coordinate
	 * @param y2 second touched point in View coordinate
	 * @return
	 */
	public boolean ZoomStart( float x1, float y1, float x2, float y2 )
	{
		return zoomStart( m_hand, x1, y1, x2, y2 );
	}
	/**
	 * invoke this when 2 point moving.
	 * @param x1 first touched point in View coordinate
	 * @param y1 first touched point in View coordinate
	 * @param x2 second touched point in View coordinate
	 * @param y2 second touched point in View coordinate
	 * @return
	 */
	public float ZoomGetRatio( float x1, float y1, float x2, float y2 )
	{
		return zoomGetRatio( m_hand, x1, y1, x2, y2 );
	}
	/**
	 * end zooming and re-render all displayed pages
	 */
	public void ZoomEnd()
	{
		zoomEnd( m_hand );
	}
	/**
	 * set redraw flag, redraw operation will applied when next draw invoked.
	 */
	public void Invalidate()
	{
		invalidate( m_hand );
	}
	/**
	 * invoke this when do fling
	 * @param dx x delta to fling
	 * @param dy y delta to fling
	 */
	public void Fling( float dx, float dy )
	{
		fling( m_hand, dx, dy );
	}
	/**
	 * start find
	 * @param pat
	 * @param match_case
	 * @param whole_word
	 * @return true or false. false means no more found.
	 */
	public boolean FindStart( String pat, boolean match_case, boolean whole_word )
	{
		return findStart( m_hand, pat, match_case, whole_word );
	}
	/**
	 * go find
	 * @param dir go backward if less than 0, otherwise go forward 
	 * @return as following:
	 * 0: found in current page<br/>
	 * 1: pending for previous finding<br/>
	 * 2: find to start, and no more found<br/>
	 * 3: find to end, and no more found<br/>
	 */
	public int Find( int dir )
	{
		return find( m_hand, dir );
	}
	/**
	 * count of displayed page
	 * @return count of displayed page
	 */
	public int GetPageCount()
	{
		return getPageCount( m_hand );
	}
	/**
	 * get displayed page by index.
	 * @param index
	 * @return displayed page object or 0
	 */
	public int GetPage( int index )
	{
		return getPage( m_hand, index );
	}
	/**
	 * get displayed page by view point.
	 * @param x
	 * @param y
	 * @param rect output parameter, 4 elements for [left, top, right, bottom]
	 * @return displayed page object or 0
	 */
	public int GetPageFromPoint( float x, float y, float[] rect )
	{
		return getPageFromPoint( m_hand, x, y, rect );
	}
	/**
	 * get zoom scale level for page
	 * @param page page object
	 * @return scale value
	 */
	public float GetPageRatio( int page )
	{
		return getPageRatio( m_hand, page );
	}
	/**
	 * get page rect area.
	 * @param page page object
	 * @param rect output as [left, top, right, bottom] in View coordinate
	 * @return true or false.
	 */
	public boolean GetPageRect( int page, float[] rect )
	{
		return getPageRect( m_hand, page, rect );
	}
	/**
	 * re-render page
	 * @param page page object returned by PDFV.GetPage() or PDFV.GetPageFromPoint()
	 * @return true or false
	 */
	public boolean PageRender( int page )
	{
		return pageRender( m_hand, page );
	}
	/**
	 * get count of annotations in the page.<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned by PDFV.GetPage() or PDFV.GetPageFromPoint()
	 * @return count of annotations in the page.
	 */
	public int PageGetAnnotCount( int page )
	{
		return pageGetAnnotCount( m_hand, page );
	}
	/**
	 * get annotation object of the page by index.<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned by getPage() or getPageFromPoint()
	 * @param index
	 * @return annotation object or 0
	 */
	public int PageGetAnnot( int page, int index )
	{
		return pageGetAnnot( m_hand, page, index );
	}
	/**
	 * most like Page.GetAnnotFillColor();<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned by getPage() or getPageFromPoint()
	 * @param annot
	 * @return
	 */
	public int PageGetAnnotFillColor( int page, int annot )
	{
		return pageGetAnnotFillColor( m_hand, page, annot );
	}
	/**
	 * most like Page.SetAnnotFillColor();<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned by getPage() or getPageFromPoint()
	 * @param annot
	 * @param color
	 * @return
	 */
	public boolean PageSetAnnotFillColor( int page, int annot, int color )
	{
		return pageSetAnnotFillColor( m_hand, page, annot, color );
	}
	/**
	 * most like Page.GetAnnotStrokeColor();<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned by getPage() or getPageFromPoint()
	 * @param annot
	 * @return
	 */
	public int PageGetAnnotStrokeColor( int page, int annot )
	{
		return pageGetAnnotStrokeColor( m_hand, page, annot );
	}
	/**
	 * most like Page.SetAnnotStrokeColor();<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned by getPage() or getPageFromPoint()
	 * @param annot
	 * @param color
	 * @return
	 */
	public boolean PageSetAnnotStrokeColor( int page, int annot, int color )
	{
		return pageSetAnnotStrokeColor( m_hand, page, annot, color );
	}
	/**
	 * most like Page.GetAnnotStrokeWidth();<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned by getPage() or getPageFromPoint()
	 * @param annot
	 * @return
	 */
	public float PageGetAnnotStrokeWidth( int page, int annot )
	{
		return pageGetAnnotStrokeWidth( m_hand, page, annot );
	}
	/**
	 * most like Page.SetAnnotStrokeWidth();<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned by getPage() or getPageFromPoint()
	 * @param annot
	 * @param width
	 * @return
	 */
	public boolean PageSetAnnotStrokeWidth( int page, int annot, float width )
	{
		return pageSetAnnotStrokeWidth( m_hand, page, annot, width );
	}
	public Path PageGetAnnotInkPath( int page, int annot )
	{
		int hand = pageGetAnnotInkPath( m_hand, page, annot );
		if( hand != 0 )
		{
			Path path = new Path();
			path.m_hand = hand;
			return path;
		}
		else
			return null;
	}
	public boolean PageSetAnnotInkPath( int page, int annot, Path path )
	{
		return pageSetAnnotInkPath( m_hand, page, annot, path.m_hand );
	}
	/**
	 * get annotation object of the page by view point.<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned by getPage() or getPageFromPoint()
	 * @param x
	 * @param y
	 * @return annotation object or 0
	 */
	public int PageGetAnnotFromPoint( int page, float x, float y )
	{
		return pageGetAnnotFromPoint( m_hand, page, x, y );
	}
	/**
	 * get RECT of an annotation.<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @param rect output value as [left, top, right, bottom] in view coordinate.
	 * @return true or false
	 */
	public boolean PageGetAnnotRect( int page, int annot, float[] rect )
	{
		return pageGetAnnotRect( m_hand, page, annot, rect );
	}
	/**
	 * get type of an annotation<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return type as these values:<br/>
	 * 0:  unknown<br/>
	 * 1:  text<br/>
	 * 2:  link<br/>
	 * 3:  free text<br/>
	 * 4:  line<br/>
	 * 5:  square<br/>
	 * 6:  circle<br/>
	 * 7:  polygon<br/>
	 * 8:  polyline<br/>
	 * 9:  text hilight<br/>
	 * 10: text under line<br/>
	 * 11: text squiggly<br/>
	 * 12: text strikeout<br/>
	 * 13: stamp<br/>
	 * 14: caret<br/>
	 * 15: ink<br/>
	 * 16: popup<br/>
	 * 17: file attachment<br/>
	 * 18: sound<br/>
	 * 19: movie<br/>
	 * 20: widget<br/>
	 * 21: screen<br/>
	 * 22: print mark<br/>
	 * 23: trap net<br/>
	 * 24: water mark<br/>
	 * 25: 3d object<br/>
	 * 26: rich media
	 */
	public int PageGetAnnotType( int page, int annot )
	{
		return pageGetAnnotType( m_hand, page, annot );
	}
	/**
	 * remove annotation object in page.<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return true or false.
	 */
	public boolean PageRemoveAnnot( int page, int annot )
	{
		return pageRemoveAnnot( m_hand, page, annot );
	}
	/**
	 * add a sticky annotation on page.<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param x point in view coordinate.
	 * @param y point in view coordinate.
	 * @return true or false.
	 */
	public boolean PageAddTextAnnot( int page, float x, float y )
	{
		return pageAddTextAnnot( m_hand, page, x, y );
	}
	/**
	 * get sticky annotation's text.<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return String object or null. null means this annotation isn't sticky annotation.
	 */
	public String PageGetAnnotText( int page, int annot )
	{
		return pageGetAnnotText( m_hand, page, annot );
	}
	/**
	 * set sticky annotation's text.<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @param txt text value
	 * @return true or false.
	 */
	public boolean PageSetAnnotText( int page, int annot, String txt )
	{
		return pageSetAnnotText( m_hand, page, annot, txt );
	}
	/**
	 * get sticky annotation's subject(or title).<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return String object or null. null means this annotation isn't sticky annotation.
	 */
	public String PageGetAnnotSubj( int page, int annot )
	{
		return pageGetAnnotSubj( m_hand, page, annot );
	}
	/**
	 * add a free-text annotation on page.<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param rect rect area in view coordinate.
	 * @param tsize size of text, in PDF coordinate
	 * @param color color of text
	 * @return true or false;
	 */
	public boolean PageAddEditboxAnnot( int page, float[] rect, float tsize, int color )
	{
		return pageAddEditboxAnnot( m_hand, page, rect, tsize, color );
	}
	/**
	 * set sticky annotation's subject(or title).<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @param subj string value
	 * @return true or false;
	 */
	public boolean PageSetAnnotSubj( int page, int annot, String subj )
	{
		return pageSetAnnotSubj( m_hand, page, annot, subj );
	}
	/**
	 * get page NO. of Document inner link.<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return 0 based page NO, or -1 means this is not an inner link.
	 */
	public int PageGetAnnotDest( int page, int annot )
	{
		return pageGetAnnotDest( m_hand, page, annot );
	}
	/**
	 * get uri of a web annotation.<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return String object or null. null means this annotation isn't a web link.
	 */
	public String PageGetAnnotURI( int page, int annot )
	{
		return pageGetAnnotURI( m_hand, page, annot );
	}
	/**
	 * this method mostly like Page.GetAnnot3D
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return same as Page.GetAnnot3D
	 */
	public String PageGetAnnot3D( int page, int annot )
	{
		return pageGetAnnot3D( m_hand, page, annot );
	}
	/**
	 * this method mostly like Page.GetAnnotMovie
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return same as Page.GetAnnotMovie
	 */
	public String PageGetAnnotMovie( int page, int annot )
	{
		return pageGetAnnotMovie( m_hand, page, annot );
	}
	/**
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return
	 */
	public String PageGetAnnotSound( int page, int annot )
	{
		return pageGetAnnotSound( m_hand, page, annot );
	}
	/**
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return
	 */
	public String PageGetAnnotAttach( int page, int annot )
	{
		return pageGetAnnotAttach( m_hand, page, annot );
	}
	/**
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @param path
	 * @return
	 */
	public boolean PageGetAnnot3DData( int page, int annot, String path )
	{
		return pageGetAnnot3DData( m_hand, page, annot, path );
	}
	/**
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @param path
	 * @return
	 */
	public boolean PageGetAnnotMovieData( int page, int annot, String path )
	{
		return pageGetAnnotMovieData( m_hand, page, annot, path );
	}
	/**
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @param paras
	 * @param path
	 * @return
	 */
	public boolean PageGetAnnotSoundData( int page, int annot, int[] paras, String path )
	{
		return pageGetAnnotSoundData( m_hand, page, annot, paras, path );
	}
	/**
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @param path
	 * @return
	 */
	public boolean PageGetAnnotAttachData( int page, int annot, String path )
	{
		return pageGetAnnotAttachData( m_hand, page, annot, path );
	}
	/**
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param ink ink object.
	 * @return true or false
	 */
	public boolean PageAddInkAnnot( int page, Ink ink )
	{
		return pageAddInkAnnot( m_hand, page, ink.hand );
	}
	/**
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param rect rect as [left, top, right, bottom] in View coordinate.
	 * @param pageno 0 based page NO.
	 * @param top y coordinate in PDF coordinate
	 * @return true or false
	 */
	public boolean PageAddGotoAnnot( int page, float[] rect, int pageno, float top )
	{
		return pageAddGotoAnnot( m_hand, page, rect, pageno, top );
	}
	/**
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param rect rect as [left, top, right, bottom] in View coordinate.
	 * @param uri URL example: http://www.com.radaee.com/en
	 * @return true or false
	 */
	public boolean PageAddUriAnnot( int page, float[] rect, String uri )
	{
		return pageAddUriAnnot( m_hand, page, rect, uri );
	}
	/**
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param rect rect as [left, top, right, bottom] in View coordinate.
	 * @param width
	 * @param color
	 * @param fill_color
	 * @return
	 */
	public boolean PageAddRectAnnot( int page, float[] rect, float width, int color, int fill_color )
	{
		return pageAddRectAnnot( m_hand, page, rect, width, color, fill_color );
	}

	public boolean PageAddLineAnnot( int page, float[] pt1, float[] pt2, int style1, int style2, float width, int color, int fill_color )
	{
		return  pageAddLineAnnot( m_hand, page, pt1, pt2, style1, style2, width, color, fill_color );
	}

	/**
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param rect rect as [left, top, right, bottom] in View coordinate.
	 * @param width
	 * @param color
	 * @param fill_color
	 * @return
	 */
	public boolean PageAddEllipseAnnot( int page, float[] rect, float width, int color, int fill_color )
	{
		return pageAddEllipseAnnot( m_hand, page, rect, width, color, fill_color );
	}
	/**
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param bitmap
	 * @param has_alpha
	 * @param rect rect as [left, top, right, bottom] in View coordinate.
	 * @return
	 */
	public boolean PageAddBitmapAnnot( int page, Bitmap bitmap, boolean has_alpha, float[] rect )
	{
		return pageAddBitmapAnnot( m_hand, page, bitmap, has_alpha, rect );
	}
	/**
	 * this method mostly like PageAddBitmapAnnot, but this is not annotation.<br/>
	 * it add bitmap to page content directly.<br/>
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param bitmap
	 * @param has_alpha
	 * @param rect rect as [left, top, right, bottom] in View coordinate.
	 * @return
	 */
	public boolean PageAddBitmap( int page, Bitmap bitmap, boolean has_alpha, float[] rect )
	{
		return pageAddBitmap( m_hand, page, bitmap, has_alpha, rect );
	}
	/**
	 * add text-markup annotation for current selection.
	 * this method can be only invoked in profession or premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param clr
	 * @param type
	 * @return
	 */
	public boolean PageAddMarkupAnnot( int page, int clr, int type )
	{
		return pageAddMarkupAnnot( m_hand, page, clr, type );
	}
	/**
	 * this method can be only invoked in premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return
	 */
	public int PageGetAnnotEditType( int page, int annot )
	{
		return pageGetAnnotEditType( m_hand, page, annot );
	}
	/**
	 * this method can be only invoked in premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @param rect output rect as [left, top, right, bottom] in View coordinate.
	 * @return
	 */
	public boolean PageGetAnnotEditTextRect( int page, int annot, float[] rect )
	{
		return pageGetAnnotEditTextRect( m_hand, page, annot, rect );
	}
	/**
	 * this method can be only invoked in premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return
	 */
	public float PageGetAnnotEditTextSize( int page, int annot )
	{
		return pageGetAnnotEditTextSize( m_hand, page, annot );
	}
	/**
	 * this method can be only invoked in premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return
	 */
	public String PageGetAnnotEditText( int page, int annot )
	{
		return pageGetAnnotEditText( m_hand, page, annot );
	}
	/**
	 * this method can be only invoked in premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @param txt
	 * @return
	 */
	public boolean PageSetAnnotEditText( int page, int annot, String txt )
	{
		return pageSetAnnotEditText( m_hand, page, annot, txt );
	}
	/**
	 * this method can be only invoked in premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return
	 */
	public int PageGetAnnotComboItemCount( int page, int annot )
	{
		return pageGetAnnotComboItemCount( m_hand, page, annot );
	}
	/**
	 * this method can be only invoked in premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @param item
	 * @return
	 */
	public String PageGetAnnotComboItem( int page, int annot, int item )
	{
		return pageGetAnnotComboItem( m_hand, page, annot, item );
	}
	/**
	 * this method can be only invoked in premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return
	 */
	public int PageGetAnnotComboSel( int page, int annot )
	{
		return pageGetAnnotComboSel( m_hand, page, annot );
	}
	/**
	 * this method can be only invoked in premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @param item
	 * @return
	 */
	public boolean PageSetAnnotComboSel( int page, int annot, int item )
	{
		return pageSetAnnotComboSel( m_hand, page, annot, item );
	}
	/**
	 * this method can be only invoked in premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return
	 */
	public int PageGetAnnotCheckStatus( int page, int annot )
	{
		return pageGetAnnotCheckStatus( m_hand, page, annot );
	}
	/**
	 * this method can be only invoked in premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @param check
	 * @return
	 */
	public boolean PageSetAnnotCheckValue( int page, int annot, boolean check )
	{
		return pageSetAnnotCheckValue( m_hand, page, annot, check );
	}
	/**
	 * this method can be only invoked in premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return
	 */
	public boolean PageSetAnnotRadio( int page, int annot )
	{
		return pageSetAnnotRadio( m_hand, page, annot );
	}
	/**
	 * this method can be only invoked in premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return
	 */
	public boolean PageGetAnnotReset( int page, int annot )
	{
		return pageGetAnnotReset( m_hand, page, annot );
	}
	/**
	 * this method can be only invoked in premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return
	 */
	public boolean PageSetAnnotReset( int page, int annot )
	{
		return pageSetAnnotReset( m_hand, page, annot );
	}
	/**
	 * this method can be only invoked in premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return
	 */
	public String PageGetAnnotSubmitTarget( int page, int annot )
	{
		return pageGetAnnotSubmitTarget( m_hand, page, annot );
	}
	/**
	 * this method can be only invoked in premium license
	 * @param page page object returned from PDFV.GetPage or PDFV.GetPageFromPoint
	 * @param annot annotation object returned from PDFV.PageGetAnnot or PDFV.PageGetAnnotFromPoint
	 * @return
	 */
	public String PageGetAnnotSubmitPara( int page, int annot )
	{
		return pageGetAnnotSubmitPara( m_hand, page, annot );
	}
}
