package com.radaee.pdf;

import com.radaee.pdf.Document.*;

import android.graphics.Bitmap;

/**
class for PDF Page.
@author Radaee
@version 1.1
*/
public class Page
{
	public class ResFont
	{
		protected int hand;
	}
	public class ResGState
	{
		protected int hand;
	}
	public class ResImage
	{
		protected int hand;
	}
	public class Annotation
	{
		protected int hand;
		protected Page page;
		/**
		 * get annotation field type in acroForm.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in premium version
		 * @return type as these values:<br/>
		 * 0: unknown<br/>
		 * 1: button field<br/>
		 * 2: text field<br/>
		 * 3: choice field<br/>
		 * 4: signature field<br/>
		 */
		public int GetFieldType()
		{
			return Page.getAnnotFieldType(page.hand, hand);
		}
		/**
		 * get annotation type.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
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
		public int GetType()
		{
			return Page.getAnnotType(page.hand, hand);
		}
		/**
		 * check if position and size of the annotation is locked?<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @return true if locked, or not locked.
		 */
		public boolean IsLocked()
		{
			return Page.isAnnotLocked(page.hand, hand);
		}
		/**
		 * check if texts of the annotation is locked?<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @return true if locked, or not locked.
		 */
		public boolean IsLockedContent()
		{
			return Page.isAnnotLockedContent(page.hand, hand);
		}
		/**
		 * check whether the annotation is hide.
		 * @return true or false.
		 */
		public boolean IsHide()
		{
			return Page.isAnnotHide(page.hand, hand);
		}
		/**
		 * set annotation's box rectangle.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @return 4 elements: left, top, right, bottom in PDF coordinate system
		 */
		public float[] GetRect()
		{
			float rect[] = new float[4];
			Page.getAnnotRect(page.hand, hand, rect);
			return rect;
		}
		/**
		 * set annotation's box rectangle.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version.<br/>
		 * you shall render page after this invoked, to resize or move annotation.
		 */
		public void SetRect( float left, float top, float right, float bottom )
		{
			float rect[] = new float[4];
			rect[0] = left;
			rect[1] = top;
			rect[2] = right;
			rect[3] = bottom;
			Page.setAnnotRect(page.hand, hand, rect);
		}
		/**
		 * set hide status for annotation.
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version.<br/>
		 * you shall render page after this invoked, to hide annotation.
		 * @param hide true or false.
		 */
		public void SetHide( boolean hide )
		{
			Page.setAnnotHide(page.hand, hand, hide);
		}
		/**
		 * get annotation's popup text.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version.
		 * @return text string or null if failed.
		 */
		public String GetPopupText()
		{
			return Page.getAnnotPopupText(page.hand, hand);
		}
		/**
		 * set annotation's popup text.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @param val text string
		 * @return true or false
		 */
		public boolean SetPopupText( String val )
		{
			return Page.setAnnotPopupText(page.hand, hand, val);
		}
		/**
		 * get annotation's popup subject.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @return subject string or null if failed.
		 */
		public String GetPopupSubject()
		{
			return Page.getAnnotPopupSubject(page.hand, hand);
		}
		/**
		 * set annotation's popup subject.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @param val subject string
		 * @return true or false
		 */
		public boolean SetPopupSubject( String val )
		{
			return Page.setAnnotPopupSubject(page.hand, hand, val);
		}
		/**
		 * get annotation's destination.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @return 0 based page NO, or -1 if failed.
		 */
		public int GetDest()
		{
			return Page.getAnnotDest(page.hand, hand);
		}
		/**
		 * get annotation's URL link string.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @return string of URL, or null
		 */
		public String GetURI()
		{
			return Page.getAnnotURI(page.hand, hand);
		}
		/**
		 * get annotation's 3D object name.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @return name of the 3D object, or null
		 */
		public String Get3D()
		{
			return Page.getAnnot3D(page.hand, hand);
		}
		/**
		 * get annotation's movie name.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @return name of the movie, or null
		 */
		public String GetMovie()
		{
			return Page.getAnnotMovie(page.hand, hand);
		}
		/**
		 * get annotation's sound name.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @return name of the audio, or null
		 */
		public String GetSound()
		{
			return Page.getAnnotSound(page.hand, hand);
		}
		/**
		 * get annotation's attachment name.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @return name of the attachment, or null
		 */
		public String GetAttachment()
		{
			return Page.getAnnotAttachment(page.hand, hand);
		}
		/**
		 * get annotation's 3D data. must be *.u3d format.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @param save_file full path name to save data.
		 * @return true if save_file created, or false.
		 */
		public boolean Get3DData( String save_file )
		{
			return Page.getAnnot3DData(page.hand, hand, save_file);
		}
		/**
		 * get annotation's movie data.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @param save_file full path name to save data.
		 * @return true if save_file created, or false.
		 */
		public boolean GetMovieData( String save_file )
		{
			return Page.getAnnotMovieData(page.hand, hand, save_file);
		}
		/**
		 * get annotation's sound data.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @param paras paras[0] == 0, if formated audio file(*.mp3 ...).
		 * @param save_file full path name to save data.
		 * @return true if save_file created, or false.
		 */
		public boolean GetSoundData( int paras[], String save_file )
		{
			return Page.getAnnotSoundData(page.hand, hand, paras, save_file);
		}
		/**
		 * get annotation's attachment data.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @param save_file full path name to save data.
		 * @return true if save_file created, or false.
		 */
		public boolean GetAttachmentData( String save_file )
		{
			return Page.getAnnotAttachmentData(page.hand, hand, save_file);
		}
		/**
		 * get type of edit-box.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in premium version
		 * @return <br/>-1: this annotation is not text-box.<br/> 1: normal single line.<br/>2: password.<br/>3: MultiLine edit area.
		 */
		public int GetEditType()
		{
			return Page.getAnnotEditType(page.hand, hand);
		}
		/**
		 * get max-len of edit-box.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in premium version
		 * @return 0 if no limit, great than 0 if has limit.
		 */
		public int GetEditMaxlen()
		{
			return Page.getAnnotEditMaxlen(page.hand, hand);
		}
		/**
		 * get position and size of edit-box.<br/>
		 * for FreeText annotation, position of edit-box is not the position of annotation.<br/>
		 * so this function is needed for edit-box.
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in premium version
		 * @param rect 4 elements in order: left, top, right, bottom, in PDF coordinate.
		 * @return true or false
		 */
		public boolean GetEditTextRect( float[] rect )
		{
			return Page.getAnnotEditTextRect(page.hand, hand, rect);
		}
		/**
		 * get text size of edit-box.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in premium version
		 * @return size of text, in PDF coordinate system.
		 */
		public float GetEditTextSize()
		{
			return Page.getAnnotEditTextSize(page.hand, hand);
		}
		/**
		 * get contents of edit-box.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in premium version
		 * @return content in edit-box
		 */
		public String GetEditText()
		{
			return Page.getAnnotEditText(page.hand, hand);
		}
		/**
		 * set contents of edit-box.<br/>
		 * you should re-render page to display modified data.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in premium version
		 * @param text contents to be set.<br/>in MultiLine mode: '\r' or '\n' means change line.<br/>in password mode the edit box always display "*". 
		 * @return true or false.
		 */
		public boolean SetEditText( String text )
		{
			return Page.setAnnotEditText(page.hand, hand, text);
		}
		/**
		 * get item count of combo-box.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in premium version
		 * @return -1: this is not combo. otherwise: items count.
		 */
		public int GetComboItemCount()
		{
			return Page.getAnnotComboItemCount(page.hand, hand);
		}
		/**
		 * get an item of combo-box.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in premium version
		 * @param item 0 based item index. range:[0, GetAnnotComboItemCount()-1]
		 * @return null if this is not combo-box, "" if no item selected, otherwise the item selected.
		 */
		public String GetComboItem( int item )
		{
			return Page.getAnnotComboItem(page.hand, hand, item);
		}
		/**
		 * get current selected item index of combo-box.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in premium version
		 * @return -1 if this is not combo-box or no item selected, otherwise the item index that selected.
		 */
		public int GetComboItemSel()
		{
			return Page.getAnnotComboItemSel(page.hand, hand);
		}
		/**
		 * set current selected.<br/>
		 * you should re-render page to display modified data.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in premium version
		 * @param item 0 based item index to set.
		 * @return true or false.
		 */
		public boolean SetComboItem( int item )
		{
			return Page.setAnnotComboItem(page.hand, hand, item);
		}
		/**
		 * get status of check-box and radio-box.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in premium version
		 * @return <br/>-1 if annotation is not valid control.<br/>0 if check-box is not checked.<br/>1 if check-box checked.<br/>2 if radio-box is not checked.<br/>3 if radio-box checked.
		 */
		public int GetCheckStatus()
		{
			return Page.getAnnotCheckStatus(page.hand, hand);
		}
		/**
		 * set value to check-box.<br/>
		 * you should re-render page to display modified data.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in premium version
		 * @param check true or false.
		 * @return true or false.
		 */
		public boolean SetCheckValue( boolean check )
		{
			return Page.setAnnotCheckValue(page.hand, hand, check);
		}
		/**
		 * check the radio-box and deselect others in radio group.<br/>
		 * you should re-render page to display modified data.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in premium version
		 * @return true or false.
		 */
		public boolean SetRadio()
		{
			return Page.setAnnotRadio(page.hand, hand);
		}
		/**
		 * check if the annotation is reset button?<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in premium version
		 * @return true or false.
		 */
		public boolean GetReset()
		{
			return Page.getAnnotReset(page.hand, hand);
		}
		/**
		 * perform the button and reset the form.<br/>
		 * you should re-render page to display modified data.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in premium version
		 * @return true or false.
		 */
		public boolean SetReset()
		{
			return Page.setAnnotReset(page.hand, hand);
		}
		/**
		 * get annotation submit target.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in premium version
		 * @return null if this is not submit button.
		 */
		public String GetSubmitTarget()
		{
			return Page.getAnnotSubmitTarget(page.hand, hand);
		}
		/**
		 * get annotation submit parameters.<br/>
		 * mail mode: return whole XML string for form data.<br/>
		 * other mode: url data likes: "para1=xxx&para2=xxx".<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in premium version
		 * @return null if this is not submit button.
		 */
		public String GetSubmitPara()
		{
			return Page.getAnnotSubmitPara(page.hand, hand);
		}
		/**
		 * get fill color of square/circle/highlight/line/ploygon/polyline/sticky text annotation.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @return color value formatted as 0xAARRGGBB, if 0 returned, means false.
		 */
		public int GetFillColor()
		{
			return Page.getAnnotFillColor(page.hand, hand);
		}
		/**
		 * set fill color of square/circle/highlight/line/ploygon/polyline/sticky text annotation.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * you need render page again to show modified annotation.<br/>
		 * this method valid in professional or premium version
		 * @param color color value formatted as 0xAARRGGBB, if alpha channel is too less or 0, return false.
		 * @return true or false
		 */
		public boolean SetFillColor( int color )
		{
			return Page.setAnnotFillColor(page.hand, hand, color);
		}
		/**
		 * get fill color of square/circle/ink/line/underline/Squiggly/strikeout/ploygon/polyline annotation.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @return color value formatted as 0xAARRGGBB, if 0 returned, means false.
		 */
		public int GetStrokeColor()
		{
			return Page.getAnnotStrokeColor(page.hand, hand);
		}
		/**
		 * set stroke color of square/circle/ink/line/underline/Squiggly/strikeout/ploygon/polyline annotation.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * you need render page again to show modified annotation.<br/>
		 * this method valid in professional or premium version
		 * @param color color value formatted as 0xAARRGGBB, if alpha channel is too less or 0, return false.
		 * @return true or false
		 */
		public boolean SetStrokeColor( int color )
		{
			return Page.setAnnotStrokeColor(page.hand, hand, color);
		}
		/**
		 * get stroke width of square/circle/ink/line/ploygon/polyline/free text annotation.<br/>
		 * for free text annotation: width of edit-box border<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @return width value in PDF coordinate, or 0 if error.
		 */
		public float GetStrokeWidth()
		{
			return Page.getAnnotStrokeWidth(page.hand, hand);
		}
		/**
		 * set stroke width of square/circle/ink/line/ploygon/polyline/free text annotation.<br/>
		 * for free text annotation: width of edit-box border<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * you need render page again to show modified annotation.<br/>
		 * this method valid in professional or premium version
		 * @param width stroke width in PDF coordinate.
		 * @return true or false
		 */
		public boolean SetStrokeWidth( float width )
		{
			return Page.setAnnotStrokeWidth(page.hand, hand, width);
		}
		/**
		 * get Path object from Ink annotation.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @return a new Path object, you need invoke Path.Destroy() to free memory.
		 */
		public Path GetInkPath()
		{
			int ret = Page.getAnnotInkPath(page.hand, hand);
			if( ret == 0 ) return null;
			Path path = new Path();
			path.m_hand = ret;
			return path;
		}
		/**
		 * set Path to Ink annotation.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * you need render page again to show modified annotation.<br/>
		 * this method valid in professional or premium version
		 * @param path Path object.
		 * @return true or false.
		 */
		public boolean SetInkPath( Path path )
		{
			if( path == null ) return false;
			return Page.setAnnotInkPath(page.hand, hand, path.m_hand);
		}
		/**
		 * get Path object from Polygon annotation.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @return a new Path object, you need invoke Path.Destroy() to free memory.
		 */
		public Path GetPolygonPath()
		{
			int ret = Page.getAnnotPolygonPath(page.hand, hand);
			if( ret == 0 ) return null;
			Path path = new Path();
			path.m_hand = ret;
			return path;
		}
		/**
		 * set Path to Polygon annotation.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * you need render page again to show modified annotation.<br/>
		 * this method valid in professional or premium version
		 * @param path Path object.
		 * @return true or false.
		 */
		public boolean SetPolygonPath( Path path )
		{
			if( path == null ) return false;
			return Page.setAnnotPolygonPath(page.hand, hand, path.m_hand);
		}
		/**
		 * get Path object from Polyline annotation.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @return a new Path object, you need invoke Path.Destroy() to free memory.
		 */
		public Path GetPolylinePath()
		{
			int ret = Page.getAnnotPolylinePath(page.hand, hand);
			if( ret == 0 ) return null;
			Path path = new Path();
			path.m_hand = ret;
			return path;
		}
		/**
		 * set Path to Polyline annotation.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * you need render page again to show modified annotation.<br/>
		 * this method valid in professional or premium version
		 * @param path Path object.
		 * @return true or false.
		 */
		public boolean SetPolylinePath( Path path )
		{
			if( path == null ) return false;
			return Page.setAnnotPolylinePath(page.hand, hand, path.m_hand);
		}
		/**
		 * set icon for sticky text note/file attachment annotation.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * you need render page again to show modified annotation.<br/>
		 * this method valid in professional or premium version
		 * @param icon icon value depends on annotation type.<br/>
		 * <strong>For sticky text note:</strong><br/>
		 * 0: Note<br/>
		 * 1: Comment<br/>
		 * 2: Key<br/>
		 * 3: Help<br/>
		 * 4: NewParagraph<br/>
		 * 5: Paragraph<br/>
		 * 6: Insert<br/>
		 * 7: Check<br/>
		 * 8: Circle<br/>
		 * 9: Cross<br/>
		 * <strong>For file attachment:</strong><br/>
		 * 0: PushPin<br/>
		 * 1: Graph<br/>
		 * 2: Paperclip<br/>
		 * 3: Tag<br/>
		 * @return true or false.
		 */
		public boolean SetIcon( int icon )
		{
			return Page.setAnnotIcon(page.hand, hand, icon);
		}
		/**
		 * get icon value for sticky text note/file attachment annotation.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @return icon value depends on annotation type.<br/>
		 * <strong>For sticky text note:</strong><br/>
		 * 0: Note<br/>
		 * 1: Comment<br/>
		 * 2: Key<br/>
		 * 3: Help<br/>
		 * 4: NewParagraph<br/>
		 * 5: Paragraph<br/>
		 * 6: Insert<br/>
		 * 7: Check<br/>
		 * 8: Circle<br/>
		 * 9: Cross<br/>
		 * <strong>For file attachment:</strong><br/>
		 * 0: PushPin<br/>
		 * 1: Graph<br/>
		 * 2: Paperclip<br/>
		 * 3: Tag<br/>
		 */
		public int GetIcon()
		{
			return Page.getAnnotInkPath(page.hand, hand);
		}
		/**
		 * remove annotation<br/>
		 * you should re-render page to display modified data.<br/>
		 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
		 * this method valid in professional or premium version
		 * @return true or false
		 */
		public boolean RemoveFromPage()
		{
			boolean ret = removeAnnot( page.hand, hand );
			hand = 0;
			return ret;
		}
	}
	public class Finder
	{
		protected int hand;
		/**
		 * get find count in this page.
		 * @param hand_finder handle of find session, obtained by FindOpen.
		 * @return count or 0 if no found.
		 */
		public int GetCount()
		{
			return Page.findGetCount( hand );
		}
		/**
		 * get find count in this page.
		 * @param hand_finder handle of find session, obtained by FindOpen.
		 * @param index 0 based index value. range:[0, FindGetCount()-1]
		 * @return the first char index of texts, see: ObjsGetString. range:[0, ObjsGetCharCount()-1]
		 */
		public int GetFirstChar( int index )
		{
			return Page.findGetFirstChar( hand, index );
		}
		/**
		 * free memory of find session.
		 * @param hand_finder handle of find session, obtained by FindOpen.
		 */
		public void Close()
		{
			Page.findClose( hand );
			hand = 0;
		}
	}
	protected int hand = 0;
	static private native void close( int hand );
	static private native void renderPrepare( int hand, int dib );
	static private native boolean render( int hand, int dib, int matrix, int quality );
	static private native boolean renderToBmp( int hand, Bitmap bitmap, int matrix, int quality );
	static private native void renderCancel(int hand);
	static private native boolean renderThumb(int hand, Bitmap bmp);
	static private native boolean renderIsFinished(int hand);
	static private native float reflowStart( int hand, float width, float scale );
	static private native boolean reflow( int hand, int dib, float orgx, float orgy );
	static private native boolean reflowToBmp( int hand, Bitmap bitmap, float orgx, float orgy );
	static private native int reflowGetParaCount( int hand );
	static private native int reflowGetCharCount( int hand, int iparagraph );
	static private native float reflowGetCharWidth( int hand, int iparagraph, int ichar );
	static private native float reflowGetCharHeight( int hand, int iparagraph, int ichar );
	static private native int reflowGetCharColor( int hand, int iparagraph, int ichar );
	static private native int reflowGetCharUnicode( int hand, int iparagraph, int ichar );
	static private native String reflowGetCharFont( int hand, int iparagraph, int ichar );
	static private native void reflowGetCharRect( int hand, int iparagraph, int ichar, float rect[] );
	static private native String reflowGetText( int hand, int iparagraph1, int ichar1, int iparagraph2, int ichar2 );

	static private native void objsStart( int hand, boolean rtol );
	static private native String objsGetString( int hand, int from, int to );
	static private native int objsAlignWord( int hand, int from, int dir );
	static private native void objsGetCharRect( int hand, int index, float[]vals );
	static private native String objsGetCharFontName( int hand, int index );
	static private native int objsGetCharCount( int hand );
	static private native int objsGetCharIndex( int hand, float[] pt );

	static private native int findOpen( int hand, String str, boolean match_case, boolean whole_word );
	static private native int findGetCount( int hand_finder );
	static private native int findGetFirstChar( int hand_finder, int index );
	static private native int findClose( int hand_finder );

	private static native int getRotate( int hand );
	static private native int getAnnotCount( int hand );
	static private native int getAnnot( int hand, int index );
	static private native int getAnnotFromPoint( int hand, float x, float y );
	static private native int getAnnotFieldType( int hand, int annot );
	static private native int getAnnotType( int hand, int annot );
	static private native boolean isAnnotLocked( int hand, int annot );
	static private native boolean isAnnotHide( int hand, int annot );
	static private native boolean isAnnotLockedContent( int hand, int annot );
	static private native void getAnnotRect( int hand, int annot, float[] rect );
	static private native void setAnnotRect( int hand, int annot, float[] rect );
	static private native void setAnnotHide( int hand, int annot, boolean hide );
	static private native String getAnnotPopupText( int hand, int annot );
	static private native boolean setAnnotPopupText( int hand, int annot, String val );
	static private native String getAnnotPopupSubject( int hand, int annot );
	static private native boolean setAnnotPopupSubject( int hand, int annot, String val );
	static private native int getAnnotDest( int hand, int annot );
	static private native String getAnnotURI( int hand, int annot );
	static private native String getAnnot3D( int hand, int annot );
	static private native String getAnnotMovie( int hand, int annot );
	static private native String getAnnotSound( int hand, int annot );
	static private native String getAnnotAttachment( int hand, int annot );
	static private native boolean getAnnot3DData( int hand, int annot, String save_file );
	static private native boolean getAnnotMovieData( int hand, int annot, String save_file );
	static private native boolean getAnnotSoundData( int hand, int annot, int paras[], String save_file );
	static private native boolean getAnnotAttachmentData( int hand, int annot, String save_file );
	static private native int getAnnotEditType( int hand, int annot );
	static private native int getAnnotEditMaxlen( int hand, int annot );
	static private native boolean getAnnotEditTextRect( int hand, int annot, float[] rect );
	static private native float getAnnotEditTextSize( int hand, int annot );
	static private native String getAnnotEditText( int hand, int annot );
	static private native boolean setAnnotEditText( int hand, int annot, String text );
	static private native int getAnnotComboItemCount( int hand, int annot );
	static private native String getAnnotComboItem( int hand, int annot, int item );
	static private native int getAnnotComboItemSel( int hand, int annot );
	static private native boolean setAnnotComboItem( int hand, int annot, int item );
	static private native int getAnnotCheckStatus( int hand, int annot );
	static private native boolean setAnnotCheckValue( int hand, int annot, boolean check );
	static private native boolean setAnnotRadio( int hand, int annot );
	static private native boolean getAnnotReset( int hand, int annot );
	static private native boolean setAnnotReset( int hand, int annot );
	static private native String getAnnotSubmitTarget( int hand, int annot );
	static private native String getAnnotSubmitPara( int hand, int annot );
	static private native int getAnnotFillColor( int hand, int annot );
	static private native boolean setAnnotFillColor( int hand, int annot, int color );
	static private native int getAnnotStrokeColor( int hand, int annot );
	static private native boolean setAnnotStrokeColor( int hand, int annot, int color );
	static private native float getAnnotStrokeWidth( int hand, int annot );
	static private native boolean setAnnotStrokeWidth( int hand, int annot, float width );
	static private native int getAnnotInkPath( int hand, int annot );
	static private native boolean setAnnotInkPath( int hand, int annot, int path );
	static private native int getAnnotPolygonPath( int hand, int annot );
	static private native boolean setAnnotPolygonPath( int hand, int annot, int path );
	static private native int getAnnotPolylinePath( int hand, int annot );
	static private native boolean setAnnotPolylinePath( int hand, int annot, int path );
	static private native boolean setAnnotIcon( int hand, int annot, int icon );
	static private native int getAnnotIcon( int hand, int annot );

	static private native boolean removeAnnot( int hand, int annot );
	static private native boolean addAnnotHWriting( int hand, int matrix, int hwriting, float orgx, float orgy );
	static private native boolean addAnnotInk( int hand, int matrix, int ink, float orgx, float orgy );
	static private native boolean addAnnotGlyph( int hand, int matrix, int path, int color, boolean winding );
	static private native boolean addAnnotLine( int hand, int matrix, float[] pt1, float[] pt2, int style1, int style2, float width, int color, int icolor );
	static private native boolean addAnnotRect( int hand, int matrix, float[] rect, float width, int color, int fill_color );
	static private native boolean addAnnotEllipse( int hand, int matrix, float[] rect, float width, int color, int fill_color );
	static private native boolean addAnnotEditbox( int hand, int matrix, float[] rect, float tsize, int color );
	static private native boolean addAnnotMarkup( int hand, int matrix, float[] rects, int color, int type );

	static private native boolean addAnnotInk2( int hand, int ink );
	static private native boolean addAnnotLine2( int hand, float[] pt1, float[] pt2, int style1, int style2, float width, int color, int icolor );
	static private native boolean addAnnotRect2( int hand, float[] rect, float width, int color, int fill_color );
	static private native boolean addAnnotEllipse2( int hand, float[] rect, float width, int color, int fill_color );
	static private native boolean addAnnotEditbox2( int hand, float[] rect, float tsize, int color );
	static private native boolean addAnnotMarkup2( int hand, int cindex1, int cindex2, int color, int type );
	static private native boolean addAnnotBitmap( int hand, Bitmap bitmap, boolean has_alpha, float[] rect );
	static private native boolean addAnnotAttachment( int hand, String path, int icon, float[] rect );
	static private native boolean addAnnotText( int hand, float[] pt );
	static private native boolean addAnnotGoto( int hand, float[] rect, int pageno, float top );
	static private native boolean addAnnotURI( int hand, float[] rect, String uri );
	static private native boolean addAnnotPolygon( int hand, int path, int color, int fill_color, float width );
	static private native boolean addAnnotPolyline( int hand, int path, int style1, int style2, int color, int fill_color, float width );

	static private native int addResFont( int hand, int font );
	static private native int addResImage( int hand, int image );
	static private native int addResGState( int hand, int gstate );
	static private native boolean addContent( int hand, int content );
	/**
	 * Close page object and free memory.
	 */
	public void Close()
	{
		close( hand );
		hand = 0;
	}
	/**
	 * prepare to render. it reset dib pixels to white value, and reset page status.
	 * @param dib DIB object to render. obtained by Global.dibGet().
	 */
	public void RenderPrePare( int dib )
	{
		renderPrepare( hand, dib );
	}
	/**
	 * render page to dib object. this function returned for cancelled or finished.<br/>before render, you need invoke RenderPrePare.
	 * @param dib DIB object to render. obtained by Global.dibGet().
	 * @param mat Matrix object define scale, rotate, translate operations.
	 * @return true or false.
	 */
	public boolean Render( int dib, Matrix mat )
	{
		boolean ret = render( hand, dib, mat.hand, Global.render_mode );
		return ret;
	}
	/**
	 * render page to Bitmap object directly. this function returned for cancelled or finished.<br/>before render, you need erase Bitmap object. 
	 * @param bitmap Bitmap object to render.
	 * @param mat Matrix object define scale, rotate, translate operations.
	 * @return true or false.
	 */
	public boolean RenderToBmp( Bitmap bitmap, Matrix mat )
	{
		return renderToBmp( hand, bitmap, mat.hand, Global.render_mode );
	}
	/**
	 * render to page in normal quality
	 * @param dib same as Render function
	 * @param mat same as Render function
	 */
	public void Render_Normal( int dib, Matrix mat )
	{
		render( hand, dib, mat.hand, 1 );
	}
	/**
	 * set page status to cancelled and cancel render function.
	 */
	public void RenderCancel()
	{
		renderCancel( hand );
	}
	/**
	 * render thumb image to Bitmap object.<br/>
	 * the image always scale and displayed in center of Bitmap.<br/>
	 * @param bmp Bitmap to render
	 * @return true if the page has thumb image, or false.
	 */
	public boolean RenderThumb(Bitmap bmp)
	{
		return renderThumb(hand, bmp);
	}
	/**
	 * check if page rendering is finished.
	 * @return true or false
	 */
	public boolean RenderIsFinished()
	{
		return renderIsFinished( hand );
	}
	/**
	 * get text objects to memory.<br/>
	 * a standard license is needed for this method
	 */
	public void ObjsStart()
	{
		objsStart( hand, Global.selRTOL );
	}
	/**
	 * get string from range. this can be invoked after ObjsStart
	 * @param from 0 based unicode index.
	 * @param to 0 based unicode index.
	 * @return string or null.
	 */
	public String ObjsGetString( int from, int to )
	{
		return objsGetString( hand, from, to );
	}
	/**
	 * get index aligned by word. this can be invoked after ObjsStart
	 * @param from 0 based unicode index.
	 * @param dir if dir < 0,  get start index of the word. otherwise get last index of the word.
	 * @return new index value.
	 */
	public int ObjsAlignWord( int from, int dir )
	{
		return objsAlignWord( hand, from, dir );
	}
	/**
	 * get char's box in PDF coordinate system, this can be invoked after ObjsStart
	 * @param index 0 based unicode index.
	 * @param vals return 4 elements for PDF rectangle.
	 */
	public void ObjsGetCharRect( int index, float[]vals )
	{
		objsGetCharRect( hand, index, vals );
	}
	/**
	 * get char's font name. this can be invoked after ObjsStart
	 * @param index 0 based unicode index.
	 * @return font name, may be null.
	 */
	public String ObjsGetCharFontName( int index )
	{
		return objsGetCharFontName( hand, index );
	}
	/**
	 * get chars count in this page. this can be invoked after ObjsStart<br/>
	 * a standard license is needed for this method
	 * @return count or 0 if ObjsStart not invoked.
	 */
	public int ObjsGetCharCount()
	{
		return objsGetCharCount( hand );
	}
	/**
	 * get char index nearest to point
	 * @param pt point as [x,y] in PDF coordinate.
	 * @return char index or -1 failed.
	 */
	public int ObjsGetCharIndex( float[] pt )
	{
		return objsGetCharIndex( hand, pt );
	}
	/**
	 * create a find session. this can be invoked after ObjsStart
	 * @param str key string to find.
	 * @param match_case match case?
	 * @param whole_word match whole word?
	 * @return handle of find session, or 0 if no found.
	 */
	public Finder FindOpen( String str, boolean match_case, boolean whole_word )
	{
		int ret = findOpen( hand, str, match_case, whole_word );
		if( ret == 0 ) return null;
		Finder find = new Finder();
		find.hand = ret;
		return find;
	}

	/**
	 * get rotate degree for page, example: 0 or 90
	 * @param page
	 * @return rotate degree for page
	 */
	public int GetRotate()
	{
		return getRotate( hand );
	}
	/**
	 * get annotations count in this page.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @return count
	 */
	public int GetAnnotCount()
	{
		return getAnnotCount( hand );
	}
	/**
	 * get annotations by index.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @param index 0 based index value. range:[0, GetAnnotCount()-1]
	 * @return handle of annotation, valid until Close invoked.
	 */
	public Annotation GetAnnot( int index )
	{
		int ret = getAnnot( hand, index );
		if( ret == 0 ) return null;
		Annotation annot = new Annotation();
		annot.hand = ret;
		annot.page = this;
		return annot;
	}
	/**
	 * get annotations by PDF point.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @param x x value in PDF coordinate system.
	 * @param y y value in PDF coordinate system.
	 * @return handle of annotation, valid until Close invoked.
	 */
	public Annotation GetAnnotFromPoint( float x, float y )
	{
		int ret = getAnnotFromPoint( hand, x, y );
		if( ret == 0 ) return null;
		Annotation annot = new Annotation();
		annot.hand = ret;
		annot.page = this;
		return annot;
	}
	/**
	 * add goto-page link to page.<br/>
	 * you should re-render page to display modified data.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @param rect link area rect [left, top, right, bottom] in PDF coordinate.
	 * @param pageno 0 based pageno to goto.
	 * @param top y coordinate in PDF coordinate, page.height is top of page. and 0 is bottom of page.
	 * @return true or false.
	 */
	public boolean AddAnnotGoto( float[] rect, int pageno, float top )
	{
		return addAnnotGoto( hand, rect, pageno, top );
	}
	/**
	 * add URL link to page.<br/>
	 * you should re-render page to display modified data.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @param rect link area rect [left, top, right, bottom] in PDF coordinate.
	 * @param uri url address, example: "http://www.radaee.com/en"
	 * @return true or false
	 */
	public boolean AddAnnotURI( float[] rect, String uri )
	{
		return addAnnotURI( hand, rect, uri );
	}
	/**
	 * add hand-writing to page.<br/>
	 * you should re-render page to display modified data.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @param mat Matrix for Render function.
	 * @param ink Ink object
	 * @param orgx origin x coordinate in page. in DIB coordinate system
	 * @param orgy origin y coordinate in page. in DIB coordinate system
	 * @return true or false
	 */
	public boolean AddAnnotInk( Matrix mat, Ink ink, float orgx, float orgy )
	{
		return addAnnotInk( hand, mat.hand, ink.hand, orgx, orgy );
	}
	/**
	 * add hand-writing to page.<br/>
	 * you should re-render page to display modified data.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @param mat Matrix for Render function.
	 * @param hwriting hand writing object
	 * @param orgx origin x coordinate in page. in DIB coordinate system
	 * @param orgy origin y coordinate in page. in DIB coordinate system
	 * @return true or false
	 */
	public boolean AddAnnotHWriting( Matrix mat, HWriting hwriting, float orgx, float orgy )
	{
		return addAnnotHWriting( hand, mat.hand,  hwriting.hand, orgx, orgy );
	}
	/**
	 * add a user-defined glyph to page.<br/>
	 * you should re-render page to display modified data.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @param mat Matrix for Render function.
	 * @param path Path object.
	 * @param color text color, formated as 0xAARRGGBB.
	 * @param winding if true, using winding fill rule, otherwise using odd-even fill rule.
	 * @return true or false.
	 */
	public boolean AddAnnotGlyph( Matrix mat, Path path, int color, boolean winding )
	{
		return addAnnotGlyph( hand, mat.hand, path.m_hand, color, winding );
	}
	/**
	 * add rectangle to page.<br/>
	 * you should re-render page to display modified data.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @param mat Matrix for Render function.
	 * @param rect 4 elements for left, top, right, bottom in DIB coordinate system
	 * @param width line width
	 * @param color rectangle color, formated as 0xAARRGGBB
	 * @param fill_color fill color in rectangle, formated as 0xAARRGGBB, if alpha channel is 0, means no fill operation, otherwise alpha channel are ignored.
	 * @return true or false
	 */
	public boolean AddAnnotRect( Matrix mat, float[] rect, float width, int color, int fill_color )
	{
		return addAnnotRect( hand, mat.hand, rect, width, color, fill_color );
	}
	/**
	 * add line to page.<br/>
	 * you should re-render page to display modified data.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @param mat Matrix for Render function.
	 * @param pt1 start point, 2 elements for x,y
	 * @param pt2 end point, 2 elements for x,y
	 * @param style1 style for start point:<br/>
	 * 0: None<br/>
	 * 1: Arrow<br/>
	 * 2: Closed Arrow<br/>
	 * 3: Square<br/>
	 * 4: Circle<br/>
	 * 5: Butt<br/>
	 * 6: Diamond<br/>
	 * 7: Reverted Arrow<br/>
	 * 8: Reverted Closed Arrow<br/>
	 * 9: Slash
	 * @param style2 style for end point, values are same as style1.
	 * @param width line width in DIB coordinate
	 * @param color line color. same as addAnnotRect.
	 * @param fill_color fill color. same as addAnnotRect.
	 * @return true or false.
	 */
	public boolean AddAnnotLine( Matrix mat, float[] pt1, float[] pt2, int style1, int style2, float width, int color, int fill_color )
	{
		return addAnnotLine( hand, mat.hand, pt1, pt2, style1, style2, width, color, fill_color );
	}
	/**
	 * add ellipse to page.<br/>
	 * you should re-render page to display modified data.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @param mat Matrix for Render function.
	 * @param rect 4 elements for left, top, right, bottom in DIB coordinate system
	 * @param width line width
	 * @param color ellipse color, formated as 0xAARRGGBB
	 * @param fill_color fill color in ellipse, formated as 0xAARRGGBB, if alpha channel is 0, means no fill operation, otherwise alpha channel are ignored.
	 * @return true or false
	 */
	public boolean AddAnnotEllipse( Matrix mat, float[] rect, float width, int color, int fill_color )
	{
		return addAnnotEllipse( hand, mat.hand, rect, width, color, fill_color );
	}
	/**
	 * add a text-markup annotation to page.<br/>
	 * you should re-render page to display modified data.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @param mat Matrix for Render function.
	 * @param rects 4 * n rectangles, each 4 elements: left, top, right, bottom in DIB coordinate system. n is decided by length of array.
	 * @param type 0: Highlight, 1: Underline, 2: StrikeOut, 3: Highlight without round corner.
	 * @return true or false.
	 */
	public boolean AddAnnotMarkup( Matrix mat, float[] rects, int type )
	{
		int color = 0xFFFFFF00;//yellow
		if( type == 1 ) color = 0xFF0000C0;//black blue
		if( type == 2 ) color = 0xFFC00000;//black red
		if( type == 2 ) color = 0xFF00C000;//black green
		return addAnnotMarkup( hand, mat.hand, rects, color, type );
	}
	/**
	 * add hand-writing to page.<br/>
	 * you should re-render page to display modified data.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @param ink Ink object in PDF coordinate.
	 * @return true or false.
	 */
	public boolean AddAnnotInk( Ink ink )
	{
		return addAnnotInk2( hand, ink.hand );
	}
	/**
	 * add line to page.<br/>
	 * you should re-render page to display modified data.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @param pt1 start point in PDF coordinate, 2 elements for x,y
	 * @param pt2 end point in PDF coordinate, 2 elements for x,y
	 * @param style1 style for start point:<br/>
	 * 0: None<br/>
	 * 1: Arrow<br/>
	 * 2: Closed Arrow<br/>
	 * 3: Square<br/>
	 * 4: Circle<br/>
	 * 5: Butt<br/>
	 * 6: Diamond<br/>
	 * 7: Reverted Arrow<br/>
	 * 8: Reverted Closed Arrow<br/>
	 * 9: Slash
	 * @param style2 style for end point, values are same as style1.
	 * @param width line width in DIB coordinate
	 * @param color line color. same as addAnnotRect.
	 * @param fill_color fill color. same as addAnnotRect.
	 * @return true or false.
	 */
	public boolean AddAnnotLine( float[] pt1, float[] pt2, int style1, int style2, float width, int color, int icolor )
	{
		return addAnnotLine2( hand, pt1, pt2, style1, style2, width, color, icolor );
	}
	/**
	 * add rectangle to page.<br/>
	 * you should re-render page to display modified data.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @param rect 4 elements for left, top, right, bottom in PDF coordinate system
	 * @param width line width in PDF coordinate.
	 * @param color rectangle color, formated as 0xAARRGGBB
	 * @param fill_color fill color in rectangle, formated as 0xAARRGGBB, if alpha channel is 0, means no fill operation, otherwise alpha channel are ignored.
	 * @return true or false
	 */
	public boolean AddAnnotRect( float[] rect, float width, int color, int fill_color )
	{
		return addAnnotRect2( hand, rect, width, color, fill_color );
	}
	/**
	 * add ellipse to page.<br/>
	 * you should re-render page to display modified data.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @param rect 4 elements for left, top, right, bottom in PDF coordinate system
	 * @param width line width in PDF coordinate
	 * @param color ellipse color, formated as 0xAARRGGBB
	 * @param fill_color fill color in ellipse, formated as 0xAARRGGBB, if alpha channel is 0, means no fill operation, otherwise alpha channel are ignored.
	 * @return true or false
	 */
	public boolean AddAnnotEllipse( float[] rect, float width, int color, int fill_color )
	{
		return addAnnotEllipse2( hand, rect, width, color, fill_color );
	}
	/**
	 * add an edit-box on page. the edit-box has no border and background.
	 * the font of edit box is set by Global.setTextFont in Global.Init().
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in premium version.
	 * @param rect 4 elements: left, top, right, bottom in PDF coordinate system.
	 * @param tsize text size in DIB coordinate system.
	 * @param color text color, formated as 0xAARRGGBB.
	 * @return
	 */
	public boolean AddAnnotEditbox( float[] rect, float tsize, int color )
	{
		return addAnnotEditbox2( hand, rect, tsize, color );
	}
	/**
	 * add polygon to page.<br/>
	 * you should re-render page to display modified data.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @param path must be a closed contour.
	 * @param color stroke color formated as 0xAARRGGBB.
	 * @param fill_color fill color, formated as 0xAARRGGBB. if AA == 0, no fill operations, otherwise alpha value is same to stroke color. 
	 * @param width stroke width in PDF coordinate
	 * @return true or false.
	 */
	public boolean AddAnnotPolygon( Path path, int color, int fill_color, float width )
	{
		return addAnnotPolygon( hand, path.m_hand, color, fill_color, width );
	}
	/**
	 * add polyline to page.<br/>
	 * you should re-render page to display modified data.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @param path must be a set of unclosed lines. do not container any move-to operation except the first point in the path.
	 * @param style1 style for start point:<br/>
	 * 0: None<br/>
	 * 1: Arrow<br/>
	 * 2: Closed Arrow<br/>
	 * 3: Square<br/>
	 * 4: Circle<br/>
	 * 5: Butt<br/>
	 * 6: Diamond<br/>
	 * 7: Reverted Arrow<br/>
	 * 8: Reverted Closed Arrow<br/>
	 * 9: Slash
	 * @param style2 style for end point, values are same as style1.
	 * @param color stroke color formated as 0xAARRGGBB.
	 * @param fill_color fill color, formated as 0xAARRGGBB. if AA == 0, no fill operations, otherwise alpha value is same to stroke color. 
	 * @param width stroke width in PDF coordinate
	 * @return true or false.
	 */
	public boolean AddAnnotPolyline( Path path, int style1, int style2, int color, int fill_color, float width )
	{
		return addAnnotPolyline( hand, path.m_hand, style1, style2, color, fill_color, width );
	}
	/**
	 * add a text-markup annotation to page.<br/>
	 * you should re-render page to display modified data.<br/>
	 * this can be only invoked after ObjsStart.<br/>
	 * this method valid in professional or premium version
	 * @param cindex1 first char index
	 * @param cindex2 second char index
	 * @param type type as following:<br/>
	 * 0: Highlight<br/>
	 * 1: Underline<br/>
	 * 2: StrikeOut<br/>
	 * 3: Highlight without round corner<br/>
	 * 4: Squiggly underline.
	 * @return true or false.
	 */
	public boolean AddAnnotMarkup( int cindex1, int cindex2, int type )
	{
		int color = 0xFFFFFF00;//yellow
		if( type == 1 ) color = 0xFF0000C0;//black blue
		if( type == 2 ) color = 0xFFC00000;//black red
		if( type == 4 ) color = 0xFF00C000;//black green
		return addAnnotMarkup2( hand, cindex1, cindex2, color, type );
	}
	/**
	 * add a bitmap object as an annotation to page.<br/>
	 * you should re-render page to display modified data.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @param bitmap Bitmap object to add, which should be formated in ARGB_8888
	 * @param has_alpha is need to save alpha channel information?
	 * @param rect 4 elements: left, top, right, bottom in PDF coordinate system.
	 * @return true or false.
	 */
	public boolean AddAnnotBitmap( Bitmap bitmap, boolean has_alpha, float[] rect )
	{
		return addAnnotBitmap( hand, bitmap, has_alpha, rect );
	}
	/**
	 * add a file as an attachment to page.<br/>
	 * you should re-render page to display modified data.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @param path absolute path name to the file.
	 * @param icon icon display to the page. values as:<br/>
	 * 0: PushPin<br/>
	 * 1: Graph<br/>
	 * 2: Paperclip<br/>
	 * 3: Tag<br/>
	 * @param rect 4 elements: left, top, right, bottom in PDF coordinate system.
	 * @return true or false.
	 */
	public boolean AddAnnotAttachment(String path, int icon, float[] rect)
	{
		return addAnnotAttachment( hand, path, icon, rect );
	}
	/**
	 * add a sticky text annotation to page.<br/>
	 * you should re-render page to display modified data.<br/>
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in professional or premium version
	 * @param pt 2 elements: x, y in PDF coordinate system.
	 * @return true or false.
	 */
	public boolean AddAnnotText( float[] pt )
	{
		return addAnnotText( hand, pt );
	}
	/**
	 * add an edit-box on page. the edit-box has no border and background.
	 * the font of edit box is set by Global.setTextFont in Global.Init().
	 * this can be invoked after ObjsStart or Render or RenderToBmp.<br/>
	 * this method valid in premium version.
	 * @param matrix Matrix object that passed to Render or RenderToBmp function.
	 * @param rect 4 elements: left, top, right, bottom in DIB coordinate system.
	 * @param tsize text size in DIB coordinate system.
	 * @param color text color, formated as 0xAARRGGBB.
	 * @return
	 */
	public boolean AddAnnotEditbox( Matrix mat, float[] rect, float tsize, int color )
	{
		return addAnnotEditbox( hand, mat.hand, rect, tsize, color );
	}
	/**
	 * Start Reflow.<br/>
	 * this method valid in professional or premium version
	 * @param width input width, function calculate height.
	 * @param scale scale base to 72 DPI, 2.0 means 144 DPI. the reflowed text will displayed in scale
	 * @return the height that reflow needed.
	 */
	public float ReflowStart( float width, float scale )
	{
		return reflowStart( hand, width, scale );
	}
	/**
	 * Reflow to dib.<br/>
	 * this method valid in professional or premium version
	 * @param dib dib to render
	 * @param orgx origin x coordinate
	 * @param orgy origin y coordinate
	 * @return true or false
	 */
	public boolean Reflow( int dib, float orgx, float orgy )
	{
		return reflow( hand, dib, orgx, orgy );
	}
	/**
	 * Reflow to Bitmap object.<br/>
	 * this method valid in professional or premium version
	 * @param bitmap bitmap to reflow
	 * @param orgx origin x coordinate
	 * @param orgy origin y coordinate
	 * @return true or false
	 */
	public boolean ReflowToBmp( Bitmap bitmap, float orgx, float orgy )
	{
		return reflowToBmp( hand, bitmap, orgx, orgy );
	}
	/**
	 * get reflow paragraph count.<br/>
	 * this method valid in professional or premium version
	 * @return count
	 */
	public int ReflowGetParaCount()
	{
		return reflowGetParaCount( hand );
	}
	/**
	 * get one paragraph's char count.<br/>
	 * this method valid in professional or premium version
	 * @param iparagraph paragraph index range[0, ReflowGetParaCount()-1]
	 * @return char count
	 */
	public int ReflowGetCharCount( int iparagraph )
	{
		if( iparagraph < 0 || iparagraph >= reflowGetParaCount( hand ) ) return 0;
		return reflowGetCharCount( hand, iparagraph );
	}
	/**
	 * get char's font width.<br/>
	 * this method valid in professional or premium version
	 * @param iparagraph paragraph index range[0, ReflowGetParaCount()-1]
	 * @param ichar char index range[0, ReflowGetCharCount()]
	 * @return font width for this char
	 */
	public float ReflowGetCharWidth( int iparagraph, int ichar )
	{
		if( ichar < 0 || ichar >= ReflowGetCharCount(iparagraph) ) return 0;
		return reflowGetCharWidth( hand, iparagraph, ichar );
	}
	/**
	 * get char's font height.<br/>
	 * this method valid in professional or premium version
	 * @param iparagraph paragraph index range[0, ReflowGetParaCount()-1]
	 * @param ichar char index range[0, ReflowGetCharCount()]
	 * @return font height for this char
	 */
	public float ReflowGetCharHeight( int iparagraph, int ichar )
	{
		if( ichar < 0 || ichar >= ReflowGetCharCount(iparagraph) ) return 0;
		return reflowGetCharHeight( hand, iparagraph, ichar );
	}
	/**
	 * get char's fill color for display.<br/>
	 * this method valid in professional or premium version
	 * @param iparagraph paragraph index range[0, ReflowGetParaCount()-1]
	 * @param ichar char index range[0, ReflowGetCharCount()]
	 * @return color value formatted 0xAARRGGBB, AA: alpha value, RR:red, GG:green, BB:blue
	 */
	public int ReflowGetCharColor( int iparagraph, int ichar )
	{
		if( ichar < 0 || ichar >= ReflowGetCharCount(iparagraph) ) return 0;
		return reflowGetCharColor( hand, iparagraph, ichar );
	}
	/**
	 * get char's unicode value.<br/>
	 * this method valid in professional or premium version
	 * @param iparagraph paragraph index range[0, ReflowGetParaCount()-1]
	 * @param ichar char index range[0, ReflowGetCharCount()]
	 * @return unicode
	 */
	public int ReflowGetCharUnicode( int iparagraph, int ichar )
	{
		if( ichar < 0 || ichar >= ReflowGetCharCount(iparagraph) ) return 0;
		return reflowGetCharUnicode( hand, iparagraph, ichar );
	}
	/**
	 * get char's font name.<br/>
	 * this method valid in professional or premium version
	 * @param iparagraph paragraph index range[0, ReflowGetParaCount()-1]
	 * @param ichar char index range[0, ReflowGetCharCount()]
	 * @return name string
	 */
	public String ReflowGetCharFont( int iparagraph, int ichar )
	{
		if( ichar < 0 || ichar >= ReflowGetCharCount(iparagraph) ) return null;
		return reflowGetCharFont( hand, iparagraph, ichar );
	}
	/**
	 * get char's bound box.<br/>
	 * this method valid in professional or premium version
	 * @param iparagraph paragraph index range[0, ReflowGetParaCount()-1]
	 * @param ichar char index range[0, ReflowGetCharCount()]
	 * @param rect output: 4 element as [left, top, right, bottom].
	 */
	public void ReflowGetCharRect( int iparagraph, int ichar, float rect[] )
	{
		if( ichar < 0 || ichar >= ReflowGetCharCount(iparagraph) ) return;
		reflowGetCharRect( hand, iparagraph, ichar, rect );
	}
	/**
	 * get text from range.<br/>
	 * this method valid in professional or premium version
	 * @param iparagraph1 first position
	 * @param ichar1 first position
	 * @param iparagraph2 second position
	 * @param ichar2 second position
	 * @return string value or null
	 */
	public String ReflowGetText( int iparagraph1, int ichar1, int iparagraph2, int ichar2 )
	{
		if( ichar1 < 0 || ichar1 >= ReflowGetCharCount(iparagraph1) ) return null;
		if( ichar2 < 0 || ichar1 >= ReflowGetCharCount(iparagraph2) ) return null;
		return reflowGetText( hand, iparagraph1, ichar1, iparagraph2, ichar2 );
	}
	/**
	 * add a font as resource of this page.<br/>
	 * a premium license is needed for this method.
	 * @param font font object created by Document.NewFontCID()
	 * @return ResFont or null.
	 */
	public ResFont AddResFont( DocFont font )
	{
		if( font == null ) return null;
		int ret = addResFont( hand, font.hand );
		if( ret != 0 )
		{
			ResFont fnt = new ResFont();
			fnt.hand = ret;
			return fnt; 
		}
		else return null;
	}
	/**
	 * add an image as resource of this page.<br/>
	 * a premium license is needed for this method.
	 * @param image image object created by Document.NewImage() or Document.NewImageJPEG()
	 * @return null means failed.
	 */
	public ResImage AddResImage( DocImage image )
	{
		if( image == null ) return null;
		int ret = addResImage( hand, image.hand );
		if( ret != 0 )
		{
			ResImage img = new ResImage();
			img.hand = ret;
			return img; 
		}
		else return null;
	}
	/**
	 * add GraphicState as resource of this page.<br/>
	 * a premium license is needed for this method.
	 * @param gstate ExtGraphicState created by Document.NewGState();
	 * @return null means failed.
	 */
	public ResGState AddResGState( DocGState gstate )
	{
		if( gstate == null ) return null;
		int ret = addResGState( hand, gstate.hand );
		if( ret != 0 )
		{
			ResGState gs = new ResGState();
			gs.hand = ret;
			return gs; 
		}
		else return null;
	}
	/**
	 * add content stream to this page.<br/>
	 * a premium license is needed for this method.
	 * @param content PageContent object called PageContent.create().
	 * @return true or false.
	 */
	public boolean AddContent( PageContent content )
	{
		if( content == null ) return false;
		return addContent( hand, content.hand );
	}
}
