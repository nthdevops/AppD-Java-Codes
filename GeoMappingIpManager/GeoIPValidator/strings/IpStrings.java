package strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpStrings {
	public static long ipToLong(String ip) {
		Pattern p = Pattern.compile("[0-9]{1,3}");
        Matcher m = p.matcher(ip);
        try {
        	String result = "";
        	while(m.find()) {
                result = result.concat(m.group());
            }
        	long resultInt = Long.parseLong(result);
        	return resultInt;
        }catch(Exception e) {
        	return 0;
        }
	}
	
	public static String ipSegment(String ip, int segment) {
		Pattern p;
		switch(segment) {
        	case 1:
        		p = Pattern.compile("[0-9]{1,3}(?=\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})");
        		break;
        	case 2:
        		p = Pattern.compile("(?<=[0-9]{1,3}\\.)[0-9]{1,3}(?=\\.[0-9]{1,3}\\.[0-9]{1,3})");
        		break;
        	case 3:
        		p = Pattern.compile("(?<=[0-9]{1,3}\\.[0-9]{1,3}\\.)[0-9]{1,3}(?=\\.[0-9]{1,3})");
        		break;
        	default:
        		p = Pattern.compile("(?<=[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)[0-9]{1,3}");
        		break;
        }
		
        Matcher m = p.matcher(ip);
        try {
        	m.find();
        	return m.group(0);
        }catch(Exception e) {
        	return null;
        }
	}
	
	public static boolean validIp(String ip) {
		if(ipSegment(ip, 4) != null) {
			return true;
		}
		return false;
	}
	
	public static String ipParse(String ip, int segments) {
		Pattern p;
		switch(segments) {
        	case 1:
        		p = Pattern.compile("[0-9]{1,3}(?=\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})");
        		break;
        	case 2:
        		p = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}(?=\\.[0-9]{1,3}\\.[0-9]{1,3})");
        		break;
        	case 3:
        		p = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}(?=\\.[0-9]{1,3})");
        		break;
        	default:
        		p = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
        		break;
        }
		
        Matcher m = p.matcher(ip);
        try {
        	m.find();
        	return m.group();
        }catch(Exception e) {
        	return null;
        }
	}
	
	public static boolean ipIn(String ip, String from, String to) {
		String tempIp = ipParse(ip, 3);
		String tempFrom = ipParse(from, 3);
		String tempTo = ipParse(to, 3);
		long ipLong = ipToLong(ip);
		long fromIp =  ipToLong(from);
		long toIp =  ipToLong(to);
		if(tempIp != null && (tempIp.equals(tempFrom) || tempIp.equals(tempTo))) {
    		if(ipLong >= fromIp && ipLong <= toIp) {
        		return true;
        	}
		}
		return false;
	}
}
