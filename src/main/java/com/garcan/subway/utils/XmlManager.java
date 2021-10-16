package com.garcan.subway.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Clase para manejo de archivos XML.
 */
public class XmlManager {

  public static final String UTF8 = "UTF-8";

  private HashMap<String, String> namespaces;
  private Document xmlDoc;
  private DocumentBuilderFactory bFactory;
  private DocumentBuilder builder;
  private XPathFactory xpathfactory;
  private XPath xpath;
  private TransformerFactory transformerFactory;
  private Transformer transformerXML;
  private String xmlPath;
  private String encoding = UTF8;

  private static final String FEATURE_DISALLOW_DOCTYPE_DECL =
      "http://apache.org/xml/features/disallow-doctype-decl";

  public static String normalizeXPath(final String xpath) {
    return "normalize-space(" + xpath + ")";
  }

  public final String getXmlPath() {
    return this.xmlPath;
  }

  public void clearNamespaces() {
    this.namespaces = null;
  }

  public void close() {
    if (this.xmlDoc != null) {
      this.xmlDoc = null;
    }
    if (this.bFactory != null) {
      this.bFactory = null;
    }
    if (this.builder != null) {
      this.builder = null;
    }
    if (this.xpathfactory != null) {
      this.xpathfactory = null;
    }
    if (this.xpath != null) {
      this.xpath = null;
    }
    if (this.transformerFactory != null) {
      this.transformerFactory = null;
    }
    if (this.transformerXML != null) {
      this.transformerXML = null;
    }
  }

  public BigDecimal getBigDecimal(final String xpath) throws XPathExpressionException {
    return getBigDecimalFromNode(null, xpath, true);
  }

  /**
   * normalize es true por default.
   *
   * @throws XPathExpressionException
   */
  public BigDecimal getBigDecimalFromNode(final Node node, final String xpath)
      throws XPathExpressionException {
    return getBigDecimalFromNode(node, xpath, true);
  }

  public BigDecimal getBigDecimalFromNode(final Node node, final String xpath,
      final boolean normalize) throws XPathExpressionException {
    return getBigDecimalFromNode(node, xpath, normalize, BigDecimal.ZERO);
  }

  public BigDecimal getBigDecimalFromNode(final Node node, final String xpath,
      final boolean normalize,
      final BigDecimal retIfFail) throws XPathExpressionException {
    try {
      return new BigDecimal(
          (String) getXPathResultFromNode(node, xpath, XPathConstants.STRING, normalize));
    } catch (final NumberFormatException e) {
      return retIfFail;
    }
  }

  /**
   * normalize es true por default.
   *
   * @throws XPathExpressionException
   */
  public BigDecimal getNumber(final String xpath) throws XPathExpressionException {
    return getNumber(xpath, true);
  }

  public BigDecimal getNumber(final String xpath, final boolean normalize)
      throws XPathExpressionException {
    try {
      return new BigDecimal(
          (String) getXPathResultFromNode(null, xpath, XPathConstants.STRING, normalize));
    } catch (final NumberFormatException e) {
      return null;
    }
  }

  public Document getXmlDoc() {
    return this.xmlDoc;
  }

  /**
   * Default XPathConstants.STRING
   *
   * @throws XPathExpressionException
   */
  public <T> T getXPathResult(final String xpathExpression) throws XPathExpressionException {
    return getXPathResult(xpathExpression, XPathConstants.STRING);
  }

  /**
   * normalize default false
   *
   * @param xpathConstant {@link javax.xml.xpath.XPathConstants}
   * @throws XPathExpressionException
   */
  @SuppressWarnings("unchecked")
  public <T> T getXPathResult(final String xpathExpression, final QName xpathConstant)
      throws XPathExpressionException {
    return (T) getXPathResultFromNode(null, xpathExpression, xpathConstant, false);
  }

  /**
   * normalize default false
   *
   * @throws XPathExpressionException
   */
  @SuppressWarnings("unchecked")
  public <T> T getXPathResultFromNode(final Node node, final String xpathExpression,
      final QName xpathConstant) throws XPathExpressionException {
    return (T) getXPathResultFromNode(node, xpathExpression, xpathConstant, false);
  }

  /**
   * @param xpathConstant {@link javax.xml.xpath.XPathConstants}
   * @throws XPathExpressionException
   */
  @SuppressWarnings("unchecked")
  public <T> T getXPathResultFromNode(final Node node, final String xpathExpression,
      final QName xpathConstant, final boolean normalize) throws XPathExpressionException {
    if (this.xpathfactory == null) {
      this.xpathfactory = XPathFactory.newInstance();
    }
    if (this.xpath == null) {
      this.xpath = this.xpathfactory.newXPath();
    } else {
      this.xpath.reset();
    }
    final XPathExpression expr =
        this.xpath.compile(normalize ? normalizeXPath(xpathExpression) : xpathExpression);
    final Object xPathResult = expr.evaluate(node != null ? node : this.xmlDoc, xpathConstant);
    if (xPathResult == null) {
      return null;
    }
    return (T) xPathResult.getClass().cast(xPathResult);
  }

  private void initTransformerFactory() throws TransformerFactoryConfigurationError {
    if (this.transformerFactory == null) {
      this.transformerFactory = TransformerFactory.newInstance();
    }
  }

  private void initTransformerXML() throws TransformerConfigurationException {
    initTransformerFactory();
    if (this.transformerXML == null) {
      this.transformerXML = this.transformerFactory.newTransformer();
      this.transformerXML.setOutputProperty(OutputKeys.ENCODING,
          this.encoding != null ? this.encoding : XmlManager.UTF8);
      this.transformerXML.setOutputProperty(OutputKeys.INDENT, "yes");
    } else {
      this.transformerXML.reset();
    }
  }

  /**
   * Load a XML file. Use default encoding.
   * <p>
   *
   * @param xmlFile
   * @throws SAXException
   * @throws IOException
   * @throws ParserConfigurationException
   */
  public XmlManager loadXML(final File xmlFile)
      throws ParserConfigurationException, IOException, SAXException {
    return loadXML(xmlFile.getPath());
  }

  /**
   * Load a XML file.
   * <p>
   *
   * @param xmlFile
   * @param encoding
   * @throws SAXException
   * @throws IOException
   * @throws ParserConfigurationException
   */
  public XmlManager loadXML(final File xmlFile, final String encoding)
      throws ParserConfigurationException, IOException, SAXException {
    return loadXML(xmlFile.getPath(), encoding);
  }

  /**
   * Load a XML file. Only need the string path where is the XML file. Use default encoding.
   * <p>
   *
   * @param xmlPath Path where is the XML file.
   * @throws SAXException
   * @throws IOException
   * @throws ParserConfigurationException
   */
  public XmlManager loadXML(final String xmlPath)
      throws ParserConfigurationException, IOException, SAXException {
    return loadXML(xmlPath, XmlManager.UTF8);
  }

  /**
   * Load a XML file.
   * <p>
   *
   * @param xmlPath
   * @param encoding
   * @throws ParserConfigurationException
   * @throws IOException
   * @throws FileNotFoundException
   * @throws UnsupportedEncodingException
   * @throws SAXException
   */
  public XmlManager loadXML(final String xmlPath, final String encoding)
      throws ParserConfigurationException, IOException, SAXException {
    if (this.bFactory == null) {
      this.bFactory = DocumentBuilderFactory.newInstance();
      this.bFactory.setFeature(FEATURE_DISALLOW_DOCTYPE_DECL, true);
    }
    if (this.builder == null) {
      this.builder = this.bFactory.newDocumentBuilder();
    }
    if (encoding != null) {
      this.encoding = encoding;
      try (InputStreamReader in = new InputStreamReader(new FileInputStream(xmlPath), encoding);
          BufferedReader reader = new BufferedReader(in);) {
        String line = null;
        final StringBuilder strXML = new StringBuilder();
        while ((line = reader.readLine()) != null) {
          strXML.append(line);
        }
        this.xmlDoc = this.builder.parse(new InputSource(new StringReader(strXML.toString())));
      }
    } else {
      this.xmlDoc = this.builder.parse(xmlPath);
    }
    this.xmlPath = xmlPath;
    return this;
  }

  public BigDecimal parseBigDecimal(final String strNumber) {
    try {
      return new BigDecimal(strNumber);
    } catch (final NumberFormatException e) {
      return null;
    }
  }

  public void printXML(final OutputStream out) throws TransformerException {
    initTransformerXML();
    this.transformerXML.transform(new DOMSource(this.xmlDoc), new StreamResult(out));
  }

  public void putNamespace(final String prefix, final String URL) {
    if (this.namespaces == null) {
      this.namespaces = new HashMap<>();
    }
    this.namespaces.put(prefix, URL);
  }

  public void readXML(final String xml)
      throws ParserConfigurationException, SAXException, IOException {
    readXML(xml, XmlManager.UTF8);
  }

  /**
   * Read xml from memory String line.
   * <p>
   *
   * @param xml String that contain the serialized XML.
   * @throws ParserConfigurationException
   * @throws IOException
   * @throws SAXException
   * @throws UnsupportedEncodingException
   */
  public void readXML(final String xml, final String encoding)
      throws ParserConfigurationException, SAXException, IOException {
    if (this.bFactory == null) {
      this.bFactory = DocumentBuilderFactory.newInstance();
    }
    if (this.builder == null) {
      this.builder = this.bFactory.newDocumentBuilder();
    } else {
      this.builder.reset();
    }
    this.xmlDoc = this.builder.parse(new ByteArrayInputStream(xml.getBytes(encoding)));
  }

  /**
   * Save {@link org.w3c.dom.Document} to a string pathfilename given. Non-tested.
   * <p>
   *
   * @param xmlDoc
   * @param fileOutput
   * @throws Exception
   */
  public boolean saveXML(final String fileOutput) {
    try {
      initTransformerXML();
      this.xmlDoc.setXmlStandalone(true);
      final DOMSource source = new DOMSource(this.xmlDoc);
      final StreamResult result = new StreamResult(fileOutput);
      this.transformerXML.transform(source, result);
      return true;
    } catch (final Exception e) {
      return false;
    }
  }

  public List<Node> toList(final NodeList nodeList) {
    return IntStream.range(0, nodeList.getLength())
        .mapToObj(nodeList::item)
        .collect(Collectors.toList());
  }
}
