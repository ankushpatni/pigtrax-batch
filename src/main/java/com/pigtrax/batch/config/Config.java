package com.pigtrax.batch.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="event" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="dataInfo">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="col" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="order" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                                     &lt;attribute name="isEmpty" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "event" })
@XmlRootElement(name = "config")
@SuppressWarnings("all")
public class Config {

	@XmlElement(required = true)
	protected List<Config.Event> event;

	/**
	 * Gets the value of the event property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the event property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getEvent().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link Config.Event }
	 * 
	 * 
	 */
	public List<Config.Event> getEvent() {
		if (event == null) {
			event = new ArrayList<Config.Event>();
		}
		return this.event;
	}

	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="dataInfo">
	 *           &lt;complexType>
	 *             &lt;complexContent>
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                 &lt;sequence>
	 *                   &lt;element name="col" maxOccurs="unbounded">
	 *                     &lt;complexType>
	 *                       &lt;complexContent>
	 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                           &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                           &lt;attribute name="order" type="{http://www.w3.org/2001/XMLSchema}int" />
	 *                           &lt;attribute name="isEmpty" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                         &lt;/restriction>
	 *                       &lt;/complexContent>
	 *                     &lt;/complexType>
	 *                   &lt;/element>
	 *                 &lt;/sequence>
	 *               &lt;/restriction>
	 *             &lt;/complexContent>
	 *           &lt;/complexType>
	 *         &lt;/element>
	 *       &lt;/sequence>
	 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "dataInfo" })
	public static class Event {

		@XmlElement(required = true)
		protected Config.Event.DataInfo dataInfo;
		@XmlAttribute(name = "id")
		protected String id;

		/**
		 * Gets the value of the dataInfo property.
		 * 
		 * @return possible object is {@link Config.Event.DataInfo }
		 * 
		 */
		public Config.Event.DataInfo getDataInfo() {
			return dataInfo;
		}

		/**
		 * Sets the value of the dataInfo property.
		 * 
		 * @param value
		 *            allowed object is {@link Config.Event.DataInfo }
		 * 
		 */
		public void setDataInfo(Config.Event.DataInfo value) {
			this.dataInfo = value;
		}

		/**
		 * Gets the value of the id property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getId() {
			return id;
		}

		/**
		 * Sets the value of the id property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setId(String value) {
			this.id = value;
		}

		/**
		 * <p>
		 * Java class for anonymous complex type.
		 * 
		 * <p>
		 * The following schema fragment specifies the expected content
		 * contained within this class.
		 * 
		 * <pre>
		 * &lt;complexType>
		 *   &lt;complexContent>
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
		 *       &lt;sequence>
		 *         &lt;element name="col" maxOccurs="unbounded">
		 *           &lt;complexType>
		 *             &lt;complexContent>
		 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
		 *                 &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
		 *                 &lt;attribute name="order" type="{http://www.w3.org/2001/XMLSchema}int" />
		 *                 &lt;attribute name="isEmpty" type="{http://www.w3.org/2001/XMLSchema}string" />
		 *               &lt;/restriction>
		 *             &lt;/complexContent>
		 *           &lt;/complexType>
		 *         &lt;/element>
		 *       &lt;/sequence>
		 *     &lt;/restriction>
		 *   &lt;/complexContent>
		 * &lt;/complexType>
		 * </pre>
		 * 
		 * 
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "col" })
		public static class DataInfo {

			@XmlElement(required = true)
			protected List<Config.Event.DataInfo.Col> col;

			/**
			 * Gets the value of the col property.
			 * 
			 * <p>
			 * This accessor method returns a reference to the live list, not a
			 * snapshot. Therefore any modification you make to the returned
			 * list will be present inside the JAXB object. This is why there is
			 * not a <CODE>set</CODE> method for the col property.
			 * 
			 * <p>
			 * For example, to add a new item, do as follows:
			 * 
			 * <pre>
			 * getCol().add(newItem);
			 * </pre>
			 * 
			 * 
			 * <p>
			 * Objects of the following type(s) are allowed in the list
			 * {@link Config.Event.DataInfo.Col }
			 * 
			 * 
			 */
			public List<Config.Event.DataInfo.Col> getCol() {
				if (col == null) {
					col = new ArrayList<Config.Event.DataInfo.Col>();
				}
				return this.col;
			}

			/**
			 * <p>
			 * Java class for anonymous complex type.
			 * 
			 * <p>
			 * The following schema fragment specifies the expected content
			 * contained within this class.
			 * 
			 * <pre>
			 * &lt;complexType>
			 *   &lt;complexContent>
			 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
			 *       &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
			 *       &lt;attribute name="order" type="{http://www.w3.org/2001/XMLSchema}int" />
			 *       &lt;attribute name="isEmpty" type="{http://www.w3.org/2001/XMLSchema}string" />
			 *     &lt;/restriction>
			 *   &lt;/complexContent>
			 * &lt;/complexType>
			 * </pre>
			 * 
			 * 
			 */
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "")
			public static class Col {

				@XmlAttribute(name = "key")
				protected String key;
				@XmlAttribute(name = "order")
				protected Integer order;
				@XmlAttribute(name = "isEmpty")
				protected String isEmpty;

				/**
				 * Gets the value of the key property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getKey() {
					return key;
				}

				/**
				 * Sets the value of the key property.
				 * 
				 * @param value
				 *            allowed object is {@link String }
				 * 
				 */
				public void setKey(String value) {
					this.key = value;
				}

				/**
				 * Gets the value of the order property.
				 * 
				 * @return possible object is {@link Integer }
				 * 
				 */
				public Integer getOrder() {
					return order;
				}

				/**
				 * Sets the value of the order property.
				 * 
				 * @param value
				 *            allowed object is {@link Integer }
				 * 
				 */
				public void setOrder(Integer value) {
					this.order = value;
				}

				/**
				 * Gets the value of the isEmpty property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getIsEmpty() {
					return isEmpty;
				}

				/**
				 * Sets the value of the isEmpty property.
				 * 
				 * @param value
				 *            allowed object is {@link String }
				 * 
				 */
				public void setIsEmpty(String value) {
					this.isEmpty = value;
				}

			}

		}

	}

}
