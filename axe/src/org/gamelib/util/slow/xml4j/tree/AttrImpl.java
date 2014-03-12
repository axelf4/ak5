/**
 * 
 */
package org.gamelib.util.slow.xml4j.tree;

import org.gamelib.util.slow.xml4j.Attr;

/**
 * @author pwnedary
 */
public class AttrImpl implements Attr {

	private String qName;
	private String value;
	private boolean id;

	/**
	 * 
	 */
	public AttrImpl(String qName, String value) {
		this.setQName(qName);
		this.setValue(value);
	}

	/** {@inheritDoc} */
	@Override
	public void setQName(String qName) {
		this.qName = qName;
	}

	/** {@inheritDoc} */
	@Override
	public String getQName() {
		return qName;
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(String value) {
		this.value = value;
	}

	/** {@inheritDoc} */
	@Override
	public String getValue() {
		return value;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isId() {
		return "id".equals(getQName()) || "ID".equals(getQName());
	}

}
