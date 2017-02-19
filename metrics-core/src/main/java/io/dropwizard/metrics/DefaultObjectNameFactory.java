package io.dropwizard.metrics;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public class DefaultObjectNameFactory implements ObjectNameFactory {

	@Override
	public ObjectName createName(String type, String domain, MetricName metricName) throws MalformedObjectNameException {
		StringBuilder nameBuilder = new StringBuilder(domain);
		nameBuilder.append(":name=");
		nameBuilder.append(formatProperty(metricName.getKey()));
		metricName.getTags().forEach((k,v)-> {
			nameBuilder.append(",");
			nameBuilder.append(formatProperty(k));
			nameBuilder.append("=");
			nameBuilder.append(formatProperty(v));
		});
		String objNameStr = nameBuilder.toString();
		ObjectName objectName = new ObjectName(objNameStr);
		return objectName;
	}


	/**
	 * Checks to see if the given string is patterned by checking to
	 * see if it contains a {@code *} or {@code ?} if so it will quote them
	 * @param property the property to check for patterned
	 * @return a valid string to add to the ObjectName
	 */
	private String formatProperty(String property) {
		if (property.contains("*") || property.contains("?")) {
			property = ObjectName.quote(property);
		}

		return property;
	}

}
