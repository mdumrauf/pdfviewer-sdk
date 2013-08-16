package com.radaee.pdf;

import android.graphics.Bitmap;

/**
class for PDF Document.
@author Radaee
@version 1.1
*/
public class Document
{
	public interface PDFStream
	{
		/**
		 * check whether the stream is writable 
		 * @return true or false
		 */
		public boolean writeable();
		/**
		 * get stream length.
		 * @return
		 */
		public int get_size();
		/**
		 * read data from stream
		 * @param data output values.
		 * @return bytes read
		 */
		public int read( byte[] data );
		/**
		 * write data to stream
		 * @param data data to write
		 * @return bytes written
		 */
		public int write( byte[] data );
		/**
		 * seek to position
		 * @param pos position from begin of the stream
		 */
		public void seek( int pos );
		/**
		 * tell current position
		 * @return position from begin of the stream
		 */
		public int tell();
	}
	public class Outline
	{
		protected int hand;
		protected Document doc;
		/**
		 * get label of Outline
		 * @return
		 */
		public String GetTitle()
		{
			return Document.getOutlineTitle(doc.hand_val, hand);
		}
		/**
		 * get next
		 * @return
		 */
		public Outline GetNext()
		{
			int ret =  Document.getOutlineNext(doc.hand_val, hand);
			if( ret == 0 ) return null;
			Outline ol = new Outline();
			ol.hand = ret;
			ol.doc = doc;
			return ol;
		}
		/**
		 * get first child
		 * @return
		 */
		public Outline GetChild()
		{
			int ret =  Document.getOutlineChild(doc.hand_val, hand);
			if( ret == 0 ) return null;
			Outline ol = new Outline();
			ol.hand = ret;
			ol.doc = doc;
			return ol;
		}
		/**
		 * get jumping page NO.
		 * @return 0 based page NO.
		 */
		public int GetDest()
		{
			return Document.getOutlineDest(doc.hand_val, hand);
		}
		/**
		 * insert outline after of this Outline.<br/>
		 * a premium license is needed for this method.
		 * @param label label of new outline.
		 * @param pageno 0 based page NO.
		 * @param top y in PDF coordinate.
		 * @return true of false.
		 */
		public boolean AddNext( String label, int pageno, float top )
		{
			return Document.addOutlineNext(doc.hand_val, hand, label, pageno, top);
		}
		/**
		 * insert outline as first child of this Outline.<br/>
		 * a premium license is needed for this method.
		 * @param label label of new outline.
		 * @param pageno 0 based page NO.
		 * @param top y in PDF coordinate.
		 * @return true or false.
		 */
		public boolean AddChild( String label, int pageno, float top )
		{
			return Document.addOutlineChild(doc.hand_val, hand, label, pageno, top);
		}
		/**
		 * remove this Outline, and all children of this Outline.<br/>
		 * this method connect previous Outline and next Outline.<br/>
		 * a premium license is needed for this method.
		 * @return true or false.
		 */
		public boolean RemoveFromDoc()
		{
			boolean ret = Document.removeOutline(doc.hand_val, hand);
			hand = 0;
			return ret;
		}
	}
	protected int hand_val = 0;
	private int page_count = 0;
	private static native int create( String path );
	private static native int createForStream( PDFStream stream );
	private static native int open( String path, String password );
	private static native int openMem( byte[] data, String password );
	private static native int openStream( PDFStream stream, String password );
	private static native boolean setCache( int hand, String path );
	private static native int getPermission( int hand );
	private static native int getPerm( int hand );
	private static native void close( int hand );
	private static native int getPage( int hand, int pageno );
	private static native int getPageCount( int hand );
	private static native float getPageWidth( int hand, int pageno );
	private static native float getPageHeight( int hand, int pageno );
	private static native boolean changePageRect( int hand, int pageno, float dl, float dt, float dr, float db );
	private static native boolean setPageRotate( int hand, int pageno, int degree );
	private static native String getOutlineTitle( int hand, int outline );
	private static native int getOutlineDest( int hand, int outline );
	private static native int getOutlineNext( int hand, int outline );
	private static native int getOutlineChild( int hand, int outline );
	private static native boolean addOutlineNext( int hand, int outline, String label, int pageno, float top );
	private static native boolean addOutlineChild( int hand, int outline, String label, int pageno, float top );
	private static native boolean removeOutline( int hand, int outline );
	private static native String getMeta( int hand, String tag );
	private static native boolean setMeta( int hand, String tag, String value );
	private static native boolean canSave( int hand );
	private static native boolean save( int hand );
	private static native boolean saveAs( int hand, String dst );//remove security info and save to another file.
	private static native boolean isEncrypted( int hand );

	private static native int newPage( int hand, int pageno, float w, float h );
	private static native boolean removePage( int hand, int pageno );
	private static native int newFontCID( int hand, String name, int style );
	private static native float getFontAscent( int hand, int font );
	private static native float getFontDescent( int hand, int font );
	private static native int newGState(int hand);
	private static native boolean setGStateStrokeAlpha(int hand, int gstate, int alpha);
	private static native boolean setGStateFillAlpha(int hand, int gstate, int alpha);
	private static native int newImage( int hand, Bitmap bmp, boolean has_alpha );
	private static native int newImageJPEG( int hand, String path );
	private static native int newImageJPX( int hand, String path );
	public class DocFont
	{
		protected int hand;
		Document doc;
		/**
		 * get ascent
		 * @return ascent based in 1, for example: 0.88f
		 */
		public float GetAscent()
		{
			return getFontAscent(doc.hand_val, hand);
		}
		/**
		 * get descent
		 * @return ascent based in 1, for example: -0.12f
		 */
		public float GetDescent()
		{
			return getFontDescent(doc.hand_val, hand);
		}
	}
	public class DocGState
	{
		protected int hand;
		Document doc;
		/**
		 * set alpha value for fill and other non-stroke operation
		 * @param alpha range: [0, 255]
		 * @return true or false
		 */
		public boolean SetFillAlpha(int alpha)
		{
			return Document.setGStateFillAlpha(doc.hand_val, hand, alpha);
		}
		/**
		 * set alpha value for stroke operation
		 * @param alpha range: [0, 255]
		 * @return true or false
		 */
		public boolean SetStrokeAlpha(int alpha)
		{
			return Document.setGStateStrokeAlpha(doc.hand_val, hand, alpha);
		}
	}
	public class DocImage
	{
		protected int hand;
	}
	public Document()
	{
	}
	/**
	 * inner function.
	 * @param vals
	 */
	public Document(int vals[] )
	{
		if( vals != null )
		{
			hand_val = vals[0];
			page_count = vals[1];
		}
	}
	/**
	 * inner function.
	 * @return inner value
	 */
	public int[] getVals()
	{
		int vals[] = new int[2];
		vals[0] = hand_val;
		vals[1] = vals[1];
		return vals;
	}
	private int getOutlineRoot( int hand )
	{
		return getOutlineNext( hand_val, 0 );
	}
	/**
	 * check if opened.
	 * @return true or false.
	 */
	public boolean is_opened()
	{
		return (hand_val != 0);
	}
	/**
	 * create a empty PDF document
	 * @param path path to create
	 * @return 0 or less than 0 means failed, same as Open.
	 */
	public int Create( String path )
	{
		if( hand_val == 0 )
		{
			int ret = 0;
			hand_val = create( path );
			if( hand_val <= 0 && hand_val >= -10 )//error
			{
				ret = hand_val;
				hand_val = 0;
				page_count = 0;
			}
			else
				page_count = getPageCount(hand_val);
			return ret;
		}
		return 0;
	}
	/**
	 * create a empty PDF document
	 * @param stream stream to create
	 * @return 0 or less than 0 means failed, same as Open.
	 */
	public int CreateForStream( PDFStream stream )
	{
		if( hand_val == 0 )
		{
			int ret = 0;
			hand_val = createForStream( stream );
			if( hand_val <= 0 && hand_val >= -10 )//error
			{
				ret = hand_val;
				hand_val = 0;
				page_count = 0;
			}
			else
				page_count = getPageCount(hand_val);
			return ret;
		}
		return 0;
	}
	/**
	 * set cache file to PDF.<br/>
	 * a premium license is needed for this method.
	 * @param path a path to save some temporary data, compressed images and so on
	 * @return true or false
	 */
	public boolean SetCache( String path )
	{
		return setCache( hand_val, path );
	}
	/**
	 * open document.<br/>
	 * first time, SDK try password as user password, and then try password as owner password.
	 * @param path PDF file to be open.
	 * @param password password or null.
	 * @return error code:<br/>
	 * 0:succeeded, and continue<br/>
	 * -1:need input password<br/>
	 * -2:unknown encryption<br/>
	 * -3:damaged or invalid format<br/>
	 * -10:access denied or invalid file path<br/>
	 * others:unknown error
	 */
	public int Open( String path, String password )
	{
		if( hand_val == 0 )
		{
			int ret = 0;
			hand_val = open( path, password );
			if( hand_val <= 0 && hand_val >= -10 )//error
			{
				ret = hand_val;
				hand_val = 0;
				page_count = 0;
			}
			else
				page_count = getPageCount(hand_val);
			return ret;
		}
		return 0;
	}
	/**
	 * open document in memory.
	 * first time, SDK try password as user password, and then try password as owner password.
	 * @param data data for whole PDF file in byte array. developers should retain array data, till document closed.
	 * @param password password or null.
	 * @return error code:<br/>
	 * 0:succeeded, and continue<br/>
	 * -1:need input password<br/>
	 * -2:unknown encryption<br/>
	 * -3:damaged or invalid format<br/>
	 * -10:access denied or invalid file path<br/>
	 * others:unknown error
	 */
	public int OpenMem( byte[] data, String password )
	{
		if( hand_val == 0 )
		{
			int ret = 0;
			hand_val = openMem( data, password );
			if( hand_val <= 0 && hand_val >= -10 )//error
			{
				ret = hand_val;
				hand_val = 0;
				page_count = 0;
			}
			else
				page_count = getPageCount(hand_val);
			return ret;
		}
		return 0;
	}
	/**
	 * open document from stream.
	 * first time, SDK try password as user password, and then try password as owner password.
	 * @param stream PDFStream object.
	 * @param password password or null.
	 * @return error code:<br/>
	 * 0:succeeded, and continue<br/>
	 * -1:need input password<br/>
	 * -2:unknown encryption<br/>
	 * -3:damaged or invalid format<br/>
	 * -10:access denied or invalid file path<br/>
	 * others:unknown error
	 */
	public int OpenStream( PDFStream stream, String password )
	{
		if( hand_val == 0 )
		{
			int ret = 0;
			hand_val = openStream( stream, password );
			if( hand_val <= 0 && hand_val >= -10 )//error
			{
				ret = hand_val;
				hand_val = 0;
				page_count = 0;
			}
			else
				page_count = getPageCount(hand_val);
			return ret;
		}
		return 0;
	}
	/**
	 * get permission of PDF, this value defined in PDF reference 1.7<br/>
	 * bit 1-2 reserved<br/>
	 * bit 3(0x4) print<br/>
	 * bit 4(0x8) modify<br/>
	 * bit 5(0x10) extract text or image<br/>
	 * others: see PDF reference
	 * @return permission flags
	 */
	public int GetPermission()
	{
		return getPermission( hand_val );
	}
	/**
	 * get permission of PDF, this value defined in "Perm" entry in Catalog object
	 * @return 0 means not defined<br/>
	 * 1 means can't modify<br/>
	 * 2 means can modify some form fields<br/>
	 * 3 means can do any modify<br/>
	 */
	public int GetPerm()
	{
		return getPerm( hand_val );
	}
	/**
	 * close the document.
	 */
	public void Close()
	{
		if( hand_val != 0 )
			close( hand_val );
		hand_val = 0;
		page_count = 0;
	}
	/**
	 * get a Page object for page NO.
	 * @param pageno 0 based page NO. range:[0, GetPageCount()-1]
	 * @return Page object
	 */
	public Page GetPage( int pageno )
	{
		if( hand_val == 0 ) return null;
		int hand = getPage( hand_val, pageno );
		if( hand == 0 ) return null;
		Page page = new Page();
		if( page != null ) page.hand = hand;
		return page;
	}
	/**
	 * get pages count.
	 * @return pages count.
	 */
	public int GetPageCount()
	{
		//it loads all pages. sometimes the function is very slow.
		return page_count;
	}
	/**
	 * get page width by page NO.
	 * @param pageno 0 based page NO. range:[0, GetPageCount()-1]
	 * @return width value.
	 */
	public float GetPageWidth( int pageno )
	{
		float w = getPageWidth( hand_val, pageno );
		if( w <= 0 ) return 1;
		else return w;
	}
	/**
	 * get page height by page NO.
	 * @param pageno 0 based page NO. range:[0, GetPageCount()-1]
	 * @return height value.
	 */
	public float GetPageHeight( int pageno )
	{
		float h = getPageHeight( hand_val, pageno );
		if( h <= 0 ) return 1;
		else return h;
	}
	/**
	 * get meta data for document.
	 * @param tag Predefined values:"Title", "Author", "Subject", "Keywords", "Creator", "Producer", "CreationDate", "ModDate".<br/>or you can pass any key that self-defined.
	 * @return Meta string value, or null.
	 */
	public String GetMeta( String tag )
	{
		return getMeta( hand_val, tag );
	}
	/**
	 * set meta data for document.<br/>
	 * this method valid only in premium version.
	 * @param tag Predefined values:"Title", "Author", "Subject", "Keywords", "Creator", "Producer", "CreationDate", "ModDate".<br/>or you can pass any key that self-defined.
	 * @param val string value.
	 * @return true or false.
	 */
	public boolean SetMeta( String tag, String val )
	{
		return setMeta( hand_val, tag, val );
	}
	/**
	 * get root outline item.
	 * @return handle value of first root outline item. or 0 if no outlines.<br/>
	 */
	public Outline GetOutlines()
	{
		int ret = getOutlineRoot(hand_val);
		if( ret == 0 ) return null;
		Outline ol = new Outline();
		ol.doc = this;
		ol.hand = ret;
		return ol;
	}
	/**
	 * check if document can be modified or saved.<br/>
	 * this always return false, if no license actived.
	 * @return true or false.
	 */
	public boolean CanSave()
	{
		return canSave( hand_val );
	}
	/**
	 * save the document.<br/>
	 * this always return false, if no license actived.
	 * @return true or false
	 */
	public boolean Save()
	{
		return save( hand_val );
	}
	/**
	 * save as the document to another file. it remove any security information.<br/>
	 * this always return false, if no license actived.
	 * @param path path to save.
	 * @return true or false.
	 */
	public boolean SaveAs( String path )
	{
		return saveAs( hand_val, path );
	}
	/**
	 * check if document is encrypted.
	 * @return true or false.
	 */
	public boolean IsEncrypted()
	{
		return isEncrypted( hand_val );
	}
	/**
	 * new a root outline to document, it insert first root outline to Document.<br/>
	 * the old first root outline, shall be next of this outline. 
	 * @param label label to display
	 * @param pageno pageno to jump
	 * @param top y position in PDF coordinate
	 * @return true or false
	 */
	public boolean NewRootOutline( String label, int pageno, float top )
	{
		return addOutlineChild(hand_val, 0, label, pageno, top);
	}
	/**
	 * insert a page to Document<br/>
	 * if pagheno >= page_count, it do same as append.<br/>
	 * otherwise, insert to pageno.<br/>
	 * a premium license is needed for this method.
	 * @param pageno 0 based page NO.
	 * @param w page width in PDF coordinate
	 * @param h page height in PDF coordinate
	 * @return Page object or null means failed.
	 */
	public Page NewPage( int pageno, float w, float h )
	{
		int ret = newPage( hand_val, pageno, w, h );
		if( ret != 0 )
		{
			Page page = new Page();
			page.hand = ret;
			return page;
		}
		else return null;
	}
	/**
	 * remove page by page NO.<br/>
	 * a premium license is needed for this method.
	 * @param pageno 0 based page NO.
	 * @return true or false
	 */
	public boolean RemovePage( int pageno )
	{
		return removePage(hand_val, pageno);
	}
	/**
	 * create a font object, used to write texts.<br/>
	 * a premium license is needed for this method.
	 * @param font_name <br/>
	 * font name exists in font list.<br/>
	 * using Global.getFaceCount(), Global.getFaceName() to enumerate fonts.
	 * @param style <br/>
	 *   (style&1) means bold,<br/>
	 *   (style&2) means Italic,<br/>
	 *   (style&8) means embed,<br/>
	 *   (style&16) means vertical writing, mostly used in Asia fonts.
	 * @return DocFont object or null is failed.
	 */
	public DocFont NewFontCID( String font_name, int style )
	{
		int ret = newFontCID(hand_val, font_name, style);
		if( ret != 0 )
		{
			DocFont font = new DocFont();
			font.hand = ret;
			font.doc = this;
			return font;
		}
		else return null;
	}
	/**
	 * create a ExtGraphicState object, used to set alpha values.<br/>
	 * a premium license is needed for this method.
	 * @return DocGState object or null.
	 */
	public DocGState NewGState()
	{
		int ret = newGState(hand_val);
		if( ret != 0 )
		{
			DocGState gs = new DocGState();
			gs.hand = ret;
			gs.doc = this;
			return gs;
		}
		else return null;
	}
	/**
	 * create an image from Bitmap object.<br/>
	 * a premium license is needed for this method.
	 * @param bmp Bitmap object in ARGB_8888 format.
	 * @param has_alpha generate alpha channel information?
	 * @return DocImage object or null.
	 */
	public DocImage NewImage( Bitmap bmp, boolean has_alpha )
	{
		int ret = newImage(hand_val, bmp, has_alpha);
		if( ret != 0 )
		{
			DocImage img = new DocImage();
			img.hand = ret;
			return img;
		}
		else return null;
	}
	/**
	 * create an image from JPEG/JPG file.<br/>
	 * supported image color space:<br/>
	 * --GRAY<br/>
	 * --RGB<br/>
	 * --CMYK<br/>
	 * a premium license is needed for this method.
	 * @param path path to JPEG file.
	 * @return DocImage object or null.
	 */
	public DocImage NewImageJPEG( String path )
	{
		int ret = newImageJPEG(hand_val, path);
		if( ret != 0 )
		{
			DocImage img = new DocImage();
			img.hand = ret;
			return img;
		}
		else return null;
	}
	/**
	 * create an image from JPX/JPEG 2k file.<br/>
	 * a premium license is needed for this method.
	 * @param path path to JPX file.
	 * @return DocImage object or null.
	 */
	public DocImage NewImageJPX( String path )
	{
		int ret = newImageJPX(hand_val, path);
		if( ret != 0 )
		{
			DocImage img = new DocImage();
			img.hand = ret;
			return img;
		}
		else return null;
	}
	/**
	 * change page rect.<br/>
	 * a premium license is needed for this method.
	 * @param pageno 0 based page NO.
	 * @param dl delta to left, page_left += dl;
	 * @param dt delta to top, page_top += dt;
	 * @param dr delta to right, page_right += dr;
	 * @param db delta to bottom, page_bottom += db;
	 * @return true or false.
	 */
	public boolean ChangePageRect( int pageno, float dl, float dt, float dr, float db )
	{
		return changePageRect( hand_val, pageno, dl, dt, dr, db );
	}
	/**
	 * set page rotate.<br/>
	 * a premium license is needed for this method.
	 * @param pageno 0 based page NO.
	 * @param degree rotate angle in degree, must be 90 * n.
	 * @return true or false
	 */
	public boolean SetPageRotate( int pageno, int degree )
	{
		return setPageRotate( hand_val, pageno, degree );
	}
}
