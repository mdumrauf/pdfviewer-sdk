package com.radaee.reader;

import java.io.File;

import com.radaee.pdf.*;
import com.radaee.pdfex.*;
import com.radaee.pdfex.PDFView.PDFAnnotListener;
import com.radaee.pdfex.PDFView.PDFPageDispPara;
import com.radaee.pdfex.PDFView.PDFPosition;
import com.radaee.pdfex.PDFView.PDFViewListener;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

public class PDFReaderOld extends View implements PDFView.PDFViewListener, ThumbView.ThumbListener
{
	private PDFView m_viewer = null;
	private int m_style = -1;
	private boolean m_lock_resize = false;
	private int m_save_w = 0;
	private int m_save_h = 0;
	private int m_cur_page = 0;
	private ThumbView m_thumbView;
	public PDFReaderOld(Context context)
	{
		super(context);
	}
	public PDFReaderOld(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	public void set_viewer( int view_style )
	{
		if( view_style == m_style ) return;
		PDFPosition pos = null;
		PDFAnnotListener annot_listener = null;
		PDFViewListener view_listener = null;
		Document doc = null;
		if( m_viewer != null )
		{
			doc = m_viewer.viewGetDoc();
			annot_listener = m_viewer.viewGetAnnotListener();
			view_listener = m_viewer.viewGetViewListener();
			pos = m_viewer.viewGetPos();
			m_viewer.viewClose();
		}
		switch( view_style )
		{
		case 1:
			m_viewer = new PDFViewHorz();
			break;
		case 2:
			m_viewer = new PDFViewScroll();
			break;
		case 3:
			m_viewer = new PDFViewSingle();
			break;
		case 4:
			m_viewer = new PDFViewSingleEx();
			break;
		case 5:
			m_viewer = new PDFViewReflow();
			break;
		case 6:
			m_viewer = new PDFViewDual(true);
			break;
		default:
			m_viewer = new PDFViewVert();
			break;
		}
		if( m_viewer != null )
		{
			if( doc != null ) m_viewer.viewOpen(getContext(), doc, 0xFFCC0000, 4);
			m_viewer.viewSetAnnotListener( annot_listener );
			m_viewer.viewSetViewListener( view_listener );
			m_viewer.viewResize(getWidth(), getHeight());
			if( pos != null ) m_viewer.viewGoto(pos);
		}
	}
	protected void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
	}
	public void set_thumb( ThumbView view )
	{
		m_thumbView = view;
	}
	public PDFView get_viewer()
	{
		return m_viewer;
	}
	public void open( Document doc )
	{
		set_viewer( Global.def_view );
		if( m_viewer != null )
			m_viewer.viewOpen(getContext(), doc, 0xFFCCCCCC, 4);
	}
	public void close()
	{
		if( m_viewer != null )
			m_viewer.viewClose();
		m_viewer = null;
	}
	protected void onSizeChanged (int w, int h, int oldw, int oldh)
	{
		m_save_w = w;
		m_save_h = h;
		if( m_viewer != null )
		{
			if(!m_lock_resize)
			{
				m_viewer.viewResize(w, h);
				//m_viewer.viewSetRatio(1, 0, 0, false);//1 or less
			}
			//m_thumbView.thumbGotoPage(m_viewer.viewGetCurPageNo());
		}
	}
	protected void onDraw( Canvas canvas )
	{
		if( m_viewer != null )
		{
			m_viewer.viewDraw(canvas);

			ActivityManager mgr = (ActivityManager)getContext().getSystemService(Context.ACTIVITY_SERVICE);
			ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
			mgr.getMemoryInfo(info);
			Paint paint = new Paint();
			paint.setARGB(255, 255, 0, 0);
			canvas.drawText( "AvialMem:" + info.availMem/(1024*1024) + " M", 20, 120, paint);
			
			int pageno = m_viewer.viewGetCurPageNo();
			canvas.drawText( "Page:" + String.valueOf(pageno), 20, 160, paint);
			canvas.drawText( "Page:" + String.valueOf(m_cur_page), 20, 200, paint);
		}
	}
	//private float xSelStart = 0;
	//private float ySelStart = 0;
	public boolean onTouchEvent (MotionEvent event)
	{
		/*
		if (event.getActionMasked()==MotionEvent.ACTION_DOWN)
		{
			xSelStart=  event.getX();
			ySelStart=  event.getY();
		}
		if (event.getActionMasked()==MotionEvent.ACTION_MOVE)
		{
			float x= event.getX();
			float y= event.getY();
			m_viewer.viewSetSel(xSelStart, ySelStart, x, y);
		}
		if (event.getActionMasked()==MotionEvent.ACTION_UP || event.getActionMasked()==MotionEvent.ACTION_CANCEL)
		{
			float x= event.getX();
			float y= event.getY();
			m_viewer.viewSetSel(xSelStart, ySelStart, x, y);
			m_viewer.annotSetMarkup(0);
			m_viewer.annotEnd();
		}
		return true;
		*/
		if( m_viewer != null )
			return m_viewer.viewTouchEvent(event);
		else
			return true;
	}
	public void setAnnotListener( PDFView.PDFAnnotListener listener )
	{
		if( m_viewer != null )
			m_viewer.viewSetAnnotListener(listener);
	}
	public void setViewListener( PDFView.PDFViewListener listener )
	{
		if( m_viewer != null )
			m_viewer.viewSetViewListener(listener);
	}
	public String annotGetText()
	{
		if( m_viewer != null )
			return m_viewer.annotGetText();
		else
			return null;
	}
	public String annotGetSubject()
	{
		if( m_viewer != null )
			return m_viewer.annotGetSubject();
		else
			return null;
	}
	public boolean annotSetText(String txt)
	{
		if( m_viewer != null )
			return m_viewer.annotSetText(txt);
		else
			return false;
	}
	public boolean annotSetEditText(String txt)
	{
		if( m_viewer != null )
			return m_viewer.annotSetEditText(txt);
		else
			return false;
	}
	public boolean annotSetChoice(int item)
	{
		if( m_viewer != null )
			return m_viewer.annotSetChoice(item);
		else
			return false;
	}
	public boolean annotSetSubject(String subj)
	{
		if( m_viewer != null )
			return m_viewer.annotSetSubject(subj);
		else
			return false;
	}
	public void annotInk()
	{
		if( m_viewer != null )
			m_viewer.annotInk();
	}
	public void annotRect()
	{
		if( m_viewer != null )
			m_viewer.annotRect();
	}
	public void annotPerform()
	{
		if( m_viewer != null )
			m_viewer.annotPerform();
	}
	public void annotEnd()
	{
		if( m_viewer != null )
			m_viewer.annotEnd();
	}
	public void annotRemove()
	{
		if( m_viewer != null )
			m_viewer.annotRemove();
	}
	public void find(int dir)
	{
		if( m_viewer != null )
			m_viewer.viewFind(dir);
	}
	public void findStart(String str, boolean match_case, boolean whole_word)
	{
		if( m_viewer != null )
			m_viewer.viewFindStart(str, match_case, whole_word);
	}
	public void onInvalidate()
	{
		if( m_viewer != null )
			invalidate();
	}
	public void onFound( boolean found )
	{
		if( !found )
			Toast.makeText(getContext(), "no more found", Toast.LENGTH_SHORT).show();
	}
	public void onOpenURL(String url)
	{
		Toast.makeText(getContext(), "todo open url:" + url, Toast.LENGTH_LONG).show();
	}
	public void onSubmit(String target, String para)
	{
		Toast.makeText(getContext(), "todo open url:" + target + "\nparameters:" + para, Toast.LENGTH_LONG).show();
	}
	public void onPageChanged(int pageno)
	{
		m_cur_page = pageno;
		if( m_thumbView != null )
			m_thumbView.thumbGotoPage(pageno);
	}
	public void onSingleTap( float x, float y )
	{
	}
	public void onOpen3D(String file_name)
	{
	}
	public void onOpenMovie(String file_name)
	{
		File file = new File(file_name);
		file.delete();//you should delete the temporary file, after played
	}

	public void onOpenSound(int[] paras, String file_name)
	{
		if( paras[0] == 0 )//means format sound file, example: mp3/wav
		{
		}
		else//means unformatted sond data
		{
			//paras[0]: sample rate
			//paras[1]: channels number
			//paras[2]: sample bits
			//paras[3]: 0:raw   1:Signed(16 bits)   2:mu-law   3:a-law
		}
		File file = new File(file_name);
		file.delete();//you should delete the temporary file, after played
	}

	public void onOpenAttachment(String file_name)
	{
		File file = new File(file_name);
		file.delete();//you should delete the temporary file, after played
	}
	public void onSelectStart()
	{
	}
	public void onSelectEnd(String text)
	{
		//if( true ) return;
		lockResize(true);
		LinearLayout layout = (LinearLayout)LayoutInflater.from(getContext()).inflate(R.layout.dlg_text, null);
		final RadioGroup rad_group = (RadioGroup)layout.findViewById(R.id.rad_group);
		final String sel_text = text;

		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which)
			{
				if( rad_group.getCheckedRadioButtonId() == R.id.rad_copy )
					Toast.makeText(getContext(), "todo copy text:" + sel_text, Toast.LENGTH_SHORT).show();
				else if( m_viewer.viewGetDoc().CanSave() )
				{
					boolean ret = false;
					if( rad_group.getCheckedRadioButtonId() == R.id.rad_highlight )
						ret = m_viewer.annotSetMarkup(0);
					else if( rad_group.getCheckedRadioButtonId() == R.id.rad_underline )
						ret = m_viewer.annotSetMarkup(1);
					else if( rad_group.getCheckedRadioButtonId() == R.id.rad_strikeout )
						ret = m_viewer.annotSetMarkup(2);
					if( !ret )
						Toast.makeText(getContext(), "add annotation failed!", Toast.LENGTH_SHORT).show();
				}
				else
					Toast.makeText(getContext(), "can't write or encrypted!", Toast.LENGTH_SHORT).show();
				annotEnd();
				dialog.dismiss();
				lockResize(false);
			}});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which)
			{
				annotEnd();
				dialog.dismiss();
				lockResize(false);
			}});
		builder.setTitle("Process selected text");
		builder.setCancelable(false);
		builder.setView(layout);
		AlertDialog dlg = builder.create();
		dlg.show();
	}
	public void onPageDisplayed(PDFPageDispPara para)
	{
		Paint paint = new Paint();
		paint.setARGB(128, 255, 0, 0);
		float x = para.left + 20 * para.real_ratio;
		float y = para.top + 40 * para.real_ratio;
		para.canvas.drawText("Render time:" + para.render_time_span + " ms", x, y, paint);
		x = para.left + 12 * para.real_ratio;
		y = para.bottom - 12 * para.real_ratio;
		para.canvas.drawText("PNO:" + (para.pageno + 1), x, y, paint);
	}
	public void onHold()
	{
	}
	public void onPressDown()
	{
	}
	public void onPressUp()
	{
	}
	public void onClickPage(int pageno)
	{
		m_viewer.viewGotoPage(pageno);
	}
	public void onSelDisplayed(PDFView.PDFSelDispPara para)
	{
		if( para == null ) return;
		Paint paint = new Paint();
		paint.setARGB(255, 0, 0, 255);
		para.canvas.drawCircle(para.x1, para.y1, 5, paint);
		para.canvas.drawCircle(para.x2, para.y2, 5, paint);
	}
	public void lockResize(boolean lock)
	{
		if( m_lock_resize == lock ) return;
		m_lock_resize = lock;
		if( lock )
		{
			m_save_w = getWidth();
			m_save_h = getHeight();
		}
		else
		{
			m_viewer.viewResize(m_save_w, m_save_h);
		}
	}
}
