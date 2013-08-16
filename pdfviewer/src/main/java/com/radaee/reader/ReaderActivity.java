package com.radaee.reader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.radaee.pdf.Document;
import com.radaee.pdf.Global;
import com.radaee.util.PDFFileStream;

/**
 * Created with IntelliJ IDEA.
 * User: Erick
 * Date: 1/21/13
 * Time: 6:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReaderActivity extends Activity {

    private ReaderController m_vPDF = null;
    private Document doc = new Document();
    private PDFFileStream stream = new PDFFileStream();

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Global.Init(ReaderActivity.this);
        String pathToPDF = "/sdcard/ebook/pdf.pdf";

        m_vPDF = new ReaderController(this);

        if( pathToPDF != null )
        {
            doc.Close();

            //to open encrypted document, modify codes below
            stream.open(pathToPDF);
            int ret = doc.OpenStream(stream, null);

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
                    break;
                default://unknown error
                    finish();
                    break;
            }

            m_vPDF.open(doc);
        }
        setContentView( m_vPDF );
        //m_vPDF = new PDFSimple(ReaderActivity.this);
    }


    public void onDestroy()
    {
        if( m_vPDF != null )
        {
            m_vPDF.close();
            m_vPDF = null;
        }
        if( doc != null )
        {
        	doc.Close();
        	doc = null;
        }
        if( stream != null )
        {
        	stream.close();
        	stream = null;
        }
        Global.RemoveTmp();
        super.onDestroy();
    }

}
