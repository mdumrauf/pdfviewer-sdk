package com.radaee.reader;

import com.radaee.pdf.Document;
import com.radaee.pdf.Global;
import com.radaee.pdf.Ink;
import com.radaee.pdf.Matrix;
import com.radaee.pdf.Page;
import com.radaee.pdf.Page.Annotation;
import com.radaee.util.ComboList;
import com.radaee.view.PDFVPage;
import com.radaee.view.PDFView;
import com.radaee.view.PDFViewCurl;
import com.radaee.view.PDFViewDual;
import com.radaee.view.PDFViewHorz;
import com.radaee.view.PDFViewVert;
import com.radaee.view.PDFView.PDFPos;
import com.radaee.view.PDFView.PDFViewListener;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.RectF;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

public class PDFReader extends View implements PDFViewListener, OnItemClickListener, PopupWindow.OnDismissListener
{
	private PDFView m_view = null;
	private Document m_doc = null;
	private PopupWindow m_pEdit = null;
	private PopupWindow m_pCombo = null;
	static final int STA_NORMAL = 0;
	static final int STA_SELECT = 1;
	static final int STA_INK = 2;
	static final int STA_RECT = 3;
	static final int STA_ELLIPSE = 4;
	static final int STA_NOTE = 5;
	static final int STA_LINE = 6;
	static final int STA_ANNOT = 100;
	private int m_status = STA_NORMAL;
	private Annotation m_annot = null;
	private PDFPos m_annot_pos = null;
	private PDFVPage m_annot_page = null;
	private float m_annot_rect[];
	private float m_annot_rect0[];
	private float m_annot_x0;
	private float m_annot_y0;
	private Ink m_ink;
	private PDFVPage m_ink_page;
	private float m_rects[];
	private PDFReaderListener m_listener;
	private int m_pageno = 0;
    private int m_edit_type = 0;
    private int m_sel_index = -1;
    private boolean m_rtol = false;
    class PDFVPageSet
    {
    	PDFVPageSet( int max_len )
    	{
    		pages = new PDFVPage[max_len];
    		pages_cnt = 0;
    	}
    	void Insert( PDFVPage vpage )
    	{
    		int cur = 0;
    		for( cur = 0; cur < pages_cnt; cur++ )
    		{
    			if( pages[cur] == vpage ) return;
    		}
    		pages[cur] = vpage;
    		pages_cnt++;
    	}
    	PDFVPage pages[];
    	int pages_cnt;
    }
	public interface PDFReaderListener
	{
		public void OnPageModified(int pageno);
		public void OnPageChanged(int pageno);
		public void OnAnnotClicked(PDFVPage vpage, Annotation annot);
		public void OnSelectEnd(String text);
		public void OnOpenURI(String uri);
		public void OnOpenMovie(String path);
		public void OnOpenSound(int[] paras, String path);
		public void OnOpenAttachment(String path);
		public void OnOpen3D(String path);
	}
	public PDFReader(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.setBackgroundColor(0xFFCCCCCC);
		m_pEdit = new PopupWindow(LayoutInflater.from(context).inflate(R.layout.pop_edit, null) );
		m_pCombo = new PopupWindow(LayoutInflater.from(context).inflate(R.layout.pop_combo, null));
		m_pEdit.setOnDismissListener(this);
		m_pCombo.setOnDismissListener(this);
		m_pEdit.setFocusable(true);
		m_pEdit.setTouchable(true);
		BitmapDrawable bitmap = new BitmapDrawable();//add back
		m_pEdit.setBackgroundDrawable(bitmap);
		m_pCombo.setFocusable(true);
		m_pCombo.setTouchable(true);
		m_pCombo.setBackgroundDrawable(bitmap);
	}

    public void PDFOpen(Document doc, boolean rtol, PDFReaderListener listener) {
        PDFOpen(doc, rtol, listener, 0xFFCCCCCC);
    }

    public void PDFOpen(Document doc, boolean rtol, PDFReaderListener listener, int back_color)
	{
        int backgroundColor = back_color;
        PDFClose();
        m_listener = listener;
        m_doc = doc;
        m_rtol = rtol;
        switch( Global.def_view )
        {
        case 1:
        {
        	PDFViewHorz view = new PDFViewHorz(this.getContext());
        	view.vSetDirection(m_rtol);
            m_view = view;
        }
            break;
        case 2:
            m_view = new PDFViewCurl(this.getContext());
            backgroundColor = 0xFFFFFFFF;
            break;
        case 3:
        {
        	PDFViewDual view = new PDFViewDual(this.getContext());
        	boolean paras[] = new boolean[m_doc.GetPageCount()];
        	int cur = 0;
        	while( cur < paras.length )
        	{
        		paras[cur] = false;
        		cur++;
        	}
        	view.vSetLayoutPara(null, paras, m_rtol);
            m_view = view;
        }
            break;
        case 4:
        case 6:
        {
        	PDFViewDual view = new PDFViewDual(this.getContext());
        	view.vSetLayoutPara(null, null, m_rtol);
            m_view = view;
        }
            break;
        default:
            m_view = new PDFViewVert(this.getContext());
            break;
        }
        m_view.vOpen(m_doc, 4, backgroundColor, this);
        m_view.vResize(getWidth(), getHeight());
	}
	public boolean PDFSave()
	{
		return m_doc.Save();
	}
	public void PDFClose()
	{
		PDFCancel();
		if( m_ink != null )
		{
			m_ink.Destroy();
			m_ink = null;
		}
		if( m_view != null )
		{
			m_view.vClose();
			m_view = null;
		}
		if( m_doc != null )
			m_doc = null;
		m_status = STA_NORMAL;
		m_annot = null;
		m_annot_pos = null;
		m_annot_page = null;
		m_annot_rect = null;
		m_ink_page = null;
		m_rects = null;
		m_pageno = 0;
	}
	public boolean PDFCanSave()
	{
		if( m_doc == null ) return false;
		return m_doc.CanSave();
	}
	public void OnPDFPosChanged(PDFPos pos)
	{
		if( m_listener != null && pos != null )
		{
			//PDFVPage vpage = m_view.vGetPage(pos.pageno);
			//int left = vpage.GetVX(m_view.vGetX());
			//int top = vpage.GetVY(m_view.vGetY());
			//int right = left + vpage.GetWidth();
			//int bottom = top + vpage.GetHeight();
			int pageno = pos.pageno;
			if( pageno != m_pageno )
			{
				m_pageno = pageno;
				m_listener.OnPageChanged(pageno);
			}
		}
	}
	public boolean OnPDFDoubleTapped(float x, float y)
	{
		if( m_status != STA_NORMAL ) return false;
		m_view.vSetScale(m_view.vGetScale() + Global.zoomStep, x, y);
		return true;
	}
	public boolean OnPDFSingleTapped(float x, float y)
	{
		if( m_status == STA_NORMAL || m_status == STA_ANNOT )
		{
			m_annot_pos = m_view.vGetPos((int)x, (int)y);
			m_annot_page = m_view.vGetPage(m_annot_pos.pageno);
			Page page = m_annot_page.GetPage();
			if( page == null ) m_annot = null;
			else m_annot = page.GetAnnotFromPoint(m_annot_pos.x, m_annot_pos.y);
			if( m_annot == null )
			{
				m_annot_page = null;
				m_annot_pos = null;
				m_annot_rect = null;
				m_view.vSetLock(0);
				if( m_listener != null && m_status == STA_ANNOT )
					m_listener.OnAnnotClicked(m_annot_page, null);
				m_status = STA_NORMAL;
			}
			else
			{
				m_annot_rect = m_annot.GetRect();
				int px = m_annot_page.GetVX(m_view.vGetX());
				int py = m_annot_page.GetVY(m_view.vGetY());
				float tmp = m_annot_rect[1];
				m_annot_rect[0] = m_annot_page.ToDIBX(m_annot_rect[0]) + px;
				m_annot_rect[1] = m_annot_page.ToDIBY(m_annot_rect[3]) + py;
				m_annot_rect[2] = m_annot_page.ToDIBX(m_annot_rect[2]) + px;
				m_annot_rect[3] = m_annot_page.ToDIBY(tmp) + py;
				m_view.vSetLock(3);
				m_status = STA_ANNOT;
				if( m_doc.CanSave() && m_annot.GetEditType() > 0 )
				{
					int[] location = new int[2];  
					getLocationOnScreen(location);
					m_pEdit.setWidth((int)(m_annot_rect[2] - m_annot_rect[0]));
					m_pEdit.setHeight((int)(m_annot_rect[3] - m_annot_rect[1]));
					EditText edit = (EditText)m_pEdit.getContentView().findViewById(R.id.annot_text);
					edit.setBackgroundColor(0xFFFFFFC0);
					float fsize = m_annot.GetEditTextSize() * m_annot_page.GetScale();
					edit.setTextSize(TypedValue.COMPLEX_UNIT_PX, fsize);
					edit.setPadding(2, 2, 2, 2);
					switch( m_annot.GetEditType() )
					{
					case 1:
						edit.setSingleLine();
						edit.setInputType(InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_NORMAL);
						break;
					case 2:
						edit.setSingleLine();
						edit.setInputType(InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PASSWORD);
						break;
					case 3:
						edit.setSingleLine(false);
						edit.setInputType(InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_NORMAL);
						break;
					}
					int maxlen = m_annot.GetEditMaxlen();
					if( maxlen > 0 )
						edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlen)});
					else
						edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1020)});
					edit.setText(m_annot.GetEditText());
					m_edit_type = 1;
					m_pEdit.showAtLocation(this, Gravity.NO_GRAVITY, (int)m_annot_rect[0] + location[0], (int)m_annot_rect[1] + location[1]);
				}
				if( m_doc.CanSave() && m_annot.GetComboItemCount() >= 0 )
				{
					int[] location = new int[2];  
					getLocationOnScreen(location);
					String opts[] = new String[m_annot.GetComboItemCount()];
					int cur = 0;
					while( cur < opts.length )
					{
						opts[cur] = m_annot.GetComboItem(cur);
						cur++;
					}
					m_pCombo.setWidth((int)(m_annot_rect[2] - m_annot_rect[0]));
					if( (m_annot_rect[3] - m_annot_rect[1] - 4) * opts.length > 250 )
						m_pCombo.setHeight(250);
					else
						m_pCombo.setHeight((int)(m_annot_rect[3] - m_annot_rect[1] - 4) * opts.length);
					ComboList combo = (ComboList)m_pCombo.getContentView().findViewById(R.id.annot_combo);
					combo.set_opts(opts);
					combo.setOnItemClickListener(this);
					m_edit_type = 2;
					m_sel_index = -1;
					m_pCombo.showAtLocation(this, Gravity.NO_GRAVITY, (int)m_annot_rect[0] + location[0], (int)(m_annot_rect[3] + location[1]));
				}
				if( m_listener != null )
					m_listener.OnAnnotClicked(m_annot_page, m_annot);
				invalidate();
			}
			return true;
		}
		return false;
	}
	public void OnPDFLongPressed(float x, float y)
	{
	}
	public void OnPDFShowPressed(float x, float y)
	{
	}
	public void OnPDFSelectEnd()
	{
		if( m_listener != null )
			m_listener.OnSelectEnd(m_view.vGetSel());
	}
	public void OnPDFFound(boolean found)
	{
		if( !found )
			Toast.makeText(getContext(), "no more found", Toast.LENGTH_SHORT).show();
	}
	public void PDFSetInk(int code)
	{
		if( code == 0 )//start
		{
			m_status = STA_INK;
			m_ink = new Ink(Global.inkWidth);
			m_view.vSetLock(3);
		}
		else if( code == 1 )//end
		{
			m_status = STA_NORMAL;
			if( m_ink_page != null )
			{
				Page page = m_ink_page.GetPage();
				if( page != null )
				{
					Matrix mat = m_ink_page.CreateInvertMatrix(m_view.vGetX(), m_view.vGetY());
					mat.TransformInk(m_ink);
					page.AddAnnotInk(m_ink);
					mat.Destroy();
					m_view.vRender(m_ink_page);
					if( m_listener != null )
						m_listener.OnPageModified(m_ink_page.GetPageNo());
				}
			}
			m_ink.Destroy();
			m_ink = null;
			m_ink_page = null;
			invalidate();
			m_view.vSetLock(0);
		}
		else//cancel
		{
			m_status = STA_NORMAL;
			m_ink.Destroy();
			m_ink = null;
			m_ink_page = null;
			invalidate();
			m_view.vSetLock(0);
		}
	}
	public void PDFSetRect(int code)
	{
		if( code == 0 )//start
		{
			m_status = STA_RECT;
			m_view.vSetLock(3);
		}
		else if( code == 1 )//end
		{
			if( m_rects != null )
			{
				int len = m_rects.length;
				int cur;
				PDFVPageSet pset = new PDFVPageSet(len);
				for( cur = 0; cur < len; cur += 4 )
				{
					PDFPos pos = m_view.vGetPos((int)m_rects[cur], (int)m_rects[cur + 1]);
					PDFVPage vpage = m_view.vGetPage(pos.pageno);
					Page page = vpage.GetPage();
					if( page != null )
					{
						Matrix mat = vpage.CreateInvertMatrix(m_view.vGetX(), m_view.vGetY());
						float rect[] = new float[4];
						if( m_rects[cur] > m_rects[cur + 2] ) 
						{
							rect[0] = m_rects[cur + 2];
							rect[2] = m_rects[cur];
						}
						else
						{
							rect[0] = m_rects[cur];
							rect[2] = m_rects[cur + 2];
						}
						if( m_rects[cur + 1] > m_rects[cur + 3] ) 
						{
							rect[1] = m_rects[cur + 3];
							rect[3] = m_rects[cur + 1];
						}
						else
						{
							rect[1] = m_rects[cur + 1];
							rect[3] = m_rects[cur + 3];
						}
						mat.TransformRect(rect);
						page.AddAnnotRect(rect, vpage.ToPDFSize(3), 0x80FF0000, 0x800000FF);
						mat.Destroy();
						pset.Insert(vpage);
					}
				}
				for( cur = 0; cur < pset.pages_cnt; cur++ )
				{
					PDFVPage vpage = pset.pages[cur];
					m_view.vRender(vpage);
					if( m_listener != null )
						m_listener.OnPageModified(vpage.GetPageNo());
				}
			}
			m_status = STA_NORMAL;
			m_rects = null;
			invalidate();
			m_view.vSetLock(0);
		}
		else//cancel
		{
			m_status = STA_NORMAL;
			m_rects = null;
			invalidate();
			m_view.vSetLock(0);
		}
	}
	public void PDFSetEllipse(int code)
	{
		if( code == 0 )//start
		{
			m_status = STA_ELLIPSE;
			m_view.vSetLock(3);
		}
		else if( code == 1 )//end
		{
			if( m_rects != null )
			{
				int len = m_rects.length;
				int cur;
				PDFVPageSet pset = new PDFVPageSet(len);
				for( cur = 0; cur < len; cur += 4 )
				{
					PDFPos pos = m_view.vGetPos((int)m_rects[cur], (int)m_rects[cur + 1]);
					PDFVPage vpage = m_view.vGetPage(pos.pageno);
					Page page = vpage.GetPage();
					if( page != null )
					{
						Matrix mat = vpage.CreateInvertMatrix(m_view.vGetX(), m_view.vGetY());
						float rect[] = new float[4];
						if( m_rects[cur] > m_rects[cur + 2] ) 
						{
							rect[0] = m_rects[cur + 2];
							rect[2] = m_rects[cur];
						}
						else
						{
							rect[0] = m_rects[cur];
							rect[2] = m_rects[cur + 2];
						}
						if( m_rects[cur + 1] > m_rects[cur + 3] ) 
						{
							rect[1] = m_rects[cur + 3];
							rect[3] = m_rects[cur + 1];
						}
						else
						{
							rect[1] = m_rects[cur + 1];
							rect[3] = m_rects[cur + 3];
						}
						mat.TransformRect(rect);
						page.AddAnnotEllipse(rect, vpage.ToPDFSize(3), 0x80FF0000, 0x800000FF);
						mat.Destroy();
						pset.Insert(vpage);
					}
				}
				for( cur = 0; cur < pset.pages_cnt; cur++ )
				{
					PDFVPage vpage = pset.pages[cur];
					m_view.vRender(vpage);
					if( m_listener != null )
						m_listener.OnPageModified(vpage.GetPageNo());
				}
			}
			m_status = STA_NORMAL;
			m_rects = null;
			invalidate();
			m_view.vSetLock(0);
		}
		else//cancel
		{
			m_status = STA_NORMAL;
			m_rects = null;
			invalidate();
			m_view.vSetLock(0);
		}
	}
	public void PDFSetSelect()
	{
		if( m_status == STA_SELECT )
		{
			m_status = STA_NORMAL;
			m_view.vSetSelStatus(false);
		}
		else
		{
			m_status = STA_SELECT;
			m_view.vSetSelStatus(true);
		}
	}
	public void PDFSetNote()
	{
		if( m_status == STA_NOTE )
		{
			m_status = STA_NORMAL;
			m_view.vSetLock(0);
		}
		else
		{
			m_status = STA_NOTE;
			m_view.vSetLock(3);
		}
	}
	public void PDFSetLine( int code )
	{
		if( code == 0 )//start
		{
			m_status = STA_LINE;
			m_view.vSetLock(3);
		}
		else if( code == 1 )//end
		{
			if( m_rects != null )
			{
				int len = m_rects.length;
				int cur;
				float[] pt1 = new float[2];
				float[] pt2 = new float[2];
				PDFVPageSet pset = new PDFVPageSet(len);
				for( cur = 0; cur < len; cur += 4 )
				{
					PDFPos pos = m_view.vGetPos((int)m_rects[cur], (int)m_rects[cur + 1]);
					PDFVPage vpage = m_view.vGetPage(pos.pageno);
					pt1[0] = m_rects[cur];
					pt1[1] = m_rects[cur + 1];
					pt2[0] = m_rects[cur + 2];
					pt2[1] = m_rects[cur + 3];
					Page page = vpage.GetPage();
					if( page != null )
					{
						Matrix mat = vpage.CreateInvertMatrix(m_view.vGetX(), m_view.vGetY());
						mat.TransformPoint(pt1);
						mat.TransformPoint(pt2);
						page.AddAnnotLine(pt1, pt2, 1, 0, vpage.ToPDFSize(3), 0x80FF0000, 0x800000FF);
						mat.Destroy();
						pset.Insert(vpage);
					}
				}
				for( cur = 0; cur < pset.pages_cnt; cur++ )
				{
					PDFVPage vpage = pset.pages[cur];
					m_view.vRender(vpage);
					if( m_listener != null )
						m_listener.OnPageModified(vpage.GetPageNo());
				}
			}
			m_status = STA_NORMAL;
			m_rects = null;
			invalidate();
			m_view.vSetLock(0);
		}
		else//cancel
		{
			m_status = STA_NORMAL;
			m_rects = null;
			invalidate();
			m_view.vSetLock(0);
		}
	}
	public void PDFCancel()
	{
		if( m_status == STA_NOTE ) PDFSetNote();
		if( m_status == STA_RECT ) PDFSetRect(2);
		if( m_status == STA_INK ) PDFSetInk(2);
		if( m_status == STA_LINE ) PDFSetLine(2);
		if( m_status == STA_ELLIPSE ) PDFSetEllipse(2);
		if( m_status == STA_ANNOT ) PDFEndAnnot();
		invalidate();
	}
	public void PDFRemoveAnnot()
	{
		if( m_status != STA_ANNOT || !m_doc.CanSave() ) return;
		m_annot.RemoveFromPage();
		m_annot = null;
		m_view.vRender(m_annot_page);
		if( m_listener != null )
			m_listener.OnPageModified(m_annot_page.GetPageNo());
		PDFEndAnnot();
	}
	public void PDFEndAnnot()
	{
		if( m_status != STA_ANNOT ) return;
		m_annot_page = null;
		m_annot_pos = null;
		m_annot = null;
		m_view.vSetLock(0);
		invalidate();
		m_status = STA_NORMAL;
		if( m_pEdit != null && m_pEdit.isShowing() ) m_pEdit.dismiss();
		if( m_pCombo != null && m_pCombo.isShowing() ) m_pCombo.dismiss();
		if( m_listener != null )
			m_listener.OnAnnotClicked(null, null);
	}
	public void PDFPerformAnnot()
	{
		if( m_status != STA_ANNOT ) return;
		Page page = m_annot_page.GetPage();
		if( page == null || m_annot == null ) return;
		int dest = m_annot.GetDest();
		if( dest >= 0 )
			m_view.vGotoPage(dest);
		String uri = m_annot.GetURI();
		if( m_listener != null )
			m_listener.OnOpenURI(uri);
		int index;
		String mov = m_annot.GetMovie();
		if( mov != null )
		{
			index = -1;
			if( index < 0 ) index = mov.lastIndexOf('\\');
			if( index < 0 ) index = mov.lastIndexOf('/');
			if( index < 0 ) index = mov.lastIndexOf(':');
			String save_file = Global.tmp_path + "/" + mov.substring(index + 1);
			m_annot.GetMovieData(save_file);
			if( m_listener != null )
				m_listener.OnOpenMovie(save_file);
		}
		String snd = m_annot.GetSound();
		if( snd != null )
		{
			int paras[] = new int[4];
			index = -1;
			if( index < 0 ) index = snd.lastIndexOf('\\');
			if( index < 0 ) index = snd.lastIndexOf('/');
			if( index < 0 ) index = snd.lastIndexOf(':');
			String save_file = Global.tmp_path + "/" + snd.substring(index + 1);
			m_annot.GetSoundData(paras, save_file);
			if( m_listener != null )
				m_listener.OnOpenSound(paras, save_file);
		}
		String att = m_annot.GetAttachment();
		if( att != null )
		{
			index = -1;
			if( index < 0 ) index = att.lastIndexOf('\\');
			if( index < 0 ) index = att.lastIndexOf('/');
			if( index < 0 ) index = att.lastIndexOf(':');
			String save_file = Global.tmp_path + "/" + att.substring(index + 1);
			m_annot.GetAttachmentData(save_file);
			if( m_listener != null )
				m_listener.OnOpenAttachment(save_file);
		}
		String f3d = m_annot.Get3D();
		if( f3d != null )
		{
			index = -1;
			if( index < 0 ) index = f3d.lastIndexOf('\\');
			if( index < 0 ) index = f3d.lastIndexOf('/');
			if( index < 0 ) index = f3d.lastIndexOf(':');
			String save_file = Global.tmp_path + "/" + f3d.substring(index + 1);
			m_annot.Get3DData(save_file);
			if( m_listener != null )
				m_listener.OnOpen3D(save_file);
		}

		int check = m_annot.GetCheckStatus();
		if( m_doc.CanSave() && check >= 0 )
		{
			switch( check )
			{
			case 0:
				m_annot.SetCheckValue(true);
				break;
			case 1:
				m_annot.SetCheckValue(false);
				break;
			case 2:
			case 3:
				m_annot.SetRadio();
				break;
			}
			m_view.vRender(m_annot_page);
			if( m_listener != null )
				m_listener.OnPageModified(m_annot_page.GetPageNo());
		}

		boolean reset = m_annot.GetReset();
		if( reset && m_doc.CanSave() )
		{
			m_annot.SetReset();
			m_view.vRender(m_annot_page);
			if( m_listener != null )
				m_listener.OnPageModified(m_annot_page.GetPageNo());
		}
		String tar = m_annot.GetSubmitTarget();
		if( tar != null )
		{
			if( m_listener != null )
				m_listener.OnOpenURI(tar + "?" + m_annot.GetSubmitTarget());
		}
		PDFEndAnnot();
	}
	public void PDFFindStart( String key, boolean match_case, boolean whole_word )
	{
		m_view.vFindStart(key, match_case, whole_word);
	}
	public void PDFFind(int dir)
	{
		m_view.vFind(dir);
	}
	public boolean PDFSetSelMarkup(int type)
	{
		return m_view.vSetSelMarkup(type);
	}
	public void OnPDFInvalidate(boolean post)
	{
		if( post ) postInvalidate();
		else invalidate();
	}
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		m_sel_index = arg2;
		m_pCombo.dismiss();
	}
	public void onDismiss()
	{
		if( m_edit_type == 1 )//edit box
		{
			EditText edit = (EditText)m_pEdit.getContentView().findViewById(R.id.annot_text);
			m_annot.SetEditText(edit.getText().toString());
			m_view.vRender(m_annot_page);
			if( m_listener != null )
				m_listener.OnPageModified(m_annot_page.GetPageNo());
			PDFEndAnnot();
		}
		if( m_edit_type == 2 )//combo
		{
			if( m_sel_index >= 0 )
			{
				m_annot.SetComboItem(m_sel_index);
				m_view.vRender(m_annot_page);
				if( m_listener != null )
					m_listener.OnPageModified(m_annot_page.GetPageNo());
			}
			m_sel_index = -1;
			PDFEndAnnot();
		}
		m_edit_type = 0;
	}
	private void onDrawAnnot(Canvas canvas)
	{
		if( m_status == STA_ANNOT )
		{
			Paint paint = new Paint();
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(2);
			paint.setARGB(0x80, 0, 0, 0);
			canvas.drawRect(m_annot_rect[0],
					m_annot_rect[1],
					m_annot_rect[2],
					m_annot_rect[3], paint);
		}
	}
	private void onDrawRect(Canvas canvas)
	{
		if( m_status == STA_RECT && m_rects != null )
		{
			int len = m_rects.length;
			int cur;
			Paint paint1 = new Paint();
			Paint paint2 = new Paint();
			paint1.setStyle(Style.STROKE);
			paint1.setStrokeWidth(3);
			paint1.setARGB(0x80, 0xFF, 0, 0);
			paint2.setStyle(Style.FILL);
			paint2.setARGB(0x80, 0, 0, 0xFF);
			for( cur = 0; cur < len; cur += 4 )
			{
				float rect[] = new float[4];
				if( m_rects[cur] > m_rects[cur + 2] ) 
				{
					rect[0] = m_rects[cur + 2];
					rect[2] = m_rects[cur];
				}
				else
				{
					rect[0] = m_rects[cur];
					rect[2] = m_rects[cur + 2];
				}
				if( m_rects[cur + 1] > m_rects[cur + 3] ) 
				{
					rect[1] = m_rects[cur + 3];
					rect[3] = m_rects[cur + 1];
				}
				else
				{
					rect[1] = m_rects[cur + 1];
					rect[3] = m_rects[cur + 3];
				}
				canvas.drawRect(rect[0], rect[1], rect[2], rect[3], paint1);
				canvas.drawRect(rect[0] + 1.5f, rect[1] + 1.5f, rect[2] - 1.5f, rect[3] - 1.5f, paint2);
			}
		}
	}
	private void onDrawLine(Canvas canvas)
	{
		if( m_status == STA_LINE && m_rects != null )
		{
			int len = m_rects.length;
			int cur;
			Paint paint1 = new Paint();
			paint1.setStyle(Style.STROKE);
			paint1.setStrokeWidth(3);
			paint1.setARGB(0x80, 0xFF, 0, 0);
			for( cur = 0; cur < len; cur += 4 )
			{
				canvas.drawLine(m_rects[cur], m_rects[cur + 1], m_rects[cur + 2], m_rects[cur + 3], paint1);
			}
		}
	}
	private void onDrawEllipse(Canvas canvas)
	{
		if( m_status == STA_ELLIPSE && m_rects != null )
		{
			int len = m_rects.length;
			int cur;
			Paint paint1 = new Paint();
			Paint paint2 = new Paint();
			paint1.setStyle(Style.STROKE);
			paint1.setStrokeWidth(3);
			paint1.setARGB(0x80, 0xFF, 0, 0);
			paint2.setStyle(Style.FILL);
			paint2.setARGB(0x80, 0, 0, 0xFF);
			for( cur = 0; cur < len; cur += 4 )
			{
				float rect[] = new float[4];
				if( m_rects[cur] > m_rects[cur + 2] ) 
				{
					rect[0] = m_rects[cur + 2];
					rect[2] = m_rects[cur];
				}
				else
				{
					rect[0] = m_rects[cur];
					rect[2] = m_rects[cur + 2];
				}
				if( m_rects[cur + 1] > m_rects[cur + 3] ) 
				{
					rect[1] = m_rects[cur + 3];
					rect[3] = m_rects[cur + 1];
				}
				else
				{
					rect[1] = m_rects[cur + 1];
					rect[3] = m_rects[cur + 3];
				}
				RectF rc = new RectF();
				rc.left = rect[0];
				rc.top = rect[1];
				rc.right = rect[2];
				rc.bottom = rect[3];
				canvas.drawOval(rc, paint1);
				rc.left += 1.5f;
				rc.top += 1.5f;
				rc.right -= 1.5f;
				rc.bottom -= 1.5f;
				canvas.drawOval(rc, paint2);
			}
		}
	}
	@Override
	public void computeScroll()
	{
		if( m_view == null ) return;
		m_view.vComputeScroll();
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		if( m_view == null ) return;
		m_view.vDraw(canvas);
		onDrawRect(canvas);
		onDrawEllipse(canvas);
		onDrawAnnot(canvas);
		onDrawLine(canvas);
		if( m_status == STA_INK )
		{
			m_ink.OnDraw(canvas, 0, 0 );
		}
	}
	@Override
	protected void onSizeChanged( int w, int h, int oldw, int oldh )
	{
		super.onSizeChanged(w,h,oldw,oldh);
		if( m_view == null ) return;
		PDFPos pos = m_view.vGetPos(0, 0);
		m_view.vResize(w, h);
		//m_view.vSetScale(0, 0, 0);//fit page while resizing.
		if( m_goto_pageno >= 0 )
		{
			m_view.vGotoPage(m_goto_pageno);
			m_goto_pageno = -1;
		}
		else if( pos != null )
		{
			m_view.vSetPos(pos, 0, 0);
			//m_view.vCenterPage(pos.pageno);
		}
		if( m_scale >= 0 )
		{
			m_view.vSetScale(m_scale, 0, 0);
			m_scale = -1;
		}
	}
	private boolean onTouchInk(MotionEvent event)
	{
		if( m_status == STA_INK )
		{
			switch(event.getActionMasked())
			{
			case MotionEvent.ACTION_DOWN:
				if( m_ink_page == null )
				{
					PDFPos pos = m_view.vGetPos((int)event.getX(), (int)event.getY());
					m_ink_page = m_view.vGetPage(pos.pageno);
				}
				m_ink.OnDown(event.getX(), event.getY());
				break;
			case MotionEvent.ACTION_MOVE:
				m_ink.OnMove(event.getX(), event.getY());
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				m_ink.OnUp(event.getX(), event.getY());
				break;
			}
			invalidate();
			return true;
		}
		return false;
	}
	private boolean onTouchRect(MotionEvent event)
	{
		if( m_status == STA_RECT )
		{
			int len = 0;
			if( m_rects != null ) len = m_rects.length;
			int cur = 0;
			switch(event.getActionMasked())
			{
			case MotionEvent.ACTION_DOWN:
				float rects[] = new float[len + 4];
				for( cur = 0; cur < len; cur++ )
					rects[cur] = m_rects[cur];
				len += 4;
				rects[cur + 0] = event.getX();
				rects[cur + 1] = event.getY();
				rects[cur + 2] = event.getX();
				rects[cur + 3] = event.getY();
				m_rects = rects;
				break;
			case MotionEvent.ACTION_MOVE:
				m_rects[len - 2] = event.getX();
				m_rects[len - 1] = event.getY();
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				m_rects[len - 2] = event.getX();
				m_rects[len - 1] = event.getY();
				break;
			}
			invalidate();
			return true;
		}
		return false;
	}
	private boolean onTouchEllipse(MotionEvent event)
	{
		if( m_status == STA_ELLIPSE )
		{
			int len = 0;
			if( m_rects != null ) len = m_rects.length;
			int cur = 0;
			switch(event.getActionMasked())
			{
			case MotionEvent.ACTION_DOWN:
				float rects[] = new float[len + 4];
				for( cur = 0; cur < len; cur++ )
					rects[cur] = m_rects[cur];
				len += 4;
				rects[cur + 0] = event.getX();
				rects[cur + 1] = event.getY();
				rects[cur + 2] = event.getX();
				rects[cur + 3] = event.getY();
				m_rects = rects;
				break;
			case MotionEvent.ACTION_MOVE:
				m_rects[len - 2] = event.getX();
				m_rects[len - 1] = event.getY();
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				m_rects[len - 2] = event.getX();
				m_rects[len - 1] = event.getY();
				break;
			}
			invalidate();
			return true;
		}
		return false;
	}
	private boolean onTouchAnnot(MotionEvent event)
	{
		if( m_status == STA_ANNOT )
		{
			switch(event.getActionMasked())
			{
			case MotionEvent.ACTION_DOWN:
				m_annot_x0 = event.getX();
				m_annot_y0 = event.getY();
				if( m_annot_x0 > m_annot_rect[0] && m_annot_y0 > m_annot_rect[1] &&
					m_annot_x0 < m_annot_rect[2] && m_annot_y0 < m_annot_rect[3] )
				{
					m_annot_rect0 = new float[4];
					m_annot_rect0[0] = m_annot_rect[0];
					m_annot_rect0[1] = m_annot_rect[1];
					m_annot_rect0[2] = m_annot_rect[2];
					m_annot_rect0[3] = m_annot_rect[3];
				}
				else
					m_annot_rect0 = null;
				break;
			case MotionEvent.ACTION_MOVE:
				if( m_annot_rect0 != null )
				{
					float x = event.getX();
					float y = event.getY();
					m_annot_rect[0] = m_annot_rect0[0] + x - m_annot_x0;
					m_annot_rect[1] = m_annot_rect0[1] + y - m_annot_y0;
					m_annot_rect[2] = m_annot_rect0[2] + x - m_annot_x0;
					m_annot_rect[3] = m_annot_rect0[3] + y - m_annot_y0;
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				if( m_annot_rect0 != null )
				{
					float x = event.getX();
					float y = event.getY();
					m_annot_rect[0] = m_annot_rect0[0] + x - m_annot_x0;
					m_annot_rect[1] = m_annot_rect0[1] + y - m_annot_y0;
					m_annot_rect[2] = m_annot_rect0[2] + x - m_annot_x0;
					m_annot_rect[3] = m_annot_rect0[3] + y - m_annot_y0;
					m_annot_rect0[0] = m_annot_page.ToPDFX(m_annot_rect[0], m_view.vGetX());
					m_annot_rect0[1] = m_annot_page.ToPDFY(m_annot_rect[3], m_view.vGetY());
					m_annot_rect0[2] = m_annot_page.ToPDFX(m_annot_rect[2], m_view.vGetX());
					m_annot_rect0[3] = m_annot_page.ToPDFY(m_annot_rect[1], m_view.vGetY());
					m_annot.SetRect(m_annot_rect0[0], m_annot_rect0[1], m_annot_rect0[2], m_annot_rect0[3]);
					m_view.vRender(m_annot_page);
					if( m_listener != null )
						m_listener.OnPageModified(m_annot_page.GetPageNo());
				}
				PDFEndAnnot();
				break;
			}
			invalidate();
			return true;
		}
		return false;
	}
	private boolean onTouchLine(MotionEvent event)
	{
		if( m_status == STA_LINE )
		{
			int len = 0;
			if( m_rects != null ) len = m_rects.length;
			int cur = 0;
			switch(event.getActionMasked())
			{
			case MotionEvent.ACTION_DOWN:
				float rects[] = new float[len + 4];
				for( cur = 0; cur < len; cur++ )
					rects[cur] = m_rects[cur];
				len += 4;
				rects[cur + 0] = event.getX();
				rects[cur + 1] = event.getY();
				rects[cur + 2] = event.getX();
				rects[cur + 3] = event.getY();
				m_rects = rects;
				break;
			case MotionEvent.ACTION_MOVE:
				m_rects[len - 2] = event.getX();
				m_rects[len - 1] = event.getY();
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				m_rects[len - 2] = event.getX();
				m_rects[len - 1] = event.getY();
				break;
			}
			invalidate();
			return true;
		}
		return false;
	}
	private boolean onTouchNote(MotionEvent event)
	{
		if( m_status == STA_NOTE )
		{
			switch(event.getActionMasked())
			{
			case MotionEvent.ACTION_UP:
				PDFPos pos = m_view.vGetPos((int)event.getX(), (int)event.getY());
				PDFVPage vpage = m_view.vGetPage(pos.pageno);
				Page page = vpage.GetPage();
				if( page != null )
				{
					float pt[] = new float[2];
					pt[0] = pos.x;
					pt[1] = pos.y;
					page.AddAnnotText(pt);
					m_view.vRender(vpage);
					if( m_listener != null )
						m_listener.OnPageModified(vpage.GetPageNo());
				}
				break;
			}
			return true;
		}
		return false;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if( m_view == null ) return false;
		if( m_view.vGetLock() == 3 )
		{
			if( onTouchInk(event) ) return true;
			if( onTouchRect(event) ) return true;
			if( onTouchEllipse(event) ) return true;
			if( onTouchNote(event) ) return true;
			if( onTouchLine(event) ) return true;
			if( onTouchAnnot(event) ) return true;
		}
		return m_view.vTouchEvent(event);
	}
	private int m_goto_pageno = -1;
	private float m_scale = -1;
	public void PDFSetScale(float scale)
	{
		if( m_view.vGetWinH() <= 0 || m_view.vGetWinW() <= 0 )
			m_scale = scale;
		else
			m_view.vSetScale(scale, 0, 0);
	}
	public void PDFGotoPage(int pageno)
	{
		if( m_view.vGetWinH() <= 0 || m_view.vGetWinW() <= 0 )
			m_goto_pageno = pageno;
		else
		{
			m_view.vGotoPage(pageno);
			invalidate();
		}
	}
	public Document PDFGetDoc()
	{
		return m_doc;
	}
	public void PDFSetView(int style)
	{
		Document doc = m_doc;
		PDFClose();
		m_doc = doc;
		int back_color = 0xFFCCCCCC;
        switch( style )
        {
        case 1:
        {
        	PDFViewHorz view = new PDFViewHorz(this.getContext());
        	view.vSetDirection(m_rtol);
            m_view = view;
        }
            break;
        case 2:
            m_view = new PDFViewCurl(this.getContext());
            back_color = 0xFFFFFFFF;
            break;
        case 3:
        {
        	PDFViewDual view = new PDFViewDual(this.getContext());
        	boolean paras[] = new boolean[m_doc.GetPageCount()];
        	int cur = 0;
        	while( cur < paras.length )
        	{
        		paras[cur] = false;
        		cur++;
        	}
        	view.vSetLayoutPara(null, paras, m_rtol);
            m_view = view;
        }
            break;
        case 4:
        case 6:
        {
        	PDFViewDual view = new PDFViewDual(this.getContext());
        	view.vSetLayoutPara(null, null, m_rtol);
            m_view = view;
        }
            break;
        default:
            m_view = new PDFViewVert(this.getContext());
            break;
        }
        m_view.vOpen(m_doc, 4, back_color, this);
		PDFPos pos = m_view.vGetPos(0, 0);
		m_view.vResize(this.getWidth(), this.getHeight());
		//m_view.vSetScale(0, 0, 0);
		if( m_goto_pageno >= 0 )
		{
			m_view.vGotoPage(m_goto_pageno);
			m_goto_pageno = -1;
		}
		else if( pos != null )
		{
			m_view.vSetPos(pos, 0, 0);
			//m_view.vCenterPage(pos.pageno);
		}
        invalidate();
	}
	public void OnPDFPageDisplayed(Canvas canvas, PDFVPage vpage)
	{
		//get page bounding in view. just do it, if you need these codes.
		//int left = vpage.GetVX(m_view.vGetX());
		//int top = vpage.GetVY(m_view.vGetY());
		//int right = left + vpage.GetWidth();
		//int bottom = top + vpage.GetHeight();
		//float mark_left = left + (right - left) / 16;
		//float mark_right = mark_left + (right - left) / 8;
		//float mark_top = top + (bottom - top) / 16;
		//float mark_bottom = mark_top + (bottom - top) / 8;
		//Paint paint = new Paint();
		//paint.setStyle(Style.STROKE);
		//paint.setARGB(128, 255, 255, 128);
		//canvas.drawRect(mark_left, mark_top, mark_right, mark_bottom, paint);
	}
}
