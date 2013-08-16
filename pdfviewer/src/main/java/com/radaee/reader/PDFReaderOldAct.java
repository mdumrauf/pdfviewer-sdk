package com.radaee.reader;

import com.radaee.pdfex.*;
import com.radaee.pdf.*;
import com.radaee.util.ComboList;
import com.radaee.util.PDFGridItem;
import com.radaee.util.PDFGridView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PDFReaderOldAct extends Activity implements OnItemClickListener, OnClickListener, PDFView.PDFAnnotListener, PopupWindow.OnDismissListener
{
	private PDFGridView m_vFiles = null;
	private Document m_doc = new Document();
	private PDFReaderOld m_vPDF = null;
	private ThumbView m_vThumb = null;
	private PopupWindow m_pEdit = null;
	private PopupWindow m_pCombo = null;
    private Button btn_ink;
    private Button btn_rect;
    private Button btn_end;
    private Button btn_remove;
    private Button btn_save;
    private Button btn_prev;
    private Button btn_next;
    private Button btn_act;
    private Button btn_saveas;
    private Button btn_edit;
    private Button btn_close;
    private EditText txt_find;
    private String str_find;
    private int edit_type = 0;
    private int sel_index = -1;
    private boolean m_modified = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Global.Init( this );
		m_vFiles = new PDFGridView(this, null);
		//m_vFiles.setOnChildClickListener(this); 
		m_vFiles.PDFSetRootPath("/mnt");
		m_vFiles.setOnItemClickListener(this);
		m_pEdit = new PopupWindow(LayoutInflater.from(this).inflate(R.layout.pop_edit, null) );
		m_pCombo = new PopupWindow(LayoutInflater.from(this).inflate(R.layout.pop_combo, null));
		m_pEdit.setOnDismissListener(this);
		m_pCombo.setOnDismissListener(this);
		m_pEdit.setFocusable(true);
		m_pEdit.setTouchable(true);
		BitmapDrawable bitmap = new BitmapDrawable();//add back
		m_pEdit.setBackgroundDrawable(bitmap);
		m_pCombo.setFocusable(true);
		m_pCombo.setTouchable(true);
		m_pCombo.setBackgroundDrawable(bitmap);
        setContentView(m_vFiles);
    }
    protected void onDestroy()
    {
    	//m_vFiles.close();
    	if( m_vThumb != null )
    		m_vThumb.thumbClose();
    	if( m_vFiles != null )
    	{
    		m_vFiles.close();
    		m_vFiles = null;
    	}
    	if( m_vPDF != null )
    	{
    		m_vPDF.set_thumb(null);
    		m_vPDF.close();
    		m_vPDF = null;
    	}
    	if( m_modified )
    	{
    		//check if need save?
    		//m_doc.Save();
    		m_modified = false;
    	}
    	if( m_doc != null )
    	{
    		m_doc.Close();
    		m_doc = null;
    	}
    	Global.RemoveTmp();
    	super.onDestroy();
    }
	public void onClick(View v)
	{
        int i = v.getId();
        if (i == R.id.btn_ink) {
            btn_ink.setEnabled(false);
            btn_rect.setEnabled(false);
            btn_remove.setEnabled(true);
            btn_end.setEnabled(true);
            btn_act.setEnabled(false);
            btn_save.setEnabled(false);
            btn_prev.setEnabled(false);
            btn_next.setEnabled(false);
            btn_edit.setEnabled(false);
            m_vPDF.annotInk();

        } else if (i == R.id.btn_rect) {
            btn_rect.setEnabled(false);
            btn_ink.setEnabled(false);
            btn_remove.setEnabled(true);
            btn_end.setEnabled(true);
            btn_act.setEnabled(false);
            btn_save.setEnabled(false);
            btn_prev.setEnabled(false);
            btn_next.setEnabled(false);
            btn_edit.setEnabled(false);
            m_vPDF.annotRect();

        } else if (i == R.id.btn_end) {
            btn_ink.setEnabled(true);
            btn_rect.setEnabled(true);
            btn_end.setEnabled(false);
            btn_remove.setEnabled(false);
            btn_act.setEnabled(false);
            btn_save.setEnabled(false);
            btn_edit.setEnabled(false);
            m_vPDF.annotEnd();

        } else if (i == R.id.btn_remove) {
            btn_ink.setEnabled(true);
            btn_rect.setEnabled(true);
            btn_end.setEnabled(false);
            btn_remove.setEnabled(false);
            btn_act.setEnabled(false);
            btn_save.setEnabled(false);
            btn_edit.setEnabled(false);
            m_vPDF.annotRemove();

        } else if (i == R.id.btn_save) {
            btn_ink.setEnabled(true);
            btn_rect.setEnabled(true);
            btn_end.setEnabled(false);
            btn_act.setEnabled(false);
            btn_save.setEnabled(false);
            btn_remove.setEnabled(false);
            btn_edit.setEnabled(false);
            m_vPDF.annotEnd();
            m_doc.Save();//save to file
            m_modified = false;

        } else if (i == R.id.btn_act) {
            btn_ink.setEnabled(true);
            btn_rect.setEnabled(true);
            btn_end.setEnabled(false);
            btn_act.setEnabled(false);
            btn_save.setEnabled(false);
            btn_remove.setEnabled(false);
            btn_edit.setEnabled(false);
            m_vPDF.annotPerform();

        } else if (i == R.id.btn_edit) {
            btn_ink.setEnabled(true);
            btn_rect.setEnabled(true);
            btn_end.setEnabled(false);
            btn_act.setEnabled(false);
            btn_save.setEnabled(false);
            btn_remove.setEnabled(false);
            btn_edit.setEnabled(false);
            m_vPDF.lockResize(true);
            LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.dlg_note, null);
            final EditText subj = (EditText) layout.findViewById(R.id.txt_subj);
            final EditText content = (EditText) layout.findViewById(R.id.txt_content);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String str_subj = subj.getText().toString();
                    String str_content = content.getText().toString();
                    m_vPDF.annotSetSubject(str_subj);
                    m_vPDF.annotSetText(str_content);
                    m_vPDF.annotEnd();
                    dialog.dismiss();
                    m_vPDF.lockResize(false);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    m_vPDF.annotEnd();
                    dialog.dismiss();
                    m_vPDF.lockResize(false);
                }
            });
            builder.setTitle("Note Content");
            builder.setCancelable(false);
            builder.setView(layout);
            AlertDialog dlg = builder.create();
            subj.setText(m_vPDF.annotGetSubject());
            content.setText(m_vPDF.annotGetText());
            dlg.show();

        } else if (i == R.id.btn_prev) {
            String str = txt_find.getText().toString();
            if (str_find != null) {
                if (str != null && str.compareTo(str_find) == 0) {
                    m_vPDF.find(-1);
                }
            }
            if (str != null && str.length() > 0) {
                str_find = str;
                m_vPDF.findStart(str, true, false);
                m_vPDF.find(-1);
            }

        } else if (i == R.id.btn_next) {
            String str = txt_find.getText().toString();
            if (str_find != null) {
                if (str != null && str.compareTo(str_find) == 0) {
                    m_vPDF.find(1);
                }
            }
            if (str != null && str.length() > 0) {
                str_find = str;
                m_vPDF.findStart(str, true, false);
                m_vPDF.find(1);
            }

        } else if (i == R.id.btn_saveas) {

        } else if (i == R.id.btn_close) {
            if (m_vThumb != null)
                m_vThumb.thumbClose();
            if (m_vPDF != null)
                m_vPDF.close();
            if (m_modified) {
                //check if need save?
                //m_doc.Save();
                m_modified = false;
            }
            if (m_doc != null) {
                m_doc.Close();
                str_find = null;
            }
            setContentView(m_vFiles);

        }
	}
	public void onAnnotDragStart(boolean has_goto, boolean has_popup)
	{
		btn_ink.setEnabled(false);
		btn_rect.setEnabled(false);
        btn_end.setEnabled(false);
        btn_save.setEnabled(false);
        btn_act.setEnabled(has_goto);
		btn_remove.setEnabled(true);
        btn_prev.setEnabled(false);
        btn_next.setEnabled(false);
        btn_edit.setEnabled(has_popup);
	}
	public void onAnnotEditBox(int type, String val, float text_size, float left, float top, float right, float bottom)
	{
		m_vPDF.lockResize(true);
		int[] location = new int[2];  
		m_vPDF.getLocationOnScreen(location);
		m_pEdit.setWidth((int)(right - left));
		m_pEdit.setHeight((int)(bottom - top));
		EditText edit = (EditText)m_pEdit.getContentView().findViewById(R.id.annot_text);
		edit.setBackgroundColor(0xFFFFFFC0);
		edit.setTextSize(TypedValue.COMPLEX_UNIT_PX, text_size);
		edit.setPadding(2, 2, 2, 2);
		switch( type )
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
		edit.setText(val);
		edit_type = 1;
		m_pEdit.showAtLocation(m_vPDF, Gravity.NO_GRAVITY, (int)left + location[0], (int)top + location[1]);
	}
	public void onAnnotComboBox(int sel, String[] opts, float left, float top, float right, float bottom)
	{
		m_vPDF.lockResize(true);
		int[] location = new int[2];  
		m_vPDF.getLocationOnScreen(location);
		m_pCombo.setWidth((int)(right - left));
		if( (bottom - top - 4) * opts.length > 250 )
			m_pCombo.setHeight(250);
		else
			m_pCombo.setHeight((int)(bottom - top - 4) * opts.length);
		ComboList combo = (ComboList)m_pCombo.getContentView().findViewById(R.id.annot_combo);
		combo.set_opts(opts);
		combo.setOnItemClickListener(this);
		edit_type = 2;
		sel_index = -1;
		m_pCombo.showAtLocation(m_vPDF, Gravity.NO_GRAVITY, (int)left + location[0], (int)top + location[1]);
	}
	public void onAnnotEnd()
	{
		btn_ink.setEnabled(true);
		btn_rect.setEnabled(true);
        btn_end.setEnabled(false);
		btn_remove.setEnabled(false);
		btn_act.setEnabled(false);
        btn_save.setEnabled(m_modified);
        btn_prev.setEnabled(true);
        btn_next.setEnabled(true);
        btn_edit.setEnabled(false);
	}

	public void onAnnotUpdate()
	{
		m_modified = true;
		btn_ink.setEnabled(true);
		btn_rect.setEnabled(true);
        btn_end.setEnabled(false);
		btn_remove.setEnabled(false);
		btn_act.setEnabled(false);
        btn_save.setEnabled(m_modified);
        btn_prev.setEnabled(true);
        btn_next.setEnabled(true);
        btn_edit.setEnabled(false);
	}
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		if( arg0 == m_vFiles )
		{
			PDFGridItem item = (PDFGridItem)arg1;
			if( item.is_dir() )
			{
				m_vFiles.PDFGotoSubdir(item.get_name());
			}
			else
			{
				m_doc.Close();
				int ret = item.open_doc(m_doc, null);
				//to open encrypted document modify codes below:
				switch( ret )
				{
				case -1://need input password
					finish();
					break;
				case -2://unknown encryption
					finish();
					break;
				case -3://damaged or invalid format
					finish();
					break;
				case -10://access denied or invalid file path
					finish();
					break;
				case 0://succeeded, and continue
					m_doc.SetCache( Global.tmp_path + "/temp.dat" );//set temporary cache for editing.
					break;
				default://unknown error
					finish();
					break;
				}
				/*
				Page page = m_doc.GetPage(0);
				page.ObjsStart();
				float[] rect = new float[4];
				rect[0] = 0;
				rect[1] = 0;
				rect[2] = 100;
				rect[3] = 100;
				page.AddAnnotBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.file03), false, rect);
				m_doc.Save();
				*/
					
	            String msg = "version:";
	            msg += m_doc.GetMeta("ver");//PDF-1.X
	            msg += "\npage count:";
	            msg += String.format("%d", m_doc.GetPageCount());
	            msg += "\n";
	            msg += "\nTitle:";
	            msg += m_doc.GetMeta("Title");
	            msg += "\nAuthor:";
	            msg += m_doc.GetMeta("Author");
	            msg += "\nCreator:";
	            msg += m_doc.GetMeta("Producer");
	            msg += "\nProducer:";
	            msg += m_doc.GetMeta("Creator");
	            Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
	            toast.show();
	            
	            //expand_outline(0);
	            
				RelativeLayout lout = (RelativeLayout)LayoutInflater.from(this).inflate(R.layout.main, null);
	            setContentView( lout );
	            m_vPDF = (PDFReaderOld)lout.findViewById(R.id.PDFView);
	            m_vThumb = (ThumbView)lout.findViewById(R.id.PDFThumb);
	            m_vPDF.open(m_doc);
	            m_vThumb.thumbOpen(m_vPDF.get_viewer(), m_vPDF);
	            m_vPDF.setAnnotListener(this);
	            m_vPDF.setViewListener(m_vPDF);
	            m_vPDF.set_thumb(m_vThumb);
	            LinearLayout bar_cmd = (LinearLayout)lout.findViewById(R.id.bar_cmd);
	            LinearLayout bar_find = (LinearLayout)lout.findViewById(R.id.bar_find);
	            btn_ink = (Button)bar_cmd.findViewById(R.id.btn_ink);
	            btn_rect = (Button)bar_cmd.findViewById(R.id.btn_rect);
	            btn_end = (Button)bar_cmd.findViewById(R.id.btn_end);
	            btn_remove = (Button)bar_cmd.findViewById(R.id.btn_remove);
	            btn_save = (Button)bar_cmd.findViewById(R.id.btn_save);
	            btn_act = (Button)bar_cmd.findViewById(R.id.btn_act);
	            btn_edit = (Button)bar_cmd.findViewById(R.id.btn_edit);
	            btn_close = (Button)bar_cmd.findViewById(R.id.btn_close);

	            txt_find = (EditText)bar_find.findViewById(R.id.txt_find);
	            btn_prev = (Button)bar_find.findViewById(R.id.btn_prev);
	            btn_next = (Button)bar_find.findViewById(R.id.btn_next);
	            btn_saveas = (Button)bar_find.findViewById(R.id.btn_saveas);
	            
	            btn_ink.setOnClickListener(this);
	            btn_rect.setOnClickListener(this);
	            btn_end.setOnClickListener(this);
	            btn_remove.setOnClickListener(this);
	            btn_save.setOnClickListener(this);
	            btn_act.setOnClickListener(this);
	            btn_prev.setOnClickListener(this);
	            btn_next.setOnClickListener(this);
	            btn_saveas.setOnClickListener(this);
	            btn_edit.setOnClickListener(this);
	            btn_close.setOnClickListener(this);

	            if( !m_doc.CanSave() )
	            {
	            	btn_ink.setEnabled(false);
	            	btn_rect.setEnabled(false);
	            }
	            btn_act.setEnabled(false);
	            btn_end.setEnabled(false);
	            btn_remove.setEnabled(false);
	            btn_save.setEnabled(false);
	            btn_edit.setEnabled(false);
	            btn_saveas.setEnabled(m_doc.IsEncrypted());
			}
		}
		else
		{
			sel_index = arg2;
			m_pCombo.dismiss();
		}
	}
	public void onDismiss()
	{
		if( edit_type == 1 )//edit box
		{
			EditText edit = (EditText)m_pEdit.getContentView().findViewById(R.id.annot_text);
			m_vPDF.annotSetEditText(edit.getText().toString());
			m_vPDF.lockResize(false);
		}
		if( edit_type == 2 )//combo
		{
			if( sel_index >= 0 )
				m_vPDF.annotSetChoice(sel_index);
			else
				m_vPDF.annotEnd();
			sel_index = -1;
			m_vPDF.lockResize(false);
		}
		edit_type = 0;
	}
	public void onLowMemory()
	{
		int iiii = 0;
	}
}