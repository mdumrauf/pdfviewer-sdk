package com.radaee.util;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;

import com.radaee.pdf.Document.PDFStream;

import java.io.FileInputStream;

/**
 * Asset stream, an implement class for PDFStream, which used in Document.OpenStream
 * @author radaee
 */
public class PDFAssetStream implements PDFStream
{
	private AssetFileDescriptor m_impl;
	private FileInputStream m_stream;
	public boolean open( AssetManager assets, String symbol )
	{
		try
		{
			m_impl = assets.openFd(symbol);
			m_stream = new FileInputStream( m_impl.getFileDescriptor() );
			return true;
		}
		catch( Exception e )
		{
			return false;
		}
	}
	public void close()
	{
		try
		{
			m_stream.close();
			m_impl.close();
		}
		catch( Exception e )
		{
		}
		m_stream = null;
		m_impl = null;
	}
	public boolean writeable()
	{
		return false;
	}
	public int get_size()
	{
		try
		{
			int len = (int)m_impl.getLength();
			if( len < 0 ) return 0;
			else return len;
		}
		catch( Exception e )
		{
			Log.d("get_size", e.getMessage());
			return 0;
		}
	}

	public int read(byte[] data)
	{
		try
		{
			int len = m_stream.read(data);
			if( len < 0 )
				return 0;
			else
				return len;
		}
		catch( Exception e )
		{
			Log.d("read", e.getMessage());
			return 0;
		}
	}

	public int write(byte[] data)
	{
		return 0;
	}

	public void seek(int pos)
	{
		try
		{
			m_stream.reset();
			m_stream.skip(pos);
		}
		catch( Exception e )
		{
			Log.d("seek", e.getMessage());
		}
	}

	public int tell()
	{
		try
		{
		    return (int)m_stream.getChannel().position();
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		}
		return 0;
	}
}
