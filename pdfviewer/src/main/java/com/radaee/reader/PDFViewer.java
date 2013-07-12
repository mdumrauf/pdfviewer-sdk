package com.radaee.reader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.radaee.pdf.*;

import java.util.Timer;
import java.util.TimerTask;

/**
 * test view<br/>
 * this view based on PDFV class.<br/>
 * no more support for this classe.
 * @author Radaee
 *
 */
public class PDFViewer extends View
{
	private Bitmap m_bitmap;
	private Document m_doc;
	private int win_cx = 0;
	private int win_cy = 0;
	private Ink ink = null;
	private PDFV m_view = null;
	public enum STATUS
    {
        sta_none,
        sta_hold,
        sta_sel,
        sta_zoom,
        sta_annot,
        sta_ink,
        sta_rect,
    };
    private STATUS m_status = STATUS.sta_none;
    public class PDFTimer extends Timer
    {
    	private Handler handUI;
    	private TimerTask task;
    	private int m_pendings;
    	protected PDFTimer(Handler handUI)
    	{
        	this.handUI = handUI;
        	m_pendings = 0;
    	}
    	private synchronized boolean pending_check()
    	{
    		if( m_pendings > 2 )
    		{
    			m_pendings = 1;
    			return true;
    		}
    		else
    		{
    			m_pendings++;
    			return false;
    		}
    	}
    	protected synchronized void pending_dec()
    	{
    		m_pendings--;
    	}
    	protected void start()
        {
            task = new TimerTask()
            {
            	public void run()
            	{
            		if( pending_check() )
            			handUI.removeMessages(100);
            		handUI.sendEmptyMessage(100);
            	}
            };
            schedule(task, 100, 40);
        }
    	protected void destroy()
        {
        	cancel();
        	task.cancel();
        }
    }
    private Handler m_hand_ui = new Handler()
    {
    	@Override
    	public void handleMessage(Message msg)
    	{
    		if( msg.what == 100 )//timer
    		{
    			switch( m_view.Draw(m_bitmap) )
    			{
    			case 1://need refresh
    			case 2://find OK, and goto
        			invalidate();
        			break;
    			case 3://no more found.
    				{
    					Toast t = Toast.makeText(PDFViewer.this.getContext(), "No more found!", Toast.LENGTH_LONG);
    					t.show();
    				}
    				break;
        		default:
        			if( m_status == STATUS.sta_zoom )
            			invalidate();
        			break;
    			}
    		}
    		super.handleMessage(msg);
    	}
    };
    private PDFTimer m_timer = new PDFTimer(m_hand_ui);
    class ViewerGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
        	if( m_status != STATUS.sta_hold ) return false;
        	m_view.OnUp(e2.getX(), e2.getY());
        	m_view.Fling(velocityX/2, velocityY/2);
       		m_status = STATUS.sta_none;
    		return true;
        }
        @Override
        public boolean onDoubleTap(MotionEvent e)
        {
        	return false;
        }
        @Override
        public boolean onDoubleTapEvent(MotionEvent e)
        {
        	return false;
        }
        @Override
        public boolean onDown(MotionEvent e)
        {
        	return false;
        }
        @Override
        public void onLongPress(MotionEvent e)
        {
        }
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
        	return false;
        }
        @Override
        public void onShowPress(MotionEvent e)
        {
        }
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e)
        {
            float x = e.getX();
            float y = e.getY();
            float[] rect = new float[4];
            int page_obj = m_view.GetPageFromPoint(x, y, rect);
            if( page_obj != 0 )
           	{
            	int annot_obj = m_view.PageGetAnnotFromPoint( page_obj, x, y );
        		if( annot_obj != 0 )
        		{
        			String uri = m_view.PageGetAnnotURI( page_obj, annot_obj );
        			if( uri != null )
        			{
        				Toast.makeText(getContext(), uri, Toast.LENGTH_SHORT).show();
        				//open uri by starting an intent.
           		    }
        			int pageno = m_view.PageGetAnnotDest(page_obj, annot_obj);
        			if( pageno >= 0 )
        				m_view.SetPos(pageno, 0, m_doc.GetPageHeight(pageno) );
        		}
          	}
            return true;
        }
        @Override
        public boolean onSingleTapUp(MotionEvent e)
        {
        	return false;
        }
    }
    private GestureDetector m_gesture = null;
    private float m_zoomx = 0;
    private float m_zoomy = 0;
    private float m_zoom_ratio = 0;
	public PDFViewer(Context context)
	{
		super(context);
	}
	public PDFViewer(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	public void Open( int type, Document doc )
	{
		m_view = new PDFV();
		m_view.Open(type, doc, 4);
		m_doc = doc;
		m_timer.start();
		m_gesture = new GestureDetector( getContext(), new ViewerGestureListener() );
		PDFVS.setFlingSpeed(0.15f);
	}
	public void Close()
	{
		m_timer.destroy();
		m_view.Close();
		m_view = null;
		m_gesture = null;
	}
	protected void onSizeChanged (int w, int h, int oldw, int oldh)
	{
		win_cx = w;
		win_cy = h;
		if( win_cx > 0 && win_cy > 0 )
		{
			if( m_bitmap != null ) m_bitmap.recycle();
			m_bitmap = Bitmap.createBitmap(win_cx, win_cy, Config.ARGB_8888);
			if( m_view != null ) m_view.SetSize(w, h);
		}
	}
	protected void onDraw( Canvas canvas )
	{
		if( m_status == STATUS.sta_zoom )
		{
    		Matrix mat = new Matrix();
    		mat.setScale(m_zoom_ratio, m_zoom_ratio, m_zoomx, m_zoomy);
    		canvas.drawBitmap(m_bitmap, mat, null);
    		mat = null;
		}
		else
		{
			canvas.drawBitmap(m_bitmap, 0, 0, null);
			if( ink != null )
				ink.OnDraw(canvas);
		}
	}
	private boolean motionNormal(MotionEvent event)
	{
		switch( event.getActionMasked() )
		{
		case MotionEvent.ACTION_DOWN:
			if( m_view != null )
				m_view.OnDown(event.getX(), event.getY());
			m_status = STATUS.sta_hold;
			break;
		case MotionEvent.ACTION_MOVE:
			if( m_view != null )
				m_view.OnMove(event.getX(), event.getY());
			break;
		case MotionEvent.ACTION_UP:
			if( m_view != null )
				m_view.OnUp(event.getX(), event.getY());
			m_status = STATUS.sta_none;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			m_zoomx = (event.getX(0) + event.getX(1))/2;
			m_zoomy = (event.getY(0) + event.getY(1))/2;
			m_view.OnUp(event.getX(), event.getY());
			m_view.ZoomStart(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
			m_zoom_ratio = 1;
			m_status = STATUS.sta_zoom;
			break;
		}
		return true;
	}
	private boolean motionInk(MotionEvent event)
	{
		switch( event.getActionMasked() )
		{
		case MotionEvent.ACTION_DOWN:
			if( ink != null )
				ink.OnDown(event.getX(), event.getY());
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			if( ink != null )
				ink.OnMove(event.getX(), event.getY());
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			if( ink != null )
				ink.OnUp(event.getX(), event.getY());
			invalidate();
			break;
		}
		return true;
	}
	private boolean motionZoom(MotionEvent event)
	{
		switch( event.getActionMasked() )
		{
		case MotionEvent.ACTION_MOVE:
			if( event.getPointerCount() == 2 )
				m_zoom_ratio = m_view.ZoomGetRatio(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			m_view.ZoomEnd();
			m_status = STATUS.sta_none;
			break;
		}
		return true;
	}
	public boolean onTouchEvent (MotionEvent event)
	{
		if( m_view == null ) return true;
    	if( m_gesture.onTouchEvent(event) )
    		return true;
		if( m_status == STATUS.sta_ink )
			return motionInk( event );
		if( m_status == STATUS.sta_none || m_status == STATUS.sta_hold )
			return motionNormal( event );
		if( m_status == STATUS.sta_zoom )
			return motionZoom(event);
		return true;
	}
}
