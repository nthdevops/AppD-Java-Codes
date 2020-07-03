package analysis;

import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Row;

import strings.IpStrings;
import xlsx.XlsxManager;
import xml.XmlManager;


public class XmlXlsxCorrelator {
	private XmlManager xml;
	private XlsxManager xlsx;
	
	public void definePaths(String xmlPath, String xlsxPath, String sheetName) {
		if(xmlPath.equals("dev")) {
			xml = new XmlManager("C:/Users/nthma/Desktop/rdFiles/config/geo-ip-mappings.xml");
		}else {
			xml = new XmlManager(xmlPath);
		}
		
    	if(xlsxPath.equals("dev")) {
    		xlsx = new XlsxManager("D:/OneDrive/Econocom/Clientes/RD/lista_banco12.xlsx", "IpsCities");
    	}else {
    		xlsx = new XlsxManager(xlsxPath, sheetName);
    	}
	}
	
	public void xlsxXmlCitiesCheck() {
		System.out.println("Verificando se as cidades do Excel estão mapeadas no XML!\n");
		ArrayList<Row> allIpsXlsx =  xlsx.getRows();
		for(Row row: allIpsXlsx) {
			String ipExcel = xlsx.getIp(row);
			String cityExcel = xlsx.getCity(row);
			String cityXml = xml.getCityByIp(ipExcel);
			if(cityXml != null) {
				if(!cityExcel.equals(cityXml)) {
					System.out.println("Cidades divergentes\nIP: " + ipExcel + "\nCExcel: " + cityExcel + "\nCXml: " + cityXml + "\n\n");
				}
			}
		}
		System.out.println("\nAnalise finalizada");
	}

	public void xlsxXmlIpsCheck() {
		System.out.println("Verificando se os IPs do Excel estão mapeados no XML!\n");
		ArrayList<Row> allIpsXlsx =  xlsx.getRows();
		for(Row row: allIpsXlsx) {
			String ip = IpStrings.ipParse(xlsx.getIp(row),4);
			if(!xml.ipMapped(ip)) {
				if(ip != null)
					System.out.println("Invalid: " + ip);
			}
		}
		System.out.println("\nAnalise finalizada\n");
	}

	public void xmlXlsxIpsCheck() {
		System.out.println("Verificando se os IPs mapeados no XML estão no Excel!\n");
		ArrayList<String> allFromIpsXml =  xml.getAllFromIPs();
		ArrayList<String> allToIpsXml =  xml.getAllToIPs();
		ArrayList<Row> allIpsXlsx =  xlsx.getRows();
		if(allFromIpsXml.size() != allToIpsXml.size()) {
			System.out.println("Xml Parameters are invalid\nProcess aborting\n");
			return;
		}
		for(int i = 0; i < allFromIpsXml.size(); i++) {
			int count = 0;
			String from = allFromIpsXml.get(i);
			String to = allToIpsXml.get(i);
			for(Row row: allIpsXlsx) {
				if(IpStrings.ipIn(xlsx.getIp(row), from, to)) {
					count++;
					break;
				}
			}
			if(count == 0) {
				System.out.println("Range not in Excel!\nFrom: " + from + "\nTo: " + to);
			}
		}
		System.out.println("\nAnalise finalizada\n");
	}
	
	public void xmlCheck() {
		System.out.println("Verificando se os IPs mapeados no XML são válidos!\n");
		ArrayList<String> allFromIpsXml =  xml.getAllFromIPs();
		ArrayList<String> allToIpsXml =  xml.getAllToIPs();
		if(allFromIpsXml.size() != allToIpsXml.size()) {
			System.out.println("Xml Parameters are invalid\nProcess aborting\n");
			return;
		}
		String from;
		String to;
		long fromLong = 0;
		long toLong = 0;
		for(int i = 0; i < allFromIpsXml.size(); i++) {
			from = allFromIpsXml.get(i);
			to  = allToIpsXml.get(i);
			fromLong = IpStrings.ipToLong(from);
			toLong = IpStrings.ipToLong(to);
			if(fromLong > toLong || !IpStrings.validIp(from) || !IpStrings.validIp(to)) {
				System.out.println("Invalid IP range!\nFrom: " + from + "\nTo: " + to);
			}
		}
		System.out.println("\nAnalise finalizada\n");
	}
	
	public XmlXlsxCorrelator(String xmlPath, String xlsxPath, String sheetName) {
		definePaths(xmlPath, xlsxPath, sheetName);
	}
}
