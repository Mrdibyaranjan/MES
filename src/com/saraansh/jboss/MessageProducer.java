/**
 * 
 *//*
package com.saraansh.jboss;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import main.ConstantConfiguration;
import main.MultiSourceReader;

import org.apache.xmlbeans.XmlException;

import testXmlParse.TRANSACTIONINFODocument;
import utility.Utility;

*//**
 * @author Sanketh.Vijay
 *
 *//*
public class MessageProducer implements Runnable {

	static InitialContext iniCtx;

	QueueSender sender;
	//QueueBrowser browser;
	QueueSession session;
	
	ArrayList<StringBuilder> xmlList = null;
	String source = null;

	
	 * Static block which initialise JBOSS context object and session
	 
	static {
		
		try {
			iniCtx = new InitialContext(Utility.getJbossProperties());
		} catch (NamingException e) {
			e.printStackTrace();
			ConstantConfiguration.READER_LOGGER.error(e.getMessage());
			MultiSourceReader.JBOSS_DOWN = true;
		} catch (Exception e) {
			e.printStackTrace();
			ConstantConfiguration.READER_LOGGER.error(e.getMessage());
			MultiSourceReader.JBOSS_DOWN = true;
		}
	}

	*//**
	 * @param xmlList
	 * @param source
	 *//*
	public MessageProducer (ArrayList<StringBuilder> xmlList, String source) {
		this.xmlList = xmlList;
		this.source = source;
	}
	
	*//**
	 * Default constructor
	 * @throws NamingException 
	 * @throws JMSException 
	 *//*
	public MessageProducer() throws NamingException, JMSException {

		Object tmp = iniCtx.lookup("jms/RemoteConnectionFactory");
		QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
		QueueConnection conn = qcf.createQueueConnection("appadmin",
				"password");
		Queue que = (Queue) iniCtx.lookup("jms/queue/RealTimeInputQueue1");
		this.session = conn.createQueueSession(false,
				QueueSession.AUTO_ACKNOWLEDGE);
		conn.start();
		this.sender = session.createSender(que);
		//this.browser = session.createBrowser(que);
	}
	
	@Override
	public void run() {

		//sendMessage (new StringBuilder("<START>"));
		
		while (true) {
			if (getJbossQueueDepth() <= 9000)
				break;
			else
				try {
					Thread.sleep(1000 * 30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		
		for (StringBuilder xml: xmlList) {
			sendMessage (xml);
		}

		//sendMessage (new StringBuilder("<END>"));

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	*//**
	 * This method will return the JMS input queue depth
	 * @return
	 *//*
	@SuppressWarnings("unused")
	private int getJbossQueueDepth() {
		int depth = 0;
		try {
			for (@SuppressWarnings("unchecked")
			Enumeration<QueueBrowser> e = browser.getEnumeration(); e.hasMoreElements();e.nextElement())
				depth++;
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return depth;
	}

	*//**
	 * This method sends message to Jboss input queue
	 * @param xml
	 *//*
	private void sendMessage(StringBuilder xml) {
		
		try {
			TRANSACTIONINFODocument.Factory.parse(xml.toString());
			TextMessage tm = session.createTextMessage(xml+"");
			sender.send(tm);
		} catch (XmlException e) {
			ConstantConfiguration.READER_LOGGER.error(xml.toString());
			e.printStackTrace();
		} catch (JMSException e) {
			ConstantConfiguration.READER_LOGGER.error("JBOSS Connection error "+e);
			e.printStackTrace();
			System.exit(1);
		}
	}

	public ArrayList<StringBuilder> getXmlList() {
		return xmlList;
	}

	public void setXmlList(ArrayList<StringBuilder> xmlList) {
		this.xmlList = xmlList;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
*/