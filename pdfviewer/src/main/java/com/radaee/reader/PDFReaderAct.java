package com.radaee.reader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.radaee.pdf.Document;
import com.radaee.pdf.Global;
import com.radaee.pdf.Page;
import com.radaee.pdf.Page.Annotation;
import com.radaee.reader.PDFReader.PDFReaderListener;
import com.radaee.util.PDFGridItem;
import com.radaee.util.PDFGridView;
import com.radaee.util.PDFThumbView;
import com.radaee.view.PDFVPage;
import com.radaee.view.PDFViewThumb.PDFThumbListener;

public class PDFReaderAct extends Activity implements OnItemClickListener, OnClickListener, PDFReaderListener, PDFThumbListener
{
	private PDFGridView m_vFiles = null;
	private PDFReader m_reader = null;
	private PDFThumbView m_thumb = null;
	private RelativeLayout m_layout;
	private Document m_doc = new Document();
    private Button btn_ink;
    private Button btn_rect;
    private Button btn_oval;
    private Button btn_note;
    private Button btn_line;
    private Button btn_cancel;
    private Button btn_save;
    private Button btn_close;

    private Button btn_sel;
    private Button btn_act;
    private Button btn_edit;
    private Button btn_remove;
    
    private Button btn_prev;
    private Button btn_next;
    private EditText txt_find;
    private String str_find;
    private boolean m_set = false;
    private PDFVPage m_annot_vpage;
    private Annotation m_annot;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Global.Init( this );
		m_layout = (RelativeLayout)LayoutInflater.from(this).inflate(R.layout.reader, null);
		m_reader = (PDFReader)m_layout.findViewById(R.id.view);
		m_thumb = (PDFThumbView)m_layout.findViewById(R.id.thumbs);
		
		//m_doc = new Document();
		//m_doc.Open( pdf_path, password );
		//m_reader.PDFOpen(m_doc, false, this);
		//m_thumb.thumbOpen(m_doc, this);

        m_vFiles = new PDFGridView(this, null);
		m_vFiles.PDFSetRootPath("/mnt");
		m_vFiles.setOnItemClickListener(this);
		setContentView(m_vFiles);

        LinearLayout bar_cmd = (LinearLayout)m_layout.findViewById(R.id.bar_cmd);
        LinearLayout bar_act = (LinearLayout)m_layout.findViewById(R.id.bar_act);
        LinearLayout bar_find = (LinearLayout)m_layout.findViewById(R.id.bar_find);
        btn_ink = (Button)bar_cmd.findViewById(R.id.btn_ink);
        btn_rect = (Button)bar_cmd.findViewById(R.id.btn_rect);
        btn_oval = (Button)bar_cmd.findViewById(R.id.btn_oval);
        btn_note = (Button)bar_cmd.findViewById(R.id.btn_note);
        btn_line = (Button)bar_cmd.findViewById(R.id.btn_line);
        btn_cancel = (Button)bar_cmd.findViewById(R.id.btn_cancel);
        btn_save = (Button)bar_cmd.findViewById(R.id.btn_save);
        btn_close = (Button)bar_cmd.findViewById(R.id.btn_close);

        btn_sel = (Button)bar_act.findViewById(R.id.btn_sel);
        btn_act = (Button)bar_act.findViewById(R.id.btn_act);
        btn_edit = (Button)bar_act.findViewById(R.id.btn_edit);
        btn_remove = (Button)bar_act.findViewById(R.id.btn_remove);
        
        txt_find = (EditText)bar_find.findViewById(R.id.txt_find);
        btn_prev = (Button)bar_find.findViewById(R.id.btn_prev);
        btn_next = (Button)bar_find.findViewById(R.id.btn_next);

        btn_sel.setOnClickListener(this);
        btn_act.setOnClickListener(this);
        btn_edit.setOnClickListener(this);
        btn_remove.setOnClickListener(this);
 
        btn_ink.setOnClickListener(this);
        btn_rect.setOnClickListener(this);
        btn_oval.setOnClickListener(this);
        btn_note.setOnClickListener(this);
        btn_line.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_close.setOnClickListener(this);

        btn_prev.setOnClickListener(this);
        btn_next.setOnClickListener(this);

        btn_act.setEnabled(false);
        btn_save.setEnabled(false);
        btn_edit.setEnabled(false);
        btn_remove.setEnabled(false);
        
        btn_cancel.setEnabled(false);
    }
    protected void onDestroy()
    {
    	//m_vFiles.close();
    	if( m_vFiles != null )
    	{
    		m_vFiles.close();
    		m_vFiles = null;
    	}
    	if( m_thumb != null )
    	{
    		m_thumb.thumbClose();
    		m_thumb = null;
    	}
    	if( m_reader != null )
    		m_reader.PDFClose();
    	if( m_doc != null )
    		m_doc.Close();
    	Global.RemoveTmp();
    	super.onDestroy();
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
					/*
					{
						int pageno = 0;
						int pagecnt = m_doc.GetPageCount();
						for( pageno = 0; pageno < pagecnt; pageno++ )
						{
							Page page = m_doc.GetPage(pageno);
							page.ObjsStart();
							int cnt = page.ObjsGetCharCount();
							String txt = page.ObjsGetString(0, cnt);
							page.Close();
						}
					}
					*/
					m_reader.PDFOpen(m_doc, false, this);
					break;
				default://unknown error
					finish();
					break;
				}
				m_thumb.thumbOpen(m_reader.PDFGetDoc(), this);
	            setContentView(m_layout);
	            btn_ink.setEnabled(m_reader.PDFCanSave());
	            btn_rect.setEnabled(m_reader.PDFCanSave());
	            btn_oval.setEnabled(m_reader.PDFCanSave());
	            btn_note.setEnabled(m_reader.PDFCanSave());
	            btn_save.setEnabled(m_reader.PDFCanSave());
	            btn_line.setEnabled(m_reader.PDFCanSave());
			}
		}
		else
		{
		}
	}
	private void onSelect()
	{
		m_set = !m_set;
		m_reader.PDFSetSelect();
		btn_ink.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_rect.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_oval.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_note.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_line.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_cancel.setEnabled(false);

		btn_sel.setPressed(m_set);
        btn_act.setEnabled(false);
		btn_edit.setEnabled(false);
		btn_remove.setEnabled(false);
	}
	private void onInk()
	{
		m_set = !m_set;
		if( m_set )
			m_reader.PDFSetInk(0);
		else
			m_reader.PDFSetInk(1);
		btn_ink.setPressed(m_set);
		btn_rect.setEnabled(!m_set);
		btn_oval.setEnabled(!m_set);
		btn_note.setEnabled(!m_set);
		btn_line.setEnabled(!m_set);
		btn_cancel.setEnabled(m_set);
        btn_save.setEnabled(!m_set);

		btn_sel.setEnabled(!m_set);
        btn_act.setEnabled(!m_set);
		btn_edit.setEnabled(!m_set);
		btn_remove.setEnabled(!m_set);

        btn_prev.setEnabled(!m_set);
        btn_next.setEnabled(!m_set);
        txt_find.setEnabled(!m_set);
	}
	private void onRect()
	{
		m_set = !m_set;
		if( m_set )
			m_reader.PDFSetRect(0);
		else
			m_reader.PDFSetRect(1);
		btn_ink.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_rect.setPressed(m_set && m_reader.PDFCanSave());
		btn_oval.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_note.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_line.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_cancel.setEnabled(m_set);
        btn_save.setEnabled(!m_set);

		btn_sel.setEnabled(!m_set);
        btn_act.setEnabled(!m_set);
		btn_edit.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_remove.setEnabled(!m_set && m_reader.PDFCanSave());

        btn_prev.setEnabled(!m_set);
        btn_next.setEnabled(!m_set);
        txt_find.setEnabled(!m_set);
	}
	private void onOval()
	{
		m_set = !m_set;
		if( m_set )
			m_reader.PDFSetEllipse(0);
		else
			m_reader.PDFSetEllipse(1);
		btn_ink.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_rect.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_oval.setPressed(m_set && m_reader.PDFCanSave());
		btn_note.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_line.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_cancel.setEnabled(m_set);
        btn_save.setEnabled(!m_set);

		btn_sel.setEnabled(!m_set);
        btn_act.setEnabled(!m_set);
		btn_edit.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_remove.setEnabled(!m_set && m_reader.PDFCanSave());

        btn_prev.setEnabled(!m_set);
        btn_next.setEnabled(!m_set);
        txt_find.setEnabled(!m_set);
	}
	private void onNote()
	{
		m_reader.PDFSetNote();
		m_set = !m_set;
		btn_ink.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_rect.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_oval.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_note.setPressed(m_set && m_reader.PDFCanSave());
		btn_line.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_cancel.setEnabled(false);

		btn_sel.setEnabled(!m_set);
        btn_act.setEnabled(!m_set);
		btn_edit.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_remove.setEnabled(!m_set && m_reader.PDFCanSave());
	}
	private void onLine()
	{
		m_set = !m_set;
		if( m_set )
			m_reader.PDFSetLine(0);
		else
			m_reader.PDFSetLine(1);
		btn_ink.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_rect.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_oval.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_note.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_line.setPressed(m_set && m_reader.PDFCanSave());
		btn_cancel.setEnabled(false);

		btn_sel.setEnabled(!m_set);
        btn_act.setEnabled(!m_set);
		btn_edit.setEnabled(!m_set && m_reader.PDFCanSave());
		btn_remove.setEnabled(!m_set && m_reader.PDFCanSave());
	}
	private void onCancel()
	{
		m_reader.PDFCancel();
		m_set = false;
		btn_ink.setEnabled(m_reader.PDFCanSave());
		btn_rect.setEnabled(m_reader.PDFCanSave());
		btn_oval.setEnabled(m_reader.PDFCanSave());
		btn_note.setEnabled(m_reader.PDFCanSave());
		btn_line.setEnabled(m_reader.PDFCanSave());
		btn_cancel.setEnabled(false);
        btn_save.setEnabled(true);

		btn_sel.setEnabled(true);
        btn_act.setEnabled(true);
		btn_edit.setEnabled(m_reader.PDFCanSave());
		btn_remove.setEnabled(m_reader.PDFCanSave());

        btn_prev.setEnabled(true);
        btn_next.setEnabled(true);
        txt_find.setEnabled(true);
	}
	private void onFindPrev()
	{
		String str = txt_find.getText().toString();
		if( str_find != null )
		{
			if( str != null && str.compareTo(str_find) == 0 )
			{
				m_reader.PDFFind(-1);
				return;
			}
		}
		if( str != null && str.length() > 0 )
		{
			m_reader.PDFFindStart(str, false, false);
			m_reader.PDFFind(1);
			str_find = str;
		}
	}
	private void onFindNext()
	{
		String str = txt_find.getText().toString();
		if( str_find != null )
		{
			if( str != null && str.compareTo(str_find) == 0 )
			{
				m_reader.PDFFind(1);
				return;
			}
		}
		if( str != null && str.length() > 0 )
		{
			m_reader.PDFFindStart(str, false, false);
			m_reader.PDFFind(1);
			str_find = str;
		}
	}
	private void onEdit()
	{
		LinearLayout layout = (LinearLayout)LayoutInflater.from(this).inflate(R.layout.dlg_note, null);
		final EditText subj = (EditText)layout.findViewById(R.id.txt_subj);
		final EditText content = (EditText)layout.findViewById(R.id.txt_content);
		Page page = null;
		if( m_annot_vpage != null ) page = m_annot_vpage.GetPage();
		if( page == null ) return;

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which)
			{
				String str_subj = subj.getText().toString();
				String str_content = content.getText().toString();
				m_annot.SetPopupSubject(str_subj);
				m_annot.SetPopupText(str_content);
				dialog.dismiss();
				m_reader.PDFEndAnnot();
				m_set = false;
				btn_ink.setEnabled(m_reader.PDFCanSave());
				btn_rect.setEnabled(m_reader.PDFCanSave());
				btn_oval.setEnabled(m_reader.PDFCanSave());
				btn_note.setEnabled(m_reader.PDFCanSave());
				btn_line.setEnabled(m_reader.PDFCanSave());
				btn_cancel.setEnabled(false);
		        btn_save.setEnabled(true);

				btn_sel.setEnabled(true);
		        btn_act.setEnabled(false);
				btn_edit.setEnabled(false);
				btn_remove.setEnabled(false);

		        btn_prev.setEnabled(true);
		        btn_next.setEnabled(true);
		        txt_find.setEnabled(true);
			}});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				m_reader.PDFEndAnnot();
				m_set = false;
				btn_ink.setEnabled(m_reader.PDFCanSave());
				btn_rect.setEnabled(m_reader.PDFCanSave());
				btn_oval.setEnabled(m_reader.PDFCanSave());
				btn_note.setEnabled(m_reader.PDFCanSave());
				btn_line.setEnabled(m_reader.PDFCanSave());
				btn_cancel.setEnabled(false);
		        btn_save.setEnabled(true);

				btn_sel.setEnabled(true);
		        btn_act.setEnabled(false);
				btn_edit.setEnabled(false);
				btn_remove.setEnabled(false);

		        btn_prev.setEnabled(true);
		        btn_next.setEnabled(true);
		        txt_find.setEnabled(true);
			}});
		builder.setTitle("Note Content");
		builder.setCancelable(false);
		builder.setView(layout);
		
		subj.setText(m_annot.GetPopupSubject());
		content.setText(m_annot.GetPopupText());
		AlertDialog dlg = builder.create();
		dlg.show();
	}
	private void onAct()
	{
		m_reader.PDFPerformAnnot();
		m_set = false;
		btn_ink.setEnabled(m_reader.PDFCanSave());
		btn_rect.setEnabled(m_reader.PDFCanSave());
		btn_oval.setEnabled(m_reader.PDFCanSave());
		btn_note.setEnabled(m_reader.PDFCanSave());
		btn_line.setEnabled(m_reader.PDFCanSave());
		btn_cancel.setEnabled(false);
        btn_save.setEnabled(true);

		btn_sel.setEnabled(true);
        btn_act.setEnabled(false);
		btn_edit.setEnabled(false);
		btn_remove.setEnabled(false);

        btn_prev.setEnabled(true);
        btn_next.setEnabled(true);
        txt_find.setEnabled(true);
	}
	private void onRemove()
	{
		m_reader.PDFRemoveAnnot();
		m_set = false;
		btn_ink.setEnabled(m_reader.PDFCanSave());
		btn_rect.setEnabled(m_reader.PDFCanSave());
		btn_oval.setEnabled(m_reader.PDFCanSave());
		btn_note.setEnabled(m_reader.PDFCanSave());
		btn_line.setEnabled(m_reader.PDFCanSave());
		btn_cancel.setEnabled(false);
        btn_save.setEnabled(true);

		btn_sel.setEnabled(true);
        btn_act.setEnabled(false);
		btn_edit.setEnabled(false);
		btn_remove.setEnabled(false);

        btn_prev.setEnabled(true);
        btn_next.setEnabled(true);
        txt_find.setEnabled(true);
	}
	public void onClick(View v)
	{
        int i = v.getId();
        if (i == R.id.btn_ink) {
            onInk();

        } else if (i == R.id.btn_rect) {
            onRect();

        } else if (i == R.id.btn_oval) {
            onOval();

        } else if (i == R.id.btn_note) {
            onNote();

        } else if (i == R.id.btn_line) {
            onLine();

        } else if (i == R.id.btn_cancel) {
            onCancel();

        } else if (i == R.id.btn_save) {
            m_reader.PDFSave();

        } else if (i == R.id.btn_sel) {
            onSelect();

        } else if (i == R.id.btn_remove) {
            onRemove();

        } else if (i == R.id.btn_act) {
            onAct();

        } else if (i == R.id.btn_edit) {
            onEdit();

        } else if (i == R.id.btn_prev) {
            onFindPrev();

        } else if (i == R.id.btn_next) {
            onFindNext();

        } else if (i == R.id.btn_close) {
            m_thumb.thumbClose();
            m_reader.PDFClose();
            if (m_doc != null) m_doc.Close();
            str_find = null;
            setContentView(m_vFiles);

        }
	}
	public void OnPageClicked(int pageno)
	{
		m_reader.PDFGotoPage(pageno);
	}
	public void OnPageChanged(int pageno)
	{
		m_thumb.thumbGotoPage(pageno);
	}
	public void OnAnnotClicked(PDFVPage vpage, Annotation annot)
	{
		m_annot_vpage = vpage;
		m_annot = annot;
		btn_ink.setEnabled(annot == null && m_reader.PDFCanSave());
		btn_rect.setEnabled(annot == null && m_reader.PDFCanSave());
		btn_oval.setEnabled(annot == null && m_reader.PDFCanSave());
		btn_note.setEnabled(annot == null && m_reader.PDFCanSave());
		btn_line.setEnabled(annot == null && m_reader.PDFCanSave());
		btn_cancel.setEnabled(false);
		btn_save.setEnabled(annot == null);

		btn_sel.setEnabled(annot == null);
        btn_act.setEnabled(annot != null);
		btn_edit.setEnabled(annot != null && m_reader.PDFCanSave());
		btn_remove.setEnabled(annot != null && m_reader.PDFCanSave());

		btn_prev.setEnabled(annot == null);
        btn_next.setEnabled(annot == null);
        txt_find.setEnabled(annot == null);
	}
	public void OnOpenURI(String uri)
	{
	}
	public void OnOpenMovie(String path)
	{
	}
	public void OnOpenSound(int[] paras, String path)
	{
	}
	public void OnOpenAttachment(String path)
	{
	}
	public void OnOpen3D(String path)
	{
	}
	public void OnSelectEnd(String text)
	{
		LinearLayout layout = (LinearLayout)LayoutInflater.from(this).inflate(R.layout.dlg_text, null);
		final RadioGroup rad_group = (RadioGroup)layout.findViewById(R.id.rad_group);
		final String sel_text = text;

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				if( rad_group.getCheckedRadioButtonId() == R.id.rad_copy )
					Toast.makeText(PDFReaderAct.this, "todo copy text:" + sel_text, Toast.LENGTH_SHORT).show();
				else if( m_reader.PDFCanSave() )
				{
					boolean ret = false;
					if( rad_group.getCheckedRadioButtonId() == R.id.rad_highlight )
						ret = m_reader.PDFSetSelMarkup(0);
					else if( rad_group.getCheckedRadioButtonId() == R.id.rad_underline )
						ret = m_reader.PDFSetSelMarkup(1);
					else if( rad_group.getCheckedRadioButtonId() == R.id.rad_strikeout )
						ret = m_reader.PDFSetSelMarkup(2);
					else if( rad_group.getCheckedRadioButtonId() == R.id.rad_squiggly )
						ret = m_reader.PDFSetSelMarkup(4);
					if( !ret )
						Toast.makeText(PDFReaderAct.this, "add annotation failed!", Toast.LENGTH_SHORT).show();
				}
				else
					Toast.makeText(PDFReaderAct.this, "can't write or encrypted!", Toast.LENGTH_SHORT).show();
				onSelect();
				dialog.dismiss();
			}});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}});
		builder.setTitle("Process selected text");
		builder.setCancelable(false);
		builder.setView(layout);
		AlertDialog dlg = builder.create();
		dlg.show();
	}
	public void OnPageModified(int pageno)
	{
		m_thumb.thumbUpdatePage(pageno);
	}
}
