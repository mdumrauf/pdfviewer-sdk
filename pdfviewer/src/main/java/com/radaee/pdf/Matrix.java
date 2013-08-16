package com.radaee.pdf;

/**
class for PDF Matrix.
@author Radaee
@version 1.1
*/
public class Matrix
{
	protected int hand = 0;
	private static native int create( float xx, float yx, float xy, float yy, float x0, float y0 );
	private static native int createScale( float sx, float sy, float x0, float y0 );
	private static native void invert( int matrix );
	private static native void transformPath( int matrix, int path );
	private static native void transformInk( int matrix, int ink );
	private static native void transformRect( int matrix, float[] rect );
	private static native void transformPoint( int matrix, float[] point );
	private static native void destroy( int matrix );
	/**
	 * constructor for full values.
	 * @param xx
	 * @param yx
	 * @param xy
	 * @param yy
	 * @param x0
	 * @param y0
	 */
	public Matrix( float xx, float yx, float xy, float yy, float x0, float y0 )
	{
		hand = create( xx, yx, xy, yy, x0, y0 );
	}
	/**
	 * constructor for scaled values.<br/>
	 * xx = sx;<br/>
	 * yx = 0;<br/>
	 * xy = 0;<br/>
	 * yx = sy;
	 * @param sx
	 * @param sy
	 * @param x0
	 * @param y0
	 */
	public Matrix( float sx, float sy, float x0, float y0 )
	{
		hand = createScale( sx, sy, x0, y0 );
	}
	public void Invert()
	{
		invert( hand );
	}
	public void TransformPath( Path path )
	{
		transformPath( hand, path.m_hand );
	}
	public void TransformInk( Ink ink )
	{
		transformInk( hand, ink.hand );
	}
	public void TransformRect( float[] rect )
	{
		transformRect( hand, rect );
	}
	public void TransformPoint( float[] point )
	{
		transformPoint( hand, point );
	}
	/**
	 * destroy and free memory.
	 */
	public void Destroy()
	{
		destroy( hand );
		hand = 0;
	}
}
