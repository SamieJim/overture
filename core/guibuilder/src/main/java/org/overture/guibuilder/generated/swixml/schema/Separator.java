//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2-70- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.04.08 at 06:53:28 PM WEST 
//

package org.overture.guibuilder.generated.swixml.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;any/>
 *       &lt;/sequence>
 *       &lt;attribute name="orientation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="defaultlocale" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="visible" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="foreground" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="background" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="font" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="preferredsize" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="minimumsize" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="maximumsize" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="alignmentx" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="alignmenty" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="inheritspopupmenu" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="nextfocusablecomponent" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="requestfocusenabled" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="verifyinputwhenfocustarget" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="border" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="debuggraphicsoptions" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tooltiptext" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="autoscrolls" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="opaque" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="doublebuffered" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="layout" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="focuscycleroot" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="focustraversalpolicyprovider" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="locale" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="location" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="bounds" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ignorerepaint" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="focusable" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="focustraversalkeysenabled" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="constraints" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="plaf" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="bundle" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="refid" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="use" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="include" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="initclass" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="action" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="macos_preferences" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="macos_about" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="macos_quit" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="macos_openapp" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="macos_openfile" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="macos_print" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="macos_reopen" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "content" })
@XmlRootElement(name = "separator")
public class Separator
{

	@XmlMixed
	@XmlAnyElement(lax = true)
	protected List<Object> content;
	@XmlAttribute(name = "orientation")
	protected String orientation;
	@XmlAttribute(name = "defaultlocale")
	protected String defaultlocale;
	@XmlAttribute(name = "visible")
	protected Boolean visible;
	@XmlAttribute(name = "enabled")
	protected Boolean enabled;
	@XmlAttribute(name = "foreground")
	protected String foreground;
	@XmlAttribute(name = "background")
	protected String background;
	@XmlAttribute(name = "font")
	protected String font;
	@XmlAttribute(name = "preferredsize")
	protected String preferredsize;
	@XmlAttribute(name = "minimumsize")
	protected String minimumsize;
	@XmlAttribute(name = "maximumsize")
	protected String maximumsize;
	@XmlAttribute(name = "alignmentx")
	protected String alignmentx;
	@XmlAttribute(name = "alignmenty")
	protected String alignmenty;
	@XmlAttribute(name = "inheritspopupmenu")
	protected Boolean inheritspopupmenu;
	@XmlAttribute(name = "nextfocusablecomponent")
	protected String nextfocusablecomponent;
	@XmlAttribute(name = "requestfocusenabled")
	protected Boolean requestfocusenabled;
	@XmlAttribute(name = "verifyinputwhenfocustarget")
	protected Boolean verifyinputwhenfocustarget;
	@XmlAttribute(name = "border")
	protected String border;
	@XmlAttribute(name = "debuggraphicsoptions")
	protected String debuggraphicsoptions;
	@XmlAttribute(name = "tooltiptext")
	protected String tooltiptext;
	@XmlAttribute(name = "autoscrolls")
	protected Boolean autoscrolls;
	@XmlAttribute(name = "opaque")
	protected Boolean opaque;
	@XmlAttribute(name = "doublebuffered")
	protected Boolean doublebuffered;
	@XmlAttribute(name = "layout")
	protected String layout;
	@XmlAttribute(name = "focuscycleroot")
	protected Boolean focuscycleroot;
	@XmlAttribute(name = "focustraversalpolicyprovider")
	protected Boolean focustraversalpolicyprovider;
	@XmlAttribute(name = "name")
	protected String name;
	@XmlAttribute(name = "size")
	protected String size;
	@XmlAttribute(name = "locale")
	protected String locale;
	@XmlAttribute(name = "location")
	protected String location;
	@XmlAttribute(name = "bounds")
	protected String bounds;
	@XmlAttribute(name = "ignorerepaint")
	protected Boolean ignorerepaint;
	@XmlAttribute(name = "focusable")
	protected Boolean focusable;
	@XmlAttribute(name = "focustraversalkeysenabled")
	protected Boolean focustraversalkeysenabled;
	@XmlAttribute(name = "constraints")
	protected String constraints;
	@XmlAttribute(name = "plaf")
	protected String plaf;
	@XmlAttribute(name = "bundle")
	protected String bundle;
	@XmlAttribute(name = "id")
	protected String id;
	@XmlAttribute(name = "refid")
	protected String refid;
	@XmlAttribute(name = "use")
	protected String use;
	@XmlAttribute(name = "include")
	protected String include;
	@XmlAttribute(name = "initclass")
	protected String initclass;
	@XmlAttribute(name = "action")
	protected String action;
	@XmlAttribute(name = "macos_preferences")
	protected String macosPreferences;
	@XmlAttribute(name = "macos_about")
	protected String macosAbout;
	@XmlAttribute(name = "macos_quit")
	protected String macosQuit;
	@XmlAttribute(name = "macos_openapp")
	protected String macosOpenapp;
	@XmlAttribute(name = "macos_openfile")
	protected String macosOpenfile;
	@XmlAttribute(name = "macos_print")
	protected String macosPrint;
	@XmlAttribute(name = "macos_reopen")
	protected String macosReopen;

	/**
	 * Gets the value of the content property.
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
	 * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
	 * the content property.
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getContent().add(newItem);
	 * </pre>
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String } {@link Object }
	 * @return 
	 */
	public List<Object> getContent()
	{
		if (content == null)
		{
			content = new ArrayList<Object>();
		}
		return this.content;
	}

	/**
	 * Gets the value of the orientation property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getOrientation()
	{
		return orientation;
	}

	/**
	 * Sets the value of the orientation property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setOrientation(String value)
	{
		this.orientation = value;
	}

	/**
	 * Gets the value of the defaultlocale property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getDefaultlocale()
	{
		return defaultlocale;
	}

	/**
	 * Sets the value of the defaultlocale property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setDefaultlocale(String value)
	{
		this.defaultlocale = value;
	}

	/**
	 * Gets the value of the visible property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isVisible()
	{
		return visible;
	}

	/**
	 * Sets the value of the visible property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 */
	public void setVisible(Boolean value)
	{
		this.visible = value;
	}

	/**
	 * Gets the value of the enabled property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isEnabled()
	{
		return enabled;
	}

	/**
	 * Sets the value of the enabled property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 */
	public void setEnabled(Boolean value)
	{
		this.enabled = value;
	}

	/**
	 * Gets the value of the foreground property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getForeground()
	{
		return foreground;
	}

	/**
	 * Sets the value of the foreground property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setForeground(String value)
	{
		this.foreground = value;
	}

	/**
	 * Gets the value of the background property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getBackground()
	{
		return background;
	}

	/**
	 * Sets the value of the background property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setBackground(String value)
	{
		this.background = value;
	}

	/**
	 * Gets the value of the font property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getFont()
	{
		return font;
	}

	/**
	 * Sets the value of the font property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setFont(String value)
	{
		this.font = value;
	}

	/**
	 * Gets the value of the preferredsize property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getPreferredsize()
	{
		return preferredsize;
	}

	/**
	 * Sets the value of the preferredsize property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setPreferredsize(String value)
	{
		this.preferredsize = value;
	}

	/**
	 * Gets the value of the minimumsize property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getMinimumsize()
	{
		return minimumsize;
	}

	/**
	 * Sets the value of the minimumsize property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setMinimumsize(String value)
	{
		this.minimumsize = value;
	}

	/**
	 * Gets the value of the maximumsize property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getMaximumsize()
	{
		return maximumsize;
	}

	/**
	 * Sets the value of the maximumsize property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setMaximumsize(String value)
	{
		this.maximumsize = value;
	}

	/**
	 * Gets the value of the alignmentx property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getAlignmentx()
	{
		return alignmentx;
	}

	/**
	 * Sets the value of the alignmentx property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setAlignmentx(String value)
	{
		this.alignmentx = value;
	}

	/**
	 * Gets the value of the alignmenty property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getAlignmenty()
	{
		return alignmenty;
	}

	/**
	 * Sets the value of the alignmenty property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setAlignmenty(String value)
	{
		this.alignmenty = value;
	}

	/**
	 * Gets the value of the inheritspopupmenu property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isInheritspopupmenu()
	{
		return inheritspopupmenu;
	}

	/**
	 * Sets the value of the inheritspopupmenu property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 */
	public void setInheritspopupmenu(Boolean value)
	{
		this.inheritspopupmenu = value;
	}

	/**
	 * Gets the value of the nextfocusablecomponent property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getNextfocusablecomponent()
	{
		return nextfocusablecomponent;
	}

	/**
	 * Sets the value of the nextfocusablecomponent property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setNextfocusablecomponent(String value)
	{
		this.nextfocusablecomponent = value;
	}

	/**
	 * Gets the value of the requestfocusenabled property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isRequestfocusenabled()
	{
		return requestfocusenabled;
	}

	/**
	 * Sets the value of the requestfocusenabled property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 */
	public void setRequestfocusenabled(Boolean value)
	{
		this.requestfocusenabled = value;
	}

	/**
	 * Gets the value of the verifyinputwhenfocustarget property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isVerifyinputwhenfocustarget()
	{
		return verifyinputwhenfocustarget;
	}

	/**
	 * Sets the value of the verifyinputwhenfocustarget property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 */
	public void setVerifyinputwhenfocustarget(Boolean value)
	{
		this.verifyinputwhenfocustarget = value;
	}

	/**
	 * Gets the value of the border property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getBorder()
	{
		return border;
	}

	/**
	 * Sets the value of the border property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setBorder(String value)
	{
		this.border = value;
	}

	/**
	 * Gets the value of the debuggraphicsoptions property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getDebuggraphicsoptions()
	{
		return debuggraphicsoptions;
	}

	/**
	 * Sets the value of the debuggraphicsoptions property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setDebuggraphicsoptions(String value)
	{
		this.debuggraphicsoptions = value;
	}

	/**
	 * Gets the value of the tooltiptext property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getTooltiptext()
	{
		return tooltiptext;
	}

	/**
	 * Sets the value of the tooltiptext property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setTooltiptext(String value)
	{
		this.tooltiptext = value;
	}

	/**
	 * Gets the value of the autoscrolls property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isAutoscrolls()
	{
		return autoscrolls;
	}

	/**
	 * Sets the value of the autoscrolls property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 */
	public void setAutoscrolls(Boolean value)
	{
		this.autoscrolls = value;
	}

	/**
	 * Gets the value of the opaque property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isOpaque()
	{
		return opaque;
	}

	/**
	 * Sets the value of the opaque property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 */
	public void setOpaque(Boolean value)
	{
		this.opaque = value;
	}

	/**
	 * Gets the value of the doublebuffered property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isDoublebuffered()
	{
		return doublebuffered;
	}

	/**
	 * Sets the value of the doublebuffered property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 */
	public void setDoublebuffered(Boolean value)
	{
		this.doublebuffered = value;
	}

	/**
	 * Gets the value of the layout property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getLayout()
	{
		return layout;
	}

	/**
	 * Sets the value of the layout property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setLayout(String value)
	{
		this.layout = value;
	}

	/**
	 * Gets the value of the focuscycleroot property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isFocuscycleroot()
	{
		return focuscycleroot;
	}

	/**
	 * Sets the value of the focuscycleroot property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 */
	public void setFocuscycleroot(Boolean value)
	{
		this.focuscycleroot = value;
	}

	/**
	 * Gets the value of the focustraversalpolicyprovider property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isFocustraversalpolicyprovider()
	{
		return focustraversalpolicyprovider;
	}

	/**
	 * Sets the value of the focustraversalpolicyprovider property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 */
	public void setFocustraversalpolicyprovider(Boolean value)
	{
		this.focustraversalpolicyprovider = value;
	}

	/**
	 * Gets the value of the name property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setName(String value)
	{
		this.name = value;
	}

	/**
	 * Gets the value of the size property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getSize()
	{
		return size;
	}

	/**
	 * Sets the value of the size property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setSize(String value)
	{
		this.size = value;
	}

	/**
	 * Gets the value of the locale property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getLocale()
	{
		return locale;
	}

	/**
	 * Sets the value of the locale property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setLocale(String value)
	{
		this.locale = value;
	}

	/**
	 * Gets the value of the location property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getLocation()
	{
		return location;
	}

	/**
	 * Sets the value of the location property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setLocation(String value)
	{
		this.location = value;
	}

	/**
	 * Gets the value of the bounds property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getBounds()
	{
		return bounds;
	}

	/**
	 * Sets the value of the bounds property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setBounds(String value)
	{
		this.bounds = value;
	}

	/**
	 * Gets the value of the ignorerepaint property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isIgnorerepaint()
	{
		return ignorerepaint;
	}

	/**
	 * Sets the value of the ignorerepaint property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 */
	public void setIgnorerepaint(Boolean value)
	{
		this.ignorerepaint = value;
	}

	/**
	 * Gets the value of the focusable property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isFocusable()
	{
		return focusable;
	}

	/**
	 * Sets the value of the focusable property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 */
	public void setFocusable(Boolean value)
	{
		this.focusable = value;
	}

	/**
	 * Gets the value of the focustraversalkeysenabled property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isFocustraversalkeysenabled()
	{
		return focustraversalkeysenabled;
	}

	/**
	 * Sets the value of the focustraversalkeysenabled property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 */
	public void setFocustraversalkeysenabled(Boolean value)
	{
		this.focustraversalkeysenabled = value;
	}

	/**
	 * Gets the value of the constraints property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getConstraints()
	{
		return constraints;
	}

	/**
	 * Sets the value of the constraints property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setConstraints(String value)
	{
		this.constraints = value;
	}

	/**
	 * Gets the value of the plaf property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getPlaf()
	{
		return plaf;
	}

	/**
	 * Sets the value of the plaf property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setPlaf(String value)
	{
		this.plaf = value;
	}

	/**
	 * Gets the value of the bundle property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getBundle()
	{
		return bundle;
	}

	/**
	 * Sets the value of the bundle property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setBundle(String value)
	{
		this.bundle = value;
	}

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setId(String value)
	{
		this.id = value;
	}

	/**
	 * Gets the value of the refid property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getRefid()
	{
		return refid;
	}

	/**
	 * Sets the value of the refid property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setRefid(String value)
	{
		this.refid = value;
	}

	/**
	 * Gets the value of the use property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getUse()
	{
		return use;
	}

	/**
	 * Sets the value of the use property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setUse(String value)
	{
		this.use = value;
	}

	/**
	 * Gets the value of the include property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getInclude()
	{
		return include;
	}

	/**
	 * Sets the value of the include property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setInclude(String value)
	{
		this.include = value;
	}

	/**
	 * Gets the value of the initclass property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getInitclass()
	{
		return initclass;
	}

	/**
	 * Sets the value of the initclass property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setInitclass(String value)
	{
		this.initclass = value;
	}

	/**
	 * Gets the value of the action property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getAction()
	{
		return action;
	}

	/**
	 * Sets the value of the action property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setAction(String value)
	{
		this.action = value;
	}

	/**
	 * Gets the value of the macosPreferences property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getMacosPreferences()
	{
		return macosPreferences;
	}

	/**
	 * Sets the value of the macosPreferences property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setMacosPreferences(String value)
	{
		this.macosPreferences = value;
	}

	/**
	 * Gets the value of the macosAbout property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getMacosAbout()
	{
		return macosAbout;
	}

	/**
	 * Sets the value of the macosAbout property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setMacosAbout(String value)
	{
		this.macosAbout = value;
	}

	/**
	 * Gets the value of the macosQuit property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getMacosQuit()
	{
		return macosQuit;
	}

	/**
	 * Sets the value of the macosQuit property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setMacosQuit(String value)
	{
		this.macosQuit = value;
	}

	/**
	 * Gets the value of the macosOpenapp property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getMacosOpenapp()
	{
		return macosOpenapp;
	}

	/**
	 * Sets the value of the macosOpenapp property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setMacosOpenapp(String value)
	{
		this.macosOpenapp = value;
	}

	/**
	 * Gets the value of the macosOpenfile property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getMacosOpenfile()
	{
		return macosOpenfile;
	}

	/**
	 * Sets the value of the macosOpenfile property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setMacosOpenfile(String value)
	{
		this.macosOpenfile = value;
	}

	/**
	 * Gets the value of the macosPrint property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getMacosPrint()
	{
		return macosPrint;
	}

	/**
	 * Sets the value of the macosPrint property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setMacosPrint(String value)
	{
		this.macosPrint = value;
	}

	/**
	 * Gets the value of the macosReopen property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getMacosReopen()
	{
		return macosReopen;
	}

	/**
	 * Sets the value of the macosReopen property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setMacosReopen(String value)
	{
		this.macosReopen = value;
	}

}
