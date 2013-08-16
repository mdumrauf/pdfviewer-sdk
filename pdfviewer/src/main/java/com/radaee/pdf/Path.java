package com.radaee.pdf;

/**
 * class for Path
 * @author louyongzhi<br/>
 * @see Example:<br/>
 *   Path path = new Path();<br/>
 *   path.MoveTo(0, 0);<br/>
 *   path.LineTo(10, 10);<br/>
 *   path.CurveTo(100, 0, 100, 100, 30, 70);<br/>
 *   path.ClosePath();<br/>
 *   int cnt = path.GetNodeCount();<br/>
 *   int cur = 0;<br/>
 *   float point[] = new float[2];<br/>
 *   while( cur < cnt )<br/>
 *   {<br/>
 *   	path.GetNode(cur, point);<br/>
 *   	cur++;<br/>
 *   }<br/>
 *   Page page = m_doc.GetPage(0);<br/>
 *   page.ObjsStart();<br/>
 *   Matrix mat = new Matrix( 1, 0, 0, -1, 0, m_doc.GetPageHeight(0) );<br/>
 *   page.AddAnnotGlyph(mat, path, 0xFF0000, true);<br/>
 *   page.Close();<br/>
 *   path.Destroy();<br/>
 */
public class Path
{
	private static native int create();
	private static native void destroy( int hand );
	private static native void moveTo( int hand, float x, float y );
	private static native void lineTo( int hand, float x, float y );
	private static native void curveTo( int hand, float x1, float y1, float x2, float y2, float x3, float y3 );
	private static native void closePath( int hand );
	private static native int getNodeCount(int hand);
	private static native int getNode( int hand, int index, float[]pt );
	protected int m_hand = create();
	/**
	 * move to operation
	 * @param x
	 * @param y
	 */
	public void MoveTo( float x, float y )
	{
		moveTo( m_hand, x, y );
	}
	/**
	 * line to operation
	 * @param x
	 * @param y
	 */
	public void LineTo( float x, float y )
	{
		lineTo( m_hand, x, y );
	}
	public void CurveTo( float x1, float y1, float x2, float y2, float x3, float y3 )
	{
		curveTo( m_hand, x1, y1, x2, y2, x3, y3 );
	}
	/**
	 * close a contour.
	 */
	public void ClosePath()
	{
		closePath(m_hand);
	}
	/**
	 * free memory
	 */
	public void Destroy()
	{
		destroy(m_hand);
		m_hand = 0;
	}
	public int GetNodeCount()
	{
		return getNodeCount( m_hand );
	}
	/**
	 * get each node
	 * @param index range [0, GetNodeCount() - 1]
	 * @param pt output value: 2 elements coordinate point
	 * @return node type:<br/>
	 * 0: move to<br/>
	 * 1: line to<br/>
	 * 3: curve to, index, index + 1, index + 2 are all data<br/>
	 * 4: close operation<br/>
	 */
	public int GetNode( int index, float pt[] )
	{
		return getNode( m_hand, index, pt );
	}
}
