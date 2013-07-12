package com.radaee.util;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.radaee.pdf.Document;
import com.radaee.view.PDFVPage;
import com.radaee.view.PDFView.PDFPos;
import com.radaee.view.PDFView.PDFViewListener;
import com.radaee.view.PDFViewThumb;
import com.radaee.view.PDFViewThumb.PDFThumbListener;

public class PDFThumbView extends View implements PDFViewListener
{
	private PDFViewThumb m_thumb;
	public PDFThumbView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		m_thumb = new PDFViewThumb(context);
	}
	public void OnPDFPosChanged(PDFPos pos)
	{
	}
	public boolean OnPDFDoubleTapped(float x, float y)
	{
		return false;
	}
	public boolean OnPDFSingleTapped(float x, float y)
	{
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
	}
	public void OnPDFFound(boolean found)
	{
	}
	public void OnPDFInvalidate(boolean post)
	{
		if( post ) postInvalidate();
		else invalidate();
	}
	@Override
	public void computeScroll()
	{
		if( m_thumb != null )
			m_thumb.vComputeScroll();
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		if( m_thumb != null )
			m_thumb.vDraw(canvas);
	}
	@Override
	protected void onSizeChanged( int w, int h, int oldw, int oldh )
	{
		if( m_thumb != null )
			m_thumb.vResize(w, h);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if( m_thumb == null ) return false;
		return m_thumb.vTouchEvent(event);
	}
	public void thumbOpen( Document doc, PDFThumbListener listener )
	{
		m_thumb.vOpen(doc, 8, 0x40CCCCCC, this);
		m_thumb.vSetThumbListener(listener);
		m_thumb.vResize(getWidth(), getHeight());
	}
	public void thumbClose()
	{
		m_thumb.vClose();
	}
	/**
	 * set selected page and goto the page
	 * @param pageno
	 */
	public void thumbGotoPage( int pageno )
	{
		m_thumb.vSetSel(pageno);
	}
	/**
	 * render a page again, after page is edited.
	 * @param pageno
	 */
	public void thumbUpdatePage( int pageno )
	{
		m_thumb.vRender(m_thumb.vGetPage(pageno));
	}
	public void OnPDFPageDisplayed(Canvas canvas, PDFVPage vpage)
	{
		// TODO Auto-generated method stub
		
	}
}
