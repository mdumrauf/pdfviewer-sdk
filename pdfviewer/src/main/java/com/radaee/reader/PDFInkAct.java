package com.radaee.reader;

import com.radaee.pdf.*;

import android.app.Activity;
import android.os.Bundle;

/**
 * test activity for PDFInk<br/>
 * PDFInk test HWriting class.
 * @author Radaee
 */
public class PDFInkAct extends Activity
{
	private PDFInk m_vInk = null;
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Global.Init( this );
        m_vInk = new PDFInk(this);
		setContentView(m_vInk);
    }
    protected void onDestroy()
    {
    	Global.RemoveTmp();
    	super.onDestroy();
    }
}